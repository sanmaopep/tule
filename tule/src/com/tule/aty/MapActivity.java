package com.tule.aty;

import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.tule.R;
import com.tule.net.HSharing;
import com.tule.net.HSharing.AddSharingCallback;
import com.tule.net.HSharing.getSharingCallback;
import com.tule.net.bean.Sharing;
import com.tule.utils.MyUtils;
import com.tule.utils.T;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MapActivity extends Activity implements LocationSource, AMapLocationListener {

	private MapView mapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;

	// handler为加载tag而用
	private Handler handler = new Handler();
	// 矛头
	private Marker MaoTou;
	/**
	 * 导航按钮
	 */
	private View vMine;
	private View vAdd;

	private View vXunLe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		init();

		initView();

		initMaoTou();// 初始化矛头

		initData();// 初始化数据

		initaMapListeners();// 初始化aMap的监听器

	}

	/**
	 * 刷新地图上的标签
	 */
	private void refreshMapTags() {
		initData();
	}

	/**
	 * 初始化aMap监听器
	 */
	private void initaMapListeners() {
		aMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				if (arg0.getId() == MaoTou.getId()) {
					T.showShort(getApplicationContext(), "我是萌萌哒的矛头~，你点哪里我就去哪里");
				} else {
					T.showShort(MapActivity.this, "你点击了一个标签");
					Sharing sharing = (Sharing) arg0.getObject();
					Dialog dialog=new SharingInfoDialog(MapActivity.this, sharing.getUsername(), sharing.getContent());
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.show();
				}
				return false;
			}
		});
	}

	/**
	 * 添加标签到服务端
	 * 
	 * @param position
	 * @param content
	 */
	private void addTagtoServer(LatLng position, String content) {
		HSharing.AddSharing(this, content, MyUtils.Lat2BmobGeo(position), new AddSharingCallback() {

			@Override
			public void Success() {
				T.showShort(getApplicationContext(), "分享成功");
			}

			@Override
			public void Fail(int faliurecode, String message) {
				T.showShort(getApplicationContext(), "分享失败：" + message);
			}
		});
	}

	/**
	 * 将标签展示到地图上
	 * 
	 * @param position
	 * @param sharing
	 */
	private void addTagtoMap(LatLng position, Sharing sharing) {
		Marker theMarker = aMap.addMarker(new MarkerOptions().anchor((float) 0.5, (float) 0.5)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.sharing)).position(position));
		theMarker.setObject(sharing);
	}

	private void initData() {
		// 按照当前地图中心位置查找
		HSharing.getSharingByPlace(getApplicationContext(), MyUtils.Lat2BmobGeo(aMap.getCameraPosition().target),
				new getSharingCallback() {

					@Override
					public void Success(final List<Sharing> result) {
						/**
						 * 为避免卡而使用新新线程+Handler处理
						 */
						new Thread() {
							public void run() {
								for (final Sharing sharing : result) {
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											addTagtoMap(MyUtils.BmobGeo2Lat(sharing.getGeoPoint()), sharing);
										}
									}, 200);
								}

							};
						}.start();
					}

					@Override
					public void Fail(int faliurecode, String result) {
						T.showShort(getApplicationContext(), "加载信息失败~");
					}
				});
	}

	private void initMaoTou() {
		float ANCHOR = (float) 0.5;
		// 初始化移动的矛头
		MarkerOptions maotou = new MarkerOptions().draggable(true)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_maotou))
				.position(aMap.getCameraPosition().target).anchor(ANCHOR, ANCHOR);
		MaoTou = aMap.addMarker(maotou);
		MaoTou.setDraggable(true);

		/**
		 * 点击更换矛头位置
		 */
		aMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng arg0) {
				MaoTou.setPosition(arg0);
			}
		});

	}

	private void initView() {

		vMine = findViewById(R.id.tab_mine);
		vMine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), PersonalCenterActivity.class);
				startActivity(intent);
			}
		});

		vAdd = findViewById(R.id.iv_mid_add);
		vAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 添加标签
				// 获取当前矛头的位置
				final LatLng lat = MaoTou.getPosition();
				// 显示一个窗口
				final EditText etText = new EditText(MapActivity.this);
				Log.d("dialogtest", "test1");
				AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
				builder.setTitle("添加标签").setView(etText).setPositiveButton("分享", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d("dialogtest", "test2");
						String content = etText.getText().toString();
						addTagtoServer(lat, content);
						refreshMapTags();
					}

				}).setNegativeButton("取消", null).show();
			}
		});

		vXunLe = findViewById(R.id.ivXunLe);
		vXunLe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 点击寻乐按钮
				Intent intent = new Intent(getApplicationContext(), XunLeActivity.class);

				
				intent.putExtra("BmobGeoPoint", MyUtils.Lat2BmobGeo(aMap.getCameraPosition().target));
				/**
				 * 把当前的地理位置也传过去
				 */
				startActivity(intent);
			}
		});
	}

	/**
	 * 地图乱七八糟
	 **************************************************************** 
	 ****************************************************************
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();// 返回aMap对象
			aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
			setUpMap();// 参考setupMap()函数
		}
	}

	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
			} else {
				Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
		}

	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates((AMapLocationListener) this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

}

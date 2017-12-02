package com.tule.aty;

import java.util.ArrayList;
import java.util.List;

import com.tule.R;
import com.tule.aty.adapter.FindSharingAdapter;
import com.tule.net.HSharing;
import com.tule.net.HSharing.getSharingCallback;
import com.tule.net.bean.Sharing;
import com.tule.utils.T;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class XunLeActivity extends Activity {

	private List<Sharing> Data;
	private ListView lvXunLe;
	private FindSharingAdapter adapter;

	private BmobGeoPoint point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xun_le);

		Data = new ArrayList<Sharing>();

		getGeoPoint();
		getData();
		initView();
	}

	private void getGeoPoint() {
		point = (BmobGeoPoint) getIntent().getSerializableExtra("BmobGeoPoint");
	}

	private void initView() {
		lvXunLe = (ListView) findViewById(R.id.lv_xunle);

		adapter=new FindSharingAdapter(this, (ArrayList<Sharing>)Data);
		lvXunLe.setAdapter(adapter);
		
		lvXunLe.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SharingInfoDialog dialog = new SharingInfoDialog(XunLeActivity.this,
						Data.get(position).getUsername(), Data.get(position).getContent());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.show();
			}
		});
	}

	private void getData() {
		HSharing.getSharingByPlace(getApplicationContext(), point, new getSharingCallback() {

			@Override
			public void Success(List<Sharing> result) {
				if (result.size() > 0) {
					Log.d("result size", new Integer(result.size()).toString());
					T.showShort(getApplicationContext(), "加载信息成功");
					adapter.refresh((ArrayList<Sharing>)result);
					adapter.notifyDataSetChanged();
					Data = result;
				} else {
					T.showShort(getApplicationContext(), "好像没有什么数据..");
				}
			}

			@Override
			public void Fail(int faliurecode, String result) {
				T.showShort(getApplicationContext(), "加载信息失败");
			}
		});

	}

	public void back(View V) {
		finish();
	}

}

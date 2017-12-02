package com.tule.net;

import java.util.List;

import com.tule.Config;
import com.tule.net.bean.Sharing;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 分享表相关的类
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class HSharing extends Net {
	
	/**
	 * 添加分享
	 * @param context
	 * @param content 內容
	 * @param point 地理位置
	 * @param callback
	 */
	public static void AddSharing(Context context,
			String content,
			BmobGeoPoint geoPoint,
			final AddSharingCallback callback){
		Sharing sharing=new Sharing();
		sharing.setContent(content);
		sharing.setGeoPoint(geoPoint);
		//获取当前用户id并存储
		String userid=Config.getCurrentUserId(context);
		sharing.setUserid(userid);
		sharing.setUsername(Config.getCurrentUser(context).getUsername());
		Log.d("Userid in Sharing", userid);
		
		sharing.save(context, new SaveListener() {
			@Override
			public void onSuccess() {
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				callback.Fail(arg0, arg1);
			}
		});
	}
	
	public interface AddSharingCallback{
		public void Success();
		public void Fail(int faliurecode,String message);
	}
	

	/**
	 * 获取最近的N条消息，N取自于Config.java
	 * @param context
	 * @param geoPoint
	 * @param callback
	 */
	public static void getSharingByPlace(Context context,
			BmobGeoPoint geoPoint,
			final getSharingCallback callback
			){
		BmobQuery<Sharing> query=new BmobQuery<Sharing>();
		query.addWhereNear("GeoPoint",geoPoint);
		query.setLimit(Config.NEAREST_SHARING);
		
		query.findObjects(context, new FindListener<Sharing>() {
			
			@Override
			public void onSuccess(List<Sharing> arg0) {
				// TODO Auto-generated method stub
				callback.Success(arg0);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				callback.Fail(arg0, arg1);
			}
		}
		);
		
	}
	public interface getSharingCallback{
		public void Success(List<Sharing> result);
		public void Fail(int faliurecode,String result);
	}
	
	/**
	 * 删除分享信息的回调,通过sharing的id进行删除
	 * @param context
	 * @param sharingid
	 * @param callback
	 */
	public static void DeleteSharing(Context context,
			String sharingid,
			final DeleteSharingCallback callback
			){
		Sharing sharing=new Sharing();
		sharing.setObjectId(sharingid);
		sharing.delete(context, new DeleteListener() {
			
			@Override
			public void onSuccess() {
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				callback.Fail(arg0, arg1);
			}
		});
		
	}
	
	public interface DeleteSharingCallback{
		public void Success();
		public void Fail(int faliurecode,String message);
	}
	
	/**
	 * 删除一条分享，直接基于sharing个体进行删除
	 * @param context
	 * @param sharing
	 * @param callback
	 */
	public static void DeleteSharing(Context context,
			Sharing sharing,
			final DeleteSharingCallback callback
			){
		sharing.delete(context,new DeleteListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				callback.Fail(arg0, arg1);
			}
		});
	}
	
	/**
	 * 按照userid进行分享的查找
	 * @param context
	 * @param userid
	 * @param callback
	 */
	public static void getSharingByUserID(Context context,
			String userid,
			final getSharingCallback callback
			){
		BmobQuery<Sharing> query=new BmobQuery<Sharing>();
		query.addWhereEqualTo("userid", userid);
		query.findObjects(context, new FindListener<Sharing>() {
			
			@Override
			public void onSuccess(List<Sharing> arg0) {
				// TODO Auto-generated method stub
				callback.Success(arg0);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				callback.Fail(arg0, arg1);
			}
		});
		
	}
	

	/**
	 * 按照当前的用户进行查找
	 * @param context
	 * @param callback
	 */
	public static void getSharingByCurrentUser(Context context,
			final getSharingCallback callback
			){
		Log.d("test in sharing", "test1");
		String userid=Config.getCurrentUserId(context);
		Log.d("test in sharing", "test2");
		BmobQuery<Sharing> query=new BmobQuery<Sharing>();
		Log.d("test in sharing", "test3");
		query.addWhereEqualTo("userid", userid);
		Log.d("test in sharing", "test4");
		query.findObjects(context, new FindListener<Sharing>() {
			
			@Override
			public void onSuccess(List<Sharing> arg0) {
				Log.d("test in sharing", "test5");
				callback.Success(arg0);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.d("test in sharing", "test6");
				callback.Fail(arg0, arg1);
			}
		});
	}
	
	public static void getSharingByPlaceWithUserId(Context context,
			BmobGeoPoint point,
			final getSharingByPlaceWithUserIdCallback callback
			){
		
	}
	
	public interface getSharingByPlaceWithUserIdCallback{
		public void Success();
		public void Fail();
	}
	
}

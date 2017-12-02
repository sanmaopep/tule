package com.tule;

import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.tule.net.bean.User;
import com.tule.utils.SPUtils;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobUser;

/**
 * 客户端配置文件
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class Config {
	
	/**
	 * 是否第一次启动查询的SPUkey
	 */
	public static final String IS_FIRST_RUN_KEY="first_run";
	//APP的名字
	public static final String APP_NAME="tule";
	//Bmob的APIkey
	public static final String BMOB_APP_ID="730e598743f3e0363deaa733adb83afd";
	//高德地图的APIkey
	//public static final String AMAP_APP_ID="";
	//加载页面的延迟时间
	public static final long LOAD_DELAY_TIME = 2000;
	//加载最近的N条分享
	public static final int NEAREST_SHARING=20;
	//引导页的三张图片
	public static final int [] GUIDE_IMAGES={
			R.drawable.guide_1, 
			R.drawable.guide_2, 
			R.drawable.guide_3 
		};
	//发送验证码的模板
	public static final String SMS_MODEL ="发送验证码2";
	//ListView中内容简写后的长度
	public static final int LIST_CONTENT_STRING_LENGTH = 10;


	
	
	/**
	 * 获取当前的用户
	 * @param context
	 * @return
	 */
	public static User getCurrentUser(Context context){	
		return BmobUser.getCurrentUser(context,User.class);
	}
	public static String getCurrentUserId(Context context){
		return BmobUser.getCurrentUser(context).getObjectId();
	}
	/**
	 * 是否第一次启动的方法
	 * @param context
	 */
	public static void setIsFirstRun(Context context){
		SPUtils.put(context, IS_FIRST_RUN_KEY, true);
		Log.d("FirstRun", "setted");
	}
	public static boolean getIsFirstRun(Context context){	
		Boolean result=(Boolean) SPUtils.get(context, IS_FIRST_RUN_KEY, false);
		Log.d("FirstRun",result.toString() );
		return result;
	}


	
	
}

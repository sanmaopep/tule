package com.tule.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amap.api.maps2d.model.LatLng;

import cn.bmob.v3.datatype.BmobGeoPoint;

public class MyUtils {

	/**
	 * 判断电话号码是否正确
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean CheckPhoneNum(String mobiles) {
		// 这里是phonenum的正则表达式
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[5-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "---");
		return m.matches();
	}
	
	/**
	 * 高德的LatLng转换为BmobGeoPoint
	 * @param arg0
	 * @return
	 */
	public static BmobGeoPoint Lat2BmobGeo(LatLng arg0){
		return new BmobGeoPoint(arg0.longitude,arg0.latitude);
	}
	
	/**
	 * bmob的地理位置转换为高德Lat
	 * @param arg0
	 * @return
	 */
	public static LatLng BmobGeo2Lat(BmobGeoPoint arg0){
		return new LatLng(arg0.getLatitude(), arg0.getLongitude());
		
	}
}

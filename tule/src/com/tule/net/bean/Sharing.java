package com.tule.net.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 分享表
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class Sharing extends BmobObject {

	private String content;
	private String userid;
	private BmobGeoPoint GeoPoint;
	private String username;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BmobGeoPoint getGeoPoint() {
		return GeoPoint;
	}
	public void setGeoPoint(BmobGeoPoint geoPoint) {
		GeoPoint = geoPoint;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}

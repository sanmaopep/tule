package com.tule.net.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 用户表
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class User extends BmobUser{
	
	private String sex;
	private String school;
	private String birthdata;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getBirthdata() {
		return birthdata;
	}
	public void setBirthdata(String birthdata) {
		this.birthdata = birthdata;
	}
	
	
	
	
}

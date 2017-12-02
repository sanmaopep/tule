package com.tule;

import com.tule.aty.LoaderActivity;
import com.tule.aty.WelcomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;


/**
 * 主程序的入口
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Bmob初始化
		Bmob.initialize(this, Config.BMOB_APP_ID);
		
		//第一次加载进入welcome，否则进入加载页面
		Intent intent=new Intent(this,LoaderActivity.class);
		//如果不是第一次启动
		if(!Config.getIsFirstRun(this)){
			Config.setIsFirstRun(this);
			intent.setClass(this, WelcomeActivity.class);
		}
		
		
		startActivity(intent);
		finish();
	}
}

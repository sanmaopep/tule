package com.tule.aty;

import com.tule.Config;
import com.tule.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 加载界面
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class LoaderActivity extends Activity {

	//利用handler延迟达到加载目的
	private Handler myhandler=new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader);
		
		myhandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoaderActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, Config.LOAD_DELAY_TIME);
	}
}

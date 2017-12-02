package com.tule.aty;

import com.tule.Config;
import com.tule.R;
import com.tule.utils.T;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PersonalCenterActivity extends Activity {

	private TextView tvUsername;
	
	private View VpersonalDetailed;
	private View VmyTag;
	private View addImage;
	private View vMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		
		initView();
		initInfo();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		tvUsername = (TextView) findViewById(R.id.tv_personalCenter_name);
		
		VpersonalDetailed=findViewById(R.id.personalCenter_data);
		VmyTag=findViewById(R.id.personalCenter_tag);
		addImage=findViewById(R.id.iv_mid_add);
		vMap=findViewById(R.id.tab_map);
		
		vMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		VmyTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),MyTagsActivity.class);
				startActivity(intent);
			}
		});
		
		addImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				T.showShort(getApplicationContext(), "不在地图，不能添加标签");
			}
		});
		
		VpersonalDetailed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),PsersonalDetailedActivity.class);
				startActivity(intent);
			}
		});
				
	}
	
	/**
	 * 初始化用户个人信息
	 */
	private void initInfo() {
		tvUsername.setText(Config.getCurrentUser(this).getUsername());
	}
	
}

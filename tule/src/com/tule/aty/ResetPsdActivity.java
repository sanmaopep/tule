package com.tule.aty;

import com.tule.R;
import com.tule.net.HUser;
import com.tule.net.HUser.SendCheckNumCallback;
import com.tule.net.HUser.VerifySMSCallback;
import com.tule.net.HUser.resetPassCallback;
import com.tule.utils.T;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 重置密码界面
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class ResetPsdActivity extends Activity {
	
	private EditText etPhone;
	private EditText etCheckNum;
	private EditText etNewPass;
	
	private Button btngetCheckNum;
	private Button btncheckCheckNum;
	private Button btnComplete;
	
	private String phone;
	private String checknum;
	private String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_psd);
		
		initView();
	}
	
	
	private void initView() {
		
		etPhone=(EditText) findViewById(R.id.et_phone);
		etCheckNum=(EditText) findViewById(R.id.et_checknum);
		etNewPass=(EditText) findViewById(R.id.et_newpass);
		
		btngetCheckNum=(Button) findViewById(R.id.btn_get_checknum);
		btncheckCheckNum=(Button) findViewById(R.id.btn_check_checknum);
		btnComplete=(Button) findViewById(R.id.btn_complete);
		
		btngetCheckNum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phone=etPhone.getText().toString();
				//获取验证码按下
				HUser.sendCheckNum(ResetPsdActivity.this, phone, new SendCheckNumCallback() {
					@Override
					public void Success() {
						T.showShort(ResetPsdActivity.this, "发送验证码成功，请注意查收");
						showVisiblityofCheck();
					}
					
					@Override
					public void Fail() {
						T.showShort(ResetPsdActivity.this, "发送验证码失败，请检查你的手机号码是否正确");
					}
				});
			}
		});
		
		btncheckCheckNum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checknum=etCheckNum.getText().toString();
				//检查验证码按下
				HUser.VerifySMS(ResetPsdActivity.this, phone, checknum, new VerifySMSCallback() {
					
					@Override
					public void Success() {
						T.showShort(ResetPsdActivity.this, "验证验证码成功");
						showVisiblityofNewPass();
					}
					
					@Override
					public void Fail() {
						T.showShort(ResetPsdActivity.this, "验证验证码失败");
					}
				});
				
			}
		});
		
		btnComplete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//更改密码完成按钮
				password=etNewPass.getText().toString();
				HUser.resetPass(ResetPsdActivity.this, password, phone, new resetPassCallback() {
					
					@Override
					public void Success() {
						T.showShort(ResetPsdActivity.this, "更改密码成功");
						Intent intent=new Intent(ResetPsdActivity.this,LoginActivity.class);
						startActivity(intent);
						finish();
					}
					
					@Override
					public void Fail() {
						
						T.showShort(ResetPsdActivity.this, "更改密码成功");
					}
				});
			}
		});
		
		
	}


	/**
	 * 使得获取验证码可见
	 */
	private void showVisiblityofCheck(){
		etCheckNum.setVisibility(View.VISIBLE);
		btncheckCheckNum.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 使得设置新密码可见
	 */
	private void showVisiblityofNewPass(){
		btnComplete.setVisibility(View.VISIBLE);
		etNewPass.setVisibility(View.VISIBLE);
	}
	
	/**
	 * iamgeview的回调
	 */
	public void back(View v){
		finish();
	}
}

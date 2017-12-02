package com.tule.aty;

import com.tule.R;
import com.tule.net.HUser;
import com.tule.net.HUser.LoginCallback;
import com.tule.utils.CaiUtil;
import com.tule.utils.T;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登录activity
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class LoginActivity extends Activity implements OnClickListener {

	
	private Button btnLogin;
	private Button btnReg;
	private Button btnResetPsd;
	private EditText etUsername;
	private EditText etPassword;

	private String username;
	private String password;
	
	private Handler myhandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		 
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();
		
	}
	private void initView() {
		Log.d("test login", "0");
		// TODO Auto-generated method stub
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnReg = (Button) findViewById(R.id.btn_register);
		btnResetPsd = (Button) findViewById(R.id.btn_reset_psd);

		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);

		btnLogin.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnResetPsd.setOnClickListener(this);
		
		 getUserInfo();
	}
	
	//获取用户信息
	private void getUserInfo() {
		SharedPreferences sp = getSharedPreferences("UserInfo", 0);
		etUsername.setText(sp.getString("username", null));
		etPassword.setText(sp.getString("password", null));
	}
	
	//保存用户信息
	private void saveUserInfo(String username, String password) {
		SharedPreferences sp = getSharedPreferences("UserInfo", 0);
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 登录按钮
		case R.id.btn_login:
			T.showShort(this,"登录时间过长请耐心等候");
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			
			if(!CaiUtil.isNetworkConnected(this)){
				toast("你好像網没有连");
			}
			if (username.equals("") || password.equals("")) {
				toast("你有一行什么都没填额~");
				break;
			} else {
				Log.d("test login", "4");
				HUser.Login(this, username, password, new LoginCallback() {
					
					@Override
					public void Success() {
						saveUserInfo(username, password);
						toast("登录成功");
						Intent toHome = new Intent(LoginActivity.this,
								MapActivity.class);
						startActivity(toHome);
						finish();
					}
					
					@Override
					public void Fail(int faliurecode, String message) {
						toast("不好意西=￣ω￣=你输入的信息you问题~");
					}
				});

			}
			break;
			
		case R.id.btn_reset_psd:
			Intent toResetPsdActivity = new Intent(LoginActivity.this, ResetPsdActivity.class);
			startActivity(toResetPsdActivity);
			break;

		case R.id.btn_register:
			Intent toReg = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(toReg);
			break;

		default:
			break;

		}
	}

	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

}
	

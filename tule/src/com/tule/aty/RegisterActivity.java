package com.tule.aty;


import com.tule.R;
import com.tule.net.HUser;
import com.tule.net.HUser.RegisterCallback;
import com.tule.utils.CaiUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册界面
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class RegisterActivity extends Activity implements OnClickListener {

	private Button btnReg;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etComfirmPsd;
	private EditText etPhone;

	private String username = null;
	private String password = null;
	private String comfirmPsd = null;
	private String phone = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initView();
	}

	private void initView() {
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
		etPhone = (EditText) findViewById(R.id.et_phone);

		btnReg = (Button) findViewById(R.id.btn_reg_now);
		btnReg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		username = etUsername.getText().toString();
		password = etPassword.getText().toString();
		comfirmPsd = etComfirmPsd.getText().toString();
		phone = etPhone.getText().toString();
		if(!CaiUtil.isNetworkConnected(this)) {
			toast("敢问網在何方( ⊙ o ⊙ ) ");
		} else if (username.equals("") || password.equals("")
				|| comfirmPsd.equals("") || phone.equals("")) {
			toast("沒有填完整=￣ω￣=快填完整吧");
		} else if (!comfirmPsd.equals(password)) {
			toast("手抖了Σ( ° △ °|||)︴两次密码输入不一致");
		} else if(!CaiUtil.isPhoneNumberValid(phone)) {
			toast("你的手机号码ヽ(*。>Д<)o゜地球人无法识别！！");
		}else {
			HUser.Register(this, username, password, phone, new RegisterCallback() {
				
				@Override
				public void Success() {
					toast("注册成功啦~~^_^");
					Intent backLogin = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(backLogin);
					RegisterActivity.this.finish();
				}
				
				@Override
				public void Fail(int faliurecode, String message) {
					toast("注册失败，估计是这么装逼的名字被用过了~~~~(>_<)~~~~");
				}
			});
			
		}
	}

	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	};
}

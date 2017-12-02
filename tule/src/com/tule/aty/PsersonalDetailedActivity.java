package com.tule.aty;

import com.tule.Config;
import com.tule.R;
import com.tule.net.HUser;
import com.tule.net.HUser.updateCallback;
import com.tule.utils.T;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 个人详细资料
 * 
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class PsersonalDetailedActivity extends Activity {

	private TextView tvSex;
	private TextView tvSchool;
	private TextView tvBirthday;
	private TextView tvUsername;

	private String Sex;
	private String School;
	private String Birthday;

	private final String[] sexType = new String[] { "男", "女", "未知" };
	public static int selectedSex = 0;

	private ImageView ivSex;
	private ImageView ivSchool;
	private ImageView ivBirthday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psersonal_detailed);

		initView();
		initUserInfo();
	}

	private void initView() {
		tvSex = (TextView) findViewById(R.id.tv_mineinfo_sex);
		tvSchool = (TextView) findViewById(R.id.tv_mineinfo_school);
		tvBirthday = (TextView) findViewById(R.id.tv_mineinfo_birthday);
		tvUsername = (TextView) findViewById(R.id.tv_personalCenter_name);

		ivSex = (ImageView) findViewById(R.id.iv_changesex);
		ivSchool = (ImageView) findViewById(R.id.iv_changeschool);
		ivBirthday = (ImageView) findViewById(R.id.iv_changebirthday);

		ivSex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(PsersonalDetailedActivity.this);
				builder.setTitle("更改性别")
						.setSingleChoiceItems(sexType, selectedSex, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedSex = which;
					}
				}).setPositiveButton("确认选择", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HUser.UpdateCurrentSex(PsersonalDetailedActivity.this, sexType[selectedSex],
								new updateCallback() {

							@Override
							public void Success() {
								T.showShort(PsersonalDetailedActivity.this, "更改成功！");
								tvSex.setText(sexType[selectedSex]);
							}

							@Override
							public void Fail() {
								T.showShort(PsersonalDetailedActivity.this, "更改失败！");
							}
						});
					}
				}).setNegativeButton("取消", null).show();

			}
		});
		ivSchool.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(PsersonalDetailedActivity.this);
				final EditText etSchool = new EditText(PsersonalDetailedActivity.this);
				builder.setTitle("修改学校").setView(etSchool)
						.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String newSchool = etSchool.getText().toString();
						T.showShort(getApplicationContext(), "新学校：" + newSchool);
						HUser.UpdateCurrentSchool(getApplicationContext(), newSchool, new updateCallback() {

							@Override
							public void Success() {
								T.showShort(PsersonalDetailedActivity.this, "更改成功！");
								tvSchool.setText(newSchool);
							}

							@Override
							public void Fail() {
								T.showShort(PsersonalDetailedActivity.this, "更改失败！");
							}
						});
					}
				}).setNegativeButton("取消", null).show();

			}
		});
		ivBirthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(PsersonalDetailedActivity.this);
				final EditText etSchool = new EditText(PsersonalDetailedActivity.this);
				builder.setTitle("修改生日").setView(etSchool)
						.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String newBirthday = etSchool.getText().toString();
						HUser.UpdateCurrentBirthday(getApplicationContext(), newBirthday, new updateCallback() {

							@Override
							public void Success() {
								T.showShort(PsersonalDetailedActivity.this, "更改成功！");
								tvBirthday.setText(newBirthday);
							}

							@Override
							public void Fail() {
								T.showShort(PsersonalDetailedActivity.this, "更改失败！");
							}
						});
					}
				}).setNegativeButton("取消", null).show();
			}
		});
	}

	/**
	 * 初始化用户信息
	 */
	private void initUserInfo() {
		selectedSex = 0;
		Sex = Config.getCurrentUser(this).getSex();
		School = Config.getCurrentUser(this).getSchool();
		Birthday = Config.getCurrentUser(this).getBirthdata();

		tvUsername.setText(Config.getCurrentUser(this).getUsername());

		if (Sex != null) {
			tvSex.setText(Sex);
		} else {
			tvSex.setText("未知");
		}
		if (School != null) {
			tvSchool.setText(School);
		} else {
			tvSchool.setText("未知");
		}
		if (Birthday != null) {
			tvBirthday.setText(Birthday);
		} else {
			tvBirthday.setText("未知");
		}
	}

	/**
	 * 返回按钮的回调
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}
}

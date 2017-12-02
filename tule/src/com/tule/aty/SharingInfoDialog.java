package com.tule.aty;

import com.tule.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SharingInfoDialog extends Dialog{

	private TextView tvUsername;
	private TextView tvContent;
	private ImageView ivBack;
	
	private String Username;
	private String Content;
	
	public SharingInfoDialog(Context context,
			String username,
			String content
			) {
		
		super(context);
		Username=username;
		Content=content;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d("testDialog 1", "test1");
		super.onCreate(savedInstanceState);
		Log.d("testDialog 1", "test2");
		setContentView(R.layout.dialog_show_sharing);
		Log.d("testDialog 1", "test3");
		tvContent=(TextView) findViewById(R.id.tv_dialog_content);
		Log.d("testDialog 1", "test31");
		tvUsername=(TextView) findViewById(R.id.tv_dialog_username);
		Log.d("testDialog 1", "test4");
		tvContent.setText(Content);
		tvUsername.setText(Username+" 分享道：");
		Log.d("testDialog 1", "test5");
		ivBack=(ImageView) findViewById(R.id.iv_back);
		Log.d("testDialog 1", "test51");
		ivBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharingInfoDialog.this.dismiss();
			}

	

		});
	}

}

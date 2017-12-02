package com.tule.aty;

import java.util.ArrayList;
import java.util.List;

import com.tule.R;
import com.tule.aty.adapter.MyTagListAdapter;
import com.tule.net.HSharing;
import com.tule.net.HSharing.getSharingCallback;
import com.tule.net.bean.Sharing;
import com.tule.utils.T;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 我的标签界面
 * 
 * @author MaoYiWei
 * @date 2015年8月2日
 */
public class MyTagsActivity extends Activity {

	private ListView lvMyTags;

	private List<Sharing> Data;

	private MyTagListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tags);

		initView();
		getData();
	}

	private void getData() {
		HSharing.getSharingByCurrentUser(this, new getSharingCallback() {

			@Override
			public void Success(List<Sharing> result) {
				if (result.size() > 0) {
					Data = (ArrayList<Sharing>) result;
					adapter.refresh((ArrayList<Sharing>) Data);
					adapter.notifyDataSetChanged();
					T.showShort(getApplicationContext(), "数据加载完成！");
				} else {
					T.showShort(getApplicationContext(), "没有更多数据了");
				}
			}

			@Override
			public void Fail(int faliurecode, String result) {

				T.showShort(getApplicationContext(), "获取分享列表失败");
			}
		});
	}

	private void initView() {
		lvMyTags = (ListView) findViewById(R.id.lv_mytags);

		Data = new ArrayList<Sharing>();
		adapter = new MyTagListAdapter(this, Data);

		lvMyTags.setAdapter(adapter);

		lvMyTags.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SharingInfoDialog dialog = new SharingInfoDialog(MyTagsActivity.this,
						Data.get(position).getUsername(), Data.get(position).getContent());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.show();
			}

		});
	}

	/**
	 * 返回的回调
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}
}

package com.tule.aty.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tule.Config;
import com.tule.R;
import com.tule.net.HSharing;
import com.tule.net.HSharing.DeleteSharingCallback;
import com.tule.net.bean.Sharing;
import com.tule.utils.T;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的标签列表适配器，直接setAdapter即可
 * 
 * @author MaoYiWei
 * @date 2015年8月2日
 */
public class MyTagListAdapter extends BaseAdapter {

	private List<Sharing> list = new ArrayList<Sharing>();
	private Context context;
	private LayoutInflater inflater = null;

	public MyTagListAdapter(Context waidecontext, List<Sharing> list) {

		context = waidecontext;
		this.inflater = LayoutInflater.from(context);

		list=(ArrayList<Sharing> )list;
	}

	@Override
	public int getCount() {
		int size = getList().size();
		return size;

	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 刷新列表中的数据
	public void refresh(List<Sharing> newlist) {
		Log.i("MyTagListAdapter", "Adapter刷新数据");
		list = newlist;
		notifyDataSetChanged();
	}

	public class MyTagViewHolder {
		public TextView tvContent;
		public TextView tvDate;

		public ImageView btnDelete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("TEST ADAPTER", "TEST 95881");
		MyTagViewHolder holder = null;
		final int place = position;

		if (convertView == null) {
			holder = new MyTagViewHolder();
			convertView = inflater.inflate(R.layout.list_mytag, null);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.btnDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
			convertView.setTag(holder);
		} else {
			holder = (MyTagViewHolder) convertView.getTag();
		}
		// 日期
		holder.tvDate.setText(getList().get(position).getCreatedAt());
		String content = getList().get(position).getContent();
		// 内容
		if(content.length()>Config.LIST_CONTENT_STRING_LENGTH){
			content = content.substring(0, Config.LIST_CONTENT_STRING_LENGTH);
			content+="...";
		}
		holder.tvContent.setText(content);

		// 删除按钮
		holder.btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("警告").setMessage("确认删除这条消息吗？")
						.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						HSharing.DeleteSharing(context, getList().get(place), new DeleteSharingCallback() {

							@Override
							public void Success() {
								T.showShort(context, "删除成功");
								list.remove(place);
								notifyDataSetChanged();
							}

							@Override
							public void Fail(int faliurecode, String message) {
								T.showShort(context, "删除失败");
							}
						});
					}
				}).setNegativeButton("取消", null).show();
			}
		});

		return convertView;
	}

	public List<Sharing> getList() {
		return list;
	}

	public void setList(List<Sharing> list) {
		this.list = list;
	}

}

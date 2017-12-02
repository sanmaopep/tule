package com.tule.aty.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tule.Config;
import com.tule.R;
import com.tule.net.bean.Sharing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FindSharingAdapter extends BaseAdapter {

	private List<Sharing> list;
	private Context context;
	private LayoutInflater inflater = null;

	public FindSharingAdapter(Context waidecontext, List<Sharing> list) {

		context = waidecontext;
		this.inflater = LayoutInflater.from(context);
		this.list = (ArrayList<Sharing>) list;

	}

	// 刷新列表中的数据
	public void refresh(List<Sharing> newlist) {
		Log.i("FindSharingListAdapter", "Adapter刷新数据");
		list = newlist;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		public TextView tvUsername;
		public TextView tvContent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_find_sharing, null);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_sharing_username);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String content = list.get(position).getContent();
		// 内容
		if(content.length()>Config.LIST_CONTENT_STRING_LENGTH){
			content = content.substring(0, Config.LIST_CONTENT_STRING_LENGTH);
			content+="...";
		}
		holder.tvContent.setText(content);
		
		holder.tvUsername.setText(list.get(position).getUsername());
		
		return convertView;
	}

}

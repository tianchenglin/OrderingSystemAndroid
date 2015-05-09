package com.utopia.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView; 
import com.utopia.Dao.sql_desk; 
import java.util.ArrayList;

public class DesklistAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> mList;

	public DesklistAdapter(Context paramContext) {
		this.context = paramContext;
		addItemByList();
	}

	public void addItemByList() {
		this.mList = new sql_desk()
				.recordlist4("select keyname from keycode");
	}

	public void execsql(String paramString) {
		this.mList = new sql_desk()
				.recordlist4("select a.keyname from keycode as a JOIN Area as b ON trim(a.AreaId) = trim(b.AreaId) where (trim(b.AreaName)='"
						+ paramString + "')");
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.mList.size();
	}

	public Object getItem(int paramInt) {
		return this.mList.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		TextView localTextView = new TextView(this.context);
		localTextView = (TextView) paramView;
		localTextView.setGravity(17);
		localTextView.setText(this.mList.get(paramInt).toString().trim());
		return localTextView;
	}
}
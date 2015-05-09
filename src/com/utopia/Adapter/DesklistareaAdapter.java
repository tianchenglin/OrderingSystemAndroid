package com.utopia.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView; 
import com.utopia.Dao.sql_Area;
import com.utopia.activity.R;

public class DesklistareaAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> mList;

	public DesklistareaAdapter(Context paramContext) {
		this.context = paramContext;
		addItemByList();
	}

	public void addItemByList() {
		this.mList = new sql_Area()
				.recordlist2("select AreaName from Area order by AreaId");
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
		TextView localTextView;
		if (paramView == null) {
			localTextView = new TextView(this.context);
			localTextView.setBackgroundResource(R.drawable.mtable_tree_btn1);
			localTextView.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
			localTextView.setGravity(17);
			localTextView.setTextColor(this.context.getResources().getColor(
					R.color.white));
		} else {
			localTextView = (TextView) paramView;
		}
		localTextView.setText(this.mList.get(paramInt).toString().trim());
		return localTextView;
	}
}

/*
 * Location: D:\android-reverse-trinea\classes_dex2jar.jar Qualified Name:
 * com.etf.Adapter.desklistareaAdapter JD-Core Version: 0.6.2
 */
package com.utopia.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView; 
import com.utopia.Dao.sql_Area;
import com.utopia.activity.R;

public class DeskareaAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> mList;

	public DeskareaAdapter(Context paramContext) {
		this.context = paramContext;
		addItemByList();
	}

	public void addItemByList() {
		this.mList = new sql_Area()
				.recordlist2("select AreaName from Area order by AreaId");
	}

	@Override
	public int getCount() {
		return this.mList.size();
	}

	@Override
	public Object getItem(int paramInt) {
		return this.mList.get(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		if (paramView == null) {
			View localView = LayoutInflater.from(this.context).inflate(
					R.layout.desk_list, null);
			localView.setTag(localView.findViewById(R.id.content_group));
			paramView = localView;
		}
		((TextView) paramView.getTag()).setText(this.mList.get(paramInt)
				.toString().trim());
		return paramView;
	}
}
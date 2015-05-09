package com.utopia.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView; 
import com.utopia.Dao.sql_desk;
import com.utopia.activity.R;

public class DeskgridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> mList;
	private String state ;
	public DeskgridAdapter(Context paramContext,String state) {
		this.state = state;
		this.context = paramContext;
		addItemByList();
	}

	public void addItemByList() {
		this.mList = new sql_desk()
				.recordlist4("select desk_name from desk where state !='"+state+"'");
	}

	public void execsql(String paramString) {
		this.mList = new sql_desk()
				.recordlist4("select a.desk_name from desk as a JOIN Area as b ON trim(a.type_id) = trim(b.AreaId) where (trim(b.AreaName)='"
						+ paramString + "') and a.state !='"+state+"'");
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
		final TextView localTextView;
		if (paramView == null) {
			localTextView = new TextView(this.context);
			localTextView.setBackgroundResource(R.drawable.mtable0);
			localTextView.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
			localTextView.setGravity(17);
			localTextView.setTextColor(this.context.getResources().getColor(
					R.color.black));
		} else
			localTextView = (TextView) paramView;
		localTextView.setGravity(17);
		localTextView.setText(this.mList.get(paramInt).toString().trim());
		return localTextView;
	}
}
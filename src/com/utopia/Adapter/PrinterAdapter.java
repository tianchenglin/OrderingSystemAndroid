package com.utopia.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PrinterAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	ArrayList<String> datalist;
	public boolean mBusy = false;

	public PrinterAdapter(Context paramContext , ArrayList<String> datalist) {
		this.context = paramContext;
		this.datalist = datalist;
	}

	public void execsql(String paramString) {
		notifyDataSetChanged();
	}

	public int getCount() {
		if (this.datalist != null)
			return this.datalist.size();
		return 0;
	}

	public Object getItem(int paramInt) {
		return this.datalist.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		TextView localTextView  = new TextView(this.context);
		localTextView.setTextColor(Color.WHITE);
		localTextView.setPadding(0, 20, 0, 20);
		localTextView.setTextSize(22);
		localTextView.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
		
		localTextView.setText(this.datalist.get(paramInt).trim());
		if(paramInt==2){
			localTextView.setTextColor(Color.BLACK);
		}
		return localTextView;
	}

	public void onClick(View paramView) {
	}

}

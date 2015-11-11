package com.utopia.Adapter;

import com.utopia.Dialog.chooseDrawerDialog;
import com.utopia.utils.Constant;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class cashierAdapter extends BaseAdapter{

	private Context context;
	private String[] cashier;
	public cashierAdapter(Context context){
		this.context=context;
		cashier=new String[20];
		SQLiteDatabase db;
		db = Constant.openDatabase();
		Cursor c = db.rawQuery("select * from cashier ", null);
		int i=0;
		while (c.moveToNext()) {
			cashier[i]=c.getString(c.getColumnIndex("id"));
			Log.e("sql_cashier", c.getString(c.getColumnIndex("id")));
			i++;
		}
		c.close();
		
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		// TODO Auto-generated method stub
		AppItem localAppItem=new AppItem();
		if(paramView==null){
			paramView=LayoutInflater.from(this.context).inflate(
					android.R.layout.simple_list_item_1,null);
			localAppItem.cashierId=(TextView) paramView.findViewById(R.id.text1);
			paramView.setTag(localAppItem);
		}else{
			localAppItem=(AppItem) paramView.getTag();
			localAppItem.cashierId.setText(cashier[paramInt]);
		}
		return paramView;
	}
	public class AppItem{
		TextView cashierId;
	}

	

}

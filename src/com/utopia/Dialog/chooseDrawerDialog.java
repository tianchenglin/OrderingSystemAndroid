package com.utopia.Dialog;

import com.utopia.activity.R;
import com.utopia.utils.Constant;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.utopia.Adapter.cashierAdapter;
import com.utopia.Base.BaseDialog;
import com.utopia.Dao.sql_Cashier;

public class chooseDrawerDialog extends BaseDialog {
   
	private Context context;
	private ListView drawer_list;
    private cashierAdapter adapter;
	public chooseDrawerDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
		//initEvent();
		initDrawerList();
	}
	private void init(){
		setContentView(R.layout.choose_drawer_dialog);
		setCanceledOnTouchOutside(false);	//禁用框外点击
		drawer_list=(ListView) findViewById(R.id.drawer_list);
	}
	private void initDrawerList(){
		
		//adapter=new cashierAdapter(context);
		//drawer_list.setAdapter(adapter);
		
		
	}
//	private void initEvent(){
//		drawer_list.setOnClickListener(this);
//	}
	

}

package com.utopia.Dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.utopia.Model.d_Customer;
import com.utopia.utils.Constant;

public class sql_Customer {
	Context context;
	SQLiteDatabase db;

	public sql_Customer() {
		this.db = Constant.openDatabase();
	}

	public sql_Customer(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void save(d_Customer dCus) {
		 detail();
		 Log.i("sdf", "sf");
		Cursor c = db.rawQuery("select *from Customer", null);
		while (c.moveToNext()) {
			if ((Integer.parseInt(c.getString(c.getColumnIndex("ItemNo"))) == dCus
					.getItemNo())
					&& Integer.parseInt(c.getString(c
							.getColumnIndex("customNo"))) == dCus.getCustomNo()) {
				return;
			}
		}
		
		db.execSQL("insert into Customer(customNo,ItemNo) values(?,?)",
				new Object[] { dCus.getCustomNo(), dCus.getItemNo() });

		 detail();
	}

	public void delete(d_Customer dCus) {
		db.execSQL("delete from Customer where customNo=? and ItemNo=?",
				new Object[] { dCus.getCustomNo(), dCus.getItemNo() });
	}
	public void delete(String str) {
		db.execSQL(str);
	}

	public void detail() {
		Cursor c = db.rawQuery("select *from Customer", null);
		while (c.moveToNext()) {
			Log.e("customer数据 ", c.getString(c.getColumnIndex("ItemNo")) + " "
					+ c.getString(c.getColumnIndex("customNo")));
		}
	}
	
	public int getCustomerNum() {
		
		Cursor c ;
		int num = 0 ;
		c = db.rawQuery("select max(customNo) from Customer", null);
		if (c.moveToNext()) {
			num = c.getInt(0);
			Log.e("customer数据 ", num+"");
		}
		return num;
	}
	

	public Cursor getCustomer() {
		Cursor c = db.rawQuery("select *from Customer", null);
		return c;
	}

	public List<d_Customer> getCustomers() {
		List<d_Customer> d_Customers = new ArrayList<d_Customer>();

		Cursor c = db.rawQuery("select *from Customer", null);
		while (c.moveToNext()) {
			d_Customer dc = new d_Customer();
			// dc.setId(c.getInt(c.getColumnIndex("id")));
			dc.setItemNo(c.getInt(c.getColumnIndex("ItemNo")));
			// Log.e("ItemNo",dc.getItemNo()+"");
			dc.setCustomNo(c.getInt(c.getColumnIndex("customNo")));
			d_Customers.add(dc);
		}

		return d_Customers;
	}

	public List<Integer> getItemsId(String customNo) {
		List<Integer> items = new ArrayList<Integer>();

		Cursor c = db.rawQuery("select *from Customer where customNo="+customNo, null);
		while (c.moveToNext()) {
			items.add(c.getInt(c.getColumnIndex("ItemNo")));
		}

		return items;
	}
	
	public boolean vertiy(int customerId, int itemNo) {
		Cursor c = db.rawQuery("select *from Customer where customNo="+customerId+" and ItemNo="+itemNo,null);
		if (c.moveToNext()) {
			Log.e("customer数据1 ", c.getInt(1)+"");
			c.close();
			return true;
		}
		c.close();
		return false;
	}

	public void deleteCustomer(int cnt) {
		db.execSQL("delete from Customer where customNo=? ",
				new Object[] { cnt});
	}

}

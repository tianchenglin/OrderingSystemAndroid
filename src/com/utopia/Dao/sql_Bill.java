package com.utopia.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 

import com.utopia.Model.d_Bill;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;

public class sql_Bill {
	Context context;
	SQLiteDatabase db;

	public sql_Bill() {
		this.db = Constant.openDatabase();
	}

	sql_Bill(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void save(d_Bill localBill) {
		Object[] arrayOfObject = new Object[8];
		arrayOfObject[0] = localBill.getBillId();
		arrayOfObject[1] = localBill.getWaiter();
		arrayOfObject[2] = localBill.getSubtotal();
		arrayOfObject[3] = localBill.getTotal();
		arrayOfObject[4] = localBill.getTax();
		arrayOfObject[5] = localBill.getDistant();
		arrayOfObject[6] = DateUtils.getDateEN();
		arrayOfObject[7] = localBill.getTip();
		Cursor localCursor = this.db.rawQuery(
				"select * from Bill where BillId='" + localBill.getBillId()
						+ "'", null);
		if (localCursor.moveToNext()) {
			db.execSQL(
					"UPDATE Bill SET Waiter=?,Subtotal=?,Total=?,Tax=?,CreateTime=?,Distant=?,Tip=?",
					new Object[] { localBill.getWaiter(),
							localBill.getSubtotal(), localBill.getTotal(),
							localBill.getTax(), localBill.getCreateTime(),
							localBill.getDistant(), localBill.getTip() });
		} else {
			db.execSQL(
					"INSERT INTO Bill(BillId,Waiter,Subtotal,Total,Tax,Distant,CreateTime,Tip) values(?,?,?,?,?,?,?,?)",
					arrayOfObject);
		}

		// select();
	}
	
	public void delete(){
		db.execSQL("delete from Bill");
	}

	public void saveOnline(d_Bill localBill) {
		//db.rawQuery("delete from Bill", null);
		Object[] arrayOfObject = new Object[8];
		arrayOfObject[0] = localBill.getBillId();
		arrayOfObject[1] = localBill.getWaiter();
		arrayOfObject[2] = localBill.getSubtotal();
		arrayOfObject[3] = localBill.getTotal();
		arrayOfObject[4] = localBill.getTax();
		arrayOfObject[5] = localBill.getDistant();
		arrayOfObject[6] = localBill.getCreateTime();
		arrayOfObject[7] = localBill.getTip();
		db.execSQL(
				"INSERT INTO Bill(BillId,Waiter,Subtotal,Total,Tax,Distant,CreateTime,Tip) values(?,?,?,?,?,?,?,?)",
				arrayOfObject);
		//Log.e("sql_Bill", localBill.getBillId());
		// select();
	}

	public void select() {
		Cursor c = this.db.rawQuery("select *from Bill ", null);
		while (c.moveToNext()) {
			 }
		c.close();
	}

	public String sum_cash() {
		String sum = "";
		float s = (float) 0.00;
		Cursor c = this.db.rawQuery(
				"select sum(Total) from Bill where CreateTime>='"
						+ Constant.clockInTime + "'", null);
		if (c.moveToFirst()) {
			s = c.getFloat(0);
		}
		/*
		 * while(c.moveToNext()){ Log.e("sql",c.getFloat(0)+""); }
		 */
		sum = Constant.decimalFormat.format(s);
		c.close();
		return sum;
	}
	
	//
	public String sum_cashAll() {
		String sum = "";
		float s = (float) 0.00;
		Cursor c = this.db.rawQuery(
				"select sum(Total) from Bill where CreateTime>='"
						+ Constant.clockInTime + "'", null);
		if (c.moveToFirst()) {
			s = c.getFloat(0);
		}
		/*
		 * while(c.moveToNext()){ Log.e("sql",c.getFloat(0)+""); }
		 */
		sum = Constant.decimalFormat.format(s);
		c.close();
		return sum;
	}

	public String getTransactions() {
		int number = 0;
		Cursor c = db.rawQuery("select count(*) from Bill where CreateTime>='"
				+ Constant.clockInTime + "'", null);
		if (c.moveToFirst()) {
			number = c.getInt(0);
		}
		c.close();
		return number + "";
	}

	public String getTip() {
		float sum = 0;
		Cursor c = db.rawQuery(
				"select count(Tip) from Bill where CreateTime>='"
						+ Constant.clockInTime + "'", null);
		if (c.moveToFirst()) {
			sum = c.getFloat(0);
		}

		return Constant.decimalFormat.format(sum);
	}
}

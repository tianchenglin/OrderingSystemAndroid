package com.utopia.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 

import com.utopia.Model.d_Cashier;
import com.utopia.utils.Constant;

public class sql_Cashier {
	Context context;
	SQLiteDatabase db;

	public sql_Cashier() {
		this.db = Constant.openDatabase();
	}

	public sql_Cashier(Context paramContext) {
		this.context = paramContext;
		if (this.db == null)
			this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void close() {
		db.close();
	}

	public void save(d_Cashier localCashier) {
		localCashier.setChangeMoney(0);
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = localCashier.getInitMoney();
		arrayOfObject[1] = localCashier.getCreateTime();
		arrayOfObject[2] = localCashier.getUserCode();
		arrayOfObject[3] = localCashier.getCashierId();
		arrayOfObject[4] = localCashier.getStatus();

		Cursor cursor = db.rawQuery(
				"select * from cashier where status = 'init'", null);
		if (cursor.moveToNext()) {
			localSQLiteDatabase.execSQL("update cashier set initMoney = ?",
					new Object[] { arrayOfObject[0] });
		} else {
			localSQLiteDatabase
					.execSQL(
							"insert into cashier(initMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?)",
							arrayOfObject);
		}

		/*
		 * Cursor m_cursor = this.db.rawQuery("select * from cashier",null );
		 * String str = ""; while(m_cursor.moveToNext()){ str =
		 * m_cursor.getString(m_cursor.getColumnIndex("initMoney")) +
		 * m_cursor.getString(m_cursor.getColumnIndex("status")); }
		 * m_cursor.close();
		 */

	}

	public void saveChangeMoney(d_Cashier localCashier) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = localCashier.getChangeMoney();
		arrayOfObject[1] = localCashier.getCreateTime();
		arrayOfObject[2] = localCashier.getUserCode();
		arrayOfObject[3] = localCashier.getCashierId();
		arrayOfObject[4] = localCashier.getStatus();

		localSQLiteDatabase
				.execSQL(
						"insert into cashier(changeMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?)",
						arrayOfObject);

		/*
		 * Cursor m_cursor = this.db.rawQuery("select * from cashier",null );
		 * String str = ""; while(m_cursor.moveToNext()){ str =
		 * m_cursor.getString(m_cursor.getColumnIndex("initMoney")) +
		 * m_cursor.getString(m_cursor.getColumnIndex("changeMoney")) +
		 * m_cursor.getString(m_cursor.getColumnIndex("status")); Log.e("读出数据",
		 * str ); } m_cursor.close();
		 */
	}

	public String getDrops() {
		float drops = 0;
		Cursor cursor = db.rawQuery(
				"select * from cashier where status = 'drops'", null);
		while (cursor.moveToNext()) {
			drops += Float.valueOf(cursor.getString(cursor
					.getColumnIndex("changeMoney")));
		}

		return drops + "";
	}

	public String getPayout() {
		float payOut = 0;
		Cursor cursor = db.rawQuery(
				"select * from cashier where status = 'payout'", null);
		while (cursor.moveToNext()) {
			payOut += Float.valueOf(cursor.getString(cursor
					.getColumnIndex("changeMoney")));
		}

		return payOut + "";
	}

	public String getPutchase() {
		float purchase = 0;
		Cursor cursor = db.rawQuery(
				"select * from cashier where status = 'purchase'", null);
		while (cursor.moveToNext()) {
			purchase += Float.valueOf(cursor.getString(cursor
					.getColumnIndex("changeMoney")));
		}

		return purchase + "";
	}

	public String getInitMoney() {
		float initMoney = 0;
		Cursor cursor = db.rawQuery(
				"select * from cashier where status = 'init' and createTime>='"+Constant.clockInTime+"'", null);
		while (cursor.moveToNext()) {
			initMoney = Float.valueOf(cursor.getString(cursor
					.getColumnIndex("initMoney")));
		}

		return initMoney + "";
	}

	public void saveCurrentMoney(d_Cashier localCashier) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = localCashier.getCurrentMoney();
		arrayOfObject[1] = localCashier.getCreateTime();
		arrayOfObject[2] = localCashier.getUserCode();
		arrayOfObject[3] = localCashier.getCashierId();
		arrayOfObject[4] = localCashier.getStatus();

		localSQLiteDatabase
				.execSQL(
						"insert into cashier(currentMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?)",
						arrayOfObject);
	}

	public void clear_all() {
		db.execSQL("delete from cashier");
	}

	public void saveInit(d_Cashier localCashier) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[8];
		arrayOfObject[0] = localCashier.getId();
		arrayOfObject[1] = localCashier.getCurrentMoney();
		arrayOfObject[2] = localCashier.getInitMoney();
		arrayOfObject[3] = localCashier.getChangeMoney();
		arrayOfObject[4] = localCashier.getCreateTime();
		arrayOfObject[5] = localCashier.getUserCode();
		arrayOfObject[6] = localCashier.getCashierId();
		arrayOfObject[7] = localCashier.getStatus();

		localSQLiteDatabase
				.execSQL(
						"insert into cashier(id,currentMoney,initMoney,changeMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?,?,?,?)",
						arrayOfObject);

	}
	
	public void select() {
		Cursor c = this.db.rawQuery("select *from cashier ", null);
		while (c.moveToNext()) {
			//Log.e("sql_cashier", c.getString(c.getColumnIndex("id")));
		}
		c.close();
	}

}

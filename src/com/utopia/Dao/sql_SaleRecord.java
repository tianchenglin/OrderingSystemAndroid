package com.utopia.Dao;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 

import com.utopia.Model.d_SaleRecord;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;

public class sql_SaleRecord {
	SQLiteDatabase db;

	public sql_SaleRecord() {
		if (db == null)
			db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void clearBill(String paramString) {
		this.db.execSQL("delete from SaleRecord where desk_name=?",
				new Object[] { paramString });
	}

	public void clear_all() {
		this.db.execSQL("delete from SaleRecord");
	}

	public void delete(String paramString) {
		this.db.execSQL("delete from SaleRecord where ItemNo=?",
				new Object[] { paramString });
	}

	public void deleteNotSent(String desk_name, String status) {
		this.db.execSQL(
				"delete from SaleRecord where desk_name=? and status=?",
				new Object[] { desk_name, status });

	}

	public void deleteLocal(String desk_name) {
		this.db.execSQL("delete from SaleRecord where desk_name=?",
				new Object[] { desk_name });
	}

	public void delete(String desk_name, String pdtName) {
		Object[] ob = new Object[3];
		ob[0] = desk_name;
		ob[1] = pdtName;
		ob[2] = "Not Sent";
		db.execSQL(
				"delete from SaleRecord where desk_name = ? and PdtName=? and status = ?",
				ob);
	}

	/*
	 * Order Form 界面 总价
	 */
	public float getOneOrderTotalMoney(String paramString) {
		String str = "select ifnull(sum(number*Price),0.0) from SaleRecord where desk_name='"
				+ paramString + "'and status!='Finish'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		float f = localCursor.getFloat(0);
		localCursor.close();
		return f;
	}

	/*
	 * Order Form 界面 总数
	 */
	public float getOneOrderTotalNum(String paramString) {
		String str = "select ifnull(sum(number),0.0) from SaleRecord where desk_name='"
				+ paramString + "'and status!='Finish'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		float f = localCursor.getFloat(0);
		localCursor.close();
		return f;
	}

	// 得到订单总额 消费合计
	public float sumTotal(String paramString) {
		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from SaleRecord where desk_name='" + paramString
				+ "' ";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("Price")))
					.floatValue()
					* (int) Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("number"))).floatValue()
					* Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("Discount"))).floatValue();
			mSumTotal += mSum;
		}
		return mSumTotal;
	}

	// 得到订单总额 消费合计
	public float sumTotalDone(String paramString) {
		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from SaleRecord where desk_name='" + paramString
				+ "'and status='Doned'";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("Price")))
					.floatValue()
					* (int) Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("number"))).floatValue()
					* Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("Discount"))).floatValue();
			mSumTotal += mSum;
		}
		return mSumTotal;
	}

	public ArrayList<d_SaleRecord> recordlist(String desk_name) {
		ArrayList<d_SaleRecord> localArrayList = new ArrayList<d_SaleRecord>();
		Cursor localCursor = this.db
				.rawQuery(
						"select * from SaleRecord where desk_name==? and status=='Not Sent'",
						new String[] { desk_name });
		while (localCursor.moveToNext()) {
			d_SaleRecord locald_SaleRecord = new d_SaleRecord();
			locald_SaleRecord.setPayId(localCursor.getString(localCursor
					.getColumnIndex("PayId")));
			locald_SaleRecord.setBILLID(localCursor.getString(localCursor
					.getColumnIndex("BILLID")));

			locald_SaleRecord.setPdtCODE(localCursor.getString(localCursor
					.getColumnIndex("PdtCODE")));
			locald_SaleRecord.setPdtName(localCursor.getString(localCursor
					.getColumnIndex("PdtName")));
			locald_SaleRecord.setNumber((int) (Float.valueOf(localCursor
					.getString(localCursor.getColumnIndex("number")))
					.floatValue()));
			locald_SaleRecord.setPrice(Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("Price")))
					.floatValue());

			locald_SaleRecord.setRebate(localCursor.getInt(localCursor
					.getColumnIndex("rebate")));
			locald_SaleRecord.setCreateTime(localCursor.getString(localCursor
					.getColumnIndex("CreateTime")));
			locald_SaleRecord.setStatus(localCursor.getString(localCursor
					.getColumnIndex("status")));
			locald_SaleRecord.setDesk_name(localCursor.getString(localCursor
					.getColumnIndex("desk_name")));

			locald_SaleRecord.setOtherSpecNo1(localCursor.getString(localCursor
					.getColumnIndex("OtherSpecNo1")));
			locald_SaleRecord.setOtherSpecNo2(localCursor.getString(localCursor
					.getColumnIndex("OtherSpecNo2")));
			locald_SaleRecord.setOtherSpec(localCursor.getString(localCursor
					.getColumnIndex("OtherSpec")));

			locald_SaleRecord.setWaiter(localCursor.getString(localCursor
					.getColumnIndex("Waiter")));
			locald_SaleRecord.setTax(localCursor.getFloat(localCursor
					.getColumnIndex("tax")));
			locald_SaleRecord.setItemNo(localCursor.getInt(localCursor
					.getColumnIndex("ItemNo")));
			localArrayList.add(locald_SaleRecord);
		}
		localCursor.close();
		return localArrayList;
	}

	public Cursor recordlist2(String paramString) {
		return db.rawQuery("select * from SaleRecord order by ItemNo", null);
	}

	public Cursor recordlist3(String paramString) {
		return this.db.rawQuery(paramString, null);
	}

	public ArrayList<String> recordlist4(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		Cursor localCursor = this.db.rawQuery(paramString, null);
		while (localCursor.moveToNext()) {
			localArrayList.add(localCursor.getString(0));
		}
		localCursor.close();
		return localArrayList;
	}

	public void save(d_SaleRecord paramd_SaleRecord) {
		Object[] arrayOfObject = new Object[15];
		arrayOfObject[0] = paramd_SaleRecord.getPdtCODE();
		arrayOfObject[1] = paramd_SaleRecord.getPdtName();
		arrayOfObject[2] = paramd_SaleRecord.getPrice();
		arrayOfObject[3] = paramd_SaleRecord.getBILLID();
		arrayOfObject[4] = paramd_SaleRecord.getNumber();
		arrayOfObject[5] = paramd_SaleRecord.getOtherSpec();
		arrayOfObject[6] = paramd_SaleRecord.getStatus();
		arrayOfObject[7] = paramd_SaleRecord.getOtherSpecNo1();
		arrayOfObject[8] = paramd_SaleRecord.getOtherSpecNo2();
		arrayOfObject[9] = paramd_SaleRecord.getDesk_name();
		arrayOfObject[10] = paramd_SaleRecord.getCreateTime();
		arrayOfObject[11] = paramd_SaleRecord.getWaiter();
		arrayOfObject[12] = paramd_SaleRecord.getTax();
		arrayOfObject[13] = 1;
		arrayOfObject[14] = paramd_SaleRecord.getCloseTime();
		Cursor localCursor = this.db.rawQuery(
				"select * from SaleRecord where PdtCODE='"
						+ paramd_SaleRecord.getPdtCODE() + "' and desk_name='"
						+ paramd_SaleRecord.getDesk_name()
						+ "' and status=='Not Sent'", null);
		if (localCursor.moveToNext()) {
			db.execSQL(
					"UPDATE SaleRecord SET number=number+1 ,OtherSpec=?, OtherSpecNo1 = ? , OtherSpecNo2 = ? , Waiter = ? WHERE desk_name=? and PdtCODE=?",
					new Object[] { arrayOfObject[5],
							paramd_SaleRecord.getOtherSpecNo1(),
							paramd_SaleRecord.getOtherSpecNo2(),
							paramd_SaleRecord.getWaiter(),
							paramd_SaleRecord.getDesk_name(),
							paramd_SaleRecord.getPdtCODE() });
		} else {
			db.execSQL(
					"INSERT INTO SaleRecord(PdtCODE,PdtName,Price,BILLID,number,OtherSpec,status,OtherSpecNo1,OtherSpecNo2,desk_name,CreateTime,Waiter,tax , Discount,closeTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					arrayOfObject);
		}
	}

	public void saveInit(d_SaleRecord paramd_SaleRecord) {
		Object[] arrayOfObject = new Object[16];
		arrayOfObject[0] = paramd_SaleRecord.getPdtCODE();
		arrayOfObject[1] = paramd_SaleRecord.getPdtName();
		arrayOfObject[2] = paramd_SaleRecord.getPrice();
		arrayOfObject[3] = paramd_SaleRecord.getBILLID();
		arrayOfObject[4] = paramd_SaleRecord.getNumber();
		arrayOfObject[5] = paramd_SaleRecord.getOtherSpec();
		arrayOfObject[6] = paramd_SaleRecord.getStatus();
		arrayOfObject[7] = paramd_SaleRecord.getOtherSpecNo1();
		arrayOfObject[8] = paramd_SaleRecord.getOtherSpecNo2();
		arrayOfObject[9] = paramd_SaleRecord.getDesk_name();
		arrayOfObject[10] = paramd_SaleRecord.getCreateTime();
		arrayOfObject[11] = paramd_SaleRecord.getWaiter();
		arrayOfObject[12] = paramd_SaleRecord.getTax();
		arrayOfObject[13] = 1;
		arrayOfObject[14] = paramd_SaleRecord.getCloseTime();
		arrayOfObject[15] = paramd_SaleRecord.getItemNo();
		Cursor localCursor = this.db.rawQuery(
				"select * from SaleRecord where PdtCODE='"
						+ paramd_SaleRecord.getPdtCODE() + "' and desk_name='"
						+ paramd_SaleRecord.getDesk_name()
						+ "' and status=='Not Sent'", null);
		if (localCursor.moveToNext()) {
			db.execSQL(
					"UPDATE SaleRecord SET number=number+1 ,OtherSpec=?, OtherSpecNo1 = ? , OtherSpecNo2 = ? , Waiter = ? WHERE desk_name=? and PdtCODE=?",
					new Object[] { arrayOfObject[5],
							paramd_SaleRecord.getOtherSpecNo1(),
							paramd_SaleRecord.getOtherSpecNo2(),
							paramd_SaleRecord.getWaiter(),
							paramd_SaleRecord.getDesk_name(),
							paramd_SaleRecord.getPdtCODE() });
		} else {
			db.execSQL(
					"INSERT INTO SaleRecord(PdtCODE,PdtName,Price,BILLID,number,OtherSpec,status,OtherSpecNo1,OtherSpecNo2,desk_name,CreateTime,Waiter,tax , Discount,closeTime,ItemNo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					arrayOfObject);
		}
	}

	public String getTransactions() {
		int number = 0;
		Cursor cursor = db.rawQuery(
				"select count(closeTime) from SaleRecord where Waiter = '"
						+ Constant.currentStaff.getS_account()
						+ "'group by closeTime", null);
		number = cursor.getCount();
		cursor.close();
		return number + "";
	}

	public String getTip() {
		float tip = 0;
		Cursor cursor = db.rawQuery(
				"select sum(tip) as  sum from SaleRecord where Waiter = '"
						+ Constant.currentStaff.getS_account()
						+ "'group by desk_name", null);
		while (cursor.moveToNext()) {
			tip += cursor.getFloat(cursor.getColumnIndex("sum"));
		}

		cursor.close();
		return tip + "";
	}

	public String getTipAll() {
		float tip = 0;
		Cursor cursor = db.rawQuery(
				"select sum(tip) as  sum from SaleRecord group by desk_name",
				null);
		while (cursor.moveToNext()) {
			tip += cursor.getFloat(cursor.getColumnIndex("sum"));
		}

		cursor.close();
		return tip + "";
	}

	public String getTransactionsAll() {
		int number = 0;
		Cursor cursor = db.rawQuery(
				"select count(closeTime) from SaleRecord group by closeTime",
				null);
		number = cursor.getCount();
		cursor.close();
		return number + "";
	}

	// 更新该餐桌 时间 ， 结账时间 。
	public void update_time(String desk_name) {
		db.execSQL("update SaleRecord set closeTime = ? where desk_name=?",
				new Object[] { DateUtils.getDateEN(), desk_name });
	}

	public void update_tip(String desk_name, float tip) {
		db.execSQL("update SaleRecord set tip = ? where desk_name=?",
				new Object[] { tip, desk_name });
	}

	public void update(d_SaleRecord paramd_SaleRecord, String paramString1,
			String paramString2) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = paramString1;
		arrayOfObject[1] = paramString2;
		localSQLiteDatabase.execSQL(
				"update SaleRecord set BILLID=? , PdtCODE=?", arrayOfObject);
	}

	/*
	 * public void update(String table) { Object[] arrayObject = new Object[3];
	 * arrayObject[0] = "prepare"; arrayObject[1] = "notsave"; arrayObject[2] =
	 * table; db.execSQL(
	 * "update SaleRecord set status = ? where status = ? and  desk_name = ?",
	 * arrayObject); }
	 */

	/*
	 * 更新数据库 ， 使status为done , 更新时间 。
	 */
	public void update(String done, String closeTime, String PdtName,
			String desk_name) {
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase
				.execSQL(
						"update SaleRecord set status = ? , closeTime = ? where PdtName=? and desk_name=?",
						new String[] { done, closeTime, PdtName, desk_name });
	}

	/*
	 * 更新数据库 ， 使status为done
	 */
	public void update(String done, String desk_name) {
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase.execSQL(
				"update SaleRecord set status = ? where  desk_name=?",
				new String[] { done, desk_name });
	}

	public void update_numac(d_SaleRecord paramd_SaleRecord, float paramFloat) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		BigDecimal localBigDecimal = BigDecimal.valueOf(paramd_SaleRecord
				.getNumber());
		float f = localBigDecimal.add(new BigDecimal(paramFloat))
				.setScale(2, 4).floatValue();
		if (f >= 1.0F) {
			Object[] arrayOfObject = new Object[3];
			arrayOfObject[0] = Float.valueOf(f);
			arrayOfObject[1] = paramd_SaleRecord.getPdtCODE();
			arrayOfObject[2] = Constant.table_id;
			localSQLiteDatabase
					.execSQL(
							"update SaleRecord set number=? where PdtCODE=? and desk_name=?",
							arrayOfObject);
		}
	}

	public void update_numspec(d_SaleRecord paramd_SaleRecord) {
		if (paramd_SaleRecord.getNumber() == 0.0F) {
			return;
		}
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = Float.valueOf(paramd_SaleRecord.getNumber());
		arrayOfObject[1] = paramd_SaleRecord.getOtherSpec();
		db.execSQL("update SaleRecord set number=?,foodSpec=? where ItemNo=?",
				arrayOfObject);
	}

	public void update_send(String desk, String status) {
		this.db.execSQL(
				"update SaleRecord set  status=? where desk_name=? and status='Not Sent'",
				new Object[] { status, desk });
	}

	/*
	 * 全部报告页面 ， 总共花费多少钱 。
	 */
	public float sum_cashAll() {
		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from SaleRecord ";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("Price")))
					.floatValue()
					* (int) Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("number"))).floatValue();
			mSumTotal += mSum;
		}
		return mSumTotal;

	}

	/*
	 * 返回数据库最后更新时间
	 */
	public String getLastTime() {
		String time = "";
		String str = "select CreateTime from SaleRecord order by CreateTime desc";
		Cursor localCursor = db.rawQuery(str, null);
		if (localCursor.moveToFirst()) {
			time = localCursor.getString(0);
		}
		if (time == null || time.equals("")) {
			time = "2015-01-01 12:12:12";
		}
		return time;

	}

	/*
	 * 更新折扣
	 */
	public void update_discount(d_SaleRecord localSaleRecord) {
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase
				.execSQL(
						"update SaleRecord set Discount = ? where  desk_name=? and PdtName = ?",
						new String[] { localSaleRecord.getDiscount() + "",
								localSaleRecord.getDesk_name(),
								localSaleRecord.getPdtName() });
	}

	/*
	 * 更新折扣
	 */
	public void update_discountAll(String discount, String desk_name) {
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase.execSQL(
				"update SaleRecord set Discount = ? where  desk_name=?",
				new String[] { discount, desk_name });
	}

	public boolean checkDesk(String table_id) {
		int result = 0;
		String str = "select count(*) from SaleRecord where desk_name=? and (status='Sent' or status='Done')";
		Cursor localCursor = db.rawQuery(str, new String[] { table_id });
		if (localCursor.moveToLast()) {
			result = localCursor.getInt(0);
		}
		return result == 0;
	}

	public void updateDone(d_SaleRecord sr) {
		if (sr.getStatus().equals("Done")) {
			db.execSQL(
					"update SaleRecord set status=? where  desk_name=? and PdtCODE = ? and status='Sent'",
					new String[] { sr.getStatus(), sr.getDesk_name(),
							sr.getPdtCODE() });
		}
	}

	public void updateFinish(d_SaleRecord sr) {
		db.execSQL(
				"update SaleRecord set status=? where  desk_name=? and PdtCODE = ?",
				new String[] { sr.getStatus(), sr.getDesk_name(),
						sr.getPdtCODE() });

	}

	public boolean getStatus(String table_id) {
		int result = 0;
		String str = "select * from SaleRecord where desk_name=? and status='Not Sent'";
		Cursor localCursor = db.rawQuery(str, new String[] { table_id });
		if (localCursor.moveToLast()) {
			result = localCursor.getCount();
		}
		return result == 1;
	}

	public void delete(String desk_name, int itemNo) {
		db.execSQL("delete from SaleRecord where desk_name =? and ItemNo=?",
				new Object[] { desk_name, itemNo });
	}
}
package com.utopia.Dao;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_SaleRecord;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;

public class sql_SaleRecord {
	private static final String String = null;
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
		String str = "select itemNo from SaleRecord where deskName='"
				+ paramString + "'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		this.db.execSQL("delete from saleandpdt where salerecordId=?",
				new Object[] { itemNo });
		this.db.execSQL("delete from SaleRecord where deskName=?",
				new Object[] { paramString });
	}

	public void clear_all() {
		this.db.execSQL("delete from saleandpdt");
		this.db.execSQL("delete from SaleRecord");
	}

	// /删除某一道菜
	// public void delete(String id) {
	//
	// this.db.execSQL("delete from saleandpdt where id=?",
	// new Object[] { id });
	//
	// }

	public void deleteNotSent(String desk_name, String status) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		this.db.execSQL(
				"delete from saleandpdt where salerecordId=? and status1=?",
				new Object[] { itemNo, status });
		// this.db.execSQL(
		// "delete from SaleRecord where deskName=? and status=?",
		// new Object[] { desk_name, status });

	}

	public void deleteLocal(String desk_name) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		this.db.execSQL("delete from saleandpdt where salerecordId=?",
				new Object[] { itemNo });
		this.db.execSQL("delete from SaleRecord where deskName=?",
				new Object[] { desk_name });
		// this.db.execSQL("delete from SaleRecord where desk_name=?",
		// new Object[] { desk_name });

	}

	public void delete(String desk_name, String pdtName) {

		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor localCursor = this.db.rawQuery(str, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		Object[] ob = new Object[2];
		ob[0] = itemNo;
		ob[1] = pdtName;
		db.execSQL(
				"delete from saleandpdt where salerecordId = ? and pdtName=?",
				ob);
	}

	/*
	 * Order Form 界面 总价
	 */
	public float getOneOrderTotalMoney(String deskName) {
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ deskName + "'";
		Cursor localCursor = this.db.rawQuery(str1, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		String str2 = "select ifnull(sum(number*Price),0.0) from saleandpdt where salerecordId="
				+ itemNo + "and status1!='Finish'";
		Cursor localCursor1 = this.db.rawQuery(str2, null);
		localCursor1.moveToFirst();
		float f = localCursor1.getFloat(0);
		localCursor1.close();
		return f;
	}

	/*
	 * Order Form 界面 总数
	 */
	public float getOneOrderTotalNum(String desk_name) {
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor localCursor = this.db.rawQuery(str1, null);
		localCursor.moveToFirst();
		int itemNo = localCursor.getInt(0);
		localCursor.close();
		String str = "select ifnull(sum(number),0.0) from saleandpdt where salerecordId="
				+ itemNo + "and status1!='Finish'";
		Cursor localCursor1 = this.db.rawQuery(str, null);
		localCursor1.moveToFirst();
		float f = localCursor1.getFloat(0);
		localCursor1.close();
		return f;
	}

	/*
	 * Order Form 界面 总数
	 */
	public int getNotCheckedNum() {
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ Constant.table_id + "'";
		Cursor localCursor1 = this.db.rawQuery(str1, null);
		localCursor1.moveToFirst();
		int itemNo = localCursor1.getInt(0);
		localCursor1.close();
		String str = "select count(*) from saleandpdt where salerecordId="
				+ itemNo + "and status1!='Finish' and status1!='Doned'";
		Cursor localCursor = db.rawQuery(str, null);
		localCursor.moveToFirst();
		int count = localCursor.getInt(0);
		localCursor.close();
		return count;
	}

	// 得到订单总额 消费合计
	public float sumTotal(String desk_name) {
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor localCursor1 = this.db.rawQuery(str1, null);
		localCursor1.moveToFirst();
		int itemNo = localCursor1.getInt(0);
		localCursor1.close();
		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from saleandpdt where salerecordId='" + itemNo
				+ "' and status1!='Finish'";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("price")))
					.floatValue()
					* (int) Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("number"))).floatValue();
			// * Float.valueOf(
			// localCursor.getString(localCursor
			// .getColumnIndex("Discount"))).floatValue();
			mSumTotal += mSum;
		}
		return mSumTotal;
	}

	// 得到订单总额 消费合计
	public float sumTotalDone(String paramString) {

		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from SaleRecord as a JOIN saleandpdt as b ON a.itemNo=b.salerecordId where a.deskName='"
				+ paramString + "' and b.status1!='Finish'";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("price")))
					.floatValue()
					* (int) Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("number"))).floatValue()
					* Float.valueOf(
							localCursor.getString(localCursor
									.getColumnIndex("rebate"))).floatValue();
			mSumTotal += mSum;
		}
		return mSumTotal;
	}

	// 得到该桌子上未发送的所有的菜的信息
	public ArrayList<d_SaleRecord> recordlist(String desk_name) {
		ArrayList<d_SaleRecord> localArrayList = new ArrayList<d_SaleRecord>();
		Cursor localCursor = this.db
				.rawQuery(
						"select * from SaleRecord as a JOIN saleandpdt as b ON a.itemNo=b.salerecordId where a.desk_name==? and b.status1=='Not Sent'",
						new String[] { desk_name });
		while (localCursor.moveToNext()) {
			d_SaleRecord locald_SaleRecord = new d_SaleRecord();
			// locald_SaleRecord.setPayId(localCursor.getString(localCursor
			// .getColumnIndex("PayId")));
			// locald_SaleRecord.setBILLID(localCursor.getString(localCursor
			// .getColumnIndex("BILLID")));

			locald_SaleRecord.setPdtCODE(localCursor.getString(localCursor
					.getColumnIndex("pdtCode")));
			locald_SaleRecord.setPdtName(localCursor.getString(localCursor
					.getColumnIndex("pdtName")));
			locald_SaleRecord.setNumber((int) (Float.valueOf(localCursor
					.getString(localCursor.getColumnIndex("number")))
					.floatValue()));
			locald_SaleRecord.setPrice(Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("price")))
					.floatValue());
			locald_SaleRecord.setDiscount(localCursor.getFloat(localCursor
					.getColumnIndex("rebate")));
			locald_SaleRecord.setCreateTime(localCursor.getString(localCursor
					.getColumnIndex("createTime1")));
			locald_SaleRecord.setStatus(localCursor.getString(localCursor
					.getColumnIndex("status1")));
			locald_SaleRecord.setDesk_name(localCursor.getString(localCursor
					.getColumnIndex("deskName")));
			locald_SaleRecord.setOtherSpecNo1(localCursor.getString(localCursor
					.getColumnIndex("otherspec1")));
			locald_SaleRecord.setOtherSpecNo2(localCursor.getString(localCursor
					.getColumnIndex("otherspec2")));
			locald_SaleRecord.setOtherSpec(localCursor.getString(localCursor
					.getColumnIndex("otherSpec0")));
			locald_SaleRecord.setWaiter(localCursor.getString(localCursor
					.getColumnIndex("waiter")));
			locald_SaleRecord.setTax(localCursor.getFloat(localCursor
					.getColumnIndex("tax")));
			locald_SaleRecord.setItemNo(localCursor.getInt(localCursor
					.getColumnIndex("itemNo")));
			locald_SaleRecord.setPriority(localCursor.getInt(localCursor
					.getColumnIndex("priority")));
			localArrayList.add(locald_SaleRecord);
		}
		localCursor.close();
		return localArrayList;
	}

	// 得到该桌子上当前被服务的所有的菜
	public ArrayList<d_SaleRecord> getAllSalerecord(String desk_name) {
		ArrayList<d_SaleRecord> localArrayList = new ArrayList<d_SaleRecord>();
		Cursor localCursor = this.db
				.rawQuery(
						"select * from SaleRecord as a JOIN saleandpdt as b ON a.itemNo=b.salerecordId where a.desk_name==? and b.status1!='Finish'",
						new String[] { desk_name });
		while (localCursor.moveToNext()) {
			d_SaleRecord locald_SaleRecord = new d_SaleRecord();
			// locald_SaleRecord.setPayId(localCursor.getString(localCursor
			// .getColumnIndex("PayId")));
			// locald_SaleRecord.setBILLID(localCursor.getString(localCursor
			// .getColumnIndex("BILLID")));

			locald_SaleRecord.setPdtCODE(localCursor.getString(localCursor
					.getColumnIndex("pdtCode")));
			locald_SaleRecord.setPdtName(localCursor.getString(localCursor
					.getColumnIndex("pdtName")));
			locald_SaleRecord.setNumber((int) (Float.valueOf(localCursor
					.getString(localCursor.getColumnIndex("number")))
					.floatValue()));
			locald_SaleRecord.setPrice(Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("price")))
					.floatValue());
			locald_SaleRecord.setDiscount(localCursor.getFloat(localCursor
					.getColumnIndex("rebate")));
			locald_SaleRecord.setCreateTime(localCursor.getString(localCursor
					.getColumnIndex("createTime1")));
			locald_SaleRecord.setStatus(localCursor.getString(localCursor
					.getColumnIndex("status1")));
			locald_SaleRecord.setDesk_name(localCursor.getString(localCursor
					.getColumnIndex("deskName")));
			locald_SaleRecord.setOtherSpecNo1(localCursor.getString(localCursor
					.getColumnIndex("otherspec1")));
			locald_SaleRecord.setOtherSpecNo2(localCursor.getString(localCursor
					.getColumnIndex("otherspec2")));
			locald_SaleRecord.setOtherSpec(localCursor.getString(localCursor
					.getColumnIndex("otherSpec0")));
			locald_SaleRecord.setWaiter(localCursor.getString(localCursor
					.getColumnIndex("waiter")));
			locald_SaleRecord.setTax(localCursor.getFloat(localCursor
					.getColumnIndex("tax")));
			locald_SaleRecord.setItemNo(localCursor.getInt(localCursor
					.getColumnIndex("itemNo")));
			locald_SaleRecord.setPriority(localCursor.getInt(localCursor
					.getColumnIndex("priority")));
			localArrayList.add(locald_SaleRecord);
		}
		localCursor.close();
		return localArrayList;
	}

	// public Cursor recordlist2(String paramString) {
	// return db.rawQuery("select * from SaleRecord order by ItemNo", null);
	// }

	public Cursor recordlist3(String paramString) {
		/*
		 * Cursor c = db.rawQuery("select *from SaleRecord", null);
		 * while(c.moveToNext()){ Log.e("sql_SaleRecord"
		 * ,c.getString(c.getColumnIndex("PdtName")) +
		 * c.getString(c.getColumnIndex("status"))); }
		 */

		return db.rawQuery(paramString, null);
	}

	public void recordlist5(String paramString) {
		/*
		 * Cursor c = db.rawQuery("select *from SaleRecord", null);
		 * while(c.moveToNext()){ Log.e("sql_SaleRecord"
		 * ,c.getString(c.getColumnIndex("PdtName")) +
		 * c.getString(c.getColumnIndex("status"))); }
		 */

		db.execSQL(paramString);
	}

	// public ArrayList<String> recordlist4(String paramString) {
	// ArrayList<String> localArrayList = new ArrayList<String>();
	// Cursor localCursor = this.db.rawQuery(paramString, null);
	// while (localCursor.moveToNext()) {
	// localArrayList.add(localCursor.getString(0));
	// }
	// localCursor.close();
	// return localArrayList;
	// }

	public void save(d_SaleRecord paramd_SaleRecord) {
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
		arrayOfObject[15] = paramd_SaleRecord.getCustomerNo();
		Cursor localCursor = this.db
				.rawQuery(
						"select * from SaleRecord as s1 join saleandpdt as s2 on s1.itemNo=s2.salerecordId"
								+ "where pdtCode='"
								+ paramd_SaleRecord.getPdtCODE()
								+ "' and deskName='"
								+ paramd_SaleRecord.getDesk_name()
								+ "' and status1=='Not Sent'", null);
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ paramd_SaleRecord.getDesk_name() + "'";
		Cursor localCursor1 = this.db.rawQuery(str1, null);
		localCursor1.moveToFirst();
		int itemNo = localCursor1.getInt(0);
		localCursor1.close();
		if (localCursor.moveToNext()) {
			db.execSQL("update SaleRecord set waiter=? where deskName=?",
					new Object[] { paramd_SaleRecord.getDesk_name() });
			db.execSQL(
					"UPDATE saleandpdt SET otherspec0=?, otherspec1 = ? , otherspec2 = ?  WHERE salerecordId=? and pdtCode=?",
					new Object[] { paramd_SaleRecord.getOtherSpec(),
							paramd_SaleRecord.getOtherSpecNo1(),
							paramd_SaleRecord.getOtherSpecNo2(), itemNo,
							paramd_SaleRecord.getPdtCODE() });
		} else {
			Cursor localCursor0 = this.db.rawQuery(
					"select * from SaleRecord where  deskName='"
							+ paramd_SaleRecord.getDesk_name()
							+ "' and itemNo='" + paramd_SaleRecord.getItemNo()
							+ "'", null);
			if (localCursor0.moveToNext()) {
				db.execSQL(
						"insert into saleandpdt(pdtCode,pdtName,price,number,otherspec0,otherspec1,"
								+ "otherspec2,createTime1,closeTime1,status1) values(?,?,?,?,?,?,?,?,?,?)",
						new Object[] { paramd_SaleRecord.getPdtCODE(),
								paramd_SaleRecord.getPdtName(),
								paramd_SaleRecord.getPrice(),
								paramd_SaleRecord.getNumber(),
								paramd_SaleRecord.getOtherSpec(),
								paramd_SaleRecord.getOtherSpecNo1(),
								paramd_SaleRecord.getOtherSpecNo2(),
								paramd_SaleRecord.getCreateTime(),
								paramd_SaleRecord.getCloseTime(),
								paramd_SaleRecord.getStatus() });
			} else {
				db.execSQL(
						"INSERT INTO SaleRecord(deskName,waiter,tax, rebate) values(?,?,?,?)",
						new Object[] { paramd_SaleRecord.getDesk_name(),
								paramd_SaleRecord.getWaiter(),
								paramd_SaleRecord.getTax(),
								paramd_SaleRecord.getDiscount() });
			}
		}
		// log();
	}

	// public void log() {
	// Cursor localCursor = this.db
	// .rawQuery("select * from SaleRecord ", null);
	// /*
	// * if (localCursor.moveToNext()) {
	// * Log.e("sql",localCursor.getString(localCursor
	// * .getColumnIndex("status"))); }
	// */
	//
	// }

	// public void saveInit(d_SaleRecord paramd_SaleRecord) {
	// Object[] arrayOfObject = new Object[16];
	// arrayOfObject[0] = paramd_SaleRecord.getPdtCODE();
	// arrayOfObject[1] = paramd_SaleRecord.getPdtName();
	// arrayOfObject[2] = paramd_SaleRecord.getPrice();
	// arrayOfObject[3] = paramd_SaleRecord.getBILLID();
	// arrayOfObject[4] = paramd_SaleRecord.getNumber();
	// arrayOfObject[5] = paramd_SaleRecord.getOtherSpec();
	// arrayOfObject[6] = paramd_SaleRecord.getStatus();
	// arrayOfObject[7] = paramd_SaleRecord.getOtherSpecNo1();
	// arrayOfObject[8] = paramd_SaleRecord.getOtherSpecNo2();
	// arrayOfObject[9] = paramd_SaleRecord.getDesk_name();
	// arrayOfObject[10] = paramd_SaleRecord.getCreateTime();
	// arrayOfObject[11] = paramd_SaleRecord.getWaiter();
	// arrayOfObject[12] = paramd_SaleRecord.getTax();
	// arrayOfObject[13] = 1;
	// arrayOfObject[14] = paramd_SaleRecord.getCloseTime();
	// arrayOfObject[15] = paramd_SaleRecord.getItemNo();
	// Cursor localCursor = this.db.rawQuery(
	// "select * from SaleRecord where PdtCODE='"
	// + paramd_SaleRecord.getPdtCODE() + "' and desk_name='"
	// + paramd_SaleRecord.getDesk_name()
	// + "' and status=='Not Sent'", null);
	// if (localCursor.moveToNext()) {
	// db.execSQL(
	// "UPDATE SaleRecord SET number=number+1 ,OtherSpec=?, OtherSpecNo1 = ? , OtherSpecNo2 = ? , Waiter = ? WHERE desk_name=? and PdtCODE=?",
	// new Object[] { arrayOfObject[5],
	// paramd_SaleRecord.getOtherSpecNo1(),
	// paramd_SaleRecord.getOtherSpecNo2(),
	// paramd_SaleRecord.getWaiter(),
	// paramd_SaleRecord.getDesk_name(),
	// paramd_SaleRecord.getPdtCODE() });
	// } else {
	// db.execSQL(
	// "INSERT INTO SaleRecord(PdtCODE,PdtName,Price,BILLID,number,OtherSpec,status,OtherSpecNo1,OtherSpecNo2,desk_name,CreateTime,Waiter,tax , Discount,closeTime,ItemNo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	//
	// arrayOfObject);
	// }
	// }

	public String getTransactions() {
		int number = 0;
		Cursor cursor = db
				.rawQuery(
						"select count(s2.closeTime1) from SaleRecord as s1 join saleandpdt as s2 on s1.itemNo=s2.salerecordId"
								+ " where s1.waiter = '"
								+ Constant.currentStaff.getS_account()
								+ "'group by s2.closeTime1", null);
		number = cursor.getCount();
		cursor.close();
		return number + "";
	}

	public String getTip() {
		float tip = 0;
		Cursor cursor = db.rawQuery(
				"select tiptotal as  sum from SaleRecord where waiter = '"
						+ Constant.currentStaff.getS_account(), null);
		while (cursor.moveToNext()) {
			tip += cursor.getFloat(cursor.getColumnIndex("sum"));
		}

		cursor.close();
		return tip + "";
	}

	// 得到销售记录里的所有小费
	public String getTipAll() {
		float tip = 0;
		Cursor cursor = db.rawQuery("select tiptotal as sum from SaleRecord ",
				null);
		while (cursor.moveToNext()) {
			tip += cursor.getFloat(cursor.getColumnIndex("sum"));
		}

		cursor.close();
		return tip + "";
	}

	public String getTransactionsAll() {
		int number = 0;
		Cursor cursor = db.rawQuery("select count(closeTime1) from saleandpdt "
				+ " group by closeTime1", null);
		number = cursor.getCount();
		cursor.close();
		return number + "";
	}

	// 更新该餐桌 时间 ， 结账时间 。
	public void update_time(String desk_name) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		db.execSQL("update saleandpdt set closeTime1 = ? where salerecordId=?",
				new Object[] { DateUtils.getDateEN(), itemNo });
	}

	// public void update_tip(String desk_name, float tip) {
	// db.execSQL("update SaleRecord set tip = ? where desk_name=?",
	// new Object[] { tip, desk_name });
	// }

	// public void update(d_SaleRecord paramd_SaleRecord, String paramString1,
	// String paramString2) {
	// SQLiteDatabase localSQLiteDatabase = this.db;
	// Object[] arrayOfObject1 = new Object[1];
	// arrayOfObject1[0] = paramString2;
	// localSQLiteDatabase.execSQL(
	// "update salandpdt set pdtCode=?", arrayOfObject1);
	// Object[] arrayOfObject2 = new Object[1];
	// arrayOfObject2[0] = paramString1;
	// localSQLiteDatabase.execSQL(
	// "update Bill set BillId=?", arrayOfObject2);
	// }

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
		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase
				.execSQL(
						"update saleandpdt set status1 = ? , closeTime1 = ? where  pdtName=? and salerecordId=?",
						new Object[] { done, closeTime, PdtName, itemNo });
	}

	/*
	 * 更新数据库 ， 使status为done , 更新时间 。
	 */
	public void update1(String done, String closeTime, String PdtName,
			String desk_name, String BILLID, String ItemNo, String createTime) {

		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase
				.execSQL(
						"update saleandpdt set status1 = ? , closeTime1 = ? where  pdtName=? and salerecordId=?",
						new Object[] { done, closeTime, PdtName, itemNo });
	}

	/*
	 * 更新数据库 ， 使status为done
	 */
	public void update(String done, String desk_name) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ desk_name + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase.execSQL(
				"update saleandpdt set status1 = ? where  salerecordId=?",
				new Object[] { done, itemNo });
		/*
		 * Cursor c = db.rawQuery("select *from SaleRecord", null);
		 * while(c.moveToNext()){ Log.e("sql_SaleRecord"
		 * ,c.getString(c.getColumnIndex("PdtName")) +
		 * c.getString(c.getColumnIndex("status"))); }
		 */

	}

	public void update_numac(d_SaleRecord paramd_SaleRecord, float paramFloat) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ Constant.table_id + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		SQLiteDatabase localSQLiteDatabase = this.db;
		BigDecimal localBigDecimal = BigDecimal.valueOf(paramd_SaleRecord
				.getNumber());
		float f = localBigDecimal.add(new BigDecimal(paramFloat))
				.setScale(2, 4).floatValue();
		if (f >= 1.0F) {
			Object[] arrayOfObject = new Object[3];
			arrayOfObject[0] = Float.valueOf(f);
			arrayOfObject[1] = paramd_SaleRecord.getPdtCODE();
			arrayOfObject[2] = itemNo;
			localSQLiteDatabase
					.execSQL(
							"update saleandpdt set number=? where pdtCode=? and salerecordId=?",
							arrayOfObject);
		}
	}

	// public void update_numspec(d_SaleRecord paramd_SaleRecord) {
	// if (paramd_SaleRecord.getNumber() == 0.0F) {
	// return;
	// }
	// Object[] arrayOfObject = new Object[2];
	// arrayOfObject[0] = Float.valueOf(paramd_SaleRecord.getNumber());
	// arrayOfObject[1] = paramd_SaleRecord.getOtherSpec();
	// db.execSQL("update SaleRecord set number=?,foodSpec=? where ItemNo=?",
	// arrayOfObject);
	// }

	public void update_send(String desk, String status) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ Constant.table_id + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		this.db.execSQL(
				"update saleandpdt set  status1=? where salerecordId=? and status1='Not Sent'",
				new Object[] { status, itemNo });
	}

	/*
	 * 全部报告页面 ， 总共花费多少钱 。
	 */
	public float sum_cashAll() {
		float mSumTotal = (float) 0.0;
		float mSum = (float) 0.0;
		String str = "select * from saleandpdt ";
		Cursor localCursor = this.db.rawQuery(str, null);
		while (localCursor.moveToNext()) {
			mSum = Float.valueOf(
					localCursor.getString(localCursor.getColumnIndex("price")))
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
		String str = "select createTime1 from saleandpdt order by createTime1 desc";
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
		localSqliteDatebase.execSQL(
				"update SaleRecord set rebate = ? where  deskName=?",
				new String[] { localSaleRecord.getDiscount() + "",
						localSaleRecord.getDesk_name(), });

	}

	/*
	 * 更新折扣
	 */
	public void update_discountAll(String discount, String desk_name) {
		SQLiteDatabase localSqliteDatebase = this.db;
		localSqliteDatebase.execSQL(
				"update SaleRecord set rebate = ? where  deskName=?",
				new String[] { discount, desk_name });
	}

	public boolean checkDesk(String table_id) {
		int result = 0;
		String str = "select count(*) from SaleRecord as s1 join saleandpdt as s2 where deskName=? and (s2.status1='Sent' or s2.status1='Done')";
		Cursor localCursor = db.rawQuery(str, new String[] { table_id });
		if (localCursor.moveToLast()) {
			result = localCursor.getInt(0);
		}
		return result == 0;
	}

	public void updateDone(d_SaleRecord sr) {
		String str = "select itemNo from SaleRecord where deskName='"
				+ sr.getDesk_name() + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();

		if (sr.getStatus().equals("Done")) {
			db.execSQL(
					"update saleandpdt set status1=? where  salerecordId=? and pdtCode = ? and status1='Sent'",
					new Object[] { sr.getStatus(), itemNo, sr.getPdtCODE() });
		}
	}

	public void updateFinish(d_SaleRecord sr) {
		db.execSQL(
				"update SaleRecord set status=? where  desk_name=? and PdtCODE = ?",
				new String[] { sr.getStatus(), sr.getDesk_name(),
						sr.getPdtCODE() });

	}

	public boolean getStatus(String table_id) {
		String str1 = "select itemNo from SaleRecord where deskName='"
				+ table_id + "'";
		Cursor cursor = db.rawQuery(str1, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		int result = 0;
		String str = "select * from saleandpdt where salerecordId=? and status1='Not Sent'";
		Cursor localCursor = db.rawQuery(str,
				(String[]) new Object[] { itemNo });
		if (localCursor.moveToLast()) {
			result = localCursor.getCount();
		}
		return result == 1;
	}

	// public void delete(String desk_name, int id) {
	// db.execSQL("delete from SaleRecord where desk_name =? and id=?",
	// new Object[] { desk_name, id });
	// }

	public void update_customerNo(d_SaleRecord paramd_SaleRecord,
			float paramFloat) {
	
		BigDecimal localBigDecimal = BigDecimal.valueOf(paramd_SaleRecord
				.getCustomerNo());
		float f = localBigDecimal.add(new BigDecimal(paramFloat))
				.setScale(2, 4).floatValue();
		if (f >= 1.0F) {
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = Float.valueOf(f);
			arrayOfObject[1] = paramd_SaleRecord.getItemNo();
			db.execSQL("update Cutomer set customNo=? where ItemNo=?",
					arrayOfObject);
		}
	}

	public void update_customerNo(String ItemNo, boolean falg) {
		String sql = "";
		if (falg) {
			sql = "update Cutomer set CustomerNo=CustomerNo+1 where ItemNo=? ";
		} else {
			sql = "update Cutomer set CustomerNo=CustomerNo-1 where ItemNo=? ";
		}
		db.execSQL(sql, new Object[] { ItemNo});
	}

	public void updateAllCustomerNo() {
		String str = "select itemNo from SaleRecord where deskName='"
				+ Constant.table_id + "'";
		Cursor cursor = db.rawQuery(str, null);
		cursor.moveToFirst();
		int itemNo = cursor.getInt(0);
		cursor.close();
		String sql = "update Cutomer set CustomerNo=0 where ItemNo=?";

		db.execSQL(sql, new Object[] { itemNo });
	}
}
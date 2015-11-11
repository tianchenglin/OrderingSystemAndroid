package com.utopia.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Sale;
import com.utopia.Model.d_SaleRecord;
import com.utopia.utils.Constant;

public class sql_Sales {
	SQLiteDatabase db;

	public sql_Sales() {
		if (db == null)
			db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}
	public void clear_all() {
		this.db.execSQL("delete from SaleRecord");
	}
	public void saveInit(d_Sale paramd_SaleRecord) {
		Object[] arrayOfObject = new Object[20];
		arrayOfObject[0] = paramd_SaleRecord.getItemNo();
		arrayOfObject[1] = paramd_SaleRecord.getCloseTime();
		arrayOfObject[2] = paramd_SaleRecord.getCreateTime();
		arrayOfObject[3] = paramd_SaleRecord.getDeskName();
		arrayOfObject[4] = paramd_SaleRecord.getOtherSpec();
		arrayOfObject[5] = paramd_SaleRecord.getOtherSpecNo1();
		arrayOfObject[6] = paramd_SaleRecord.getOtherSpecNo2();
		arrayOfObject[7] = paramd_SaleRecord.getStatus();
		arrayOfObject[8] = paramd_SaleRecord.getDept();
		arrayOfObject[9] = paramd_SaleRecord.getSubtotal();
		arrayOfObject[10] = paramd_SaleRecord.getTiptotal();
		arrayOfObject[11] = paramd_SaleRecord.getTotal();
		arrayOfObject[12] = paramd_SaleRecord.getInitTotal();
		arrayOfObject[13] = paramd_SaleRecord.getRebate();
		arrayOfObject[14] = paramd_SaleRecord.getTax();
		arrayOfObject[15] = paramd_SaleRecord.getWaiter();
		arrayOfObject[16] = paramd_SaleRecord.getCashTotal();
		arrayOfObject[17] = paramd_SaleRecord.getCardTotal();
		//arrayOfObject[18] = "";
		//arrayOfObject[19] = paramd_SaleRecord.getCustomNo();
//		Cursor localCursor = this.db.rawQuery(
//				"select * from SaleRecord where itemNo='"
//						+ paramd_SaleRecord.getItemNo() + "' and desk_name='"
//						+ paramd_SaleRecord.getDeskName()
//						, null);  //+ "' and status=='Not Sent'"
//		if (localCursor.moveToNext()) {
//			db.execSQL(
//					"UPDATE SaleRecord SET number=number+1 ,OtherSpec=?, OtherSpecNo1 = ? , OtherSpecNo2 = ? , Waiter = ? WHERE desk_name=? and PdtCODE=?",
//					new Object[] { arrayOfObject[5],
//							paramd_SaleRecord.getOtherSpecNo1(),
//							paramd_SaleRecord.getOtherSpecNo2(),
//							paramd_SaleRecord.getWaiter(),
//							paramd_SaleRecord.getDeskName(),
//							paramd_SaleRecord.getItemNo() });
//		} else {
			db.execSQL(
					"INSERT INTO SaleRecord(itemNo,closeTime,createTime,deskName,otherSpec,otherSpecNo1,otherSpecNo2,status,dept,subtotal,tiptotal,total,initTotal , rebate,tax,waiter,cashTotal,cardTotal) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                     arrayOfObject);
		}
	}


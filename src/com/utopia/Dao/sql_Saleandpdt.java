package com.utopia.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Sale;
import com.utopia.Model.d_Saleandpdt;
import com.utopia.utils.Constant;

public class sql_Saleandpdt {
	SQLiteDatabase db;

	public sql_Saleandpdt() {
		if (db == null)
			db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}
	public void clear_all() {
		this.db.execSQL("delete from saleandpdt");
	}
	public void saveInit(d_Saleandpdt paramd_Saleandpdt) {
		Object[] arrayOfObject = new Object[13];
		arrayOfObject[0] = paramd_Saleandpdt.getId();
		arrayOfObject[1] = paramd_Saleandpdt.getSalerecordId();
		arrayOfObject[2] = paramd_Saleandpdt.getPdtCode();
		arrayOfObject[3] = paramd_Saleandpdt.getPdtName();
		arrayOfObject[4] = paramd_Saleandpdt.getNumber();
		arrayOfObject[5] = paramd_Saleandpdt.getPrice();
		arrayOfObject[6] = paramd_Saleandpdt.getOtherspec();
		arrayOfObject[7] = paramd_Saleandpdt.getOtherspec1();
		arrayOfObject[8] = paramd_Saleandpdt.getOtherspec2();
		arrayOfObject[9] = paramd_Saleandpdt.getPriority();
		arrayOfObject[10] = paramd_Saleandpdt.getStatus();
		arrayOfObject[11] = paramd_Saleandpdt.getCreateTime();
		arrayOfObject[12] = paramd_Saleandpdt.getCloseTime();
		Cursor localCursor = this.db.rawQuery(
				"select * from saleandpdt where pdtCode='"
						+ paramd_Saleandpdt.getPdtCode()
						+ "' and salerecordId='"
						+ paramd_Saleandpdt.getSalerecordId()
						+ "' and status1=='Not Sent'", null);
		if (localCursor.moveToNext()) {
			db.execSQL(
					"UPDATE saleandpdt SET number=number+1 ,otherspec0=?, otherspec1 = ? , otherspec2 = ? WHERE salerecordId=? and pdtCode=?",
					new Object[] { arrayOfObject[5],
							paramd_Saleandpdt.getOtherspec(),
							paramd_Saleandpdt.getOtherspec1(),
							paramd_Saleandpdt.getOtherspec2(),
							paramd_Saleandpdt.getSalerecordId(),
							paramd_Saleandpdt.getPdtCode() });
		} else {
			db.execSQL(
					"INSERT INTO saleandpdt(id,salerecordId,pdtCode,pdtName,number,price,otherspec0,otherspec1,otherspec2,priority,status1,createTime1,closeTime1) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
					arrayOfObject);
		}

	}
}

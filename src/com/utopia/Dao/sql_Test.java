package com.utopia.Dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Area;
import com.utopia.utils.Constant;

public class sql_Test {
	Context context;
	SQLiteDatabase db;

	public sql_Test() {
		this.db = Constant.openDatabase();
	}

	sql_Test(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}
	public String getNumber() {
		String numbers = "";
		Cursor localCursor = null;
		String sql[]={
				"select count(*) from Product",
				"select count(*) from Desk",
				"select count(*) from Area",
				"select count(*) from Menutype",
				"select count(*) from Tax",
				"select count(*) from Salerecord",
				"select count(*) from Bill",
				"select count(*) from Cashier",
				"select count(*) from Contact"
		};
		for(int i = 0 ; i<sql.length ;i++){
			localCursor = db.rawQuery(sql[i], null);
			if(localCursor.moveToNext()){
				numbers +=localCursor.getInt(0)+",";
			}else{
				numbers +="0,";
			}
		}
		return numbers;
	}
}
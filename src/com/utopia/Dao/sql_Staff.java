package com.utopia.Dao;

import com.utopia.Model.d_Staff;
import com.utopia.utils.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class sql_Staff {
	Context context;
	SQLiteDatabase db;

	public sql_Staff(){
		this.db = Constant.openDatabase();
	}
	
	public sql_Staff(Context paramContext) {
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

	public void save(d_Staff param) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = param.getS_account();
		arrayOfObject[1] = param.getS_name();
		arrayOfObject[2] = param.getS_pwd();
		arrayOfObject[3] = param.getType_name();
		localSQLiteDatabase
				.execSQL(
						"INSERT INTO tbl_user(TypeId,TypeName,TypeParentId) values(?,?,?)",
						arrayOfObject);
		
	}
}

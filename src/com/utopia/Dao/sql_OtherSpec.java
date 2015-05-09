package com.utopia.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_OtherSpec;
import com.utopia.utils.Constant;

import java.util.ArrayList;

public class sql_OtherSpec {
	Context context;
	SQLiteDatabase db;

	public sql_OtherSpec() {
		this.db = Constant.openDatabase();
	}

	sql_OtherSpec(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void checksave(d_OtherSpec paramd_OtherSpec) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = paramd_OtherSpec.getOtherSpecTypeID();
		arrayOfObject[1] = Integer.valueOf(paramd_OtherSpec.getOtherSpecNo());
		arrayOfObject[2] = paramd_OtherSpec.getOtherSpecName();
		arrayOfObject[3] = paramd_OtherSpec.getOtherSpecPy();
		arrayOfObject[4] = Integer.valueOf(paramd_OtherSpec.getOtherSpecNo());
		localSQLiteDatabase.beginTransaction();
		try {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO OtherSpec(OtherSpecTypeID,OtherSpecNo,OtherSpecName,OtherSpecPy) select ?,?,?,? where not exists(select 1 from OtherSpec where OtherSpecNo=?)",
							arrayOfObject);
			localSQLiteDatabase.setTransactionSuccessful();
			return;
		} catch (SQLException localSQLException) {
			throw localSQLException;
		} finally {
			localSQLiteDatabase.endTransaction();
		}
	}

	public void clear_all() {
		this.db.execSQL("delete from OtherSpec");
	}

	public void delete(String paramString) {
		this.db.execSQL("delete from OtherSpec where OtherSpecTypeID=?",
				new Object[] { paramString });
	}

	public ArrayList<d_OtherSpec> recordlist(String paramString) {
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = this.db.rawQuery(
				"select * from OtherSpec order by OtherSpecTypeID", null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				return localArrayList;
			}
			d_OtherSpec locald_OtherSpec = new d_OtherSpec();
			locald_OtherSpec.setOtherSpecTypeID(localCursor.getString(0));
			locald_OtherSpec.setOtherSpecNo(Integer.valueOf(
					localCursor.getString(1)).intValue());
			locald_OtherSpec.setOtherSpecName(localCursor.getString(2));
			locald_OtherSpec.setOtherSpecPy(localCursor.getString(3));
			localArrayList.add(locald_OtherSpec);
		}
	}

	public ArrayList recordlist2(String paramString) {
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = this.db.rawQuery(paramString, null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				return localArrayList;
			}
			localArrayList.add(localCursor.getString(0));
		}
	}

	public void save(d_OtherSpec paramd_OtherSpec) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = paramd_OtherSpec.getOtherSpecTypeID();
		arrayOfObject[1] = Integer.valueOf(paramd_OtherSpec.getOtherSpecNo());
		arrayOfObject[2] = paramd_OtherSpec.getOtherSpecName();
		localSQLiteDatabase
				.execSQL(
						"INSERT INTO OtherSpec(OtherSpecTypeID,OtherSpecNo,OtherSpecName) values(?,?,?)",
						arrayOfObject);
	}

	public void update(d_OtherSpec paramd_OtherSpec, String paramString1,
			String paramString2) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = paramString1;
		arrayOfObject[1] = paramString2;
		arrayOfObject[2] = paramd_OtherSpec.getOtherSpecTypeID();
		localSQLiteDatabase
				.execSQL(
						"update OtherSpec set OtherSpecName=? , OtherSpecPy=? where OtherSpecTypeID=?",
						arrayOfObject);
	}
}
package com.utopia.Dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Area;
import com.utopia.utils.Constant;

public class sql_Area {
	Context context;
	SQLiteDatabase db;

	public sql_Area() {
		this.db = Constant.openDatabase();
	}

	sql_Area(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void checksave(d_Area paramd_Area) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = paramd_Area.getAreaId();
		arrayOfObject[1] = paramd_Area.getAreaName();
		arrayOfObject[2] = paramd_Area.getAreaId();
		localSQLiteDatabase.beginTransaction();
		try {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO Area(AreaId,AreaName) select ?,? where not exists(select 1 from Area where AreaId=?)",
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
		this.db.execSQL("delete from Area");
	}

	public void delete(String paramString) {
		this.db.execSQL("delete from Area where AreaId=?",
				new Object[] { paramString });
	}

	public ArrayList<d_Area> recordlist(String paramString) {
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = this.db.rawQuery(
				"select * from Area order by AreaId", null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				return localArrayList;
			}
			d_Area locald_Area = new d_Area();
			locald_Area.setAreaId(localCursor.getString(0));
			locald_Area.setAreaName(localCursor.getString(1));
			localArrayList.add(locald_Area);
		}
	}

	public ArrayList<String> recordlist2(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		Cursor localCursor = this.db.rawQuery(paramString, null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				return localArrayList;
			}
			localArrayList.add(localCursor.getString(0));
		}
	}

	public void save(d_Area paramd_Area) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = paramd_Area.getAreaId();
		arrayOfObject[1] = paramd_Area.getAreaName();
		localSQLiteDatabase.execSQL(
				"INSERT INTO Area(AreaId,AreaName) values(?,?)", arrayOfObject);
	}

	public void update(d_Area paramd_Area, String paramString1,
			String paramString2) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = paramString1;
		arrayOfObject[1] = paramd_Area.getAreaId();
		localSQLiteDatabase.execSQL(
				"update Area set AreaName=? where AreaId=?", arrayOfObject);
	}
}
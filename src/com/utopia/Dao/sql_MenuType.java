package com.utopia.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_MenuType;
import com.utopia.utils.Constant;

import java.util.ArrayList;

public class sql_MenuType {
	Context context;
	SQLiteDatabase db;

	public sql_MenuType() {
		this.db = Constant.openDatabase();
	}

	public sql_MenuType(Context paramContext) {
		this.context = paramContext;
		if (this.db == null)
			this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void checksave(d_MenuType paramd_TypeName) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[4];
		arrayOfObject[0] = paramd_TypeName.getTypeId();
		arrayOfObject[1] = paramd_TypeName.getTypeName();
		arrayOfObject[2] = paramd_TypeName.getTypeParentId();
		arrayOfObject[3] = paramd_TypeName.getTypeId();
		localSQLiteDatabase.beginTransaction();
		try {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO MenuType(TypeId,TypeName,TypeParentId) select ?,?,? where not exists(select 1 from TypeName where trim(TypeId)=?)",
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
		this.db.execSQL("delete from MenuType");
	}

	public void delete(String paramString) {
		this.db.execSQL("delete from MenuType where TypeId=?",
				new Object[] { paramString });
	}

	public ArrayList<d_MenuType> queryMenuTypes(String paramString) {
		ArrayList<d_MenuType> localArrayList = new ArrayList<d_MenuType>();
		Cursor localCursor = this.db.rawQuery(paramString, null);
		while (localCursor.moveToNext()) {
			d_MenuType locald_TypeName = new d_MenuType();
			locald_TypeName.setTypeId(localCursor.getString(0));
			locald_TypeName.setTypeName(localCursor.getString(1));
			locald_TypeName.setTypeParentId(localCursor.getString(2));
			localArrayList.add(locald_TypeName);
		}
		localCursor.close();
		return localArrayList;
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

	public void save(d_MenuType paramd_TypeName) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = paramd_TypeName.getTypeId();
		arrayOfObject[1] = paramd_TypeName.getTypeName();
		arrayOfObject[2] = paramd_TypeName.getTypeParentId();
		localSQLiteDatabase
				.execSQL(
						"INSERT INTO MenuType(TypeId,TypeName,TypeParentId) values(?,?,?)",
						arrayOfObject);
	}

	public void update(d_MenuType paramd_TypeName) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = paramd_TypeName.getTypeName();
		arrayOfObject[1] = paramd_TypeName.getTypeParentId();
		arrayOfObject[4] = paramd_TypeName.getTypeId();
		localSQLiteDatabase.execSQL(
				"update MenuType set TypeName=?,TypeParentId=? where TypeId=?",
				arrayOfObject);
	}
}
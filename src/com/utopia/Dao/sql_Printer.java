package com.utopia.Dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Printer;
import com.utopia.utils.Constant;

public class sql_Printer {
	Context context;
	SQLiteDatabase db;

	//初始化数据库
	public sql_Printer() {
		db = Constant.openDatabase();
	}

	sql_Printer(Context paramContext) {
		context = paramContext;
		if (db == null)
			db = openDatabase();
	}

	//打开数据库
	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	/**
	 * 检查是否可存
	 * @param param
	 */
	public void checksave(d_Printer param) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = param.getMac();
		arrayOfObject[1] = param.getAlias();
		arrayOfObject[2] = param.getId();
		db.beginTransaction();
		try {
			db.execSQL(
					"INSERT INTO Printer(mac,alias) select ?,? where not exists(select 1 from Printer where id=?)",
					arrayOfObject);
			db.setTransactionSuccessful();
		} catch (SQLException localSQLException) {
			throw localSQLException;
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * 清空数据表
	 */
	public void clear_all() {
		db.execSQL("delete from Printer");
	}

	/**
	 * 删除某个记录
	 * @param paramString
	 */
	public void delete(String paramString) {
		db.execSQL("delete from Printer where id=?",
				new Object[] { paramString });
	}

	/**
	 * 返回所有记录值
	 * @param paramString
	 * @return
	 */
	public ArrayList<d_Printer> recordlist(String paramString) {
		d_Printer printer;
		ArrayList<d_Printer> localArrayList = new ArrayList<d_Printer>();
		Cursor localCursor = db.rawQuery("select * from Printer order by id",
				null);
		while (localCursor.moveToNext()) {
			printer = new d_Printer();
			printer.setMac(localCursor.getString(localCursor.getColumnIndex("mac")));
			printer.setAlias(localCursor.getString(localCursor.getColumnIndex("alias")));
			localArrayList.add(printer);
		}
		localCursor.close();
		return localArrayList;
	}

	/**
	 * 自定义查询条件
	 * @param paramString
	 * @return
	 */
	public ArrayList<String> recordlist2(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		Cursor localCursor = db.rawQuery(paramString, null);
		while (localCursor.moveToNext()) {
			localArrayList.add(localCursor.getString(0));
		}
		localCursor.close();
		return localArrayList;
	}

	/**
	 * 插入数据
	 * @param d_Printer
	 */
	public void save(d_Printer param) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = param.getMac();
		arrayOfObject[1] = param.getAlias();
		db.execSQL(
				"INSERT INTO Printer(mac,alias) values(?,?)", arrayOfObject);
	}

	/**
	 * 更新数据库
	 * @param paramd_Area
	 * @param paramString1
	 * @param paramString2
	 */
	public void update(d_Printer param, String paramString1,
			String paramString2) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = paramString1;
		arrayOfObject[1] = paramString2;
		arrayOfObject[2] = param.getId();
		db.execSQL(
				"update Printer set mac=?,alias=? where id=?", arrayOfObject);
	}
}
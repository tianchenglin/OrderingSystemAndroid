package com.utopia.Dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase; 

import com.utopia.Model.d_Desk;
import com.utopia.Model.d_Product;
import com.utopia.utils.Constant;

public class sql_desk {
	Context context;
	SQLiteDatabase db = Constant.openDatabase();

	public sql_desk(Context context) {
		this.context = context;
	}

	public sql_desk() {
	}

	public void save(d_Desk paramd_Desk) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[12];
		arrayOfObject[0] = paramd_Desk.getId();
		arrayOfObject[1] = paramd_Desk.getType_id();
		arrayOfObject[2] = paramd_Desk.getState();
		arrayOfObject[3] = paramd_Desk.getS_account();
		arrayOfObject[4] = paramd_Desk.getDesk_name();
		arrayOfObject[5] = paramd_Desk.getStatetime();
		arrayOfObject[6] = paramd_Desk.getStarttime();
		arrayOfObject[7] = paramd_Desk.getPeople_num();
		arrayOfObject[8] = paramd_Desk.getRow();
		arrayOfObject[9] = paramd_Desk.getCol();
		arrayOfObject[10] = paramd_Desk.getDelmark();
		arrayOfObject[11] = paramd_Desk.getMessage();

		localSQLiteDatabase.beginTransaction();
		try {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO desk(id, type_id, state, s_account,desk_name,statetime, starttime, people_num, row, col, delmark,message) values(?,?,?,?,?,?,?,?,?,?,?,?)",
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
		this.db.execSQL("delete from desk");
	}

	public String select() {
		String table = "Select Tables";
		Cursor c = db.rawQuery("select *from desk where state!='EMPTY'", null);

		c.close();
		return table;
	}

	/**
	 * 开桌
	 * 
	 * @param paramd_Desk
	 */
	public void opendesk(d_Desk paramd_Desk) {
		Object[] arrayOfObject = new Object[6];
		arrayOfObject[0] = paramd_Desk.getState();
		arrayOfObject[1] = paramd_Desk.getPeople_num();
		arrayOfObject[2] = paramd_Desk.getS_account();
		arrayOfObject[3] = paramd_Desk.getStarttime();
		arrayOfObject[4] = paramd_Desk.getMessage();
		arrayOfObject[5] = paramd_Desk.getDesk_name();
		db.execSQL(
				"update desk set state=?,people_num=?,s_account=?,starttime=?,message=? where desk_name=?",
				arrayOfObject);
		// Log.e("sql_desk", paramd_Desk.getMessage()+"");
	}

	public Cursor recordlist2(String paramString) {
		Cursor c = db.rawQuery("select * from desk order by id", null);
		db.close();
		return c;
	}

	public Cursor recordlist(String paramString) {
		Cursor c = db.rawQuery(paramString, null);
		// while(c.moveToNext()){
		// Log.e("sql_desk", c.getString(c.getColumnIndex("type_id")));
		// }
		// c.close();
		return this.db.rawQuery(paramString, null);
	}

	public ArrayList<String> recordlist4(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		Cursor localCursor = this.db.rawQuery(paramString, null);
		while (localCursor.moveToNext()) {
			localArrayList.add(localCursor.getString(0));
		}

		return localArrayList;
	}

	public void update(String status, String desk_name) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = status;
		arrayOfObject[1] = desk_name;
		localSQLiteDatabase.execSQL(
				"update desk set state=? where desk_name=?", arrayOfObject);
	}

	public Cursor select(String desk_name) {
		return this.db.rawQuery("select * from desk where desk_name='"
				+ desk_name + "'", null);

	}

	public List<d_Desk> queryMenus(String area) {

		ArrayList<d_Desk> localArrayList = new ArrayList<d_Desk>();
		Cursor localCursor = this.db
				.rawQuery(
						"select d.id,d.type_id,d.state,d.s_account,d.desk_name,d.statetime,d.starttime,d.people_num,d.row,d.col,d.message from desk as d JOIN Area as a ON trim(a.AreaId) = trim(d.type_id) where a.AreaName='"
								+ area + "' order by d.row,d.col", null);
		while (localCursor.moveToNext()) {
			d_Desk locald_Desk = new d_Desk();
			locald_Desk.setId(localCursor.getInt(localCursor
					.getColumnIndex("id")));
			locald_Desk.setType_id(localCursor.getString(localCursor
					.getColumnIndex("type_id")));
			locald_Desk.setState(localCursor.getString(localCursor
					.getColumnIndex("state")));
			locald_Desk.setS_account(localCursor.getString(localCursor
					.getColumnIndex("s_account")));
			locald_Desk.setDesk_name(localCursor.getString(localCursor
					.getColumnIndex("desk_name")));
			locald_Desk.setStatetime(Integer.parseInt(localCursor.getString(localCursor
					.getColumnIndex("statetime"))));
			locald_Desk.setStarttime(localCursor.getString(localCursor.getColumnIndex("starttime")));
			locald_Desk.setPeople_num(localCursor.getInt(localCursor
					.getColumnIndex("people_num")));
			locald_Desk.setRow(localCursor.getInt(localCursor
					.getColumnIndex("row")));
			locald_Desk.setCol(localCursor.getInt(localCursor
					.getColumnIndex("col")));
			locald_Desk.setMessage(localCursor.getInt(localCursor
					.getColumnIndex("message")));
			localArrayList.add(locald_Desk);
		}
		localCursor.close();
		return localArrayList;
	}

}
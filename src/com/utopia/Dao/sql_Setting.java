package com.utopia.Dao;

import com.utopia.Model.d_Setting;
import com.utopia.utils.Constant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class sql_Setting {

	Context context;
	SQLiteDatabase db;

	public sql_Setting() {
		this.db = Constant.openDatabase();
	}

	public sql_Setting(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void save(d_Setting paramd_Setting) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[8];
		arrayOfObject[0] = paramd_Setting.getServerip();
		arrayOfObject[3] = paramd_Setting.getSerialNumber();
		arrayOfObject[1] = paramd_Setting.getGraphicalOrder();
		arrayOfObject[2] = paramd_Setting.getListOrder();
		arrayOfObject[4] = paramd_Setting.getCodeOrder();
		arrayOfObject[5] = paramd_Setting.getFastOrder();
		arrayOfObject[6] = paramd_Setting.getCustom();
		arrayOfObject[7] = paramd_Setting.getWaiter();
		paramd_Setting.setSettingId("1");
		if (paramd_Setting.getSettingId() != null) {
			localSQLiteDatabase
					.execSQL(
							"UPDATE Setting SET serverip=? ,serialNumber=? , GraphicalOrder = ? , ListOrder = ? , CodeOrder = ? , FastOrder = ? , Custom = ? , Waiter = ? WHERE SettingId=?",
							new Object[] { arrayOfObject[0], arrayOfObject[3],
									arrayOfObject[1], arrayOfObject[2],
									arrayOfObject[4], arrayOfObject[5],
									arrayOfObject[6], arrayOfObject[7],
									paramd_Setting.getSettingId() });
		} else {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO Setting(serverip,serialNumber,GraphicalOrder,ListOrder,CodeOrder,FastOrder,Custom,Waiter) values(?,?,?,?,?,?,?,?)",
							arrayOfObject);
		}

	}

	public void updateIP(String ip) {
		db.execSQL("UPDATE Setting SET serverip=?", new Object[] { ip });
	}

	public d_Setting getDate() {
		d_Setting mySetting = new d_Setting();

		String padName = null;
		String serverip = null;
		// SQLiteDatabase localSQLiteDatabase = this.db;
		Cursor cursor = db.query("Setting", null, null, null, null, null, null);
		// Log.e("cursor", cursor.toString());
		if (cursor.moveToNext()) {
			serverip = cursor.getString(cursor.getColumnIndex("serverip")); //
			padName = cursor.getString(cursor.getColumnIndex("serialNumber"));//

			// 读出的 0 和 1 是以字符串读出 ， 不能直接转换为Boolean.parseBoolean
			// 只有Boolean.parseBoolean（String s） ， 中的 s 是true或者false时 ，才可以转换成功

			Boolean gOrder; // =
							// Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("GraphicalOrder")));
			if (cursor.getString(cursor.getColumnIndex("GraphicalOrder"))
					.equals("1")) {
				gOrder = true;
			} else
				gOrder = false;
			Boolean lOrder;// =
							// Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("ListOrder")));
			if (cursor.getString(cursor.getColumnIndex("ListOrder"))
					.equals("1")) {
				lOrder = true;
			} else
				lOrder = false;
			Boolean cOrder;// =
							// Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("CodeOrder")));
			if (cursor.getString(cursor.getColumnIndex("CodeOrder"))
					.equals("1")) {
				cOrder = true;
			} else
				cOrder = false;
			Boolean fOrder = Boolean.parseBoolean(cursor.getString(cursor
					.getColumnIndex("FastOrder")));
			if (cursor.getString(cursor.getColumnIndex("FastOrder"))
					.equals("1")) {
				fOrder = true;
			} else
				fOrder = false;
			Boolean custom = Boolean.parseBoolean(cursor.getString(cursor
					.getColumnIndex("Custom")));
			if (cursor.getString(cursor.getColumnIndex("Custom")).equals("1")) {
				custom = true;
			} else
				custom = false;
			Boolean waiter = Boolean.parseBoolean(cursor.getString(cursor
					.getColumnIndex("Waiter")));
			if (cursor.getString(cursor.getColumnIndex("Waiter")).equals("1")) {
				waiter = true;
			} else
				waiter = false;

			mySetting.setServerip(serverip);
			mySetting.setSerialNumber(padName);
			mySetting.setGraphicalOrder(gOrder);
			mySetting.setListOrder(lOrder);
			mySetting.setFastOrder(fOrder);
			mySetting.setCodeOrder(cOrder);
			mySetting.setCustom(custom);
			mySetting.setWaiter(waiter);
		}

		return mySetting;
	}

	public String getServerIP() {
		String serverip = "";
		Cursor cursor = db.query("Setting", null, null, null, null, null, null);
		if (cursor.moveToNext()) {
			serverip = cursor.getString(cursor.getColumnIndex("serverip")); //
		}
		return serverip;
	}

}

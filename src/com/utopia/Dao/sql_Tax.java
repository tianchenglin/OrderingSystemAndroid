package com.utopia.Dao;

import java.util.ArrayList;
import java.util.List;

import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Tax;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class sql_Tax {
	Context context;
	SQLiteDatabase db;

	public sql_Tax() {
		this.db = Constant.openDatabase();
	}

	public sql_Tax(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void save(d_Tax localTax) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = localTax.getTax_id();
		arrayOfObject[1] = localTax.getTax_name();
		arrayOfObject[2] = localTax.getTax_value();
		db.execSQL(
				"INSERT INTO Tax(tax_id , tax_name , tax_value) values(?,?,?)",
				arrayOfObject);

	}

	public List<d_Tax> select() {
		List<d_Tax> taxs = new ArrayList<d_Tax>();
		d_Tax tax = new d_Tax();
		Cursor cursor = db.rawQuery("select * from Tax", null);
		while (cursor.moveToNext()) {
			tax = new d_Tax(Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("tax_id"))), cursor.getString(cursor
					.getColumnIndex("tax_name")), Double.parseDouble(cursor
					.getString(cursor.getColumnIndex("tax_value"))));
			taxs.add(tax);
		}
		cursor.close();
		return taxs;
	}

	public void clear_all() {
		this.db.execSQL("delete from Tax");
	}
}

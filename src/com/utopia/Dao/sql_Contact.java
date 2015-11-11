package com.utopia.Dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utopia.Model.d_Area;
import com.utopia.Model.d_Contact;
import com.utopia.utils.Constant;

public class sql_Contact {
	Context context;
	SQLiteDatabase db;

	public sql_Contact() {
		this.db = Constant.openDatabase();
	}

	public sql_Contact(Context paramContext) {
		this.context = paramContext;
		if (this.db != null)
			return;
		this.db = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(
				Constant.getDatabaseFilename(), null);
	}

	public void save(d_Contact contact) {
		String sql = " insert into Contact(Name,Phone,Add_Number,Add_Street,Add_Apt,Add_City,Add_State,Add_Code,Card_Number,Card_Date,Card_Cvv,Card_Fname,Card_Lname,Be_Notes,Not_Note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql,
				new Object[] { contact.getName(),
				contact.getPhone(),contact.getAdd_Number(),contact.getAdd_Street(),contact.getAdd_Apt(),contact.getAdd_City(),contact.getAdd_State(),contact.getAdd_Code(),
				contact.getCard_Number(),contact.getCard_Date(),contact.getCard_Cvv(),contact.getCard_Fname(),contact.getCard_Lname(),contact.getBe_Notes(),contact.getNot_Notes() });
	}
	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<d_Contact> getAllContact() throws SQLException {
		List<d_Contact> contacts = new ArrayList<d_Contact>();
		d_Contact contact = new d_Contact();
		String sql = "select * from Contact";
		Cursor localCursor = this.db.rawQuery(sql, null);
		while (localCursor.moveToNext()) {
			contact = new d_Contact(localCursor.getInt(localCursor.getColumnIndex("id")),localCursor.getString(localCursor.getColumnIndex("Name")), localCursor.getString(localCursor.getColumnIndex("Phone")), localCursor.getString(localCursor.getColumnIndex("Add_Number")) ,
					localCursor.getString(localCursor.getColumnIndex("Add_Street")) , localCursor.getString(localCursor.getColumnIndex("Add_Apt") ), localCursor.getString(localCursor.getColumnIndex("Add_City")) ,
					localCursor.getString(localCursor.getColumnIndex("Add_State")) , localCursor.getString(localCursor.getColumnIndex("Add_Code") ), localCursor.getString(localCursor.getColumnIndex("Card_Number") ),
					localCursor.getString(localCursor.getColumnIndex("Card_Date")) , localCursor.getString(localCursor.getColumnIndex("Card_Cvv") ), localCursor.getString(localCursor.getColumnIndex("Card_Fname") ),
					localCursor.getString(localCursor.getColumnIndex("Card_Lname") ), localCursor.getString(localCursor.getColumnIndex("Be_Notes")) , localCursor.getString(localCursor.getColumnIndex("Not_Note")) );
			contacts.add(contact);
		}
		localCursor.close();
		return contacts;
	}

	public void clear_all() {
		this.db.execSQL("delete from Contact");
	}
}

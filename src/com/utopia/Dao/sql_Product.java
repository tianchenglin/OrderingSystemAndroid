package com.utopia.Dao;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase; 

import com.utopia.Model.d_Product;
import com.utopia.utils.Constant;

public class sql_Product {
	Context context;
	SQLiteDatabase db = Constant.openDatabase();
	DecimalFormat df = new DecimalFormat("0.00");
    
    
	public sql_Product(Context context) {
		this.context = context;
		df.setRoundingMode(RoundingMode.HALF_UP);
	}

	public sql_Product() {
		df.setRoundingMode(RoundingMode.HALF_UP);
	}

	public void checksave(d_Product paramd_Product) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[9];
		arrayOfObject[0] = paramd_Product.getPdtID();
		arrayOfObject[1] = paramd_Product.getPdtCode();
		arrayOfObject[2] = paramd_Product.getPdtName();
		arrayOfObject[3] = paramd_Product.getPdtUnit();
		arrayOfObject[4] = paramd_Product.getPdtSalePrice1();
		arrayOfObject[5] = paramd_Product.getDepartId();
		arrayOfObject[6] = paramd_Product.getTypeId();
		arrayOfObject[7] = paramd_Product.getPdtPy();
		arrayOfObject[8] = paramd_Product.getPdtCode();
		localSQLiteDatabase.beginTransaction();
		try {
			localSQLiteDatabase
					.execSQL(
							"INSERT INTO Product(PdtID,PdtCode,PdtName,PdtUnit,PdtSalePrice1,DepartId,Typeid,PdtPy) select ?,?,?,?,?,?,?,? where not exists(select 1 from Product where PdtCode=?)",
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
		this.db.execSQL("delete from Product");
	}

	public void delete(String paramString) {
		this.db.execSQL("delete from Product where PdtCode=?",
				new Object[] { paramString });
	}

	public ArrayList<d_Product> recordlist(String paramString) {
		ArrayList<d_Product> localArrayList = new ArrayList<d_Product>();
		Cursor localCursor = this.db.rawQuery(
				"select * from Product order by PdtCode", null);
		while (localCursor.moveToNext()) {
			d_Product locald_Product = new d_Product();
			locald_Product.setPdtID(localCursor.getString(0));
			locald_Product.setPdtCode(localCursor.getString(1));
			locald_Product.setPdtName(localCursor.getString(2));
			locald_Product.setPdtUnit(localCursor.getString(3));
			locald_Product.setPdtSalePrice1(localCursor.getFloat(4));
			locald_Product.setPdtSalePrice2(localCursor.getFloat(5));
			localArrayList.add(locald_Product);
		}
		localCursor.close();
		return localArrayList;
	}

	public ArrayList<d_Product> queryMenus(String TypeId) {
		ArrayList<d_Product> localArrayList = new ArrayList<d_Product>();
		Cursor localCursor = this.db.rawQuery(
				"select * from Product  where TypeId='" + TypeId
						+ "' order by PdtCode", null);
		while (localCursor.moveToNext()) {
			d_Product locald_Product = new d_Product();
			locald_Product.setPdtID(localCursor.getString(localCursor.getColumnIndex("PdtID")));
			locald_Product.setPdtCode(localCursor.getString(localCursor.getColumnIndex("PdtCode")));
			locald_Product.setPdtName(localCursor.getString(localCursor.getColumnIndex("PdtName")));
			locald_Product.setPdtPy(localCursor.getString(localCursor.getColumnIndex("PdtPy")));
			locald_Product.setPdtUnit(localCursor.getString(localCursor.getColumnIndex("PdtUnit")));
			locald_Product.setPdtSalePrice1(localCursor.getFloat(localCursor.getColumnIndex("PdtSalePrice1")));
			locald_Product.setPdtSalePrice2(localCursor.getFloat(localCursor.getColumnIndex("PdtSalePrice2")));
			localArrayList.add(locald_Product);
		}
		localCursor.close();
		return localArrayList;
	}

	public Cursor recordlist2(String paramString) {
		return this.db.rawQuery("select * from Product order by PdtCode", null);
	}

	public Cursor recordlist3(String paramString) {
		
		return this.db.rawQuery(paramString, null);
	}

	public void save(d_Product paramd_Product) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[29];
		arrayOfObject[0] = paramd_Product.getDepartId();
		arrayOfObject[1] = paramd_Product.getPdtAccType();
		arrayOfObject[2] = paramd_Product.getPdtAutoInc();
		arrayOfObject[3] = paramd_Product.getPdtCanUsed();
		arrayOfObject[4] = paramd_Product.getPdtCanZk();
		arrayOfObject[5] = paramd_Product.getPdtChangePrice();
		arrayOfObject[6] = paramd_Product.getPdtCode();
		arrayOfObject[7] = paramd_Product.getPdtGg();
		arrayOfObject[8] = paramd_Product.getPdtID();
		arrayOfObject[9] = paramd_Product.getPdtInMix();
		
		arrayOfObject[10] = paramd_Product.getPdtMCode();
		arrayOfObject[11] = paramd_Product.getPdtMakeTime();
		arrayOfObject[12] = paramd_Product.getPdtName();
		arrayOfObject[13] = paramd_Product.getPdtNoShow();
		arrayOfObject[14] = paramd_Product.getPdtPayType();
		arrayOfObject[15] = paramd_Product.getPdtPy();
		
		arrayOfObject[16] = df.format(paramd_Product.getPdtSalePrice1());
		
		arrayOfObject[17] = paramd_Product.getPdtSalePrice2();
		arrayOfObject[18] = paramd_Product.getPdtUnit();
		arrayOfObject[19] = paramd_Product.getPdtisSet();
		arrayOfObject[20] = paramd_Product.getTypeId();
		arrayOfObject[21] = paramd_Product.getPdtminrebate();
		arrayOfObject[22] = paramd_Product.getnotout();
		arrayOfObject[23] = paramd_Product.getnotshow();
		arrayOfObject[24] = paramd_Product.getnotshowonbill();
		arrayOfObject[25] = paramd_Product.getpdtchgNumber();
		arrayOfObject[26] = paramd_Product.getpdtdownprice1();
		arrayOfObject[27] = paramd_Product.getpdtdownprice2();
		arrayOfObject[28] = paramd_Product.getPdtInPrice();
		
		localSQLiteDatabase
				.execSQL(
						"INSERT INTO Product(departId, pdtAccType, pdtAutoInc,pdtCanUsed, pdtCanZk, pdtChangePrice,"
								+ "pdtCode, pdtGg, pdtID, pdtInMix, pdtMCode, pdtMakeTime,pdtName, pdtNoShow, pdtPayType, pdtPy, pdtSalePrice1, pdtSalePrice2, pdtUnit,"
								+ " pdtisSet, typeId, minrebate, notout, notshow,  notshowonbill,  pdtchgNumber, pdtdownprice1,  pdtdownprice2,pdtInPrice) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						arrayOfObject);
	}

	public void update(d_Product paramd_Product) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = paramd_Product.getPdtName();
		arrayOfObject[1] = paramd_Product.getPdtUnit();
		arrayOfObject[2] = paramd_Product.getPdtSalePrice1();
		arrayOfObject[3] = paramd_Product.getPdtSalePrice2();
		arrayOfObject[4] = paramd_Product.getPdtCode();
		localSQLiteDatabase
				.execSQL(
						"update Product set PdtName=?,PdtUnit=?,PdtSalePrice1=?,PdtSalePrice2=? where PdtCode=?",
						arrayOfObject);
	}
}
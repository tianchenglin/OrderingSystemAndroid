package com.utopia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;

import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Staff;
import com.utopia.activity.R;

public class Constant {
	
	public static d_Staff currentStaff;//记录当前登录的员工信息
	
	public static String DATABASE_FILENAME = "food.db";//记录数据库名称
	public static String DATABASE_PATH = "";//记录数据库路径
	public static SQLiteDatabase db;//数据库
	public static String table_id;//y记录当前桌号
	public static String lastTime = "2015-01-01 00:00:00";//记录数据库中最后更新时间
	public static int versionCode = 0;//服务器端版本
	public static View currentView = null;//当前编辑的view
	public static String DataTime;
	public static String NewDataTime;
	public static String NewPicTime;
	public static String PicTime;
	public static boolean b_autoup;
	public static boolean b_updata;
	public static boolean b_uppic;
	public static String bill_id;
	
	
	public static String desk_name;
	public static int dlg_logintype;
	public static float foodnumcount;
	public static Handler foodnumhandler;
	public static Boolean list_type;
	public static Context maincontext;
	public static int mainmgindex;
	public static boolean mainmgpic;
	public static String managerId = null;
	public static Boolean pad_type;
	public static String sendcallid;
	public static String sysPromo;
	public static Handler sysPromohandler;
	public static String sysPromoid;
	public static String sysboss;
	public static String syspass;
	public static String sysuserId;
	public static String sysusername;
	
	public static String printerAddress;
	public static String userCode; // 操作者Id
	public static String cashierId; // 收银机id
	public static float sumTotal; // 打印机 费用合计
	public static float tip ; //小费
	
	public static String schedule="";//记录桌子的所有菜发送到厨房的时间
	public static float due ; 
	public static float paid ; 
	public static String Area ; //记录不同区域 ， tables  liquor bar ， sushi bar， take out， delivery
	public static DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	
	public static d_SaleRecord currentSR ; 
	public static String clockInTime = "2015-01-01 00:00:00";

	//public static List<d_SaleRecord> allSaleRecord;
	public static List<d_SaleRecord> billSaleRecord ; 
	public static List<d_SaleRecord> allBill ; 
	public static int position  ;
    
	public static boolean pop = false; 
	static {
		position = 0  ;
		//allSaleRecord = new ArrayList<d_SaleRecord>();
		billSaleRecord  = new ArrayList<d_SaleRecord>();
		allBill = new ArrayList<d_SaleRecord>();
		pad_type = Boolean.valueOf(false);
		list_type = Boolean.valueOf(false);
		DataTime = "";
		NewDataTime = "";
		PicTime = "";
		NewPicTime = "";
		dlg_logintype = 0;
		sendcallid = "";
		table_id = "Select tables";
		bill_id = "";
		desk_name = "";
		sysuserId = "";
		sysusername = "";
		syspass = "";
		sysboss = "";
		b_updata = false;
		b_uppic = false;
		b_autoup = false;
		sysPromo = "";
		sysPromoid = "";
		sysPromohandler = null;
		mainmgindex = 0;
		mainmgpic = false;
		foodnumhandler = null;
		foodnumcount = 0.0F;
		DATABASE_FILENAME = "food.db";
		new StringBuilder(String.valueOf(DATABASE_PATH)).append("/");
		userCode = "6666";
		cashierId = "cashier1";
		sumTotal = (float) 0.0;
		tip = (float) 0.0;
		due = (float) 0.0;
		paid = (float) 0.0;
		printerAddress = "";
		Area = "Tables";
		
	}

	private void CreateFromRawDbFiles(File[] paramArrayOfFile,
			FileOutputStream paramFileOutputStream) {
		int i = 0;
		int j = paramArrayOfFile.length;
		try {
			while (i < j && paramArrayOfFile[i] != null) {
				FileInputStream localFileInputStream = new FileInputStream(
						paramArrayOfFile[i]);
				byte[] arrayOfByte = new byte[localFileInputStream.available()];
				paramFileOutputStream.write(arrayOfByte, 0,
						localFileInputStream.read(arrayOfByte));
				localFileInputStream.close();
				i++;
			}
			paramFileOutputStream.close();
		} catch (IOException localIOException) {
		}
	}

	private void copydb(Context paramContext, int paramInt1,
			String paramString, int paramInt2) {
		try {
			InputStream localInputStream = paramContext.getResources()
					.openRawResource(paramInt1);
			FileOutputStream localFileOutputStream = new FileOutputStream(
					paramString);
			byte[] arrayOfByte = new byte[paramInt2];
			int i = localInputStream.read(arrayOfByte);
			if (i > 0)
				localFileOutputStream.write(arrayOfByte, 0, i);
			localFileOutputStream.close();
			localInputStream.close();
			return;
		} catch (Exception localException) {
		}
	}

	private void deleteFile(File paramFile) {
		if (paramFile.isDirectory()) {
			File[] arrayOfFile = paramFile.listFiles();
			if (arrayOfFile.length >= 0)
				paramFile.delete();
			int i = arrayOfFile.length;
			for (int j = 0;; j++) {
				if (j >= i)
					return;
				File localFile = arrayOfFile[j];
				if (localFile.isFile())
					localFile.delete();
			}
		}
		paramFile.delete();
	}

	public static String getDatabaseFilename() {
		return new StringBuilder(String.valueOf(DATABASE_PATH)).append("/")
				.toString() + DATABASE_FILENAME;
	}

	public static SQLiteDatabase openDatabase() {
		if (db == null)
			db = SQLiteDatabase.openOrCreateDatabase(getDatabaseFilename(),
					null);
		return db;
	}

	public static BitmapDrawable readBitMapDrawable(Context paramContext,
			int paramInt) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		localOptions.inPurgeable = true;
		localOptions.inInputShareable = true;
		localOptions.inSampleSize = 1;
		return new BitmapDrawable(BitmapFactory.decodeStream(paramContext
				.getResources().openRawResource(paramInt), null, localOptions));
	}

	public static BitmapDrawable readBitMapFile(Context paramContext,
			String paramString) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		localOptions.inPurgeable = true;
		localOptions.inInputShareable = true;
		localOptions.inSampleSize = 1;
		return new BitmapDrawable(BitmapFactory.decodeFile(paramString,
				localOptions));
	}

	public void copy(Context paramContext) {
		try {
			File localFile = new File(DATABASE_PATH);
			if (!localFile.exists())
				localFile.mkdir();
			if (!new File(getDatabaseFilename()).exists()) {
				copydb(paramContext, R.raw.food, String.valueOf(DATABASE_PATH)
						+ "/food", 125952);
				File[] arrayOfFile = new File[12];
				arrayOfFile[0] = new File(String.valueOf(DATABASE_PATH)
						+ "/food");
				CreateFromRawDbFiles(arrayOfFile, new FileOutputStream(
						getDatabaseFilename()));
			}
			return;
		} catch (Exception localException) {
		}
	}

	public void recopy(Context paramContext) {
		try {
			if (db != null) {
				db.close();
				db = null;
				paramContext.deleteDatabase(getDatabaseFilename());
			}
			deleteFile(new File(DATABASE_PATH));
			copy(paramContext);
		} catch (Exception localException) {
		}
	}
}

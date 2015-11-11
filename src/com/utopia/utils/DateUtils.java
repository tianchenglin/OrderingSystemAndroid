package com.utopia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class DateUtils {
	/**
	 * 获取yyyyMMdd格式日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date getDate(String time) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String formatDate(Context context, long date) {
		@SuppressWarnings("deprecation")
		int format_flags = android.text.format.DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| android.text.format.DateUtils.FORMAT_ABBREV_ALL
				| android.text.format.DateUtils.FORMAT_CAP_AMPM
				| android.text.format.DateUtils.FORMAT_SHOW_DATE
				| android.text.format.DateUtils.FORMAT_SHOW_DATE
				| android.text.format.DateUtils.FORMAT_SHOW_TIME;
		return android.text.format.DateUtils.formatDateTime(context, date,
				format_flags);
	}
	public static String getDateCN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日 23:41:31
	}
	
	public static int getHour(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
//	    int year = c.get(Calendar.YEAR); 
//	   int month = c.get(Calendar.MONTH); 
//	   int date = c.get(Calendar.DATE); 
	   int hour = c.get(Calendar.HOUR_OF_DAY); 
//	int minute = c.get(Calendar.MINUTE); 
//	int second = c.get(Calendar.SECOND); 
	return hour;
	}
	public static int getminute(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
	    int minute = c.get(Calendar.MINUTE); 
	return minute;
	}

	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}

	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date = format.format(new Date(System.currentTimeMillis()));
	
		return date;
	}

	public static String getDateWN() {
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy ");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;//  23:41:31 10-03-2012
	}
	
	public static String getTime() {
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;//  23:41:31 10-03-2012
	}
}

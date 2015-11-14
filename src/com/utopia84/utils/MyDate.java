package com.utopia84.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2014-10-03 23:41:31
	}
}
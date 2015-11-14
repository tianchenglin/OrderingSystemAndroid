package com.utopia84.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoString {
	/**
	 * 校正HTML里的符号
	 * @param source
	 * @return
	 */
	public static String HTMLChange(String source) {
		String changeStr = "";
		changeStr = source.replaceAll("&", "&amp;");
		changeStr = changeStr.replaceAll(" ", "&nbsp;");
		changeStr = changeStr.replaceAll("<", "&lt;");
		changeStr = changeStr.replaceAll(">", "&gt;");
		changeStr = changeStr.replaceAll("\r\n", "<br>");
		return changeStr;
	}
	/**
	 * Date转Sting
	 * @param source
	 * @return
	 */
	public static String dateTimeChange(Date source) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String changeTime = format.format(source);
		return changeTime;
	}
}

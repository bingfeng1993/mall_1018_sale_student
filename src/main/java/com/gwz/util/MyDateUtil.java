package com.gwz.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {

	public static void main(String[] args) {
		//关于日期的格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
		String format = sdf.format(new Date());
		System.out.println(format);
		
		//关于日期的计算
		
	}
	
	//关于日期的计算
	public static Date getMyDate(int i) {
		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, i);

		return c.getTime();
	}
	//关于日期的格式化
	public static String getMyDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

		String format = sdf.format(new Date());
		return format;
	}

}

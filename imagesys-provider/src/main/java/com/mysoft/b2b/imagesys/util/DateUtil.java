package com.mysoft.b2b.imagesys.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public static String getNow(){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public static String getTimeString(Long date){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
}

package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtils {
	
	private static Logger log = Logger.getLogger(DateUtils.class);
	
	/**
	 * @param Date 
	 * @return String 
	 * @DESC 返回格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static String currentDate(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	/**
	 * @param dateStr 字符串格式日期  yyyy-MM-dd HH:mm:ss
	 * @param patten 要转换成的目标日期格式
	 * @return patten
	 * */
	public static String formatStringDate2String(String dateStr, String patten){
		if(dateStr == null || "".equals(dateStr)){
			return "";
		}
		
		SimpleDateFormat format = new SimpleDateFormat(patten);
		try {
			return format.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
		} catch (ParseException e) {
			log.info("转换日期出错：源日期字符串"+dateStr);
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * @param dateStr 字符串格式日期  yyyy-MM-dd
	 * @param patten 要转换成的目标日期格式
	 * @return patten
	 * */
	public static String formatShortStringDate2String(String dateStr, String patten){
		if(dateStr == null || "".equals(dateStr)){
			return "";
		}
		
		SimpleDateFormat format = new SimpleDateFormat(patten);
		try {
			return format.format(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
		} catch (ParseException e) {
			log.info("转换日期出错：源日期字符串"+dateStr);
			e.printStackTrace();
		}
		return "";
	}

	public static String formatBirthdat2Age(String birthday){
		if(birthday == null || "".equals(birthday)){
			return "0";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date birthDate = sdf.parse(birthday);
			Date currentDate = new Date();
			int currentYear = currentDate.getYear();
			int currentMonth = currentDate.getMonth();
			int currentDay = currentDate.getDay();
			int bYear = birthDate.getYear();
			int bMonth = birthDate.getMonth();
			int bDay = birthDate.getDay();
			int age = currentYear - bYear;
			if(currentMonth == bMonth){
				if(currentDay < bDay){
					age = age -1 ;
				}
			}else if(currentMonth < bMonth){
				age = age -1 ;
			}
			if(age < 0){ age = 0 ;}
			return age+"";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}
	/**
	 * 传入yyyy-MM-dd HH:mm:ss格式日期字符串
	 * 返回Long毫秒数
	 * 异常时返回当前时间毫秒数
	 * */
	public static long getTimeMillis(String createDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(createDate).getTime();
		} catch (ParseException e) {
			
		}
		
		return System.currentTimeMillis();
	}
	public static void main(String[] args) {
		System.out.println(formatBirthdat2Age("20091117"));
	}
	
}

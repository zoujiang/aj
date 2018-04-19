package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		System.out.println(getLastDayOfMonth("2018-04"));
	}
	/**
	 * 获取当前时间最近N个月
	 * 返回yyyy-MM
	 * */
	public static List<String> getNPreMonth(int n){
		List<String> list = new ArrayList<String>();
		if(n <= 0){ return list;}
		String patten = "yyyy-MM";
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		Calendar c = Calendar.getInstance();
		list.add(sdf.format(c.getTimeInMillis()));
		System.out.println(sdf.format(c.getTimeInMillis()) );
		for(int i=1; i< n; i++){
			c.add(Calendar.MONTH, -1);
			list.add(sdf.format(c.getTimeInMillis()));
		}
		
		return list;
	}
	 /** 
     * 获取某月的最后一天 
     * @Title:getLastDayOfMonth 
     * @Description: 
     * @param:@param yearMonth  2018-05
     * @param:@return 
     * @return:String 
     * @throws 
     */  
    public static Long getLastDayOfMonth(String yearMonth)  
    {  
    	String[] ym = yearMonth.split("-");
        Calendar cal = Calendar.getInstance();  
        //设置年份  
        cal.set(Calendar.YEAR, Integer.parseInt(ym[0]));  
        //设置月份  
        cal.set(Calendar.MONTH, Integer.parseInt(ym[1])-1);  
        //获取某月最大天数  
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
        //设置日历中月份的最大天数  
        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
        //格式化日期  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String lastDayOfMonth = sdf.format(cal.getTime());  
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {
			return sdf2.parse(lastDayOfMonth+" 23:59:59").getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }  
}

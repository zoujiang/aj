package com.frame.core.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Corn表达式工具类
 */
public final class CronExpUtil {

	private static final SecureRandom numberGenerator = new SecureRandom();
	/**
	 * 生成TriggerID
	 * 
	 * @return 返回前缀加时间的ID字符串
	 */
	public static String generateID(String prefix) {
		String id = prefix;
		synchronized (numberGenerator) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			id += format.format(date);
		}
		return id;
	}
	/**
	 * 将日期时间转化为Corn表达式
	 * 
	 * @return Corn表达式
	 */
	public static String toCornExpr(String prefix) {
		// 2013-01-04 13:14:52       23:00-8:00
		// 52 14 13 04 01 ? 2014     10
		String corn = "";
		synchronized (numberGenerator) {
			String[] arr=prefix.trim().split(" ");
			String[] date=arr[0].split("-");
			String[] time=arr[1].split(":");
			corn =time[2]+" "+time[1]+" "+time[0]+" "+date[2]+" "+date[1]+" ? "+date[0];
		}
		return corn;
	}
	/**
	 * 离线Corn表达式
	 * @return Corn表达式
	 */
	public static String toLixianExpr(String prefix) {
		// 2013-01-04 13:14:52    	   	离线：22:00-24:00  0:00-8:00
		// 52 14 13 04 01 ? 2014     	在线：8:00-12:00   12:00-22:00
		String corn = "";
		synchronized (numberGenerator) {
			String[] arr=prefix.trim().split(" ");
			String[] date=arr[0].split("-");
			String[] time=arr[1].split(":");
			int h=Integer.parseInt(time[0]);
			if(h>=0 && h<=8){	//离线0:00-8:00 ,不延迟
				corn =time[2]+" "+time[1]+" "+time[0]+" "+date[2]+" "+date[1]+" ? "+date[0];
			}else if(h>8 && h<=12){	//在线8:00-12:00，延迟到晚上 20:00-24:00
				h=h+12;
				corn =time[2]+" "+time[1]+" "+h+" "+date[2]+" "+date[1]+" ? "+date[0];
			}else if(h>12 && h<=22){ //在线12:00-22:00，延迟到晚上 0:00-10:00
				h=h-12;
				int d=Integer.parseInt(date[1]);
				d=d+1;	//到第二天
				corn =time[2]+" "+time[1]+" "+h+" "+d+" "+date[1]+" ? "+date[0];
			}else if(h>22 && h<=24){//离线22:00-24:00，不延迟
				corn =time[2]+" "+time[1]+" "+h+" "+date[2]+" "+date[1]+" ? "+date[0];
			}
		}
		return corn;
	}
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(new Date());
		System.out.println(time);
		System.out.println(CronExpUtil.toLixianExpr(time));
	}
}
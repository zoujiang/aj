package com.frame.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * <p>
 * 日期工具类
 * </p>
 * 
 * <p>
 * Copyright: 版权所有 (c) 2002 - 2008<br>
 * Company: mymost
 * </p>
 * 
 * @author yl
 * @version 2011-6-29
 */
public class DateUtil {

	private final static String EMPTY = "";
	public final static String DEFAULT_PATTERN = "yyyy-MM-dd";

	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

	public final static String DATE_TIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";
	
	public final static String DATE_TIME_PATTERN3 = "yyyyMMdd";
	
	public final static String DATE_TIME_PATTERN4 = "yyyyMMddHHmmss";
	
	public final static String DATE_TIME_PATTERN5 = "HH:mm:ss";

	public final static String DEFAULT_PATTERN_CH = "yyyy年";
	/**
	 * 一天的毫秒数
	 */
	public final static long ONE_DATE_TIME = 24 * 60 * 60 * 1000l;

	/**
	 * 把long类型的日期转成指定格式pattern的字符串返回
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String dateFromat(long time, String pattern) {
		if (time > 0) {
			Date thisDate = new Date(time);
			String p = DEFAULT_PATTERN;
			if (CheckIsNull.isNotEmpty(pattern)) {
				p = pattern;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(p);
			return sdf.format(thisDate);
		}
		return EMPTY;
	}

	/**
	 * 把long类型的日期转成"yyyy-MM-dd"的字符串返回
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String dateFromat(long time) {
		if (time > 0) {
			Date thisDate = new Date(time);
			String p = DEFAULT_PATTERN;
			SimpleDateFormat sdf = new SimpleDateFormat(p);
			return sdf.format(thisDate);
		}
		return EMPTY;
	}

	/**
	 * 把long类型的日期转成"yyyy-MM-dd"的字符串返回
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String dateFromat(Date thisDate) {
		if (null != thisDate) {
			String p = DEFAULT_PATTERN;
			SimpleDateFormat sdf = new SimpleDateFormat(p);
			return sdf.format(thisDate);
		}
		return EMPTY;
	}

	/**
	 * 把long类型的日期转成"yyyy-MM-dd"的字符串返回
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String dateFromat(Date thisDate, String pattern) {
		if (thisDate != null) {
			String p = DEFAULT_PATTERN;
			if (CheckIsNull.isNotEmpty(pattern)) {
				p = pattern;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(p);
			return sdf.format(thisDate);
		}
		return EMPTY;
	}

	/**
	 * 新历转成农历日期
	 * 
	 * @return
	 */
	public static long new2OldDate(long newDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(newDate);
		Date time = ChineseCalendar.sCalendarSolarToLundar(cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal
				.get(Calendar.DAY_OF_MONTH));
		return time.getTime();
	}

	/**
	 * 农历转新历
	 * 
	 * @param oldDate
	 * @return
	 */
	public static long old2NewDate(long oldDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(oldDate);
		Date time = ChineseCalendar.sCalendarLundarToSolar(cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal
				.get(Calendar.DAY_OF_MONTH));
		return time.getTime();
	}
	
	/**
	 * 得到当前日志的timestamp串
	 *格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param oldDate
	 * @return
	 */
	public static Timestamp stringToTimestamp(String oldDate) {
        Format f = new SimpleDateFormat(DATE_TIME_PATTERN2);            
        Date d= new Date();
		try {
			d = (Date) f.parseObject(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}           
        Timestamp ts = new Timestamp(d.getTime());
		
		return ts;
	}
	/**
	 * 得到当前日志的timestamp串
	 *格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param oldDate
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date oldDate) {
        Timestamp ts = new Timestamp(oldDate.getTime());
		return ts;
	}

	/**
	 * 得到当前日志的timestamp串
	 *格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param oldDate
	 * @return
	 */
	public static Timestamp stringToTimestamp(String oldDate,String pattern) {
        Format f = new SimpleDateFormat(pattern);            
        Date d= new Date();
		try {
			d = (Date) f.parseObject(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}           
        Timestamp ts = new Timestamp(d.getTime());
		
		return ts;
	}
	/**
	 * 将形如2003-2-2的时间字符串转换为 Date对象
	 * 
	 * @param dateStr
	 * @return long
	 */
	public static Date convertStringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将形如2003-2-2的时间字符串转换为 Date对象，如果字符串格式不正确，返回当前时间对象
	 * 
	 * @param dateStr
	 * @return long
	 */
	public static Date convertStringToDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期取整函数<Br>
	 * 将指定日期从指定的时间单位处开始取整，如将2011-11-1 15:30 抹去时间 获得 2011-11-1 00:00
	 * 
	 * 
	 * @param time
	 * @param field
	 *            接受值为java.util.Calendar的常量 Calendar.DATE 从天数处开始取整
	 *            Calendar.MONTH 从月份处开始取整 2011-5-3 返回 2011-5-1 Calendar.YEAR
	 *            从年份处开始取整 Calendar.HOUR 从小时取整
	 * @return long
	 */
	public static long trunc(long time, int field) {
		String format = "yyyyMMdd 00:00";
		switch (field) {
		case Calendar.DATE:
			break;
		case Calendar.MONTH:
			format = "yyyyMM";
			break;
		case Calendar.YEAR:
			format = "yyyy";
			break;
		case Calendar.HOUR:
			format = "yyyyMMdd hh:00";
			break;
		default:
			throw new IllegalArgumentException("日期取整时参数错误：没有" + field
					+ "这种类型。常量值请参考java.util.Calendar");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(sdf.format(time)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}

	/**
	 * 将指定时间对象的小时分钟信息归零
	 * 
	 * @param time
	 * @return long
	 */
	public static long truncDay(long time) {
		return DateUtil.trunc(time, Calendar.DATE);
	}

	/**
	 * 得到指定日的起始时间（0时0分0秒）
	 * 
	 * @param date
	 * @return
	 */
	public static long getDayStartTime(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar.getTimeInMillis();
	}

	/**
	 * 得到指定日的结束时间（23时59分59秒...）
	 * 
	 * @param date
	 * @return
	 */
	public static long getDayEndTime(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar.getTimeInMillis() + 24 * 60 * 60 * 1000 - 1;
	}

	/**
	 * 获取本月第一天日期
	 * 
	 * @return
	 */
	public static Date getThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	/**
	 * 获取本月最后一天日期
	 * 
	 * @return
	 */
	public static Date getLastDayOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE,  - 1 );
		return cal.getTime();
	}

	/**
	 * 获取今天开始的时间
	 * 
	 * @return
	 */
	public static long getToday() {
		return truncDay(System.currentTimeMillis());
	}

	/**
	 * 判断两个日期是否跨年
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean checkIsNewYear(long beginDate, long endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(beginDate);
		int beginYear = cal.get(Calendar.YEAR);
		cal.setTimeInMillis(endDate);
		int endYear = cal.get(Calendar.YEAR);
		return beginYear != endYear;
	}

	/**
	 * 传入当前日期，格式是yyyy-mm-dd
	 * 
	 * @param date
	 * @return 返回传入日期是当前的今年某个月的某个星期 格式为:yyyymmww
	 */
	public static String getDayofWeekofMonth(String time) {
		// String time = "2011-12-02";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				/*
				 * System.out.println("本周期末时间： <=" + new
				 * SimpleDateFormat("yyyyMMdd").format(calendar .getTime()));
				 */
				/*
				 * calendar.set(Calendar.DAY_OF_YEAR, calendar
				 * .get(Calendar.DAY_OF_YEAR) - 7); System.out.println("本周期初时间：<=" +
				 * new SimpleDateFormat("yyyyMMdd").format(calendar
				 * .getTime()));
				 */
				return new SimpleDateFormat("yyyyMMdd").format(calendar
						.getTime());
			} else {
				while (calendar.get(Calendar.DAY_OF_WEEK) < 7) {
					calendar.set(Calendar.DAY_OF_WEEK, calendar
							.get(Calendar.DAY_OF_WEEK) + 1);
				}
				calendar.set(Calendar.DAY_OF_YEAR, calendar
						.get(Calendar.DAY_OF_YEAR) + 1);
				/*
				 * System.out.println("本周期末时间： <=" + new
				 * SimpleDateFormat("yyyyMMdd").format(calendar .getTime()));
				 * calendar.set(Calendar.DAY_OF_YEAR, calendar
				 * .get(Calendar.DAY_OF_YEAR) - 7); System.out.println("本周期初时间：<=" +
				 * new SimpleDateFormat("yyyyMMdd").format(calendar
				 * .getTime()));
				 */
				return new SimpleDateFormat("yyyyMMdd").format(calendar
						.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;

		/*
		 * Calendar c_now = new GregorianCalendar(); Calendar c_begin = new
		 * GregorianCalendar(); Calendar c_end = new GregorianCalendar();
		 * DateFormatSymbols dfs = new DateFormatSymbols(); SimpleDateFormat df =
		 * new SimpleDateFormat("yyyy-M-dd"); java.util.Date cDate = null; try {
		 * cDate = df.parse(date); } catch (ParseException e) {
		 * e.printStackTrace(); } c_now.setTime(cDate); int year =
		 * c_now.get(Calendar.YEAR); int month = c_now.get(Calendar.MONTH) + 1;
		 * int today = c_now.get(Calendar.DAY_OF_MONTH);
		 * System.out.println(today); int[] days = { 0, 31, 28, 31, 30, 31, 30,
		 * 31, 31, 30, 31, 30, 31 }; if (year % 4 == 0) { days[2] = 29;// 大年 }
		 * c_begin.set(2010, month - 1, 1); // 月 0-11 天 0- c_end.set(2010, month -
		 * 1, days[month]);
		 * 
		 * int count = 1; c_end.add(Calendar.DAY_OF_YEAR, 1); //
		 * 结束日期下滚一天是为了包含最后一天 int day = c_now.get(Calendar.DAY_OF_WEEK) - 1;
		 * while (c_begin.before(c_end)) { if (day == 8) { count++; day = 1; }
		 * 
		 * if (today == c_begin.get(Calendar.DAY_OF_MONTH)) {
		 * //System.out.println(df.format(c_begin.getTime()) + "是" + month //+
		 * "月第" + count + "周的第" + day + "天"); String returnmonth =
		 * (c_begin.get(Calendar.YEAR)+1) + "" +
		 * (c_begin.get(Calendar.MONTH)+1); Date date2; String returnvalue = "";
		 * try { date2 = new SimpleDateFormat("yyyyMM").parse(returnmonth);
		 * returnvalue = new SimpleDateFormat("yyyyMM").format(date2)+ "0" +
		 * count; } catch (ParseException e) { e.printStackTrace(); } return
		 * returnvalue ; } day++; // System.out.println("第"+count+"周 日期："+new //
		 * java.sql.Date(c_begin. getTime().getTime())+",
		 * "+weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
		 * 
		 * c_begin.add(Calendar.DAY_OF_YEAR, 1); }
		 * //System.out.println("共计（跨越）：" + (count) + "周"); return null;
		 */
	}

	/**
	 * 
	 * @param date
	 *            当前日期
	 * @return 得到当前月日期所属季度
	 */
	public static int getCurrQuarter(String str) {
		String s = "";
		if (null != str) {
			String da = str.substring(4, 6);
			String yy = str.substring(0, 4);
			int m = Integer.valueOf(da);
			int cs = (m - 1) / 3 + 1;
			s = yy + String.valueOf(cs);

		}
		return Integer.parseInt(s);
	}

	/**
	 * 得到当前月份
	 * 
	 * @param str
	 * @return
	 */
	public static int getMonth(String str) {
		int reValue = 0;
		if (null != str) {
			String s = str.substring(0, 6);
			reValue = Integer.parseInt(s);
		}
		return reValue;
	}

	/**
	 * 得到上一个星期的日期
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getBeforeWeekDay(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) - 7);
		return calendar;
	}

	/**
	 * 得到两个日期的间隔天数
	 */
	public static long getDaysBetween(Date startDate, Date endDate) {
		long days = 0;
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		days = ((toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 判断指定日期是否为当天
	 */
	public static boolean isCurrentDay(long date) {
		return getDayStartTime(new Date().getTime()) <= date
				&& getDayEndTime(new Date().getTime()) >= date;
	}

	/**
	 * 获取时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getHHMMSS(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String hh = c.get(Calendar.HOUR_OF_DAY)<10?("0"+c.get(Calendar.HOUR_OF_DAY)):(c.get(Calendar.HOUR_OF_DAY)+"");
		String mm = c.get(Calendar.MINUTE)<10?("0"+c.get(Calendar.MINUTE)):(c.get(Calendar.MINUTE)+"");
		String ss = c.get(Calendar.SECOND)<10?("0"+c.get(Calendar.SECOND)):(c.get(Calendar.SECOND)+"");
		return hh+":"+mm+":"+ss;

	}

	/**
	 * 获取上月的今天
	 */
	public static Date getTodayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取明天
	 */
	public static String getTomorrow(Date date) {
		if (CheckIsNull.isEmpty(date))
			date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, +24);
		return dateFromat(calendar.getTime());
	}
	
	/**
	 * 获取后天
	 */
	public static String getTheDayAfterTomorrow(Date date) {
		if (CheckIsNull.isEmpty(date))
			date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, +48);
		return dateFromat(calendar.getTime());
	}
	
	/**
	 * 获取24小时前时间
	 */
	public static long get24HoursAgo(Date date)
	{
		if (CheckIsNull.isEmpty(date))
			date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, -(60*60*24));
		return calendar.getTimeInMillis();
	}
	/** 
	 * 将一个表示时间段的数转换为毫秒数 
	 * @param num 时间差数值,支持小数 
	 * @param type 时间类型：1->秒,2->分钟,3->小时,4->天 
	 * @return long类型时间差毫秒数，当为-1时表示参数有错 
	 */  
	public static long formatToTimeMillis(double num, int type) {  
	    if (num <= 0)  
	        return 0;  
	    switch (type ) {  
	    case 0:  
	        return (long) (num * 1000); 
	    case 1:  
	       return (long) (num * 60 * 1000);  
	    case 2:  
	        return (long) (num * 60 * 60 * 1000);  
	    case 3:  
	        return (long) (num * 24 * 60 * 60  * 1000);  
	    default:  
	        return -1;  
	    }  
	    /** 
	     * 获取某一指定时间的前一段时间 
	     * @param num 时间差数值 
	     * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
	     * @param date 参考时间 
	     * @return 返回Date对象 
	     */  
	

	}  
    public static Date getPreTime(double num,int type,Date date){  
        long nowLong=date.getTime();//将参考日期转换为毫秒时间  
        Date time = new Date(nowLong-formatToTimeMillis(num, type));//减去时间差毫秒数  
        return time;  
    } 
	/** 
	 * 判断某个时间为星期几 
	 * @param ptime 
	 */
	 public static int dayForWeek(String pTime) throws Exception { 
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		  Calendar c = Calendar.getInstance(); 
		  c.setTime(format.parse(pTime)); 
		  int dayForWeek = 0; 
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){ 
		   dayForWeek = 7; 
		  }else{ 
		   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1; 
		  } 
		  return dayForWeek; 
   }   
	 /**
	 * 返回指定天数位移后的日期
	 */
	public static Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}

	/**
	 * 返回指定日期相应位移后的日期
	 * 
	 * @param date
	 *            参考日期
	 * @param field
	 *            位移单位，见 {@link Calendar}
	 * @param offset
	 *            位移数量，正数表示之后的时间，负数表示之前的时间
	 * @return 位移后的日期
	 */
	public static Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}
	private static Calendar convert(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}
	/**
	 * 试用参数Format格式化Calendar成字符串
	 * 
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static String format(Calendar cal, String pattern) {
		return cal == null ? "" : new SimpleDateFormat(pattern).format(cal
				.getTime());
	}
	/**
	 * 求几分钟之后的时间 
	 */
	public static Date getLastDate(int lastDate){
		java.util.Calendar Cal=java.util.Calendar.getInstance();    
		Cal.setTime(new Date());    
		Cal.add(java.util.Calendar.MINUTE,lastDate);    
		return Cal.getTime();
	}
	
	public static boolean isInDate(Date date,List<String> times){
		if(times == null)return false;
		boolean flag = false;
		for (int i = 0; i < times.size(); i++) {
			flag|= isInDate(new Date(), times.get(i).split("-")[0], times.get(i).split("-")[1]);
		}
		if(flag){// 可以下单
			return true;
		}else {//不能下单
			return false;
		}
	}
	
	public static boolean isInDeliDate(String deliData,List<String> times){
		if(times == null)return false;
		boolean flag = false;
		String[]deliDateArray = deliData.split("-");
		//兼容老格式  8:00-12:00
		if(deliDateArray.length <2)return false;
		boolean startFlag = false;
		boolean endFlag = false;
		long startDeliDate = formatTime(deliDateArray[0],2);
		long endDeliDate = formatTime(deliDateArray[1],-2);
		
		//11:00-11:20   11:01-11:21
		//              11:02-11:18
		for (int i = 0; i < times.size(); i++) {
			//flag|= isInDate(new Date(), times.get(i).split("-")[0], times.get(i).split("-")[1]);
			long timesStart = formatTime(times.get(i).split("-")[0],0);
			long timesEnd = formatTime(times.get(i).split("-")[1],0);
			
			//如果开始时间在范围内，结束时间也在范围内，则认为可下单
			if(startDeliDate >= timesStart && startDeliDate <= timesEnd)startFlag = true;
			if(endDeliDate >= timesStart && endDeliDate <= timesEnd)endFlag = true;
			if(startFlag && endFlag)break;
		}
		
		if(startFlag && endFlag){// 可以下单
			return true;
		}else {//不能下单
			return false;
		}
	}
	
	
	private static long formatTime(String time,int minNum){
		//time格式：8:00.12:00，8:1,8:12
		String times[] = time.split(":");
		int hour;
		int min;
		if(times[0].length() == 1){
			hour = Integer.parseInt(times[0]);
		}else{
			hour = Integer.parseInt(times[0]);
		}
		
		if(times[1].length() == 1){
			min = Integer.parseInt(times[1]);
		}else{
			min = Integer.parseInt(times[1]);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		
		if(minNum > 0)
		calendar.add(Calendar.MINUTE, minNum);
		
		return calendar.getTimeInMillis();
		
	}
	
	/**
	 *  判断date在不在某个时间段内  6:00~12:00
	 * @param date 要判断的时间
	 * @param strDateBegin  开始时间：6:00
	 * @param strDateEnd  结束时间   12:00
	 * @return  
	 */
	public static boolean isInDate(Date date, String strDateBegin,
			String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date); // 截取当前时间时分秒  
		int strDateH = Integer.parseInt(strDate.substring(11, 13));// 小时
		int strDateM = Integer.parseInt(strDate.substring(14, 16));//分钟
		int strDateBeginH = Integer.parseInt(strDateBegin.split(":")[0]);
		int strDateBeginM = Integer.parseInt(strDateBegin.split(":")[1]);

		int strDateEndH = Integer.parseInt(strDateEnd.split(":")[0]);
		int strDateEndM = Integer.parseInt(strDateEnd.split(":")[1]);
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) { // 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateM > strDateBeginM && ((strDateM < strDateEndM)||strDateEndM==0)) {
				return true; // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
					&& strDateM <= strDateEndM) {
				return true; // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM) {
				return true;
			} // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM <= strDateEndM) {
				return true; // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM == strDateEndM) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static int getAge(String birthDay) {

		if (birthDay!= null && !"".equals(birthDay)){
			
			int age = 0;
			
			Date now = new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date birthDate;
			try {
				birthDate = format.parse(birthDay);
				SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
				SimpleDateFormat format_M = new SimpleDateFormat("MM");
				SimpleDateFormat format_D = new SimpleDateFormat("dd");
				
				String birth_year = format_y.format(birthDate);
				String this_year = format_y.format(now);
				
				String birth_month = format_M.format(birthDate);
				String this_month = format_M.format(now);
				
				String birth_day = format_D.format(birthDate);
				String this_day = format_D.format(now);
				
				// 初步，估算
				age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);
				
				// 如果未到出生月份，则age - 1
				if(this_month.compareTo(birth_month) < 0){
					
					age -= 1;
				}else if(this_month.compareTo(birth_month) == 0){
					if(this_day.compareTo(birth_day) < 0){
						age -= 1;
					}
				}
				if (age < 0){
					
					age = 0;
				}
				return age;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	
	}
	
	
	/**
	 * 得到当前日志的timestamp串
	 *格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param oldDate
	 * @return
	 */
	public static Date stringToDate(String oldDate) {
        Format f = new SimpleDateFormat(DATE_TIME_PATTERN2);            
        Date d= new Date();
		try {
			d = (Date) f.parseObject(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}           
		
		return d;
	}
	
	/**
	 * 得到两个日期的间隔天数
	 */
	public static long getDaysBetweenHours(Date startDate, Date endDate) {
		long hours = 0;
		hours = ((endDate.getTime() - startDate.getTime()) / (1000 * 60*60 ));
		return hours;
	}
	
	/**
	 * 得到当前日志的timestamp串
	 *格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param oldDate
	 * @return
	 */
	public static String timestampToString(Timestamp  ts) {
        Format f = new SimpleDateFormat(DATE_TIME_PATTERN2);            
        String tsStr = "";   
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        try {   
           
            tsStr = sdf.format(ts);   
           
        } catch (Exception e) {   
           e.printStackTrace();   
        }  
     
       
		
		return tsStr;
	}
	
	/**
	 * 得到两个时间相差的月数
	 * */
	public static int monthSpace(Date d1 , Date d2){
		int month = 0;
		try {
			if(d1.getTime() > d2.getTime()){
				month = (d1.getYear() - d2.getYear()) * 12;
				month += d1.getMonth() - d2.getMonth();
				if(d1.getDay() - d2.getDay() < 0){
					month -= 1;
				}
			}else{
				month = (d2.getYear() - d1.getYear()) * 12;
				month += d2.getMonth() - d1.getMonth();
				if(d2.getDay() - d1.getDay() < 0){
					month -= 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return month;
	}
	public static String now(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN2);
		return sdf.format(new Date());
	}
}

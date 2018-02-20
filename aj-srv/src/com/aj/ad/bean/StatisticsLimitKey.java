package com.aj.ad.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.LimitKey;


public class StatisticsLimitKey extends LimitKey {
	private String startTime;
	private String endTime;
	
	private String pos;
	private String ad;
	
	//如果不是时间格式，返回空
	public String getEndTimeParse() {
		if (StringUtil.isEmpty(endTime)) {
			return "";
		}
		
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format_yyyy_mm_dd.parse(endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (date == null) {
			return "";
		}
		return format_yyyy_mm_dd.format(date);
	}
	//如果不是时间格式，返回空
	public String getStartTimeParse() {
		if (StringUtil.isEmpty(startTime)) {
			return "";
		}
		
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format_yyyy_mm_dd.parse(startTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (date == null) {
			return "";
		}
		return format_yyyy_mm_dd.format(date);
		
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	
	
	
	
	
	
	
	

	

	

}

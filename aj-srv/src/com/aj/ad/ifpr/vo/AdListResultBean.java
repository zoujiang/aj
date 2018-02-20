package com.aj.ad.ifpr.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;


public class AdListResultBean {
	
	private String name;//广告名称
	private String type;//否	广告类型 url 外部链接;text 文字;video 视频;img图片;product商品广告;merchant商家广告
	private String url;//是	跳转链接地址
	private String text;//是	文字内容
	private String video;//是	视频地址
	private String imgSmall;//是	小图480 除了文字以外，其他几个类型也会有图片
	private String imgBig;//是	大图720
	private String relationId;//是	关联ID 如果是商品就是商品ID， 如果是商户就是商户ID
	private String relationTitle;//是	关联名称 商品名称、商户名称
	private int interval;//否	轮播间隔时间 单位秒
	private String offlineTime;//否	下线时间 YYYYMMDDHHMM 如：20140321000000
	private String onlineTime;//否	上线时间 YYYYMMDDHHMM 如：20140321000000
	private String time;//是	有效时间段 多个时间用”,”分隔。如：02:00-12:00,13:00-23:59
	private String backParam;//是	调用5.2统计接口回传参数


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVideo() {
		return SystemConfig.getValue("res.http.url") + imgSmall;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getImgSmall() {
		
		if (StringUtil.isEmpty(imgSmall)) {
			return ""; 
		} else if (imgSmall.startsWith("http://")) {
			return imgSmall;
		} else {
			return SystemConfig.getValue("img.http.url") + imgSmall;
		}
		
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}

	public String getImgBig() {
		if (StringUtil.isEmpty(imgBig)) {
			return ""; 
		} else if (imgBig.startsWith("http://")) {
			return imgBig;
		} else {
			return SystemConfig.getValue("img.http.url") + imgBig;
		}
	}

	public void setImgBig(String imgBig) {
		this.imgBig = imgBig;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getRelationTitle() {
		return relationTitle;
	}

	public void setRelationTitle(String relationTitle) {
		this.relationTitle = relationTitle;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	public void setOfflineTime(Date offline_time) {
		if (offline_time == null) {
			this.offlineTime = "";
		}
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.offlineTime = format_yyyy_mm_dd.format(offline_time);
		
	}
	public void setOnlineTime(Date online_time) {
		if (online_time == null) {
			this.onlineTime = "";
		}
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.onlineTime = format_yyyy_mm_dd.format(online_time);
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBackParam() {
		return backParam;
	}

	public void setBackParam(String backParam) {
		this.backParam = backParam;
	}
	
	

	

	
	
	
}

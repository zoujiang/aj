package com.aj.ad.ifpr.bean;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;

//广告关系表（广告内容与广告位）
public class AdRelationBean  extends BaseBean {
	private static Logger logger = Logger.getLogger(AdRelationBean.class);
	
	private String posid;	//广告位ID';
	private String content_id;	//广告内容ID';
	private int interval;	//轮播时间/秒';
	private int seq;		//排序序号';
	private int status;		//状态(上线1,下线0)';
	private String time;	//时间段,多个用","分隔，开始与结束用"-"分隔, 如:  8:00-9:30,18:30-21:30;
	
	//private String ad_name; //广告名称  表没有此字段
	private String pos_name; //广告位名称
	
	private AdContentBean content;
	private AdPositionBean postion;
	
	
	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.posid = (String)row.get("position_id");
		this.content_id = (String) row.get("content_id");
		this.interval = (Integer)row.get("show_interval");
		this.seq = (Integer)row.get("seq");
		this.status = (Integer)row.get("status");
		this.time = (String) row.get("time");
	}
	
	public String getPosid() {
		return posid;
	}
	public void setPosid(String posid) {
		this.posid = posid;
	}
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public String getTimeBr() {
		if (time != null) {
			return time.replace(",", "<br/>");
		}
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public AdContentBean getContent() {
		return content;
	}

	public void setContent(AdContentBean content) {
		this.content = content;
	}

	public String getPos_name() {
		return pos_name;
	}

	public void setPos_name(String pos_name) {
		this.pos_name = pos_name;
	}

	public AdPositionBean getPostion() {
		return postion;
	}

	public void setPostion(AdPositionBean postion) {
		this.postion = postion;
	}
	
	
	
	
}

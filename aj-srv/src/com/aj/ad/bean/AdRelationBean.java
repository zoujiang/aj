package com.aj.ad.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

//骞垮憡鍏崇郴琛紙骞垮憡鍐呭涓庡箍鍛婁綅锛�
public class AdRelationBean  extends BaseBean {
	private static Logger logger = Logger.getLogger(AdRelationBean.class);
	
	private String posid;	//骞垮憡浣岻D';
	private String content_id;	//骞垮憡鍐呭ID';
	private int interval;	//杞挱鏃堕棿/绉�;
	private int seq;		//鎺掑簭搴忓彿';
	private int status;		//鐘舵�(涓婄嚎1,涓嬬嚎0)';
	private String time;	//鏃堕棿娈�澶氫釜鐢�,"鍒嗛殧锛屽紑濮嬩笌缁撴潫鐢�-"鍒嗛殧, 濡�  8:00-9:30,18:30-21:30;
	
	//private String ad_name; //骞垮憡鍚嶇О  琛ㄦ病鏈夋瀛楁
	private String pos_name; //骞垮憡浣嶅悕绉�
	private String create_user; //骞垮憡浣嶅悕绉�
	
	private AdContentBean content;
	private PositionBean postion;
	
	
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
		this.create_user = (String) row.get("create_user");
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
		return time == null ? "" : time;
	}
	public String getTimeBr() {
		if (time != null) {
			return time.replace(",", "<br/>");
		}
		return "";
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

	public PositionBean getPostion() {
		return postion;
	}

	public void setPostion(PositionBean postion) {
		this.postion = postion;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	
	
	
	
	
}

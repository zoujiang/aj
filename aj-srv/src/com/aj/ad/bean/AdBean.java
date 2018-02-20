package com.aj.ad.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aj.ad.constant.AdConstants;

//广告
public class AdBean extends BaseBean {
	protected static final String DATA_FORMAT_yyyy_mm_dd = "yyyy-MM-dd";
	public static final SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat(DATA_FORMAT_yyyy_mm_dd);
	private static Logger logger = Logger.getLogger(AdBean.class);
	
	private String name;	//广告名称';
	private Date online_time;	//上线时间';
	private Date offline_time;	//下线时间';
	private String type;	//类型， web wap ios android组合串;
	private int status;	//状态(上线1,下线0)';
	private String createUser; //创建用户
	
	private AdContentBean ios;
	private AdContentBean android;
	private AdContentBean wxh5;
	
	public AdContentBean getWxh5() {
		return wxh5;
	}

	public void setWxh5(AdContentBean wxh5) {
		this.wxh5 = wxh5;
	}

	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.name = (String)row.get("name");
		this.online_time = (Date) row.get("online_time");
		this.offline_time = (Date) row.get("offline_time");
		this.type = (String)row.get("type");
		this.status = (Integer)row.get("status");
		
		this.createUser = (String)row.get("create_user");
		
	}
	
	public Date getOnline_time() {
		return online_time;
	}

	public void setOnline_time(Date online_time) {
		this.online_time = online_time;
	}

	public Date getOffline_time() {
		return offline_time;
	}

	public void setOffline_time(Date offline_time) {
		this.offline_time = offline_time;
	}
	
	public void setOffline_timeStr(String time) {
		try {
			this.offline_time = format_yyyy_mm_dd.parse(time);
		} catch (ParseException e) {
			logger.error("offline_time解析出错:" + time +"---" + DATA_FORMAT_yyyy_mm_dd, e);
			this.offline_time = null;
			return;
		}
	}
	public void setOnline_timeStr(String time) {
		try {
			this.online_time = format_yyyy_mm_dd.parse(time);
		} catch (ParseException e) {
			logger.error("online_time解析出错:" + time +"---" + DATA_FORMAT_yyyy_mm_dd, e);
			this.online_time = null;
			return;
		}
	}
	public String getOffline_timeStr() {
		if (this.offline_time == null) {
			return null;
		}
		return format_yyyy_mm_dd.format(offline_time);
	}
	public String getOnline_timeStr() {
		if (this.online_time == null) {
			return null;
		}
		return format_yyyy_mm_dd.format(online_time);
	}

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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusText() {
		if (status == AdConstants.STATUS_ON) {
			return "上线";
		} else {
			return "下线";
		}
	}

	
	public AdContentBean getIos() {
		if (ios != null) {
			ios.setPosition_type(AdConstants.ios);
			ios.setAdid(id);
		}
		return ios;
	}
	public AdContentBean getAndroid() {
		if (android != null) {
			android.setPosition_type(AdConstants.android);
			android.setAdid(id);
		}
		return android;
	}

	public void setIos(AdContentBean ios) {
		this.ios = ios;
	}

	public void setAndroid(AdContentBean android) {
		this.android = android;
	}
	
	public boolean isType(String type) {
		if (this.type == null || type == null) {
			return false;
		}
		return this.type.contains(type);
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}

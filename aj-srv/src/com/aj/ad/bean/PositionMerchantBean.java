package com.aj.ad.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

public class PositionMerchantBean {
	private static Logger logger = Logger.getLogger(PositionMerchantBean.class);
	private SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	private SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	/*
	 * 
	 	0	待审核
		1	上线
		2	下线
		3	删除
		4	回退
	 * */
	private String id;
	//private String merchantId;
	private String merchantName;
	private String positionId;
	private String positionName;
	private int positionCode;
	private String positionImge;
	private Date createDate;
	private Date passDate;  //通过时间
	private Date backDate;  //驳回时间
	
	private String passUser;  //通过操作人员
	private String backUser; //驳回操作人员
	
	private String status; //0 申请  1	上线(通过)   4 回退
	
	
	// eff_start_time, eff_end_time
	private Date effStartTime;	//生效开始时间
	private Date effEndTime;	//生效结束时间
	 
	 
	private String reason;
	
	//t_mall_merchant_ad_relation
	//t_mall_merchant
	
	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.merchantName = (String)row.get("merchant_name");
		this.positionName = (String)row.get("position_name");
		this.positionId = (String)row.get("position_id");
		this.positionCode = (Integer)row.get("position_code");
		this.positionImge = (String)row.get("position_image");
		this.effStartTime = (Date) row.get("eff_start_time");
		this.effEndTime = (Date) row.get("eff_end_time");
		this.createDate = (Date) row.get("create_date");
		this.passDate = (Date) row.get("pass_date");
		this.backDate = (Date) row.get("back_date");
		this.passUser = (String) row.get("pass_user");
		this.backUser = (String) row.get("back_user");
		
		this.status = (String)row.get("status");
		this.reason = (String)row.get("reason");
		
	}
	
	//0 申请  1	上线(通过)   4 回退
	public String getStatusText() {
		if ("0".equals(status)) {
			return "申请";
		} else if ("1".equals(status)) {
			return "通过";
		}  else if ("4".equals(status)) {
			return "回退";
		}
		return status;
	}
	
	public void setEffStartTimeStr(String time) {
		try {
			this.effStartTime = format_yyyy_mm_dd.parse(time);
		} catch (ParseException e) {
			logger.error("effStartTime解析出错:" + time, e);
			this.effStartTime = null;
			return;
		}
	}
	public void setEffEndTimeStr(String time) {
		try {
			this.effEndTime = format_yyyy_mm_dd.parse(time);
		} catch (ParseException e) {
			logger.error("effEndTime解析出错:" + time, e);
			this.effEndTime = null;
			return;
		}
	}
	
	public String getCreateDateStr() {
		return format(createDate);
	}

	public String getPassDateStr() {
		return format(passDate);
	}

	public String getBackDateStr() {
		return format(backDate);
	}

	public String getEffStartTimeStr() {
		if (effStartTime == null) {
			return "";
		}
		return format_yyyy_mm_dd.format(effStartTime);
	}

	public String getEffEndTimeStr() {
		if (effEndTime == null) {
			return "";
		}
		return format_yyyy_mm_dd.format(effEndTime);
	}


	
	private String format(Date date) {
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getPassDate() {
		return passDate;
	}
	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public String getPassUser() {
		return passUser;
	}
	public void setPassUser(String passUser) {
		this.passUser = passUser;
	}
	public String getBackUser() {
		return backUser;
	}
	public void setBackUser(String backUser) {
		this.backUser = backUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEffStartTime() {
		return effStartTime;
	}
	public void setEffStartTime(Date effStartTime) {
		this.effStartTime = effStartTime;
	}
	public Date getEffEndTime() {
		return effEndTime;
	}
	public void setEffEndTime(Date effEndTime) {
		this.effEndTime = effEndTime;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPositionName() {
		return positionName;
	}

	public int getPositionCode() {
		return positionCode;
	}

	public String getPositionImge() {
		return positionImge;
	}

	public String getMerchantName() {
		return merchantName;
	}
	
	
	

}

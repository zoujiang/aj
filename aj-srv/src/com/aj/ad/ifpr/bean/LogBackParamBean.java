package com.aj.ad.ifpr.bean;

import org.apache.log4j.Logger;


//链接访问日志
public class LogBackParamBean extends BaseBean {
	private static Logger logger = Logger.getLogger(LogBackParamBean.class);
	protected String userid;	//用户ID(和客户端唯一标志至少需要一个)
	protected String channel_id;	//频道ID
	//private Date time;		//操作时间YYYY-MM-DD HH24:MI:SS
	protected String ad_position_name;	//广告位名称
	protected String ad_name;	//广告名称
	protected String ad_seq;	//广告栏位置ID，D1,AD2,AD3,AD4,AD5,AD6,AD7,AD8,AD9,AD10,AD11对应系统中广告序号：1,2,3,4,5,6,78,9,10,11
	protected int ad_status;	//状态 0：下线；1：上线
	
	protected String ad_position_id;	//广告位ID
	protected int ad_position_code;	//广告位编码   用于跳转回传时取信息
	protected String ad_content_id;	//广告内容ID
	protected String ad_content_type;	//广告内容类型  文字 视频等等
	protected String ad_relation_id;	//广告位与广告内容关联ID
	protected String ad_url;	//
	protected String relation_id; //关联ID 商家ID 商品ID等
	protected String relation_title; //关联ID 商家名称 商品名称等
	protected String call_type;	//访问平台类型 001-Android,002-IOS,003-Web,004-Wap
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getAd_position_name() {
		return ad_position_name;
	}
	public void setAd_position_name(String ad_position_name) {
		this.ad_position_name = ad_position_name;
	}
	public String getAd_name() {
		return ad_name;
	}
	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}
	public String getAd_seq() {
		return ad_seq;
	}
	public void setAd_seq(String ad_seq) {
		this.ad_seq = ad_seq;
	}
	public int getAd_status() {
		return ad_status;
	}
	public void setAd_status(int ad_status) {
		this.ad_status = ad_status;
	}
	
	
	
	
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getAd_relation_id() {
		return ad_relation_id;
	}
	public void setAd_relation_id(String ad_relation_id) {
		this.ad_relation_id = ad_relation_id;
	}
	
	public String getAd_position_id() {
		return ad_position_id;
	}
	public void setAd_position_id(String ad_position_id) {
		this.ad_position_id = ad_position_id;
	}
	public String getAd_content_id() {
		return ad_content_id;
	}
	public void setAd_content_id(String ad_content_id) {
		this.ad_content_id = ad_content_id;
	}
	
	public String getAd_url() {
		return ad_url;
	}
	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public int getAd_position_code() {
		return ad_position_code;
	}

	public void setAd_position_code(int ad_position_code) {
		this.ad_position_code = ad_position_code;
	}
	public String getRelation_id() {
		return relation_id;
	}
	public void setRelation_id(String relation_id) {
		this.relation_id = relation_id;
	}
	public String getRelation_title() {
		return relation_title;
	}
	public void setRelation_title(String relation_name) {
		this.relation_title = relation_name;
	}
	public String getCall_type() {
		return call_type;
	}
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}
	public String getAd_content_type() {
		return ad_content_type;
	}
	public void setAd_content_type(String ad_content_type) {
		this.ad_content_type = ad_content_type;
	}
	
	
	

	
	
}

package com.aj.ad.ifpr.config;

import java.util.ResourceBundle;


public class AdConstants {
	private static ResourceBundle rb = ResourceBundle.getBundle("system");
	public static final String DEF_KEY = rb.getString("desKey");// 默认KEY
	
	public static final String callType_andriond = "001";
	public static final String callType_ios = "002";
	public static final String callType_web = "003";
	public static final String callType_wap = "004";
	
	public static final String sysType_android = "android";//android
	public static final String sysType_ios = "ios";//ios
	public static final String sysType_web = "web";//web
	public static final String sysType_wap = "wap";//wap
	
	public static final String position_pos_top = "top";//广告位位置,上top
	public static final String position_pos_middle = "middle";//广告位位置,中middle
	public static final String position_pos_bottom = "bottom";//广告位位置,下bottom
	
	public static final int status_on = 1;//状态  上线1
	public static final int status_off = 0;//状态  下线0
	public static final int status_del = 3;//状态  删除3
	public static final int status_default = status_off;//默认状态 
	
	public static final String adContent_type_url = "url"; //广告内容类型 外部链接
	public static final String adContent_type_text = "text"; //广告内容类型    文字
	public static final String adContent_type_video = "video"; //广告内容类型   视频
	public static final String adContent_type_img = "img"; //广告内容类型    图片
	public static final String adContent_type_product = "product"; //广告内容类型   商品广告
	public static final String adContent_type_merchant = "merchant"; //广告内容类型  商家广告

	
	public static final int page_count = 10;  //默认每页显示条数
	
	
	

}

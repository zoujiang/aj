package com.frame.core.constant;

import com.frame.core.util.SystemConfig;

public class Constant {
	
	public static String TRUE = "1";
	public static String FALSE = "0";
	public static final int MAX_PASSWORD = 30;// 最大用户密码长度

	public static final String Login_CheckWord = "kaptcha";
	public static final String LoginAdminUser = "Login_User_Info";
	public static final String Login = "Login_User";
	public static final String LoginMemberUser = "Login_Member_Info";
	public static final String EditType = "editType";//1 新建 2 修改 3 详情 4 复制
	public static String resPath = (String) SystemConfig.getValue("ftp.res.path.root");
	public static String photoZipDownloadAddress = (String) SystemConfig.getValue("aj.photo.zip.download.address");
}

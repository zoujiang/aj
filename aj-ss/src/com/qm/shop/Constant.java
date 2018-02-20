package com.qm.shop;

import com.frame.core.constant.FtpConstant;
import com.frame.core.util.SystemConfig;

public class Constant {

	
	 public static String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	 public static String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	 public static String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	 public static String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	 public static String tempPath = (String) SystemConfig.getValue(FtpConstant.FTP_FILE_TEM_PATH);
	 public static int port =  SystemConfig.getValue(FtpConstant.PORT) == null ? 21 : Integer.parseInt(SystemConfig.getValue(FtpConstant.PORT).toString());
		
	 public static String imgPrefix = (String) SystemConfig.getValue("img.http.url");
	 public static String resPrefix = (String) SystemConfig.getValue("res.http.url");
	 
	 public static String clearImgPrefix = (String) SystemConfig.getValue("clear.img.http.url");
	 public static String clearResPrefix = (String) SystemConfig.getValue("clear.res.http.url");
	 public static String resPath = (String) SystemConfig.getValue("ftp.res.path.root");
}

package com.frame.ifpr.vo;

import com.frame.core.vo.ResultVo;

public class FileUploadResultVo extends ResultVo {
  private String picPath;

  private String shortPicPath; //短地址， 保存在数据库的地址
  
public String getPicPath() {
	return picPath;
}

public void setPicPath(String picPath) {
	this.picPath = picPath;
}

public String getShortPicPath() {
	return shortPicPath;
}

public void setShortPicPath(String shortPicPath) {
	this.shortPicPath = shortPicPath;
}
  
}

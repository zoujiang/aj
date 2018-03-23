package com.frame.core.vo;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 响应参数封装VO
 * @author lishun
 *
 */
public class ResultVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6475750683176132082L;
	
	private String succMsg;
	
	

	public String getSuccMsg() {
		return succMsg;
	}



	public void setSuccMsg(String succMsg) {
		this.succMsg = succMsg;
	}



	@Override
	public String toString() {
		return this==null?"":super.toString();
	}
}

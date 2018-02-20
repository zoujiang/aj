/** 
 * 用于前台显示的类
 * @className framework.core.util.ListRange
 * @author ChingWang    
 * @date 2016-4-18 上午9:53:23
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.frame.core.util;

import java.util.List;

public class ListRange<T> {

	private boolean success;
	private String message;
	private long total;
	private List<T> rows;
	private T obj;
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	/**
	 * @return the obj
	 */
	public T getObj() {
		return obj;
	}
	/**
	 * @param obj the obj to set
	 */
	public void setObj(T obj) {
		this.obj = obj;
	}
	
}

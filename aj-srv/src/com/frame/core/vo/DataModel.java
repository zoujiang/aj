package com.frame.core.vo;

import java.util.ArrayList;
import java.util.List;

public class DataModel<T> {

	public DataModel(List<T> rows, long total) {
		this.rows = rows;
		this.total = total;
		this.isAdd = true;
	}

	public DataModel() {
		this.rows = new ArrayList<T>();
		this.total = 0;
		this.isAdd = true;
	}
	private String serverName;
	/**
	 * 行数
	 */
	private long total;

	/**
	 * 行记录
	 */
	private List<T> rows;
	
	/**
	 * isAdd
	 */
	private boolean isAdd;
	/**
	 * 尾部
	 */
	private List<T> footer;
	

	
	public boolean getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}

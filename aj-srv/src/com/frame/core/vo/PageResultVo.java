package com.frame.core.vo;

import java.util.List;

public class PageResultVo<T> extends ResultVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 872260515664141631L;
	/**总行数*/
	protected long totalResults;
	/** 行记录*/
	protected List<T> list;
	
	public long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}

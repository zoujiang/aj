package com.frame.core.vo;

import java.util.List;

public class PageResultVo1<T> extends ResultVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 872260515664141631L;
	/**总行数*/
	protected long totalResults;

	protected int totalNoGetResults;
	
	protected int totalGetResults;
	
	protected int totalExpireResults;
	
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
	public int getTotalNoGetResults() {
		return totalNoGetResults;
	}
	public void setTotalNoGetResults(int totalNoGetResults) {
		this.totalNoGetResults = totalNoGetResults;
	}
	public int getTotalGetResults() {
		return totalGetResults;
	}
	public void setTotalGetResults(int totalGetResults) {
		this.totalGetResults = totalGetResults;
	}
	public int getTotalExpireResults() {
		return totalExpireResults;
	}
	public void setTotalExpireResults(int totalExpireResults) {
		this.totalExpireResults = totalExpireResults;
	}


}


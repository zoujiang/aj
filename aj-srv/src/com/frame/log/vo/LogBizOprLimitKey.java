package com.frame.log.vo;

import com.frame.core.util.pagination.key.LimitKey;

public class LogBizOprLimitKey extends LimitKey {
	private String searchText;
	private String sortColumnName;
	private String oprUserAccount;
	private String oprType;
	
	private String startDt;
	private String endDt;
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getSortColumnName() {
		return sortColumnName;
	}
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	public String getOprUserAccount() {
		return oprUserAccount;
	}
	public void setOprUserAccount(String oprUserAccount) {
		this.oprUserAccount = oprUserAccount;
	}
	public String getOprType() {
		return oprType;
	}
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	
}

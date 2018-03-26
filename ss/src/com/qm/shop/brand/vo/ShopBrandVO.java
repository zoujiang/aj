package com.qm.shop.brand.vo;

import com.frame.core.util.pagination.key.LimitKey;

public class ShopBrandVO extends LimitKey {
	
	private String id;
	private String brandName;
	private Integer sortIndex;
	private Integer status;
	private String brandIcon;
	//1.像馆  2：幼儿园 3券商
	private Integer type;
	//是否推荐0:否 1:是
	private Integer isRecommond;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBrandIcon() {
		return brandIcon;
	}
	public void setBrandIcon(String brandIcon) {
		this.brandIcon = brandIcon;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsRecommond() {
		return isRecommond;
	}

	public void setIsRecommond(Integer isRecommond) {
		this.isRecommond = isRecommond;
	}
}

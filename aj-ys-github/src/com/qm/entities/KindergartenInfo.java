package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class KindergartenInfo extends LimitKey{
    private Integer id;

	private String name;

	private String brandId;

	private String tele;

	private String registName;

	private String address;

	private String serviceTel;

	private String gps;

	private Integer category;

	private String serviceStartTime;

	private String serviceEndTime;

	private Integer sortIndex;

	private Integer isRecommend;

	private String logo;

	private String showPics;

	private String createTime;

	private String createUser;

	private Integer properties;

	private String shopCategoryId;

	private Integer status;

	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId == null ? null : brandId.trim();
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele == null ? null : tele.trim();
	}

	public String getRegistName() {
		return registName;
	}

	public void setRegistName(String registName) {
		this.registName = registName == null ? null : registName.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel == null ? null : serviceTel.trim();
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps == null ? null : gps.trim();
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime == null ? null : serviceStartTime.trim();
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime == null ? null : serviceEndTime.trim();
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo == null ? null : logo.trim();
	}

	public String getShowPics() {
		return showPics;
	}

	public void setShowPics(String showPics) {
		this.showPics = showPics == null ? null : showPics.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Integer getProperties() {
		return properties;
	}

	public void setProperties(Integer properties) {
		this.properties = properties;
	}

	public String getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId == null ? null : shopCategoryId.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

}
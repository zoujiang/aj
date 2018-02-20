package com.qm.shop.feedback.vo;

import com.frame.core.util.pagination.key.LimitKey;

public class ShopFeedbackVO extends LimitKey {
	
	private String id;
    private String fbCategory;
    private String fbContent;
    private String createTime;
    private String shopId;
    private String shopAccount;  
    private String replyUserId;
    private String replyContent;
    private String replyTime;
    private Integer isShow;
    private String shopName;
    private String replyUserName;
    private String fbStartTime;
    private String fbEndTime;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFbCategory() {
		return fbCategory;
	}
	public void setFbCategory(String fbCategory) {
		this.fbCategory = fbCategory;
	}
	public String getFbContent() {
		return fbContent;
	}
	public void setFbContent(String fbContent) {
		this.fbContent = fbContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopAccount() {
		return shopAccount;
	}
	public void setShopAccount(String shopAccount) {
		this.shopAccount = shopAccount;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getFbStartTime() {
		return fbStartTime;
	}
	public void setFbStartTime(String fbStartTime) {
		this.fbStartTime = fbStartTime;
	}
	public String getFbEndTime() {
		return fbEndTime;
	}
	public void setFbEndTime(String fbEndTime) {
		this.fbEndTime = fbEndTime;
	}
	
}

package com.qm.shop.album.vo;

import com.frame.core.util.pagination.key.LimitKey;

public class ShopAlbumVO extends LimitKey {
	
	private String id;
	private String shopId;
	private String shopName;
    private String userId;
    private String userName;
    private String albumName;
    private String albumLogo;
    private String albumType;
    private String origPrice;
    private String payType_3;
    private String payType_6;
    private String payType_9;
    private String payType_12;
    private String payType_24;
    private String payType_25;
    private String photoUrls;
    private String createTime;
    private String downloadAddress;
    private String downloadSecret;
    private Long  albumSize;
    private String photoTime;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getAlbumLogo() {
		return albumLogo;
	}
	public void setAlbumLogo(String albumLogo) {
		this.albumLogo = albumLogo;
	}
	public String getAlbumType() {
		return albumType;
	}
	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}
	public String getOrigPrice() {
		return origPrice;
	}
	public void setOrigPrice(String origPrice) {
		this.origPrice = origPrice;
	}
	public String getPayType_3() {
		return payType_3;
	}
	public void setPayType_3(String payType_3) {
		this.payType_3 = payType_3;
	}
	public String getPayType_6() {
		return payType_6;
	}
	public void setPayType_6(String payType_6) {
		this.payType_6 = payType_6;
	}
	public String getPayType_9() {
		return payType_9;
	}
	public void setPayType_9(String payType_9) {
		this.payType_9 = payType_9;
	}
	public String getPayType_12() {
		return payType_12;
	}
	public void setPayType_12(String payType_12) {
		this.payType_12 = payType_12;
	}
	public String getPayType_24() {
		return payType_24;
	}
	public void setPayType_24(String payType_24) {
		this.payType_24 = payType_24;
	}
	public String getPayType_25() {
		return payType_25;
	}
	public void setPayType_25(String payType_25) {
		this.payType_25 = payType_25;
	}
	public String getPhotoUrls() {
		return photoUrls;
	}
	public void setPhotoUrls(String photoUrls) {
		this.photoUrls = photoUrls;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDownloadAddress() {
		return downloadAddress;
	}
	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}
	public String getDownloadSecret() {
		return downloadSecret;
	}
	public void setDownloadSecret(String downloadSecret) {
		this.downloadSecret = downloadSecret;
	}
	public Long getAlbumSize() {
		return albumSize;
	}
	public void setAlbumSize(Long albumSize) {
		this.albumSize = albumSize;
	}
	public String getPhotoTime() {
		return photoTime;
	}
	public void setPhotoTime(String photoTime) {
		this.photoTime = photoTime;
	}
	
}

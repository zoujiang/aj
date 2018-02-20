package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShopAlbum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_shop_album")
public class TShopAlbum implements java.io.Serializable {

	// Fields

	private String id;
	private String shopId;
	private String userId;
	private String albumName;
	private String albumLogo;
	private Integer isPay;
	private String originalPrice;
	private String payType;
	private String createTime;
	private Integer hadPaid;
	private String downloadAddress;
	private String downloadSecret;
	private Integer viewCount;
	private String photoTime;
	private Integer visibleProperty;
	private String description;

	// Constructors

	/** default constructor */
	public TShopAlbum() {
	}

	/** minimal constructor */
	public TShopAlbum(String id, String shopId, String userId,
			String albumName, Integer isPay, Integer hadPaid) {
		this.id = id;
		this.shopId = shopId;
		this.userId = userId;
		this.albumName = albumName;
		this.isPay = isPay;
		this.hadPaid = hadPaid;
	}

	/** full constructor */
	public TShopAlbum(String id, String shopId, String userId,
			String albumName, String albumLogo, Integer isPay,
			String originalPrice, String payType, String createTime,
			Integer hadPaid, String downloadAddress, String downloadSecret) {
		this.id = id;
		this.shopId = shopId;
		this.userId = userId;
		this.albumName = albumName;
		this.albumLogo = albumLogo;
		this.isPay = isPay;
		this.originalPrice = originalPrice;
		this.payType = payType;
		this.createTime = createTime;
		this.hadPaid = hadPaid;
		this.downloadAddress = downloadAddress;
		this.downloadSecret = downloadSecret;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "shop_id", nullable = false, length = 64)
	public String getShopId() {
		return this.shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Column(name = "user_id", nullable = false, length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "album_name", nullable = false, length = 200)
	public String getAlbumName() {
		return this.albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Column(name = "album_logo", length = 100)
	public String getAlbumLogo() {
		return this.albumLogo;
	}

	public void setAlbumLogo(String albumLogo) {
		this.albumLogo = albumLogo;
	}

	@Column(name = "is_pay", nullable = false)
	public Integer getIsPay() {
		return this.isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	@Column(name = "original_price", length = 11)
	public String getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(name = "pay_type", length = 50)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "create_time", length = 32)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "had_paid", nullable = false)
	public Integer getHadPaid() {
		return this.hadPaid;
	}

	public void setHadPaid(Integer hadPaid) {
		this.hadPaid = hadPaid;
	}

	@Column(name = "download_address", length = 200)
	public String getDownloadAddress() {
		return this.downloadAddress;
	}

	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}

	@Column(name = "download_secret", length = 6)
	public String getDownloadSecret() {
		return this.downloadSecret;
	}

	public void setDownloadSecret(String downloadSecret) {
		this.downloadSecret = downloadSecret;
	}
	@Column(name = "view_count")
	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	@Column(name = "photo_time")
	public String getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(String photoTime) {
		this.photoTime = photoTime;
	}
	@Column(name = "VISIBLE_PROPERTY")
	public Integer getVisibleProperty() {
		return visibleProperty;
	}

	public void setVisibleProperty(Integer visibleProperty) {
		this.visibleProperty = visibleProperty;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShopPhoto entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_shop_photo")
public class TShopPhoto implements java.io.Serializable {

	// Fields

	private String id;
	private String albumId;
	private String photoUrl;
	private String createTime;
	private Integer digNum;
	private String digRelationUserId;

	// Constructors

	/** default constructor */
	public TShopPhoto() {
	}

	/** minimal constructor */
	public TShopPhoto(String id, String albumId, String photoUrl) {
		this.id = id;
		this.albumId = albumId;
		this.photoUrl = photoUrl;
	}

	/** full constructor */
	public TShopPhoto(String id, String albumId, String photoUrl,
			String createTime) {
		this.id = id;
		this.albumId = albumId;
		this.photoUrl = photoUrl;
		this.createTime = createTime;
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

	@Column(name = "album_id", nullable = false, length = 64)
	public String getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	@Column(name = "photo_url", nullable = false, length = 150)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "create_time", length = 32)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "dig_num", length = 5)
	public Integer getDigNum() {
		return digNum;
	}

	public void setDigNum(Integer digNum) {
		this.digNum = digNum;
	}
	@Column(name = "dig_relation_user_id", length = 1000)
	public String getDigRelationUserId() {
		return digRelationUserId;
	}

	public void setDigRelationUserId(String digRelationUserId) {
		this.digRelationUserId = digRelationUserId;
	}

}
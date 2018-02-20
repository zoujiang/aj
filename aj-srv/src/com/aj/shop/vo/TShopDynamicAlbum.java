package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShopAlbum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_shop_dynamic_album")
public class TShopDynamicAlbum implements java.io.Serializable {

	// Fields

	private String id;
	private String shopId;
	private String userId;
	private String albumName;
	private String albumLogo;
	private String templateId;
	private String createTime;
	private Integer visibleProperty;
	private String description;

	

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

	
	@Column(name = "create_time", length = 32)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	@Column(name = "template_id")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
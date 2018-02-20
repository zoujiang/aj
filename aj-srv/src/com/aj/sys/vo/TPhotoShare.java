package com.aj.sys.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCallEnum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_photo_share")
public class TPhotoShare implements java.io.Serializable {

	// Fields

	private String id;
	private String url;
	private String shopId;

	// Constructors

	/** default constructor */
	public TPhotoShare() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "URL", nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name = "shop_id")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
package com.aj.brand.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShopBrand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_shop_brand")

public class TShopBrand implements java.io.Serializable {

	// Fields

	private String id;
	private String brandName;
	private String brandIcon;
	private Integer sortIndex;
	private Integer status;
	private Integer type;
	private Integer isRecommend;

	// Constructors

	/** default constructor */
	public TShopBrand() {
	}

	/** minimal constructor */
	public TShopBrand(String id, String brandName, String brandIcon, Integer status, Integer type,
			Integer isRecommend) {
		this.id = id;
		this.brandName = brandName;
		this.brandIcon = brandIcon;
		this.status = status;
		this.type = type;
		this.isRecommend = isRecommend;
	}

	/** full constructor */
	public TShopBrand(String id, String brandName, String brandIcon, Integer sortIndex, Integer status, Integer type,
			Integer isRecommend) {
		this.id = id;
		this.brandName = brandName;
		this.brandIcon = brandIcon;
		this.sortIndex = sortIndex;
		this.status = status;
		this.type = type;
		this.isRecommend = isRecommend;
	}

	// Property accessors
	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "brand_name", nullable = false, length = 200)

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "brand_icon", nullable = false, length = 200)

	public String getBrandIcon() {
		return this.brandIcon;
	}

	public void setBrandIcon(String brandIcon) {
		this.brandIcon = brandIcon;
	}

	@Column(name = "sort_index")

	public Integer getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type", nullable = false)

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "is_recommend", nullable = false)

	public Integer getIsRecommend() {
		return this.isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

}
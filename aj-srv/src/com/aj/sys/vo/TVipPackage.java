package com.aj.sys.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TVipPackage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_vip_package")

public class TVipPackage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String packageName;
	private String originalPrice;
	private String favorablePrice;
	private Integer packageDays;
	private Integer sortIndex;

	// Constructors

	/** default constructor */
	public TVipPackage() {
	}

	/** minimal constructor */
	public TVipPackage(String packageName) {
		this.packageName = packageName;
	}

	/** full constructor */
	public TVipPackage(String packageName, String originalPrice, String favorablePrice, Integer packageDays,
			Integer sortIndex) {
		this.packageName = packageName;
		this.originalPrice = originalPrice;
		this.favorablePrice = favorablePrice;
		this.packageDays = packageDays;
		this.sortIndex = sortIndex;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "package_name", nullable = false, length = 128)

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Column(name = "original_price", length = 10)

	public String getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(name = "favorable_price", length = 10)

	public String getFavorablePrice() {
		return this.favorablePrice;
	}

	public void setFavorablePrice(String favorablePrice) {
		this.favorablePrice = favorablePrice;
	}

	@Column(name = "package_days")

	public Integer getPackageDays() {
		return this.packageDays;
	}

	public void setPackageDays(Integer packageDays) {
		this.packageDays = packageDays;
	}

	@Column(name = "sort_index")

	public Integer getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
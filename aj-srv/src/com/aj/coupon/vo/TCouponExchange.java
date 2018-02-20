package com.aj.coupon.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCouponExchange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_coupon_exchange")

public class TCouponExchange implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer couponId;
	private String couponName;
	private String shopName;
	private String userId;
	private String userNick;
	private String userTel;
	private String exchangeTime;
	private String couponCode;

	// Constructors

	/** default constructor */
	public TCouponExchange() {
	}

	/** minimal constructor */
	public TCouponExchange(Integer couponId, String userId, String userTel, String exchangeTime) {
		this.couponId = couponId;
		this.userId = userId;
		this.userTel = userTel;
		this.exchangeTime = exchangeTime;
	}

	/** full constructor */
	public TCouponExchange(Integer couponId, String couponName, String shopName, String userId, String userNick,
			String userTel, String exchangeTime, String couponCode) {
		this.couponId = couponId;
		this.couponName = couponName;
		this.shopName = shopName;
		this.userId = userId;
		this.userNick = userNick;
		this.userTel = userTel;
		this.exchangeTime = exchangeTime;
		this.couponCode = couponCode;
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

	@Column(name = "coupon_id", nullable = false)

	public Integer getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	@Column(name = "coupon_name", length = 128)

	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Column(name = "shop_name", length = 128)

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "user_id", nullable = false, length = 64)

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_nick", length = 128)

	public String getUserNick() {
		return this.userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	@Column(name = "user_tel", nullable = false, length = 20)

	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "exchange_time", nullable = false, length = 30)

	public String getExchangeTime() {
		return this.exchangeTime;
	}

	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	@Column(name = "coupon_code", length = 10)

	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

}
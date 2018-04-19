package com.aj.pay.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TVipOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_vip_order")

public class TVipOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer vipPackageId;
	private String amountMoney;
	private String orderTime;
	private Integer isPay;
	private String payTime;
	private Integer payType;
	private String prepayId;
	private String tradeNo;
	private String orderId;
	

	// Constructors

	/** default constructor */
	public TVipOrder() {
	}

	/** minimal constructor */
	public TVipOrder(Integer userId, Integer vipPackageId, String amountMoney, Integer payType) {
		this.userId = userId;
		this.vipPackageId = vipPackageId;
		this.amountMoney = amountMoney;
		this.payType = payType;
	}

	/** full constructor */
	public TVipOrder(Integer userId, Integer vipPackageId, String amountMoney, String orderTime, Integer isPay,
			String payTime, Integer payType, String prepayId, String tradeNo) {
		this.userId = userId;
		this.vipPackageId = vipPackageId;
		this.amountMoney = amountMoney;
		this.orderTime = orderTime;
		this.isPay = isPay;
		this.payTime = payTime;
		this.payType = payType;
		this.prepayId = prepayId;
		this.tradeNo = tradeNo;
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

	@Column(name = "user_id", nullable = false)

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "vip_package_id", nullable = false)

	public Integer getVipPackageId() {
		return this.vipPackageId;
	}

	public void setVipPackageId(Integer vipPackageId) {
		this.vipPackageId = vipPackageId;
	}

	@Column(name = "amount_money", nullable = false, length = 10)

	public String getAmountMoney() {
		return this.amountMoney;
	}

	public void setAmountMoney(String amountMoney) {
		this.amountMoney = amountMoney;
	}

	@Column(name = "order_time", length = 32)

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "is_pay")

	public Integer getIsPay() {
		return this.isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	@Column(name = "pay_time", length = 32)

	public String getPayTime() {
		return this.payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	@Column(name = "pay_type", nullable = false)

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "prepay_id", length = 64)

	public String getPrepayId() {
		return this.prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Column(name = "trade_no", length = 64)

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	@Column(name = "order_id", length = 64)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
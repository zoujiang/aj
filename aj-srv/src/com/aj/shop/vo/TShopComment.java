package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShopComment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_shop_comment")
public class TShopComment implements java.io.Serializable {

	// Fields

	private String id;
	private String cmtUserId;
	private Integer score;
	private String cmtContent;
	private String cmtImg;
	private String type;
	private String cmtTime;
	private Integer status;
	private String cmtShopId;
	private Integer shopType;

	// Constructors

	/** default constructor */
	public TShopComment() {
	}

	/** minimal constructor */
	public TShopComment(String id, Integer status, String cmtShopId) {
		this.id = id;
		this.status = status;
		this.cmtShopId = cmtShopId;
	}

	/** full constructor */
	public TShopComment(String id, String cmtUserId, Integer score,
			String cmtContent, String cmtImg, String type, String cmtTime,
			Integer status, String cmtShopId) {
		this.id = id;
		this.cmtUserId = cmtUserId;
		this.score = score;
		this.cmtContent = cmtContent;
		this.cmtImg = cmtImg;
		this.type = type;
		this.cmtTime = cmtTime;
		this.status = status;
		this.cmtShopId = cmtShopId;
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

	@Column(name = "cmt_user_id", length = 64)
	public String getCmtUserId() {
		return this.cmtUserId;
	}

	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "cmt_content", length = 500)
	public String getCmtContent() {
		return this.cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	@Column(name = "cmt_img", length = 500)
	public String getCmtImg() {
		return this.cmtImg;
	}

	public void setCmtImg(String cmtImg) {
		this.cmtImg = cmtImg;
	}

	@Column(name = "type", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "cmt_time", length = 32)
	public String getCmtTime() {
		return this.cmtTime;
	}

	public void setCmtTime(String cmtTime) {
		this.cmtTime = cmtTime;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "cmt_shop_id", nullable = false, length = 64)
	public String getCmtShopId() {
		return this.cmtShopId;
	}

	public void setCmtShopId(String cmtShopId) {
		this.cmtShopId = cmtShopId;
	}
	@Column(name = "shop_type")
	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

}
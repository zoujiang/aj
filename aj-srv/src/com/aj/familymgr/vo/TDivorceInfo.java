package com.aj.familymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDivorceInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_divorce_info")
public class TDivorceInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String createUserId;
	private String relationUserId;
	private String applyDate;
	private Integer status;   //1：刚申请  2：同意  3：不同意
	private String dealDate;
	private String divorceDesc;

	// Constructors

	/** default constructor */
	public TDivorceInfo() {
	}

	/** minimal constructor */
	public TDivorceInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TDivorceInfo(String id, String createUserId, String relationUserId,
			String applyDate, Integer status, String dealDate) {
		this.id = id;
		this.createUserId = createUserId;
		this.relationUserId = relationUserId;
		this.applyDate = applyDate;
		this.status = status;
		this.dealDate = dealDate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "create_user_id", length = 64)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "relation_user_id", length = 64)
	public String getRelationUserId() {
		return this.relationUserId;
	}

	public void setRelationUserId(String relationUserId) {
		this.relationUserId = relationUserId;
	}

	@Column(name = "apply_date", length = 64)
	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "deal_date", length = 64)
	public String getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	@Column(name = "divorce_desc", length = 500)
	public String getDivorceDesc() {
		return divorceDesc;
	}

	public void setDivorceDesc(String divorceDesc) {
		this.divorceDesc = divorceDesc;
	}

}
package com.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_area")
public class TArea implements java.io.Serializable {
	private static final long serialVersionUID = -1330878241731643214L;
	private String areaId;
	private String areaName;
	private String createUser;
	private Timestamp createDt;
	private String isValid;
	private Integer areaOrder;
	private String modifyUser;
	private Timestamp modifyDt;
	private String areaPic;

	// Constructors

	/** default constructor */
	public TArea() {
	}

	/** full constructor */
	public TArea(String areaName, String createUser, Timestamp createDt,
			String isValid, Integer areaOrder, String modifyUser,
			Timestamp modifyDt, String areaPic) {
		this.areaName = areaName;
		this.createUser = createUser;
		this.createDt = createDt;
		this.isValid = isValid;
		this.areaOrder = areaOrder;
		this.modifyUser = modifyUser;
		this.modifyDt = modifyDt;
		this.areaPic = areaPic;
	}

	@Id
    @Column(name="AREA_ID", unique=true, nullable=false, length=32)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "AREA_NAME", length = 64)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "CREATE_DT", length = 19)
	public Timestamp getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "AREA_ORDER")
	public Integer getAreaOrder() {
		return this.areaOrder;
	}

	public void setAreaOrder(Integer areaOrder) {
		this.areaOrder = areaOrder;
	}
	@Column(name = "MODIFY_USER")
	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Column(name = "MODIFY_DT", length = 19)
	public Timestamp getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Timestamp modifyDt) {
		this.modifyDt = modifyDt;
	}

	@Column(name = "AREA_PIC", length = 512)
	public String getAreaPic() {
		return this.areaPic;
	}

	public void setAreaPic(String areaPic) {
		this.areaPic = areaPic;
	}

}
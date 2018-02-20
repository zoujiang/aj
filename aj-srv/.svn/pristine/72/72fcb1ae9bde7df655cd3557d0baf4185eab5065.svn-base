package com.aj.sys.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCallEnum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_enum")
public class TCallEnum implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer isShow;
	private Integer isPrivate;
	private Integer level;

	// Constructors

	/** default constructor */
	public TCallEnum() {
	}

	/** minimal constructor */
	public TCallEnum(String name, Integer isShow, Integer isPrivate) {
		this.name = name;
		this.isShow = isShow;
		this.isPrivate = isPrivate;
	}

	/** full constructor */
	public TCallEnum(String name, Integer isShow, Integer isPrivate,
			Integer level) {
		this.name = name;
		this.isShow = isShow;
		this.isPrivate = isPrivate;
		this.level = level;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IS_SHOW", nullable = false)
	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "IS_PRIVATE", nullable = false)
	public Integer getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}

	@Column(name = "LEVEL")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
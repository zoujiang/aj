package com.frame.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WZ_RES_BUSS")
public class WeResBussPo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4643320099426131269L;
	/** 业务类型ID */
	private String id;
	/** 业务类型名称 */
	private String name;

	public WeResBussPo() {

	}

	public WeResBussPo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "BUSS_ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BUSS_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


package com.frame.system.vo;

import java.util.List;

/**
 * 角色
 */
public class RoleExtForm {
	/**
	 * id标识
	 */
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 复选框选中状态，用于展现
	 */
	private boolean ck;

	/**
	 * 资源列表
	 * @return
	 */
	private List<ResourceExtForm> resourcesList;
	
	
	public boolean isCk() {
		return ck;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ResourceExtForm> getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(List<ResourceExtForm> resourcesList) {
		this.resourcesList = resourcesList;
	}
}

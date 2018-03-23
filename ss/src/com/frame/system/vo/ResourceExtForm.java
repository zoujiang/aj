package com.frame.system.vo;


/**
 * 资源
 */

public class ResourceExtForm {

	/**
	 * 资源Id
	 */
	private String id;

	/**
	 * 父节点
	 */
	private String parentId;

	/**
	 * 资源代码
	 */
	private String code;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 资源位置
	 */
	private String href;
	
	private String iconCls;
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 资源被使用
	 * @return
	 */
	private boolean isChecked = false;
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}

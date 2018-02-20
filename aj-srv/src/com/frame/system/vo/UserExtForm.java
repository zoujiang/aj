package com.frame.system.vo;

import java.util.List;

/**
 * 用户
 */

public class UserExtForm {
	
	/**
	 * 标识
	 */
	private  String id;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 系统登录帐号
	 */
	private String account;
	
	/**
	 * 手机号码
	 */
	private String tele;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 密码
	 */
	private String pwd;
	private String citizenId;

	private String accountType;	
	
	private String otherCode;	
	public String getOtherCode() {
		return otherCode;
	}

	public void setOtherCode(String otherCode) {
		this.otherCode = otherCode;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 启用状态
	 */
	private String isEnabled;
	
	/**
	 * 角色列表
	 */
	private List<RoleExtForm> roleExtList;
	
	/**
	 * 资源列表
	 * @return
	 */
	private List<ResourceExtForm> resourceExtFormsList;
	
	/**
	 * 资源树列表
	 * @return
	 */
	private TreeModel resourceTree;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<RoleExtForm> getRoleExtList() {
		return roleExtList;
	}

	public void setRoleExtList(List<RoleExtForm> roleExtList) {
		this.roleExtList = roleExtList;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<ResourceExtForm> getResourceExtFormsList() {
		return resourceExtFormsList;
	}

	public void setResourceExtFormsList(List<ResourceExtForm> resourceExtFormsList) {
		this.resourceExtFormsList = resourceExtFormsList;
	}

	public TreeModel getResourceTree() {
		return resourceTree;
	}

	public void setResourceTree(TreeModel resourceTree) {
		this.resourceTree = resourceTree;
	}
	
}

package com.frame.system.service;

import com.frame.core.vo.DataModel;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.TreeModel;



public interface RoleManager {
	
	/**
	 * 查询角色列表
	 */
	@SuppressWarnings("unchecked")
	public DataModel getRoles(String ...args);
	
	/**
	 * 通过角色
	 */
	public RoleExtForm getRoleById(String roleId);
	
	/**
	 * 增加角色
	 */
	public void addRole(RoleExtForm roleExtForm);
	
	/**
	 * 修改角色
	 */
	public void updateRole(RoleExtForm roleExtForm);
	
	/**
	 * 删除角色
	 */
	public void deleteRole(String...  roleIds);
	
	/**
	 * 指定资源
	 */
	public void exeAssignResouces(String roleId,String[] resourceIds);
	
	/**
	 * 获取角色所具有的资源树
	 */
	public  TreeModel getResourceOfRole(String roleId);
	/**
	 * 根据roleIds获取该角色下面的用户
	 * @param roleIds
	 * @return
	 */
	public DataModel getUsersByRoleIds(String roleIds);
	
	
}

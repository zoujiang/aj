package com.frame.system.service;

import java.util.List;

import com.frame.core.vo.DataModel;
import com.frame.system.po.Role;
import com.frame.system.po.RoleResource;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.TreeModel;


public interface RoleService {
	/**
	 * 查询角色列表
	 */
	public List<RoleExtForm> getRoles(String ...args);
	
	/**
	 * 通过角色
	 */
	public RoleExtForm getRoleById(String roleId);
	
	/**
	 * 增加角色
	 */
	public void addRole(RoleExtForm roleExtForm);

	List<Role> getRolesByCode(String code);

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
	 * 获取角色所具有的资源
	 */
	public List<RoleResource> getResoucerOfRole(String roleId);

	/**
	 * 构建资源树型结构
	 * @param resourceExtForms
	 * @return
	 */
	public TreeModel generateResourceTree(List<ResourceExtForm> resourceExtForms);
	/**
	 * 根据roleId获取该角色下面的用户
	 * @param roleId
	 * @return
	 */
	public DataModel getUsersByRoleIds(String roleId);
	
	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param rid
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-8      Ching Wang     v1.0.0      create
	 *
	 */
	public Role getRole(String rid);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param rid
	 * @param state
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-8      Ching Wang     v1.0.0      create
	 *
	 */
	public int modifyState(String rid, String state);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param roleName
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-8      Ching Wang     v1.0.0      create
	 *
	 */
	public long getTotal(String roleName);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param roleName
	 * @param offset
	 * @param limit
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-8      Ching Wang     v1.0.0      create
	 *
	 */
	public List<Role> getList(String roleName, int offset, int limit);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param role
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-9      Ching Wang     v1.0.0      create
	 *
	 */
	public void saveOrUpdateRole(Role role);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-11      Ching Wang     v1.0.0      create
	 *
	 */
	public List<Role> getListAll();
}

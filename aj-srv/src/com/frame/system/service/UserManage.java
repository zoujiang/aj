package com.frame.system.service;

import com.frame.core.vo.DataModel;
import com.frame.system.po.User;
import com.frame.system.vo.UserExtForm;
import com.frame.system.vo.UserVo;

public interface UserManage {
	/**
	 * 查询用户列表
	 */
	@SuppressWarnings("unchecked")
	public DataModel getUsers(String ...args);
	
	/**
	 * 获取用户基本信息
	 */
	public UserExtForm getUserById(String userId);
	
	/**
	 * 增加用户
	 * @return TODO
	 */
	public boolean addUser(UserExtForm userExtForm);
	/**
	 * 修改用户
	 * @return TODO
	 */
	public boolean updateUser(UserExtForm userExtForm);
	
	/**
	 * 删除用户
	 */
	public void deleteUser(String  userIds);
	
	/**
	 * 指定角色
	 */
	public void assignRoles(String userId,String []roleIds);
	
    
	/**
	 * 获取用户具有角色的角色列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataModel getRolesOfUser(String userId);
	
	/**
	 * 更新用户的启用状态
	 */
	public void setUserState(String state,String ...id);
	
	/**
	 * 获取用户基本信息
	 * @return TODO
	 */
	public UserExtForm getUserInfo(String userId);
	
	
	/**
	 * 获取Login信息
	 * @param loginName
	 * @param password
	 * @param checkCode
	 * @return TODO
	 */
	public String getLoginInfo(String loginName, String password);
	
	/**
	 * 根据登录名称和密码获查找用户信息
	 * @param loginName
	 * @param password
	 * @return
	 */
	public UserVo getUserInfoByLoginNameAndPassword(String loginName, String password);
	/**
	 * 根据登录名称和密码获查找用户信息
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User getUserPoByLoginNameAndPassword(String loginName, String password);
	/**
	 * 检查帐号是否存在
	 * @param id
	 * @param account
	 * @return
	 */
	boolean checkUserAccountExisted(String id, String account);
	
	/**
	 * 检查手机号码是否存在
	 * @param id
	 * @param mobile
	 * @return
	 */
	boolean checkUserMobileExisted(String id, String mobile);
	
	
	/**
	 * 检查email是否存在 
	 * @param id
	 * @param email
	 * @return
	 */
	boolean checkUseEmailExisted(String id, String email);
	
	/**
	 * 修改秘密
	 * @param userid
	 * @param password
	 * @param newspassword
	 * @return
	 */
	boolean exeResetPassWord(String userid, String password, String newspassword);
	/**
	 * 关联用户和角色
	 * @param user
	 * @return
	 */

	void exeAssignRoles(String userId,String []roleIds) ;
}

package com.frame.system.service;

import java.util.List;

import com.frame.core.vo.DataModel;
import com.frame.system.po.User;
import com.frame.system.po.UserRole;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.UserExtForm;
import com.frame.system.vo.UserVo;

public interface UserService {
	/**
	 * 查询用户列表
	 */
	public DataModel getUsers(String ...args);
	
	/**
	 * 通过脚色
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
	 * 获取角色
	 */
	public List<UserRole> getRolesByUserId(String userId);
	
	/**
	 * 更新用户状态
	 * @param state
	 * @param id
	 */
	public void updateUserState(String state,String ...id);
	
	/**
	 * 指定用户角色
	 * @param userId
	 * @param roleId
	 */
	public void exeAssignRoles(String userId, String[] roleId);
	
	
	/**
	 * 获取用户所有资源信息
	 * @param userId
	 * @return
	 */
	public List<ResourceExtForm> getUserResource(String userId);
	
	/**
	 * 获取登录信息，
	 * 如果成功，返回用户Id，否则返回null;
	 */
	public String getLoginInfo(String loginName,String password);

	/**
	 * 获取登录信息，
	 * 如果成功，返回用户Id，否则返回null;
	 */
	public String getLoginInfo(String loginName, String password,String type);

	/**
	 * 获取用户信息
	 * @param loginName
	 * @param password
	 * @return
	 */
	public UserVo getUserInfoByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 获取用户PO实体
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User getUserPoByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 执行重置密码
	 * @param newspassword TODO
	 * @return TODO
	 */
	public boolean exeResetPassWord(String userid,String password, String newspassword);
	
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
	 * 根据帐号列表获取用户List
	 * @param accounts帐号列表 多个值用，号隔开 如("ams_admin,ams_exe");
	 * @return List<User>
	 */
	public List<User> queryListByAccounts(String accounts);
	
	/**
	 * 根据帐号列表获取Mobile
	 * @param accounts帐号列表 多个值用，号隔开 如("ams_admin,ams_exe");
	 * @return 返回电话列表，获得数据字符串返回，格式如("13534256324,12433262342")
	 */
	public String queryStrMobiles(String accounts);
	
	/**
	 * 根据帐号列表获取Emails
	 * @param accounts帐号列表 多个值用，号隔开 如("ams_admin,ams_exe");
	 * @return 返回邮件列表，获得数据字符串返回，格式如("lishun@mymost.com.cn,shengpi@mymost.com.cn")
	 */
	public String queryStrEmails(String accounts);
	
	/**
	 * 
	 * @param accounts 帐号列表 多个值用，号隔开 如("ams_admin,ams_exe");
	 * @return  String []数组格式，包含邮件和电话号码<br>
	 * String[0]为 Email列表,如：string[0]="lishun@mymost.com.cn,shengpi@mymost.com.cn"<br>
	 * String[1]为 Mobile列表,如：string[1]="13534256324,12433262342"
	 */
	public String [] queryStrEmailAndMobiles(String accounts);

    /**
     * 根据帐号类型获取用户List
     * 
     * @param type帐号列表
     * @return List<?>
     */
    public List<?> queryListByType(List<Integer> type);
}

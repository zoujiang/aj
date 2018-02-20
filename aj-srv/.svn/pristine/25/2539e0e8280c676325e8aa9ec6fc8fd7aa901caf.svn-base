package com.frame.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.system.po.User;
import com.frame.system.po.UserRole;
import com.frame.system.service.RoleService;
import com.frame.system.service.UserManage;
import com.frame.system.service.UserService;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.UserExtForm;
import com.frame.system.vo.UserVo;

@Component
public class UserManagerImpl implements UserManage{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Override
	public boolean addUser(UserExtForm userExtForm) {
		return userService.addUser(userExtForm);
		
	}


	@Override
	public void deleteUser(String userIds) {
		userService.deleteUser(userIds);
	}

	@Override
	public UserExtForm getUserById(String userId) {
		return userService.getUserById(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel getUsers(String... args) {
		
		return userService.getUsers(args);
	}

	@Override
	public boolean updateUser(UserExtForm userExtForm) {
		return userService.updateUser(userExtForm);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataModel getRolesOfUser(String userId){
		List<RoleExtForm> roleList =  roleService.getRoles();
		List<UserRole> userRoleList = userService.getRolesByUserId(userId);
		DataModel  mode = new DataModel();
		for(RoleExtForm r:roleList){
			for(UserRole ur:userRoleList){
				if(r.getId().equals(ur.getRoleId())){
					r.setCk(true);
					break;
				}
			}
		}
		mode.setRows(roleList);
		mode.setTotal(roleList.size());
		return mode;
	}
	
	
	@Override
	public boolean exeResetPassWord(String userid,String password, String newspassword){
		return userService.exeResetPassWord(userid, password, newspassword);
	}
	
	
	@Override
	public void setUserState(String state,String ...id){
		userService.updateUserState(state, id);
	}


	@Override
	public void assignRoles(String userId, String[] roleIds) {
		userService.exeAssignRoles(userId, roleIds);
	}
	
	@Override
	public String getLoginInfo(String loginName,String password){
		return this.userService.getLoginInfo(loginName, password);
	}

	public UserVo getUserInfoByLoginNameAndPassword(String loginName, String password) {
		return userService.getUserInfoByLoginNameAndPassword(loginName, password);
	}
	public User getUserPoByLoginNameAndPassword(String loginName, String password) {
		return userService.getUserPoByLoginNameAndPassword(loginName, password);
	}
	
	@Override
	public UserExtForm getUserInfo(String userId) {
		UserExtForm userExtForm = userService.getUserById(userId);
		userExtForm.setRoleExtList(getRoleofUser(userId));
		userExtForm.setResourceExtFormsList(userService.getUserResource(userId));
		userExtForm.setResourceTree(roleService.generateResourceTree(userExtForm.getResourceExtFormsList()));
		return userExtForm;
	}
	
	//通过用户id去获得角色列表
	private List<RoleExtForm> getRoleofUser(String userId){
		List<RoleExtForm> roleList =  roleService.getRoles();
		List<UserRole> userRoleList = userService.getRolesByUserId(userId);
		List<RoleExtForm> retlist = new ArrayList<RoleExtForm>();
		for(RoleExtForm r:roleList){
			for(UserRole ur:userRoleList){
				if(r.getId().equals(ur.getRoleId())){
					r.setCk(true);
					retlist.add(r);
					break;
				}
			}
		}
	
		return retlist;
	}


	@Override
	public boolean checkUserAccountExisted(String id, String account) {
		return this.userService.checkUserAccountExisted(id, account);
	}


	@Override
	public boolean checkUserMobileExisted(String id, String mobile) {
		return this.userService.checkUserMobileExisted(id, mobile);
	}


	@Override
	public boolean checkUseEmailExisted(String id, String email) {
		return this.userService.checkUseEmailExisted(id, email);
	}


	@Override
	public void exeAssignRoles(String userId, String[] roleIds) {
		this.userService.exeAssignRoles(userId, roleIds);
	}

	
}

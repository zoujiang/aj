package com.frame.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.LimitKey;
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
	
	@Autowired
	private GenericDAO baseDAO;

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


	@Override
	public List<UserVo> getList(LimitKey key) {
		List<UserVo> list=new ArrayList<UserVo>();
		String sql="SELECT * FROM  T_SYS_USER";
		Query query = baseDAO.getSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(key.getOffset());// 查询起始记录
		query.setMaxResults(key.getPageSize());// 查询结束记录
		List lsquery = query.list();
		for (int i = 0; i < lsquery.size(); i++) {
			Map map = (Map) lsquery.get(i);
			UserVo vo = new UserVo();
			vo.setAccount(map.get("ACCOUNT") == null ? "" : map
					.get("ACCOUNT") + "");
		    vo.setEmail(map.get("EMAIL") == null ? "" : map
					.get("EMAIL") + "");
		    vo.setId(map.get("ID") == null ? "" : map
					.get("ID") + "");
		    vo.setIsEnabled(map.get("ISENABLED") == null ? "" : map
					.get("ISENABLED") + "");
		    vo.setMobile(map.get("MOBILE") == null ? "" : map
					.get("MOBILE") + "");
		    vo.setName(map.get("NAME") == null ? "" : map
					.get("NAME") + "");
		    vo.setPwd(map.get("PWD") == null ? "" : map
					.get("PWD") + "");
		    vo.setTele(map.get("TELE") == null ? "" : map
					.get("TELE") + "");
		    list.add(vo);
		 }
		return list;
	}


	@Override
	public int getCountTotalUserList(LimitKey key) {
		String sql="SELECT COUNT(1) FROM T_SYS_USER";
		Query query = baseDAO.getSession().createSQLQuery(sql);
		List lsquery = query.list();
		return Integer.parseInt(lsquery.get(0).toString());
	}

	
}

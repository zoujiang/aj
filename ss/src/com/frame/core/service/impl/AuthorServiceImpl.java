package com.frame.core.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.frame.core.constant.Constant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.service.AuthorService;
import com.frame.core.util.BeanCopy;
import com.frame.core.util.StringUtil;
import com.frame.system.po.Role;
import com.frame.system.po.User;
import com.frame.system.vo.UserExtForm;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private GenericDAO genericDAO;
	/**
	 * 	获取当前登录用户VO
	 */
	@Override
	public UserExtForm getSessionUser(){
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		UserExtForm loginUser = (UserExtForm)request.getSession().getAttribute(Constant.LoginAdminUser);
		return loginUser;
	}
	
	/**
	 * 	获取当前登录用户Po
	 */
	@Override
	public User getSessionUserPo(){
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		UserExtForm loginUser = (UserExtForm)request.getSession().getAttribute(Constant.LoginAdminUser);
		User cmsUser = null;
		if(loginUser != null){
		// cmsUser = genericDAO.get(User.class,loginUser.getId());
			cmsUser = BeanCopy.copy(User.class, loginUser);
			//BeanUtilEx.copyProperties(loginUser,cmsUser);
		}
		//User tuser = new User();
	
		return cmsUser;
	}
	/**
	 * 判断该创建用户是否为系统是配置的角色（满足系统角色中的其中一个）
	 * @param userid 	当前用户的userid
	 * @return
	 */
	@Override
	public boolean existInRole(String roleid) {
		//if(StringUtil.isNotEmpty(roleid)){return true;}
		UserExtForm loginUser = getSessionUser();
		if(loginUser == null)return false;
		List<Role> list=genericDAO.getGenericByHql("from Role r inner join fetch r.users u where u.id=?", loginUser.getId());
		for(Role r:list){
			if (-1 != roleid.indexOf(r.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断创建用户是否为当前用户（满足谁创建谁修改的原则）
	 * @param account	创建用户的账号
	 * @return
	 */
	@Override
	public boolean isCreateUser(String account) {
		UserExtForm loginUser = getSessionUser();
		
		if(StringUtil.isNotEmpty(account) && account.equals(loginUser.getAccount())){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是Admin操作用户(admin 是唯一的)
	 * @return TRUE
	 */
	@Override
	public boolean isAdminOptAccount() {
		UserExtForm currentPo=getSessionUser();
		if("admin".equals(currentPo.getAccount())){
			return true;
		}
		return false;
	}
	
	@Override
	public HttpServletResponse getResponse() {
		HttpServletResponse response=((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
	@Override
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes()).getRequest();
		return request;
	}
}

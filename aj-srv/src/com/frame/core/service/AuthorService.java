package com.frame.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frame.system.po.User;
import com.frame.system.vo.UserExtForm;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface AuthorService {
	/**
	 * 判断该创建用户是否为系统是配置的角色（满足系统角色中的其中一个）
	 * @param userId 	当前用户的id
	 * @return
	 */
	boolean existInRole(String roleid);
	
	
	/**
	 * 判断创建用户是否为当前用户（满足谁创建谁修改的原则）
	 * @param createUserId	创建用户的id
	 * @return
	 */
	boolean isCreateUser(String createUserId);


	public UserExtForm getSessionUser();


	public User getSessionUserPo();
	
	HttpServletResponse getResponse();
	HttpServletRequest getRequest();


	boolean isAdminOptAccount();
}

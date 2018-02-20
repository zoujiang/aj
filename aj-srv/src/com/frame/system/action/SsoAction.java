package com.frame.system.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.core.action.BaseAction;
import com.frame.core.constant.Constant;
import com.frame.core.util.BeanCopy;
import com.frame.system.po.Role;
import com.frame.system.po.User;
import com.frame.system.service.UserManage;
import com.frame.system.vo.UserExtForm;
import com.frame.system.vo.UserVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class SsoAction extends BaseAction {
	@Autowired
	private UserManage userManage;

	@RequestMapping(value = "/SsoAction")
	public void desktop(HttpServletRequest request, HttpServletResponse response) {
		UserExtForm userForm = (UserExtForm) request.getAttribute("userForm");
		String uri = (String) request.getAttribute("uri");
		String roleId = (String) request.getAttribute("roleId");

		String[] roleIds = roleId.split(",");
		String userAccount = userForm.getAccount();
		String ps = userForm.getPwd();

		UserVo userInfo = null;
		User user = userManage.getUserPoByLoginNameAndPassword(userAccount, ps);

		if (user == null) {
			if (userManage.addUser(userForm)) {
				userInfo = userManage.getUserInfoByLoginNameAndPassword(
						userAccount, ps);// userId
				userManage.exeAssignRoles(userInfo.getId(), roleIds);
			}
		} else {
			if (!user.isEquals(userForm)) {
				userForm.setId(user.getId());
				userForm.setIsEnabled(user.getIsEnabled());
				userForm.setPwd(user.getPwd());
				userForm.setTele(user.getTele());
				userManage.updateUser(userForm);
			}
			List<String> list1 = Arrays.asList(roleIds);
			Set<Role> set = user.getRoles();
			List<String> list2 = new ArrayList<String>(4);
			for (Role role : set) {
				list2.add(role.getId());
			}
			userInfo = BeanCopy.copy(UserVo.class, user);
			if (!CollectionUtils.isEqualCollection(list1, list2)) {
				userManage.exeAssignRoles(user.getId(), roleIds);
			}
		}
		try {
			UserExtForm userExtForm = userManage.getUserInfo(userInfo.getId());
			request.getSession().setAttribute(Constant.LoginAdminUser,
					userExtForm);
			request.getSession().setAttribute(Constant.Login, userInfo.getId());
			String uri2 = uri.substring(uri.indexOf("/", 1));
			request.getRequestDispatcher(uri2).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ModelAndView mv=new ModelAndView();
		// System.out.println("uri2="+uri2);
		// mv.setViewName("redirect:"+uri2);
		// mv.addObject(Constant.LoginAdminUser,request.getSession().getAttribute(Constant.LoginAdminUser));
		// return mv;
	};
}

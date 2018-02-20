package com.frame.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.BaseAction;
import com.frame.core.util.ResponseEntityUtil;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;
import com.frame.system.service.RoleManager;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.TreeModel;
import com.frame.system.vo.UserExtForm;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class RoleAction extends BaseAction {
	
	@Autowired
	private RoleManager roleManager;

	/**
	 * 获取角色列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/role/getRoles")
	@ResponseBody
	public DataModel getRoles(HttpServletRequest requert,
			HttpServletResponse response) {
		try {
			DataModel model = roleManager.getRoles();
			return model;
		} catch (Exception e) {
			log.error("获取角色发生异常===="+e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * 新增角色
	 */
	@RequestMapping("/role/addRole")
	@ResponseBody
	public ResponseEntity<MessageModel> addRole(RoleExtForm role){
		try{
			roleManager.addRole(role);
			return ResponseEntityUtil.getResponseEntity(Success);
		}catch (Exception e) {
			log.error("新增角色发生异常===="+e.getStackTrace());
			return  null;
		}
	}
		
	/**
	 * 修改角色
	 */
	@RequestMapping("/role/updateRole")
	@ResponseBody
	public  ResponseEntity<MessageModel>  updateRole(RoleExtForm role){
		try{
			roleManager.updateRole(role);
			return ResponseEntityUtil.getResponseEntity(Success);
		}catch (Exception e) {
			log.error("修改角色发生异常===="+e.getStackTrace());
			return null;
		}
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/role/deleteRole")
	@ResponseBody
	public MessageModel deleteRole2(@RequestParam(value = "id", required = true) String id){
		try{
			roleManager.deleteRole(id);
			return Success;
		}catch (Exception e) {
			log.error("删除角色发生异常===="+e.getStackTrace());
			return Failure;
		}
	}
	
	@RequestMapping("/role/getResourceOfRole")
	@ResponseBody
	public List<TreeModel> getResourceOfRole(@RequestParam(value = "roleId", required = false) String roleId,@RequestParam(value = "id", required = false) String resouceId) {
		try {
//			if(StringUtil.isEmpty(resouceId)||"root".equals(resouceId)){
//				TreeModel treeModel = new TreeModel();
//				treeModel.setId("root");
//				treeModel.setText("系统资源");
//				treeModel.setState("open");
//				
//				List<TreeModel> list = new ArrayList<TreeModel>();
//				list.add(treeModel);
//				List<TreeModel> childList = roleManager.getResourceOfRole(roleId);
//				if(childList!=null&&childList.size()>0){
//					treeModel.setChildren(childList.toArray(new TreeModel[childList.size()]));
//				}
//				return list;
//			}else{
//				return roleManager.getResourceOfRole(roleId);
//			}
			
			List<TreeModel> list = new ArrayList<TreeModel>();
			list.add(roleManager.getResourceOfRole(roleId));
			return list;
		} catch (Exception e) {
			log.error("获取角色资源发生异常====" + e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * 角色配置资源
	 */
	@RequestMapping("/role/roleConfigResource")
	@ResponseBody
	public MessageModel roleConfigResouce(@RequestParam(value = "ids[]", required = true) String[] resourceIds,@RequestParam(value = "id", required = true) String roleId){
		try { 
			roleManager.exeAssignResouces(roleId, resourceIds);
			return Success;
		}catch(Exception e){
			log.error("保存角色资源发生异常====" + e.getStackTrace());
			return Failure;
		}
	}
	
	/**
	 * 获取审核人员
	 */
	@RequestMapping("/role/getApproveUserByRoleId")
	@ResponseBody
	public DataModel getApproveUserByRoleId(String roleIds){
		DataModel model = new DataModel<UserExtForm>();
		try {
			 model = roleManager.getUsersByRoleIds(roleIds);
			return model;
		} catch (Exception e) {
			log.error("获取用户发生异常====" + e.getStackTrace());
		}
		return model;
	}

}

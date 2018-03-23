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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.BaseAction;
import com.frame.core.util.ListRange;
import com.frame.core.util.LogUtil;
import com.frame.core.util.ResponseEntityUtil;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;
import com.frame.system.po.Role;
import com.frame.system.po.RoleResource;
import com.frame.system.service.RoleManager;
import com.frame.system.service.RoleService;
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
	@Autowired RoleService roleService;

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

	@RequestMapping("/role/list")
	@ResponseBody
	public String list(String roleName, int offset, int limit) {
		ListRange<Role> range = new ListRange<Role>();
		try{
			long total = roleService.getTotal(roleName);
			range.setTotal(total);
			List<Role> list = new ArrayList<Role>();
			if(total > 0){
				list = roleService.getList(roleName, offset, limit);
				if(list != null && list.size() > 0){
					for(Role role : list){
						role.setUsers(null);
					}
				}
			}
			range.setRows(list);
		}catch (Exception e) {
			LogUtil.info("查询数据异常{}", e.getLocalizedMessage());
		}
		return JSONObject.toJSONString(range);
	}
	
	@RequestMapping("/role/listAll")
	@ResponseBody
	public String listAll() {
		ListRange<Role> range = new ListRange<Role>();
		try{
			long total = roleService.getTotal("");
			range.setTotal(total);
			List<Role> list = new ArrayList<Role>();
			if(total > 0){
				list = roleService.getListAll();
			}
			range.setRows(list);
		}catch (Exception e) {
			LogUtil.info("查询数据异常{}", e.getLocalizedMessage());
		}
		return JSONObject.toJSONString(range);
	}
	
	@RequestMapping("/role/modifyState")
	@ResponseBody
	public String modifyState(String rid, String state){
		JSONObject obj = new JSONObject();
		if(StringUtil.isEmpty(rid)){
			obj.put("success", false);
			obj.put("message", "角色ID不能为空");
		} else {
			if(roleService.modifyState(rid, state) > 0){
				obj.put("success", true);
				obj.put("message", "操作成功");
			} else {
				obj.put("success", false);
				obj.put("message", "操作失败");
			}
		}
		return obj.toJSONString();
	}
	
	@RequestMapping("/role/getRoleResIds")
	@ResponseBody
	public String getRoleResIds(String rid){
		List<String> list = new ArrayList<String>();
		List<RoleResource> ur = roleService.getResoucerOfRole(rid);
		if(ur!= null && ur.size() > 0){
			for(RoleResource rr : ur){
				list.add(rr.getResourceId());
			}
		}
		return JSONArray.toJSONString(list);
	}
	
	@RequestMapping("/role/edit")
	@ResponseBody
	public String edit(Role role){
		JSONObject obj = new JSONObject();
		try {
			roleService.saveOrUpdateRole(role);
			obj.put("success", true);
			obj.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
			obj.put("message", "操作失败");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping("/role/setRoleRes")
	@ResponseBody
	public String setRoleRes(String resourceIds, String roleId){
		JSONObject obj = new JSONObject();
		try { 
			roleManager.exeAssignResouces(roleId, resourceIds.split(","));
			obj.put("success", true);
			obj.put("message", "操作成功");
		}catch(Exception e){
			log.error("保存角色资源发生异常====" + e.getStackTrace());
			obj.put("success", false);
			obj.put("message", "操作失败");
		}
		return obj.toJSONString();
	}
}

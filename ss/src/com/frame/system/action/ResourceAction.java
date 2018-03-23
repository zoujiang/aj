package com.frame.system.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.BaseAction;
import com.frame.core.util.ResponseEntityUtil;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.MessageModel;
import com.frame.system.po.Resource;
import com.frame.system.service.ResourceManage;
import com.frame.system.service.ResourceService;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.TreeModel;
import com.frame.system.vo.TreeModel.AttributesModel;

@Controller
public class ResourceAction extends BaseAction {

	@Autowired
	private ResourceManage resourceManager;
	@Autowired ResourceService resourceService;
	/**
	 * 获取资源列表
	 */
	@RequestMapping("/resource/getResources")
	@ResponseBody
	public List<TreeModel> getResources(@RequestParam(value = "id", required = false) String id) {
		try {
			if(id==null||"root".equals(id)){
				TreeModel treeModel = new TreeModel();
				treeModel.setId("root");
				treeModel.setText("系统资源");
				treeModel.setState("open");
				
				List<TreeModel> list = new ArrayList<TreeModel>();
				list.add(treeModel);
				List<ResourceExtForm> resourceList = resourceManager.getResourcesByParentId(null);
				resourceConventerToTreeModel(resourceList);
				treeModel.setChildren(resourceConventerToTreeModel(resourceList).toArray(new TreeModel[resourceList.size()]));
				return list;
			}else{
				List<ResourceExtForm> resourceList = resourceManager.getResourcesByParentId(id);
				return resourceConventerToTreeModel(resourceList);
			}
		} catch (Exception e) {
			log.error("获取资源发生异常====" + e.getStackTrace());
		}
		return null;
	}

	/**
	 * 新增或更新资源
	 */
	@RequestMapping("/resource/saveResource")
	@ResponseBody
	public ResponseEntity<List<TreeModel>> addResource(ResourceExtForm resource) {
		ResourceExtForm ref = null;
		try {
			if(StringUtil.isEmpty(resource.getId())){
				ref = resourceManager.addResource(resource);
			}else{
				ref = resourceManager.updateResource(resource);
			}
			List<ResourceExtForm> list = new ArrayList<ResourceExtForm>();
			list.add(ref);
			
			return ResponseEntityUtil.getResponseEntity(resourceConventerToTreeModel(list));
		} catch (Exception e) {
			log.error("新增资源发生异常====" + e.getStackTrace());
		}
		return null;
	}
	
	
	@RequestMapping("/resource/checkResourceCode")
	@ResponseBody
	public MessageModel checkResourceCode(@RequestParam(value = "id", required = false)String id,
			@RequestParam(value = "code", required = true)String code) {
		try {
			boolean r = resourceManager.checkResouceCode(id, code);
			if(r){
				return new MessageModel(false,"资源编号已经存在");
			}else{
				return new MessageModel(true,"资源编号已经不存在");
			}
		} catch (Exception e) {
			log.error("删除资源发生异常====" + e.getStackTrace());
			return Failure;
		}
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resource/deleteResource")
	@ResponseBody
	public MessageModel deleteResource2(@RequestParam(value = "id", required = true)
			String id) {
		try {
			resourceManager.deleteResource(id);
			return Success;
		} catch (Exception e) {
			log.error("删除资源发生异常====" + e.getStackTrace());
			return Failure;
		}
	}
	
	/**
	 * 将资源转换为对应的TreeModel
	 */
	private List<TreeModel> resourceConventerToTreeModel(List<ResourceExtForm> resourceList) {
		List<TreeModel> treeModelList = new ArrayList<TreeModel>();
		ResourceExtForm resource;
		
		TreeModel treeModel;
		AttributesModel attributeModel;
		for (int i = 0; i < resourceList.size(); i++) {
			resource = resourceList.get(i);
			
			treeModel = new TreeModel();
			treeModel.setId(resource.getId());
			treeModel.setText(resource.getTitle());
			treeModel.setState(isHasNodes(resource.getId()));
			
			attributeModel = treeModel.new AttributesModel();
			attributeModel.setCode(resource.getCode());
			attributeModel.setParentId(resource.getParentId());
			attributeModel.setHref(resource.getHref());
			
			treeModel.setAttributes(attributeModel);
			treeModelList.add(treeModel);
		}
		return treeModelList;
	}
	
	/**
	 * 判断节点是否有子节点
	 */
	private String isHasNodes(String id){
		if(resourceManager.getResourcesByParentId(id).size() < 1){
			return "open";
		}
		return "closed";
	}
	
	/**
	 * 获取资源列表
	 */
	@RequestMapping("/resource/getRess")
	@ResponseBody
	public List<JSONObject> getRess() {
		
		List<JSONObject> menu = null;
		try {
			menu = resourceService.loadMenu();//this.getMenu("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menu;
	}
	@RequestMapping("/resource/editRes")
	@ResponseBody
	public String editRes(Resource res, String opt){
		JSONObject obj = new JSONObject();
		resourceService.addRes(res);
		if(res != null){
			obj.put("success", true);
			obj.put("message", "add".equals(opt)?"添加成功":"编辑成功");
		} else {
			obj.put("success", false);
			obj.put("message", "add".equals(opt)?"添加失败":"编辑失败");
		}
		return obj.toString();
	}
	
	@RequestMapping("/resource/desRes")
	@ResponseBody
	public String delRes(String id){
		JSONObject obj = resourceService.delRes(id);
		return obj.toString();
	}
	
	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param string
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-8-31      Ching Wang     v1.0.0      create
	 *
	 */
	private List<JSONObject> getMenu(String pid) throws Exception {
		List<JSONObject> objects = new ArrayList<JSONObject>();
		List<ResourceExtForm> resourceList = resourceManager.getResourcesByParentId(null);
		for(ResourceExtForm resource : resourceList){
			JSONObject object = new JSONObject();
			object.put("id", resource.getId());
			object.put("text", resource.getTitle());
			//object.put("icon", "");
			object.put("href", resource.getHref());
			object.put("code", resource.getCode());
			JSONObject state = new JSONObject();
			if(StringUtil.isEmpty(pid)){
				state.put("opened", true);
				/*state.put("disabled", false);
				state.put("selected", false);*/
			}
			if(resourceManager.getResourcesByParentId(resource.getId()).size() > 0){
				object.put("children", this.getMenu(resource.getId()));
			} else {
				state.put("opened", true);
			}
		}
		return objects;
	}
}

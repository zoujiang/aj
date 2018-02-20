package com.frame.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.GUID;
import com.frame.core.util.ListUtils;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.StringUtil;
import com.frame.log.service.LogBizOprService;
import com.frame.system.po.Resource;
import com.frame.system.service.ResourceService;
import com.frame.system.vo.ResourceExtForm;

@Service
public class ResourceServiceImpl  implements ResourceService{
	
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public boolean checkResouceCode(String id,String code){
		boolean isExist = false;
		String sql = " select count(*) from Resouce r where r.code= '"+code+"' ";
		if(!StringUtil.isEmpty(id)){
			sql += " r.id != '"+id+"' ";
		}
		int i = genericDAO.getGenericCountByHql(sql);
		if(i>0) isExist = true;
		return isExist;
	}

	@Override
	public ResourceExtForm addResource(ResourceExtForm resourceExtForm) {
		Resource cmsResource = BeanUtils.copyProperties(Resource.class, resourceExtForm);
		cmsResource.setId(RandomGUID.newGuid());
		genericDAO.save(cmsResource);
		logBizOprService.saveLog("菜单管理","1" , "新增菜单(菜单名称:" + resourceExtForm.getTitle() + ")" , "t_sys_resource", cmsResource.toString());
//		if(!StringUtil.isEmpty(cmsResource.getParentId())){
//			Resource parentResouce = genericDAO.get(Resource.class, cmsResource.getParentId());
//		}
		
		
		resourceExtForm.setId(cmsResource.getId());
		return resourceExtForm;
	}
	
	/**
	 *获取一级子节点
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<ResourceExtForm> getResourcesByParentId(String parentId){
		StringBuffer sb = new StringBuffer();
		sb.append(" from Resource r  where  1=1 ");
		if(parentId!=null&&!parentId.trim().equals("")){
			sb.append(" and r.parentId = '").append(parentId).append("' ");
		}else{
			sb.append(" and ( r.parentId ='' or r.parentId is null) ");
		} 
		sb.append("order by r.code ");
		List<Resource> list = genericDAO.getGenericByHql(sb.toString());
		return ListUtils.transform(list, ResourceExtForm.class);
	}
	
	@Override
	public void deleteResource(String... resourceIds){ 
		String hsql =null;
		for(String id:resourceIds){
			Resource cmsResource = genericDAO.get(Resource.class,id);
			hsql = "DELETE FROM T_SYS_RESOURCE WHERE ID = '"+cmsResource.getId() +"'";
			genericDAO.execteNativeBulk(hsql);
			hsql = "DELETE FROM T_SYS_RESOURCE WHERE PARENTID = '"+cmsResource.getId() +"'";
			genericDAO.execteNativeBulk(hsql);
			hsql = "DELETE FROM T_SYS_ROLERESOURCE  WHERE RESOURCEID = '"+cmsResource.getId() +"'";
			genericDAO.execteNativeBulk(hsql);
			logBizOprService.saveLog("菜单管理","3" , "删除菜单(菜单名称:" + cmsResource.getTitle() + ")" , "t_sys_resource", cmsResource.toString());

		}
	}

	@Override
	public ResourceExtForm getResourceById(String resourceId) {
		Resource cmsResource = genericDAO.get(Resource.class,resourceId);
		if(cmsResource!=null){
			return BeanUtils.copyProperties(ResourceExtForm.class, cmsResource);
		}
		return null;
	}

	@Override
	public List<ResourceExtForm> getResources(String... args) {
		String sql  = "from Resource r order by r.code";
		List<Resource> list = genericDAO.getGenericByHql(sql);
		return ListUtils.transform(list, ResourceExtForm.class);
	}

	@Override
	public ResourceExtForm updateResource(ResourceExtForm resourceExtForm) {
		Resource cmsResource = BeanUtils.copyProperties(Resource.class, resourceExtForm);
		genericDAO.saveOrUpdate(cmsResource);
		logBizOprService.saveLog("菜单管理","2" , "修改菜单(菜单名称:" + cmsResource.getTitle() + ")" , "t_sys_resource", cmsResource.toString());
		return resourceExtForm;
		
	}

	/* (非 Javadoc)
	 * <p>Title: loadMenu</p>
	 * <p>Description: </p>
	 * @return
	 * @throws Exception 
	 * @see com.frame.system.service.ResourceService#loadMenu()
	 */
	@Override
	public List<JSONObject> loadMenu() throws Exception {
		JSONObject object = new JSONObject();
		object.put("id", "root");
		object.put("text", "系统资源");
		//object.put("icon", "");
		JSONObject state = new JSONObject();
		state.put("opened", true);
		state.put("opened", true);
		object.put("state", state);
		if(this.hasChildren("")){
			object.put("children", this.getMenu(""));
		}
		List<JSONObject> menu = new ArrayList<JSONObject>();//this.getMenu("");
		menu.add(object);
		return menu;
	}
	
	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param uid
	 * @param string
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-7-6      Ching Wang     v1.0.0      create
	 *
	 */
	private List<JSONObject> getMenu(String pid) throws Exception{
		List<JSONObject> objects = new ArrayList<JSONObject>();
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("from Resource res ");
		strbuf.append(" where 1 = 1 ");
		if(StringUtil.isNotEmpty(pid)){
			strbuf.append(" and res.parentId = '").append(pid).append("' ");
		}
		else{
			strbuf.append(" and (res.parentId is null or res.parentId = '') ");
		}
		strbuf.append("order by res.code ");
		
		List<Resource> list = genericDAO.getGenericByHql(strbuf.toString());
		if(list != null && list.size() > 0){
			for(Resource resource : list){
				JSONObject object = new JSONObject();
				object.put("id", resource.getId());
				object.put("text", resource.getTitle());
				object.put("icon", "");
				object.put("href", resource.getHref());
				object.put("code", resource.getCode());
				JSONObject state = new JSONObject();
				if(StringUtil.isEmpty(pid)){
					state.put("opened", true);
					state.put("disabled", false);
					state.put("selected", false);
				}
				if(hasChildren(resource.getId())){
					object.put("children", this.getMenu(resource.getId()));
				}
				objects.add(object);
			}
		}
		return objects;
	}
	
	private boolean hasChildren(String pid) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("select count(res) from Resource res ");
		strbuf.append(" where 1 = 1 ");
		
		if(StringUtil.isNotEmpty(pid)){
			strbuf.append(" and res.parentId = '").append(pid).append("' ");
		}
		
		return genericDAO.getGenericCountByHql(strbuf.toString(), null) > 0;
	}

	/* (非 Javadoc)
	 * <p>Title: addRes</p>
	 * <p>Description: </p>
	 * @param res
	 * @return 
	 * @see com.frame.system.service.ResourceService#addRes(com.frame.system.po.Resource)
	 */
	@Override
	public void addRes(Resource res) {
		try{
			if(StringUtil.isEmpty(res.getParentId())){
				res.setParentId(null);
			}
			if(StringUtil.isEmpty(res.getId())){
				res.setId(GUID.generateID("RES"));
				genericDAO.save(res);
				logBizOprService.saveLog("资源管理","1" , "新增资源(资源名称:" + res.getTitle() + ")" , "", "");
			}else{
				genericDAO.merge(res);
				logBizOprService.saveLog("资源管理","2" , "修改资源(资源ID:" + res.getId()+ ")" , "", "");
			}
		}catch(Exception e){
			if(res.getId()==null){
				logBizOprService.saveLog("资源管理","1" , "新增资源(资源名称:" +res.getTitle()+ ")失败,"+ e.getStackTrace() , "", "");
			}else{
				logBizOprService.saveLog("资源管理","2" , "修改资源(资源ID:" + res.getId()+ ")失败,"+ e.getStackTrace() , "", "");
			}
		}
	}

	/* (非 Javadoc)
	 * <p>Title: delRes</p>
	 * <p>Description: </p>
	 * @param id
	 * @throws Exception 
	 * @see com.frame.system.service.ResourceService#delRes(java.lang.String)
	 */
	@Override
	public JSONObject delRes(String id){
		JSONObject obj = new JSONObject();
		try{
			if(hasChildren(id)){
				obj.put("success", false);
				obj.put("message", "存在下级资源，不能删除");
			} else {
				genericDAO.delete(Resource.class, id);
				logBizOprService.saveLog("资源管理","2" , "删除资源(资源ID:" + id+ ")" , "", "");
				obj.put("success", true);
				obj.put("message", "删除资源成功");
			}
		}catch(Exception e){
			logBizOprService.saveLog("资源管理","2" , "删除资源(资源ID:" + id+ ")失败,"+ e.getStackTrace() , "", "");
			obj.put("success", false);
			obj.put("message", "删除资源失败");
		}
		return obj;
	}
}

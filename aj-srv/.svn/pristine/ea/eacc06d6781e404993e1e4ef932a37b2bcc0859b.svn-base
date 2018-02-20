package com.frame.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.BeanUtils;
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

}

package com.frame.system.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.frame.system.po.Resource;
import com.frame.system.vo.ResourceExtForm;


public interface ResourceService {
	
	/**
	 * 查询资源列表
	 */
	public List<ResourceExtForm> getResources(String ...args);
	
	/**
	 * 通过资源
	 */
	public ResourceExtForm getResourceById(String resourceId);
	
	/**
	 * 增加资源
	 * @return TODO
	 */
	public ResourceExtForm addResource(ResourceExtForm resourceExtForm);
	
	/**
	 * 修改资源
	 * @return TODO
	 */
	public ResourceExtForm updateResource(ResourceExtForm resourceExtForm);
	
	/**
	 * 删除资源
	 */
	public void deleteResource(String...  resourceIds);

	public List<ResourceExtForm> getResourcesByParentId(String parentId);

	/**
	 * 检查编码是否存在
	 * @param id
	 * @param code
	 * @return
	 */
	public boolean checkResouceCode(String id,String code);
	
	public List<JSONObject> loadMenu() throws Exception;
	
	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param res
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-1      Ching Wang     v1.0.0      create
	 *
	 */
	public void addRes(Resource res);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param id
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-1      Ching Wang     v1.0.0      create
	 *
	 */
	public JSONObject delRes(String id);
}

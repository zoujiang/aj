package com.frame.system.service;

import java.util.List;

import com.frame.system.vo.ResourceExtForm;

public interface ResourceManage {
	/**
	 * 查询资源列表
	 */
	public List<ResourceExtForm> getResources(String ...args);
	
	/**
	 * 获取子节点 
	 * @param parentId
	 * @return
	 */
	public  List<ResourceExtForm> getResourcesByParentId(String parentId);
	
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
	public void deleteResource(String  resourceIds);
	
	/**
	 * 检查资源是否存在
	 * @param id
	 * @param code
	 * @return
	 */
	public boolean checkResouceCode(String id,String code);
	
}

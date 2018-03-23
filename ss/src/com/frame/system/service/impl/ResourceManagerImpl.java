package com.frame.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.system.service.ResourceManage;
import com.frame.system.service.ResourceService;
import com.frame.system.vo.ResourceExtForm;


@Component
public class ResourceManagerImpl implements ResourceManage{
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public ResourceExtForm addResource(ResourceExtForm resourceExtForm) {
		return resourceService.addResource(resourceExtForm);
		
	}

	@Override
	public void deleteResource(String resourceIds) {
		resourceService.deleteResource(resourceIds);
		
	}

	@Override
	public ResourceExtForm getResourceById(String resourceId) {
		return resourceService.getResourceById(resourceId);
	}

	@Override
	public List<ResourceExtForm> getResources(String... args) {
		return resourceService.getResources(args);
	}

	@Override
	public ResourceExtForm updateResource(ResourceExtForm resourceExtForm) {
		 return resourceService.updateResource(resourceExtForm);
	}

	@Override
	public List<ResourceExtForm> getResourcesByParentId(String parentId) {
		return resourceService.getResourcesByParentId(parentId);
	}

	@Override
	public boolean checkResouceCode(String id, String code) {
		return resourceService.checkResouceCode(id, code);
	}

}

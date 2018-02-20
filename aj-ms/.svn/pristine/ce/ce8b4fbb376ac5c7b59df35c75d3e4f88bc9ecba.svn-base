package com.frame.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.frame.system.service.SystemResourceManager;
import com.frame.system.service.SystemResourceService;


@Service
public class SystemResourceServiceImpl implements SystemResourceService {

	private SystemResourceManager manager;

	public SystemResourceServiceImpl() {
		manager = new SystemResourceManagerImpl();
	}

	public <T> List<T> getList(Class<T> clazz) {
		return manager.getList(clazz);
	}

	
	public <T> T find(Class<T> clazz, String recid) {
		return manager.find(clazz, recid);
	}

	
	public <T> T findByKey(Class<T> clazz, Object... key) {
		return manager.findByKey(clazz, key);
	}

	/**
	 * 根据条件得到对象
	 */
	
	public <T> List<T> getListByKey(Class<T> clazz, Object... key) {
		return manager.getListByKey(clazz, key);
	}

	
	public <T> boolean update(T t) {
		return manager.update(t);
	}

	
	public <T> boolean insert(T t) {
		return manager.insert(t);
	}

	
	public <T> boolean delete(T t) {
		return manager.delete(t);
	}

	
	public <T> boolean delete(Class<T> clazz, String recid) {
		return manager.delete(clazz, recid);
	}
}

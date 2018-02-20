package com.frame.system.service;

import java.util.List;

/**
 * @author Jideas 系统资源服务
 */
public interface SystemResourceService {
	/**
	 * 得到全部对象
	 */
	<T> List<T> getList(Class<T> clazz);

	/**
	 * 根据id得到对象
	 */
	<T> T find(Class<T> clazz, String recid);

	/**
	 * 根据条件得到对象
	 */
	<T> T findByKey(Class<T> clazz, Object... key);

	/**
	 * 根据条件得到对象组合
	 */
	<T> List<T> getListByKey(Class<T> clazz, Object... key);

	/**
	 * 更新资源
	 */
	<T> boolean update(T t);

	/**
	 * 新增资源
	 */
	<T> boolean insert(T t);

	/**
	 * 删除资源
	 */
	<T> boolean delete(T t);

	/**
	 * 删除资源
	 */
	<T> boolean delete(Class<T> clazz, String recid);

}

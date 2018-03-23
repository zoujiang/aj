package com.frame.system.service;

import java.util.List;

/**
 * @author Jideas
 * 
 */
public interface BaseResourceProvider<T> {

	List<T> getElements(Object service);
	
	Class<T> getEntityClass();

	String getServiceName();

	boolean getOneKeyValidate(T t, Object key);

	boolean getTwoKeyValidate(T t, Object key, Object key2);

	boolean getThreeKeyValidate(T t, Object key, Object key2, Object key3);
}

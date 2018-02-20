package com.frame.system.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.frame.core.util.BeanCopy;
import com.frame.core.util.CheckIsNull;

/**
 * @author 
 */
public abstract class SystemResourceManager {

	@SuppressWarnings("unchecked")
	private static Map<Class, Map<String, Object>> map = new HashMap<Class, Map<String, Object>>();

	@SuppressWarnings( { "unchecked" })
	private static Map<Class, BaseResourceProvider> providers = new HashMap<Class, BaseResourceProvider>();

	public <T> T findByKey(Class<T> clazz, Object... key) {
		BaseResourceProvider<T> provider = getProvider(clazz);
		if (null == key) {
			return null;
		}
		if (key.length == 1) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getOneKeyValidate(t, key[0])) {
					return t;
				}
			}
		} else if (key.length == 2) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getTwoKeyValidate(t, key[0], key[1])) {
					return t;
				}
			}
		} else if (key.length == 3) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getThreeKeyValidate(t, key[0], key[1], key[3])) {
					return t;
				}
			}
		}
		return null;
	}

	public <T> List<T> getListByKey(Class<T> clazz, Object... key) {
		BaseResourceProvider<T> provider = getProvider(clazz);
		if (null == key) {
			return null;
		}
		if (null == provider) {
			return null;
		}
		List<T> resultList = new ArrayList<T>();
		if (key.length == 1) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getOneKeyValidate(t, key[0])) {
					resultList.add(t);
				}
			}
		} else if (key.length == 2) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getTwoKeyValidate(t, key[0], key[1])) {
					resultList.add(t);
				}
			}
		} else if (key.length == 3) {
			List<T> list = this.getList(clazz);
			for (T t : list) {
				if (provider.getThreeKeyValidate(t, key[0], key[1], key[3])) {
					resultList.add(t);
				}
			}
		}
		return resultList;
	}

	public <T> boolean update(T t) {
		if (null == t) {
			return false;
		}
		if (null == map.get(t.getClass())) {
			return insert(t);
		} else {
			Field f;
			try {
				f = t.getClass().getDeclaredField("recid");
			} catch (SecurityException e) {
				e.printStackTrace();
				return false;
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				return false;
			}
			try {
				f.setAccessible(true);
				map.get(t.getClass()).put(f.get(t) + "", t);
				f.setAccessible(false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public <T> boolean insert(T t) {
		if (null == t) {
			return false;
		}
		Field f;
		try {
			f = t.getClass().getDeclaredField("recid");
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return false;
		}
		try {
			if (map.get(t.getClass()) == null) {
				map.put(t.getClass(), new HashMap<String, Object>());
			}
			f.setAccessible(true);
			map.get(t.getClass()).put(f.get(t) + "", t);
			f.setAccessible(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public <T> boolean delete(T t) {
		if (CheckIsNull.isEmpty(t)) {
			return false;
		}
		if (null == map.get(t.getClass())) {
			return true;
		}
		Field f;
		try {
			f = t.getClass().getDeclaredField("recid");
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return false;
		}
		try {
			f.setAccessible(true);
			map.get(t.getClass()).remove(f.get(t));
			f.setAccessible(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public <T> boolean delete(Class<T> clazz, String recid) {
		if (CheckIsNull.isEmpty(recid)) {
			return false;
		}
		if (null == map.get(clazz)) {
			return true;
		}
		map.get(clazz).remove(recid);
		return true;
	}

	/**
	 * 得到数据资源提供其器
	 */
	@SuppressWarnings("unchecked")
	protected <T> BaseResourceProvider<T> getProvider(Class<T> clazz) {
		return providers.get(clazz);
	}

	/**
	 * 得到全部对象
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		if (null == map.get(clazz)) {
			return list;
		}
		for (Object obj : map.get(clazz).values()) {
			list.add(BeanCopy.copy(clazz, obj));
		}
		return list;
	}

	/**
	 * 根据id得到对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> clazz, String recid) {
		if (null == map.get(clazz)) {
			return null;
		}
		return BeanCopy.copy(clazz, map.get(clazz).get(recid));
	}

	@SuppressWarnings( { "unchecked" })
	public static final void init(FilterConfig sc) {
		List<Class> classesList = readClassesList(sc.getServletContext().getRealPath(""));
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
		for (Class clazz : classesList) {
			BaseResourceProvider provider = null;
			try {
				provider = (BaseResourceProvider) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Class serviceClass = null;
			try {
				serviceClass = Class.forName(provider.getServiceName());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			List<Object> serviceList = new ArrayList(context.getBeansOfType(serviceClass).values());
			List<Object> list = provider.getElements(serviceList.get(0));
			Class objClass = null;
			if (null == list || list.isEmpty()) {
				providers.put(provider.getEntityClass(), provider);
				continue;
			}
			Map<String, Object> items = new HashMap<String, Object>();
			for (Object obj : list) {
				if (null == objClass) {
					objClass = obj.getClass();
				}
				Field idField = null;
				try {
					idField = obj.getClass().getDeclaredField("recid");
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				try {
					idField.setAccessible(true);
					items.put(idField.get(obj) + "", obj);
					idField.setAccessible(false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (null == objClass) {
				continue;
			}
			map.put(objClass, items);
			providers.put(objClass, provider);
		}
	}

	@SuppressWarnings( { "unchecked", })
	private static final List<Class> readClassesList(String realPath) {
//		String path = realPath + "/xml/spark.xml";
//		File file = new File(path);
//		SAXReader saxReader = new SAXReader();
//		Document document = null;
//		try {
//			document = saxReader.read(file);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		Element root = document.getRootElement();
//		Element providers = root.element("providers");
//		Iterator<Element> providerIterator = providers.elementIterator("provider");
//		List<String> list = new ArrayList<String>();
//		while (providerIterator.hasNext()) {
//			Element provider = providerIterator.next();
//			Attribute attr = provider.attribute("class");
//			if (null == attr) {
//				continue;
//			}
//			list.add(attr.getValue());
//		}
		List<Class> classesList = new ArrayList<Class>();
//		for (String s : list) {
//			try {
//				Class clazz = Class.forName(s);
//				classesList.add(clazz);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
		return classesList;
	}

}

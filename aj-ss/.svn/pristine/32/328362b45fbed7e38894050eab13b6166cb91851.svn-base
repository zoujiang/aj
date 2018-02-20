package com.frame.ifpr.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 */
public class GenericsUtils {
	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) throws ClassNotFoundException {
		Type[] genType = clazz.getGenericInterfaces();// 得到泛型父类
		Class  entityClass =null;
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		for(Type type:genType){
			if (!(type instanceof ParameterizedType)) {
				return Object.class;
			}
		    Type[] params = ((ParameterizedType) type).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				throw new RuntimeException("你输入的索引"+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			entityClass = (Class) ((ParameterizedType)type).getActualTypeArguments()[index];
		}
		return entityClass;
	}

	/**
	 * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承泛型父类
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz) throws ClassNotFoundException {
		return getSuperClassGenricType(clazz, 0);
	}
}
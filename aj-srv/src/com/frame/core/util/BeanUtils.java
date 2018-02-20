package com.frame.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;

public class BeanUtils extends org.springframework.beans.BeanUtils {
	
	public static <T,S> T copyProperties(Class<T> clazz,S s){
		
		try{
			T t = clazz.newInstance();
			PropertyUtils.copyProperties(t, s);
			return t;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 使用方法：BeanUtils.copyProperties(object,new Object());
	 * @param src  源对象
	 * @param dest	目标对象
	 * @return
	 * @throws Exception
	 */
	public static <T> void copyProperties1(Object src, T dest) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(src.getClass());
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		Method get = null;
		Method set = null;
		for (int i = 0; i < properties.length; i++) {
			try {
				get = properties[i].getReadMethod();
				set = dest.getClass().getMethod(
						properties[i].getWriteMethod().getName(),
						new Class[] { properties[i].getPropertyType() });
			} catch (Throwable a) {
				continue;
			}
			if (set != null && get != null) {
				set.invoke(dest, new Object[] { get.invoke(src,
						new Object[] {}) });
				set = null;
				get = null;
			}
		}
	}
	
	public static void copyProperties(Object source, Object target) throws BeansException {
			Class<?> actualEditable = target.getClass();
			PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
			for (PropertyDescriptor targetPd : targetPds) {
				if (targetPd.getWriteMethod() != null) {
					PropertyDescriptor sourcePd = getPropertyDescriptor(
							source.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						try {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod.getDeclaringClass()
									.getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
							if (value != null) {
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								if(value instanceof Date){
									Date date=(Date)value;
									writeMethod.invoke(target, DateUtil.dateFromat(date,DateUtil.DATE_TIME_PATTERN2));
								}else {
									writeMethod.invoke(target, value);
								}
							}
						} catch (Throwable ex) {
						}
					}
				}
			}
			}

}

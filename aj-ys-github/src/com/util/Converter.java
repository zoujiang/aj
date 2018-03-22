package com.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class Converter {

	public static String toBlank(Object obj) {
		if (obj == null) {
			return "";
		} else {
			String className = obj.getClass().getName();
			String oValue = "";
			if (className.equals("java.util.Date") || className.equals("java.sql.Timestamp")) {
				try {
					oValue = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(obj).trim();
				} catch (Exception e) {
					try {
						oValue = new SimpleDateFormat("yyyy-MM-dd").format(obj).trim();
					} catch (Exception e_) {
						oValue = "";
					}
				}
			} else if (className.equals("oracle.sql.CLOB")) {
				try {
					Clob clob = (Clob) obj;
					oValue = clob.getSubString(1L, (int) clob.length()).trim();
				} catch (SQLException e) {
					oValue = "";
				}
			} else {
				oValue = obj.toString().trim();
			}
			return oValue;
		}
	}
	
	public static Long toLong(Object obj) {
		try {
			String longString = Converter.toBlank(obj);
			return Long.valueOf(longString);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Long toLong(Object obj, Long defValue) {
		try {
			String longString = Converter.toBlank(obj);
			return Long.valueOf(longString);
		} catch (Exception e) {
			return defValue;
		}
	}
	
	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
	
	
}

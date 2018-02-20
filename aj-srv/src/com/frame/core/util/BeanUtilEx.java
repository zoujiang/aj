package com.frame.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;

import com.frame.core.util.converter.DateConvert;

public final class BeanUtilEx extends BeanUtils {
	private BeanUtilEx() {
	}
	static {
		// 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.lang.String.class);
	}
	
	public static void copyProperties(Object target, Object source){
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 批量类型转换工具
	 * 
	 * @param targetClass
	 *            目标对象类型
	 * @param o
	 *            需要转型的原对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> copys(Class<T> targetClass, List o) {
		List<T> list =null;
		try{
			if(null==o){
				return null;
			}
			list= new ArrayList<T>();
			for (int i = 0; i < o.size(); i++) {
				T t=targetClass.newInstance();
				copyProperties(t, o.get(i));
				list.add(t);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}
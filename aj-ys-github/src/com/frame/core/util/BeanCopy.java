/**
 * 
 */
package com.frame.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Jideas
 * 
 */
public abstract class BeanCopy {

	@SuppressWarnings( { "unchecked" })
	public  static <T> T copyObject(Object o, T t) {
		if(o==null){
			return null;
		}
		Class clazz = o.getClass();
		Field far[] = t.getClass().getDeclaredFields();
		List<Field> flist = new ArrayList(ArrayUtils.arrayToList(far));
		fillItWithSuperClass(t.getClass(),flist);
		for (Field f : flist) { 
			Field oldField;
			try {
				oldField = clazz.getDeclaredField(f.getName());
			} catch (SecurityException e1) {
				// e1.printStackTrace();
				continue;
			} catch (NoSuchFieldException e1) {
				// e1.printStackTrace();
				continue;
			}
			Class oldtype = oldField.getType();
			String getName = "get" + getMethodName(f.getName());
			String setName = "set" + getMethodName(f.getName());
			if (f.getType().equals(boolean.class)) {
				if (f.getName().startsWith("is")
						&& f.getName().substring(2, 3).equals(f.getName().substring(2, 3).toUpperCase())) {
					getName = f.getName();
					setName = "set" + f.getName().substring(2);
				} else {
					getName = "is" + getMethodName(f.getName());
				}
			}
			Method om = null;
			try {
				om = clazz.getMethod(getName);
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				continue;
			}
			Method tm = null;
			try {
				tm = t.getClass().getMethod(setName, new Class[] { f.getType() });
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				continue;
			}
			if (null == tm || null == om) {
				continue;
			}
			if (f.getType().equals(String.class) && oldtype.equals(Date.class)) {
				Date date = null;
				try {
					date = (Date) om.invoke(o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				if (null == date) continue;
				String dateStr = DateUtil.dateFromat(date, DateUtil.DATE_TIME_PATTERN2);
				try {
					tm.invoke(t, dateStr);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
			} else if (f.getType().equals(String.class) && oldtype.equals(byte[].class)) {
				byte[] bs;
				try {
					bs = (byte[]) om.invoke(o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				if (null == bs) {
					continue;
				}
				String s = GUID.valueOf(bs).toString();
				try {
					tm.invoke(t, s);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
			} else if (f.getType().equals(byte[].class) && oldtype.equals(String.class)) {
				String s;
				try {
					s = (String) om.invoke(o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				if (CheckIsNull.isEmpty(s)) {
					continue;
				}
				byte[] bs = GUID.valueOf(s).toBytes();
				try {
					tm.invoke(t, bs);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
			} else {
				try {
					tm.invoke(t, om.invoke(o));
				} catch (IllegalArgumentException e) {
					System.out.println(f.getName());
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return t;
	}

	/**
	 * @param class1
	 * @param flist
	 */
	private static void fillItWithSuperClass(Class<? extends Object> clazz, List<Field> flist) {
		if(null==clazz.getSuperclass()||clazz.getSuperclass().equals(Object.class)){
			return ;
		}
		Field far[] = clazz.getSuperclass().getDeclaredFields();
		flist.addAll(ArrayUtils.arrayToList(far));
		fillItWithSuperClass(clazz.getSuperclass(), flist);
	} 

	/**
	 * class 转换工具
	 * 
	 * @param targetClass
	 *            目标对象类型
	 * @param o
	 *            需要转型的原对象
	 * @return
	 */
	public static <T> T copy(Class<T> targetClass, Object o) {
		T t = null;
		try {
			t = targetClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return copyObject(o, t);
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
		if(null==o){
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < o.size(); i++) {
			list.add(copy(targetClass, o.get(i)));
		}
		return list;
	}

	public static String getMethodName(String s) {
		String str = null;
		str = s.substring(0, 1).toUpperCase() + s.substring(1);
		return str;
	}
}

package com.frame.core.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExportTextUtils {

	private Log log=LogFactory.getLog(ExportTextUtils.class);
			
	private String[] columns;
	private int columnLength = 0;
	private BufferedWriter bw = null;
	

	/**
	 * 生成txt文件
	 * 
	 * @param title
	 * @param out
	 * @throws IOException 
	 */
	public void exportCsv(List<?> dataList, String headerColumn, BufferedWriter bw) throws IOException {
		JSONArray columnArray = JSONArray.fromObject(headerColumn);
		this.columnLength = columnArray.size();
		this.columns = new String[columnLength];
		this.bw = bw;
		StringBuilder sb = new StringBuilder();
		for (int column = 0; column < columnArray.size(); column++) {
			JSONObject obj=(JSONObject) columnArray.get(column);
			columns[column]=obj.getString("field");
			sb.append(obj.getString("title"));
			if(column < columnArray.size() -1) {
				sb.append(",");
			}
		}
		this.bw.append(sb.toString());
		this.bw.append("\r\n");
		// 遍历集合数据，产生数据行
		for (Object element : dataList) {
			sb.delete(0, sb.length());
			for (int i = 0; i < columnLength; i++) {
				// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
				sb.append(this.getDataText(element, i));
				if(i < columnLength -1) {
					sb.append(",");
				}
			}
			if(StringUtils.isNotEmpty(sb.toString())) {
				this.bw.append(sb.toString());
				this.bw.append("\r\n");
			}
		}
		bw.flush();
	}

	/**
	 * 获取对象相关字段上内容
	 * @param element
	 * @param columnIndex
	 * @return
	 */
	protected String getDataText(Object element, int columnIndex) {
		Class<?> clazz = element.getClass();
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			String field = columns[columnIndex];
			return map.get(field).toString();
		} else {
			String field = columns[columnIndex];
			String getName = "get" + BeanCopy.getMethodName(field);//拼组getMethodName
			Method getMethod;
			try {
				getMethod = clazz.getMethod(getName);
				//整形数据
				if (Integer.class == getMethod.getReturnType()) {
					Integer columnValue = (Integer) getMethod.invoke(element);
					return String.valueOf(columnValue);
				} else {
					String columnValue = (String) getMethod.invoke(element);
					return columnValue;
				}
			} catch (SecurityException e) {
				log.error("" , e);
			} catch (NoSuchMethodException e) {
				log.error("" , e);
			} catch (IllegalArgumentException e) {
				log.error("" , e);
			} catch (IllegalAccessException e) {
				log.error("" , e);
			} catch (InvocationTargetException e) {
				log.error("" , e);
			}
		}
		return null;
	}
}
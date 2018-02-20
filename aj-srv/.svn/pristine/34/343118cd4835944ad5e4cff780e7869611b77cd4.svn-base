package com.frame.ifpr.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
/**
 * JSON格式处理工具类
 * @author lishun
 *
 */
public class JsonUtil {
	 /** 
     * 从一个JSON 对象字符格式中得到一个java对象 
     *  
     * @param jsonString 
     * @param pojoCalss 
     * @return 
     */  
    public static Object getObject4JsonString(String jsonString, Class pojoCalss) {  
        Object pojo=null;  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        pojo = JSONObject.toBean(jsonObject, pojoCalss);  
        return pojo;  
    }  
    /** 
     * 从json数组中解析出List<Map<String,Object>>
     *  
     * @param jsonString 
     * @return 
     */  
    public static List<Map<String,Object>> getListMap(String jsonString,List<Map<String,Object>> list){  
        JSONArray array = JSONArray.fromObject(jsonString);  
        for(int i = 0; i < array.size(); i++){  
            JSONObject jsonObject = array.getJSONObject(i); 
            Map<String,Object> map=new HashMap<String,Object>();
            Iterator<String> it=jsonObject.keys();
            while(it.hasNext()){
            	String key=it.next();
            	String value=jsonObject.get(key).toString();
            	map.put(key, value);
            }
            list.add(map);
        }  
        return list;  
    }  
    /** 
     * 从json对象集合表达式中得到一个java对象列表 
     *  
     * @param jsonString 
     * @param pojoClass 
     * @return 
     */  
    public static <T> Set<T> getSet4Json(String jsonString, Class pojoClass) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        JSONObject jsonObject;  
        T pojoValue;  
  
        Set<T> set = new HashSet<T>();  
        for (int i = 0; i < jsonArray.size(); i++) {  
            jsonObject = jsonArray.getJSONObject(i);  
            pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass);  
            set.add(pojoValue);  
  
        }  
        return set;  
  
    }  
    /** 
     * 从json HASH表达式中获取一个map，改map支持嵌套功能 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Map<String, Object> getMap4Json(String jsonString,Class pojoClass) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator<String> keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
  
        while (keyIter.hasNext()) {  
            key = keyIter.next();  
            value = jsonObject.get(key).toString();  
            try{
            	//如果Outer{Set<Inner>}的形式
            	//Set<Object> set=getSet4Json(value.toString(),pojoClass);
            	//如果Outer{Inner}的形式
            	Map<String, Object> map=getMap4Json(value.toString());
            	Object obj=pojoClass.newInstance();
            	PropertyUtils.copyProperties(obj, map);
            	valueMap.put(key, obj); 
            }catch(Exception e){
            	valueMap.put(key, value);
            }
        }  
        return valueMap;  
    }  
    /** 
     * 从json字符串中获取一个map
     *  
     * @param jsonString 
     * @return 
     */  
    public static Map<String, Object> getMap4Json(String jsonString) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator<String> keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
  
        while (keyIter.hasNext()) {  
            key = keyIter.next();  
            value = jsonObject.get(key).toString();  
            valueMap.put(key, value);
        }  
        return valueMap;  
    } 
    /** 
     * 将java对象转换成json字符串 
     *  
     * @param javaObj 
     * @return 
     */  
    public static String getJsonString4JavaPOJO(Object object) {  
    	String jsonString = null;  
        if(object != null){  
            if(object instanceof Collection || object instanceof Object[]){  
                jsonString = JSONArray.fromObject(object).toString();  
            }else{  
                jsonString = JSONObject.fromObject(object).toString();  
            }  
        }  
        return jsonString == null ? "{}" : jsonString;  
  
    }  
    
    
    /** 
     * 从json字符串中获取一个map
     *  
     * @param jsonString 
     * @return 
     */  
    public static Map<String, Object> getMap4JsonByList(String jsonString) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator<String> keyIter = jsonObject.keys();  
        String key;  
        String key1;
        Object value;  
        Object value1; 
        Map<String, Object> valueMap = new HashMap<String, Object>(); 
        Set<Map<String, Object>> values = new HashSet<Map<String, Object>>();
        Set<Map<String, Object>> values1 = new HashSet<Map<String, Object>>();
        while (keyIter.hasNext()) {  
            key = keyIter.next();  
            value = jsonObject.get(key).toString();
            if(value.toString().contains("prodId")){
            	JSONArray js=JSONArray.fromObject(value);
            	for (Object object : js) {
            	Map<String, Object> valueMap1 = new HashMap<String, Object>();
            	JSONObject jo = JSONObject.fromObject(object);
            	Iterator<String> ki = jo.keys();
            	 while (ki.hasNext()) {  
                     key1 = ki.next();
                     value1 = jo.get(key1).toString();
                     valueMap1.put(key1, value1) ;
                }           
            
            	     values.add(valueMap1);
            	    
                } 
            	    valueMap.put(key, values);
            }else  if(value.toString().contains("deliTime")){
                 	JSONArray ja=JSONArray.fromObject(value);
                 	for (Object object : ja) {
                 	Map<String, Object> valueMap2 = new HashMap<String, Object>();
                 	JSONObject jo = JSONObject.fromObject(object);
                 	Iterator<String> ki = jo.keys();
                 	 while (ki.hasNext()) {  
                          key1 = ki.next();
                          value1 = jo.get(key1).toString();
                          valueMap2.put(key1, value1) ;
                 	 }                
                 
                 	      values1.add(valueMap2);
                 	    
                  } 
                 	valueMap.put(key, values1);   	
               }else{
            	    valueMap.put(key, value);
               }
              
            }  

        return valueMap;  
    }
    
    /** 
     * 从json HASH表达式中获取一个map，改map支持嵌套功能 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Map<String, Object> getMap4JsonByList(String jsonString,Class pojoClass) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator<String> keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
  
        while (keyIter.hasNext()) {  
            key = keyIter.next();  
            value = jsonObject.get(key).toString();  
            try{
            	//如果Outer{Set<Inner>}的形式
            	//Set<Object> set=getSet4Json(value.toString(),pojoClass);
            	//如果Outer{Inner}的形式
            	Map<String, Object> map=getMap4JsonByList(value.toString());
            	Object obj=pojoClass.newInstance();
            	PropertyUtils.copyProperties(obj, map);
            	valueMap.put(key, obj); 
            }catch(Exception e){
            	valueMap.put(key, value);
            }
        }  
        return valueMap;  
    }  
      
}

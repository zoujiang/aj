package com.frame.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**     
 * 系统配置
 * 
 * @author：caiwen    
 * @date 2013-5-03 下午15:02:43    
 * @version  1.0    
 */
public class SystemConfig {
private static Log log=LogFactory.getLog(SystemConfig.class);
	
	private static SystemConfig instance;
	
	private static Properties properties=new Properties();
	
	
	protected SystemConfig(){
		init();
	}
	
	public static synchronized SystemConfig getInstance(){
		if(instance==null){
			instance=new SystemConfig();
		}
		return instance;
	}
	private  void init(){
		try {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties");
			properties.load(is);
			is.close();
			//properties.load(getClass().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("系统配置文件system.properties加载失败，请确认文件是否存在");
			//e.printStackTrace();
		}
	}
	
	public static  String  getValue(String key){
		SystemConfig system =  getInstance();
		return system.properties.getProperty(key);
		//return properties.getProperty(key);
	}
}

package com.aj.easemob.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author Lynch 2014-09-15
 *
 */
public class PropertiesUtils {

	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("RestAPIConfig.properties");

			//InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("RestAPIConfig.properties");

			p.load(inputStream);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return p;
	}

}

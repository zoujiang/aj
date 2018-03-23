package com.frame.core.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * 
 * <p>
 * 日期工具类
 * </p>
 * 
 * <p>
 * Copyright: 版权所有 (c) 2002 - 2008<br>
 * Company: mymost
 * </p>
 * 
 * @author yl
 * @version 2011-6-29
 */
public class ClobUtil {

	
	public static String convertToString(Clob clob) {
		  Reader inStream=null;
		  String data = null;
		  try {
			inStream = clob.getCharacterStream();
			 char[] c = new char[(int) clob.length()];  
			  inStream.read(c);  //data是读出并需要返回的数据，类型是String 
			  data = new String(c); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			 try {
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
}

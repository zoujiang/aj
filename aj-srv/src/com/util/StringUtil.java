package com.util;

import java.util.Random;

public class StringUtil {

	/**
	 * 电话号码把中间四位替换成****
	 * */
	public static String telTool(String tel){
		
		try {
			return tel.substring(0, 3) +"****" + tel.substring(7);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String genRandomStr(int length){  
	      int  maxNum = 36;  
	      int i;  
	      int count = 0;  
	      char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',  
	        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
	        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
	      StringBuffer pwd = new StringBuffer("");  
	      Random r = new Random();  
	      while(count < length){  
	       i = Math.abs(r.nextInt(maxNum));     
	       if (i >= 0 && i < str.length) {  
	        pwd.append(str[i]);  
	        count ++;  
	       }  
	    }  
	      return pwd.toString();  
	}
}

package com.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


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
	
	
	
	
}

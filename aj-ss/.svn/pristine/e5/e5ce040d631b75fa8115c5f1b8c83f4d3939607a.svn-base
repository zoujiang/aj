package com.frame.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 字符统计工具类
 * 
 * @author yanzelai
 * @version 2011-6-29
 */
public class CharCountUtil {
	/**
	 * 统计字符出现次数(replace)
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static int getCharCountNum(String source, String target) {
		if (null != source && !"".equals(source)) {
			return (source.length() - source.replaceAll(target, "").length())
					/ target.length();
		}
		return 0;
	}

	/**
	 * 统计字符出现次数(replace)
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static int geStringCountNum(String source, String target) {
		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(source);
		int i = 0;
		while (m.find()) {
			i++;
		}
		return i;
	}
}

package com.frame.core.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 */
public class StringUtil {

	/**
	 * 构造函数
	 */
	public StringUtil() {
	}
	
	/**
	 * 去掉字符串中的回车、换行
	 * 注：\n 回车(\u000a)
	 *	\t 水平制表符(\u0009)
	 *	\s 空格(\u0008)
	 *	\r 换行(\u000d)
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
	   Pattern p = Pattern.compile("\\r|\n");
	   Matcher m = p.matcher(str);
	  return m.replaceAll("");
	}
	// Empty
	// --------------------------------------------------------------------------

	/**
	 * 剔除字符串中的控制字符和空白字符,如果字符串对象为null返回空字符串
	 * 
	 * @param str
	 *            字符串
	 * @return 处理后的字符串
	 */
	public static String clean(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 剔除字符串中的控制字符和空白字符,如果字符串对象为null返回null
	 * 
	 * @param str
	 *            字符串
	 * @return 处理后的字符串
	 */
	public static String trim(String str) {
		return (str == null ? null : str.trim());
	}

	/**
	 * 提出空白字符，空白字符由Character.isWhitespace定义
	 * 
	 * @param str
	 *            字符串
	 * @return the 处理后的字符串
	 */
	public static String deleteWhitespace(String str) {
		StringBuffer buffer = new StringBuffer();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				buffer.append(str.charAt(i));
			}
		}
		return buffer.toString();
	}

	/**
	 * 检测字符串对象是否不为null，且字符串不为空串
	 * 
	 * @param str
	 *            字符串
	 * @return true 如果字符串对象不为null，且字符串不为空串
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 检测字符串对象是否为null或字符串为空串
	 * 
	 * @param str
	 *            字符串
	 * @return true 如果字符串对象为null或字符串为空串
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	// Equals and IndexOf
	// --------------------------------------------------------------------------

	/**
	 * 比较两字符串内容是否相同
	 * 
	 * @param str1
	 *            字符串
	 * @param str2
	 *            字符串
	 * @return true 如果字符串内容相符，或两字符串对象都为null
	 */
	public static boolean equals(String str1, String str2) {
		return (str1 == null ? str2 == null : str1.equals(str2));
	}

	/**
	 * 比较两字符串内容是否相同(服略大小写)
	 * 
	 * @param str1
	 *            字符串
	 * @param str2
	 *            字符串
	 * @return true 如果字符串内容相符，或两字符串对象都为null
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return (str1 == null ? str2 == null : str1.equalsIgnoreCase(str2));
	}

	/**
	 * 查找出现指定集合中任意字符串的最早的位置
	 * 
	 * @param str
	 *            字符串
	 * @param searchStrs
	 *            要匹配查找的字符串数组
	 * @return str最早出现searchStrs中任意字符串的位置
	 */
	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			tmp = str.indexOf(searchStrs[i]);
			if (tmp == -1) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
			}
		}

		return (ret == Integer.MAX_VALUE) ? -1 : ret;
	}

	/**
	 * 查找出现指定集合中任意字符串的最晚的位置
	 * 
	 * @param str
	 *            字符串
	 * @param searchStrs
	 *            要匹配查找的字符串数组
	 * @return str最晚出现searchStrs中任意字符串的位置
	 */
	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			tmp = str.lastIndexOf(searchStrs[i]);
			if (tmp > ret) {
				ret = tmp;
			}
		}
		return ret;
	}

	// Substring
	// --------------------------------------------------------------------------

	/**
	 * 获取某字符串指定位置开始的子串，如果开始位置为负数(pos)，截取的位置为str.length() + pos
	 * 
	 * @param str
	 *            字符串
	 * @param start
	 *            开始截取子串的位置
	 * @return 子串
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return "";
		}

		return str.substring(start);
	}

	/**
	 * 获取某字符串指定位置的子串，如果开始或结束位置为负数(pos)，截取的位置为str.length() + pos，如果该值仍然为负数则从0开始；
	 * 结束位置大于字符串长度，结束位置定于字符串末尾；如果开始位置大于结束位置，返回空字符串
	 * 
	 * @param str
	 *            字符串
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return 子串
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			// check this works.
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return "";
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 获取字符串从开始位置算起指定长度的字符串，如果截取长度大于字符串长度，返回整个字符串
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            截取长度
	 * @return 截取的字符串
	 */
	public static String left(String str, int len) {
		if (len < 0) {
			throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
		}
		if ((str == null) || (str.length() <= len)) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * 获取字符串从结束位置算起往前指定长度的字符串，如果截取长度大于字符串长度，返回整个字符串
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            截取长度
	 * @return 截取的字符串
	 */
	public static String right(String str, int len) {
		if (len < 0) {
			throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
		}
		if ((str == null) || (str.length() <= len)) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * 获取字符串指定位置算起指定长度的字符串，如果截取范围超出整个字符串，返回整个字符串
	 * 
	 * @param str
	 *            字符串
	 * @param pos
	 *            开始截取的位置
	 * @param len
	 *            截取长度
	 * @return 截取的字符串
	 */
	public static String mid(String str, int pos, int len) {
		if ((pos < 0) || (str != null && pos > str.length())) {
			throw new StringIndexOutOfBoundsException("String index " + pos + " is out of bounds");
		}
		if (len < 0) {
			throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
		}
		if (str == null) {
			return null;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		} else {
			return str.substring(pos, pos + len);
		}
	}

	// Splitting
	// --------------------------------------------------------------------------

	/**
	 * 默认分隔符解析字符串
	 * 
	 * @param str
	 *            要解析的字符串
	 * @return 解析后的字符串数组
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * 指定分隔符解析分割字符串
	 * 
	 * @param text
	 *            要解析的字符串
	 * @param separator
	 *            分隔符
	 * @return 解析后的字符串数组
	 */
	public static String[] split(String text, String separator) {
		return split(text, separator, -1);
	}

	/**
	 * 指定分隔符解析字符串，并能控制返回数组的最大长度
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            分隔符，如果为null,分隔符为StringTokenizer默认的空白符
	 * @param max
	 *            结合最大长度，负数或0表示无约束
	 * @return 解析后得到的数组
	 */
	public static String[] split(String str, String separator, int max) {
		StringTokenizer tok = null;
		if (separator == null) {
			// Null separator means we're using StringTokenizer's default
			// delimiter, which comprises all whitespace characters.
			tok = new StringTokenizer(str);
		} else {
			tok = new StringTokenizer(str, separator);
		}

		int listSize = tok.countTokens();
		if (max > 0 && listSize > max) {
			listSize = max;
		}

		String[] list = new String[listSize];
		int i = 0;
		int lastTokenBegin = 0;
		int lastTokenEnd = 0;
		while (tok.hasMoreTokens()) {
			if (max > 0 && i == listSize - 1) {
				// In the situation where we hit the max yet have
				// tokens left over in our input, the last list
				// element gets all remaining text.
				String endToken = tok.nextToken();
				lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
				list[i] = str.substring(lastTokenBegin);
				break;
			} else {
				list[i] = tok.nextToken();
				lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
				lastTokenEnd = lastTokenBegin + list[i].length();
			}
			i++;
		}
		return list;
	}

	// Joining
	// --------------------------------------------------------------------------
	/**
	 * 将数组集合中的对象组合成字符串
	 * 
	 * @param array
	 *            数组集合，包含要组合成字符串的各个元素
	 * @return 组合后的字符串
	 */
	public static String concatenate(Object[] array) {
		return join(array, "");
	}

	/**
	 * 将数组集合中的对象组合成字符串，集合中的元素将被指定的分割符分割
	 * 
	 * @param array
	 *            数组集合，包含要组合成字符串的各个元素
	 * @param separator
	 *            分隔符
	 * @return 组合后的字符串
	 */
	public static String join(Object[] array, String separator) {
		if (separator == null) {
			separator = "";
		}
		int arraySize = array.length;
		int bufSize = (arraySize == 0 ? 0 : (array[0].toString().length() + separator.length()) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * 将集合中的对象组合成字符串，各个对象将被指定分隔符分割
	 * 
	 * @param iterator
	 *            Iterator集合,包含要组合成字符串的各个元素
	 * @param separator
	 *            分隔符
	 * @return 组合后的字符串
	 */ 
	@SuppressWarnings("unchecked")
	public static String join(Iterator iterator, String separator) {
		if (separator == null) {
			separator = "";
		}
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		while (iterator.hasNext()) {
			buf.append(iterator.next());
			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	// Replacing
	// --------------------------------------------------------------------------

	/**
	 * 用新字符串替换母字符串中的指定子串，替换操作执行一次
	 * 
	 * @param text
	 *            将要被替换掉子串的母字符串
	 * @param repl
	 *            将要被替换掉的字符串
	 * @param with
	 *            用来替换的字符串
	 * @return 替换操作后的字符串
	 */
	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	/**
	 * 用新字符串替换母字符串中所有指定子字符串
	 * 
	 * @param text
	 *            将要被替换掉子串的母字符串
	 * @param repl
	 *            将要被替换掉的字符串
	 * @param with
	 *            用来替换的字符串
	 * @return 替换操作后的字符串
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * 用新字符串替换母字符串中的子字符串，并指定最大替换个数； with替换text中的字符串repl,替换个数最多为max个
	 * 
	 * @param text
	 *            将要被替换掉子串的母字符串
	 * @param repl
	 *            将要被替换掉的字符串
	 * @param with
	 *            用来替换的字符串
	 * @param max
	 *            替换的最大个数，负数表示没有最大限制
	 * @return 替换操作后的字符串
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 在字符串的某个区域添加字符串
	 * 
	 * @param text
	 *            包含将要被替换掉子串的母字符串
	 * @param overlay
	 *            用来替换的字符串
	 * @param start
	 *            替换的开始位置
	 * @param end
	 *            替换的结束位置
	 * @return String 替换操作后的字符串
	 */
	public static String overlayString(String text, String overlay, int start, int end) {
		return new StringBuffer(start + overlay.length() + text.length() - end + 1)
				.append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
	}

	// Centering
	// --------------------------------------------------------------------------

	/**
	 * 在字符串前后重复填补指定空字符串，两边填补的个数一致，填补后的字符串最大长度为size
	 * 
	 * @param str
	 *            将被填补的字符串
	 * @param size
	 *            新字符串的最大长度
	 * @return String 填补后的字符串
	 */
	public static String center(String str, int size) {
		return center(str, size, " ");
	}

	/**
	 * 在字符串前后重复填补指定字符串，两边填补的个数一致，填补后的字符串最大长度为size
	 * 
	 * @param str
	 *            将被填补的字符串
	 * @param size
	 *            新字符串的最大长度
	 * @param delim
	 *            用来填补的字符串
	 * @return String 填补后的字符串
	 */
	public static String center(String str, int size, String delim) {
		int sz = str.length();
		int p = size - sz;
		if (p < 1) {
			return str;
		}
		str = leftPad(str, sz + p / 2, delim);
		str = rightPad(str, size, delim);
		return str;
	}

	// Chomping
	// --------------------------------------------------------------------------

	/**
	 * 截取字符串最后一行的前面部分，即去除字符串的最后一行
	 * 
	 * @param str
	 *            将被截取的字符串
	 * @return String 截取后的字符串
	 */
	public static String chomp(String str) {
		return chomp(str, "\n");
	}

	/**
	 * 截取字符串，截取位置从str最后出现sep的位置开始到str字符串结束处
	 * 
	 * @param str
	 *            将被截取的字符串
	 * @param sep
	 *            截取的子串
	 * @return String 截取后的字符串
	 */
	public static String chomp(String str, String sep) {
		int idx = str.lastIndexOf(sep);
		if (idx != -1) {
			return str.substring(0, idx);
		} else {
			return str;
		}
	}

	/**
	 * 去掉字符串结尾处的换行符
	 * 
	 * @param str
	 *            字符串
	 * @return String str去掉结尾处换行符后的字符串
	 */
	public static String chompLast(String str) {
		return chompLast(str, "\n");
	}

	/**
	 * 当母字符串以指定的子串结尾时，在母串中去除该子串
	 * 
	 * @param str
	 *            String 母字符串
	 * @param sep
	 *            String 子字符串
	 * @return String 去除操作后的字符串
	 */
	public static String chompLast(String str, String sep) {
		if (str.length() == 0) {
			return str;
		}
		String sub = str.substring(str.length() - sep.length());
		if (sep.equals(sub)) {
			return str.substring(0, str.length() - sep.length());
		} else {
			return str;
		}
	}

	/**
	 * 截取字符串str，截取位置从str最后出现sep的位置到str.length-1
	 * 
	 * @param str
	 *            母字符串
	 * @param sep
	 *            子串
	 * @return String 截取的字符串
	 */
	public static String getChomp(String str, String sep) {
		int idx = str.lastIndexOf(sep);
		if (idx == str.length() - sep.length()) {
			return sep;
		} else if (idx != -1) {
			return str.substring(idx);
		} else {
			return "";
		}
	}

	/**
	 * 截取字符串str，截取位置从str第一次出现sep的位置+sep.length,到str.length-1
	 * 
	 * @param str
	 *            母字符串
	 * @param sep
	 *            子串
	 * @return 截取后的字符串
	 */
	public static String prechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx != -1) {
			return str.substring(idx + sep.length());
		} else {
			return str;
		}
	}

	/**
	 * 截取字符串str，截取位置从0到str第一次出现sep的位置+sep.length
	 * 
	 * @param str
	 *            母字符串
	 * @param sep
	 *            子串
	 * @return String 截取后的字符串
	 */
	public static String getPrechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx != -1) {
			return str.substring(0, idx + sep.length());
		} else {
			return "";
		}
	}

	// Chopping
	// --------------------------------------------------------------------------

	/**
	 * 去除字符串中的最后一个字符，如果字符串以\r\n结束，这两个字符都将被去除
	 * 
	 * @param str
	 *            String 母字符串
	 * @return String 去除操作后的字符串
	 */
	public static String chop(String str) {
		if ("".equals(str)) {
			return "";
		}
		if (str.length() == 1) {
			return "";
		}
		int lastIdx = str.length() - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);
		if (last == '\n') {
			if (ret.charAt(lastIdx - 1) == '\r') {
				return ret.substring(0, lastIdx - 1);
			}
		}
		return ret;
	}

	/**
	 * 去除字符串最后的\n字符，如果字符串以\r\n结束，这两个字符都将被去除
	 * 
	 * @param str
	 *            String to chop a newline from
	 * @return String without newline
	 * @throws NullPointerException
	 *             if str is null
	 */
	public static String chopNewline(String str) {
		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);
		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	// Conversion
	// --------------------------------------------------------------------------

	// spec 3.10.6
	/**
	 * 将字符串中的控制字符转化为转义字符串，如just\n转化为just\\n
	 * 
	 * @param str
	 *            要进行处理的字符串
	 * @return String 转化后的字符串
	 */
	public static String escape(String str) {
		// improved with code from cybertiger@cyberiantiger.org
		// unicode from him, and defaul for < 32's.
		int sz = str.length();
		StringBuffer buffer = new StringBuffer(2 * sz);
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);

			// handle unicode
			if (ch > 0xfff) {
				buffer.append("\\u" + Integer.toHexString(ch));
			} else if (ch > 0xff) {
				buffer.append("\\u0" + Integer.toHexString(ch));
			} else if (ch > 0x7f) {
				buffer.append("\\u00" + Integer.toHexString(ch));
			} else if (ch < 32) {
				switch (ch) {
				case '\b':
					buffer.append('\\');
					buffer.append('b');
					break;
				case '\n':
					buffer.append('\\');
					buffer.append('n');
					break;
				case '\t':
					buffer.append('\\');
					buffer.append('t');
					break;
				case '\f':
					buffer.append('\\');
					buffer.append('f');
					break;
				case '\r':
					buffer.append('\\');
					buffer.append('r');
					break;
				default:
					if (ch > 0xf) {
						buffer.append("\\u00" + Integer.toHexString(ch));
					} else {
						buffer.append("\\u000" + Integer.toHexString(ch));
					}
					break;
				}
			} else {
				switch (ch) {
				case '\'':
					buffer.append('\\');
					buffer.append('\'');
					break;
				case '"':
					buffer.append('\\');
					buffer.append('"');
					break;
				case '\\':
					buffer.append('\\');
					buffer.append('\\');
					break;
				default:
					buffer.append(ch);
					break;
				}
			}
		}
		return buffer.toString();
	}

	// Padding
	// --------------------------------------------------------------------------

	/**
	 * n个相同字符串组合成一个新字符传
	 * 
	 * @param str
	 *            String 要组合的字符串
	 * @param repeat
	 *            int 重复个数
	 * @return String 组合后的字符串
	 */
	public static String repeat(String str, int repeat) {
		StringBuffer buffer = new StringBuffer(repeat * str.length());
		for (int i = 0; i < repeat; i++) {
			buffer.append(str);
		}
		return buffer.toString();
	}

	/**
	 * 往字符串后填补空格，填补后的字符串长度为size
	 * 
	 * @param str
	 *            String 要进行填补操作的字符串
	 * @param size
	 *            int 字符串填补后的长度
	 * @return right 填补的字符串
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, " ");
	}

	/**
	 * 往字符串后填补另一个字符串，填补后的字符串长度为size
	 * 
	 * @param str
	 *            String 要进行填补操作的字符串
	 * @param size
	 *            int 字符串填补后的长度
	 * @param delim
	 *            String 填补的字符串
	 * @return 填补后的字符串
	 */
	public static String rightPad(String str, int size, String delim) {
		size = (size - str.length()) / delim.length();
		if (size > 0) {
			str += repeat(delim, size);
		}
		return str;
	}

	/**
	 * 往字符串前重复填补空格，填补后的字符串长度为size
	 * 
	 * @param str
	 *            String 要进行填补操作的字符串
	 * @param size
	 *            int 字符串填补后的长度
	 * @return 填补后的字符串
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, " ");
	}

	/**
	 * 往字符串前重复填补另一个字符串，填补后的字符串长度为size
	 * 
	 * @param str
	 *            String 要进行填补操作的字符串
	 * @param size
	 *            int 字符串填补后的长度
	 * @param delim
	 *            String 填补的字符串
	 * @return 填补后的字符串
	 */
	public static String leftPad(String str, int size, String delim) {
		size = (size - str.length()) / delim.length();
		if (size > 0) {
			str = repeat(delim, size) + str;
		}
		return str;
	}

	// Stripping
	// --------------------------------------------------------------------------

	/**
	 * 剔除首尾的空字符，该空字符的判断由Character.isWhitespace定义
	 * 
	 * @param str
	 *            进行剔除操作的字符串
	 * @return 剔除操作后的字符串
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	/**
	 * 从字符串str开始和结束位置各剔除一段字符串，剔除的字符串的每个字符都被包含在delim中 如：strip("1234","134")="2"
	 * 
	 * @param str
	 *            进行剔除操作的字符串
	 * @param delim
	 *            包含要剔除的字符
	 * @return 剔除操作后的字符串
	 */
	public static String strip(String str, String delim) {
		str = stripStart(str, delim);
		return stripEnd(str, delim);
	}

	/**
	 * 剔除字符串数组中每个字符串首尾的空字符，该空字符的判断由Character.isWhitespace定义
	 * 
	 * @param strs
	 *            进行剔除操作的字符串数组
	 * @return 剔除操作后的字符串数组
	 */
	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	/**
	 * 字符串数组中每个字符串首尾剔除一段字符串，剔除的字符串的每个字符都被包含在delimiter中
	 * 
	 * @param strs
	 *            进行剔除操作的字符串数组
	 * @param delimiter
	 *            包含要剔除的字符
	 * @return 剔除操作后的字符串数组
	 */
	public static String[] stripAll(String[] strs, String delimiter) {
		if ((strs == null) || (strs.length == 0)) {
			return strs;
		}
		int sz = strs.length;
		String[] newArr = new String[sz];
		for (int i = 0; i < sz; i++) {
			newArr[i] = strip(strs[i], delimiter);
		}
		return newArr;
	}

	/**
	 * 从字符串str结束位置剔除一段字符串，剔除的字符串的每个字符都被包含在strip中 如：stripEnd("1234","34")="12"
	 * 
	 * @param str
	 *            进行剔除操作的字符串
	 * @param strip
	 *            包含要剔除的字符
	 * @return 剔除操作后的字符串
	 */
	public static String stripEnd(String str, String strip) {
		if (str == null) {
			return null;
		}
		int end = str.length();

		if (strip == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else {
			while ((end != 0) && (strip.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	/**
	 * 从字符串str开始位置剔除一段字符串，剔除的字符串的每个字符都被包含在strip中；
	 * 如：stripStart("1234","13")="234"
	 * 
	 * @param str
	 *            进行剔除操作的字符串
	 * @param strip
	 *            包含要剔除的字符
	 * @return 剔除操作后的字符串
	 */
	public static String stripStart(String str, String strip) {
		if (str == null) {
			return null;
		}

		int start = 0;

		int sz = str.length();

		if (strip == null) {
			while ((start != sz) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else {
			while ((start != sz) && (strip.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	// Case conversion
	// --------------------------------------------------------------------------

	/**
	 * 将字符串转化为大写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return the 转化后的字符串
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * 将字符串转化为小写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转化后的字符串
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * 将字符串的第一个字母转换为小写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转化后的字符串
	 */
	public static String uncapitalise(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return "";
		}
		return new StringBuffer(str.length()).append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 将字符串的第一个字母转换为大写
	 * 
	 * @param str
	 *            将要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String capitalise(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return "";
		}
		return new StringBuffer(str.length()).append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 将字符串中的字母大小写互换；如： swapCase("a B")="A b"; swapCase("a b")="A B";
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String swapCase(String str) {
		if (str == null) {
			return null;
		}
		int sz = str.length();
		StringBuffer buffer = new StringBuffer(sz);

		boolean whitespace = false;
		char ch = 0;
		char tmp = 0;

		for (int i = 0; i < sz; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				tmp = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				tmp = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				if (whitespace) {
					tmp = Character.toTitleCase(ch);
				} else {
					tmp = Character.toUpperCase(ch);
				}
			} else {
				tmp = ch;
			}
			buffer.append(tmp);
			whitespace = Character.isWhitespace(ch);
		}
		return buffer.toString();
	}

	/**
	 * 将字符串首字符和空白符后的首字符转换为大写；如： capitaliseAllWords("af bb c")="Af Bb C";
	 * 
	 * @param str
	 *            将要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String capitaliseAllWords(String str) {
		if (str == null) {
			return null;
		}
		int sz = str.length();
		StringBuffer buffer = new StringBuffer(sz);
		boolean space = true;
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				buffer.append(ch);
				space = true;
			} else if (space) {
				buffer.append(Character.toTitleCase(ch));
				space = false;
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	// Nested extraction
	// --------------------------------------------------------------------------

	/**
	 * 从母字符串中取出两个相同子串之间的字符串；如： getNestedString("afbac","a")="fb";
	 * 
	 * @param str
	 *            母字符串
	 * @param tag
	 *            子串，用以截取位置定位
	 * @return str中两个tag间的子串
	 */
	public static String getNestedString(String str, String tag) {
		return getNestedString(str, tag, tag);
	}

	/**
	 * 从母字符串中取出两个子串之间的子串；如： getNestedString("afbbc","f","c")="bb";
	 * 
	 * @param str
	 *            母字符串
	 * @param open
	 *            子字符串1
	 * @param close
	 *            子字符串2
	 * @return 子字符串1和子字符串2之间的子串
	 */
	public static String getNestedString(String str, String open, String close) {
		if (str == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * 计算子串在母串中出现的次数
	 * 
	 * @param str
	 *            母串
	 * @param sub
	 *            子串
	 * @return 子串sub在母串str中出现的次数
	 */
	public static int countMatches(String str, String sub) {
		if (str == null) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	// Character Tests
	// --------------------------------------------------------------------------

	/**
	 * 检查字符串中是否全是字母
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是字母 false 不全是字母
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串中是否全是字母或是空格
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是字母或空格 false 不全是字母或空格
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串中是否全是字母或数字
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是字母或数字 false 不全是字母或数字
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串中是否全是字母或数字或空格
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是字母或数字或空格 false 不全是字母或数字或空格
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串中是否全是数字； 字符串对象为Null将返回false,空字符串返回true.
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是数字 false 不全是数字
	 */
	public static boolean isNumeric(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串中是否全是数字或空格 字符串对象为Null将返回false,空字符串返回true.
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return true 全是数字或空格 false 不全是数字或空格
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// --------------------------------------------------------------------------

	/**
	 * 检查字符串是否为空对象，若是以空字符串替代
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @return 如果字符串对象为空，返回空字符串；若不为空对象，返回本身
	 */
	public static String defaultString(String str) {
		return defaultString(str, "");
	}

	/**
	 * 检查字符串是否为空对象，若是以指定默认字符串替代
	 * 
	 * @param str
	 *            要进行检查的字符串
	 * @param defaultString
	 *            默认字符串，用来替代字符串为空对象的情况
	 * @return 如果字符串对象为空，返回默认字符串；若不为空对象，返回本身
	 */
	public static String defaultString(String str, String defaultString) {
		return (str == null) ? defaultString : str;
	}

	// Reversing
	// --------------------------------------------------------------------------

	/**
	 * 倒叙排列字符串
	 * 
	 * @param str
	 *            要进行倒叙排列的字符串
	 * @return 倒叙排列后的字符串
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * 倒叙排列有分隔符的字符串，但分隔符之间的字符串并不倒叙排列。如：
	 * reverseDelimitedString("abc,def,ghi",",")="ghi,def,abc";
	 * 
	 * @param str
	 *            要进行倒叙排列的字符串
	 * @param delimiter
	 *            分隔符
	 * @return 倒叙排列后的字符串
	 */
	public static String reverseDelimitedString(String str, String delimiter) {
		// could implement manually, but simple way is to reuse other,
		// probably slower, methods.
		String[] strs = split(str, delimiter);
		reverseArray(strs);
		return join(strs, delimiter);
	}

	/**
	 * 倒叙排列数组内的元素
	 * 
	 * @param array
	 *            the array to reverse
	 */
	private static void reverseArray(Object[] array) {
		int i = 0;
		int j = array.length - 1;
		Object tmp;

		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	/**
	 * 检查字符串中的每个字符是否都包含在指定的字符数组中
	 * 
	 * @param str
	 *            要检查的字符串
	 * @param char[] 字符数组
	 * @return true str中的每个字符都包含在valid字符数组中 ; false str中的每个字符不都包含在valid字符数组中
	 */
	public static boolean containsOnly(String str, char[] valid) {
		if (str == null || valid == null) {
			return false;
		}

		int strSize = str.length();
		int validSize = valid.length;

		for (int i = 0; i < strSize; i++) {
			boolean contains = false;
			for (int j = 0; j < validSize; j++) {
				if (valid[j] == str.charAt(i)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				return false;
			}
		}

		return true;
	}

	/******** 从StringUtil类迁移来的属性和方法 ********/
	/**
	 * 用指定算法加密字符串，该算法需在web.xml文件中声明
	 * 
	 * @param password
	 *            待加密的字符串
	 * @param algorithm
	 *            加密算法
	 * 
	 * @return encypted 加密后的字符串
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	// /**
	// * 用Base64加密，将密码存储至Cookie时用。
	// *
	// * This is weak encoding in that anyone can use the decodeString
	// * routine to reverse the encoding.
	// *
	// * @param str
	// * @return String
	// */
	// public static String encodeString(String str) {
	// sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	// return encoder.encodeBuffer(str.getBytes()).trim();
	// }
	//
	// /**
	// * 用Base64解码
	// *
	// * @param str
	// * @return String
	// */
	// public static String decodeString(String str) {
	// sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
	// try {
	// return new String(dec.decodeBuffer(str));
	// } catch (IOException io) {
	// throw new RuntimeException(io.getMessage(), io.getCause());
	// }
	// }
	//
	/******** 从StringHelper类迁移的方法 ********/
	/**
	 * 判断字符串是否为null或为空串
	 * 
	 * @param value
	 *            字符串
	 * @return boolean
	 */
	public static boolean isNull(String value) {
		return value == null || value.equals("") || value.equals("null");
	}

	/**
	 * 判断字符串是否不为null而且不是空串
	 * 
	 * @param value
	 *            字符串
	 * @return boolean
	 */
	public static boolean notNull(String value) {
		return value != null && !value.equals("");
	}

	/**
	 * 从一个字符串的某个位置读取一个Boolean类型的值
	 * 
	 * @param str
	 *            字符串
	 * @param index
	 *            位置
	 * @return boolean 如果该位置的字符为1返回true否则返回false
	 */
	public static boolean readBool(String str, int index) {
		boolean result = false;
		if (str != null && str.length() > index && index >= 0) {
			result = str.charAt(index) == '1';
		}
		return result;
	}

	/**
	 * 把一个Boolean类型字符设置到一个字符串的某一个字符
	 * 
	 * @param str
	 *            字符串
	 * @param index
	 *            位置，必须>=0零的值，下标从0开始到字符串.length-1，如果index>length则给字符串补#
	 * @param value
	 *            布尔类型的值
	 * @return String 返回一个新的字符串
	 */
	public static String writeBool(String str, int index, boolean value) {
		StringBuffer result = str == null ? new StringBuffer() : new StringBuffer(str);
		if (index >= 0) {
			int nullcount = index - result.length();
			for (int i = 0; i <= nullcount; i++) {
				result.append('#');
			}
			result.setCharAt(index, value ? '1' : '0');
		}
		return result.toString();
	}

	/**
	 * 计算字符串出现在数组中的索引，字符串相符判断的标准为equals，如：indexOfArray("a",{"a","ab","abc"})=0;
	 * 
	 * @param str
	 * @param ary
	 * @return
	 * @author renbaogang
	 */
	public static int indexOfArray(String str, String[] ary) {
		int pos = -1;
		if (str == null || ary == null) {
			return pos;
		}
		for (int i = 0; i < ary.length; i++) {
			if (str.equals(ary[i])) {
				pos = i;
				return pos;
			}
		}
		return pos;
	}

	/**
	 * 过滤掉字符串 首或尾 出现的某个字符 eg:37010200100 去尾 370102001 0001230001 去头 1230001
	 * 
	 * @param str
	 *            要过滤的字符串
	 * @param s
	 *            要过滤的字符
	 * @param pos
	 *            位置 0:首，1：尾
	 * @return 过滤后的字符串
	 * @author zhuweiming
	 * @date 2008-4-9
	 */
	public static String filterChar(String str, char s, int pos) {
		if (pos == 0) {
			int flag = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == s) {
					flag = i;
				} else {
					break;
				}
			}
			if (flag != 0) {
				flag += 1;
			}
			return str.substring(flag, str.length());
		} else {
			int flag = str.length() - 1;
			for (int i = str.length() - 1; i >= 0; i--) {
				if (str.charAt(i) == s) {
					flag = i;
				} else {
					break;
				}
			}
			if (flag == str.length() - 1) {
				flag += 1;
			}
			return str.substring(0, flag);
		}
	}
	/**
	 * 辅助处理 处理人双引号问题
	 * @param dealUser
	 * @param cond
	 * @return
	 */
	public static String handleDealUser(String dealUser, String cond) {
		if (StringUtil.isEmpty(dealUser)) return "";
		String dealer = "";
		String[] dealUserArr = null;
		if ("add".equals(cond)) {//添加"
			dealUserArr = dealUser.split(",");
			for (int i = 0; i < dealUserArr.length; i++) {
				dealer += "\""+dealUserArr[i]+"\",";
			}
		}
		if("remove".equals(cond)){//去掉"
			if (dealUser.indexOf("\"")<0)return dealUser; 
			dealUserArr = dealUser.split(",");
			for (int i = 0; i < dealUserArr.length; i++) {
				dealer += dealUserArr[i].substring(1,dealUserArr[i].length()-1) + ",";
			}
		}
		dealer = dealer.substring(0,dealer.length()-1);	
		return dealer;
	}
}

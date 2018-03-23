/**============================================================
 * 版权：  版权所有 (c) 2002 - 2012
 * 包： com.spark.lib.character
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2011-4-30       Administrator        
 * ============================================================*/

package com.frame.core.util;

import java.util.List;
import java.util.Map;

/**
 * <p>检查是否为空公共方法</p>
 *
 * <p>Copyright: 版权所有 (c) 2002 - 2008<br>

 *
 * @author xiangzhongqiu
 * @version 2011-4-30
 */

public class CheckIsNull{

	/**
	 * 检查当前对象是否不为空  包括NULL和空字符串 
	 * 如果不为空返回true
	 * 
	 * @param obj  要检查的对象
	 * @return boolean 返回true或false
	 */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

	/**
	 * 检查当前对象是否为空  包括NULL和空字符串
	 * 如果为空返回true
	 * @param obj
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj){
		boolean isTrue = false;
		if(null == obj){
			isTrue = true;
		}
		if(obj instanceof String){
			if(((String)obj).trim().length() == 0){
				isTrue = true;
			}
			else{
				isTrue = false;
			}
		}
		if(obj instanceof List<?>){
			if(((List<?>)obj).size() <= 0){
				isTrue = true;
			}
		}
		if(obj instanceof Map<?,?>){
			if(((Map<?,?>)obj).size() <= 0){
				isTrue = true;
			}
		}
		return isTrue;
	}

}

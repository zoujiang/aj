package com.frame.core.util;

import java.util.HashMap;
import java.util.Map;

public class UrlUtil {
	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;
		//去除大小写转化
		//strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}

		return strPage;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
        //去除大小写转化
		//strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
	
	public static String getParamValue(String strURL,String param) {
		Map<String, String> mapRequest = UrlUtil.URLRequest(strURL);
		return mapRequest.get(param);
		
	}

	public static String getRelationUrl(String strURL) {
		String url1 = null;
		if(StringUtil.isEmpty(strURL))return "";
		String tmp1= SystemConfig.getValue("img.http.url");
		if(strURL.indexOf(tmp1) < 0){
			tmp1= SystemConfig.getValue("res.http.url");
		}
		if(strURL.indexOf(tmp1) < 0)return strURL;
		 url1 = strURL.substring(strURL.indexOf(tmp1)+tmp1.length());
		 return url1;
		
	}
	
	public static String getAbsolutePath(String[] paths,String prefix){
		StringBuffer sb = new StringBuffer();
		for(String path:paths){
			if(StringUtil.isNotEmpty(path)){
				sb.append(prefix + path + ",");
			}
		}
		if(sb.length() > 0)sb = sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		// 请求url
		String str = "index.jsp?action=del&id=123&sort=";
		String str2 = "http://localhost:8080/video/service/img?img=/handy/1422457458292.jpg";
		
		String param = "http://localhost:8080/aam/service/img?img=dddddddd";
		System.out.println(getRelationUrl(str2));
		// url页面路径
		/*System.out.println(UrlUtil.UrlPage(str));

		// url参数键值对
		String strRequestKeyAndValues = "";
		Map<String, String> mapRequest = UrlUtil.URLRequest(str);

		for (String strRequestKey : mapRequest.keySet()) {

			String strRequestValue = mapRequest.get(strRequestKey);
			strRequestKeyAndValues += "key:" + strRequestKey + ",Value:"
					+ strRequestValue + ";";

		}
		System.out.println(strRequestKeyAndValues);

		// 获取无效键时，输出null
		System.out.println(mapRequest.get("action"));*/
		//System.out.println(UrlUtil.getParamValue(str2, "img"));

	}

}

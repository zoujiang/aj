package com.aj.pay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SignUtil {

	public static String createMD5Sign(SortedMap params, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = params.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		sb.append("key=" + key);
		
		String sign = MD5Util.MD5Encode(sb.toString());
		
		return sign.toUpperCase();
	}
	public static boolean validateSign(String xml , String key){
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Iterator<Element> rootItor = root.elementIterator();
			SortedMap<String, Object> sm = new TreeMap<String, Object>();
			String sign = "";
			while (rootItor.hasNext()) {
				Element tmpElement = rootItor.next();
				String name = tmpElement.getName();
				String val = tmpElement.getTextTrim();
				if("sign".equals(name)){
					sign = val;
				}else{
					sm.put(name, val);
				}
			}
			String createSign = createMD5Sign(sm, key);
			if(sign.equals(createSign)){
				return true;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}

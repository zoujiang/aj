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
import org.dom4j.Node;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;


public class JsonXmlUtil {

	
	public static String xml2JSON(String xml) {
		return new XMLSerializer().read(xml).toString();
	}

	public static String json2XML(String json) {
		JSONObject jobj = JSONObject.fromObject(json);
		String xml = new XMLSerializer().write(jobj);
		return xml;
	}
	
	public static String createXml(SortedMap<String, Object> dataMap){
		
		StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            sb.append("<" + entry.getKey() + ">");
            sb.append(entry.getValue());
            sb.append("</" + entry.getKey() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
	}
	
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
		Map.Entry entry = (Map.Entry) it.next();
		String k = (String) entry.getKey();
		String v = (String) entry.getValue();
		if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)|| "sign".equalsIgnoreCase(k)) {
		sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
		} else {
		sb.append("<" + k + ">" + v + "</" + k + ">");
		}
		}
		sb.append("</xml>");
		return sb.toString();
		}
	public static JSONObject xml2JSONObject(String xml){
		
		//xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxb72f926e3e77ccf2]]></appid><mch_id><![CDATA[1451029602]]></mch_id><device_info><![CDATA[WEB]]></device_info><nonce_str><![CDATA[NlY76inbagOZKFlA]]></nonce_str><sign><![CDATA[6C03450F13D5CFCAFE153C7483A880F8]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx20170329093525bf8d4e9e1a0229644903]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
		JSONObject json = new JSONObject();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Iterator<Element> rootItor = root.elementIterator();
			while (rootItor.hasNext()) {
				Element tmpElement = rootItor.next();
				String name = tmpElement.getName();
				String val = tmpElement.getTextTrim();
				json.put(name, val);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}

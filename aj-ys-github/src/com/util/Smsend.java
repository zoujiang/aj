package com.util;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.frame.core.util.HttpClient;
import com.frame.core.util.SystemConfig;

public class Smsend {
	private static Log logger=LogFactory.getLog(Smsend.class);
	//private static final String smsUrl = "http://218.206.27.199:17084/sag/smsJsonService";
	//private static final String smsUrl = "http://192.168.99.36:7084/sag/smsJsonService";
	private static final String smsUrl = SystemConfig.getValue("sag.sms.url");
	private static final String smsUser = SystemConfig.getValue("sag.sms.user");
	private static final String smsPwd = SystemConfig.getValue("sag.sms.pwd");
	private static final String smsapikey = SystemConfig.getValue("sag.sms.apikey");
	/*private static final String smsUrl = SystemConfig.getValue("sag.sms.url");*/
	/**
	 * @author xuechun
	 * @version phone多个用,分开
	 */
	public static Boolean sendSMS(String phone ,String sendMessage) throws Exception
	{
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(smsUrl);
		
		// APIKEY
		sb.append("apikey="+smsapikey);

		//用户名
		sb.append("&username="+smsUser);

		// 向StringBuffer追加密码
		sb.append("&password="+smsPwd);

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phone);

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(sendMessage,"UTF-8"));

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();
		System.out.println(inputline);
		if(inputline.startsWith("success:")){
			return true ;
		}else{
			return false ;
		}
		
		
		
		
	}
	
	
	public static void  main(String args[]) throws Exception
	{
		//发送内容
		String content = "美联软通JAVA示例"; 
		
		String phone="18580205972";
		sendSMS(phone, "【旅游】尊敬的用户你好，你的优惠券兑换码为:"+"1");

	}

}

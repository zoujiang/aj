package com.frame.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * @author WangWen
 * @version 创建时间：2011-10-25 上午11:43:05
 */
public class HttpClient {

	private static Logger logger = Logger.getLogger(HttpClient.class.getName());

	private static final String $line_feed = System.getProperty("line.separator");
	
	/**
	 * Delete基础请求
	 *
	 * @param url
	 *            请求地址
	 * @return 
	 * @return 请求成功后的结果
	 */
	public static byte[] doDelete(String url,String requestParam) {

		InputStream in;
		byte[] bre = null;
		HttpResponse response;			
			HttpDelete delete = new HttpDelete(url);			
			delete.setHeader("Authorization", requestParam);				
			delete.setHeader("Accept-Encoding", "gzip, deflate");
			delete.setHeader("Accept-Language", "zh-CN");
			delete.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");
			try {
				response = new DefaultHttpClient().execute(delete);
				if (response != null) {
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		

		return bre;
	

	}
	
	/**
	 * post方式提交请求
	 * 
	 * @param strURL.
	 *            请求地址
	 * @param requestStr.
	 *            参数值
	 * @return 服务器返回
	 */
	public static String doPostMethod(String strURL, String requestStr) {
		StringBuffer sbReturn = new StringBuffer("");
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader br = null;
		try {
			url = new URL(strURL);
			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			httpConnection.setRequestProperty("Cache-Control", "no-cache");
			httpConnection.setRequestProperty("Content-Length", String.valueOf(requestStr.length()));
			httpConnection.setRequestProperty("Cache-Control", "no-cache");
			httpConnection.setRequestProperty("accept", "*/*");

			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setConnectTimeout(20000);
			httpConnection.setReadTimeout(20000);

			out = httpConnection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
			osw.write(requestStr);
			osw.flush();
			osw.close();

			in = httpConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String strRead = "";
			while ((strRead = br.readLine()) != null) {
				sbReturn.append(strRead);
				sbReturn.append($line_feed);
			}
		} catch (Exception ex) {
			logger.info(strURL + "  无法访问   " + ex.getMessage());
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (in != null)
					in.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (br != null)
					br.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (httpConnection != null)
					httpConnection.disconnect();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
		}
		return sbReturn.toString();
	}
	
	
	public static String doJSONPostMethod(String strURL, String requestStr) {
		StringBuffer sbReturn = new StringBuffer("");
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader br = null;
		try {
			url = new URL(strURL);
			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.setRequestProperty("Cache-Control", "no-cache");
			httpConnection.setRequestProperty("Content-Length", String.valueOf(requestStr.length()));
			httpConnection.setRequestProperty("Cache-Control", "no-cache");
			httpConnection.setRequestProperty("accept", "*/*");

			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setConnectTimeout(60000);
			httpConnection.setReadTimeout(60000);

			out = httpConnection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
			osw.write(requestStr);
			osw.flush();
			osw.close();

			in = httpConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String strRead = "";
			while ((strRead = br.readLine()) != null) {
				sbReturn.append(strRead);
				sbReturn.append($line_feed);
			}
		} catch (Exception ex) {
			logger.info(strURL + "  �޷�����   " + ex.getMessage());
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (in != null)
					in.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (br != null)
					br.close();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (httpConnection != null)
					httpConnection.disconnect();
			} catch (Exception fx) {
				fx.printStackTrace();
			}
		}
		return sbReturn.toString();
	}
	
	public static void main(String[] args) {
		JSONObject params =  new JSONObject();
		params.put("model", "an");
		params.put("channelId", "109");
		params.put("curVer", "7");
		
		JSONObject all =  new JSONObject();
		
		all.put("serviceName", "queryEdit");
		all.put("callType", "02");
		all.put("params", params);
		
		System.out.println(all);

	}
}

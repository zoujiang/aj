package com.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * @author WangWen
 * @version 创建时间：2011-10-25 上午11:43:05
 */
public class HttpClient {


	private static Logger logger = Logger.getLogger(HttpClient.class.getName());

	private static final String $line_feed = System.getProperty("line.separator");
	
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
			httpConnection.setRequestProperty("Content-Type","text/json");
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
			//logger.info("sbReturn:"+sbReturn);
		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	public static void main(String[] args) {
		
        String url = "http://61.183.55.34:1890/edu-gateway/eduServer";
		
		JSONObject all = new JSONObject();
		all.put("mac", "32435lksldjflejger8jflsdjfou9jsdfo");
		
		
		JSONObject head = new JSONObject();
		head.put("method", "022223");
		head.put("serialNumber", "34234444444444");
		head.put("version", "v1.0");
		
		JSONObject body = new JSONObject();
		body.put("number", "5");
		
		all.put("head", head);
		all.put("body", body);
		
		String requestStr = all.toString();
		
		String res = doPostMethod(url, requestStr);
		
		System.out.println(res);

	}
}

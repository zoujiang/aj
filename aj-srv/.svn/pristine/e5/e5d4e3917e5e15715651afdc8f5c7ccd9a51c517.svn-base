package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
	public static String doPostFileMethod() {
		  org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();  
           String respStr = "";
	        try {  
	      
	            HttpPost httppost = new HttpPost("http://113.204.225.238:8888/aj/service/fileUpload");  
	              
	            FileBody bin = new FileBody(new File("c:\\aa.jpg"));  
	  
	            MultipartEntity reqEntity = new MultipartEntity();  
	              
	            reqEntity.addPart("file", bin);//file1为请求后台的File upload;属性      
	            httppost.setEntity(reqEntity);  
	              
	            HttpResponse response = httpclient.execute(httppost);  
	              
	                  
	            int statusCode = response.getStatusLine().getStatusCode();  
	              
	                  
	            if(statusCode == HttpStatus.SC_OK){  
	                      
	                System.out.println("服务器正常响应.....");  
	                  
	                HttpEntity resEntity = response.getEntity();  
	                  
	                respStr =    EntityUtils.toString(resEntity);
	                System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据  
	                  
	                System.out.println(resEntity.getContent());     
	  
	                EntityUtils.consume(resEntity);  
	            }  
	                  
	            } catch (ParseException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            } finally {  
	                try {   
	                    httpclient.getConnectionManager().shutdown();   
	                } catch (Exception ignore) {  
	                      
	                }  
	            }
			return respStr;  
	}
	
	public static void main(String[] args) {
		
      
		String res = doPostFileMethod();
		
		System.out.println(res);

	}
}

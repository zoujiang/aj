package com.frame.ifpr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.frame.core.util.SystemConfig;

/**
 * 
 * @author yhb
 * @version 1.0
 */
public class HttpClientUtils {

	private final static Log logger = LogFactory.getLog(HttpClientUtils.class);

	/**
	 * PATH:TODO（测试用地址）
	 * 
	 * @since Ver 1.1
	 */
	public static String PATH = "http://localhost:8080/?name=queue&opt=put";

	/**
	 * invokePostService(http请求，使用post调用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param @param url
	 * @param @param paramvalue
	 * @param @param charsetName
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	public static String invokePostService(String url, String paramvalue,
			String charsetName, int timeoutms) {
		String resStr = "";
		HttpClient httpclient = null;
		logger.debug("request url : " + url);
		logger.debug("request param :" + paramvalue);
		try {
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setIntParameter("http.socket.timeout",
					timeoutms);
			httpclient.getParams().setParameter("Accept-Language",
					"zh-CN,zh;q=0.8");
			HttpPost httppost = new HttpPost(url);
			StringEntity entity = new StringEntity(paramvalue, charsetName);
			// entity.setContentType("application/json");
			entity.setChunked(true);
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream is = resEntity.getContent();
				resStr = convertStreamToString(is, charsetName);
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			// HttpClient的实例不再需要时，降低连接，管理器关闭，以确保立即释放所有系统资源
			if (httpclient != null)
				httpclient.getConnectionManager().shutdown();
		}
		logger.debug("interface return result: " + resStr);
		return resStr;
	}

	/**
	 * invokeFormPostService form方式提交
	 * 
	 * @param @param url
	 * @param @param paramkey
	 * @param @param paramvalue
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	public static String invokeFormPostService(String url,
			Map<String, String> keyValues, String charsetName, int timeoutms) {
		String resStr = "";
		HttpClient httpclient = null;
		logger.debug("request url : " + url);
		try {
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setIntParameter("http.socket.timeout",
					timeoutms);
			httpclient.getParams().setParameter("Accept-Language",
					"zh-CN,zh;q=0.8");
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> set = keyValues.entrySet();
			Iterator<Entry<String, String>> itr = set.iterator();
			while (itr.hasNext()) {
				Entry<String, String> entry = itr.next();
				logger.info("request param :" + entry.getKey() + ":"
						+ entry.getValue());
				formparams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					"UTF-8");
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream is = resEntity.getContent();
				resStr = convertStreamToString(is, charsetName);
			}
		} catch (ClientProtocolException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			// HttpClient的实例不再需要时，降低连接，管理器关闭，以确保立即释放所有系统资源
			if (httpclient != null)
				httpclient.getConnectionManager().shutdown();
		}
		logger.debug("interface return result: " + resStr);
		return resStr;
	}

	/**
	 * invokeGetService(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param @param url
	 * @param @param charsetName
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	public static String invokeGetService(String url, String charsetName,
			int timeoutms) {
		String resStr = "";
		HttpClient httpclient = null;
		logger.info("request url : " + url);
		try {
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setIntParameter("http.socket.timeout",
					timeoutms);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			// resStr = resEntity.toString();
			if (resEntity != null) {
				InputStream is = resEntity.getContent();
				resStr = convertStreamToString(is, charsetName);
			}
		} catch (ClientProtocolException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			// HttpClient的实例不再需要时，降低连接，管理器关闭，以确保立即释放所有系统资源
			if (httpclient != null)
				httpclient.getConnectionManager().shutdown();
		}
		logger.debug("interface return result: " + resStr);
		return resStr;
	}

	public static String convertStreamToString(InputStream is,
			String charsetName) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, charsetName));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
		return sb.toString();
	}

	public static String getContent(String url, String charsetName) {
		String backContent = null;
		HttpURLConnection connection = null;
		try {
			URL imageUrl = new URL(url);
			connection = (HttpURLConnection) imageUrl.openConnection();
			// connection.setConnectTimeout( 5000 );
			connection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.107 Safari/535.1");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection
					.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

			InputStream is = connection.getInputStream();
			// BufferedReader in = new BufferedReader(new InputStreamReader(is,
			// charsetName));
			// String line = "";
			// StringBuffer buffer = new StringBuffer();
			// while ((line = in.readLine()) != null) {
			// buffer.append(line);
			// }
			// //end 读取整个页面内容
			// backContent = buffer.toString();
			backContent = convertStreamToString(is, charsetName);
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		logger.debug(backContent);
		return backContent;
	}

	/**
	 * getAbsoluteURL利用相对地址得到绝对地址
	 * 
	 * @param @param baseURI
	 * @param @param relativePath
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	public static String getAbsoluteURL(String baseURI, String relativePath) {
		String abURL = null;
		try {
			URI base = new URI(baseURI);// 基本网页URI
			URI abs = base.resolve(relativePath);// 解析于上述网页的相对URL，得到绝对URI
			URL absURL = abs.toURL();// 转成URL
			abURL = absURL.toString();
			logger.debug(relativePath + " of " + baseURI
					+ " convert to absoluteurl is : " + abURL);
		} catch (MalformedURLException e) {
			logger.info(" getabsolute url MalformedURLException "
					+ e.getMessage());
		} catch (URISyntaxException e) {
			logger.info(" getabsolute url URISyntaxException " + e.getMessage());
		} finally {
			return abURL;
		}
	}

	public static void main(String[] args) {
	}
}

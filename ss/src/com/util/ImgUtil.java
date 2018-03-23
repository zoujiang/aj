package com.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;

public class ImgUtil {
	
	//private static final String IMG_PRE_URL = SystemConfig.getValue("img.http.url");
	private static final String IMG_PRE_URL = SystemConfig.getValue("host.http.url");
	
	//添加前缀
	public static String fixUrls(String urls) {
		if (StringUtil.isEmpty(urls)) {
			return "";
		}
		
		String[] us = urls.split(";");
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String u : us ) {
			if (!isFirst) {
				sb.append(";");
			}
			sb.append(fixUrl(u));
			isFirst = false;
		}
		return sb.toString();
	}
	//添加前缀
	public static String fixUrl(String url) {
		if (StringUtil.isEmpty(url)) {
			return "";
		}
		
		if (url.startsWith("http://") ||url.startsWith("https://")) {
			return url;
		} else {
			return IMG_PRE_URL + url;
		}
	}
	
	public static void main(String[] args) {
		String url = "/res/crt/19.jpg";
		System.out.println(IMG_PRE_URL);
		System.out.println(fixUrl(url));
		url = "http://localhost:8080/spt/clt/1452599828589.jpg;http://localhost:6666/tis/service/img?img=/clt/1452599828740.jpg;http://localhost:6666/tis/service/img?img=/clt/1452599828997.jpg;http://localhost:6666/tis/service/img?img=/clt/1452599829049.jpg;http://localhost:6666/tis/service/img?img=/clt/1452599829455.jpg";
		System.out.println(movePreUrls(url));
	}
	
	
	//添加前缀
	public static String movePreUrls(String urls) {
		if (StringUtil.isEmpty(urls)) {
			return "";
		}
		
		return urls.replace(IMG_PRE_URL, "");
	}
	//添加前缀
	public static String movePreUrl(String url) {
		if (StringUtil.isEmpty(url)) {
			return "";
		}
		
		if (url.startsWith("http://")) {
			return url.replace(IMG_PRE_URL, "");
		} else {
			return url;
		}
	}
	
	/**
	 * 
	 * @param imgUrl
	 *            图片地址
	 * @return
	 */
	public static BufferedImage getBufferedImage(String imgUrl) {
		URL url = null;
		InputStream is = null;
		BufferedImage img = null;
		try {
			url = new URL(imgUrl);
			is = url.openStream();
			img = ImageIO.read(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

}

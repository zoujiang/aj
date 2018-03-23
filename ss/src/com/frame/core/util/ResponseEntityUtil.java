package com.frame.core.util;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {
	/**
	 * 在 beanconvertToJson 需要 指定编码为UTF-8
	 */
	public static MediaType mediaType_TextHtml_UTF8 = new MediaType("text", "html", Charset.forName("UTF-8"));
	
	
	public static <T> ResponseEntity<T> getResponseEntity(T t){
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setContentType(mediaType_TextHtml_UTF8);
		ResponseEntity<T> entity = new ResponseEntity<T>(t,httpHeader,HttpStatus.OK);
		return entity;
	}

}

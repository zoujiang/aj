package com.frame.ifpr.service.interfaceability.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.frame.core.vo.MessageVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.exception.ReceiveException;
import com.frame.ifpr.service.interfaceability.InterfaceAbility;
import com.frame.ifpr.util.HttpClientUtils;
import com.frame.ifpr.util.JsonUtil;

/**
 * REST风格的方式对外发布与接入
 * 
 * @author lishun
 * 
 */
@Service("restInterface")
public class RestInterface implements InterfaceAbility {
	protected Log logger=LogFactory.getLog(RestInterface.class);
	@Override
	public Object processPublish(Object object) throws PublicException {
		String jsonString = null;
		jsonString = JsonUtil.getJsonString4JavaPOJO(object);
		return jsonString;
	}

	@Override
	public Object processReceive(MessageVo mess) throws ReceiveException {
		logger.info("请求地址为："+mess.getUrl());
		logger.info("请求json为："+mess.getContent());
		String result = HttpClientUtils.invokePostService(mess.getUrl(), mess
				.getContent(), mess.getCharset(), mess.getTimeout());
		logger.info("请求结果为："+result);
		return result;
	}

}

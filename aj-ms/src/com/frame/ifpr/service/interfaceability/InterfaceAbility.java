package com.frame.ifpr.service.interfaceability;

import com.frame.core.vo.MessageVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.exception.ReceiveException;

/**
 * 发布方式接口定义
 * 
 * @author lishun
 * 
 */
public interface InterfaceAbility {

	public Object processPublish(Object object) throws PublicException;

	public Object processReceive(MessageVo obj) throws ReceiveException;
}

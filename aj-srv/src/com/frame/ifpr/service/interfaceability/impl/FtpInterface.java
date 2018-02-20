package com.frame.ifpr.service.interfaceability.impl;

import org.springframework.stereotype.Service;

import com.frame.core.vo.MessageVo;
import com.frame.ifpr.service.interfaceability.InterfaceAbility;

/**
 * FTP的方式对外发布与接入
 * 
 * @author lishun
 * 
 */
@Service
public class FtpInterface implements InterfaceAbility {

	@Override
	public Object processPublish(Object object) {
		return null;
	}

	@Override
	public Object processReceive(MessageVo mess) {
		return null;
	}

}

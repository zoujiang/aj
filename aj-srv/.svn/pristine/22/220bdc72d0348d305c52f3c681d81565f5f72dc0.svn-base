package com.frame.ifpr.service.base;

import com.frame.core.vo.MessageVo;
import com.frame.ifpr.exception.ReceiveException;
import com.frame.ifpr.service.ReceiveService;
import com.frame.ifpr.service.interfaceability.InterfaceAbility;

/**
 * 接收服务通用处理类
 * 
 * @author lishun
 * 
 */
public class BaseReceiveService implements ReceiveService {

	protected InterfaceAbility interfaceAbility;

	@Override
	public Object receiveService(MessageVo mess) throws ReceiveException {
		String result = (String) interfaceAbility.processReceive(mess);
		return result;
	}

	public InterfaceAbility getInterfaceAbility() {
		return interfaceAbility;
	}

	public void setInterfaceAbility(InterfaceAbility interfaceAbility) {
		this.interfaceAbility = interfaceAbility;
	}

}

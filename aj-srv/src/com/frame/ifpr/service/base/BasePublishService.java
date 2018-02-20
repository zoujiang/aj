package com.frame.ifpr.service.base;

import org.apache.commons.beanutils.DynaBean;

import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.common.author.Author;
import com.frame.ifpr.common.parser.ParserAndWraper;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.exception.common.ParseException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.service.interfaceability.InterfaceAbility;
import com.frame.ifpr.vo.ErrorVo;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;

/**
 * 发布服务通用处理类
 * 
 * @author lishun
 * 
 */
public class BasePublishService implements PublishService {
	private InterfaceAbility interfaceAbility;
	private ParserAndWraper parserAndWraper;
	private PublishService publishService;
	private Author author;

	@Override
	public Object publishService(Object object) throws PublicException {
		ResponseVo responseVo = null;
		RequestVo requestVo = null;
		try {
			requestVo = (RequestVo) parserAndWraper.doParse(object);
			
			//rvo.setServiceName("");
			//requestVo.setServiceName(serviceName);
		} catch (ParseException e) {
			ErrorVo errorVo = new ErrorVo();
			errorVo.setReturnCode(ResponseVo.ERROR);
			errorVo.setErrorMsg(e.getMessage());
			errorVo.setServiceName("无法获得服务名");
			return interfaceAbility.processPublish(errorVo);
		}
		try {
			Object target = publishService.publishService(requestVo);
			if(target instanceof ResponseVo){
				responseVo = (ResponseVo)target;
				responseVo.setServiceName(requestVo.getServiceName());
				return interfaceAbility.processPublish(responseVo);
			}else if(target instanceof DynaBean){
				return interfaceAbility.processPublish(target);
			}else{
				throw new PublicException("不是ResponseVo的子类或者动态Bean");
			}
		} catch (PublicException e) {
			ErrorVo errorVo = new ErrorVo();
			errorVo.setReturnCode(ResponseVo.ERROR);
			errorVo.setErrorMsg(e.getMessage());
			errorVo.setServiceName(requestVo.getServiceName());
			return interfaceAbility.processPublish(errorVo);
		}
		
	}

	public InterfaceAbility getInterfaceAbility() {
		return interfaceAbility;
	}

	public void setInterfaceAbility(InterfaceAbility interfaceAbility) {
		this.interfaceAbility = interfaceAbility;
	}

	public ParserAndWraper getParserAndWraper() {
		return parserAndWraper;
	}

	public void setParserAndWraper(ParserAndWraper parserAndWraper) {
		this.parserAndWraper = parserAndWraper;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public PublishService getPublishService() {
		return publishService;
	}

	public void setPublishService(PublishService publishService) {
		this.publishService = publishService;
	}

}

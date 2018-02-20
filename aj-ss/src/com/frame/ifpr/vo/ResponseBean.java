package com.frame.ifpr.vo;

import com.frame.core.vo.ResultVo;

//直接ResponseVo, 其他地方调用的时候不用再一个一个set，少两行代码
public class ResponseBean {

	public static ResponseVo error(String errorMsg) {
		ResponseVo resp = new ResponseVo();
		resp.setResult(null);
		resp.setReturnCode(ResponseVo.ERROR);
		resp.setErrorMsg(errorMsg);
		return resp;
	}
	
	public static ResponseVo success(ResultVo resultVo) {
		ResponseVo resp = new ResponseVo();
		resp.setResult(resultVo);
		resp.setReturnCode(ResponseVo.SUCCESS);
		return resp;
	}
	
	public static ResponseVo error(String errorMsg, String serviceName) {
		ResponseVo resp = new ResponseVo();
		resp.setResult(null);
		resp.setServiceName(serviceName);
		resp.setReturnCode(ResponseVo.ERROR);
		resp.setErrorMsg(errorMsg);
		return resp;
	}
	
	public static ResponseVo success(ResultVo resultVo, String serviceName) {
		ResponseVo resp = new ResponseVo();
		resp.setResult(resultVo);
		resp.setServiceName(serviceName);
		resp.setReturnCode(ResponseVo.SUCCESS);
		return resp;
	}
	

}

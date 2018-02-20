package com.aj.sys.service;

import java.rmi.RemoteException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.sms.SingletonClient;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.ResponseVo;
import com.util.Constant;

/**
 * 发送短信
 * */

@Service("sendSms")
public class SendSmsService implements PublishService{

	private Logger log = Logger.getLogger(SendSmsService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userTel = params.optString("userTel");
		String content = params.optString("content");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userTel == null || "".equals(userTel)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userTel为空！");
			return returnJSON.toString();
		}else if(content == null || "".equals(content)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "content为空！");
				return returnJSON.toString();
		}
		
		//下发短信
		try {
			 SingletonClient.getClient().sendSMS(new String[] {userTel}, "\u3010\u6211\u7231\u591a\u5b9d\u9c7c\u3011"+content, "",5);
			 returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			 returnJSON.put("result", result);
			 returnJSON.put("errorMsg", "");
			 return returnJSON.toString();
		} catch (RemoteException e) {
			e.printStackTrace();
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", SystemConfig.getValue("msg.sms.apply.failure"));
			return returnJSON.toString();
		}
	}

}

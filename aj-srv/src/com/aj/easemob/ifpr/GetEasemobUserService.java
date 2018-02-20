package com.aj.easemob.ifpr;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.easemob.service.EasemobUserService;
import com.aj.easemob.utils.PropertiesUtils;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("easemobUserGet")
public class GetEasemobUserService implements PublishService {

	@Autowired EasemobUserService easemobUserService;

	private Logger log = Logger.getLogger(GetEasemobUserService.class);
	
	@Override
	public Object publishService(Object obj) throws PublicException {

		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		
		if(params.optString("userName") == null || "".equals(params.optString("userName"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信ID不能为空！");
			return returnJSON.toString();
		}
		try {
			JSONObject object = easemobUserService.getUser(params);
			log.info(object.toString());
			if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
				JSONArray array = object.getJSONArray("entities");
				result.put("succMsg", "获取环信用户信息成功！");	
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", array.get(0));
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			} else {
				returnJSON.put("result", result);
				log.info(object.toString());
				String msg = PropertiesUtils.getProperties().getProperty(object.get("statusCode").toString());
				if(msg != null && !"".equals(msg)){
					returnJSON.put("errorMsg", msg);
				} else {
					returnJSON.put("errorMsg", "获取环信用户信息失败！");
				}
				
				return returnJSON.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "获取环信用户信息失败！");
			return returnJSON.toString();
		}
	}
}

package com.aj.easemob.ifpr;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.easemob.service.EasemobGroupService;
import com.aj.easemob.utils.PropertiesUtils;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("easemobGroupDel")
public class DelEasemobGroupService implements PublishService {

	@Autowired EasemobGroupService easemobGroupService;

	private Logger log = Logger.getLogger(EasemobGroupService.class);
	
	@Override
	public Object publishService(Object obj) throws PublicException {

		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		
		if(params.optString("group_id") == null || "".equals(params.optString("group_id"))){//群组名称
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组Id不能为空！");
			return returnJSON.toString();
		}
		try {
			JSONObject object = easemobGroupService.delGroup(params);
			log.info(object.toString());
			if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
				result.put("succMsg", "移除环信群组成功！");	
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", object.getJSONObject("data"));
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			} else {
				returnJSON.put("result", result);
				log.info(object.toString());
				String msg = PropertiesUtils.getProperties().getProperty(object.get("statusCode").toString());
				if(msg != null && !"".equals(msg)){
					returnJSON.put("errorMsg", msg);
				} else {
					returnJSON.put("errorMsg", "移除环信群组失败！");
				}
				
				return returnJSON.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "移除环信群组失败！");
			return returnJSON.toString();
		}
	}

}

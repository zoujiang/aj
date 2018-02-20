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

@Service("easemobGroupMod")
public class ModEasemobGroupService implements PublishService {

	@Autowired EasemobGroupService easemobGroupService;

	private Logger log = Logger.getLogger(ModEasemobGroupService.class);
	
	@Override
	public Object publishService(Object obj) throws PublicException {

		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		
		if(params.optString("group_id") == null || "".equals(params.optString("group_id"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组ID不能为空！");
			return returnJSON.toString();
		}
		if(params.optString("groupname") != null && !"".equals(params.optString("groupname"))){
			params.put("groupname", params.optString("groupname").replaceAll(" ", "+"));
		}
		if(params.optString("description") != null && !"".equals(params.optString("description"))){
			params.put("description", params.optString("description").replaceAll(" ", "+"));
		}
		if(params.optString("maxusers") != null && !"".equals(params.optString("maxusers")) &&params.optInt("maxusers") == 0){
			params.put("maxusers", 200);
		}
		try {
			JSONObject object = easemobGroupService.modGroup(params);
			log.info(object.toString());
			if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
				result.put("succMsg", "修改环信群组信息成功！");	
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", object.getJSONObject("data"));
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			} else {
				returnJSON.put("result", result);
				log.info(object.toString());
				if(object.get("statusCode") != null){
					String msg = PropertiesUtils.getProperties().getProperty(object.get("statusCode").toString());
					if(msg != null && !"".equals(msg)){
						returnJSON.put("errorMsg", msg);
					}
				} else {
					returnJSON.put("errorMsg", "修改环信群组信息失败！");
				}
				return returnJSON.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "修改环信群组信息失败！");
			return returnJSON.toString();
		}
	}

}

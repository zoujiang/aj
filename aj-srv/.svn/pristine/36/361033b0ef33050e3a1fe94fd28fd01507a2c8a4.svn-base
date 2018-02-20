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

@Service("easemobGroupAdd")
public class addEasemobGroupService implements PublishService {

	@Autowired EasemobGroupService easemobGroupService;

	private Logger log = Logger.getLogger(addEasemobGroupService.class);

	@Override
	public Object publishService(Object obj) throws PublicException {


		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		
		if(params.optString("groupname") == null || "".equals(params.optString("groupname"))){//群组名称
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组名称不能为空！");
			return returnJSON.toString();
		}
		if(params.optString("desc") == null || "".equals(params.optString("desc"))){//群组描述
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组描述不能为空！");
			return returnJSON.toString();
		}
		if(params.optString("public") == null || "".equals(params.optString("public"))){//是否是公开群
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组是否公开不能为空！");
			return returnJSON.toString();
		}
		//maxusers//群组成员最大数(包括群主)，值为数值类型，默认值200

		if(params.optBoolean("public") && (params.optString("approval") == null || "".equals(params.optString("approval")))){//加入公开群是否需要批准
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组加入公开群是否需要批准不能为空！");
			return returnJSON.toString();
		}
		if(!params.optBoolean("public")){
			params.put("approval", true);
		}
		if(params.optString("owner") == null || "".equals(params.optString("owner"))){//是否是公开群
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "环信群组管理员不能为空！");
			return returnJSON.toString();
		}
		//members //群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
		try {
			JSONObject object = easemobGroupService.createGroup(params);
			log.info(object.toString());
			if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
				result.put("succMsg", "添加环信群组成功！");	
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", object.getJSONObject("data"));
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			} else {
				returnJSON.put("result", result);
				log.info(object.toString());
				if(object.optString("error") != null && "duplicate_unique_property_exists".equals(object.optString("error"))){
					returnJSON.put("errorMsg", "环信群组已存在");
				} else{
					String msg = PropertiesUtils.getProperties().getProperty(object.get("statusCode").toString());
					if(msg != null && !"".equals(msg)){
						returnJSON.put("errorMsg", msg);
					} else {
						returnJSON.put("errorMsg", "添加环信群组失败！");
					}
				}
				
				return returnJSON.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "添加环信群组失败！");
			return returnJSON.toString();
		}
	}

}

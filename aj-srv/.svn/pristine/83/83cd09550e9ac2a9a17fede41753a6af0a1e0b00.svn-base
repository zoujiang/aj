package com.aj.sys.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

/**
 * 设置极光推送渠道ID
 * */

@Service("updateJHPushChannelServcie")
public class SaveOrUpdateJPushChannelService implements PublishService{

	private Logger log = Logger.getLogger(SaveOrUpdateJPushChannelService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String channelId = params.optString("channelId");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "smsValidateToken为空！");
			return returnJSON.toString();
		}else if(channelId == null || "".equals(channelId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "channelId为空！");
			return returnJSON.toString();
		}
		
		try {
			String hsql = " from TUser where id = ? ";
			List<TUser> users = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
			if(users != null && users.size() > 0){
				TUser  user = users.get(0);
				String cId = user.getChannelId();
				if(cId == null || !cId.equals(channelId)){
					user.setChannelId(channelId);
					baseDAO.update(user);
				}
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", "保存渠道ID成功");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "用户不存在");
			}
		} catch (Exception e) {
			
			
			log.info("设置密码失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}

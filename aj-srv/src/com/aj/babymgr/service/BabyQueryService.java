package com.aj.babymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.babymgr.vo.TBabyInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 胎儿管理
 * */

@Service("babyQuery")
public class BabyQueryService implements PublishService{

	private Logger log = Logger.getLogger(BabyQueryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String babyId = params.optString("babyId");
		String userId = params.optString("userId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(babyId == null || "".equals(babyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "babyId为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TBabyInfo where id = ? ";
		List<TBabyInfo> userList = baseDAO.getGenericByHql(hsql, babyId);
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该胎儿信息！");
			return returnJSON.toString();
		}else{
			TBabyInfo user = userList.get(0);
			result.put("userId", user.getId().toString());
			result.put("babyNo", user.getAjno());
			result.put("nickName", user.getNickname());
			result.put("conceptionDate", user.getConceptionDate());
			result.put("bbNumber", user.getBbNumber());
			result.put("bornDate", user.getExpectedDate());
			String imgUrl= SystemConfig.getValue("img.http.url");
			result.put("photo", (user.getPhotoUrl() != null && !"".equals(user.getPhotoUrl())) ? imgUrl+user.getPhotoUrl() : "");
			result.put("result", "操作成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

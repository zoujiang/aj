package com.aj.familymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 查看家庭成员
 * */

@Service("familyQuery")
public class QueryFamilyService implements PublishService{

	private Logger log = Logger.getLogger(QueryFamilyService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String targetUserId = params.optString("targetUserId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(targetUserId == null || "".equals(targetUserId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "targetUserId为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(targetUserId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户信息！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			result.put("userId", user.getId().toString());
			result.put("ajNo", user.getAjNo());
			String imgUrl= SystemConfig.getValue("img.http.url");
			if(user.getPhoto() == null || "".equals(user.getPhoto())){
				result.put("photo", "");
			}else{
				result.put("photo",   imgUrl+user.getPhoto());
			}
			result.put("nickName", user.getNickName()==null?"":user.getNickName());
			result.put("trueName", user.getTrueName()==null?"":user.getTrueName());
			result.put("six", user.getSex());
			result.put("birthday", DateUtils.formatStringDate2String(user.getBirthday(), "yyyyMMddHHmm"));
			result.put("age", DateUtils.formatBirthdat2Age(user.getBirthday()));
			result.put("sign", user.getAutograph()==null?"":user.getAutograph());
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

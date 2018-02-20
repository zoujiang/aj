package com.aj.familymgr.service;

import com.aam.model.TUser;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 根据家庭ID查询家庭成员
 * */

@Service("queryFamilyMembers")
public class QueryFamilyMembersService implements PublishService{

	private Logger log = Logger.getLogger(QueryFamilyMembersService.class);
	String imgUrl= SystemConfig.getValue("img.http.url");
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String familyId = params.optString("familyId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId不能为空！");
			return returnJSON.toString();
		}if(familyId == null || "".equals(familyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "familyId不能为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TUser where familyId = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, familyId);
		JSONArray data = new JSONArray();
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在！");
			return returnJSON.toString();
		}else {

			for (TUser user : userList) {
				JSONObject u = new JSONObject();
				u.put("memberId", user.getId());
				u.put("memberPhoto", (user.getPhoto() == null || "".equals(user.getPhoto())) ? "" : imgUrl +user.getPhoto());
				u.put("memberNickName", user.getNickName());
				u.put("relationName", "");
				data.add(u);
			}
		}

		hsql = " from TChildrenInfo where familyId = ? ";
		List<TChildrenInfo> childrenInfoList = baseDAO.getGenericByHql(hsql, familyId);
		if(childrenInfoList != null && childrenInfoList.size() > 0){
			for (TChildrenInfo user : childrenInfoList) {
				JSONObject u = new JSONObject();
				u.put("memberId", user.getId());
				u.put("memberPhoto", (user.getPhotoUrl() == null || "".equals(user.getPhotoUrl())) ? "" : imgUrl +user.getPhotoUrl());
				u.put("memberNickName", user.getNickname());
				u.put("relationName", "");
				data.add(u);
			}
		}

		result.put("data", data);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}

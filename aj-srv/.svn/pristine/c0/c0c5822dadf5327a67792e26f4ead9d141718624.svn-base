package com.aj.childrenmgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.childrenmgr.vo.TChildrenInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 孩子查询
 * */

@Service("childrenQuery")
public class ChildrenQueryService implements PublishService{

	private Logger log = Logger.getLogger(ChildrenQueryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String childrenId = params.optString("childrenId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(childrenId == null || "".equals(childrenId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "childrenId为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TChildrenInfo where id = ? ";
		List<TChildrenInfo> userList = baseDAO.getGenericByHql(hsql, childrenId);
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该孩子信息！");
			return returnJSON.toString();
		}else{
			TChildrenInfo user = userList.get(0);
			result.put("userId", user.getId().toString());
			result.put("ajNo", user.getAjno());
			String imgUrl= SystemConfig.getValue("img.http.url");
			result.put("photo", imgUrl+user.getPhotoUrl());
			result.put("nickName", user.getNickname());
			result.put("trueName", user.getTruename());
			result.put("sex", user.getSex());
			result.put("birthday", user.getBirthday());
			result.put("isPrivate", user.getIsPrivate());
			result.put("succMsg", "操作成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

package com.aj.familymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familyrelation.vo.TFamilyRelationship;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 保存结婚日期
 * */

@Service("marrieddateSave")
public class SaveMarriedService implements PublishService{

	private Logger log = Logger.getLogger(SaveMarriedService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String marrieddate = params.optString("marrieddate");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(marrieddate == null || "".equals(marrieddate)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "marrieddate为空！");
			return returnJSON.toString();
		}
		
		
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			TFamilyInfo family = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
			family.setIsMarried(0);
			family.setMarriedDate(marrieddate);
			baseDAO.update(family);
			
			result.put("succMsg", "保存结婚纪念日成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
				
		}
		
	}
	public void addUserRelation(String userId, TUser family, String callId, String isPrivate, String description){
		
		TFamilyRelationship relation = new TFamilyRelationship();
		relation.setCreateUserId(Integer.parseInt(userId));
		relation.setRelationUserId(family.getId());
		relation.setCallId(Integer.parseInt(callId)); 
		relation.setCreateDate(DateUtils.currentDate());
		if("1".equals(isPrivate)){
			relation.setIsPrivate(1);
		}else if("2".equals(isPrivate)){
			relation.setIsPrivate(0);
		}
		relation.setDescription(description);
		relation.setIsConfirm(1);
		baseDAO.save(relation);
		
		//调用短信发送接口，向family.getUserTel发送添加短信以便确认关系
		
	}
	

}

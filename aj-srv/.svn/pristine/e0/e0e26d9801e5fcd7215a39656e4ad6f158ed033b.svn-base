package com.aj.petmgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.petmgr.vo.TPetInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 宠物查询
 * */

@Service("petQuery")
public class PetQueryService implements PublishService{

	private Logger log = Logger.getLogger(PetQueryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String petId = params.optString("petId");
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
		if(petId == null || "".equals(petId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "petId为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TPetInfo where id = ? and createUserId = ?";
		List<TPetInfo> petList = baseDAO.getGenericByHql(hsql, petId, userId);
		if(petList == null || petList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该宠物信息或该宠物不属于您！");
			return returnJSON.toString();
		}else{
			String imgUrl= SystemConfig.getValue("img.http.url");
			TPetInfo pet = petList.get(0);
			result.put("petId", pet.getId());
			result.put("ajNo", pet.getAjno());
			result.put("photo", imgUrl+pet.getPhotoUrl());
			result.put("nickName", pet.getNickName());
			result.put("sex", pet.getSex());
			result.put("birthday", pet.getBirthday());
			result.put("petType", pet.getPetType());
			result.put("succMsg", "操作成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

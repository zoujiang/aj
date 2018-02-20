package com.aj.familyrelation.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.familyrelation.vo.TFamilyRelationKey;
import com.aj.praylettermgr.service.QueryPrayLetterService;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 9.5查询家庭关系密钥（aj_family_relation_key_validtion）
 * */

@Service("familyRelationKeyValidation")
public class FamilyRelationKeyValidationService implements PublishService{

	private Logger log = Logger.getLogger(FamilyRelationKeyValidationService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String key = params.optString("key");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(key == null || "".equals(key)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "key为空！");
			return returnJSON.toString();
		}
		String hsql = "from TFamilyRelationKey where userId = ? and key = ?";
		List<TFamilyRelationKey> pkList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId), key);
		if(pkList != null && pkList.size() >0){
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "验证通过");
			return returnJSON.toString();
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "密钥错误");
			return returnJSON.toString();
		}
	}

}

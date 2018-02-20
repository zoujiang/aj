package com.aj.familymgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 根据家人ID查看家人
 * */

@Service("userByFamilyList")
public class UserByFamilyListService implements PublishService{

	private Logger log = Logger.getLogger(UserByFamilyListService.class);
	
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
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(familyId == null || "".equals(familyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "familyId为空！");
			return returnJSON.toString();
		}
		String imgUrl= SystemConfig.getValue("img.http.url");
		String sql = "    SELECT u.id userId, u.AJ_NO ajNo, c.NAME callName, CONCAT('"+imgUrl+"',u.photo) photo  FROM t_user u  , t_call_enum c , t_family_relationship r  WHERE u.FAMILYID = ?  AND  r.RELATION_USER_ID = u.ID AND r.CREATE_USER_ID = ? AND r.CALL_ID = c.ID AND  (r.IS_DEL IS NULL OR r.IS_DEL =1)";
		List<Map<String, Object>> userList = baseDAO.getGenericBySQL(sql, new Object[]{familyId, userId});
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该家庭信息！");
			return returnJSON.toString();
		}else{
			result.put("list", userList);
			result.put("succMsg", "操作成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

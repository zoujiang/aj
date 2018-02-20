package com.aj.familymgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查看我的更多家人
 * */

@Service("moreFamilyList")
public class QueryMyMoreFamilyListService implements PublishService{

	private Logger log = Logger.getLogger(QueryMyMoreFamilyListService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
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
		String imgUrl= SystemConfig.getValue("img.http.url");
		String sql = "SELECT u.id userId,c.id callId, c.NAME callName, CONCAT('"+imgUrl+"',u.photo) photo    FROM t_family_relationship r LEFT JOIN t_call_enum c ON r.CALL_ID = c.ID , t_user u  WHERE  r.RELATION_USER_ID = u.ID AND  r.is_confirm = 0 and   c.IS_SHOW = 1  AND r.CREATE_USER_ID = ?  GROUP BY u.FAMILYID  ";
		List<Map<String, Object>> userList = baseDAO.getGenericBySQL(sql, new Object[]{ Integer.parseInt(userId)});
		result.put("userList", userList);
		result.put("succMsg", "查询成功");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

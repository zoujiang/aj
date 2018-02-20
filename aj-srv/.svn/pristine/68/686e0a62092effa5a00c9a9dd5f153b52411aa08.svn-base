package com.aj.familyrelation.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
/**
 * 找回删除家人
 * */
@Service("repairRelation")
public class RepairRelationService implements PublishService{

	private Logger log = Logger.getLogger(RepairRelationService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String oper = params.optString("oper");
		String userIds = params.optString("userIds");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		try {
			if("0".equals(oper)){
				//查询已删除的家人列表
				String sql = "SELECT u.ID userId, u.NICK_NAME nickName , u.PHOTO photo, c.NAME callName FROM t_family_relationship r, t_call_enum c, t_user u   WHERE r.CALL_ID = c.ID AND u.ID = r.RELATION_USER_ID AND  r.CREATE_USER_ID = ? AND r.IS_DEL = 0";
				List<Map<String, Object>> userList = baseDAO.getGenericBySQL(sql, new Object[]{userId});
				result.put("succMsg", "查询成功");
				result.put("list", userList);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		} catch (Exception e) {
			log.info("查询已删除家人列表时异常："+e.getMessage());
			result.put("succMsg", "");
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误");
			return returnJSON.toString();
		}
		try {
			if("1".equals(oper)){
				if(userIds == null || "".equals(userIds)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "userIds为空！");
					return returnJSON.toString();
				}
				String inIds = "'"+ userIds.replaceAll(",", "','")+"'";
				String sql = "UPDATE t_family_relationship r SET r.IS_DEL = 1 , r.IS_PRIVATE = 0  WHERE r.CREATE_USER_ID = ? AND r.RELATION_USER_ID IN ("+inIds+") ";
				baseDAO.execteNativeBulk(sql, userId);
				result.put("succMsg", "操作成功");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		} catch (Exception e) {
			log.info("找回已删除家人时异常："+e.getMessage());
			result.put("succMsg", "");
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误");
			return returnJSON.toString();
		}
		
		result.put("succMsg", "");
		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "服务器内部错误");
		return returnJSON.toString();
	}	
}

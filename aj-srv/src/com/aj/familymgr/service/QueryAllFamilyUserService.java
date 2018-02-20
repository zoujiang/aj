package com.aj.familymgr.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


/**
 * 查看所有家庭成员
 * */

@Service("queryAllFamilyUser")
public class QueryAllFamilyUserService implements PublishService{

	private Logger log = Logger.getLogger(QueryAllFamilyUserService.class);
	
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
		
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			//String sql = "SELECT DISTINCT u.ID userId, CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.NICK_NAME END nickName, u.PHOTO photo FROM t_home_info h,t_user u LEFT JOIN t_remark_info r ON u.ID = r.relation_id AND r.type = 1 and r.create_user_id = ?  WHERE h.create_user_id =  CASE WHEN h.is_private = '0' THEN  ? ELSE ?  END AND CASE WHEN h.invite_area = '1' THEN h.relation_user_id = u.FAMILYID ELSE h.relation_user_id = u.ID END order by u.nick_name";
			String ascOrDesc = "asc";
			if(user.getSex() > 0){
				ascOrDesc = "desc";
			}
			
		/*	String sql = "SELECT * FROM "+
							 " (SELECT DISTINCT "+
							  "  u.ID userId,"+
							  "  CASE "+
							  "    WHEN r.remark IS NOT NULL "+
							  "    THEN r.remark "+
							  "    ELSE u.NICK_NAME "+
							  "  END nickName,"+
							  "  u.PHOTO photo "+
							 " FROM "+
							  "  t_home_info h,"+
							  "  t_user u "+
							  "  LEFT JOIN t_remark_info r "+
							  "    ON u.ID = r.relation_id "+
							  "    AND r.type = 1 "+
							  "    AND r.create_user_id = ? "+
							 " WHERE u.familyid = ? "+
							  "  AND h.create_user_id = "+
							  "  CASE "+
							  "    WHEN h.is_private = '0' "+
							  "    THEN ? "+
							  "    ELSE ? "+
							  "  END "+
							  "  AND "+
							  "  CASE "+
							  "    WHEN h.invite_area = '1' "+
							  "    THEN h.relation_user_id = u.FAMILYID "+
							  "    ELSE h.relation_user_id = u.ID "+
							  "  END "+
							 " ORDER BY u.sex "+ascOrDesc+") temp "+
						"	UNION ALL "+
						"	SELECT  * FROM "+
						"	  (SELECT DISTINCT "+
							"    u.ID userId,"+
							"    CASE "+
							"      WHEN r.remark IS NOT NULL "+
							"      THEN r.remark "+
							"      ELSE u.NICK_NAME "+
							"    END nickName,"+
							"    u.PHOTO photo "+
							"  FROM "+
							"    t_home_info h,"+
							"    t_user u "+
							"    LEFT JOIN t_remark_info r "+
							"      ON u.ID = r.relation_id "+
							"      AND r.type = 1 "+
							"      AND r.create_user_id = ? "+
							"  WHERE h.is_valid = 0 and u.familyid != ? "+
							"    AND h.create_user_id = "+
							"    CASE "+
							"      WHEN h.is_private = '0' "+
							"      THEN ? "+
							"      ELSE ? "+
							"    END "+
							"    AND "+
							"    CASE "+
							"      WHEN h.invite_area = '1' "+
							"      THEN h.relation_user_id = u.FAMILYID "+
							"      ELSE h.relation_user_id = u.ID "+
							"    END "+
							"  ORDER BY h.id,"+
							"    u.sex) t2 ";*/
			String sql = "SELECT * FROM "+
					" (SELECT DISTINCT "+
					"  u.ID userId,"+
					"  CASE "+
					"    WHEN r.remark IS NOT NULL "+
					"    THEN r.remark "+
					"    ELSE u.NICK_NAME "+
					"  END nickName,"+
					"  u.PHOTO photo "+
					" FROM "+
					"  t_user u "+
					"  LEFT JOIN t_remark_info r "+
					"    ON u.ID = r.relation_id "+
					"    AND r.type = 1 "+
					"    AND r.create_user_id = ? "+
					" WHERE u.familyid = ? "+
					" ORDER BY u.sex "+ascOrDesc+") temp "+
					"	UNION ALL "+
					"	SELECT  * FROM "+
					"	  (SELECT DISTINCT "+
					"    u.ID userId,"+
					"    CASE "+
					"      WHEN r.remark IS NOT NULL "+
					"      THEN r.remark "+
					"      ELSE u.NICK_NAME "+
					"    END nickName,"+
					"    u.PHOTO photo "+
					"  FROM "+
					"    t_home_info h,"+
					"    t_user u "+
					"    LEFT JOIN t_remark_info r "+
					"      ON u.ID = r.relation_id "+
					"      AND r.type = 1 "+
					"      AND r.create_user_id = ? "+
					"  WHERE h.is_valid = 0 and u.familyid != ? "+
					"    AND h.create_user_id = "+
					"    CASE "+
					"      WHEN h.is_private = '0' "+
					"      THEN ? "+
					"      ELSE ? "+
					"    END "+
					"    AND "+
					"    CASE "+
					"      WHEN h.invite_area = '1' "+
					"      THEN h.relation_user_id = u.FAMILYID "+
					"      ELSE h.relation_user_id = u.ID "+
					"    END "+
					"  ORDER BY h.id,"+
					"    u.sex) t2 ";
			
			List<Map<String, Object>> familyUserList =  baseDAO.getGenericBySQL(sql, new Object[]{userId, user.getFamilyId(),  userId, user.getFamilyId(), user.getFamilyId(), userId});
			List<Map<String, Object>> familyUserList2 = new ArrayList<Map<String,Object>>();
			if(familyUserList != null && familyUserList.size() > 0){
				String imgUrl= SystemConfig.getValue("img.http.url");
				for(Map<String, Object> map : familyUserList){
					if( userId.equals(map.get("userId").toString())){
						//把自己从列表剔除
						/*familyUserList.remove(map);
						continue;*/
					}
						
					map.put("photo", (map.get("photo") == null || "".equals(map.get("photo"))) ? "" : imgUrl+ map.get("photo") .toString());
					familyUserList2.add(map);
				}
				
			}
			
			result.put("list", familyUserList2);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

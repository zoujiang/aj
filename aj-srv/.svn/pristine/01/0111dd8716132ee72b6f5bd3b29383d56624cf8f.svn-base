package com.aj.familymgr.service;

import java.util.ArrayList;
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
 * 查看家庭资料
 * */

@Service("queryFamilyInfo")
public class QueryFamilyInfoService implements PublishService{

	private Logger log = Logger.getLogger(QueryFamilyInfoService.class);
	
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
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户！");
			return returnJSON.toString();
		}else{
			String sql = "SELECT u.id targetUserId, f.family_name familyName,f.is_married isMarried,case when f.married_date is null then '' else f.married_date end marriedDate, l.content familyLive FROM t_family_info f LEFT JOIN t_family_live l ON f.id = l.family_id , t_user u WHERE f.id = u.FAMILYID  AND u.id = ? ORDER BY l.create_date DESC LIMIT 0,1";
			List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{targetUserId});
			if(list != null &&  list.size() > 0){
				result.putAll(list.get(0));
			}else{
				result.put("targetUserId", targetUserId);
				result.put("familyName", "");
				result.put("familyLive", "");
				result.put("isMarried", 3);
				result.put("marriedDate", "");
			}
			//查询家庭头像
			
		   String queryHomePhotos ="SELECT u.PHOTO FROM t_user u WHERE  u.FAMILYID = (SELECT t.FAMILYID FROM t_user t WHERE t.id  = ?) "+
								"	UNION  ALL  "+
							//	"	SELECT u.photo_url PHOTO FROM t_children_info u WHERE ( u.is_private = '0' or (u.is_private = '1' and u.CREATE_USER_ID = ?) ) and  u.family_id = (SELECT t.FAMILYID FROM t_user t WHERE t.id  = ?) "+
								"	SELECT u.photo_url PHOTO FROM t_children_info u WHERE ( u.is_private = '0' or (u.is_private = '1' and u.CREATE_USER_ID = ?) ) and  u.family_id like  CONCAT('%',(SELECT t.FAMILYID FROM t_user t WHERE t.id  = ?),'%') "+
								"	UNION  ALL "+
							//	"	SELECT u.photo_url PHOTO FROM t_baby_info u WHERE ( u.is_private = '0' or (u.is_private = '1' and u.CREATE_USER_ID = ?) )  and  u.family_id = (SELECT t.FAMILYID FROM t_user t WHERE t.id  = ?) ";
								"	SELECT u.photo_url PHOTO FROM t_baby_info u WHERE ( u.is_private = '0' or (u.is_private = '1' and u.CREATE_USER_ID = ?) )  and  u.family_id like  CONCAT('%',(SELECT t.FAMILYID FROM t_user t WHERE t.id  = ?),'%') ";
								List<String> par = new ArrayList<String>();
								par.add(targetUserId);
								par.add(targetUserId);
								par.add(targetUserId);
								par.add(targetUserId);
								par.add(targetUserId);
								if(userId.equals(targetUserId)){
									//查自己家的
									queryHomePhotos += " UNION  ALL SELECT p.PHOTO_URL PHOTO FROM t_pet_info p WHERE p.CREATE_USER_ID = ?";
									par.add(targetUserId);
								}
		   List<Map<String, Object>> homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, par.toArray());
		   String photos = "";
		   String prefix = SystemConfig.getValue("img.http.url");
		   if(homePhotoList != null && homePhotoList.size() > 0){
			   
			   for(int i=0;i<homePhotoList.size();i++){
				   if(homePhotoList.get(i) != null && !"".equals(homePhotoList.get(i))){
					   
					   photos += prefix + homePhotoList.get(i).get("PHOTO")+";";
				   }
			   }
		   }
		   if(photos.length() > 0){
			   result.put("homePhoto", photos.substring(0, photos.length()-1));
		   }else{
			   result.put("homePhoto", photos);
		   }
			
			
			result.put("succMsg", "查询成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
	}
			
}

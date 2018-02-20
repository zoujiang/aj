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
 * 查看我的家人列表
 * */

@Service("queryMyFamily")
public class QueryMyFamilyService implements PublishService{

	private Logger log = Logger.getLogger(QueryMyFamilyService.class);
	
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
			String sql = "from TUser where familyId = ?";
			List<TUser> familyUserList =  baseDAO.getGenericByHql(sql, user.getFamilyId());
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			String imgUrl= SystemConfig.getValue("img.http.url");
			int i = 2;
			if(familyUserList != null && familyUserList.size() > 0){
				sql = "select remark from t_remark_info where create_user_id = ? and relation_id = ? and type = '1'";
				
				for(TUser familyUser : familyUserList){
					Map<String, Object> map = new HashMap<String, Object>();
					List<Map<String, Object>> remarkList = baseDAO.getGenericBySQL(sql, new Object[]{userId, familyUser.getId()});
					if( userId.equals(familyUser.getId().toString())){
						//自己
						map.put("callName", "我");
						map.put("index", 1);
					}else{
						if(familyUser.getSex() == 0){
							map.put("callName", "老公");
						}else{
							map.put("callName", "老婆");
						}
						map.put("index", i);
						i++ ;
					}
					map.put("photo", (familyUser.getPhoto() == null || "".equals(familyUser.getPhoto())) ? "" : (imgUrl+ familyUser.getPhoto()));
					map.put("userId", familyUser.getId());
					if(remarkList != null && remarkList.size()>0){
						map.put("nickName", remarkList.get(0).get("remark"));
					}else{
						map.put("nickName", familyUser.getNickName());
					}
					resultList.add(map);
				}
			}
			//查询child
			hsql = "SELECT "+
					 " u.id,"+
					 " CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.nickname END  nickName,"+
					 " sex,"+
					 " photo_url photo ,"
					 + " fixSortName, fixCallName "+
				"	FROM "+
					"  t_children_info u left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? "+
					//" WHERE u.family_id = ?  "+
					" WHERE u.family_id like  CONCAT('%',?,'%')  "+
					 " AND ( u.is_private = '0' or (u.is_private = '1' and u.create_user_id = ?) ) "+
				" ORDER BY u.SEX ";
			//查询小孩信息，单独放在一个List中
			List<Map<String, Object>> childrenList = baseDAO.getGenericBySQL(hsql, new Object[]{userId, user.getFamilyId(), user.getId()+""});
			for(Map<String, Object> child : childrenList){
				Map<String, Object> map = new HashMap<String, Object>();
			/*	if(child.get("sex") !=null && "1".equals(child.get("sex").toString()) ){
					map.put("callName", "儿子");
				}else{
					map.put("callName", "女儿");
				}
					*/
				String fixSortName =  (child.get("fixSortName") == null || child.get("fixSortName").equals("无")) ? "" : child.get("fixSortName").toString();
				map.put("callName", fixSortName + child.get("fixCallName"));
				map.put("photo", (child.get("photo") == null || "".equals(child.get("photo"))) ? "" : (imgUrl+ child.get("photo")));
				map.put("userId", child.get("id"));
				map.put("nickName", child.get("nickName"));
				map.put("index", i);
				i++ ;
				resultList.add(map);
			}
			
			hsql = "SELECT "+
					 " u.id,"+
					 "  CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.nickname END  nickName,"+
					 "  photo_url photo"+
				"	FROM "+
					"  t_baby_info u left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? "+
					" WHERE u.family_id  like  CONCAT('%',?,'%')   "+
					 " AND ( u.is_private = '0' or (u.is_private = '1' and u.create_user_id = ?)) ";
			//查询小孩信息，
			List<Map<String, Object>> babyList = baseDAO.getGenericBySQL(hsql, new Object[]{userId, user.getFamilyId(),user.getId()+""});
			for(Map<String, Object> baby : babyList){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("callName", "胎儿");
				map.put("photo", (baby.get("photo") == null || "".equals(baby.get("photo"))) ? "" : (imgUrl+ baby.get("photo")));
				map.put("userId", baby.get("id"));
				map.put("nickName", baby.get("nickName"));
				map.put("index", i);
				i++ ;
				resultList.add(map);
			}
			
			//查询宠物
			hsql = "SELECT "+
					 " u.id userId,"+
					 "CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.NICK_NAME END  nickName,"+
					 " CONCAT('"+imgUrl+"', photo_url) photo,"+
					 " '宠物' AS callName "+
				"	FROM "+
					"  t_pet_info u left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? "+
					" WHERE u.CREATE_USER_ID = ? ";
			//查询宠物信息，
			List<Map<String, Object>> petList = baseDAO.getGenericBySQL(hsql, new Object[]{userId,userId});
			if(petList != null && petList.size() > 0){
				for(Map<String, Object> pet : petList){
					pet.put("index", i);
					i++ ;
				}
				
			}
			resultList.addAll(petList);
			
			Collections.sort(resultList, new Comparator<Map<String, Object>>() {

				@Override
				public int compare(Map<String, Object> o1,Map<String, Object> o2) {
					int i1 = Integer.parseInt(o1.get("index").toString());
					int i2 = Integer.parseInt(o2.get("index").toString());
					return i1 -i2;
				}
				
			});
			
			
			result.put("list", resultList);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}

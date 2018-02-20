package com.aj.praylettermgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.photomgr.vo.TPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 爱的传承相册查询
 * */

@Service("babyPhotosQuery")
public class BabyPhotosQueryService implements PublishService{

	private Logger log = Logger.getLogger(BabyPhotosQueryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String babyId = params.optString("childId");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(babyId == null || "".equals(babyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "childId为空！");
			return returnJSON.toString();
		}
		
		String imgUrl= SystemConfig.getValue("img.http.url");
		List<Map<String, Object>> albumList = new ArrayList<Map<String,Object>>();
		//查询该孩子是否已经传承
		String sql = "SELECT inherit_target FROM t_inherit_history WHERE child_user_id = ?";
		List<Map<String, Object>> inheritHistoryList = baseDAO.getGenericBySQL(sql, new Object[]{babyId});
		if(inheritHistoryList == null || inheritHistoryList.size() == 0){
			//未传承， 那么传承相册只有一个， 小孩资料也查询当前传入的babyid
			//查询宝宝信息
			String hsql = "from TChildrenInfo where id = ?";
			List<TChildrenInfo> userList = baseDAO.getGenericByHql(hsql, babyId);
			if(userList == null || userList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "宝宝信息不存在！");
				return returnJSON.toString();
			}else{
				result.put("babyId", userList.get(0).getId());
				result.put("babyName", userList.get(0).getNickname());
				result.put("babyPhoto", imgUrl + userList.get(0).getPhotoUrl());
				result.put("birthday", userList.get(0).getBirthday());
			}
			sql = "SELECT a.ID albumId FROM t_album a WHERE a.ALBUM_TYPE = '3' AND a.BABY_USER_ID = ? AND a.INHERIT_TARGET IS NULL";
			albumList = baseDAO.getGenericBySQL(sql, new Object[]{babyId});
		}else{
			//已经传承， 如果当前userid=传承对象id,那么就是小孩自己再查看传承相册
			if(userId.equals(inheritHistoryList.get(0).get("inherit_target").toString())){
				String hsql = "from TUser where id = ?";
				List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
				if(userList == null || userList.size() == 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "宝宝信息不存在！");
					return returnJSON.toString();
				}else{
					result.put("babyId", userList.get(0).getId());
					result.put("babyName", userList.get(0).getNickName());
					result.put("babyPhoto", imgUrl + userList.get(0).getPhoto());
					result.put("birthday", userList.get(0).getBirthday());
				}
				sql = "SELECT a.id albumId FROM t_album a WHERE a.ALBUM_TYPE = '3' AND a.BABY_USER_ID = ?  AND a.HADINHERIT = 1 AND a.INHERIT_TARGET = ?";
				albumList = baseDAO.getGenericBySQL(sql, new Object[]{babyId, userId});
			}else{
				//其他人查看
				String hsql = "from TUser where id = ?";
				List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(inheritHistoryList.get(0).get("inherit_target").toString()));
				if(userList == null || userList.size() == 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "宝宝信息不存在！");
					return returnJSON.toString();
				}else{
					result.put("babyId", userList.get(0).getId());
					result.put("babyName", userList.get(0).getNickName());
					result.put("babyPhoto", imgUrl + userList.get(0).getPhoto());
					result.put("birthday", userList.get(0).getBirthday());
				}
				sql = "SELECT a.ID albumId FROM t_album a WHERE a.ALBUM_TYPE = '3' AND a.BABY_USER_ID = ? AND a.INHERIT_TARGET IS NULL";
				albumList = baseDAO.getGenericBySQL(sql, new Object[]{babyId});
				
			}
		}
		/*
		//先通过userid为inherit_target查询传承相册， 要是没有， 那么就是父母或亲人在查看
		String sql = "SELECT  BABY_USER_ID babyUserId, HADINHERIT hadInherit, ID albumId, INHERIT_TARGET inheritTarget FROM t_album WHERE ALBUM_TYPE = 3 AND INHERIT_TARGET = ?  ";
		List<Map<String, Object>> albumList = baseDAO.getGenericBySQL(sql, new Object[]{userId});
		if(albumList != null && albumList.size() > 0){
			//孩子自己在查看， 下面查询照片也应该查询孩子拥有的传承相册
			String hsql = "from TUser where id = ?";
			List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
			if(userList == null || userList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "宝宝信息不存在！");
				return returnJSON.toString();
			}else{
				result.put("babyId", userList.get(0).getId());
				result.put("babyName", userList.get(0).getNickName());
				result.put("babyPhoto", imgUrl + userList.get(0).getPhoto());
				result.put("birthday", userList.get(0).getBirthday());
			}
		}else{
			//分2中情况， 1.未传承  2.已经传承，家人在查看相册
			sql = "SELECT  BABY_USER_ID babyUserId, HADINHERIT hadInherit, ID albumId, INHERIT_TARGET inheritTarget FROM t_album WHERE ALBUM_TYPE = 3 AND BABY_USER_ID = ?";
			albumList = baseDAO.getGenericBySQL(sql, new Object[]{babyId});
			if(albumList != null && albumList.size() > 0){
				if(albumList.get(0).get("hadInherit") != null && "1".equals(albumList.get(0).get("hadInherit").toString())){
					
					if(albumList.get(0).get("inheritTarget") != null && !"".equals(albumList.get(0).get("inheritTarget").toString())){
						//遍历到了孩子的那个传承相册
						//查询宝宝信息
						String hsql = "from TUser where id = ?";
						List<TUser> userList = baseDAO.getGenericByHql(hsql,  Integer.parseInt(albumList.get(0).get("inheritTarget").toString()));
						if(userList == null || userList.size() == 0){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "宝宝信息不存在！");
							return returnJSON.toString();
						}else{
							result.put("babyId", userList.get(0).getId());
							result.put("babyName", userList.get(0).getNickName());
							result.put("babyPhoto", imgUrl + userList.get(0).getPhoto());
							result.put("birthday", userList.get(0).getBirthday());
						}
						//把孩子相册移除
						albumList.remove(0);
					}else{
						//遍历到的父母的相册
						//查询宝宝信息
						String hsql = "from TUser where id = ?";
						List<TUser> userList = baseDAO.getGenericByHql(hsql,  Integer.parseInt(albumList.get(1).get("inheritTarget").toString()));
						if(userList == null || userList.size() == 0){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "宝宝信息不存在！");
							return returnJSON.toString();
						}else{
							result.put("babyId", userList.get(0).getId());
							result.put("babyName", userList.get(0).getNickName());
							result.put("babyPhoto", imgUrl + userList.get(0).getPhoto());
							result.put("birthday", userList.get(0).getBirthday());
						}
						//把孩子相册移除
						albumList.remove(1);
					}
					
				}else{
					//未传承,传承相册只有一个， 孩子信息也是传入的babyid
					//查询宝宝信息
					String hsql = "from TChildrenInfo where id = ?";
					List<TChildrenInfo> userList = baseDAO.getGenericByHql(hsql, babyId);
					if(userList == null || userList.size() == 0){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "宝宝信息不存在！");
						return returnJSON.toString();
					}else{
						result.put("babyId", userList.get(0).getId());
						result.put("babyName", userList.get(0).getNickname());
						result.put("babyPhoto", imgUrl + userList.get(0).getPhotoUrl());
						result.put("birthday", userList.get(0).getBirthday());
					}
				} 
			}
		}*/
		
		JSONArray photoArray = new JSONArray();
		String[] ages = {"0-1","1-2","2-3","3-4","4-5","5-6","6-7","7-8","8-9","9-10","10-11","11-12"};
		//查询爱的传承相册ID
		//hsql = "from TAlbum where  albumType = ? and babyUserId = ?";
		//List<TAlbum> albumList = baseDAO.getGenericByHql(hsql, 3, babyId);
		
		/*String sql = "SELECT  BABY_USER_ID babyUserId, HADINHERIT hadInherit, ID albumId, INHERIT_TARGET inheritTarget FROM t_album WHERE ALBUM_TYPE = 3 AND (  INHERIT_TARGET = ? or BABY_USER_ID = ? ) ";
		List<Map<String, Object>> albumList = baseDAO.getGenericBySQL(sql, new Object[]{babyId, babyId});
		*/
		
		if(albumList !=  null && albumList.size()>0){
			
			for(String bbAge : ages){
				JSONObject photoJo = new JSONObject();
				String hsql = "from TPhoto where age = ? and albumId = ? order by createDate";
				List<TPhoto> photoList = baseDAO.getGenericByHql(hsql, bbAge, albumList.get(0).get("albumId").toString());
				photoJo.put("photoAge", bbAge);
				JSONArray pts = new JSONArray();
				if(photoList != null && photoList.size() > 0){
					for(int i=0;i<photoList.size();i++){
						JSONObject jo = new JSONObject();
						jo.put("photoId", photoList.get(i).getId());
						jo.put("photoUrl", imgUrl+photoList.get(i).getPhotoUrl());
						pts.add(jo);
					}
				}
				photoJo.put("photos", pts);
				photoArray.add(photoJo);
			}
		}
		result.put("photoList", photoArray);
		result.put("succMsg", "查询成功！");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

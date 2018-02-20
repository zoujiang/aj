/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.zone.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;

/**
 * 8.6查看空间照片（aj_zone_photo_list）
 * */
@Service("zonePhotoList")
public class QueryHomeZonePhotoService implements PublishService{

	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj){
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String zoneId = params.optString("zoneId");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		Integer type = params.optInt("type") <= 0 ? 1 : params.optInt("type"); //1普通 2影楼 3幼儿园
		Integer properties = params.optInt("properties") == 0 ? 1: params.optInt("properties");  //1 查看个人空间  2查看他人空间


		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(zoneId == null || "".equals(zoneId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "zoneId为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(Integer.parseInt(pageSize)<=0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize必须大于0！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		if(Integer.parseInt(currentPage)<0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage不能小于0！");
			return returnJSON.toString();
		}


		int begin = Integer.parseInt(currentPage ) * Integer.parseInt(pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONArray list = new JSONArray();
			if(type == 1){
				
				//String sql = "SELECT SUBSTRING(t.CREATE_DATE,1,10) sharedate  FROM ( SELECT p.CREATE_DATE FROM t_photo p WHERE  p.ALBUM_ID = ?  ORDER BY p.CREATE_DATE DESC LIMIT ? , ?) t GROUP BY  SUBSTRING(t.CREATE_DATE,1,10) ORDER BY SUBSTRING(t.CREATE_DATE,1,10)  DESC";
				String sql = "SELECT t.sharedate FROM (SELECT SUBSTRING(p.CREATE_DATE,1,10) sharedate FROM t_photo p WHERE album_id = ?) t GROUP BY t.sharedate ORDER BY t.sharedate DESC LIMIT ?,?";
				List<Map<String, Object>> dateList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, begin, Integer.parseInt(pageSize)});
				if(dateList != null && dateList.size() >0){
					if(properties == 2){

						//记录该相册访问记录
						sql= "select id from t_album_access_info where album_id = ? and access_user_id = ?";
						List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, userId});
						if(accessList ==  null || accessList.size() == 0){
							//新增
							sql = "insert into t_album_access_info (id, album_id, access_time, access_user_id) values (?,?,?,?)";
							baseDAO.execteNativeBulk(sql, UUIDUtil.uuid(), zoneId, sdf.format(new Date()), userId);
						}else{
							sql = "update t_album_access_info set access_time = ? where id = ? ";
							baseDAO.execteNativeBulk(sql, sdf.format(new Date()), accessList.get(0).get("id").toString());
						}
					}

					
					String imgUrl= SystemConfig.getValue("img.http.url");
					String resUrl= SystemConfig.getValue("res.http.url");
					for(int i=0;i<dateList.size();i++){
						JSONObject data = new JSONObject();
						String sharedate = dateList.get(i).get("sharedate").toString();
						data.put("date", sharedate);
						sql = "SELECT p.id, p.descript, p.photo_url, p.video_url, p.photo_type, p.dig_num, p.dig_relation_user_id FROM t_photo p WHERE  p.ALBUM_ID = ? AND SUBSTRING(p.CREATE_DATE, 1,10) = ? ORDER BY p.CREATE_DATE DESC";
						List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, sharedate});
						JSONArray photoPreDayList = new JSONArray();
						if(photoList != null && photoList.size()>0){
							for(int j=0;j<photoList.size();j++){
								JSONObject photoDigComments = new JSONObject();
								Map<String, Object> photo = photoList.get(j);
								photoDigComments.put("photoId", photo.get("id"));
								photoDigComments.put("descript", photo.get("descript"));
								photoDigComments.put("photo", imgUrl+photo.get("photo_url"));
								photoDigComments.put("video", (photo.get("video_url") == null || "".equals(photo.get("video_url"))) ? "" :  (resUrl+photo.get("video_url")));
								photoDigComments.put("photoType", photo.get("photo_type"));
								photoDigComments.put("dig_num", photo.get("dig_num"));
								String digUsers = photo.get("dig_relation_user_id") == null ? "" : photo.get("dig_relation_user_id").toString();
								boolean isDig = false;
								//查询点赞人员信息
								if( !"".equals(digUsers)){
									String[] digUserArray = digUsers.split(",");
									//我是否点赞
									for(String dUser : digUserArray){
										if(userId.equals(dUser)){
											isDig = true;
										}
									}
									//查询点赞人员列表，最多显示最近8个
									List<Map<String, Object>> digUserList = new ArrayList<Map<String,Object>>();
									sql = "SELECT u.id dig_user_id, u.NICK_NAME dig_user_nick,CONCAT('"+imgUrl+"',u.photo)  dig_user_photo FROM t_user u WHERE  u.id IN ('"+digUsers+"')";
									List<Map<String, Object>> digAllUserList = baseDAO.getGenericBySQL(sql, null);
									if(digAllUserList != null && digAllUserList.size()>8){
										digUserList = digAllUserList.subList(0, 8);
									}else{
										digUserList = digAllUserList;
									}
									photoDigComments.put("digList", digUserList);
								}
								if(isDig){
									photoDigComments.put("hadDig", 1);
								}else{
									photoDigComments.put("hadDig", 0);
								}
								//查询评论
								sql = "SELECT "+
										"  c.ID commentId,"+
										"  c.CREATE_USER_ID cmtUserId,"+
										"  u.NICK_NAME cmtUserNickName,"+
										"  c.CONTENT cmtContent,"+
										"  c.IS_REPLY isReply,"+
										"  c.REPLY_USER_ID replyUserId,"+
										"  ru.NICK_NAME replyUserNickName,"+
										"  c.REPLY_COMMENT_ID replayCommentId "+
										"	FROM "+
										"  t_comment c "+
										"  LEFT JOIN t_user ru "+
										"    ON ru.id = c.REPLY_USER_ID,"+
										"  t_user u "+
										"	WHERE c.CREATE_USER_ID = u.id "+
										"  AND c.PHOTO_ID = ? "+
										"	ORDER BY c.CREATE_DATE "+
										"	LIMIT 0, 3 ";
								List<Map<String, Object>> commentList =baseDAO.getGenericBySQL(sql, new Object[]{photo.get("id")});
								photoDigComments.put("cmtList", commentList);
								photoPreDayList.add(photoDigComments);
							}
							data.put("photoPreDayList", photoPreDayList);
						}
						list.add(data);
					}
					
				}
			}else if(type == 2){
				String sql = "SELECT t.sharedate FROM (SELECT SUBSTRING(p.CREATE_TIME,1,10) sharedate FROM t_shop_photo p WHERE album_id = ?) t GROUP BY t.sharedate ORDER BY t.sharedate DESC LIMIT ?,?";
				List<Map<String, Object>> dateList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, begin, Integer.parseInt(pageSize)});
				if(dateList != null && dateList.size() >0){
					if(properties == 2) {
						//记录该相册访问记录
						sql = "select id from t_album_access_info where album_id = ? and access_user_id = ?";
						List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, userId});
						if (accessList == null || accessList.size() == 0) {
							//新增
							sql = "insert into t_album_access_info (id, album_id, access_time, access_user_id) values (?,?,?,?)";
							baseDAO.execteNativeBulk(sql, UUIDUtil.uuid(), zoneId, sdf.format(new Date()), userId);
						} else {
							sql = "update t_album_access_info set access_time = ? where id = ? ";
							baseDAO.execteNativeBulk(sql, sdf.format(new Date()), accessList.get(0).get("id").toString());
						}
					}
					
					String imgUrl= SystemConfig.getValue("img.http.url");
					for(int i=0;i<dateList.size();i++){
						JSONObject data = new JSONObject();
						String sharedate = dateList.get(i).get("sharedate").toString();
						data.put("date", sharedate);
						sql = "SELECT p.id,'' descript, p.photo_url,  p.dig_num, p.dig_relation_user_id FROM t_shop_photo p WHERE  p.ALBUM_ID = ? AND SUBSTRING(p.create_time, 1,10) = ? ORDER BY p.create_time DESC";
						List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, sharedate});
						JSONArray photoPreDayList = new JSONArray();
						if(photoList != null && photoList.size()>0){
							for(int j=0;j<photoList.size();j++){
								JSONObject photoDigComments = new JSONObject();
								Map<String, Object> photo = photoList.get(j);
								photoDigComments.put("photoId", photo.get("id"));
								photoDigComments.put("descript", "");
								photoDigComments.put("photo", imgUrl+photo.get("photo_url"));
								photoDigComments.put("video", "");
								photoDigComments.put("photoType", "1");
								photoDigComments.put("dig_num", photo.get("dig_num"));
								String digUsers = photo.get("dig_relation_user_id") == null ? "" : photo.get("dig_relation_user_id").toString();
								boolean isDig = false;
								//查询点赞人员信息
								if( !"".equals(digUsers)){
									String[] digUserArray = digUsers.split(",");
									//我是否点赞
									for(String dUser : digUserArray){
										if(userId.equals(dUser)){
											isDig = true;
										}
									}
									//查询点赞人员列表，最多显示最近8个
									List<Map<String, Object>> digUserList = new ArrayList<Map<String,Object>>();
									sql = "SELECT u.id dig_user_id, u.NICK_NAME dig_user_nick,CONCAT('"+imgUrl+"',u.photo)  dig_user_photo FROM t_user u WHERE  u.id IN ('"+digUsers+"')";
									List<Map<String, Object>> digAllUserList = baseDAO.getGenericBySQL(sql, null);
									if(digAllUserList != null && digAllUserList.size()>8){
										digUserList = digAllUserList.subList(0, 8);
									}else{
										digUserList = digAllUserList;
									}
									photoDigComments.put("digList", digUserList);
								}
								if(isDig){
									photoDigComments.put("hadDig", 1);
								}else{
									photoDigComments.put("hadDig", 0);
								}
								//查询评论
								sql = "SELECT "+
										"  c.ID commentId,"+
										"  c.CREATE_USER_ID cmtUserId,"+
										"  u.NICK_NAME cmtUserNickName,"+
										"  c.CONTENT cmtContent,"+
										"  c.IS_REPLY isReply,"+
										"  c.REPLY_USER_ID replyUserId,"+
										"  ru.NICK_NAME replyUserNickName,"+
										"  c.REPLY_COMMENT_ID replayCommentId "+
										"	FROM "+
										"  t_comment c "+
										"  LEFT JOIN t_user ru "+
										"    ON ru.id = c.REPLY_USER_ID,"+
										"  t_user u "+
										"	WHERE c.CREATE_USER_ID = u.id "+
										"  AND c.PHOTO_ID = ? "+
										"	ORDER BY c.CREATE_DATE "+
										"	LIMIT 0, 3 ";
								List<Map<String, Object>> commentList =baseDAO.getGenericBySQL(sql, new Object[]{photo.get("id")});
								photoDigComments.put("cmtList", commentList);
								photoPreDayList.add(photoDigComments);
							}
							data.put("photoPreDayList", photoPreDayList);
						}
						list.add(data);
					}
					
				}
			}else if(type == 3){
				String sql = "SELECT t.sharedate FROM (SELECT SUBSTRING(p.CREATE_TIME,1,10) sharedate FROM t_kindergarten_photo p WHERE album_id = ?) t GROUP BY t.sharedate ORDER BY t.sharedate DESC LIMIT ?,?";
				List<Map<String, Object>> dateList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, begin, Integer.parseInt(pageSize)});
				if(dateList != null && dateList.size() >0){
					if(properties == 2) {
						//记录该相册访问记录
						sql = "select id from t_album_access_info where album_id = ? and access_user_id = ?";
						List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, userId});
						if (accessList == null || accessList.size() == 0) {
							//新增
							sql = "insert into t_album_access_info (id, album_id, access_time, access_user_id) values (?,?,?,?)";
							baseDAO.execteNativeBulk(sql, UUIDUtil.uuid(), zoneId, sdf.format(new Date()), userId);
						} else {
							sql = "update t_album_access_info set access_time = ? where id = ? ";
							baseDAO.execteNativeBulk(sql, sdf.format(new Date()), accessList.get(0).get("id").toString());
						}
					}

					String imgUrl= SystemConfig.getValue("img.http.url");
					for(int i=0;i<dateList.size();i++){
						JSONObject data = new JSONObject();
						String sharedate = dateList.get(i).get("sharedate").toString();
						data.put("date", sharedate);
						sql = "SELECT p.id,'' descript, p.photo_url,  p.dig_num, p.dig_relation_user_id FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ? AND SUBSTRING(p.create_time, 1,10) = ? ORDER BY p.create_time DESC";
						List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, sharedate});
						JSONArray photoPreDayList = new JSONArray();
						if(photoList != null && photoList.size()>0){
							for(int j=0;j<photoList.size();j++){
								JSONObject photoDigComments = new JSONObject();
								Map<String, Object> photo = photoList.get(j);
								photoDigComments.put("photoId", photo.get("id"));
								photoDigComments.put("descript", "");
								photoDigComments.put("photo", imgUrl+photo.get("photo_url"));
								photoDigComments.put("video", "");
								photoDigComments.put("photoType", "1");
								photoDigComments.put("dig_num", photo.get("dig_num"));
								String digUsers = photo.get("dig_relation_user_id") == null ? "" : photo.get("dig_relation_user_id").toString();
								boolean isDig = false;
								//查询点赞人员信息
								if( !"".equals(digUsers)){
									String[] digUserArray = digUsers.split(",");
									//我是否点赞
									for(String dUser : digUserArray){
										if(userId.equals(dUser)){
											isDig = true;
										}
									}
									//查询点赞人员列表，最多显示最近8个
									List<Map<String, Object>> digUserList = new ArrayList<Map<String,Object>>();
									sql = "SELECT u.id dig_user_id, u.NICK_NAME dig_user_nick,CONCAT('"+imgUrl+"',u.photo)  dig_user_photo FROM t_user u WHERE  u.id IN ('"+digUsers+"')";
									List<Map<String, Object>> digAllUserList = baseDAO.getGenericBySQL(sql, null);
									if(digAllUserList != null && digAllUserList.size()>8){
										digUserList = digAllUserList.subList(0, 8);
									}else{
										digUserList = digAllUserList;
									}
									photoDigComments.put("digList", digUserList);
								}
								if(isDig){
									photoDigComments.put("hadDig", 1);
								}else{
									photoDigComments.put("hadDig", 0);
								}
								//查询评论
								sql = "SELECT "+
										"  c.ID commentId,"+
										"  c.CREATE_USER_ID cmtUserId,"+
										"  u.NICK_NAME cmtUserNickName,"+
										"  c.CONTENT cmtContent,"+
										"  c.IS_REPLY isReply,"+
										"  c.REPLY_USER_ID replyUserId,"+
										"  ru.NICK_NAME replyUserNickName,"+
										"  c.REPLY_COMMENT_ID replayCommentId "+
										"	FROM "+
										"  t_comment c "+
										"  LEFT JOIN t_user ru "+
										"    ON ru.id = c.REPLY_USER_ID,"+
										"  t_user u "+
										"	WHERE c.CREATE_USER_ID = u.id "+
										"  AND c.PHOTO_ID = ? "+
										"	ORDER BY c.CREATE_DATE "+
										"	LIMIT 0, 3 ";
								List<Map<String, Object>> commentList =baseDAO.getGenericBySQL(sql, new Object[]{photo.get("id")});
								photoDigComments.put("cmtList", commentList);
								photoPreDayList.add(photoDigComments);
							}
							data.put("photoPreDayList", photoPreDayList);
						}
						list.add(data);
					}

				}
			}
			result.put("succMsg", "查询成功");
			result.put("list", list);
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("errorMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
		}
		return returnJSON.toString();
	}

}

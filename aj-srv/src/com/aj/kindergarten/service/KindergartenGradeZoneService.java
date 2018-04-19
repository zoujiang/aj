/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenTeacher;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;

/**
 * 根据班级ID进入班级空间，用于老师上传照片视频
 * */
@Service("kindergartenGradeZone")
public class KindergartenGradeZoneService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");
	String resUrl= SystemConfig.getValue("res.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int albumId = params.optInt("albumId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(albumId == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}

		int classLimit = 9;
		
		List<Map<String, Object>> photoList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> honorZone = new ArrayList<Map<String, Object>>();
		TKindergartenAlbum album = baseDAO.get(TKindergartenAlbum.class, albumId);
		//照片/视频
		String sql = "SELECT p.id photoId,p.name name, p.category category, p.photo_url photoUrl, p.video_url videoUrl,  p.dig_num digNum, p.dig_relation_user_id, p.comment_num commentNum FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ? AND type = 2  ORDER BY p.create_time DESC limit ?";
		photoList = baseDAO.getGenericBySQL(sql, new Object[]{album.getId(), classLimit});
		if(photoList != null && photoList.size()>0){
			for(int j=0;j<photoList.size();j++){
				Map<String, Object> photo = photoList.get(j);
				if(photo.get("photoUrl") != null && !"".equals(photo.get("photoUrl").toString())){
					photo.put("photoUrl", imgUrl + photo.get("photoUrl"));
				}
				if(photo.get("videoUrl") != null && !"".equals(photo.get("videoUrl").toString())){
					photo.put("videoUrl", resUrl + photo.get("videoUrl"));
				}
				String digUsers = photo.get("dig_relation_user_id") == null ? "" : photo.get("dig_relation_user_id").toString();
				int hadDig = 0;
				//查询点赞人员信息
				if( !"".equals(digUsers)){
					String[] digUserArray = digUsers.split(",");
					//我是否点赞
					for(String dUser : digUserArray){
						if(userId.equals(dUser)){
							hadDig = 1;
						}
					}
				}
				photo.put("hadDig",hadDig);
			}
		}
		//留言板
		List<Map<String, Object>> messageBoard = baseDAO.getGenericBySQL("SELECT message_id messageId, message_content messageContent, message_tye messageType, create_time createTime ," +
				"m.user_type userType,m.user_id userId,  CASE WHEN user_type != 8 THEN (SELECT CASE TYPE WHEN 1 THEN '园长' WHEN 2 THEN '副园长' ELSE NAME END NAME FROM t_kindergarten_teacher t WHERE t.id = m.user_id)  ELSE (SELECT f.family_name FROM t_user u, t_family_info f WHERE u.familyid = f.id AND u.id = m.user_id) END messageNickName " +
				"FROM t_kindergarten_message_board m WHERE album_id = ? limit 0, 4", new Object[]{albumId});
		for(Map<String, Object> mb : messageBoard){
			if(mb.get("userType") != null && !"8".equals(mb.get("userType").toString())){
				List<TKindergartenTeacher> teacherList = baseDAO.getGenericByHql("from TKindergartenTeacher where userId = ? ", Integer.parseInt(mb.get("userId").toString()));
				if(teacherList.size() > 0){
					
					mb.put("photoUrls", (teacherList.get(0).getPhoto() == null || "".equals(teacherList.get(0).getPhoto())) ? "" : imgUrl + teacherList.get(0).getPhoto() );
				}
			}else{
				//家长
				String familySql = "select case when (photo is not null and photo != '' ) then concat('"+imgUrl+"', photo) else '' end photoUrl from t_user where familyid = (select familyid from t_user where id = ?)";
				List<Map<String, Object>> list = baseDAO.getGenericBySQL(familySql, new Object[]{mb.get("userId")});
				String photoUrls = "";
				for(Map<String, Object> m : list){
					if(!"".equals(m.get("photoUrl"))){
						photoUrls = photoUrls+ m.get("photoUrl") +",";
					}
				}
				if(photoUrls.length() > 0){
					photoUrls = photoUrls.substring(0, photoUrls.length() -1 );
				}
				mb.put("photoUrls", photoUrls);
			}
		}
		
		result.put("messageBoard", messageBoard);
		
		//荣誉专区
		sql = "SELECT id honorId, CASE WHEN photo_url IS NOT NULL AND photo_url !='' THEN  CONCAT('"+imgUrl+"',photo_url) ELSE '' END  honorUrl, create_time createTime,NAME honorName FROM t_kindergarten_honor WHERE album_id = ?";
		honorZone = baseDAO.getGenericBySQL(sql, new Object[]{albumId});
			
		result.put("photoList",photoList);
		result.put("honorZone",honorZone);
		result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}

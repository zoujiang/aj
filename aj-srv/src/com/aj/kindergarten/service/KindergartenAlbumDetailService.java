/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenAlbun;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 * 5.2.5	幼儿园相册明细查询
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("kindergartenAlbumDetail")
public class KindergartenAlbumDetailService implements PublishService{


	String imgUrl= SystemConfig.getValue("img.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int albumId = params.optInt("albumId");
		int type = params.optInt("type");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "albumId", "type"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		//班级返回6个照片或视频   个人返回9个照片或视频
		int classLimit = 6;
		if(type == 2){
			classLimit = 9;
		}
		TKindergartenAlbun album = baseDAO.get(TKindergartenAlbun.class, albumId);

		//照片/视频
		String sql = "SELECT p.id photoId,p.name name, p.category category, p.photo_url photoUrl, p.video_url videoUrl,  p.dig_num digNum, p.dig_relation_user_id, p.comment_num commentNum FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ? AND type = ?  ORDER BY p.create_time DESC limit ?";
		List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, type, classLimit});
		if(photoList != null && photoList.size()>0){
			for(int j=0;j<photoList.size();j++){
				JSONObject photoDigComments = new JSONObject();
				Map<String, Object> photo = photoList.get(j);
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
		result.put("photoList",photoList);

		//如果是班级，返回 留言板/班级成员/班级通知
		if(type == 1){
			//留言板
			List<Map<String, Object>> messageBoard = baseDAO.getGenericBySQL("SELECT message_id messageId, message_content messageContent, message_tye messageType, create_time createTime ," +
					"CASE WHEN user_type != 8 THEN (SELECT CASE TYPE WHEN 1 THEN '园长' WHEN 2 THEN '副园长' ELSE NAME END NAME FROM t_kindergarten_teacher t WHERE t.id = m.user_id)  ELSE (SELECT f.family_name FROM t_user u, t_family_info f WHERE u.familyid = f.id AND u.id = m.user_id) END messageNickName " +
					"FROM t_kindergarten_message_board m WHERE album_id = ? limit 0, 4", new Object[]{albumId});
			result.put("messageBoard", messageBoard);

			//班级成员
			TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, album.getGradeId());
			List<Map<String, Object>> classMember = new ArrayList<Map<String, Object>>();
			if(grade != null){
				//查询学校园长信息
				 sql = "SELECT id memberId,CASE TYPE  WHEN  1 THEN '园长' WHEN 2 THEN '副园长' WHEN 3 THEN '管理人员'  END memberNickName, CASE WHEN photo is not null THEN  CONCAT('"+imgUrl+"', photo) ELSE '' END memberPhoto , 0 isVip FROM t_kindergarten_teacher t WHERE kindergarten_id = ? AND TYPE IN (1, 2, 3) ORDER BY TYPE ";
				List<Map<String, Object>>  mgrMember =	baseDAO.getGenericBySQL(sql , new Object[]{grade.getKindergartenId()});
				classMember.addAll(mgrMember);
				//查询班级老师信息

				int firstTeacher = grade.getFirstTeacher();
				String teachers = ""+firstTeacher;
				if(grade.getSecondTeacher() != null && grade.getSecondTeacher() > 0){
					teachers += ","+grade.getSecondTeacher();
				}
				if(grade.getNurse() != null && grade.getNurse()  >0){
					teachers += ","+grade.getNurse();
				}
				if(teachers != null && !"".equals(teachers)){
					sql = "SELECT id memberId,CASE TYPE  WHEN  1 THEN '园长' WHEN 2 THEN '副园长' WHEN 3 THEN '管理人员' WHEN 4 THEN '主教' WHEN 5 THEN '副教' WHEN 6 THEN '保育员' WHEN 7 THEN '其他'  END memberNickName, CASE WHEN photo is not null THEN  CONCAT('"+imgUrl+"', photo) ELSE '' END memberPhoto , 0 isVip FROM t_kindergarten_teacher WHERE id IN ("+ teachers +")";
					List<Map<String, Object>>  teacherMember =	baseDAO.getGenericBySQL(sql , null);
					classMember.addAll(teacherMember);
				}
			}
			//查询家长信息
			sql = "SELECT f.id memberId, f.`family_name` memberNickName, u.`IS_VIP` isVip FROM t_user u , t_kindergarten_student s, t_family_info f WHERE u.`USERTEL` = s.`parents_tel` AND u.`FAMILYID` = f.`id` AND s.`grade_id` = ?";
			List<Map<String, Object>> parentsFamilyList = baseDAO.getGenericBySQL(sql , new Object[]{grade.getId()});
			for(Map<String, Object> parentFamily : parentsFamilyList){
				String familyId = parentFamily.get("memberId")+"";
				List<TUser> parentList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
				String familyPhoto = "";
				for(TUser u : parentList){
					if(u.getPhoto() != null && !"".equals(u.getPhoto())){
						familyPhoto += imgUrl + u.getPhoto() +",";
					}
				}
				parentFamily.put("memberPhoto", familyPhoto.length() == 0 ? "" : familyPhoto.substring(0, familyPhoto.length()-1 ));

			}
			classMember.addAll(parentsFamilyList);
			result.put("classMember", classMember);
			//班级通知
			sql = "SELECT n.id noticeId, n.notice_content noticeContent,n.status noticeStatus, CASE WHEN t.`photo` IS NOT NULL AND t.`photo` !=''  THEN CONCAT( '"+imgUrl+"',t.`photo`) ELSE '' END reportUserPhoto, t.`name` reportUserNickName FROM t_kindergarten_notice n LEFT JOIN t_kindergarten_teacher t ON n.`report_user_id` = t.`id` WHERE grade_id = ?";
			List<Map<String, Object>> classNotice = baseDAO.getGenericBySQL(sql, new Object[]{grade.getId()});
			result.put("classNotice",classNotice);
		}

		//荣誉专区
		int ownerId = album.getGradeId();
		if(album.getType() == 2){
			ownerId = album.getStudent();
		}
		sql = "SELECT id honorId, CASE WHEN photo_url IS NOT NULL AND photo_url !='' THEN  CONCAT('',photo_url) ELSE '' END  honorUrl, create_time createTime,NAME honorName FROM t_kindergarten_honor WHERE owner_id = ? AND TYPE = ?";
		List<Map<String, Object>> honorZone = baseDAO.getGenericBySQL(sql, new Object[]{ownerId, type});
		result.put("honorZone",honorZone);

		result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

	private String checkParam(JSONObject param, String[] keys){

	    for(String key : keys){
	        if(param.get(key) == null || "".equals(param.get(key).toString())){
	            return key;
            }
        }
        return null;
    }

}

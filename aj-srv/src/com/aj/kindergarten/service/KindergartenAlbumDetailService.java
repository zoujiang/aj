/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

/***
 * 5.2.5	幼儿园相册明细查询
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenStudent;
import com.aj.kindergarten.vo.TKindergartenTeacher;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;

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
		int albumId = params.optInt("albumId"); //班级相册ID
		int type = params.optInt("type");
		int studentId = params.optInt("studentId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "albumId", "type", "studentId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		//班级返回6个照片或视频   个人返回9个照片或视频
		int classLimit = 6;
		TKindergartenAlbum album = baseDAO.get(TKindergartenAlbum.class, albumId);
		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, album.getGradeId());
		TKindergartenStudent student = baseDAO.get(TKindergartenStudent.class, studentId);
		
		Map<String, Object> gradeInfo = new HashMap<String, Object>();
		gradeInfo.put("gradeLogo", (grade.getLogo() == null || "".equals(grade.getLogo())) ? "" :imgUrl+ grade.getLogo());
		gradeInfo.put("declaration", grade.getDeclaration());
		gradeInfo.put("gradeName", grade.getSeries()+"级"+student.getName()+ album.getAlbumName());
		result.put("gradeInfo",gradeInfo);
		List<Map<String, Object>> photoList = new ArrayList<Map<String,Object>>();
		String sql = "SELECT p.id photoId,p.name name, p.category category, p.photo_url photoUrl, p.video_url videoUrl,  p.dig_num digNum, p.dig_relation_user_id, p.comment_num commentNum FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ?   ORDER BY p.create_time DESC limit ?";
		Integer aId = null;
		if(type == 1){
			//班级
			//照片/视频
			aId = albumId;
		}else{
			classLimit = 9;
			//查询该班级学生个人的相册ID
			String albumSql= "select id from t_kindergarten_album where shcool_id = ? and grade_id = ? and student = ? and current_grade_name = ? and type = 2";
			List<Map<String, Object>> generAlbumList = baseDAO.getGenericBySQL(albumSql, new Object[]{album.getShcoolId(), album.getGradeId(), studentId, album.getCurrentGradeName()});
			Integer generAlbumId = null;
			if(generAlbumList != null && generAlbumList.size() > 0){
				generAlbumId = Integer.parseInt(generAlbumList.get(0).get("id")+"") ;
				aId = generAlbumId;
			}
		}
		if(aId != null){
			
			photoList = baseDAO.getGenericBySQL(sql, new Object[]{aId, classLimit});
			if(photoList != null && photoList.size()>0){
				for(int j=0;j<photoList.size();j++){
					Map<String, Object> photo = photoList.get(j);
					if(photo.get("photoUrl") != null && !"".equals(photo.get("photoUrl"))){
						photo.put("photoUrl", imgUrl +photo.get("photoUrl") );
					}
					if(photo.get("videoUrl") != null && !"".equals(photo.get("videoUrl"))){
						photo.put("videoUrl", imgUrl +photo.get("videoUrl") );
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
		}
		result.put("photoList",photoList);

		//如果是班级，返回 留言板/班级成员/班级通知
		if(type == 1){
			//留言板
			List<Map<String, Object>> messageBoard = baseDAO.getGenericBySQL("SELECT message_id messageId, message_content messageContent, message_tye messageType, create_time createTime ," +
					"m.user_type userType,m.user_id userId,  CASE WHEN user_type != 8 THEN (SELECT CASE TYPE WHEN 1 THEN '园长' WHEN 2 THEN '副园长' ELSE NAME END NAME FROM t_kindergarten_teacher t WHERE t.id = m.user_id)  ELSE (SELECT f.family_name FROM t_user u, t_family_info f WHERE u.familyid = f.id AND u.id = m.user_id) END messageNickName " +
					"FROM t_kindergarten_message_board m WHERE album_id = ? order by create_time desc  limit 0, 4", new Object[]{albumId});
			for(Map<String, Object> mb : messageBoard){
				
				if("2".equals(mb.get("messageType")) || "3".equals(mb.get("messageType"))){
					mb.put("messageContent", imgUrl + mb.get("messageContent"));
				}
				
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

			//班级成员
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
		/*	sql = "SELECT f.id memberId, f.`family_name` memberNickName, u.`IS_VIP` isVip FROM t_user u , t_kindergarten_student s, t_family_info f WHERE u.`USERTEL` = s.`parents_tel` AND u.`FAMILYID` = f.`id` AND s.`grade_id` = ?";
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
			*/
			sql = "select s.id memberId, s.`name` memberNickName, s.photo, u.familyid, u.`IS_VIP` isVip from t_kindergarten_student s left join t_user u on s.parents_tel = u.USERTEL where s.grade_id = ?";
			List<Map<String, Object>> studentAndParentsFamilyList = baseDAO.getGenericBySQL(sql , new Object[]{grade.getId()});
			for(Map<String, Object> spf : studentAndParentsFamilyList){
				String familyPhoto = spf.get("familyid") == null ? "" : imgUrl + spf.get("photo") +"," ;
				String familyId = spf.get("familyid") ==null? null :spf.get("familyid").toString() ;
				if(familyId != null && !"".equals(familyId)){
					
					TFamilyInfo family = baseDAO.get(TFamilyInfo.class, familyId);
					spf.put("memberNickName", family.getFamilyName());
					List<TUser> parentList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
					for(TUser u : parentList){
						if(u.getPhoto() != null && !"".equals(u.getPhoto())){
							familyPhoto += imgUrl + u.getPhoto() +",";
						}
					}
				}
				spf.put("memberPhoto", familyPhoto);
			}
			
			classMember.addAll(studentAndParentsFamilyList);
			
			
			result.put("classMember", classMember);
			//班级通知
			sql = "SELECT n.id noticeId, n.notice_content noticeContent,n.status noticeStatus, CASE WHEN t.`photo` IS NOT NULL AND t.`photo` !=''  THEN CONCAT( '"+imgUrl+"',t.`photo`) ELSE '' END reportUserPhoto, t.`name` reportUserNickName FROM t_kindergarten_notice n LEFT JOIN t_kindergarten_teacher t ON n.`report_user_id` = t.`id` WHERE grade_id = ?";
			List<Map<String, Object>> classNotice = baseDAO.getGenericBySQL(sql, new Object[]{grade.getId()});
			result.put("classNotice",classNotice);
		}

		//荣誉专区
		sql = "SELECT id honorId, CASE WHEN ( photo_url IS NOT NULL AND photo_url !='' ) THEN  CONCAT('"+imgUrl+"',photo_url) ELSE '' END  honorUrl, create_time createTime,NAME honorName FROM t_kindergarten_honor WHERE album_id = ?";
		List<Map<String, Object>> honorZone = new ArrayList<Map<String, Object>>();
		if(aId != null){
			honorZone = baseDAO.getGenericBySQL(sql, new Object[]{aId});
		}
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

/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenStudent;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;

/**
 * 根据学生ID进入学生个人空间，用于老师上传照片视频
 * */
@Service("kindergartenStudentZone")
public class KindergartenStudentZoneService implements PublishService{

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
		String studentId = params.optString("studentId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(studentId == null || "".equals(studentId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "studentId为空！");
			return returnJSON.toString();
		}

		int classLimit = 9;
		
		//学生
		TKindergartenStudent student = baseDAO.get(TKindergartenStudent.class, Integer.parseInt(studentId));
		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, student.getGradeId());
		String currentClass = GradeNameUtil.getGradeName(grade);
		List<TKindergartenAlbum> albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ? and student =? and currentGradeName = ?", student.getKindergartenId(), student.getGradeId(), student.getId(), currentClass);
		List<Map<String, Object>> photoList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> honorZone = new ArrayList<Map<String, Object>>();
		if(albumList != null && albumList.size() > 0){
			TKindergartenAlbum album = albumList.get(0);
			result.put("albumId", album.getId());
			//照片/视频
			String sql = "SELECT p.id photoId,p.name name, p.category category, p.photo_url photoUrl, p.video_url videoUrl,  p.dig_num digNum, p.dig_relation_user_id, p.comment_num commentNum FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ? AND type = 2  ORDER BY p.create_time DESC limit ?";
			photoList = baseDAO.getGenericBySQL(sql, new Object[]{album.getId(), classLimit});
			if(photoList != null && photoList.size()>0){
				for(int j=0;j<photoList.size();j++){
					JSONObject photoDigComments = new JSONObject();
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
			
			//荣誉专区
			sql = "SELECT id honorId, CASE WHEN photo_url IS NOT NULL AND photo_url !='' THEN  CONCAT('"+imgUrl+"',photo_url) ELSE '' END  honorUrl, create_time createTime,NAME honorName FROM t_kindergarten_honor WHERE album_id = ?";
			honorZone = baseDAO.getGenericBySQL(sql, new Object[]{album.getId()});
			
		}	
		result.put("photoList",photoList);
		result.put("honorZone",honorZone);
		result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}

/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenInfo;
import com.aj.kindergarten.vo.TKindergartenTeacher;
import com.aj.kindergarten.vo.TRewardInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;


@Service("kindergartenTeaherTaskInfo")
public class KindergartenTeaherTaskInfoService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");

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
		TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
		String taskCondition = SystemConfig.getValue("aj.teacher.task.condition");
		if(taskCondition == null || "".equals(taskCondition) || taskCondition.split("\\|").length != 2){
			taskCondition = "8|3";
		}

		//查询用户对应的教师所教的班级
		List<TKindergartenTeacher> teacherList = baseDAO.getGenericByHql("from TKindergartenTeacher where userId = ?", Integer.parseInt(userId));
		if(teacherList == null || teacherList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "您不是老师，没有查看权限！");
			return returnJSON.toString();
		}
		
		//String photoSql = "select t.gradeId, t.category, t.total, g.rule, g.series, g.class_no classNo, g.kindergarten_id kindergartenId from t_kindergarten_grade g, (select grade_id gradeId,category, count(category) total from t_kindergarten_photo where create_user = ? and left(create_time, 7) = ? group by grade_id, category ) t where g.id = t.gradeId";
		String photoSql = "select g.id gradeId, t.category, t.total, g.rule, g.series, g.class_no classNo, g.kindergarten_id kindergartenId,g.create_time createTime from t_kindergarten_grade g left join (select grade_id gradeId,category, count(category) total from t_kindergarten_photo where create_user = ? and left(create_time, 7) = ? group by grade_id, category ) t on g.id = t.gradeId where g.first_teacher = "+teacherList.get(0).getId()+" or g.second_teacher = "+teacherList.get(0).getId()+" or g.nurse = "+teacherList.get(0).getId()+" ";
		//存放gradeId- kindergartenId对应关系
		Map<String, Integer> gradeKindergartenMap = new HashMap<String, Integer>();
		
		List<String> dateList = DateUtils.getNPreMonth(3);
		//某月完成N个班级
		List<Map<String, Object>> complateList = new ArrayList<Map<String, Object>>();
		
		Map<String, Integer> gradeMap = new HashMap<String, Integer>();
		//每个班级 每月完成多余的数量，可以补之前2个月的， key: 班级_图片或视频_遗留数量  grade_category_left; 由于只能是后面月份补前面月份的，所以下面的dateList必须是时间倒序排列
		Map<String, Integer> gradeComplateLeftMap = new HashMap<String, Integer>();
		Set<String> gradeNameSet = new HashSet<String>();
		for(String yearMonth : dateList){
			Map<String,Object> map = new HashMap<String, Object>();
			//List<Map<String, Object>> totalGradeList = baseDAO.getGenericBySQL(totalGradeSql, new Object[]{userId, yearMonth});
			String month = yearMonth.substring(5);
			//map.put("gradeNum", totalGradeList.get(0).get("totalGrade"));
			List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(photoSql, new Object[]{userId, yearMonth});
			
			int complateGradeNum = 0;
			List<String> complateGradeNameList = new ArrayList<String>();
			//某月某班级的总照片数 key:  grade + "_photo_total"
			Map<String, Integer> gradePhotoTotalMap = new HashMap<String, Integer>();
			//某月某班级的总视频数 key:  grade + "_video_total"
			Map<String, Integer> gradeVideoTotalMap = new HashMap<String, Integer>();
			for(Map<String, Object> m : photoList){
				// m 代表每个班级
				int totalPhoto = 0;
				int totalVideo = 0;
				String gradeId = m.get("gradeId") +"";
				gradeKindergartenMap.put(gradeId, Integer.parseInt(m.get("kindergartenId")+"") );
				
				//gradeName是 gradeId - name拼接
				String gradeName = gradeId+"-"+ getGradeName(m.get("rule").toString(), m.get("series").toString(), m.get("classNo")==null?"":m.get("classNo").toString(), m.get("createTime")+"");
				gradeNameSet.add(gradeName);
				if(m.get("category") != null){
					
					if("1".equals( m.get("category").toString()) ){
						//照片
						totalPhoto = Integer.parseInt(m.get("total") +"");
						gradePhotoTotalMap.put(gradeName + "_photo_total", (gradePhotoTotalMap.get(gradeName + "_photo_total") ==null? 0:gradePhotoTotalMap.get(gradeName + "_photo_total")) + totalPhoto);
						gradeMap.put(gradeName+"_photoNum", (gradeMap.get(gradeName+"_photoNum") == null? 0 : gradeMap.get(gradeName+"_photoNum")) + totalPhoto) ;
					}else if("2".equals( m.get("category").toString()) ){
						//视频
						totalVideo = Integer.parseInt(m.get("total") +"");
						gradeVideoTotalMap.put(gradeName + "_video_total", (gradeVideoTotalMap.get(gradeName + "_video_total") ==null? 0:gradeVideoTotalMap.get(gradeName + "_video_total"))+ totalVideo);
						gradeMap.put(gradeName+"_videoNum", (gradeMap.get(gradeName+"_videoNum") == null? 0 : gradeMap.get(gradeName+"_videoNum")) + totalVideo) ;
					}
				}
			}
			
			Iterator<String> iterator = gradeNameSet.iterator();
		    while(iterator.hasNext()){
		    	String gradeName = iterator.next();
		    	int totalPhoto = gradePhotoTotalMap.get(gradeName + "_photo_total") == null ? 0 : gradePhotoTotalMap.get(gradeName + "_photo_total");
		    	int totalVideo = gradeVideoTotalMap.get(gradeName + "_video_total") == null ? 0 : gradeVideoTotalMap.get(gradeName + "_video_total");
		    	
		    	totalPhoto += gradeComplateLeftMap.get(gradeName + "_1_left" ) == null ? 0 : gradeComplateLeftMap.get(gradeName + "_1_left" );
				totalVideo += gradeComplateLeftMap.get(gradeName + "_2_left" ) == null ? 0 : gradeComplateLeftMap.get(gradeName + "_2_left" );
					
				if(totalPhoto >= Integer.parseInt(taskCondition.split("\\|")[0]) && totalVideo >= Integer.parseInt(taskCondition.split("\\|")[1])){
					complateGradeNum ++;
					complateGradeNameList.add(gradeName);
					
					//去除达标数量， 把多余的累积起来(写在if里面，说明必须是当月达标了，才能把剩余的给前面的月份； 如果当月未达标，也可以把当月的给上月，需要把一下2行放到if外面， 并且不会减去达标数量)
					gradeComplateLeftMap.put(gradeName + "_1_left", totalPhoto - Integer.parseInt(taskCondition.split("\\|")[0]));
					gradeComplateLeftMap.put(gradeName + "_2_left", totalVideo - Integer.parseInt(taskCondition.split("\\|")[1]));
				}
		    	
		    }
		    Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("month", month);
			resultMap.put("complateGradeNum", complateGradeNum);
			int totalReward = 0;
			for(String complateGradeName : complateGradeNameList ){
				String gradeId = complateGradeName.substring(0, complateGradeName.indexOf("-")) ;
				TKindergartenInfo kindergarten = baseDAO.get(TKindergartenInfo.class,  gradeKindergartenMap.get(gradeId));
		    	List<TRewardInfo> rewardList = baseDAO.getGenericByHql("from TRewardInfo where userType =? and kindergartenCategory = ? ", user.getType(), kindergarten.getCategory());
		    	if(rewardList != null && !rewardList.isEmpty()){
		    		TRewardInfo reward = rewardList.get(0);
		    		totalReward += reward.getRewardInfo();
		    	}
			}
			resultMap.put("totalReward", totalReward);
			
			complateList.add(resultMap);
		}
		//处理每个班级近3个月的照片和视频上传情况
		
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Iterator<String> it = gradeNameSet.iterator();
	    while(it.hasNext()){
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String gradeName = it.next();
	    	String gradeId = gradeName.substring(0,gradeName.indexOf("-"));
	    	map.put("gradeId", gradeId);
	    	TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, Integer.parseInt(gradeId));
	    	map.put("gradeLogo", (grade.getLogo() == null || "".equals(grade.getLogo())) ? "" : SystemConfig.getValue("img.http.url") + grade.getLogo());
	    	map.put("gradeName", gradeName.substring(gradeName.indexOf("-")+1));
	    	map.put("declaration", grade.getDeclaration());
	    	map.put("photoNum", gradeMap.get(gradeName+"_photoNum") == null ? 0 : gradeMap.get(gradeName+"_photoNum"));
	    	map.put("videoNum", gradeMap.get(gradeName+"_videoNum")  == null ? 0 : gradeMap.get(gradeName+"_videoNum"));
	    	resultList.add(map);
	    }		
	
		result.put("succMsg", "查询成功");
		result.put("complateList", complateList);
		result.put("resultList", resultList);
		
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
	private String getGradeName(String rule, String series, String classNo, String createTime){
		
		String name = series +"级";
		TKindergartenGrade grade = new TKindergartenGrade();
		grade.setCreateTime(createTime);
		grade.setRule(rule);
		grade.setSeries(series);
		String className = GradeNameUtil.getGradeName(grade);
		
		return name + className + classNo;
	}

}

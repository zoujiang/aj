/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;


@Service("kindergartenTeaherTaskDetail")
public class KindergartenTeaherTaskDetailService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gradeIdStr = params.optString("gradeId");
		String type = params.optString("type");  //1班级 2个人

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		String taskCondition = SystemConfig.getValue("aj.teacher.task.condition");
		if(taskCondition == null || "".equals(taskCondition) || taskCondition.split("\\|").length != 2){
			taskCondition = "8|3";
		}
		
		int photoTaskCondition = Integer.parseInt(taskCondition.split("\\|")[0]);
		int videoTaskCondition = Integer.parseInt(taskCondition.split("\\|")[1]);

		//String totalGradeSql = "select count(DISTINCT grade_id ) totalGrade from t_kindergarten_photo where create_user = ? and left(create_time, 7) = ?";
		String photoSql = "select category, count(category) total from t_kindergarten_photo where  grade_id = ? and create_user = ? and left(create_time, 7) = ? group by grade_id, category ";
		
		List<String> dateList = DateUtils.getNPreMonth(3);
		
		//记录某月的照片或视频数量 key : month_photo  month_video
		Map<String, Integer> monthNum = new HashMap<String, Integer>();
		//记录最后结果集
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		//每个班级 每月完成多余的数量，可以补之前2个月的， key: 图片或视频_遗留数量  category_left; 由于只能是后面月份补前面月份的，所以下面的dateList必须是时间倒序排列
		Map<String, Integer> gradeComplateLeftMap = new HashMap<String, Integer>();
		gradeComplateLeftMap.put("photo_left", 0); 
		gradeComplateLeftMap.put("video_left", 0); 
		for(int i=0; i< dateList.size(); i ++){
			String yearMonth = dateList.get(i);
			String month = yearMonth.substring(5);
			List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(photoSql, new Object[]{gradeIdStr, userId, yearMonth});
			
			//每月的照片总量和视频总量
			int monthTotalPhoto = 0;
			int monthTotalVideo = 0;
			
			for(Map<String, Object> m : photoList){
				if("1".equals( m.get("category").toString()) ){
					//照片
					monthTotalPhoto += Integer.parseInt(m.get("total") +"");
				}else if("2".equals( m.get("category").toString()) ){
					//视频
					monthTotalVideo += Integer.parseInt(m.get("total") +"");
				}
			}
			monthNum.put(month+"_photo", monthTotalPhoto);
			monthNum.put(month+"_video", monthTotalVideo);
			
			Map<String, Object> preMonthData = new HashMap<String, Object>();
			preMonthData.put("month", month);
			preMonthData.put("photoNum", monthTotalPhoto);
			preMonthData.put("videoNum", monthTotalVideo);
			
			if(monthTotalPhoto  >= photoTaskCondition &&  monthTotalVideo >= videoTaskCondition){
				preMonthData.put("isComplate", 1);
				gradeComplateLeftMap.put("photo_left", gradeComplateLeftMap.get("photo_left") + (monthTotalPhoto - photoTaskCondition));
				gradeComplateLeftMap.put("video_left", gradeComplateLeftMap.get("video_left") + (monthTotalVideo - videoTaskCondition));
			}else if( ( gradeComplateLeftMap.get("photo_left") + monthTotalPhoto ) >= photoTaskCondition && ( gradeComplateLeftMap.get("video_left") + monthTotalVideo) >= videoTaskCondition){
				//完成
				preMonthData.put("isComplate", 1);
				gradeComplateLeftMap.put("photo_left", gradeComplateLeftMap.get("photo_left") + (monthTotalPhoto - photoTaskCondition));
				gradeComplateLeftMap.put("video_left", gradeComplateLeftMap.get("video_left") + (monthTotalVideo - videoTaskCondition));
				//被某月补充而达到任务目标
				preMonthData.put("replenishMonth", dateList.get(i -1).substring(5));
				if( photoTaskCondition - monthTotalPhoto > 0){
					preMonthData.put("replenishPhotoNum", (photoTaskCondition - monthTotalPhoto));
				}
				if(videoTaskCondition - monthTotalVideo > 0){
					preMonthData.put("replenishVideoNum", (videoTaskCondition - monthTotalVideo));
				}
			}else{
				preMonthData.put("isComplate", 0);
				if(( gradeComplateLeftMap.get("photo_left") + monthTotalPhoto ) < photoTaskCondition){
					preMonthData.put("needPhoto", (photoTaskCondition - monthTotalPhoto));
				}else{
					gradeComplateLeftMap.put("photo_left", gradeComplateLeftMap.get("photo_left") + (monthTotalPhoto - photoTaskCondition));
				}
				if(( gradeComplateLeftMap.get("video_left") + monthTotalVideo) < videoTaskCondition){
					preMonthData.put("needVideo", (videoTaskCondition - monthTotalVideo));
				}else{
					gradeComplateLeftMap.put("video_left", gradeComplateLeftMap.get("video_left") + (monthTotalVideo - videoTaskCondition));
				}
				
			}
			
			data.add(preMonthData);
			
		}
		//根据班级ID查询相册ID，一个班每年都会生成一个单独的相册，比如小班，中班，大班
		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, Integer.parseInt(gradeIdStr));
		String currentClass = GradeNameUtil.getGradeName(grade);
		List<TKindergartenAlbum> albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ?  and currentGradeName = ? and type =1 ", grade.getKindergartenId(), grade.getId(),  currentClass);
		if(albumList != null && albumList.size() > 0){
			result.put("albumId", albumList.get(0).getId());
		}
		result.put("succMsg", "查询成功");
		result.put("data", data);
		
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}

/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;


@Service("kindergartenManagerTaskInfo")
public class KindergartenManagerTaskInfoService implements PublishService{

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

		int taskNum = SystemConfig.getValue("aj.manager.task.condition") == null? 3 : Integer.parseInt(SystemConfig.getValue("aj.manager.task.condition"));
		
		String totalGradeSql = "select count(DISTINCT grade_id ) totalGrade from t_kindergarten_photo_view_history where user_id = ? and create_time >= ? and create_time<= ?";
		
		List<String> dateList = DateUtils.getNPreMonth(3);
		//某月完成N个班级
		List<Map<String, Object>> complateList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
		for(String yearMonth : dateList){
			Long beginTime =null;
			try {
				beginTime = format0.parse(yearMonth+"-01").getTime() /1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Map<String,Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> totalGradeList = baseDAO.getGenericBySQL(totalGradeSql, new Object[]{userId,beginTime , DateUtils.getLastDayOfMonth(yearMonth)});
			String month = yearMonth.substring(5);
			int gradeNum = totalGradeList.get(0).get("totalGrade") == null ? 0 : Integer.parseInt(totalGradeList.get(0).get("totalGrade")+"");
			map.put("gradeNum", gradeNum);
			map.put("month", month);
			if(gradeNum >= taskNum){
				map.put("isComplate", 0);
			}else{
				map.put("isComplate", 1);
				map.put("needMoreVisit", taskNum - gradeNum);
				
			}
			complateList.add(map);
			
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String currentMonth = format.format(new Date());
		Long currentMonthTime =null;
		try {
			currentMonthTime = format0.parse(currentMonth+"-01").getTime() /1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//查询当月所有班级及查看情况
		String sql = "select g.id, g.rule, g.series, g.class_no, g.logo, g.declaration, g.create_time createTime, (select count(h.id) from t_kindergarten_photo_view_history h where h.grade_id = g.id and h.user_id = ? and h.create_time >= ? and h.create_time <= ?)  visitNum from t_kindergarten_grade g  where g.kindergarten_id = (select  kindergarten_id from t_kindergarten_teacher where user_id = ? )";		
	    List<Map<String, Object>> gradeVisitList = baseDAO.getGenericBySQL(sql, new Object[]{userId, currentMonthTime, DateUtils.getLastDayOfMonth(currentMonth), user.getId()});
		List<Map<String, Object>> visitList = new ArrayList<Map<String, Object>>();
	    for(Map<String, Object> map : gradeVisitList){
			String className = getGradeName(map.get("rule")+"", map.get("series")+"", map.get("class_no")+"", map.get("createTime")+"");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("gradeId", map.get("id"));
			temp.put("gradeLogo", (map.get("logo") == null || "".equals(map.get("logo"))) ? "" : imgUrl+ map.get("logo") );
			temp.put("gradeName", className);
			temp.put("declaration", map.get("declaration"));
			if(map.get("visitNum") == null || Integer.parseInt(map.get("visitNum").toString()) <= 0){
				temp.put("isVisit", 1);
			}else{
				temp.put("isVisit", 0);
			}
			visitList.add(temp);
		}
	    
	    result.put("succMsg", "查询成功");
		result.put("complateList", complateList);
		result.put("visitList", visitList);
		
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

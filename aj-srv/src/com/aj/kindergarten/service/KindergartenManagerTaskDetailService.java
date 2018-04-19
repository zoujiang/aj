/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


@Service("kindergartenManagerTaskDetail")
public class KindergartenManagerTaskDetailService implements PublishService{

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

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
		String currentMonth = format.format(new Date());
		Long currentMonthTime =null;
		try {
			currentMonthTime = format0.parse(currentMonth+"-01").getTime() /1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String sql = "select category, count(category) num from t_kindergarten_photo_view_history where user_id = ? and grade_id = ? and create_time >= ? and create_time <= ? group by category";
		List<Map<String, Object>> data = baseDAO.getGenericBySQL(sql, new Object[]{userId, gradeIdStr, currentMonthTime, DateUtils.getLastDayOfMonth(currentMonth)});
		result.put("succMsg", "查询成功");
		result.put("data", data);
		//根据班级ID查询相册ID，一个班每年都会生成一个单独的相册，比如小班，中班，大班
		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, Integer.parseInt(gradeIdStr));
		String currentClass =GradeNameUtil.getGradeName(grade);
		List<TKindergartenAlbum> albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ?  and currentGradeName = ?  and type =1 ", grade.getKindergartenId(), grade.getId(),  currentClass);
		if(albumList != null && albumList.size() > 0){
			result.put("albumId", albumList.get(0).getId());
		}
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}

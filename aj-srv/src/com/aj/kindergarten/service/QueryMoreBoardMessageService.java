/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenTeacher;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;

@Service("queryMoreBoardMessage")
public class QueryMoreBoardMessageService implements PublishService{


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
		int pageSize = params.optInt("pageSize");
        int currentPage = params.optInt("currentPage");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"albumId","pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		
		//留言板
		List<Map<String, Object>> messageBoard = baseDAO.getGenericBySQL("SELECT message_id messageId, message_content messageContent, message_tye messageType, create_time createTime ," +
				"m.user_type userType,m.user_id userId,  CASE WHEN user_type != 8 THEN (SELECT CASE TYPE WHEN 1 THEN '园长' WHEN 2 THEN '副园长' ELSE NAME END NAME FROM t_kindergarten_teacher t WHERE t.user_id = m.user_id)  ELSE (SELECT f.family_name FROM t_user u, t_family_info f WHERE u.familyid = f.id AND u.id = m.user_id) END messageNickName " +
				"FROM t_kindergarten_message_board m WHERE album_id = ? order by create_time desc limit ?, ?", new Object[]{albumId, pageSize * currentPage , pageSize});
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

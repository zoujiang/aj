package com.aj.kindergarten.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *幼儿园照片评价列表查询
 * */

@Service("kindergartenPhotoCommentList")
public class KindergartenPhotoCommentListService implements PublishService{

	private Logger log = Logger.getLogger(KindergartenPhotoCommentListService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String photoId = params.optString("photoId");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(photoId == null || "".equals(photoId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "photoId为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		
		String imgPrefix = SystemConfig.getValue("img.http.url");
		
		JSONArray cmtList = new JSONArray();
		try {
			String sql = "SELECT "+
						 " p.ID,"+
						 " p.CONTENT,"+
						 " p.CREATE_DATE,"+
						 " p.IS_REPLY,"+
						 " p.REPLY_USER_ID,"+
						 " p.CREATE_USER_ID,"+
						 " u.photo CREATE_USER_PHOTO, "+
						 " (SELECT  case when r.remark is not null then r.remark else u.NICK_NAME end  FROM t_user u left join t_remark_info r on u.id = r.relation_id and r.type = 1 and r.create_user_id = ? WHERE u.id = p.CREATE_USER_ID) commentUserNickName,"+
						 " (SELECT case when r.remark is not null then r.remark else u.NICK_NAME end  FROM t_user u left join t_remark_info r on u.id = r.relation_id and r.type=1 and r.create_user_id = ? WHERE u.id = p.REPLY_USER_ID) replyUserNickName "+
						" FROM "+
						"  t_kindergarten_photo_comment p left join t_user u on p.CREATE_USER_ID = u.id  "+
						" WHERE p.photo_id = ? "+
						" ORDER BY p.CREATE_DATE ";
			List<Map<String, Object>> commentList = baseDAO.getGenericByPositionToSQL(sql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)), Integer.parseInt(pageSize), new Object[]{userId, userId, photoId});
			for(int i=0;i<commentList.size();i++){
				JSONObject jo = new JSONObject();
				jo.put("commonId", commentList.get(i).get("ID"));
				jo.put("cmtContent", commentList.get(i).get("CONTENT"));
				jo.put("isReply", commentList.get(i).get("IS_REPLY"));
				jo.put("replyUserId", commentList.get(i).get("REPLY_USER_ID"));
				jo.put("replyUserNickName", commentList.get(i).get("replyUserNickName"));
				jo.put("commentUserId", commentList.get(i).get("CREATE_USER_ID"));
				jo.put("commentUserNickName", commentList.get(i).get("commentUserNickName"));
				jo.put("cmtDate", commentList.get(i).get("CREATE_DATE"));
				jo.put("cmtUserPhoto", (commentList.get(i).get("CREATE_USER_PHOTO") == null || "".equals(commentList.get(i).get("CREATE_USER_PHOTO").toString().trim())) ? "" : imgPrefix + commentList.get(i).get("CREATE_USER_PHOTO"));
				cmtList.add(jo);
			}
			} catch (Exception e) {
				
			}
		result.put("commentInfo", cmtList);
		
		result.put("successMsg", "查询成功");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}

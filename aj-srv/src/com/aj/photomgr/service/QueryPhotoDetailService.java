/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.photomgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

/**
 * 查询照片详情
 * */
@Service("albumPhotoDetailQuery")
public class QueryPhotoDetailService implements PublishService{

	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj){
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String photoId = params.optString("photoId");
		String albumType = params.optString("albumType");
		
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
		if(albumType == null || "".equals(albumType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumType为空！");
			return returnJSON.toString();
		}
		
		try {
			String imgUrl= SystemConfig.getValue("img.http.url");
			String resUrl= SystemConfig.getValue("res.http.url");
			String sql = "";
			if(!"7".equals(albumType)){
				sql = "SELECT p.id photoId,case when p.PHOTO_URL is not null then  CONCAT('"+imgUrl+"',p.PHOTO_URL) else '' end photoUrl, case when p.VIDEO_URL is not null then  CONCAT('"+resUrl+"',p.VIDEO_URL) else '' end videoUrl, p.photo_type photoType, p.CREATE_DATE createDate, p.dig_num digNum, p.dig_relation_user_id digUserList, descript description FROM t_photo p   WHERE p.ID = ?";
			}else{
				sql = "SELECT p.id photoId,case when p.PHOTO_URL is not null then  CONCAT('"+imgUrl+"',p.PHOTO_URL) else '' end photoUrl, '' videoUrl, '1' photoType, p.create_time createDate, p.dig_num digNum, p.dig_relation_user_id digUserList, '' description FROM t_shop_photo p   WHERE p.ID = ?";
			}
			List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{photoId});
			if(photoList == null || photoList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "照片不存在！");
				return returnJSON.toString();
			}else{
				result.put("photoInfo", photoList.get(0));
				String digUserIds = photoList.get(0).get("digUserList") == null ? "": photoList.get(0).get("digUserList").toString();
				if(!"".equals(digUserIds)){
					String[] digUsers = digUserIds.split(",");
					String hadDig = "0";
					for(String uId : digUsers){
						if(uId.equals(userId)){
							hadDig = "1";
							break;
						}
					}
					result.put("hadDig", hadDig);
				}else{
					result.put("hadDig", "0");
				}
			}
			//查询评论
			sql = "SELECT "+
					"  c.ID commentId,"+
					"  c.CREATE_USER_ID cmtUserId,"+
					" case when ri.remark is not null then ri.remark else u.NICK_NAME end cmtUserNickName,"+
					"  c.CONTENT cmtContent,"+
					"  c.IS_REPLY isReply,"+
					"  c.REPLY_USER_ID replyUserId,"+
					"  case when r.remark is not null then r.remark else ru.NICK_NAME end replyUserNickName,"+
					"  c.REPLY_COMMENT_ID replayCommentId "+
				"	FROM "+
					"  t_comment c "+
					"  LEFT JOIN t_user ru  ON ru.id = c.REPLY_USER_ID "+
					"  LEFT JOIN t_remark_info r on c.REPLY_USER_ID = r.relation_id and r.type = 1 and r.create_user_id = ? ,"+
					"  t_user u LEFT JOIN t_remark_info ri on u.id = ri.relation_id and ri.type = 1 and ri.create_user_id = ?  "+
				"	WHERE c.CREATE_USER_ID = u.id "+
					"  AND c.PHOTO_ID = ? "+
				"	ORDER BY c.CREATE_DATE ";
			 List<Map<String, Object>> commentList =baseDAO.getGenericBySQL(sql, new Object[]{userId, userId, photoId});
			 result.put("cmtList", commentList);
			
			result.put("succMsg", "查询成功");
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("errorMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
		}
		return returnJSON.toString();
	}

}

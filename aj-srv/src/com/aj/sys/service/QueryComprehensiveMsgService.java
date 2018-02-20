package com.aj.sys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.sys.vo.TSysMsgAlert;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

/**
 * 我家首页  综合消息查询
 * */

@Service("queryComprehensive")
public class QueryComprehensiveMsgService implements PublishService{

	private Logger log = Logger.getLogger(QueryComprehensiveMsgService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		try {
			Map<String, Object>	resultMap = new HashMap<String, Object>();
			String hsql = " from TSysMsgAlert  where reciveUserId = ? and HAD_READ != 2 order by createDate desc";
			List<TSysMsgAlert> msgList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
			//系统消息
			if(msgList != null && msgList.size() > 3){
				resultMap.put("systemMsg", msgList.subList(0, 2));
			}else if(msgList != null && msgList.size() > 0){
				resultMap.put("systemMsg", msgList.subList(0, msgList.size()));
			}else{
				resultMap.put("systemMsg", null);
			}
			//相册更新提醒
			
			String sql = "SELECT "+
						 "  photoNum, case when r.remark is not null then r.remark else f.family_name end family_name , temp.maxUpdateDate, f.id , temp.easemobGroupId, temp.homeId, temp.inviteArea, temp.relationUser "+
						" FROM "+
						 " (SELECT  "+
						  "  p.*, "+
						  "  a.FAMILY_ID, "+
						  "  h.easemobGroupId, "+
						  "  h.id homeId, "+
						  "  h.invite_area inviteArea, "+
						  "  h.relation_user_id relationUser, "+
						   " COUNT(DISTINCT p.ID) photoNum ,"+
						   " MAX(p.CREATE_DATE) maxUpdateDate "+
						 " FROM "+
						  "  t_photo p  LEFT JOIN t_album_access_info aa ON aa.album_id = p.ALBUM_ID and aa.access_user_id = ?,"+
						   " t_album a,"+
						   " t_home_info h,"+
						   " t_user u  "+
						 " WHERE h.is_valid = 0 and p.ALBUM_ID = a.ID " +
						   " AND CASE WHEN aa.access_time IS NOT NULL THEN aa.access_time < p.CREATE_DATE ELSE 1=1 END "+
						   " AND a.ALBUM_TYPE = 4 "+
						   " AND u.ID = ? "+
						   " AND h.relation_user_id = "+
						   " CASE "+
						    "  WHEN h.invite_area = '1' "+
						    "  THEN a.FAMILY_ID "+
						    "  ELSE p.CREATE_USER_Id "+
						   " END "+
						   " AND h.create_user_id = "+
						   " CASE "+
						   "   WHEN h.is_private = '0' "+
						   "   THEN u.FAMILYID "+
						   "   ELSE u.id "+
						   " END  "
						   + " GROUP BY  a.FAMILY_ID ) temp , t_family_info f left join t_remark_info r on f.id = r.relation_id and r.create_user_id = ?  WHERE temp.FAMILY_ID = f.id and f.id != ?"+
						" GROUP BY temp.FAMILY_ID "+
					" ORDER BY temp.maxUpdateDate DESC ";
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
			List<Map<String, Object>> photoList =  baseDAO.getGenericBySQL(sql, new Object[]{userId, userId, userId, user.getFamilyId()});
			String prefix = SystemConfig.getValue("img.http.url");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			List<Map<String, Object>> subList = new ArrayList<Map<String,Object>>();
			if(photoList != null && photoList.size() > 5){
				subList = photoList.subList(0, 4);
				/*for(Map<String, Object> map : subList){
					
					   String maxUpdateDate = map.get("maxUpdateDate").toString();
					   if( (format.parse(format.format(new Date())).getTime() - format.parse(maxUpdateDate).getTime()) > (2* 24*60*60*1000)){
						   //大于2天， 显示全长日期
						   map.put("maxUpdateDate", maxUpdateDate);
					   }else if((format.parse(format.format(new Date())).getTime() - format.parse(maxUpdateDate).getTime()) > (2* 24*60*60*1000)){
						   //大于1天  ， 显示昨天
						   map.put("maxUpdateDate", "昨天");
					   }else{
						   map.put("maxUpdateDate", maxUpdateDate.substring(maxUpdateDate.indexOf(" ")+1, maxUpdateDate.length()));
					   }
					
					 //  String queryHomePhotos ="SELECT u.PHOTO FROM t_user u WHERE u.FAMILYID = ?";
					   //  List<Map<String, Object>> homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, new Object[]{map.get("id")});
					   
					   String queryHomePhotos = "SELECT CASE WHEN u.PHOTO IS NULL OR u.PHOTO = '' THEN '' ELSE  CONCAT('"+prefix+"',u.PHOTO) END PHOTO FROM t_user u WHERE u.FAMILYID = ? "+
								"	UNION  ALL "+
								"	SELECT CASE WHEN c.photo_url IS NULL OR c.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',c.photo_url) END PHOTO FROM t_children_info c WHERE c.family_id = ? "+
								"	UNION  ALL "+
								"	SELECT CASE WHEN b.photo_url IS NULL OR b.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',b.photo_url) END PHOTO FROM t_baby_info b WHERE b.family_id = ? ";
					   List<Map<String, Object>> homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, new Object[]{map.get("id"), map.get("id"),map.get("id")});
					   
					   String photos = "";
					   if(homePhotoList != null && homePhotoList.size() > 0){
						   
						   for(int i=0;i<homePhotoList.size();i++){
							   if(homePhotoList.get(i) != null && !"".equals(homePhotoList.get(i))){
								   
								   photos += prefix + homePhotoList.get(i).get("PHOTO")+";";
							   }
						   }
					   }
					   if(photos.length() > 0){
						   map.put("homePhoto", photos.substring(0, photos.length()-1));
					   }else{
						   map.put("homePhoto", photos);
					   }
				}
				
				
				resultMap.put("photo", subList);*/
			}else if(photoList != null && photoList.size() > 0){
				subList = photoList;
			}
			if(subList.size() > 0){
				for(Map<String, Object> map : photoList){
					
					   String maxUpdateDate = map.get("maxUpdateDate").toString();
					   if( (format.parse(format.format(new Date())).getTime() - format.parse(maxUpdateDate).getTime()) > (2* 24*60*60*1000)){
						   //大于2天， 显示全长日期
						   map.put("maxUpdateDate", maxUpdateDate);
					   }else if((format.parse(format.format(new Date())).getTime() - format.parse(maxUpdateDate).getTime()) > (2* 24*60*60*1000)){
						   //大于1天  ， 显示昨天
						   map.put("maxUpdateDate", "昨天");
					   }else{
						   map.put("maxUpdateDate", maxUpdateDate.substring(maxUpdateDate.indexOf(" "), maxUpdateDate.length()));
					   }
					
					  // String queryHomePhotos ="SELECT u.PHOTO FROM t_user u WHERE u.FAMILYID = ?";
					  // List<Map<String, Object>> homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, new Object[]{map.get("id")});
					   List<Map<String, Object>> homePhotoList = new ArrayList<Map<String,Object>>();
					   if("1".equals(map.get("inviteArea").toString())){
						   
						   String queryHomePhotos = "SELECT CASE WHEN u.PHOTO IS NULL OR u.PHOTO = '' THEN '' ELSE  CONCAT('"+prefix+"',u.PHOTO) END PHOTO FROM t_user u WHERE u.FAMILYID = ? "+
								   "	UNION  ALL "+
								//   "	SELECT CASE WHEN c.photo_url IS NULL OR c.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',c.photo_url) END PHOTO FROM t_children_info c WHERE c.family_id = ? "+
								   "	SELECT CASE WHEN c.photo_url IS NULL OR c.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',c.photo_url) END PHOTO FROM t_children_info c WHERE c.family_id like  CONCAT('%',?,'%') "+
								   "	UNION  ALL "+
								 //  "	SELECT CASE WHEN b.photo_url IS NULL OR b.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',b.photo_url) END PHOTO FROM t_baby_info b WHERE b.family_id = ? ";
								 	"	SELECT CASE WHEN b.photo_url IS NULL OR b.photo_url = '' THEN '' ELSE  CONCAT('"+prefix+"',b.photo_url) END PHOTO FROM t_baby_info b WHERE b.family_id like  CONCAT('%',?,'%') ";
						  homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, new Object[]{map.get("id"), map.get("id"),map.get("id")});
					   }else{
						   String queryHomePhotos = "SELECT CASE WHEN u.PHOTO IS NULL OR u.PHOTO = '' THEN '' ELSE  CONCAT('"+prefix+"',u.PHOTO) END PHOTO FROM t_user u WHERE u.id = ? ";
						  homePhotoList = baseDAO.getGenericBySQL(queryHomePhotos, new Object[]{map.get("relationUser")});
					   }
					   
					   String photos = "";
					   if(homePhotoList != null && homePhotoList.size() > 0){
						   
						   for(int i=0;i<homePhotoList.size();i++){
							   if(homePhotoList.get(i) != null && !"".equals(homePhotoList.get(i))){
								   
								   photos += homePhotoList.get(i).get("PHOTO")+";";
							   }
						   }
					   }
					   if(photos.length() > 0){
						   map.put("homePhoto", photos.substring(0, photos.length()-1));
					   }else{
						   map.put("homePhoto", photos);
					   }
				}
				
				resultMap.put("photo", photoList);
			}else{
				resultMap.put("photo", photoList);
			}
			
			// 任务 今明两天快到的任务列表（后台查出），优先级第二，全部显示
			/*String taskSql = "SELECT "+
								 " t.id,"+
								 " t.content,"+
								 " t.audio_url audioUrl,"+
								 " t.img_url imgUrl,"+
								 " t.user_id userId,"+
								 " tu.nick_name nickName,"+
								 " t.send_time sendTime,"+
								 " t.create_date createDate,"+
								 " t.type,"+
								 " t.status "+
								 " FROM "+
								 " t_task t,"+
								 " t_user tu,"+
								 " t_task_accept ta "+
								 " WHERE t.id = ta.task_id "+
								 " AND t.user_id = tu.id"+ 
								 " AND t.STATUS = 1 "+
								 " AND  (DATEDIFF(t.send_time, NOW()) = 0 OR DATEDIFF(t.send_time, NOW()) = 1 ) "+
								 " AND ta.user_id = ? ";
			
			List<Map<String, Object>> taskList =  baseDAO.getGenericBySQL(taskSql, new Object[]{userId});
			if(taskList != null && taskList.size() > 0){
				resultMap.put("task", taskList);
			}else{
				resultMap.put("task", null);
			}*/
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			
			
			result.put("succMsg", "操作成功");
			result.put("firstPageMsg", resultMap);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			
		} catch (Exception e) {
			
			log.info("查询系统消息失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}

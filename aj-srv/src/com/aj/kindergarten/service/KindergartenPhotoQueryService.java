/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("kindergartenPhotoQuery")
public class KindergartenPhotoQueryService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
        int albumId = params.optInt("albumId");
        int pageSize = params.optInt("pageSize");
        int currentPage = params.optInt("currentPage");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId","albumId", "pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}

	//	TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));

		int begin = currentPage * pageSize;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT t.sharedate FROM (SELECT SUBSTRING(p.CREATE_TIME,1,10) sharedate FROM t_kindergarten_photo p WHERE album_id = ?) t GROUP BY t.sharedate ORDER BY t.sharedate DESC LIMIT ?,?";
		JSONArray data = new JSONArray();
		List<Map<String, Object>> dateList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, begin, pageSize});
		if(dateList != null && dateList.size() >0){
				//记录该相册访问记录
				sql = "select id from t_album_access_info where album_id = ? and access_user_id = ?";
				List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, userId});
				if (accessList == null || accessList.size() == 0) {
					//新增
					sql = "insert into t_album_access_info (id, album_id, access_time, access_user_id) values (?,?,?,?)";
					baseDAO.execteNativeBulk(sql, UUIDUtil.uuid(), albumId, sdf.format(new Date()), userId);
				} else {
					sql = "update t_album_access_info set access_time = ? where id = ? ";
					baseDAO.execteNativeBulk(sql, sdf.format(new Date()), accessList.get(0).get("id").toString());
				}

			String imgUrl= SystemConfig.getValue("img.http.url");

			for(int i=0;i<dateList.size();i++){
				JSONObject album = new JSONObject();
				String sharedate = dateList.get(i).get("sharedate").toString();
				album.put("photoDate", sharedate);
				sql = "SELECT p.id,'' descript, p.photo_url,  p.dig_num, p.dig_relation_user_id FROM t_kindergarten_photo p WHERE  p.ALBUM_ID = ? AND SUBSTRING(p.create_time, 1,10) = ? ORDER BY p.create_time DESC";
				List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, sharedate});
				JSONArray photoPreDayList = new JSONArray();
				if(photoList != null && photoList.size()>0){
					for(int j=0;j<photoList.size();j++){
						JSONObject photoDigComments = new JSONObject();
						Map<String, Object> photo = photoList.get(j);
						photoDigComments.put("photoId", photo.get("id"));
						photoDigComments.put("descript", "");
						photoDigComments.put("photo", imgUrl+photo.get("photo_url"));
						photoDigComments.put("video", "");
						photoDigComments.put("photoType", "1");
						photoDigComments.put("dig_num", photo.get("dig_num"));
						String digUsers = photo.get("dig_relation_user_id") == null ? "" : photo.get("dig_relation_user_id").toString();
						boolean isDig = false;
						//查询点赞人员信息
						if( !"".equals(digUsers)){
							String[] digUserArray = digUsers.split(",");
							//我是否点赞
							for(String dUser : digUserArray){
								if(userId.equals(dUser)){
									isDig = true;
								}
							}
							//查询点赞人员列表，最多显示最近8个
							List<Map<String, Object>> digUserList = new ArrayList<Map<String,Object>>();
							sql = "SELECT u.id dig_user_id, u.NICK_NAME dig_user_nick,CONCAT('"+imgUrl+"',u.photo)  dig_user_photo FROM t_user u WHERE  u.id IN ('"+digUsers+"')";
							List<Map<String, Object>> digAllUserList = baseDAO.getGenericBySQL(sql, null);
							if(digAllUserList != null && digAllUserList.size()>8){
								digUserList = digAllUserList.subList(0, 8);
							}else{
								digUserList = digAllUserList;
							}
							photoDigComments.put("digList", digUserList);
						}
						if(isDig){
							photoDigComments.put("hadDig", 1);
						}else{
							photoDigComments.put("hadDig", 0);
						}
						//查询评论
						sql = "SELECT "+
								"  c.ID commentId,"+
								"  c.CREATE_USER_ID cmtUserId,"+
								"  u.NICK_NAME cmtUserNickName,"+
								"  c.CONTENT cmtContent,"+
								"  c.IS_REPLY isReply,"+
								"  c.REPLY_USER_ID replyUserId,"+
								"  ru.NICK_NAME replyUserNickName,"+
								"  c.REPLY_COMMENT_ID replayCommentId "+
								"	FROM "+
								"  t_comment c "+
								"  LEFT JOIN t_user ru "+
								"    ON ru.id = c.REPLY_USER_ID,"+
								"  t_user u "+
								"	WHERE c.CREATE_USER_ID = u.id "+
								"  AND c.PHOTO_ID = ? "+
								"	ORDER BY c.CREATE_DATE "+
								"	LIMIT 0, 3 ";
						List<Map<String, Object>> commentList =baseDAO.getGenericBySQL(sql, new Object[]{photo.get("id")});
						photoDigComments.put("cmtList", commentList);
						photoPreDayList.add(photoDigComments);
					}
					album.put("photoList", photoPreDayList);
				}
				data.add(album);
			}

		}
		result.put("succMsg", "查询成功");
		result.put("data", data);
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

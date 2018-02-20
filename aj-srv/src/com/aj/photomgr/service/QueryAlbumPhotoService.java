package com.aj.photomgr.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.albummgr.vo.TAlbum;
import com.aj.photomgr.vo.TPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查询相册照片
 * */

//@Service("queryAlbumPhotoService")
@Service("albumPhotoQuery")
public class QueryAlbumPhotoService implements PublishService{

	private Logger log = Logger.getLogger(QueryAlbumPhotoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		String currentPage = params.optString("currentPage");
		String pageSize = params.optString("pageSize");
			
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(albumId == null || "".equals(albumId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		
		JSONArray list = new JSONArray();
		JSONArray albumListArray = new JSONArray();
		int total = 0;
		int isDir = 0;
		try {
			
			String albumHsql = "from TAlbum where id = ?";
			List<TAlbum> qalbumList = baseDAO.getGenericByHql(albumHsql, albumId);
			if(qalbumList == null || qalbumList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "该相册不存在");
				return returnJSON.toString();
			}
			isDir = qalbumList.get(0).getIsDir() == null ? 0 : qalbumList.get(0).getIsDir(); /*是否文件夹：0：否 1：是，对应返回照片或者子相册目录*/
			
			if(isDir == 0){
				
				String imgUrl= SystemConfig.getValue("img.http.url");
				String resUrl= SystemConfig.getValue("res.http.url");
				//String hsql = "from TPhoto where createUserId = ? and albumId = ?";
				String hsql = "from TPhoto where  albumId = ?";
				List<TPhoto> totalList = baseDAO.getGenericByHql(hsql,  albumId);
				total = totalList ==null ? 0 : totalList.size();
				int begin = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
				int end = (Integer.parseInt(currentPage)+1) * Integer.parseInt(pageSize);
				String sql = "SELECT DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') create_date FROM (SELECT * FROM t_photo WHERE  ALBUM_ID = ? ORDER BY create_date DESC LIMIT ?,? ) p " +
						" GROUP BY DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') ORDER BY create_date DESC";
				//	String sql = "SELECT DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') create_date FROM (SELECT * FROM t_photo WHERE CREATE_USER_ID =? AND ALBUM_ID = ? ORDER BY create_date DESC LIMIT ?,? ) p " +
				//			" GROUP BY DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') ORDER BY create_date DESC";
				List<Map<String, Object>> dayList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, begin, end });
				//查询每天下面的照片信息
				if(dayList != null && dayList.size()>0){
					for(int i=0;i<dayList.size();i++){
						
						String date = dayList.get(i).get("create_date").toString();
						//sql = "SELECT p.id photoId,CONCAT('"+imgUrl+"',p.PHOTO_URL)  photo, p.ISPRIVATE isPrivate  FROM t_photo p WHERE p.CREATE_USER_ID =? AND p.ALBUM_ID = ?  AND  DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') = ? ORDER BY p.CREATE_DATE";
						sql = "SELECT p.id photoId,CONCAT('"+imgUrl+"',p.PHOTO_URL)  photo, CONCAT('"+resUrl+"',p.ViDEO_URL) videoUrl,photo_type photoType, descript description  FROM t_photo p WHERE  p.ALBUM_ID = ?  AND  DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') = ? ORDER BY p.CREATE_DATE DESC";
						List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, date});
						
						JSONObject jo = new JSONObject();
						jo.put("date", date.replaceFirst("-", "年").replace("年0", "年").replace("-", "月").replace("月0", "月")+"日");
						jo.put("photoPreDayList", photoList);
						list.add(jo);
					}
				}
			}else if(isDir == 1){
				String sql = "select family_name from t_album where create_user_id = ? and parent_id = ? and is_sysinit = 0 group by family_name order by create_date";
				List<Map<String, Object>> familyNameList = baseDAO.getGenericBySQL(sql, new Object[]{userId, albumId});
				if(familyNameList != null && familyNameList.size() >0){
					String hsql ;
					for(int i=0;i<familyNameList.size();i++){
						JSONObject data = new JSONObject();
						String familyName = familyNameList.get(i).get("family_name") == null ? "" : familyNameList.get(i).get("family_name").toString();
						hsql = "SELECT a.id albumId, a.album_name albumName, a.ALBUM_URL albumUrl ,(SELECT COUNT(p.id) FROM t_photo p WHERE p.album_id = a.ID )  photoNumber FROM t_album a WHERE a.CREATE_USER_ID = ? AND a.FAMILY_NAME = ? ORDER BY a.CREATE_DATE";
						List<Map<String, Object>> albumList = baseDAO.getGenericBySQL(hsql, new Object[]{ userId, familyName});
						data.put("familyName", familyName);
						data.put("albumList", albumList);
						albumListArray.add(data);
					}
				}
			}
		
		} catch (Exception e) {
			log.info("查询相册照片时异常："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", null);
			returnJSON.put("errorMsg", "服务器内部错误");
			return returnJSON.toString();
		}
		result.put("totalResults", total);
		result.put("isDir", isDir);
		result.put("list", list);
		result.put("albumList", albumListArray);
		result.put("succMsg", "查询成功！");	
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

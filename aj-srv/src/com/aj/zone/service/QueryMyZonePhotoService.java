package com.aj.zone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.photomgr.vo.TPhoto;
import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查询我的空间照片列表
 * */

@Service("myZonePhotoList")
public class QueryMyZonePhotoService implements PublishService{

	private Logger log = Logger.getLogger(QueryMyZonePhotoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String zoneId = params.optString("zoneId");
		String zoneType = params.optString("zoneType");
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
		if(zoneId == null || "".equals(zoneId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "zoneId为空！");
			return returnJSON.toString();
		}
		if(zoneType == null || "".equals(zoneType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "zoneType为空！");
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
		int total = 0;
		try {
			String imgUrl= SystemConfig.getValue("img.http.url");
			String resUrl= SystemConfig.getValue("res.http.url");
			if(!"7".equals(zoneType) && !"8".equals(zoneType)){
				
				String hsql = "from TPhoto where createUserId = ? and albumId = ?";
				List<TPhoto> totalList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId), zoneId);
				total = totalList ==null ? 0 : totalList.size();
				int begin = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
				//int end = (Integer.parseInt(currentPage)+1) * Integer.parseInt(pageSize);
				int end = Integer.parseInt(pageSize);
				String sql = "SELECT DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') create_date FROM (SELECT * FROM t_photo WHERE  ALBUM_ID = ? ORDER BY create_date DESC LIMIT ?,? ) p " +
						" GROUP BY DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') ORDER BY create_date DESC";
				List<Map<String, Object>> dayList = baseDAO.getGenericBySQL(sql, new Object[]{ zoneId, begin, end });
				//查询每天下面的照片信息
				
				if(dayList != null && dayList.size()>0){
					for(int i=0;i<dayList.size();i++){
						
						String date = dayList.get(i).get("create_date").toString();
						sql = "SELECT p.id photoId,CONCAT('"+imgUrl+"',p.PHOTO_URL)  photo, p.ISPRIVATE isPrivate,case when p.ViDEO_URL is not null and p.ViDEO_URL != '' then  CONCAT('"+resUrl+"',p.ViDEO_URL) else '' end  video,photo_type photoType,p.descript FROM t_photo p WHERE  p.ALBUM_ID = ?   AND  DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') = ? ORDER BY p.CREATE_DATE DESC";
						List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{zoneId, date});
						
						JSONObject jo = new JSONObject();
						jo.put("date", date);
						jo.put("photoPreDayList", photoList);
						list.add(jo);
					}
				}
			}else if("7".equals(zoneType)){
				
				TShopAlbum album = baseDAO.get(TShopAlbum.class, zoneId);
				String hsql = "from TShopPhoto where albumId = ?";
				List<TShopPhoto> totalList = baseDAO.getGenericByHql(hsql, zoneId);
				total = totalList ==null ? 0 : totalList.size();
				JSONObject jo = new JSONObject();
				jo.put("date", album.getPhotoTime());
				List<Map<String,Object>> photoList = new ArrayList<Map<String,Object>>();
				for(TShopPhoto photo : totalList){
					//,photo_type photoType,p.descript
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("photoId",photo.getId());
					 map.put("photo", imgUrl+photo.getPhotoUrl());
					 map.put("isPrivate", "1");
					 map.put("video", "");
					 map.put("photoType", "1");
					 map.put("descript", "");
					 photoList.add(map);
				}
				jo.put("photoPreDayList", photoList);
				list.add(jo);
			}
		
		} catch (Exception e) {
			log.info("查询个人空间照片时异常："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", null);
			returnJSON.put("errorMsg", "服务器内部错误");
			return returnJSON.toString();
		}
		result.put("totalResults", total);
		result.put("list", list);
		result.put("succMsg", "查询成功！");	
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

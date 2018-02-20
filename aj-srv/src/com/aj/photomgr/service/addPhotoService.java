package com.aj.photomgr.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.photomgr.vo.TPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 添加照片
 * */

@Service("albumPhotoAdd")
public class addPhotoService implements PublishService{

	private Logger log = Logger.getLogger(addPhotoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		String photoUrl = params.optString("photoUrl");
		
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
		if(photoUrl == null || "".equals(photoUrl)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "photoUrl为空！");
			return returnJSON.toString();
		}
		try {
			TPhoto photo = new TPhoto();
			photo.setCreateUserId(Integer.parseInt(userId));
			photo.setCreateDate(DateUtils.currentDate());
			String imgUrl= SystemConfig.getValue("img.http.url");
			photo.setPhotoUrl(photoUrl.replace(imgUrl, ""));
			photo.setAlbumId(albumId);
			photo.setIsprivate(0);
			baseDAO.save(photo);
			
		} catch (Exception e) {
			log.info("添加照片异常："+e.getMessage());
		}
		result.put("succMsg", "添加照片成功！");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

package com.aj.collectionmgr.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.collectionmgr.vo.TCollection;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 新增我的收藏
 * */

@Service("collectionAdd")
public class AddCollectionService implements PublishService{

	private Logger log = Logger.getLogger(AddCollectionService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String type = params.optString("type"); 
		String photoUrl = params.optString("photoUrl");
		String textUrl = params.optString("textUrl");
		String target = params.optString("target");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(type == null || "".equals(type)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "type为空！");
			return returnJSON.toString();
		}
		
		try {
			
			TCollection coll = new TCollection();
			coll.setCreateDate(DateUtils.currentDate());
			String imgUrl= SystemConfig.getValue("img.http.url");
			coll.setPhotoUrl(photoUrl.replace(imgUrl, ""));
			coll.setTarget(target);
			coll.setTextUrl(textUrl);
			coll.setType(type);
			coll.setUserId(Integer.parseInt(userId));
			baseDAO.save(coll);
			
			result.put("succMsg", "收藏成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
			
		} catch (Exception e) {
			log.info("保存收藏时候异常："+e.getMessage());
			e.printStackTrace();
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			return returnJSON.toString();
			
		}
	}

}

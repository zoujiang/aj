package com.aj.sys.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.sys.vo.TPhotoShare;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;

/**
 * 照片分享
 * 客户端传入照片列表，后台生成唯一照片集ID， 然后返回分享地址
 * */

@Service("photoShare")
public class PhotoShareService implements PublishService{

	private Logger log = Logger.getLogger(PhotoShareService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String photoUrl = params.optString("photoUrl");
		String type = params.optString("type");
		String shopId = params.optString("shopId");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}else if(photoUrl == null || "".equals(photoUrl)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "请选择要分享的照片！");
				return returnJSON.toString();
		}else if(photoUrl.split(",").length > 12){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "分享的照片数量不能超过12张");
			return returnJSON.toString();
		}else if(type == null || "".equals(type) ){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "类型不能为空！");
			return returnJSON.toString();
		}else if("1".equals(type) && (shopId == null || "".equals(shopId))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "分享相册为商户类型时商户id不能为空！");
			return returnJSON.toString();
		}
		
		try {
			TPhotoShare ps = new TPhotoShare();
			String id = UUIDUtil.uuid();
			ps.setId(id);
			ps.setUrl(photoUrl);
			ps.setShopId(shopId);
			baseDAO.save(ps);
			String url = "";
			if("1".equals(type)){ //商家相册
				url = SystemConfig.getValue("photo_share_url_shop")+"?"+id;
			}else{ 
				//普通相册
				url = SystemConfig.getValue("photo_share_url_nomal")+"?"+id;
			}
			result.put("shareUrl", url);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			
		} catch (Exception e) {
			
			log.info("获取分享照片地址失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}

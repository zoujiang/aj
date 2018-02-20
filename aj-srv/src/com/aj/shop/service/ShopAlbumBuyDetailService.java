package com.aj.shop.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.EncryptUtils;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 5.7.5影楼相册购买详情
 * */

@Service("shopAlbumBuyDetail")
public class ShopAlbumBuyDetailService implements PublishService{

	private Logger log = Logger.getLogger(ShopAlbumBuyDetailService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		
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
		
		TShopAlbum  album = baseDAO.get(TShopAlbum.class, albumId);
		if(album == null ){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", "相册不存在");
			return returnJSON.toString();
		}else{
			TShopInfo shop = baseDAO.get(TShopInfo.class, album.getShopId());
			
			if(album.getIsPay() == 0 || album.getHadPaid() == 1){
				result.put("albumName", album.getAlbumName());
				//result.put("fileUrl", com.frame.core.constant.Constant.resPath+ album.getDownloadAddress());
				String id_encode = EncryptUtils.getInstance().base64_encode(album.getId());
				result.put("fileUrl", com.frame.core.constant.Constant.photoZipDownloadAddress +"?"+ id_encode);
				result.put("filePassword", album.getDownloadSecret());
				if(shop !=  null ){
					result.put("shopName", shop.getShopName());
					result.put("tel", shop.getTel());
				}else{
					result.put("shopName", "");
					result.put("tel", "");
				}
			}
		}
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}

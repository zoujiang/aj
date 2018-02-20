package com.aj.shop.service;

import com.aj.shop.vo.TShopInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 查询商家详情
 * */

@Service("shopDetail")
public class QueryShopDetailService implements PublishService{

	private Logger log = Logger.getLogger(QueryShopDetailService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String shopId = params.optString("shopId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String re = checkParam(params, new String[]{"userId","shopId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		//根据userId查询有哪些商户

		String imgUrl= SystemConfig.getValue("img.http.url");
        String resUrl= SystemConfig.getValue("res.http.url");
		TShopInfo shop = baseDAO.get(TShopInfo.class, shopId);
		Map<String, Object> shopInfo = new HashMap<String, Object>();
		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		if(shop != null){
			shopInfo.put("shopId", shopId);
			shopInfo.put("shopName", shop.getShopName());
			shopInfo.put("shopAddress", shop.getAddress());
			shopInfo.put("tel",  shop.getTel());
			shopInfo.put("description", shop.getDescription());

			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shopId});
			if(cmtList != null && cmtList.size() > 0){
				shopInfo.put("shopScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
				shopInfo.put("shopCmtCount", cmtList.get(0).get("num"));
			}else{
				shopInfo.put("shopScore", 4);
				shopInfo.put("shopCmtCount", 0);
			}

			if( shop.getLogo() != null && !"".equals(shop.getLogo())){
				shopInfo.put("shopLogo", imgUrl + shop.getLogo());
			}
			if( shop.getShowPic() != null && !"".equals(shop.getShowPic())){
				String[] pics = shop.getShowPic().split(",");
				StringBuffer fullUrl = new StringBuffer();
				for(String pic : pics){
					fullUrl.append(imgUrl);
					fullUrl.append(pic);
					fullUrl.append(",");
				}
				shopInfo.put("shopShowPic", fullUrl.toString().substring(0, fullUrl.toString().length()-1));
			}
			//查询作品展示
           String sql = "SELECT  id showId, NAME showName, category, CASE WHEN (imageUrl IS NOT NULL AND imageUrl != '' ) THEN CONCAT('"+imgUrl+"',imageUrl) ELSE '' END imageUrl , CASE WHEN (videoUrl IS NOT NULL AND videoUrl != '' ) THEN CONCAT('"+resUrl+"',videoUrl) ELSE '' END  videoUrl  FROM t_show  WHERE shop_id = ?";
           List<Map<String, Object>> shopShow = baseDAO.getGenericBySQL(sql, new Object[]{shopId});
            result.put("shopShow", shopShow);
		}
        result.put("shopInfo", shopInfo);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
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

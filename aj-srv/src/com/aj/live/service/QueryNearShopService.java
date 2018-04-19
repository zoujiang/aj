package com.aj.live.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GpsDistanceUtil;

import net.sf.json.JSONObject;


/**
 * 查询附近商家列表
 * */

@Service("queryNearShop")
public class QueryNearShopService implements PublishService{

	private Logger log = Logger.getLogger(QueryNearShopService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {

		String imgUrl= SystemConfig.getValue("img.http.url");

		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gps = params.optString("gps");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(gps == null || "".equals(gps) || gps.split(",").length != 2){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "gps为空或格式不对！");
			return returnJSON.toString();
		}
		double distance = Double.parseDouble(SystemConfig.getValue("aj.near.shop.distance"));
		double x = Double.parseDouble(gps.split(",")[0]);
		double y = Double.parseDouble(gps.split(",")[1]);
		
		String sql = "select 1 type, i.id shopId, i.shop_name shopName, c.`name` shopCategory, i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_shop_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0 and i.gps is not null and i.gps != '' and ( substring_index(i.gps, ',', 1 ) <= "+ (x + distance) +" or substring_index(i.gps, ',', 1 ) >= "+ (x - distance) +" ) and ( substring(gps, LOCATE(',',gps ) +1 ) <= "+( y + distance) +" or  substring(gps, LOCATE(',',gps ) +1 ) >= "+ (y - distance) + " ) ";
		sql+= " union ";		
		sql+= " SELECT 2 type, i.id shopId, i.name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_kindergarten_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0  and i.gps is not null and i.gps != '' and ( substring_index(i.gps, ',', 1 ) <= "+ (x + distance) +" or substring_index(i.gps, ',', 1 ) >= "+ (x - distance) +" ) and ( substring(gps, LOCATE(',',gps ) +1 ) <= "+( y + distance) +" or  substring(gps, LOCATE(',',gps ) +1 ) >= "+ (y - distance) + " ) ";	
		sql+= " union ";		
		sql+= " SELECT 3 type, i.id shopId, i.shop_name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_coupon_shop_info i, t_shop_category c WHERE i.`shop_category` = c.`id` AND i.status = 0  and i.gps is not null and i.gps != '' and ( substring_index(i.gps, ',', 1 ) <= "+ (x + distance) +" or substring_index(i.gps, ',', 1 ) >= "+ (x - distance) +" ) and ( substring(gps, LOCATE(',',gps ) +1 ) <= "+( y + distance) +" or  substring(gps, LOCATE(',',gps ) +1 ) >= "+ (y - distance) + " ) ";
		
		List<Map<String, Object>> shopList =  baseDAO.getGenericBySQL(sql, null);
		
		
		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";

		for(Map<String, Object> shop : shopList){
			String logo = shop.get("shopLogo") == null ? "" :shop.get("shopLogo").toString();
			if(!"".equals(logo)){
				logo = imgUrl + logo;
				shop.put("shopLogo", logo);
			}

			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("shopId")});
			String shopScore = "4";
			int shopCmtCount = 0;
			if(cmtList != null && cmtList.size() > 0){
				shopScore = cmtList.get(0).get("score") == null ? shopScore :  cmtList.get(0).get("score")+"";
				shopCmtCount = Integer.parseInt(cmtList.get(0).get("num")+"");
			}
			shop.put("shopScore", shopScore);
			shop.put("shopCmtCount", shopCmtCount);
			shop.put("distance", GpsDistanceUtil.getDistance(gps, shop.get("gps").toString()));
		}
		
		result.put("shopList", shopList);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
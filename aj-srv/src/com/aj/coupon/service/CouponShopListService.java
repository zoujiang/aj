package com.aj.coupon.service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GpsDistanceUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 优惠券商家列表
 * */

@Service("couponShopList")
public class CouponShopListService implements PublishService{

	private Logger log = Logger.getLogger(CouponListService.class);

	String imgUrl= SystemConfig.getValue("img.http.url");
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {



		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gps = params.optString("gps");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		String brandId = params.optString("brandId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String re = checkParam(params, new String[]{"brandId","pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re + "不能为空！");
			return returnJSON.toString();
		}
		List<Object> sqlParam = new ArrayList<Object>();
		String sql = "SELECT  s.`id` shopId, s.`shop_name` shopName, s.tel, s.logo, " +
				"s.`address` shopAddress, s.`gps`, c.name shopCategory FROM  t_coupon_shop_info s left join t_shop_category c on s.shop_category = c.id WHERE s.status = 0 and s.brand_id = ? order by s.recommend_Ix limit ?, ?";
		sqlParam.add(brandId);
		sqlParam.add( Integer.parseInt(currentPage) * Integer.parseInt(pageSize) );
		sqlParam.add(Integer.parseInt(pageSize));

		List<Map<String, Object>> couponShopList = baseDAO.getGenericBySQL(sql , sqlParam.toArray());

		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";

		for(Map<String, Object> shop : couponShopList){
			if(shop.get("logo") != null && !"".equals(shop.get("logo"))){
				shop.put("logo", imgUrl + shop.get("logo") );
			}

			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("shopId")});
			if(cmtList != null && cmtList.size() > 0){
				shop.put("couponShopScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
				shop.put("couponShopCmtCount", cmtList.get(0).get("num"));
			}else{
				shop.put("couponShopScore", 4);
				shop.put("couponShopCmtCount", 0);
			}
			if(gps != null && !"".equals(gps) && shop.get("gps") != null && !"".equals(shop.get("gps"))){
				double distance = GpsDistanceUtil.getDistance(gps, shop.get("gps").toString());
				shop.put("distance", distance);
			}else{
				shop.put("distance", -1);
			}
		}
		result.put("shopList", couponShopList);
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

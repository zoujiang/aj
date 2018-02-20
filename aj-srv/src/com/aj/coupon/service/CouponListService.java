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
 * 优惠券列表
 * */

@Service("couponList")
public class CouponListService implements PublishService{

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
		String couponName = params.optString("couponName");
		String brandId = params.optString("brandId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String re = checkParam(params, new String[]{"pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re + "不能为空！");
			return returnJSON.toString();
		}
		List<Object> sqlParam = new ArrayList<Object>();
		String sql = "SELECT i.`id` couponId, i.`name` couponName, i.`first_page_pic` couponLogo, i.`is_link` isLink, i.`link_address` linkAddress, i.description, s.`id` shopId, s.`shop_name` shopName," +
				"s.`address` shopAddress, s.`gps` FROM t_coupon_info i, t_coupon_shop_info s WHERE i.`shop_id` = s.`id` and i.is_validate = 0 ";
		if(brandId != null && !"".equals(brandId)){
			sql += "and s.brand_id = ? ";
			sqlParam.add(brandId);
		}
		if(couponName != null && !"".equals(couponName)){
			sql += " and i.name like '%"+couponName+"%'";
		}
		if(userId != null && !"".equals(userId)){

			sql += " and i.id in (select coupon_id from t_my_coupon where user_id = ?) ";
			sqlParam.add(userId);
		}
		sql += " ORDER BY i.recommend_idx limit ?, ?";
		sqlParam.add( Integer.parseInt(currentPage) * Integer.parseInt(pageSize) );
		sqlParam.add(Integer.parseInt(pageSize));

		List<Map<String, Object>> couponList = baseDAO.getGenericBySQL(sql , sqlParam.toArray());

		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";

		for(Map<String, Object> coupon : couponList){
			if(coupon.get("couponLogo") != null && !"".equals(coupon.get("couponLogo"))){
				coupon.put("couponLogo", imgUrl + coupon.get("couponLogo") );
			}

			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{coupon.get("shopId")});
			if(cmtList != null && cmtList.size() > 0){
				coupon.put("couponScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
				coupon.put("couponCmtCount", cmtList.get(0).get("num"));
			}else{
				coupon.put("couponScore", 4);
				coupon.put("couponCmtCount", 0);
			}
			if(gps != null && !"".equals(gps) && coupon.get("gps") != null && !"".equals(coupon.get("gps"))){
				double distance = GpsDistanceUtil.getDistance(gps, coupon.get("gps").toString());
				coupon.put("distance", distance);
			}else{
				coupon.put("distance", -1);
			}
		}

		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", couponList);
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

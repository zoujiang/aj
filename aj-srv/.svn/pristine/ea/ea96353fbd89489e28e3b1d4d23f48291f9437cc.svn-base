package com.aj.coupon.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.coupon.vo.TCouponExchange;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


/**
 * 优惠券详情
 * */

@Service("couponDetail")
public class CouponDetailService implements PublishService{

	private Logger log = Logger.getLogger(CouponDetailService.class);

	String imgUrl= SystemConfig.getValue("img.http.url");
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {



		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int couponId = params.optInt("couponId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);

		String sql = "SELECT i.`id` couponId, i.`name` couponName, i.`first_page_pic` couponLogo, i.get_condition  getCondition, i.show_img couponShowPic, i.`is_link` isLink, i.`link_address` linkAddress, i.description, i.limit_num limitNum, i.left_num leftNum, s.`id` shopId, s.`shop_name` shopName," +
				"s.`address` shopAddress, s.`tel` shopTel FROM t_coupon_info i, t_coupon_shop_info s WHERE i.`shop_id` = s.`id` and i.id = ? ";

		List<Map<String, Object>> couponList = baseDAO.getGenericBySQL(sql , new Object[]{couponId} );

		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		Map<String, Object> coupon = couponList.get(0);
		if(coupon.get("couponLogo") != null && !"".equals(coupon.get("couponLogo"))){
			coupon.put("couponLogo", imgUrl + coupon.get("couponLogo") );
		}
		if(coupon.get("couponShowPic") != null && !"".equals(coupon.get("couponShowPic"))){

			String[] showPics = coupon.get("couponShowPic").toString().split(",");
			String pics = "";
			for(String pic : showPics){
				pics += imgUrl + pic +",";
			}
			coupon.put("couponShowPic" , pics.substring(0, pics.length() -1));
		}

		List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{coupon.get("shopId")});
		if(cmtList != null && cmtList.size() > 0){
			coupon.put("couponScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
			coupon.put("couponCmtCount", cmtList.get(0).get("num"));
		}else{
			coupon.put("couponScore", 4);
			coupon.put("couponCmtCount", 0);
		}
		int limitNum = Integer.parseInt(coupon.get("limitNum")+"");
		int leftNum = Integer.parseInt(coupon.get("leftNum")+"");
		if(userId != null && !"".equals(userId)){
			
			List<TCouponExchange> hadCouponList = baseDAO.getGenericByHql("from TCouponExchange where couponId = ? and userId =?", couponId, userId);
			if(leftNum == 0 ||  limitNum >= hadCouponList.size()){
				
				coupon.put("hadGet", 0);
			}else{
				coupon.put("hadGet", 1);
			}
		}

		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", coupon);
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

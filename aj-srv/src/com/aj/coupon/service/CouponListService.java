package com.aj.coupon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


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
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		String shopId = params.optString("shopId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String re = checkParam(params, new String[]{"shopId","pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re + "不能为空！");
			return returnJSON.toString();
		}
		List<Object> sqlParam = new ArrayList<Object>();
		String sql = "SELECT i.* FROM t_coupon_info i WHERE i.`shop_id` = ? and i.is_validate = 0  ";
		sqlParam.add(shopId);
		sql += " order by i.create_time limit ?, ?";
		sqlParam.add( Integer.parseInt(currentPage) * Integer.parseInt(pageSize) );
		sqlParam.add(Integer.parseInt(pageSize));

		List<Map<String, Object>> couponList = baseDAO.getGenericBySQL(sql , sqlParam.toArray());

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> coupon : couponList){
			Map<String, Object> data = new HashMap<String, Object>();
			if(coupon.get("first_page_pic") != null && !"".equals(coupon.get("first_page_pic"))){
				data.put("couponLogo", imgUrl + coupon.get("first_page_pic") );
			}else{
				data.put("couponLogo", "");
			}
			data.put("couponId", coupon.get("id"));
			data.put("couponName", coupon.get("name"));
			data.put("couponGetCondition", coupon.get("get_condition"));
			data.put("startTime", coupon.get("start_time"));
			data.put("endTime", coupon.get("end_time"));
			data.put("isLink", coupon.get("is_link"));
			data.put("linkAddress", coupon.get("link_address"));
			dataList.add(data);
			
		}
		result.put("dataList", dataList);
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

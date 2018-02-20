package com.aj.pay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 支付方式列表
 * */

@Service("orderPayType")
public class OrderPayTypeService implements PublishService{

	private Logger log = Logger.getLogger(OrderPayTypeService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		String callType = json.optString("callType");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		
		JSONObject result = new JSONObject();
		if(callType == null || "".equals(callType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "callType为空！");
			return returnJSON.toString();
		}
		List<Map<String, Object>> payList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> payableList = baseDAO.getGenericBySQL("SELECT pay_type_android, pay_type_ios FROM t_shop_pay_able", null);
		if(payableList != null && payableList.size() > 0){
			Map<String, Object> payable = payableList.get(0);
			if("001".equals(callType)){
				String pta = payable.get("pay_type_android") == null? "" : payable.get("pay_type_android").toString();
				if(!"".equals(pta)){
					Map<String, Object> alp = new HashMap<String, Object>();
					alp.put("id", "alipay");
					alp.put("name", "支付宝");
					if("0".equals(pta.substring(1, 2))){
						alp.put("valid", "1");
					}else{
						alp.put("valid", "0");
					}
					payList.add(alp);
					Map<String, Object> wx = new HashMap<String, Object>();
					wx.put("id", "weixin");
					wx.put("name", "微信支付");
					if("0".equals(pta.substring(3, 4))){
						wx.put("valid", "1");
					}else{
						wx.put("valid", "0");
					}
					payList.add(wx);
				}
			}else if("002".equals(callType)){
				String pta = payable.get("pay_type_ios") == null? "" : payable.get("pay_type_ios").toString();
				if(!"".equals(pta)){
					Map<String, Object> alp = new HashMap<String, Object>();
					alp.put("id", "alipay");
					alp.put("name", "支付宝");
					if("0".equals(pta.substring(1, 2))){
						alp.put("valid", "1");
					}else{
						alp.put("valid", "0");
					}
					payList.add(alp);
					Map<String, Object> wx = new HashMap<String, Object>();
					wx.put("id", "weixin");
					wx.put("name", "微信支付");
					if("0".equals(pta.substring(3, 4))){
						wx.put("valid", "1");
					}else{
						wx.put("valid", "0");
					}
					payList.add(wx);
				}
			}
			result.put("list", payList);
		}
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}

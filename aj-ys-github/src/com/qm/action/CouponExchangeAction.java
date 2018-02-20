package com.qm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.qm.entities.CouponExchange;
import com.qm.service.CouponExchangeService;

@RestController
@RequestMapping("/coupon/exchange")
public class CouponExchangeAction {

	@Autowired
	private CouponExchangeService couponExchangeService;
	
	@RequestMapping("/list")
	public String list(String shopName, Integer offset, Integer limit){
		
		CouponExchange info = new CouponExchange();
		info.setShopName(shopName);
		List<CouponExchange> list = couponExchangeService.queryList(info);
		int total = couponExchangeService.getTotal(info);
		JSONObject json = new JSONObject();
        json.put("rows",list );
        json.put("total", total);
        return json.toJSONString();
	}
}

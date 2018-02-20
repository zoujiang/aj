package com.qm.shop.renewal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.jdbcdao.BaseDao;
import com.qm.shop.renewal.service.ShopRenewalService;

@Service("shopRenewalService")
@Scope("prototype")
public class ShopRenewalServiceImpl implements ShopRenewalService{

	@Autowired
	BaseDao baseDao;
	
	@Override
	public void save(String uuid, String out_trade_no, String shopId, double d, String prepay_id, String code_url,
			int type, String date) {
		
		String sql = "insert into t_shop_renewal (id, out_trade_no, shop_id, total_fee, prepay_id, code_url, pay_type, create_time) "
				+ "values (?,?,?,?,?,?,?,?)";
		baseDao.insert(sql, uuid, out_trade_no, shopId, d+"", prepay_id, code_url, type, date);
	}

	@Override
	public Map<String, Object> queryByOutTradeNo(String out_trade_no) {
		
		String sql = "select result_code, return_code from t_shop_renewal where out_trade_no = ?";
		List<Map<String, Object>> list =baseDao.query(sql, out_trade_no);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}

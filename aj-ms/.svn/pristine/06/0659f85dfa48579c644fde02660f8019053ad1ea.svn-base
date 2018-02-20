package com.qm.shop.payable.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.qm.shop.payable.service.ShopPayAbleService;

@Service("shopPayAbleService")
@Scope("prototype")
public class ShopPayAbleServiceImpl  implements ShopPayAbleService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;

	@Override
	public int saveOrUpdate(String id, String accountZFB, String secretZFB,
			String accountWX, String secretWX, String payTypeAndroid,
			String payTypeIOS, String accessAndroidZFB, String accessAndroidWX,
			String accessIOSZFB, String accessIOSWX, String createTime,
			String updateTime) {
		
		String querySql = "delete from t_shop_pay_able where id = ?" ;
		baseDAO.execteNativeBulk(querySql, new Object[]{id});
		//新增
		String sql = "insert into t_shop_pay_able (id, account_zfb, secret_zfb,account_wx,secret_wx, pay_type_android, pay_type_ios, access_android_zfb, access_android_wx, access_ios_zfb, access_ios_wx, create_time, update_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int index = baseDAO.execteNativeBulk(sql, id, accountZFB, secretZFB, accountWX, secretWX, payTypeAndroid, payTypeIOS, accessAndroidZFB, accessAndroidWX, accessIOSZFB, accessIOSWX, createTime, updateTime);
		
		return index;
	}

	@Override
	public Map<String, Object> init() {
		String sql = "select id, account_zfb, secret_zfb,account_wx,secret_wx, pay_type_android, pay_type_ios, access_android_zfb, access_android_wx, access_ios_zfb, access_ios_wx, create_time, update_time, service_amount from t_shop_pay_able";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	
}

package com.qm.shop.customer.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.customer.vo.ShopCustomerVO;

public interface ShopCustomerService {

	DataModel<Map<String, Object>> getList(ShopCustomerVO limitKey);

	int save(ShopCustomerVO limitKey);

	Map<String, Object> findAccountByUserTelAndShopId(String userTel,
			String shopId);

	Map<String, Object> findAppUserByUserTel(String userTel);

	int saveRegistRegInfo(String id, String tokenId,String userTel);

	List<Map<String, Object>> getAll(String shopId, String userTel);

	Map<String, Object> findCustomerById(String userId);


}

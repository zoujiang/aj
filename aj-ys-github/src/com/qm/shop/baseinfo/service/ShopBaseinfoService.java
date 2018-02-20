package com.qm.shop.baseinfo.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.baseinfo.vo.ShopInfoVO;
import com.qm.shop.baseinfo.vo.TShopInfo;

public interface ShopBaseinfoService {

	DataModel<Map<String, Object>> getList(ShopInfoVO limitKey);

	List<Map<String, Object>> getAll();

	Map<String, Object> findById(String shopId);

	int save(TShopInfo info);

	int modifyState(String id, String status);

	int update(TShopInfo info);

	List<Map<String, Object>> getAllDataForExport(String shopName);

	void updateZoneUsed(String shopId, long curUsedSize);

}

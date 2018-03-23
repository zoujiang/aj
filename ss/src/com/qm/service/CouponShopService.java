package com.qm.service;

import java.util.List;
import java.util.Map;

import com.qm.entities.CouponShopInfo;

public interface CouponShopService {

	int save(CouponShopInfo info);

	List<CouponShopInfo> queryList(CouponShopInfo info);

	int getTotal(CouponShopInfo info);

	int updateByPrimaryKeySelective(CouponShopInfo info);

	CouponShopInfo selectByPrimaryKey(String id);

	List<Map<String, Object>> queryList2(CouponShopInfo info);

}

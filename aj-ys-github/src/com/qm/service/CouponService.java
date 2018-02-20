package com.qm.service;

import java.util.List;

import com.qm.entities.CouponInfo;

public interface CouponService {

	int save(CouponInfo info);

	List<CouponInfo> queryList(CouponInfo info);

	int getTotal(CouponInfo info);

	int updateByPrimaryKeySelective(CouponInfo info);

	CouponInfo selectByPrimaryKey(Integer id);

}

package com.qm.mapper;

import java.util.List;

import com.qm.entities.CouponShopInfo;

public interface CouponShopInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CouponShopInfo record);

    int insertSelective(CouponShopInfo record);

    CouponShopInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CouponShopInfo record);

    int updateByPrimaryKey(CouponShopInfo record);

	List<CouponShopInfo> queryByCondition(CouponShopInfo info);

	int getTotal(CouponShopInfo info);
}
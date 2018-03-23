package com.qm.mapper;

import java.util.List;

import com.qm.entities.CouponInfo;

public interface CouponInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponInfo record);

    int insertSelective(CouponInfo record);

    CouponInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponInfo record);

    int updateByPrimaryKey(CouponInfo record);

	List<CouponInfo> selectByCondition(CouponInfo info);

	int getTotal(CouponInfo info);
}
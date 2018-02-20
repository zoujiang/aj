package com.qm.mapper;

import java.util.List;

import com.qm.entities.CouponExchange;

public interface CouponExchangeMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(CouponExchange record);

    int insertSelective(CouponExchange record);

    CouponExchange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponExchange record);

    int updateByPrimaryKey(CouponExchange record);

	List<CouponExchange> selectByCondtion(CouponExchange info);

	int getTotal(CouponExchange info);
}
package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.CouponExchange;
import com.qm.mapper.CouponExchangeMapper;
import com.qm.service.CouponExchangeService;

@Service
public class CouponExchangeServiceImpl implements CouponExchangeService {

	@Autowired
	private CouponExchangeMapper couponExchangeMapper;

	@Override
	public List<CouponExchange> queryList(CouponExchange info) {
		
		return couponExchangeMapper.selectByCondtion(info);
	}

	@Override
	public int getTotal(CouponExchange info) {
		
		return couponExchangeMapper.getTotal(info);
	}
}

package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.CouponShopInfo;
import com.qm.mapper.CouponShopInfoMapper;
import com.qm.service.CouponShopService;

@Service
public class CouponShopServiceImpl implements CouponShopService {
	
	@Autowired
	private CouponShopInfoMapper couponShopInfoMapper;

	@Override
	public int save(CouponShopInfo info) {
		
		return couponShopInfoMapper.insertSelective(info);
	}

	@Override
	public List<CouponShopInfo> queryList(CouponShopInfo info) {
		
		return couponShopInfoMapper.queryByCondition(info);
	}

	@Override
	public int getTotal(CouponShopInfo info) {
		
		return couponShopInfoMapper.getTotal(info);
	}

	@Override
	public int updateByPrimaryKeySelective(CouponShopInfo info) {
		
		return couponShopInfoMapper.updateByPrimaryKeySelective(info);
	}

	@Override
	public CouponShopInfo selectByPrimaryKey(String id) {
		
		return couponShopInfoMapper.selectByPrimaryKey(id);
	}

}

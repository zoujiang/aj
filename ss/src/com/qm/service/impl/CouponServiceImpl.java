package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.CouponInfo;
import com.qm.mapper.CouponInfoMapper;
import com.qm.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponInfoMapper couponInfoMapper;

	@Override
	public int save(CouponInfo info) {
		
		return couponInfoMapper.insertSelective(info);
	}

	@Override
	public List<CouponInfo> queryList(CouponInfo info) {
		
		return couponInfoMapper.selectByCondition(info);
	}

	@Override
	public int getTotal(CouponInfo info) {
		
		return couponInfoMapper.getTotal(info);
	}

	@Override
	public int updateByPrimaryKeySelective(CouponInfo info) {
		
		return couponInfoMapper.updateByPrimaryKeySelective(info);
	}

	@Override
	public CouponInfo selectByPrimaryKey(Integer id) {
		
		return couponInfoMapper.selectByPrimaryKey(id);
	}
}

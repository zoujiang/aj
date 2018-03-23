package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.PrizeGrantInfo;
import com.qm.mapper.PrizeGrantInfoMapper;
import com.qm.service.PrizeGrantService;

@Service
public class PrizeGrantServiceImpl implements PrizeGrantService {

	@Autowired
	private PrizeGrantInfoMapper prizeGrantInfoMapper;

	@Override
	public List<PrizeGrantInfo> queryList(PrizeGrantInfo info) {
		
		return prizeGrantInfoMapper.selectByCondtion(info);
	}

	@Override
	public int getTotal(PrizeGrantInfo info) {
		
		return prizeGrantInfoMapper.getTotal(info);
	}

	@Override
	public void insertSelected(PrizeGrantInfo prize) {
		
		prizeGrantInfoMapper.insertSelective(prize);
	}
	
}

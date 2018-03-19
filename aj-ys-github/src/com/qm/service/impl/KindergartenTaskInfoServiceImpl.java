package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenTaskInfo;
import com.qm.mapper.KindergartenTaskInfoMapper;
import com.qm.service.KindergartenTaskInfoService;

@Service
public class KindergartenTaskInfoServiceImpl implements KindergartenTaskInfoService {

	
	@Autowired
	private KindergartenTaskInfoMapper kindergartenTaskInfoMapper;
	
	@Override
	public List<KindergartenTaskInfo> queryList(KindergartenTaskInfo info) {
		
		return kindergartenTaskInfoMapper.selectByCondition(info);
	}

	@Override
	public int getTotal(KindergartenTaskInfo info) {
		
		return kindergartenTaskInfoMapper.getTotal(info);
	}

	@Override
	public KindergartenTaskInfo selectByPrimary(int id) {
		
		return kindergartenTaskInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(KindergartenTaskInfo info) {
		
		return kindergartenTaskInfoMapper.updateByPrimaryKeySelective(info);
	}

}

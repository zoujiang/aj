package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenAccount;
import com.qm.mapper.KindergartenAccountMapper;
import com.qm.service.KindergartenAccountService;

@Service
public class KindergartenAccountServiceImpl implements KindergartenAccountService {
	
	@Autowired
	private KindergartenAccountMapper kindergartenAccountMapper;

	@Override
	public List<KindergartenAccount> queryList(KindergartenAccount info) {
		
		return kindergartenAccountMapper.selectByCondition(info);
	}

	@Override
	public int getTotal(KindergartenAccount info) {
		
		return kindergartenAccountMapper.getTotal(info);
	}

	@Override
	public int save(KindergartenAccount account) {
		
		return kindergartenAccountMapper.insert(account);
	}

	@Override
	public int updateByPrimaryKeySelective(KindergartenAccount account) {
		
		return kindergartenAccountMapper.updateByPrimaryKeySelective(account);
			
	}

	@Override
	public KindergartenAccount selectByPrimaryKey(Integer id) {
		
		return kindergartenAccountMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<KindergartenAccount> findByCondition(KindergartenAccount account) {
		
		return kindergartenAccountMapper.findByCondition(account);
	}

}

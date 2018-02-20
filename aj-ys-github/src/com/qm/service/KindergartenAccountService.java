package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenAccount;

public interface KindergartenAccountService {

	List<KindergartenAccount> queryList(KindergartenAccount info);

	int getTotal(KindergartenAccount info);

	int save(KindergartenAccount account);

	int updateByPrimaryKeySelective(KindergartenAccount account);

	KindergartenAccount selectByPrimaryKey(Integer id);

	List<KindergartenAccount> findByCondition(KindergartenAccount account);

}

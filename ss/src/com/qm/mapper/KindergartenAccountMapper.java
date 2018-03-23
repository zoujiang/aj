package com.qm.mapper;

import java.util.List;

import com.qm.entities.KindergartenAccount;

public interface KindergartenAccountMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(KindergartenAccount record);

    int insertSelective(KindergartenAccount record);

    KindergartenAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenAccount record);

    int updateByPrimaryKey(KindergartenAccount record);

	List<KindergartenAccount> selectByCondition(KindergartenAccount info);

	int getTotal(KindergartenAccount info);

	List<KindergartenAccount> findByCondition(KindergartenAccount account);
}
package com.qm.mapper;

import com.qm.entities.KindergartenInfo;

import java.util.List;

public interface KindergartenInfoMapper {
   

	int deleteByPrimaryKey(Integer id);

    int insert(KindergartenInfo record);

    int insertSelective(KindergartenInfo record);

    KindergartenInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenInfo record);

    int updateByPrimaryKeyWithBLOBs(KindergartenInfo record);

    int updateByPrimaryKey(KindergartenInfo record);

    List<KindergartenInfo> selectByCondition(KindergartenInfo record);

    int getTotal(KindergartenInfo limitKey);
}
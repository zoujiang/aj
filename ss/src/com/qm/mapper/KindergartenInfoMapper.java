package com.qm.mapper;

import com.qm.entities.KindergartenInfo;

import java.util.List;
import java.util.Map;

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

	List<Map<String, Object>> selectByCondition2(KindergartenInfo info);
}
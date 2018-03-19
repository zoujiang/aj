package com.qm.mapper;

import java.util.List;

import com.qm.entities.KindergartenTaskInfo;

public interface KindergartenTaskInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenTaskInfo record);

    int insertSelective(KindergartenTaskInfo record);

    KindergartenTaskInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenTaskInfo record);

    int updateByPrimaryKey(KindergartenTaskInfo record);

	List<KindergartenTaskInfo> selectByCondition(KindergartenTaskInfo query);

	int getTotal(KindergartenTaskInfo info);
}
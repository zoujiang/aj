package com.qm.mapper;

import java.util.List;

import com.qm.entities.RewardInfo;

public interface RewardInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardInfo record);

    int insertSelective(RewardInfo record);

    RewardInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RewardInfo record);

    int updateByPrimaryKey(RewardInfo record);

	List<RewardInfo> selectByCondition(RewardInfo ri);
}
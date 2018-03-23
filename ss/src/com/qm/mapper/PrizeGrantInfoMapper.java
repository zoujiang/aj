package com.qm.mapper;

import java.util.List;

import com.qm.entities.PrizeGrantInfo;

public interface PrizeGrantInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PrizeGrantInfo record);

    int insertSelective(PrizeGrantInfo record);

    PrizeGrantInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PrizeGrantInfo record);

    int updateByPrimaryKey(PrizeGrantInfo record);

	List<PrizeGrantInfo> selectByCondtion(PrizeGrantInfo info);

	int getTotal(PrizeGrantInfo info);
}
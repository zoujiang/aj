package com.qm.mapper;

import java.util.List;
import java.util.Map;

import com.qm.entities.KindergartenDailyStatistics;

public interface KindergartenDailyStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenDailyStatistics record);

    int insertSelective(KindergartenDailyStatistics record);

    KindergartenDailyStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenDailyStatistics record);

    int updateByPrimaryKey(KindergartenDailyStatistics record);

	List<KindergartenDailyStatistics> selectByCondition(Map<String, Object> param);
}
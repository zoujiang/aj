package com.qm.mapper;

import java.util.List;

import com.qm.entities.KindergartenTeacherDailyUploadStatistics;

public interface KindergartenTeacherDailyUploadStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

	int insert(KindergartenTeacherDailyUploadStatistics record);

	int insertSelective(KindergartenTeacherDailyUploadStatistics record);

	KindergartenTeacherDailyUploadStatistics selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(KindergartenTeacherDailyUploadStatistics record);

	int updateByPrimaryKey(KindergartenTeacherDailyUploadStatistics record);

	void batchInsert(List<KindergartenTeacherDailyUploadStatistics> statisticsData);

	List<KindergartenTeacherDailyUploadStatistics> selectByParam(KindergartenTeacherDailyUploadStatistics statistics);

	int getTotal(KindergartenTeacherDailyUploadStatistics statistics);
}
package com.qm.mapper;

import com.qm.entities.KindergartenGradeVisitInfo;

public interface KindergartenGradeVisitInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenGradeVisitInfo record);

    int insertSelective(KindergartenGradeVisitInfo record);

    KindergartenGradeVisitInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenGradeVisitInfo record);

    int updateByPrimaryKey(KindergartenGradeVisitInfo record);

	int selectTotalByCondition(KindergartenGradeVisitInfo visitInfo);
}
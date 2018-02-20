package com.qm.mapper;

import java.util.List;

import com.qm.entities.KindergartenStudent;

public interface KindergartenStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenStudent record);

    int insertSelective(KindergartenStudent record);

    KindergartenStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenStudent record);

    int updateByPrimaryKey(KindergartenStudent record);

	List<KindergartenStudent> selectByCondition(KindergartenStudent student);

	int getTotal(KindergartenStudent student);

	List<KindergartenStudent> queryListWithPotoNum(KindergartenStudent student);
}
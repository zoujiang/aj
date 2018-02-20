package com.qm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qm.entities.KindergartenGrade;

public interface KindergartenGradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenGrade record);

    int insertSelective(KindergartenGrade record);

    KindergartenGrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenGrade record);

    int updateByPrimaryKey(KindergartenGrade record);

	List<KindergartenGrade> selectByCondition(KindergartenGrade grade);

	int getTotal(KindergartenGrade grade);
	
	KindergartenGrade selectGradeAndTeacherByPrimaryKey(Integer id);

	List<KindergartenGrade> selectGradeNamesByIds(@Param("gradeNum") List<String> gradeNum);
}
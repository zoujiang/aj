package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenGrade;

public interface KindergartenGradeService {

	int save(KindergartenGrade grade);

	List<KindergartenGrade> queryList(KindergartenGrade grade);

	int getTotal(KindergartenGrade grade);

	int deleteByPrimaryKey(Integer id);

	KindergartenGrade selectByPrimaryKey(Integer id);

	int update(KindergartenGrade grade);
	
	KindergartenGrade selectGradeAndTeacherByPrimaryKey(Integer id);

	String selectGradeNamesByIds(String gradeNum);

	int getSeriesNum(KindergartenGrade grade);

}

package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenTeacher;

public interface KindergartenTeacherService {

	List<KindergartenTeacher> queryList(KindergartenTeacher info);

	int getTotal(KindergartenTeacher info);

	int save(KindergartenTeacher account);

	int updateByPrimaryKeySelective(KindergartenTeacher account);

	KindergartenTeacher selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	int getNumberByType(int i);

	KindergartenTeacher queryTeacherByTel(KindergartenTeacher validate);

}

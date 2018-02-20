package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenStudent;

public interface KindergartenStudentService {

	int save(KindergartenStudent student);

	List<KindergartenStudent> queryList(KindergartenStudent student);

	int getTotal(KindergartenStudent student);

	int deleteByPrimaryKey(Integer id);

	KindergartenStudent selectByPrimaryKey(Integer id);

	int update(KindergartenStudent student);

	List<KindergartenStudent> queryListWithPotoNum(KindergartenStudent student);
	
}



package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenStudentMapper;
import com.qm.service.KindergartenStudentService;

@Service
public class KindergartenStudentServiceImpl implements KindergartenStudentService{
	
	@Autowired
	private KindergartenStudentMapper kindergartenStudentMapper;

	@Override
	public int save(KindergartenStudent student) {
		
		return kindergartenStudentMapper.insertSelective(student);
	}

	@Override
	public List<KindergartenStudent> queryList(KindergartenStudent student) {
		
		return kindergartenStudentMapper.selectByCondition(student);
	}

	@Override
	public int getTotal(KindergartenStudent student) {
		
		return kindergartenStudentMapper.getTotal(student);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return kindergartenStudentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public KindergartenStudent selectByPrimaryKey(Integer id) {
		
		return kindergartenStudentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(KindergartenStudent student) {
		
		return kindergartenStudentMapper.updateByPrimaryKeySelective(student);
	}

	@Override
	public List<KindergartenStudent> queryListWithPotoNum(KindergartenStudent student) {
		
		return kindergartenStudentMapper.queryListWithPotoNum(student);
	}

	@Override
	public List<KindergartenStudent> queryListWithHonorNum(KindergartenStudent student) {
		
		return kindergartenStudentMapper.queryListWithHonorNum(student);
	}

}

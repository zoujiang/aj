package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenPhotoMapper;
import com.qm.service.KindergartenPhotoService;

@Service
public class KindergartenPhotoServiceImpl implements KindergartenPhotoService {

	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;


	@Override
	public List<KindergartenPhoto> queryPhotoByOwerId(String owerId) {
		
		return kindergartenPhotoMapper.queryPhotoByOwerId(owerId);
	}

	@Override
	public int update(KindergartenPhoto photo) {
		
		return kindergartenPhotoMapper.updateByPrimaryKeySelective(photo);
	}

	@Override
	public int deleteByPrimary(Integer id) {
		
		return kindergartenPhotoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<KindergartenPhoto> queryListByTeacherIdAndDate(KindergartenPhoto photo) {
		
		return kindergartenPhotoMapper.selectByCondition(photo);
	}

	@Override
	public int getTotal(KindergartenPhoto photo) {
		
		return kindergartenPhotoMapper.getTotal(photo);
	}

	@Override
	public List<KindergartenPhoto> selectByCondition(KindergartenPhoto p) {
		
		return kindergartenPhotoMapper.selectByCondition(p);
	}
}

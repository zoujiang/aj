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
	public List<KindergartenStudent> queryList(KindergartenStudent student) {
		
	 return null;
	}

	@Override
	public int getTotal(KindergartenStudent student) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<KindergartenStudent> queryListWithPotoNum(KindergartenStudent student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KindergartenPhoto> queryPhotoByOwerId(String owerId) {
		
		return kindergartenPhotoMapper.queryPhotoByOwerId(owerId);
	}

	@Override
	public int update(KindergartenPhoto photo) {
		
		return kindergartenPhotoMapper.updateByPrimaryKeySelective(photo);
	}
}

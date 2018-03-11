package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenPhoto;

public interface KindergartenPhotoService {

	List<KindergartenPhoto> queryPhotoByOwerId(String owerId);

	int update(KindergartenPhoto photo);

	int deleteByPrimary(Integer id);

	List<KindergartenPhoto> queryListByTeacherIdAndDate(KindergartenPhoto photo);

	int getTotal(KindergartenPhoto photo);

}

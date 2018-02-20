package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;

public interface KindergartenPhotoService {

	List<KindergartenStudent> queryList(KindergartenStudent student);

	int getTotal(KindergartenStudent student);

	List<KindergartenStudent> queryListWithPotoNum(KindergartenStudent student);

	List<KindergartenPhoto> queryPhotoByOwerId(Integer owerId);

	int update(KindergartenPhoto photo);

}

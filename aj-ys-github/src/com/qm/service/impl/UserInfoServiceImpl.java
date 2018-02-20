package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.UserInfo;
import com.qm.mapper.UserInfoMapper;
import com.qm.service.UserInfoService;


@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public List<UserInfo> queryForPage(UserInfo user) {
		
		return userInfoMapper.queryForPage(user);
	}

	@Override
	public int getTotal(UserInfo user) {
		
		return userInfoMapper.getTotal(user);
	}
}

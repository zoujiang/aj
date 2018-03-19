package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenTeacher;
import com.qm.mapper.KindergartenTeacherMapper;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenTeacherService;

@Service
public class KindergartenTeacherServiceImpl implements KindergartenTeacherService {
	
	@Autowired
	private KindergartenTeacherMapper kindergartenTeacherMapper;
	@Autowired
	private KindergartenGradeService kindergartenGradeService;

	@Override
	public List<KindergartenTeacher> queryList(KindergartenTeacher info) {
		
		List<KindergartenTeacher> list = kindergartenTeacherMapper.selectByCondition(info);
		for(KindergartenTeacher t : list){
			if(t.getGradeNum() != null && !"".equals(t.getGradeNum())){
				
				String names = kindergartenGradeService.selectGradeNamesByIds(t.getGradeNum());
				t.setGradeNumNames(names);
			}
			
		}
		
		return list;
	}

	@Override
	public int getTotal(KindergartenTeacher info) {
		
		return kindergartenTeacherMapper.getTotal(info);
	}

	@Override
	public int save(KindergartenTeacher account) {
		
		return kindergartenTeacherMapper.insert(account);
	}

	@Override
	public int updateByPrimaryKeySelective(KindergartenTeacher account) {
		
		return kindergartenTeacherMapper.updateByPrimaryKeySelective(account);
			
	}

	@Override
	public KindergartenTeacher selectByPrimaryKey(Integer id) {
		
		return kindergartenTeacherMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
	
		return kindergartenTeacherMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int getNumberByType(int type) {
		
		return kindergartenTeacherMapper.getNumberByType(type);
	}

	@Override
	public KindergartenTeacher queryTeacherByTel(KindergartenTeacher validate) {
		
		return kindergartenTeacherMapper.selectTeacherByTel(validate);
	}


}

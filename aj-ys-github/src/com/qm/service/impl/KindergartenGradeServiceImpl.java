package com.qm.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.KindergartenGrade;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.service.KindergartenGradeService;
import com.util.GradeNameUtil;

@Service
public class KindergartenGradeServiceImpl implements KindergartenGradeService {

	String[] ruleNames = {"小小班","小班","中班","大班","大大班"};
	
	@Autowired
	private KindergartenGradeMapper kindergartenGradeMapper;

	@Override
	public int save(KindergartenGrade grade) {
		
		return kindergartenGradeMapper.insertSelective(grade);
	}

	@Override
	public List<KindergartenGrade> queryList(KindergartenGrade grade) {
		
		List<KindergartenGrade> gradeList = kindergartenGradeMapper.selectByCondition(grade);
		for(KindergartenGrade grade2 : gradeList){
			String ruleString = "";
			String rule = grade2.getRule();
			char[] ruleChar = rule.toCharArray();
			for(int i= 0 ; i< ruleChar.length ; i++){
				if(ruleChar[i] == '1'){
					ruleString += ruleNames[i] +";";
				}
			}
			grade2.setRule(ruleString);
			
			String series = grade2.getSeries();
			String name = series +"级";
			String className =  GradeNameUtil.getGradeName(grade2);
			grade2.setName(name + className + grade2.getClassNo());
		}
		return gradeList;
	}

	@Override
	public int getTotal(KindergartenGrade grade) {
		
		return kindergartenGradeMapper.getTotal(grade);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return kindergartenGradeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public KindergartenGrade selectByPrimaryKey(Integer id) {
		
		return kindergartenGradeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(KindergartenGrade grade) {
		
		return kindergartenGradeMapper.updateByPrimaryKeySelective(grade);
	}

	@Override
	public KindergartenGrade selectGradeAndTeacherByPrimaryKey(Integer id) {
		
		KindergartenGrade grade = kindergartenGradeMapper.selectGradeAndTeacherByPrimaryKey(id);
		if(grade != null){
			String ruleString = "";
			String rule = grade.getRule();
			char[] ruleChar = rule.toCharArray();
			for(int i= 0 ; i< ruleChar.length ; i++){
				if(ruleChar[i] == '1'){
					ruleString += ruleNames[i] +";";
				}
			}
			grade.setRule(ruleString);
			
			String series = grade.getSeries();
			String name = series +"级";
			String className =  GradeNameUtil.getGradeName(grade);
			grade.setName(name + className + grade.getClassNo());
			
		}
		
		return grade;
	}

	@Override
	public String selectGradeNamesByIds(String gradeNum) {
		List<KindergartenGrade> list = kindergartenGradeMapper.selectGradeNamesByIds(Arrays.asList(gradeNum.split(",")) );
		String names = "";
		for(KindergartenGrade grade : list){
			
			String series = grade.getSeries();
			String name = series +"级";
			String className =  GradeNameUtil.getGradeName(grade);
			names += name + className + grade.getClassNo() +",";
		}
		
		return names;
	}

	@Override
	public int getSeriesNum(KindergartenGrade grade) {
		
		return kindergartenGradeMapper.getSeriesNum(grade);
	}
	
}

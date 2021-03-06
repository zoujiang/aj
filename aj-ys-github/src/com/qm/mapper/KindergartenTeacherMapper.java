package com.qm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qm.entities.KindergartenTeacher;

public interface KindergartenTeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenTeacher record);

    int insertSelective(KindergartenTeacher record);

    KindergartenTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenTeacher record);

    int updateByPrimaryKey(KindergartenTeacher record);
    
    List<KindergartenTeacher> selectByCondition(KindergartenTeacher info);

	int getTotal(KindergartenTeacher info);

	List<KindergartenTeacher> select(KindergartenTeacher t);

	int getNumberByType(@Param("type") Integer type);

	KindergartenTeacher selectTeacherByTel(KindergartenTeacher validate);
}
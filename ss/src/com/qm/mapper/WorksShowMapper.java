package com.qm.mapper;

import java.util.List;

import com.qm.entities.WorksShow;

public interface WorksShowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksShow record);

    int insertSelective(WorksShow record);

    WorksShow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksShow record);

    int updateByPrimaryKey(WorksShow record);

	List<WorksShow> selectByCondition(WorksShow show);
}
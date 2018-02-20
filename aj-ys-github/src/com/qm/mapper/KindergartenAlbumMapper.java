package com.qm.mapper;

import com.qm.entities.KindergartenAlbum;

public interface KindergartenAlbumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenAlbum record);

    int insertSelective(KindergartenAlbum record);

    KindergartenAlbum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenAlbum record);

    int updateByPrimaryKey(KindergartenAlbum record);
}
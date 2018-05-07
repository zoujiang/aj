package com.qm.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.util.RandomGUID;
import com.qm.entities.KindergartenAlbum;
import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenAlbumMapper;
import com.qm.mapper.KindergartenPhotoMapper;
import com.qm.service.KindergartenPhotoService;
import com.qm.shop.Constant;
import com.util.FileZipUtil;

@Service
public class KindergartenPhotoServiceImpl implements KindergartenPhotoService {

	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	@Autowired
	private KindergartenAlbumMapper kindergartenAlbumMapper;


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
		
		KindergartenPhoto photo = kindergartenPhotoMapper.selectByPrimaryKey(id);
		int i = kindergartenPhotoMapper.deleteByPrimaryKey(id);
		//重新打压缩包
		KindergartenPhoto queryPhoto = new KindergartenPhoto();
		queryPhoto.setAlbumId(photo.getAlbumId());
		List<KindergartenPhoto> photoList = kindergartenPhotoMapper.selectByCondition(queryPhoto);
		List<String> urlList = new ArrayList<String>();
		for(KindergartenPhoto p : photoList){
			if(p.getCategory() == 1){
				
				String subUrl =  p.getPhotoUrl().substring(p.getPhotoUrl().lastIndexOf("/") + 1);
				if(subUrl.startsWith("s")){
					photo.setPhotoUrl(Constant.imgPrefix +photo.getPhotoUrl().substring(0, photo.getPhotoUrl().lastIndexOf("/") + 1) + subUrl.substring(1));
					urlList.add(Constant.path + p.getPhotoUrl().substring(0, p.getPhotoUrl().lastIndexOf("/") + 1) + subUrl.substring(1));
				}else{
					urlList.add(Constant.path + p.getPhotoUrl());
				}
				
			}else if(p.getCategory() == 2){
				urlList.add(Constant.path +p.getVideoUrl());
			}
		}
		String to ="/kindergarten/Album/"+ photo.getAlbumId() +".zip" ;
		String targetUrl = Constant.resPath + to ;
		String[] arr = {};
		File file = new File(targetUrl);
		file.delete();
		FileZipUtil.zip(urlList.toArray(arr), targetUrl);
		
		KindergartenAlbum album = new KindergartenAlbum();
		album.setId(photo.getAlbumId());
		album.setDownloadUrl(to);
		album.setDownloadSecret(RandomGUID.getRandomString(4));
		kindergartenAlbumMapper.updateByPrimaryKeySelective(album);
		
		
		return i;
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

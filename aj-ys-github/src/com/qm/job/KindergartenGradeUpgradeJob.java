package com.qm.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.entities.KindergartenAlbum;
import com.qm.entities.KindergartenGrade;
import com.qm.mapper.KindergartenAlbumMapper;
import com.qm.mapper.KindergartenGradeMapper;
import com.util.GradeNameUtil;
/**
 * 每个班9月1号升级，比如小班到中班，那么需要新建中班的相册
 * */
public class KindergartenGradeUpgradeJob {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenGradeMapper kindergartenGradeMapper;
	@Autowired
	private KindergartenAlbumMapper kindergartenAlbumMapper;

	public void process(){
		
		logger.info("开始统批量生成班级相册......");
		
		List<KindergartenGrade> gradeList = kindergartenGradeMapper.selectByCondition(null);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String createTime = format.format(new Date());
		List<KindergartenAlbum> albumList = new ArrayList<KindergartenAlbum>();
		for(KindergartenGrade grade : gradeList){
			KindergartenAlbum album = new KindergartenAlbum();
			String currentClassName = GradeNameUtil.getGradeName(grade);
			album.setAlbumName(currentClassName);
			album.setAlbumUrl(grade.getLogo());
			album.setCreateTime(createTime);
			album.setCreateUser("admin");
			album.setCurrentGradeName(currentClassName);
			album.setGradeId(grade.getId());
			album.setShcoolId(grade.getKindergartenId());
			album.setType(1);
			albumList.add(album);
		}
		if(albumList.size() > 0){
			
			kindergartenAlbumMapper.batchInsert(albumList);
		}
		logger.info("结束统批量生成班级相册.....batchInsert.");
		
	}
}

package com.qm.job;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.entities.KindergartenDailyStatistics;
import com.qm.entities.KindergartenInfo;
import com.qm.mapper.KindergartenDailyStatisticsMapper;
import com.qm.mapper.KindergartenHonorMapper;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.mapper.KindergartenPhotoMapper;
/**
 * 统计每个幼儿园每天照片、视频、荣誉的数量
 * */
public class KindergartenDailyStatisticsJob {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	@Autowired
	private KindergartenHonorMapper kindergartenHonorMapper;
	@Autowired
	private KindergartenInfoMapper kindergartenInfoMapper;
	@Autowired
	private KindergartenDailyStatisticsMapper kindergartenDailyStatisticsMapper;

	public void process(){
		
		logger.info("开始统计每个幼儿园每天照片、视频、荣誉的数量......");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long preDay = System.currentTimeMillis() - (24 *60 * 60 *1000);
		String preDayStr = format.format(preDay);
		KindergartenInfo kindergarten = new KindergartenInfo();
		kindergarten.setStatus(0);
		List<KindergartenInfo> kindergartenList = kindergartenInfoMapper.selectByCondition(kindergarten);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("queryDate", preDayStr);
		for(KindergartenInfo kInfo : kindergartenList){
			param.put("kindergartenId", kInfo.getId());
			int photoNum = kindergartenPhotoMapper.queryPhotoNumber(param);
			int videoNum = kindergartenPhotoMapper.queryVideoNumber(param);
			int honorNum = kindergartenHonorMapper.queryHonorNumber(param);
			KindergartenDailyStatistics record = new KindergartenDailyStatistics();
			record.setDaily(preDayStr);
			record.setKindergartenId(kInfo.getId());
			record.setHonorNum(honorNum);
			record.setPhotoNum(photoNum);
			record.setVideoNum(videoNum);
			kindergartenDailyStatisticsMapper.insertSelective(record);
		}
		
		logger.info("结束统计每个幼儿园每天照片、视频、荣誉的数量......");
		
	}
}

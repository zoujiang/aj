package com.qm.job.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.qm.entities.KindergartenDailyStatistics;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenTaskInfo;
import com.qm.entities.KindergartenTeacher;
import com.qm.entities.KindergartenTeacherDailyUploadStatistics;
import com.qm.entities.RewardInfo;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.mapper.KindergartenHonorMapper;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.mapper.KindergartenPhotoMapper;
import com.qm.mapper.KindergartenPhotoViewHistoryMapper;
import com.qm.mapper.KindergartenTaskInfoMapper;
import com.qm.mapper.KindergartenTeacherDailyUploadStatisticsMapper;
import com.qm.mapper.RewardInfoMapper;

public class TeacherDailyUploadStatisticsThread extends Thread{

	private Logger log = LoggerFactory.getLogger(TeacherDailyUploadStatisticsThread.class);
	private List<KindergartenTeacher> list;
	private Map<String, Object> param ;
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	private KindergartenHonorMapper kindergartenHonorMapper;
	private KindergartenTeacherDailyUploadStatisticsMapper kindergartenTeacherDailyUploadStatisticsMapper;
	
	public TeacherDailyUploadStatisticsThread(List<KindergartenTeacher> list, Map<String, Object> param){
		
		this.list = list;
		this.param = param;
		this.kindergartenPhotoMapper = (KindergartenPhotoMapper) param.get("kindergartenPhotoMapper");
		this.kindergartenHonorMapper = (KindergartenHonorMapper) param.get("kindergartenHonorMapper");
		this.kindergartenTeacherDailyUploadStatisticsMapper = (KindergartenTeacherDailyUploadStatisticsMapper) param.get("kindergartenTeacherDailyUploadStatisticsMapper");
	}
	@Override
	public void run() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long preDay = System.currentTimeMillis() - (24 *60 * 60 *1000);
		String preDayStr = format.format(preDay);
		List<KindergartenTeacherDailyUploadStatistics> statisticsData = new ArrayList<KindergartenTeacherDailyUploadStatistics>();
		for(KindergartenTeacher teacher : list){
			KindergartenTeacherDailyUploadStatistics statistics = new KindergartenTeacherDailyUploadStatistics();
			statistics.setTeacherId(teacher.getId());
			statistics.setKindergartenId(teacher.getKindergartenId());
			statistics.setTeacherName(teacher.getName());
			statistics.setStaticticsDate(preDayStr);
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("preDayStr", preDayStr);
			queryParam.put("teacherId", teacher.getId());
			//照片视频
			List<Map<String, Object>> dataList = kindergartenPhotoMapper.statistics(queryParam);
			for(Map<String, Object> map : dataList){
				if(map.get("category") != null && "1".equals(map.get("category").toString())){
					//照片
					statistics.setPhotoNum(map.get("total") == null ? 0 : Integer.parseInt(map.get("total").toString()));
				}else if(map.get("category") != null && "2".equals(map.get("category").toString())){
					//视频
					statistics.setVideoNum(map.get("total") == null ? 0 : Integer.parseInt(map.get("total").toString()));
				}
			}
			//荣誉
			int honorNum = kindergartenHonorMapper.statistics(queryParam);
			statistics.setHonorNum(honorNum);
			statisticsData.add(statistics);
		}
			
		kindergartenTeacherDailyUploadStatisticsMapper.batchInsert(statisticsData);
	}
	
}
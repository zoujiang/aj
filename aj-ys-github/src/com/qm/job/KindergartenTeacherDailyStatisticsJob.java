package com.qm.job;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.entities.KindergartenTeacher;
import com.qm.job.thread.TeacherDailyUploadStatisticsThread;
import com.qm.job.thread.ThreadPoolFactory;
import com.qm.mapper.KindergartenDailyStatisticsMapper;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.mapper.KindergartenHonorMapper;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.mapper.KindergartenPhotoMapper;
import com.qm.mapper.KindergartenPhotoViewHistoryMapper;
import com.qm.mapper.KindergartenTaskInfoMapper;
import com.qm.mapper.KindergartenTeacherDailyUploadStatisticsMapper;
import com.qm.mapper.KindergartenTeacherMapper;
import com.qm.mapper.RewardInfoMapper;
/**
 * 统计每个幼儿园老师每天照片、视频、荣誉的数量
 * */
public class KindergartenTeacherDailyStatisticsJob {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenInfoMapper kindergartenInfoMapper;
	@Autowired
	private KindergartenTeacherMapper kindergartenTeacherMapper;
	@Autowired
	private RewardInfoMapper rewardInfoMapper;
	@Autowired
	private KindergartenGradeMapper kindergartenGradeMapper;
	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	@Autowired
	private KindergartenHonorMapper KindergartenHonorMapper;
	@Autowired
	private KindergartenTeacherDailyUploadStatisticsMapper kindergartenTeacherDailyUploadStatisticsMapper;
	

	public void process(){
		
		logger.info("开始统计每个幼儿园老师每天照片、视频、荣誉的数量......");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kindergartenPhotoMapper", kindergartenPhotoMapper);
		param.put("KindergartenHonorMapper", KindergartenHonorMapper);
		param.put("rewardInfoMapper", rewardInfoMapper);
		param.put("kindergartenInfoMapper", kindergartenInfoMapper);
		param.put("kindergartenGradeMapper", kindergartenGradeMapper);
		param.put("kindergartenTeacherDailyUploadStatisticsMapper", kindergartenTeacherDailyUploadStatisticsMapper);
		int pageSize = 500;
		//防止内存溢出，每次取500条教师信息
		int total = kindergartenTeacherMapper.getTotal(null);
		int times = total % pageSize == 0 ? total/pageSize : (total / pageSize +1);
		KindergartenTeacher t = new KindergartenTeacher();
		for(int i=0; i< times; i++){
			t.setOffset(pageSize * i);
			t.setPageSize(pageSize);
			List<KindergartenTeacher> list = kindergartenTeacherMapper.select(t);
			ThreadPoolFactory.getInstance().getTheadPool().execute(new TeacherDailyUploadStatisticsThread(list, param));
		}
		
		logger.info("结束统计每个幼儿园老师每天照片、视频、荣誉的数量......");
		
	}
}

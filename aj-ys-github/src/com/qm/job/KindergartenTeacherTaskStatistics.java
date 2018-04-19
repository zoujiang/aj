package com.qm.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.entities.KindergartenTeacher;
import com.qm.job.thread.TeacherTaskStatisticsThread;
import com.qm.job.thread.ThreadPoolFactory;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.mapper.KindergartenPhotoViewHistoryMapper;
import com.qm.mapper.KindergartenTaskInfoMapper;
import com.qm.mapper.KindergartenTeacherMapper;
import com.qm.mapper.RewardInfoMapper;
/**
 * 幼儿园园长、管理人员、老师 任务情况
 * */
public class KindergartenTeacherTaskStatistics {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenTeacherMapper kindergartenTeacherMapper;
	@Autowired
	private KindergartenTaskInfoMapper kindergartenTaskInfoMapper;
	@Autowired
	private KindergartenPhotoViewHistoryMapper kindergartenPhotoViewHistoryMapper;
	@Autowired
	private RewardInfoMapper rewardInfoMapper;
	@Autowired
	private KindergartenInfoMapper kindergartenInfoMapper;
	@Autowired
	private KindergartenGradeMapper kindergartenGradeMapper;
	
	public void process(){
		
		logger.info("开始老师任务统计定时任务......");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kindergartenTaskInfoMapper", kindergartenTaskInfoMapper);
		param.put("kindergartenPhotoViewHistoryMapper", kindergartenPhotoViewHistoryMapper);
		param.put("rewardInfoMapper", rewardInfoMapper);
		param.put("kindergartenInfoMapper", kindergartenInfoMapper);
		param.put("kindergartenGradeMapper", kindergartenGradeMapper);
		int pageSize = 500;
		//防止内存溢出，每次取500条教师信息
		int total = kindergartenTeacherMapper.getTotal(null);
		int times = total % pageSize == 0 ? total/pageSize : (total / pageSize +1);
		KindergartenTeacher t = new KindergartenTeacher();
		for(int i=0; i< times; i++){
			t.setOffset(pageSize * i);
			t.setPageSize(pageSize);
			List<KindergartenTeacher> list = kindergartenTeacherMapper.select(t);
			ThreadPoolFactory.getInstance().getTheadPool().execute(new TeacherTaskStatisticsThread(list, param));
		}
		logger.info("结束老师任务统计定时任务查询......");
		
	}
}

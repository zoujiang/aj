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

import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenTaskInfo;
import com.qm.entities.KindergartenTeacher;
import com.qm.entities.RewardInfo;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.mapper.KindergartenPhotoViewHistoryMapper;
import com.qm.mapper.KindergartenTaskInfoMapper;
import com.qm.mapper.RewardInfoMapper;

public class TeacherTaskStatisticsThread extends Thread{

	private Logger log = LoggerFactory.getLogger(TeacherTaskStatisticsThread.class);
	private List<KindergartenTeacher> list;
	private Map<String, Object> param ;
	
	public TeacherTaskStatisticsThread(List<KindergartenTeacher> list, Map<String, Object> param){
		
		this.list = list;
		this.param = param;
	}
	@Override
	public void run() {
		
		KindergartenPhotoViewHistoryMapper visitInfoMapper = (KindergartenPhotoViewHistoryMapper) param.get("kindergartenPhotoViewHistoryMapper");
		KindergartenTaskInfoMapper kindergartenTaskInfoMapper = (KindergartenTaskInfoMapper) param.get("kindergartenTaskInfoMapper");
		RewardInfoMapper rewardInfoMapper = (RewardInfoMapper) param.get("rewardInfoMapper");
		KindergartenInfoMapper kindergartenInfoMapper = (KindergartenInfoMapper) param.get("kindergartenInfoMapper");
		KindergartenGradeMapper kindergartenGradeMapper = (KindergartenGradeMapper) param.get("kindergartenGradeMapper");
		
		int managerVisitNum = SystemConfig.getValue("aj.manager.task.condition") == null ? 3: Integer.parseInt(SystemConfig.getValue("aj.manager.task.condition"));
		String taskCondition = SystemConfig.getValue("aj.teacher.task.condition");
		if(taskCondition == null || "".equals(taskCondition) || taskCondition.split("\\|").length != 2){
			taskCondition = "8|3";
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
		String currentMonth = format.format(new Date());
		
		Long currentMonthTime =null;
		try {
			currentMonthTime = format0.parse(currentMonth+"-01").getTime() /1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for(KindergartenTeacher teacher : list){
			
			KindergartenTaskInfo taskInfo = new KindergartenTaskInfo();
			taskInfo.setTaskDate(currentMonth);
			taskInfo.setUserId(teacher.getUserId());
			taskInfo.setTeacherId(teacher.getId());
			taskInfo.setTeacherName(teacher.getName());
			taskInfo.setUserType(teacher.getType());
			taskInfo.setUpdateTime(DateUtil.getNowDate());
			taskInfo.setRenewPhone(teacher.getRechargeTelNo());
			if(teacher.getType() == 1 || teacher.getType() == 2 || teacher.getType() == 3){
				//园长 副园长 管理人员
				Map<String, Object> queryParam = new HashMap<String, Object>();
				queryParam.put("userId", teacher.getUserId());
				queryParam.put("startTime", currentMonthTime);
				queryParam.put("endTime", getLastDayOfMonth(currentMonth));
				
				int visitGradeNum = visitInfoMapper.selectTotalByCondition(queryParam);
				taskInfo.setVisitGradeNum(visitGradeNum);
				if(managerVisitNum > visitGradeNum){
					taskInfo.setIsGetReward(1);
				}else{
					KindergartenInfo kindergarten = kindergartenInfoMapper.selectByPrimaryKey(teacher.getKindergartenId());
					RewardInfo ri = new RewardInfo();
					ri.setUserType(teacher.getType());
					ri.setKindergartenCategory(kindergarten.getCategory());
					List<RewardInfo> rewardList = rewardInfoMapper.selectByCondition(ri);
					if(rewardList != null && !rewardList.isEmpty()){
						ri = rewardList.get(0);
						int rewardInfo = ri.getRewardInfo() ==null ? 0 : ri.getRewardInfo();
						taskInfo.setTotalReward(rewardInfo+"");
					}else{
						log.info("当前学校未配置奖励标准："+ kindergarten.getId() +"-"+kindergarten.getCategory());
					}
					
					taskInfo.setIsGetReward(0);
					
					taskInfo.setRemark("当月任务完成获得");
				}
				taskInfo.setIsSend(1);
				
			}else if(teacher.getType() == 4 || teacher.getType() == 5 || teacher.getType() == 6){
				//老师
				
				//String photoSql = "select t.gradeId, t.category, t.total, g.rule, g.series, g.class_no classNo, g.kindergarten_id kindergartenId from t_kindergarten_grade g, (select grade_id gradeId,category, count(category) total from t_kindergarten_photo where create_user = ? and left(create_time, 7) = ? group by grade_id, category ) t where g.id = t.gradeId";
				//存放gradeId- kindergartenId对应关系
				Map<String, Integer> gradeKindergartenMap = new HashMap<String, Integer>();
				
				List<String> dateList = getNPreMonth(1);
				//某月完成N个班级
				List<Map<String, Object>> complateList = new ArrayList<Map<String, Object>>();
				
				Map<String, Integer> gradeMap = new HashMap<String, Integer>();
				//每个班级 每月完成多余的数量，可以补之前2个月的， key: 班级_图片或视频_遗留数量  grade_category_left; 由于只能是后面月份补前面月份的，所以下面的dateList必须是时间倒序排列
				Map<String, Integer> gradeComplateLeftMap = new HashMap<String, Integer>();
				Set<String> gradeNameSet = new HashSet<String>();
				int i = 0; //计算首月照片、视频数
				int firstMonthPhotoNum = 0;
				int firstMonthVideoNum = 0;
				for(String yearMonth : dateList){
					i++;
					Map<String,Object> map = new HashMap<String, Object>();
					//List<Map<String, Object>> totalGradeList = baseDAO.getGenericBySQL(totalGradeSql, new Object[]{userId, yearMonth});
					String month = yearMonth.substring(5);
					//map.put("gradeNum", totalGradeList.get(0).get("totalGrade"));
					//List<Map<String, Object>> photoList = baseDao.getGenericBySQL(photoSql, new Object[]{teacher.getUserId(), yearMonth});
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("userId", teacher.getUserId());
					param.put("yearMonth", yearMonth);
					List<Map<String, Object>> photoList = kindergartenGradeMapper.selectGradeAndTeacherInfo(param);
					
					
					int complateGradeNum = 0;
					List<String> complateGradeNameList = new ArrayList<String>();
					//某月某班级的总照片数 key:  grade + "_photo_total"
					Map<String, Integer> gradePhotoTotalMap = new HashMap<String, Integer>();
					//某月某班级的总视频数 key:  grade + "_video_total"
					Map<String, Integer> gradeVideoTotalMap = new HashMap<String, Integer>();
					
					
					for(Map<String, Object> m : photoList){
						// m 代表每个班级
						int totalPhoto = 0;
						int totalVideo = 0;
						String gradeId = m.get("gradeId") +"";
						gradeKindergartenMap.put(gradeId, Integer.parseInt(m.get("kindergartenId")+"") );
						
						//gradeName是 gradeId - name拼接
					//	String gradeName = gradeId+"-"+ getGradeName(m.get("rule").toString(), m.get("series").toString(), m.get("classNo")==null?"":m.get("classNo").toString());
						String gradeName = gradeId+"-"+ gradeId;
						gradeNameSet.add(gradeName);
						
						if("1".equals( m.get("category").toString()) ){
							//照片
							totalPhoto = Integer.parseInt(m.get("total") +"");
							gradePhotoTotalMap.put(gradeName + "_photo_total", (gradePhotoTotalMap.get(gradeName + "_photo_total") ==null? 0:gradePhotoTotalMap.get(gradeName + "_photo_total")) + totalPhoto);
							gradeMap.put(gradeName+"_photoNum", (gradeMap.get(gradeName+"_photoNum") == null? 0 : gradeMap.get(gradeName+"_photoNum")) + totalPhoto) ;
							if(i == 1){
								firstMonthPhotoNum += totalPhoto;
							}
						}else if("2".equals( m.get("category").toString()) ){
							//视频
							totalVideo = Integer.parseInt(m.get("total") +"");
							gradeVideoTotalMap.put(gradeName + "_video_total", (gradeVideoTotalMap.get(gradeName + "_video_total") ==null? 0:gradeVideoTotalMap.get(gradeName + "_video_total"))+ totalVideo);
							gradeMap.put(gradeName+"_videoNum", (gradeMap.get(gradeName+"_videoNum") == null? 0 : gradeMap.get(gradeName+"_videoNum")) + totalVideo) ;
							if(i == 1){
								firstMonthVideoNum += totalVideo;
							}
						}
					}
					
					Iterator<String> iterator = gradeNameSet.iterator();
				    while(iterator.hasNext()){
				    	String gradeName = iterator.next();
				    	int totalPhoto = gradePhotoTotalMap.get(gradeName + "_photo_total") == null ? 0 : gradePhotoTotalMap.get(gradeName + "_photo_total");
				    	int totalVideo = gradeVideoTotalMap.get(gradeName + "_video_total") == null ? 0 : gradeVideoTotalMap.get(gradeName + "_video_total");
				    	
				    	totalPhoto += gradeComplateLeftMap.get(gradeName + "_1_left" ) == null ? 0 : gradeComplateLeftMap.get(gradeName + "_1_left" );
						totalVideo += gradeComplateLeftMap.get(gradeName + "_2_left" ) == null ? 0 : gradeComplateLeftMap.get(gradeName + "_2_left" );
							
						if(totalPhoto >= Integer.parseInt(taskCondition.split("\\|")[0]) && totalVideo >= Integer.parseInt(taskCondition.split("\\|")[1])){
							complateGradeNum ++;
							complateGradeNameList.add(gradeName);
							
							//去除达标数量， 把多余的累积起来(写在if里面，说明必须是当月达标了，才能把剩余的给前面的月份； 如果当月未达标，也可以把当月的给上月，需要把一下2行放到if外面， 并且不会减去达标数量)
							gradeComplateLeftMap.put(gradeName + "_1_left", totalPhoto - Integer.parseInt(taskCondition.split("\\|")[0]));
							gradeComplateLeftMap.put(gradeName + "_2_left", totalVideo - Integer.parseInt(taskCondition.split("\\|")[1]));
						}
				    	
				    }
				    Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("month", month);
					resultMap.put("yearMonth", yearMonth);
					resultMap.put("complateGradeNum", complateGradeNum);
					if(i == 1){
						
						int totalReward = 0;
						for(String complateGradeName : complateGradeNameList ){
							String gradeId = complateGradeName.substring(0, complateGradeName.indexOf("-")) ;
							KindergartenInfo kindergarten = kindergartenInfoMapper.selectByPrimaryKey(gradeKindergartenMap.get(gradeId));
							RewardInfo ri = new RewardInfo();
							ri.setUserType(teacher.getType());
							ri.setKindergartenCategory(kindergarten.getCategory());
							List<RewardInfo> rewardList = rewardInfoMapper.selectByCondition(ri);
							if(rewardList != null && !rewardList.isEmpty()){
								ri = rewardList.get(0);
								int rewardInfo = ri.getRewardInfo() ==null ? 0 : ri.getRewardInfo();
								totalReward += rewardInfo;
							}
						}
						taskInfo.setTotalReward(totalReward+"");
					}
					
					complateList.add(resultMap);
				}
				
				taskInfo.setPhotoNum(firstMonthPhotoNum);
				taskInfo.setVideoNum(firstMonthVideoNum);
				
				
				//如果是老师， 还可以补充前2个月的未任务
				if(i > 1){
					//前两个月
					//查询前2个月的任务完成情况
					
					complateList.remove(0); //去掉首月的
					for(Map<String, Object> map : complateList){
						if(map.get("complateGradeNum") != null && Integer.parseInt(map.get("complateGradeNum").toString()) > 0){
							String yearMonth = map.get("yearMonth")+"";
							//查询数据库中该月的任务完成情况， 如果未完成，更新状态为已完成
							KindergartenTaskInfo query = new KindergartenTaskInfo();
							query.setTaskDate(yearMonth);
							query.setTeacherId(teacher.getId());
							List<KindergartenTaskInfo> list = kindergartenTaskInfoMapper.selectByCondition(query);
							if(list != null && list.size() > 0){
								KindergartenTaskInfo info = list.get(0);
								if(info.getIsGetReward() == 1){
									info.setIsGetReward(0);
									info.setUpdateTime(DateUtil.getNowDate());
									info.setRemark("由"+currentMonth +"补充上传获得");
									
									KindergartenInfo kindergarten = kindergartenInfoMapper.selectByPrimaryKey(teacher.getKindergartenId());
									RewardInfo ri = new RewardInfo();
									ri.setUserType(teacher.getType());
									ri.setKindergartenCategory(kindergarten.getCategory());
									List<RewardInfo> rewardList = rewardInfoMapper.selectByCondition(ri);
									int totalReward = 0;
									if(rewardList != null && !rewardList.isEmpty()){
										ri = rewardList.get(0);
										int rewardInfo = ri.getRewardInfo() ==null ? 0 : ri.getRewardInfo();
										totalReward += rewardInfo;
									}
									info.setTotalReward(totalReward+"");
									kindergartenTaskInfoMapper.updateByPrimaryKeySelective(info);
								}
								
							}
							
						}
					}
					
				}
			}
			
			kindergartenTaskInfoMapper.insertSelective(taskInfo);
			
		}
		
	}
	
	/**
	 * 获取当前时间最近N个月
	 * 返回yyyy-MM
	 * */
	public static List<String> getNPreMonth(int n){
		List<String> list = new ArrayList<String>();
		if(n <= 0){ return list;}
		String patten = "yyyy-MM";
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		Calendar c = Calendar.getInstance();
		list.add(sdf.format(c.getTimeInMillis()));
		System.out.println(sdf.format(c.getTimeInMillis()) );
		for(int i=1; i< n; i++){
			c.add(Calendar.MONTH, -1);
			list.add(sdf.format(c.getTimeInMillis()));
		}
		
		return list;
	}
	 /** 
     * 获取某月的最后一天 
     * @Title:getLastDayOfMonth 
     * @Description: 
     * @param:@param yearMonth  2018-05
     * @param:@return 
     * @return:String 
     * @throws 
     */  
    public static Long getLastDayOfMonth(String yearMonth)  
    {  
    	String[] ym = yearMonth.split("-");
        Calendar cal = Calendar.getInstance();  
        //设置年份  
        cal.set(Calendar.YEAR, Integer.parseInt(ym[0]));  
        //设置月份  
        cal.set(Calendar.MONTH, Integer.parseInt(ym[1])-1);  
        //获取某月最大天数  
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
        //设置日历中月份的最大天数  
        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
        //格式化日期  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String lastDayOfMonth = sdf.format(cal.getTime());  
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {
			return sdf2.parse(lastDayOfMonth+" 23:59:59").getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }  
}
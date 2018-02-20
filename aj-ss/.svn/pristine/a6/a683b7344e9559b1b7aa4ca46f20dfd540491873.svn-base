package com.demo.task.jobs;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;

/**
 * 调用存储过程 PRO_T_PFT_DAY
 * @author roy
 * 
 */
public class IncomeDayJob implements Job {
	protected Log logger = LogFactory.getLog(IncomeDayJob.class);

	/*@Autowired
	private MarketMonitorService marketMonitorService;
	@Autowired
	private CallProcedureService callProcedureService;*/
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		/*logger.info("统计每天收益(" + arg0.getFireInstanceId() + ")*****start");
		try {
			Date start = marketMonitorService.getLastIncomeDayDT();
			if (start==null) {
				SimpleDateFormat formatDate = new SimpleDateFormat(DateUtil.DATE_TIME_PATTERN2);
				try {
					start = formatDate.parse(SystemConfig.getValue("pro.incomeDetail.init.startTime"));
				} catch (ParseException e) {
					logger.error("" , e);
				}
			}
			Date end = marketMonitorService.getOracleSysDate();
			callProcedureService.call("{call PRO_T_PFT_DAY(?,?)}", new BigInteger(DateUtil.dateFromat(start, DateUtil.DATE_TIME_PATTERN4)), 
					new BigInteger(DateUtil.dateFromat(end, DateUtil.DATE_TIME_PATTERN4)));
		} catch (Exception e) {
			logger.error("" , e);
		}
		logger.info("统计每天收益(" + arg0.getFireInstanceId() + ")*****end");*/
	}
}

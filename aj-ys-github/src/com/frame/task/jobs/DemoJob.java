package com.frame.task.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service
public class DemoJob extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		 System.out.println("schedule test date:" + new Date().toString()+" ,jobContext="+jobContext);			
	}
}

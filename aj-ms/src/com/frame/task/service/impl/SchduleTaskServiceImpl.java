package com.frame.task.service.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.frame.core.util.CronExpUtil;
import com.frame.core.util.StringUtil;
import com.frame.task.service.SchduleTaskService;
import com.frame.task.vo.QuartzTask;

/**
 * 任务调度业务处理类
 * 
 * @author lishun
 */
@Service
public class SchduleTaskServiceImpl implements SchduleTaskService,ApplicationContextAware {
	protected Log logger = LogFactory.getLog(SchduleTaskServiceImpl.class);
	private ApplicationContext ac;

	/**
	 * 添加任务(一个触发器)
	 */
	@SuppressWarnings("unchecked")
	public Scheduler addJobOneTrigger(QuartzTask task)
			throws SchedulerException {
		logger.info("加入调度任务Service(addJobOneTrigger:" + task + ")*****start");
		Scheduler mktScheduler= (StdScheduler)ac.getBean("gSchedulerFactory");
		if (null != task) {
			// 注册任务名称
			JobDetail myJob = newJob(task.getClassName()).withIdentity(
					CronExpUtil.generateID(task.getJobName()),
					task.getJobGroup()).requestRecovery(true)// 失败后重做
					.build();
			myJob.getJobDataMap().putAll(task.getData());// 保存数据
			Trigger trigger = newTrigger().withIdentity(
					CronExpUtil.generateID(task.getTriggerName()),
					task.getTriggerGroup()).withSchedule(
					CronScheduleBuilder.cronSchedule(task.getExpress()))
					.forJob(myJob).build(); // 调度任务
			trigger.getJobDataMap().putAll(task.getData());// 保存数据
			mktScheduler.scheduleJob(myJob, trigger);// 把新任务和触发器绑定调度
			logger.info("加入调度任务Service(addJobOneTrigger:" + task + ")*****end");
		}
		return mktScheduler;
	}

	/**
	 * 添加任务(多触发器)
	 */
	@SuppressWarnings("unchecked")
	public Scheduler addJobMoreTrigger(QuartzTask task)
			throws SchedulerException {
		logger.info("加入调度任务(addJobMoreTrigger:" + task + ")*****start");
		Scheduler mktScheduler= (StdScheduler)ac.getBean("gSchedulerFactory");
		if (null != task) {
			// 注册任务名称
			JobDetail myJob = newJob(task.getClassName()).withIdentity(
					CronExpUtil.generateID(task.getJobName()),
					task.getJobGroup()).storeDurably(true)// 没有trigger关联时自动删除
					.requestRecovery(true)// 失败后重做
					.build();
			myJob.getJobDataMap().putAll(task.getData());// 保存数据
			mktScheduler.addJob(myJob, true);// 加入任务等待
			// 注册触发器
			String[] expresses = task.getExpresses();
			for (int i = 0; i < expresses.length; i++) {
				if (StringUtil.isNotEmpty(expresses[i])) {
					Trigger trigger = newTrigger().withIdentity(
							CronExpUtil.generateID(task.getTriggerName()),
							task.getTriggerGroup()).withSchedule(
							CronScheduleBuilder.cronSchedule(expresses[i]))
							.forJob(myJob).build(); // 调度任务
					trigger.getJobDataMap().putAll(task.getData());// 保存数据
					mktScheduler.scheduleJob(trigger);// 把新任务和触发器绑定调度
				}
			}
		}
		logger.info("加入调度任务(addJobMoreTrigger:" + task + ")*****end");
		return mktScheduler;
	}

	/**
	 *删除任务
	 */
	@Override
	public Scheduler deleteJob(QuartzTask task) throws SchedulerException {
		Scheduler mktScheduler= (StdScheduler)ac.getBean("gSchedulerFactory");
		if (null != task) {
			mktScheduler.resumeJob(new JobKey(task.getJobName(), task
					.getJobGroup()));// 刪除任务
			mktScheduler.resumeTrigger(new TriggerKey(task.getTriggerName(),
					task.getTriggerGroup()));// 删除触发器
		}
		return mktScheduler;
	}

	/**
	 * 修改任务
	 */
	@Override
	public Scheduler editTigger(QuartzTask task) throws SchedulerException {
		Scheduler mktScheduler= (StdScheduler)ac.getBean("gSchedulerFactory");
		if (null != task) {
			mktScheduler.resumeTrigger(new TriggerKey(task.getTriggerName(),
					task.getTriggerGroup()));// 删除触发器
			@SuppressWarnings("unused")
			Trigger trigger = newTrigger().withIdentity(task.getTriggerName(),
					task.getTriggerGroup()).startAt(new Date()).withSchedule(
					simpleSchedule().withIntervalInSeconds(15).repeatForever())
					.build(); // 调度任务
		}
		return mktScheduler;
	}

	@Override
	public Scheduler deleteGroupJobs(QuartzTask task) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac=ac;
	}

}

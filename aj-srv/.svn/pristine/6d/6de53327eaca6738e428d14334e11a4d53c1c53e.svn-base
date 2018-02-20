package com.frame.task.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.frame.task.vo.QuartzTask;

/**
 * 定时任务控制类
 * 
 * @author yanzelai 2013-4-18
 */
public interface SchduleTaskService {

	/**
	 * 新加入任务(一个触发器)
	 */
	public Scheduler addJobOneTrigger(QuartzTask task)
			throws SchedulerException;

	/**
	 * 新加入任务(多个个触发器)
	 */
	public Scheduler addJobMoreTrigger(QuartzTask task)
			throws SchedulerException;

	/**
	 * 删除任务
	 * 
	 * @return
	 */
	public Scheduler deleteJob(QuartzTask task) throws SchedulerException;

	/**
	 * 修改触发器
	 * 
	 * @return
	 */
	public Scheduler editTigger(QuartzTask task) throws SchedulerException;

	/**
	 *  删除同一个组的任务
	 * @param task
	 * @return
	 * @throws SchedulerException
	 */
	Scheduler deleteGroupJobs(QuartzTask task) throws SchedulerException;
}

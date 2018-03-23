package com.frame.task.service;


public interface ThreadPoolTaskService  {
	/**
	 * 新加入一个任务线程
	 */
	public void addTask(Runnable runnable)throws Exception;
	
	/**
	 * @param closeType
	 * @return false--执行失败，true--执行成功
	 * 完成所有任务线程执行
	 */
	public boolean closeTaskPool();
	
}

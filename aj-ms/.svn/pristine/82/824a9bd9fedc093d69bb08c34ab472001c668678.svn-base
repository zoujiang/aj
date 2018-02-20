/**
 * 
 */
package com.frame.task.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.frame.core.util.SystemConfig;
import com.frame.task.service.ThreadPoolTaskService;

/**
 * 任务调度业务服务类
 * 
 * @author caiwen 2013-7-17
 */
@Service("threadPoolTaskService")
public class ThreadPoolTaskServiceImpl implements ThreadPoolTaskService {
	protected static final Log log = LogFactory.getLog(ThreadPoolTaskServiceImpl.class);

	private ThreadPool threadPool = null;
	
	public ThreadPoolTaskServiceImpl(){
		try {
		int poolNum = Integer.parseInt(SystemConfig.getValue("threadPool.num"));
		 threadPool = new ThreadPool(poolNum);
		 Thread.sleep(poolNum * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("初始化线程池失败" + e.getMessage()); //$NON-NLS-1$
		}
	}
	@Override
	public void addTask(Runnable runnable) throws Exception {
		log.debug("addTask("+ runnable + ") - start"); //$NON-NLS-1$
		 //运行任务   
		 threadPool.execute(runnable);   
		 log.debug("addTask("+ runnable + ") - end"); //$NON-NLS-1$
	}
	@Override
	public boolean closeTaskPool() {
		log.debug("closeTaskPool() - start"); //$NON-NLS-1$
		threadPool.closePool();
		log.info("成功关闭所有线程池...."); //$NON-NLS-1$
		log.debug("closeTaskPool() - end"); //$NON-NLS-1$
		return true;
	}

	

}

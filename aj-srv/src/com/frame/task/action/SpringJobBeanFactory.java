package com.frame.task.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.frame.core.util.DateUtil;

/**
 * 
 * 定时任务Job工厂
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class SpringJobBeanFactory extends SpringBeanJobFactory {

	static final Log log = LogFactory.getLog(SpringJobBeanFactory.class);
	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	/**
	 * 这里我们覆盖了super的createJobInstance方法，对其创建出来的类再进行autowire。
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		log.debug("createJobInstance(" + bundle + ") - start:" + DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2)); //$NON-NLS-1$
		Object jobInstance = super.createJobInstance(bundle);
		beanFactory.autowireBean(jobInstance);
		log.debug("createJobInstance(" + bundle + ") - end:" + DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2)); //$NON-NLS-1$
		return jobInstance;
	}
}
package com.qm.job;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.entities.MessagePublishInfo;
import com.qm.job.thread.SendMsgThread;
import com.qm.job.thread.ThreadPoolFactory;
import com.qm.service.MessagePublishSendLogService;
import com.qm.service.MessagePublishService;

public class MessageSendJob {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessagePublishService messagePublishService;
	@Autowired
	private MessagePublishSendLogService messagePublishSendLogService;

	public void process(){
		
		logger.info("开始短信发送定时任务查询......");
		int currentInt = (int) (System.currentTimeMillis() / 1000);
		List<MessagePublishInfo> list  = messagePublishService.selectNoSendTask(currentInt);
		for(MessagePublishInfo mp : list){
			String[] reciveUsers = mp.getReciveUserTel().split(",");
			Set<String> telSet = new HashSet<String>();
			for(String userTel : reciveUsers){
				logger.info("发送短信：手机号码："+userTel +",内容："+mp.getMsgContent());
				if(!telSet.contains(userTel.trim())){
					telSet.add(userTel.trim());
					ThreadPoolFactory.getInstance().getTheadPool().execute(new SendMsgThread(mp, userTel, messagePublishSendLogService));
				}
			}
			//更新状态
			mp.setStatus(1);
			messagePublishService.update(mp);
		}
		logger.info("结束短信发送定时任务查询......");
		
	}
}

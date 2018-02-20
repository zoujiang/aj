package com.demo.task.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestThread implements Runnable {

	protected Log log = LogFactory.getLog(TestThread.class);
	//private ParticipatePrizeService participatePrizeService;

	@Override
	public void run() {
		long s1 = System.currentTimeMillis();
		//participatePrizeService.exeSendPrize(olGif);
		System.out.println("Hello world end--执行开始时间" + s1 + "执行结束时间"
				+ System.currentTimeMillis() + "花费"
				+ (System.currentTimeMillis() - s1) + "");
	}


}

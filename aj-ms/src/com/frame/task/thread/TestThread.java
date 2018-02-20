package com.frame.task.thread;

public class TestThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 System.out.println("Hello world start");   
			long s1 = System.currentTimeMillis();

		 try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 System.out.println("Hello world end--执行开始时间" + s1 + "执行结束时间" + System.currentTimeMillis() + "花费" + (System.currentTimeMillis() - s1) + "");  
	}

}

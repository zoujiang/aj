package com.qm.job.thread;

	import java.util.concurrent.TimeUnit;

	
	public class ThreadPoolFactory {

	    private static int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
	    private static int maximumPoolSize = 50;
	    private static long keepAliveTime = 10;
	    private static int dequeMaxSize = 10000;


	    private ThreadPoolFactory(){
	    }

	    public static ThreadPoolFactory getInstance()
	    {
	        return LazyThreadPoolFactoryHolder.instance;
	    }

	    private static class LazyThreadPoolFactoryHolder {
	        public static ThreadPoolFactory instance = new ThreadPoolFactory();
	    }

	    public ThreadPool getTheadPool(){
	    		
	    	ThreadPool pool = null;
	            synchronized (ThreadPoolFactory.class)
	            {
	            	pool = new ThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, dequeMaxSize);
	            }

	        return pool;
	    }
	}

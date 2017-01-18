package com.wangzi.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	private static ThreadPoolExecutor threadPool = null;
	// 线程池维护线程的最少数量
	private static int CORE_POOL_SIZE = 10;

	// 线程池维护线程的最大数量
	private static int MAX_POOL_SIZE = 100;

	// 线程池维护线程所允许的空闲时间
	private static int KEEP_ALIVE_TIME = 180;

	// 线程池所使用的缓冲队列大小
	private static int WORK_QUEUE_SIZE = 10000;
	
	private ThreadPool(){}
	public synchronized static ThreadPoolExecutor getInstance(){
		if(threadPool == null){
			threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
					KEEP_ALIVE_TIME, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE),
					new ThreadPoolExecutor.DiscardOldestPolicy());
		}
		return threadPool;
	}
	
}

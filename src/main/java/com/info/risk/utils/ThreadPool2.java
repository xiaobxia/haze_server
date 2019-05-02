package com.info.risk.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 线程池工具类，工程所有多线程均调用此类，控制整个工程允许的最大线程数
 * 
 * @author fanyinchuan
 * 
 */
public class ThreadPool2 {
	public static ThreadPool2 threadPool;
	private ExecutorService executorService = Executors.newFixedThreadPool(30);

	public static ThreadPool2 getInstance() {
		if (threadPool == null) {
			threadPool = new ThreadPool2();
		}
		return threadPool;
	}

	public void run(Runnable r) {
		executorService.execute(r);
	}
}

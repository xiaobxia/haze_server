package com.info.web.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 线程池工具类，工程所有多线程均调用此类，控制整个工程允许的最大线程数
 * 
 * @author zjb
 * 
 */
public class ThreadPool4 {
	public static ThreadPool4 threadPool;
	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	public static ThreadPool4 getInstance() {
		if (threadPool == null) {
			threadPool = new ThreadPool4();
		}
		return threadPool;
	}

	public void run(Runnable r) {
		executorService.execute(r);
	}
}

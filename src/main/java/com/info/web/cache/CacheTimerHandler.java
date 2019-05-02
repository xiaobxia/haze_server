package com.info.web.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 缓存操作类，对缓存进行管理,清除方式采用Timer定时的方式
 * 
 * @author：gaoyuhai
 */
@Slf4j
public class CacheTimerHandler {
	private static final int DEFUALT_VALIDITY_TIME = 30000;// 默认过期时间
	private static final Timer timer;
	private static final SimpleConcurrentMap<String, CacheEntity> map;

	static {
		timer = new Timer();
		map = new SimpleConcurrentMap<String, CacheEntity>(
				new HashMap<String, CacheEntity>(1 << 16));
	}

	/**
	 * 增加缓存对象
	 * 
	 */
	public static void addCache(String key, CacheEntity ce) {
		addCache(key, ce, DEFUALT_VALIDITY_TIME);
		// map.put(key, ce);
	}

	/**
	 * 增加缓存对象
	 * 
	 * @param validityTime
	 *            有效时间 (秒)
	 */
	public static synchronized void addCache(String key, CacheEntity ce,
			int validityTime) {
		map.put(key, ce);
		// 添加过期定时
		timer.schedule(new TimeoutTimerTask(key), validityTime * 1000);
	}

	/**
	 * 获取缓存对象
	 */
	public static synchronized CacheEntity getCache(String key) {
		return map.get(key);
	}

	/**
	 * 检查是否含有制定key的缓冲
	 */
	public static synchronized boolean isConcurrent(String key) {
		return map.containsKey(key);
	}

	/**
	 * 删除缓存
	 */
	public static synchronized void removeCache(String key) {
		map.remove(key);
	}

	/**
	 * 获取缓存大小
	 * 
	 */
	public static int getCacheSize() {
		return map.size();
	}

	/**
	 * 清除全部缓存
	 */
	public static synchronized void clearCache() {
		if (null != timer) {
			timer.cancel();
		}
		map.clear();
		System.out.println("clear cache");
	}

	/**
	 * 清除超时缓存定时服务类
	 */
	static class TimeoutTimerTask extends TimerTask {
		private String ceKey;

		public TimeoutTimerTask(String key) {
			this.ceKey = key;
		}

		@Override
		public void run() {
			CacheTimerHandler.removeCache(ceKey);
			log.info("------------------------remove:{}", ceKey);
		}
	}
}

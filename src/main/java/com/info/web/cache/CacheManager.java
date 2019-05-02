package com.info.web.cache;

/**
 * 缓存管理类
 * 
 * @author gaoyuhai
 * 
 */
public class CacheManager {

	private static CacheManager instance;

	private static CacheTimerHandler cacheTimerHandler;

	private CacheManager() {
	}

	public static synchronized CacheManager getInstance() {
		if (null == instance) {
			instance = new CacheManager();
		}
		return instance;
	}

	/**
	 * 存放规则
	 * 
	 * @return
	 */
	public CacheTimerHandler getCacheTimerHandler() {
		if (null == cacheTimerHandler) {
			return new CacheTimerHandler();
		}
		return cacheTimerHandler;
	}
}

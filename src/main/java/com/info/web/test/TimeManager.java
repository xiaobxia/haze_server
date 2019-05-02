package com.info.web.test;

public class TimeManager {

	private static TimeManager instance;
	private static int times;

	private TimeManager() {
	}

	public static synchronized TimeManager getInstance() {
		if (null == instance) {
			instance = new TimeManager();
		}
		return instance;
	}

	public int getTimes() {
		if (times == 0) {
			return 0;
		}
		return times;
	}
}

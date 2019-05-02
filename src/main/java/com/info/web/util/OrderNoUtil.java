package com.info.web.util;

import java.util.UUID;

public class OrderNoUtil {
	public static OrderNoUtil orderNoUtil;

	public static OrderNoUtil getInstance() {
		if (orderNoUtil == null) {
			orderNoUtil = new OrderNoUtil();
		}
		return orderNoUtil;
	}

	/**
	 * 获得UUID
	 * 
	 * @return
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}


	/**
	 * 获得UUID 前16位
	 *
	 * @return
	 */
	public String getUUID16() {
		return UUID.randomUUID().toString().replaceAll("-","").substring(0,16);
	}
}

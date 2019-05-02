package com.info.web.service;

import com.info.web.pojo.PublicAbout;

public interface IPublicAboutService {
	
	/**
	 * 新增关于
	 */
	public int searchPublicAbout(PublicAbout about);
	
	/**
	 * 查询关于
	 */
	public PublicAbout selectPublicAbout();
	
	/**
	 * 修改关于
	 */
	public int updatePublicAbout(PublicAbout about);
}

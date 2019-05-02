package com.info.web.dao;

import com.info.web.pojo.PublicAbout;

public interface IPublicAboutDao {
	
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

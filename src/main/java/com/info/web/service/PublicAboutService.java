package com.info.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IPublicAboutDao;
import com.info.web.pojo.PublicAbout;

@Service
public class PublicAboutService implements IPublicAboutService {
	
	@Autowired
	private IPublicAboutDao publicAboutDao;
	
	/**
	 * 新增关于
	 */
	public int searchPublicAbout(PublicAbout about){
		return this.publicAboutDao.searchPublicAbout(about);
	}
	
	/**
	 * 查询关于
	 */
	public PublicAbout selectPublicAbout(){
		return this.publicAboutDao.selectPublicAbout();
	}
	
	/**
	 * 修改关于
	 */
	public int updatePublicAbout(PublicAbout about){
		return this.publicAboutDao.updatePublicAbout(about);
	}

}

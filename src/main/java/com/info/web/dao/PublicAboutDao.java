package com.info.web.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.PublicAbout;

@Repository
public class PublicAboutDao extends BaseDao implements IPublicAboutDao {

	/**
	 * 新增关于
	 */
	public int searchPublicAbout(PublicAbout about){
		return this.getSqlSessionTemplate().insert("searchPublicAbout", about);
	}
	
	/**
	 * 查询关于
	 */
	public PublicAbout selectPublicAbout(){
		return this.getSqlSessionTemplate().selectOne("selectPublicAbout");
	}
	
	/**
	 * 修改关于
	 */
	public int updatePublicAbout(PublicAbout about){
		return this.getSqlSessionTemplate().update("updatePublicAbout", about);
	}

}

package com.info.web.dao;

import java.util.HashMap;

import com.info.web.pojo.PlatfromAdvise;

public interface IPlatfromAdviseDao {
	
	/**
	 * 新增用户反馈内容
	 */
	public int searchPlatfromAdvise(PlatfromAdvise plat);
	
	/**
	 * 根据id查询单个
	 */
	public PlatfromAdvise selectPlatfromAdviseById(HashMap<String, Object> params);
	
	/**
	 * 修改反馈内容
	 */
	public int updatePlatfromAdvise(PlatfromAdvise plat);
}

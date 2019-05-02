package com.info.web.service;

import java.util.HashMap;

import com.info.web.pojo.PlatfromAdvise;
import com.info.web.util.PageConfig;

public interface IPlatfromAdviseService {
	
	/**
	 * 新增用户反馈内容
	 */
	public int searchPlatfromAdvise(PlatfromAdvise plat);
	
	/**
	 * 多条件 查询反馈内容
	 */
	public PageConfig<PlatfromAdvise> selectPlatfromAdvise(HashMap<String, Object> params);
	
	/**
	 * 根据id查询单个
	 */
	public PlatfromAdvise selectPlatfromAdviseById(HashMap<String, Object> params);
	
	/**
	 * 修改反馈内容
	 */
	public int updatePlatfromAdvise(PlatfromAdvise plat);
}

package com.info.web.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IPlatfromAdviseDao;
import com.info.web.pojo.PlatfromAdvise;
import com.info.web.util.PageConfig;

@Service
public class PlatfromAdviseService implements IPlatfromAdviseService {
	
	@Autowired
	private IPlatfromAdviseDao platfromAdviseDao;
	@Autowired
	private IPaginationDao paginationDao;
	
	/**
	 * 新增用户反馈内容
	 */
	public int searchPlatfromAdvise(PlatfromAdvise plat){
		return this.platfromAdviseDao.searchPlatfromAdvise(plat);
	}
	
	/**
	 * 多条件 查询反馈内容
	 */
	@SuppressWarnings("unchecked")
	public PageConfig<PlatfromAdvise> selectPlatfromAdvise(HashMap<String, Object> params){
		params.put(Constant.NAME_SPACE, "PlatfromAdvise");
		return this.paginationDao.findPage("selectPlatfromAdvise", "selectPlatfromAdviseCount", params,"web");
	}
	
	/**
	 * 根据id查询单个
	 */
	public PlatfromAdvise selectPlatfromAdviseById(HashMap<String, Object> params){
		return this.platfromAdviseDao.selectPlatfromAdviseById(params);
	}
	
	/**
	 * 修改反馈内容
	 */
	public int updatePlatfromAdvise(PlatfromAdvise plat){
		return this.platfromAdviseDao.updatePlatfromAdvise(plat);
	}
}

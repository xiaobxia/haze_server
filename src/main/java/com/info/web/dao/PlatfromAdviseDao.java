package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.PlatfromAdvise;

@Repository
public class PlatfromAdviseDao extends BaseDao implements IPlatfromAdviseDao {

	/**
	 * 新增用户反馈内容
	 */
	public int searchPlatfromAdvise(PlatfromAdvise plat){
		return this.getSqlSessionTemplate().insert("searchPlatfromAdvise", plat);
	}

	/**
	 * 根据id查询单个
	 */
	public PlatfromAdvise selectPlatfromAdviseById(HashMap<String, Object> params){
		return this.getSqlSessionTemplate().selectOne("selectPlatfromAdviseById", params);
	}
	
	/**
	 * 修改反馈内容
	 */
	public int updatePlatfromAdvise(PlatfromAdvise plat){
		return this.getSqlSessionTemplate().update("updatePlatfromAdvise", plat);
	}
}

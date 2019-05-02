package com.info.web.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserLoginLog;

@Repository
public class UserLoginLogDao extends BaseDao implements IUserLoginLogDao {
	
	/***
	 * 查询
	 * @param params
	 * @return
	 */
	@Override
	public UserLoginLog selectByParams(Map<String, String> params) {
		return getSqlSessionTemplate().selectOne("selectByParams", params);
	}
	
	/****
	 * 新增
	 * @param log
	 */
	@Override
	public void saveUserLoginLog(UserLoginLog log) {
		getSqlSessionTemplate().insert("saveUserLoginLog", log);
	}
	
	/***
	 * 修改
	 */
	public void updateUserLoginLog(UserLoginLog log){
		this.getSqlSessionTemplate().update("updateUserLoginLog", log);
	}
}

package com.info.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IUserLoginLogDao;
import com.info.web.pojo.UserLoginLog;

@Service
public class UserLoginLogService implements IUserLoginLogService {
	
	@Autowired
	private IUserLoginLogDao userLoginLogDao;
	
	/***
	 * 查询
	 */
	@Override
	public UserLoginLog selectByParams(Map<String, String> params) {
		return this.userLoginLogDao.selectByParams(params);
	}
	
	/****
	 * 新增
	 */
	@Override
	public void saveUserLoginLog(UserLoginLog log) {
		this.userLoginLogDao.saveUserLoginLog(log);
	}
	
	/***
	 * 修改
	 */
	public void updateUserLoginLog(UserLoginLog log){
		this.userLoginLogDao.updateUserLoginLog(log);
	}
}

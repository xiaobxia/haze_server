package com.info.web.service;

import java.util.List;

import com.info.web.pojo.UserAppSoftware;

public interface IUserAppSoftwareService {
	/**
	 * 查询用户手机里的应用软件
	 */
	public int selectUserAppSoftwareCount(Integer userId);
	
	/**
	 * 添加用户手机里的应用软件入库
	 */
	public void saveUserAppSoftware(List<UserAppSoftware> soft);
	/**
	 * 根据用户id删除此用户的所有应用软件
	 */
	public void delUserAppSoftware(Integer userId);
}

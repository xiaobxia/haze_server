package com.info.web.service;

import java.util.HashMap;

import com.info.web.pojo.User;
import com.info.web.util.PageConfig;

public interface IUserH5service {
	/**
	 * 查询H5注册待实名认证审核用户
	 * @param params
	 * @return
	 */
	public PageConfig<User> getUserAuditPage(HashMap<String, Object> params);
	/**
	 * 实名 认证审核
	 * @param usr
	 * @return
	 */
	public int updateUserRemalStatus(User usr);
	
}

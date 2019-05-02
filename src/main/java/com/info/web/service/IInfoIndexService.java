package com.info.web.service;

import java.util.HashMap;

import com.info.web.pojo.InfoNotice;
import com.info.web.pojo.UserInfoOld;

public interface IInfoIndexService {
	/**
	 * 更新用户额度
	 * @param map
	 */
	public void changeUserAmount(HashMap<String, Object> map);

	/**
	 * 用户信息认证
	 * @param map
	 */
	public void authMobile(HashMap<String, Object> map);

	/**
	 * 借款信息
	 * @param map
	 */
    public void saveInfoNotice(HashMap<String, Object> map);
}

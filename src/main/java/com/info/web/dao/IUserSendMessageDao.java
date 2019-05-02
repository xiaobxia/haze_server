package com.info.web.dao;

import java.util.Map;

import com.info.web.pojo.UserSendMessage;

public interface IUserSendMessageDao {
	/**
	 * 新增短信
	 */
	public int saveUserSendMsg(UserSendMessage msg);
	
	/**
	 * 查询近期发送的短信有没有超过一分钟
	 */
	public UserSendMessage lastMsg(Map<String, String> queryParams);
}

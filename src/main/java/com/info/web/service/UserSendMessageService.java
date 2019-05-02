package com.info.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IUserSendMessageDao;
import com.info.web.pojo.UserSendMessage;
@Service
public class UserSendMessageService implements IUserSendMessageService {
	
	@Autowired
	private IUserSendMessageDao userSendMessageDao;
	
	/**
	 * 新增短信
	 */
	public int saveUserSendMsg(UserSendMessage msg){
		return this.userSendMessageDao.saveUserSendMsg(msg);
	}
	
	/**
	 * 查询近期发送的短信有没有超过一分钟
	 */
	public UserSendMessage lastMsg(Map<String, String> queryParams){
		return this.userSendMessageDao.lastMsg(queryParams);
	}
	
}

package com.info.web.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserSendMessage;
@Repository
public class UserSendMessageDao extends BaseDao implements IUserSendMessageDao {
	
	/**
	 * 新增短信
	 */
	public int saveUserSendMsg(UserSendMessage msg){
		return this.getSqlSessionTemplate().insert("saveUserSendMsg", msg);
	}
	
	/**
	 * 查询近期发送的短信有没有超过一分钟
	 */
	public UserSendMessage lastMsg(Map<String, String> queryParams){
		return this.getSqlSessionTemplate().selectOne("lastMsg", queryParams);
	}
}

package com.info.web.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.ObtainUserShortMessage;
@Repository
public class ObtainUserShortMessageDao extends BaseDao implements IUserAppSoftwareDao {
	
	/**
	 * 查询用户手机里的短信多少
	 */
	public int selectObtainUserShortMessageCount(Integer userId){
		return this.getSqlSessionTemplate().selectOne("selectObtainUserShortMessage", userId);
	}
	
	/**
	 * 添加用户手机里的短信入库
	 */
	public void saveObtainUserShortMessage(ObtainUserShortMessage soft){
		this.getSqlSessionTemplate().insert("saveObtainUserShortMessage", soft);
	}
	
	/**
	 * 根据用户id删除此用户的所有短信
	 */
	public void delObtainUserShortMessage(Integer userId){
		this.getSqlSessionTemplate().delete("delObtainUserShortMessage", userId);
	}
}

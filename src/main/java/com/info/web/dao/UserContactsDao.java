package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserContacts;

@Repository
public class UserContactsDao extends BaseDao implements IUserContactsDao {

	/**
	 * 查询是否用户是否已经添加过此手机号码
	 */
	public int selectByIdorPhone(HashMap<String,Object> map){
		return this.getSqlSessionTemplate().selectOne("selectByIdorPhone", map);
	}
	
	/**
	 * 查询用户通讯录有多少联系人
	 */
	public int selectUserContactCount(Integer userId){
		return this.getSqlSessionTemplate().selectOne("selectUserContactCount", userId);
	}
	
	/**
	 * 新增联系人
	 */
	public void saveUserContacts(UserContacts contacts){
		this.getSqlSessionTemplate().insert("saveUserContacts", contacts);
	}
	
	/**
	 *  根据用户id删除此用户的所有联系人 
	 */
	public void delUserContact(Integer userId){
		this.getSqlSessionTemplate().delete("delUserContact", userId);
	}


	/**
	 * 查询
	 */
	public List<UserContacts> selectUserContacts(Map<String, Object> params){
		return  this.getSqlSessionTemplate().selectList("selectUserContactsPage", params);
	}
}

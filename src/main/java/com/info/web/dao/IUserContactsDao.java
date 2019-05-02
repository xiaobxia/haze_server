package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.UserContacts;

public interface IUserContactsDao {
	
	/**
	 * 查询是否用户是否已经添加过此手机号码
	 */
	public int selectByIdorPhone(HashMap<String, Object> map);
	
	/**
	 * 查询用户通讯录有多少联系人
	 */
	public int selectUserContactCount(Integer userId);
	
	/**
	 * 新增联系人
	 */
	public void saveUserContacts(UserContacts contacts);
	
	/**
	 *  根据用户id删除此用户的所有联系人 
	 */
	public void delUserContact(Integer userId);

	/**
	 * 查询联系人信息
	 * @param params
	 * @return
     */
	public List<UserContacts> selectUserContacts(Map<String, Object> params);
}

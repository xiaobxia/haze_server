package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserContactsDao;
import com.info.web.pojo.UserContacts;
import com.info.web.util.PageConfig;

@Service
public class UserContactsService implements IUserContactsService {

	@Autowired
	private IUserContactsDao userContactsDao;
	@Autowired
	private IPaginationDao paginationDao;
	/**
	 * 查询是否用户是否已经添加过此手机号码
	 */
	public int selectByIdorPhone(HashMap<String,Object> map){
		return this.userContactsDao.selectByIdorPhone(map);
	}
	
	/**
	 * 查询用户通讯录有多少联系人
	 */
	public int selectUserContactCount(Integer userId){
		return this.userContactsDao.selectUserContactCount(userId);
	}
	
	/**
	 * 新增联系人
	 */
	public void saveUserContacts(List<UserContacts> contacts){
		for (int i = 0; i < contacts.size(); i++) {
			 this.userContactsDao.saveUserContacts(contacts.get(i));
		}
	}
	
	/**
	 *  根据用户id删除此用户的所有联系人 
	 */
	public void delUserContact(Integer userId){
		this.userContactsDao.delUserContact(userId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<UserContacts> selectUserContactsList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "UserContacts");
		return paginationDao.findPage("selectUserContactsPage", "selectUserContactsCount", params,"web");
	}

	@Override
	public List<UserContacts> selectUserContacts(Map<String, Object> params){
		return this.userContactsDao.selectUserContacts(params);
	}
}

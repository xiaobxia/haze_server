package com.info.web.dao;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserAppSoftware;
@Repository
public class UserAppSoftwareDao extends BaseDao implements IUserAppSoftwareDao {
	
	/**
	 * 查询用户手机里的应用软件
	 */
	public int selectUserAppSoftwareCount(Integer userId){
		return this.getSqlSessionTemplate().selectOne("selectUserAppSoftwareCount", userId);
	}
	
	/**
	 * 添加用户手机里的应用软件入库
	 */
	public void saveUserAppSoftware(UserAppSoftware soft){
		this.getSqlSessionTemplate().insert("saveUserAppSoftware", soft);
	}
	
	/**
	 * 根据用户id删除此用户的所有应用软件
	 */
	public void delUserAppSoftware(Integer userId){
		this.getSqlSessionTemplate().delete("delUserAppSoftware", userId);
	}
}

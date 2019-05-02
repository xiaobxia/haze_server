package com.info.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserInfoImage;

@Repository
public class UserInfoImageDao extends BaseDao implements IUserInfoImageDao {

	/**
	 * 上传图片
	 */
	public int saveUserImage(UserInfoImage img){
		return this.getSqlSessionTemplate().insert("user_info_image.saveUserImage", img);
	}
	
	/**
	 * 查询图片
	 */
	public List<UserInfoImage> selectImageByUserId(int uid){
		return this.getSqlSessionTemplate().selectList("selectImageByUserId", uid);
	}
	
	/**
	 * 修改图片
	 */
	public int updateUserImageStatus(int id){
		return this.getSqlSessionTemplate().update("updateUserImageStatus", id);
	}
}

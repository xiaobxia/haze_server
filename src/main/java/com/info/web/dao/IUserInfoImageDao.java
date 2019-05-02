package com.info.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.UserInfoImage;

public interface IUserInfoImageDao {
	
	/**
	 * 上传图片
	 */
	public int saveUserImage(UserInfoImage img);
	
	/**
	 * 查询图片
	 */
	public List<UserInfoImage> selectImageByUserId(int uid);
	
	/**
	 * 修改图片
	 */
	public int updateUserImageStatus(int id);
}

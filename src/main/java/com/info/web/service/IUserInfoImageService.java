package com.info.web.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.info.web.pojo.UserInfoImage;

public interface IUserInfoImageService {
	
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

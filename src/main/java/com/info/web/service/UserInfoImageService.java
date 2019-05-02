package com.info.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IUserInfoImageDao;
import com.info.web.pojo.UserInfoImage;

@Service
public class UserInfoImageService implements IUserInfoImageService {

	@Autowired
	private IUserInfoImageDao userInfoImageDao;
	/**
	 * 上传图片
	 */
	public int saveUserImage(UserInfoImage img){
		return this.userInfoImageDao.saveUserImage(img);
	}
	
	/**
	 * 查询图片
	 */
	public List<UserInfoImage> selectImageByUserId(int uid){
		return this.userInfoImageDao.selectImageByUserId(uid);
	}
	
	/**
	 * 修改图片
	 */
	public int updateUserImageStatus(int id){
		return this.userInfoImageDao.updateUserImageStatus(id);
	}
}

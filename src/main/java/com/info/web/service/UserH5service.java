package com.info.web.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserDao;
import com.info.web.pojo.User;
import com.info.web.util.PageConfig;
@Service
public class UserH5service implements IUserH5service{
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPaginationDao paginationDao;
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<User> getUserAuditPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.findPage("selectUserAuditPage", "selectUserAuditCount", params,"web");
	}

	@Override
	public int updateUserRemalStatus(User usr) {
		return userDao.updateByPrimaryKeyUser(usr);
	}

}

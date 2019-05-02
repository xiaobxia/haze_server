package com.info.web.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserOrderDao;
import com.info.web.pojo.UserOrder;
import com.info.web.util.PageConfig;


@Service
public class UserOrderService implements IUserOrderService {

	@Autowired
	private IUserOrderDao userOrderDao;
	@Autowired
	private IPaginationDao paginationDao;
	

	@SuppressWarnings("unchecked")
	public PageConfig<UserOrder> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "UserOrder");
		return paginationDao.findPage("findAll", "findAllCount", params, "web");
	}

	@Override
	public int insert(UserOrder userOrder) {
		return 0;
	}

	@Override
	public int delete(UserOrder userOrder) {
		return 0;
	}

	@Override
	public int update(UserOrder userOrder) {
		return 0;
	}


	@Override
	public UserOrder findById(Integer id) {
		return userOrderDao.findById(id);
	}

	@Override
	public UserOrder findBankById(Integer id) {
		return  userOrderDao.findById(id);
	}

}

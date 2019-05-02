package com.info.web.service;

import java.util.HashMap;

import com.info.web.pojo.UserOrder;
import com.info.web.util.PageConfig;

public interface IUserOrderService {
	

	/**
	 * 插入
	 * 
	 * @param 
	 * @return
	 */
	public int insert(UserOrder userOrder);

	/**
	 * 删除
	 * 
	 * @return
	 */
	public int delete(UserOrder userOrder);

	/**
	 * 修改
	 * 
	 * @return
	 */
	public int update(UserOrder userOrder);


	/**
	 * 根据主键查询
	 * 
	 * @param id主键
	 * @return
	 */
	public UserOrder findById(Integer id);
	
	/**
	 * 前台查看我的借款列表分页
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<UserOrder> findPage(HashMap<String, Object> params);
	
	/**
	 * 根据借款信息查询银行卡
	 * 
	 * @param id主键
	 * @return
	 */
	public UserOrder findBankById(Integer id);

	
}

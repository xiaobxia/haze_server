package com.info.web.dao;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.UserOrder;


/**
 * 用户中心借款列表
 * 
 * 2016年12月9日 16:21:04
 * @param <T>
 */
@Repository
public interface IUserOrderDao  {
	
	/**
	 * 插入
	 * 
	 * @param zbNews
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
	 * 获取用户银行卡信息
	 * 
	 * @param id主键
	 * @return
	 */
	public UserOrder findBankById(Integer id);
 
 
	
}

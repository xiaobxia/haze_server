package com.info.web.dao;

import com.info.web.pojo.OutOrders;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IOutOrdersDao {
	/**
	 * 发出请求
	 * 
	 * @param orders
	 * @return
	 */
	public int insert(OutOrders orders);

	/**
	 * 更新
	 * 
	 * @param orders
	 * @return
	 */
	public int update(OutOrders orders);

	/**
	 * 更新
	 *
	 * @param orders
	 * @return
	 */
	public int updateByOrderNo(OutOrders orders);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public OutOrders findById(Integer id);

	/**
	 * 查询
	 *
	 * @param rderNo
	 * @return
	 */
	public OutOrders findByOrderNo(String orderNo);
	
	
	/**
	 * 通过表名发出请求
	 * 
	 * @param orders
	 * @return
	 */
	public int insertByTablelastName(OutOrders orders);
	/**
	 * 通过表名更新
	 * @param orders
	 * @param TablelastName
	 * @return
	 */
	public int updateByTablelastName(OutOrders orders);
	
	/**
	 * 更新
	 *
	 * @param orders
	 * @return
	 */
	public int updateByOrderNoByTablelastName(OutOrders orders);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public OutOrders findByIdByTablelastName(@Param("id") Integer id, @Param("TablelastName") String TablelastName);
	/**
	 * 查询
	 *
	 * @param rderNo
	 * @return
	 */
	public OutOrders findByOrderNoByTablelastName(@Param("orderNo") String orderNo, @Param("TablelastName") String TablelastName);
}

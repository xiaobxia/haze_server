package com.info.risk.service;

import com.info.risk.pojo.RiskOrders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IRiskOrdersService {
	/**
	 * 发出请求
	 * 
	 * @param zbNews
	 * @return
	 */
	public int insert(RiskOrders orders);

	/**
	 * 更新
	 * 
	 * @param Integer
	 * @return
	 */
	public int update(RiskOrders orders);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public RiskOrders findById(Integer id);


	/**
	 * 查询淘宝认证被拒的用户
	 * @param parms
	 * @return
	 */
	public List<RiskOrders> selectAfterTbReport(HashMap<String, Object> parms);


	public Integer getTbRefuseCount();

	public Map<String,Map<String,Integer>> riskMessageStatistics(HashMap<String, Object> parms);
}

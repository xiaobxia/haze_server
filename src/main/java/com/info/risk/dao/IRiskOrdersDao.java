package com.info.risk.dao;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskOrders;

import java.util.HashMap;
import java.util.List;

@Repository
public interface IRiskOrdersDao {
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

	public int findTypeCount(RiskOrders riskOrders);
	/**
	 * 查询宜信报告
	 * @param params
	 * @return
	 */
	public RiskOrders findYxReturnData(HashMap<String, Object> params);

	/**
	 * 根据订单id查询 creditReport信息
	 * @param parms
	 * @return
	 */
	public RiskOrders selectCreditReportByBorrowId(HashMap<String, Object> parms);

	public int insertCreditReport(RiskOrders riskOrders);

	public List<RiskOrders> selectAfterTbReport(HashMap<String, Object> parms);

	public List<RiskOrders> selectRiskOrdersByStartTimeAndEndTime(HashMap<String, Object> params);

	public Integer selectRiskOrdersByStartTimeAndEndTimeCount(HashMap<String, Object> params);

}

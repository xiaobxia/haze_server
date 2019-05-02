package com.info.back.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.PlatformReport;
import com.info.web.util.PageConfig;

/**
 * 注册 人数统计
 * 
 * @author Administrator
 * 
 */
public interface IBackStatisticService {

	/**
	 * 注册统计
	 */
	public Map<String, Long> registerStatistic();

	/**
	 * 认证统计
	 * 
	 * @return
	 */
	Map<String, Long> findAllApprove(String type);

	/**
	 * 放款统计
	 */
	public Map<String, Long> applyBorrowCountStatistic();

	/**
	 * 放款成功统计
	 */
	public Map<String, Object> sendMoneyStatistic();

	/**
	 * 放款中统计
	 */
	public Map<String, Object> findLaon();

	/**
	 * 机审订单总数及通过数
	 * 
	 * @return
	 */
	public Map<String, Object> findRiskOrders();

	Map<String, Object> findRiskUser();

	Map<String, Object> findRiskOrdersToday();

	public void updateReport(Calendar begin, Calendar end);
	/**
	 * 分页
	 */
	PageConfig<PlatformReport> findPlatformPage(HashMap<String, Object> params);
	
	/**
	 * 分页
	 */
	public Integer findPlatformCount(HashMap<String, Object> params);
	/**
	 * 当天新、老用户通过率
	 * @return
	 */
	Map<String, Object> findOldToday(String customerType);
}

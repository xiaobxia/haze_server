package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ISendMoneyStatisticDao {

	public Map<String, Object> sendMoneyCount();

	public Map<String, Object> sendMoneyCountToday();

	public Map<String, Object> findLoaning();

	public Map<String, Object> findLoanFail();

	public List<Integer> findRiskOrders();

	public List<Integer> findRiskUser();
	public List<Integer> findRiskOrdersToday();
	public List<Integer> findDjsOrdersToday();
	public List<Integer> findOldToday(String customerType);
	Map<String,Object> findAllPendingRepayMoney(String number);
	Map<String,Object> findTodayMoneyCount(String status);
	Integer applyCountToday();
}

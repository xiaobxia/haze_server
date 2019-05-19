package com.info.back.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISendMoneyStatisticDao {

	public Map<String, Object> sendMoneyCount();

	public Map<String, Object> sendMoneyCountToday();

	public Map<String,Object> loanMoneyToday();

	public Map<String, Object> findLoaning();

	public Map<String, Object> findLoanFail();

	public List<Integer> findRiskOrders();

	public List<Integer> findRiskUser();
	public List<Integer> findRiskOrdersToday();
	public List<Integer> findDjsOrdersToday();
	public List<Integer> findOldToday(String customerType);
	Map<String,Object> findAllPendingRepayMoney(Integer param);
	Map<String,Object> findTodayMoneyCount(Integer status);
	Integer applyCountToday();
	Integer applyUserCount();
	BigDecimal lateMoney(@Param("fristNumber") Integer fristNumber, @Param("endNumbers") Integer endNumbers);
    BigDecimal threeMoney();
    Map<String,Object> extendToday();
    Map<String,Object> reBorrow();
	Map<String,Object> findMoneyToday();
}

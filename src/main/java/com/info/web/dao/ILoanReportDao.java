package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.LoanReport;

public interface ILoanReportDao {
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<LoanReport> findAll(Map<String, Object> params);
	
	public int findAllCount(Map<String, Object> params);
	
	public int findRegistCount(Map<String, Object> params);
	public int findBorrowApplyCount(Map<String, Object> params);
	public int findBorrowSucCount(Map<String, Object> params);
	
	/**
	 * 根据条件查询放款总额
	 * @param params
	 * @return
	 */
	public Long findAllMoneySum(Map<String, Object> params);
	
	/**
	 * 根据条件查询放款总额
	 * @param params
	 * @return
	 */
	public Integer findAllLoanSum(Map<String, Object> params);
	

	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(LoanReport loanReport);

	/**
	 * 根据日期删除对象
	 * 
	 * @param data
	 */
	public int deleteByLoanReport(LoanReport loanReport);
	
	/**
	 * 7天或14天,老用户或新用户得到当天放款总额
	 * @param param
	 * @return
	 */
	public Long findMoneyCount(Map<String, Object> param);
	/**
	 * 7天或14天,老用户或新用户得到当天放款单数
	 * @param param
	 * @return
	 */
	public int findLoanCount(Map<String, Object> param);

	public int findReportDate(Map<String, Object> param);
	
	/**
	 * (续期)7天或14天,老用户(只要续期都是老用户)得到当天放款总单数
	 * @param param
	 * @return
	 */
	public int findRenewalLoanCount(Map<String, Object> param);
	/**
	 * (续期)7天或14天,老用户(只要续期都是老用户)得到当天放款总额
	 * @param param
	 * @return
	 */
	public Long findRenewalMoneyCount(Map<String, Object> param);


	//public List<Map<String, Object>> findLoanMoneyCount(Map<String, Object> param);
	

}

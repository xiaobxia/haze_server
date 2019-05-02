package com.info.web.dao;

 
import java.util.Map;

import com.info.web.pojo.LoanMoneyReport;
import com.info.web.pojo.LoanReport;
import org.springframework.stereotype.Repository;

@Repository("loanMoneyReportDao")
public interface ILoanMoneyReportDao {
	 
	 
	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(LoanMoneyReport loanMoneyReport);

	/**
	 * 根据日期删除对象
	 * 
	 * @param data
	 */
	public int deleteByLoanReport(LoanMoneyReport loanMoneyReport);
	
	/**
	 * 7天或14天,老用户或新用户得到当天放款总额
	 * @param param
	 * @return
	 */
	public Long findMoneyCount(Map<String, Object> param);
	 

	public int findReportDate(Map<String, Object> param);
	
	 
	/**
	 * (续期)7天或14天,老用户(只要续期都是老用户)得到当天放款总额
	 * @param param
	 * @return
	 */
	public Long findRenewalMoneyCount(Map<String, Object> param);


	
	/**
	 * 查询到期金额
	 * @param param
	 * @return
	 */
	public Long findExpireAmount(Map<String, Object> param);
	
	/**
	 * 查询逾期金额
	 * @param param
	 * @return
	 */
	public Long findOverdueAmount(Map<String, Object> param);
	
	
	/**
	 * 查询7或14逾期金额
	 * @param param
	 * @return
	 */
	public Long findOverdueRateAmount(Map<String, Object> param);


 
	

}

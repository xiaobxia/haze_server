package com.info.web.service;

import java.util.HashMap;




import com.info.web.pojo.LoanReport;
import com.info.web.util.PageConfig;


public interface ILoanReportService {
	
	/**
	 * 统计每日数据
	 * 
	 * @return
	 */
	public boolean saveLoanReport(String nowTime);
	
	/**
	 * 分页
	 */
	PageConfig<LoanReport> findPage(HashMap<String, Object> params);

	Long findLoanMoneySum(HashMap<String, Object> params);
	
	Integer findLoanSum(HashMap<String, Object> params);
}

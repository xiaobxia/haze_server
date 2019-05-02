package com.info.web.service;

import java.util.HashMap;
   
public interface ILoanMoneyReportService {
	
	/**
	 * 统计每日数据
	 * 
	 * @return
	 */
	public boolean saveLoanMoneyReport(String nowTime);
}

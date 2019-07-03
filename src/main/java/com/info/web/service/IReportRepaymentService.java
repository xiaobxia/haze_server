package com.info.web.service;

import com.info.web.pojo.*;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IReportRepaymentService {
	boolean insertSelective(ReportRepayment record);

	boolean updateByPrimaryKeySelective(ReportRepayment record);

	PageConfig<Repayment> findReportRepaymentCount(HashMap<String, Object> params);

	PageConfig<Repayment> findReportRepaymentAmount(HashMap<String, Object> params);

	PageConfig<Repayment> findReportRepaymentAll(HashMap<String, Object> params);

	List<Map<String, Object>> findRenewalByRepaymentReport(Map<String, Object> params);

	Integer findIdByDate(String reportDate);

	Map<String, Object> findAllBorrowByReport(String reportTime);

	Map<String, Object> findAllRenewalBorrowByReport(String reportTime);

	Map<String, Object> findAllRepayByReport(String reportTime);

	Map<String, Object> findAllRenewalRepayByReport(String reportTime);

	Map<String, Object> findAllOverdueByReport(String reportTime);

	Map<String, Object> findAllByReport(String reportTime);

	Map<String, Object> findAllRenewalByReport(String reportTime);

	List<Map<String, Object>> findOverdueRateSByReport(Map<String, Object> params);

	Map<String,Object> findRepayReport(String reportTime);
}

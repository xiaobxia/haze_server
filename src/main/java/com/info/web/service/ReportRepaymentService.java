package com.info.web.service;


import com.info.constant.Constant;
import com.info.web.dao.*;
import com.info.web.pojo.*;
import com.info.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ReportRepaymentService implements IReportRepaymentService {

	@Autowired
	private IReportRepaymentDao reportRepaymentDao;
	@Autowired
	private IPaginationDao paginationDao;

	@Override
	public boolean insertSelective(ReportRepayment record) {
		return reportRepaymentDao.insertSelective(record) > 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(ReportRepayment record) {
		return reportRepaymentDao.updateByPrimaryKeySelective(record) > 0;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Repayment> findReportRepaymentCount(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ReportRepayment");
		return paginationDao.findPage("findReportRepaymentCount", "findParamsCount", params, "web");
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Repayment> findReportRepaymentAmount(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ReportRepayment");
		return paginationDao.findPage("findReportRepaymentAmount", "findParamsCount", params, "web");
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Repayment> findReportRepaymentAll(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ReportRepayment");
		return paginationDao.findPage("findReportRepaymentAll", "findParamsCount", params, "web");
	}

	public List<Map<String, Object>> findRenewalByRepaymentReport(Map<String, Object> params){
		return reportRepaymentDao.findRenewalByRepaymentReport(params);
	}

	@Override
	public Integer findIdByDate(String reportDate){
		return reportRepaymentDao.findIdByDate(reportDate);
	}

	@Override
	public Map<String, Object> findAllBorrowByReport(String reportTime) {
		return reportRepaymentDao.findAllBorrowByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllRenewalBorrowByReport(String reportTime) {
		return reportRepaymentDao.findAllRenewalBorrowByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllRepayByReport(String reportTime) {
		return reportRepaymentDao.findAllRepayByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllRenewalRepayByReport(String reportTime) {
		return reportRepaymentDao.findAllRenewalRepayByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllOverdueByReport(String reportTime) {
		return reportRepaymentDao.findAllOverdueByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllByReport(String reportTime) {
		return reportRepaymentDao.findAllByReport(reportTime);
	}

	@Override
	public Map<String, Object> findAllRenewalByReport(String reportTime){
		return reportRepaymentDao.findAllRenewalByReport(reportTime);
	}

	@Override
	public List<Map<String, Object>> findOverdueRateSByReport(Map<String, Object> params) {
		return reportRepaymentDao.findOverdueRateSByReport(params);
	}


}

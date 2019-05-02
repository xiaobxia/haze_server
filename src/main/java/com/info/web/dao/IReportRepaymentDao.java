package com.info.web.dao;

import com.info.web.pojo.ReportRepayment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("reportRepaymentDao")
public interface IReportRepaymentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportRepayment record);

    int insertSelective(ReportRepayment record);

    ReportRepayment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReportRepayment record);

    Integer findIdByDate(String reportDate);

    List<Map<String, Object>> findRenewalByRepaymentReport(Map<String, Object> params);

    int updateByPrimaryKey(ReportRepayment record);

    Map<String, Object> findAllBorrowByReport(String reportTime);

    Map<String, Object> findAllRenewalBorrowByReport(String reportTime);

    Map<String, Object> findAllRepayByReport(String reportTime);

    Map<String, Object> findAllRenewalRepayByReport(String reportTime);

    Map<String, Object> findAllOverdueByReport(String reportTime);

    Map<String, Object> findAllByReport(String reportTime);

    Map<String, Object> findAllRenewalByReport(String reportTime);

    List<Map<String, Object>> findOverdueRateSByReport(Map<String, Object> params);
}
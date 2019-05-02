package com.info.back.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskBlackUser;
import com.info.web.pojo.PlatformReport;

@Repository
public interface IPlatformReportDao {

	int insert(PlatformReport platformReport);

	int update(String reportDate);

	/**
	 * 某一天的注册用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findRegisterCount(String reportDate);

	/**
	 * 某一天的实名认证用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findRealPersonCount(String reportDate);

	/**
	 * 某一天的实名认证次数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findRealCount(String reportDate);

	/**
	 * 某一天的运营商认证次数(不一定都成功)
	 * 
	 * @param reportDate
	 * @return
	 */
	int findYysCount(String reportDate);

	/**
	 * 某一天的运营商认证成功次数(不一定都成功)
	 * 
	 * @param reportDate
	 * @return
	 */
	int findYysSucCount(String reportDate);

	/**
	 * 某一天的绑卡用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findBankCount(String reportDate);

	/**
	 * 某一天的芝麻用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findZmCount(String reportDate);

	/**
	 * 某一天的支付宝认证数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findAlipayCount(String reportDate);

	/**
	 * 某一天的借款申请总数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findBorrowCount(String reportDate);

	/**
	 * 某一天的通过审核总数和总金额(放款审核通过时间是当天)
	 * 
	 * @param reportDate
	 * @return
	 */
	List<Map<String, Object>> findBorrowSucCountMoney(String reportDate);

	/**
	 * 某一天放款成功的笔数和金额(可能会包括昨天放款失败或者未放款的记录)
	 * 
	 * @param reportDate
	 * @return
	 */
	List<Map<String, Object>> findPaySucCountMoney(String reportDate);

	/**
	 * 某一天应打款但未到账的笔数和金额(放款审核通过时间是当天，状态却是放款中)
	 * 
	 * @param reportDate
	 * @return
	 */
	List<Map<String, Object>> findNoPayCountMoney(String reportDate);

	/**
	 * 某一天应打款但打款失败的笔数和金额(放款审核通过时间是当天，状态却是打款失败,只是统计到当前运算时，可能之后又打款成功)
	 * 
	 * @param reportDate
	 * @return
	 */
	List<Map<String, Object>> findFailPayCountMoney(String reportDate);

	/**
	 * 某一天申请借款的用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findBorrowUserCount(String reportDate);

	/**
	 * 某一天借款申请通过的用户数
	 * 
	 * @param reportDate
	 * @return
	 */
	int findBorrowSucUserCount(String reportDate);

	/**
	 * 当日风控审核订单总数、通过风控的总数、风控推到复审的总数、因为征信失败推到复审的、本地异常推到复审的、规则判定推到复审的
	 * 
	 * @param reportDate
	 * @return
	 */
	List<Integer> findRiskOrdersToday(String reportDate);

	/**
	 * 批量插入黑名单
	 * 
	 * @param list
	 * @return
	 */
	int batchInsert(List<RiskBlackUser> list);

	/**
	 * 批量更新以往黑名单
	 * 
	 * @param list
	 * @return
	 */
	int batchDelete(List<RiskBlackUser> list);

	/**
	 * 获得总记录数
	 * 
	 * @param params
	 *            tbDate过程数据表后缀20160101格式<br>
	 *            reportDate查询的日期2016-01-01格式<br>
	 *            ruleId要查询的规则<br>
	 *            sql动态sql语句<br>
	 *            sql2要查询的字段
	 * @return
	 */
	int findRiskCount(HashMap<String, Object> params);
	/**
	 * 获得导出Excel总记录数
	 */
	int findAllCount(HashMap<String, Object> params);
}
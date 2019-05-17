package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.Repayment;
import com.info.web.pojo.RepaymentChecking;
import com.info.web.pojo.RepaymentDetail;
import com.info.web.util.PageConfig;

/**
 *
 */
public interface IRepaymentDetailService {

	public RepaymentDetail selectByPrimaryKey(Integer id);
	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public boolean deleteByPrimaryKey(Integer id);
	
	/**
	 * 根据订单号查询对象
	 * 
	 * @param orderId
	 */
	public RepaymentDetail selectByOrderId(String orderId);

	/**
	 * 插入 对象
	 * 
	 * @param detail
	 */
	public boolean insert(RepaymentDetail detail);

	/**
	 * 插入 对象
	 *
	 * @param detail
	 */
	public boolean insertSelective(RepaymentDetail detail);

	/**
	 * 更新 象
	 * 
	 * @param detail
	 */
	public boolean updateByPrimaryKey(RepaymentDetail detail);


	/**
	 * 更新 象
	 *
	 * @param detail
	 */
	public boolean updateByPrimaryKeySelective(RepaymentDetail detail);
	
    /**
     * 还款对账
     * @param params
     * @return
     */
	PageConfig<RepaymentChecking> checkRepayment(HashMap<String, Object> params);
	int checkRepaymentCount(HashMap<String, Object> params);
    /**
     * 续期对账列表
     * @param params
     * @return
     */
	PageConfig<RepaymentChecking> checkRenewal(HashMap<String, Object> params);
	int checkRenewalCount(HashMap<String, Object> params);
	
	/**
	 * 还款详情列表
	 */
	PageConfig<RepaymentDetail> repaymentDetilList(HashMap<String, Object> params);
	/**
	 * 查询支付未回调的还款详情
	 * @param params
	 * @return
	 */
	List<RepaymentDetail> queryOrderResult(HashMap<String, Object> params);

	/**
	 * 插入统计数据表
	 * */
	void insertStatisticsDetail(List<Map<String, Object>> list);

	PageConfig<Map<String,Object>> assignStatistics(HashMap<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsByCondition(Map<String, Object> params);
	List<Map<String,Object>> selectAssignStatisticsBackMoney(Map<String,Object> params);

	PageConfig<Map<String,Object>> assignBackMoneyStatistics(HashMap<String,Object> params);

	int assignBackMoneyStatisticsCount(HashMap<String,Object> params);

	Integer findRiskScore(Integer userId);

}

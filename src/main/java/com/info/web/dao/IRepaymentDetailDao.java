package com.info.web.dao;

import com.info.back.pojo.DayRepayFailExcel;
import com.info.web.pojo.RepaymentChecking;
import com.info.web.pojo.RepaymentDetail;
import com.info.web.util.PageConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository("repaymentDetailDao")
public interface IRepaymentDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RepaymentDetail record);

    int insertSelective(RepaymentDetail record);

    RepaymentDetail selectByPrimaryKey(Integer id);
    
    RepaymentDetail selectByOrderId(String orderId);

    int updateByPrimaryKeySelective(RepaymentDetail record);

    int updateByPrimaryKeyWithBLOBs(RepaymentDetail record);

    int updateByPrimaryKey(RepaymentDetail record);

    List<RepaymentDetail> findParams(Map<String, Object> map);
    /**
     * 还款对账
     * @param params
     * @return
     */
    List<RepaymentChecking> checkRepayment(Map<String, Object> params);
    int checkRepaymentCount(Map<String, Object> params);
    /**
     * 续期对账列表
     * @param params
     * @return
     */
    List<RepaymentChecking> checkRenewal(Map<String, Object> params);
    int checkRenewalCount(Map<String, Object> params);
    
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

    void insertStatisticsDetail(@Param("list") List<Map<String, Object>> list);

    List<Map<String,Object>> selectAssignStatisticsByCondition(Map<String, Object> params);

    PageConfig<Map<String,Object>> assignStatistics(HashMap<String, Object> params);

    List<DayRepayFailExcel> getRepayFailRecords(@Param("startTime") String startTime, @Param("endTime") String endTime);

    PageConfig<Map<String,Object>> assignBackMoneyStatistics(HashMap<String,Object> params);

    int assignBackMoneyStatisticsCount(HashMap<String,Object> params);

    List<Map<String,Object>> selectAssignStatisticsBackMoney(Map<String,Object> params);

    List<Map<String,Object>> getRepaymentDetailByUserId(@Param("userId")Integer userId);

    List<Map<String,Object>> getRenewalHistory(@Param("userId")Integer userId);

    Integer findRiskScore(@Param("userId") Integer userId);

}

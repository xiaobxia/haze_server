package com.info.risk.dao;

import com.info.risk.pojo.RiskModelOrder;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author xiefei
 * @date 2018/05/12
 */
public interface IRiskModelOrderDao {
    int insert(RiskModelOrder riskModelOrder);

    RiskModelOrder findById(Integer id);

    RiskModelOrder findOneByParams(Map<String, Object> params);

    /**
     * 统计记录数
     *
     * @param params
     * @return
     */
    int findParamsCount(HashMap<String, Object> params);

    /**
     * 更新复审人员信息
     * @param riskModelOrder
     * @return
     */
    int updateRiskModelOrder(RiskModelOrder riskModelOrder);

    /**
     * 查询所有的评分卡编号
     *
     * @return
     */
    List<String> findAllScoreCard();

    /**
     * 根据借款订单信息查询部分风控订单信息
     *
     * @param borrowOrderId
     * @return
     */
    RiskModelOrder selectRiskOrderInfoBybrdId(Integer borrowOrderId);
}

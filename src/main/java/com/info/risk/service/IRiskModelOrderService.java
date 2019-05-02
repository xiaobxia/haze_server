package com.info.risk.service;

import com.info.risk.pojo.RiskModelOrder;
import com.info.web.pojo.statistics.Overdue;
import com.info.web.util.PageConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tl on 2018/5/2.
 */
public interface IRiskModelOrderService {

    /**
     * 统计记录数
     *
     * @param params
     * @return
     */
    int findParamsCount(HashMap<String, Object> params);

    RiskModelOrder findOneByParams(Map<String, Object> params);

    /**
     * 更新人工复审信息
     *
     * @param borrowOrderId
     * @param borrowOrderStatus
     * @param personReviewAccount
     * @param personReviewRemark
     * @return
     */
    int updateRiskModelOrder(Integer borrowOrderId, Integer borrowOrderStatus, String personReviewAccount,
                             String personReviewRemark, Date personReviewTime);

    /**
     * 查询所有的评分卡编号-不重复
     *
     * @return
     */
    List<String> findAllScoreCards();


    /**
     * 根据借款订单ID查询风控订单信息
     *
     * @param borrowOrderID
     * @return
     */
    public RiskModelOrder findRiskOrderByBrdId(Integer borrowOrderID);
}

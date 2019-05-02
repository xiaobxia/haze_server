package com.info.risk.service;

import com.info.risk.dao.IRiskModelOrderDao;
import com.info.risk.pojo.RiskModelOrder;
import com.info.web.pojo.BorrowOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by tl on 2018/5/2.
 */
@Service
public class RiskModelOrderService implements IRiskModelOrderService {

    @Resource
    private IRiskModelOrderDao riskModelOrderDao;

    @Override
    public int findParamsCount(HashMap<String, Object> params) {
        return riskModelOrderDao.findParamsCount(params);
    }

    @Override
    public RiskModelOrder findOneByParams(Map<String, Object> params) {
        return riskModelOrderDao.findOneByParams(params);
    }

    @Override
    public RiskModelOrder findRiskOrderByBrdId(Integer borrowOrderID){
        return riskModelOrderDao.selectRiskOrderInfoBybrdId(borrowOrderID);
    }

    @Override
    public int updateRiskModelOrder(Integer borrowOrderId, Integer borrowOrderStatus, String personReviewAccount,
                                    String personReviewRemark, Date personReviewTime) {
        RiskModelOrder riskModelOrder = new RiskModelOrder();
        riskModelOrder.setBorrowOrderId(borrowOrderId);
        riskModelOrder.setPersonReviewAdvice(getAdvice(borrowOrderStatus));
        riskModelOrder.setPersonReviewAccount(personReviewAccount);
        riskModelOrder.setPersonReviewRemark(personReviewRemark);
        riskModelOrder.setPersonReviewTime(personReviewTime);
        return riskModelOrderDao.updateRiskModelOrder(riskModelOrder);
    }

    /**
     * 查询所有的模型编号-不重复
     *
     * @return
     */
    @Override
    public List<String> findAllScoreCards() {
        return riskModelOrderDao.findAllScoreCard();
    }


    private static Integer getAdvice(Integer borrowOrderStatus) {
        Integer advice = null;
        if (BorrowOrder.STATUS_FSTG.equals(borrowOrderStatus) || BorrowOrder.STATUS_FKZ.equals(borrowOrderStatus)) {
            advice = RiskModelOrder.ADVICE_PASS;
        }
        if (BorrowOrder.STATUS_FSBH.equals(borrowOrderStatus) || BorrowOrder.STATUS_FSBHLH.equals(borrowOrderStatus)) {
            advice = RiskModelOrder.ADVICE_REJECT;
        }
        return advice;
    }
}

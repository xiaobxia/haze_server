package com.info.analyze.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    /**
     * 查找未还的钱
     * @return Integer
     */
    Integer selectNoReturnMoney();
    /**
     * 查找 新老用户 借款 人数
     * @param map map
     * @return list
     */
    List<Map<String, Object>> selectBorrowApplyCount(Map<String, Object> map);


    Integer selectRegistCount(Map<String, Object> map);

    /**
     *查找 新老用户成功借款人数
     * @param map map
     * @return map
     */
    HashMap<String, Object> selectBorrowLoanUserCount(Map<String, Object> map) ;

    /**
     * 查找 新老用户 放款钱的数量
     * @param map map
     * @return map
     */
    HashMap<String, Object> selectBorrowMoneyCount(Map<String, Object> map) ;


    /**
     * 统计
     * 贷款人数
     * 以及贷款两次以上的（包括两次的人数）
     * @return Integer
     */
    Integer loanAginCount(Map<String, Object> map);

    /**
     * 统计 核准率的 相关指标
     * @param map map
     * @return Integer
     */
    Integer selectCheckRateCount(Map<String, Object> map);

    /**
     * 按周、月统计的申请人数
     * @param map map
     * @return map
     */
    HashMap<String,Object> selectAppyUserCountByMonthOrWeek(Map<String, Object> map);

    /**
     * 按周、月统计的放款人数
     * @param map map
     * @return map
     */
    HashMap<String,Object> selectLoanUserCountByMonthOrWeek(Map<String, Object> map);
}

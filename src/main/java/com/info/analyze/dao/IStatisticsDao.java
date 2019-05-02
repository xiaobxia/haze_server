package com.info.analyze.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface IStatisticsDao {

    /**
     * 统计 用户的申请人数情况
     * @param map map
     * @return list
     */
    List<Map<String,Object>> selectBorrowApplyUserCount(Map<String, Object> map);

    /**
     * 统计 用户的已经放款的人数情况
     * @param map map
     * @return map
     */
    HashMap<String,Object> selectBorrowLoanUserCount(Map<String, Object> map);

    /**
     * 统计 已经放款的金额
     * @param map map
     * @return map
     */
    HashMap<String,Object> selectBorrowMoneyCount(Map<String, Object> map);

    /**
     * 查找注册用户数
     * @param map map
     * @return Integer
     */
    Integer selectRegistCount(Map<String, Object> map);


    /**
     * 查找未还的钱（按月统计）
     * @return Integer
     */
    Integer selectNoReturnMoney();

    /**
     * 统计
     * 贷款人数
     * 以及贷款两次以上的（包括两次的人数）
     * @return Integer
     */
    Integer loanAginCount(Map<String, Object> map);

    /**
     * 统计核准率的 通过申请人数以及借款人数
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

package com.info.analyze.service;

import com.info.analyze.dao.IStatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService implements IStatisticsService {

    @Autowired
    private IStatisticsDao statisticsDao;
    /**
     * 查找未还的钱
     * @return Integer
     */
    public Integer selectNoReturnMoney(){
        Integer returnMoney = statisticsDao.selectNoReturnMoney();
        if (returnMoney != null) {
            return returnMoney;
        }
        return 0;
    }
    /**
     * 查找 新老用户 借款 人数
     * @param map map
     * @return list
     */
    public List<Map<String, Object>> selectBorrowApplyCount(Map<String, Object> map) {
        return statisticsDao.selectBorrowApplyUserCount(map);
    }


    public Integer selectRegistCount(Map<String,Object> map){
        Integer num = statisticsDao.selectRegistCount(map);
        if (num == null) {
            return 0;
        }
        return num;
    }

    /**
     *查找 新老用户成功借款人数
     * @param map map
     * @return map
     */
    public HashMap<String, Object> selectBorrowLoanUserCount(Map<String, Object> map) {
        return statisticsDao.selectBorrowLoanUserCount(map);
    }

    /**
     * 查找 新老用户 放款钱的数量
     * @param map map
     * @return map
     */
    public HashMap<String, Object> selectBorrowMoneyCount(Map<String, Object> map) {
        return statisticsDao.selectBorrowMoneyCount(map);
    }


    public Integer loanAginCount(Map<String, Object> map){
        Integer count = statisticsDao.loanAginCount(map);
        if (count == null) {
            return 0;
        }
        return count;
    }


    public Integer selectCheckRateCount(Map<String,Object> map){
        Integer num = statisticsDao.selectCheckRateCount(map);
        if (num == null) {
            return 0;
        }
        return num;
    }

    @Override
    public HashMap<String, Object> selectAppyUserCountByMonthOrWeek(Map<String, Object> map) {
        return statisticsDao.selectAppyUserCountByMonthOrWeek(map);
    }

    @Override
    public HashMap<String, Object> selectLoanUserCountByMonthOrWeek(Map<String, Object> map) {
        return statisticsDao.selectLoanUserCountByMonthOrWeek(map);
    }
}

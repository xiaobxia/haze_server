package com.info.web.service;

import com.info.web.pojo.BackLoanCensus;
import com.info.web.util.PageConfig;

import java.util.HashMap;


/**
 * 贷后统计
 */
public interface IBackLoanCensusService {

    //贷后统计定时任务
    Boolean afterLoanCensus(String repayTime);

    //贷后逾期统计
    Boolean BackLoanOveCensus();

    //添加贷后统计
    void insertBackLoanCensus(BackLoanCensus backLoanCensus);

    //更新贷后统计
    void updateBackLoanCensus(BackLoanCensus backLoanCensus);

    //根据还款日期查找贷后记录
    BackLoanCensus findBackLoanCensusByTime(String repayTime);
    //贷后查询
    PageConfig<BackLoanCensus> backLoanCensusResult(HashMap<String,Object> params);

}

package com.info.analyze.service;

import com.info.analyze.pojo.DataStatistics;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.Map;

public interface IDataStatisticsService {
    void insertDataStatisticsByFlag(boolean getBeforeFlag);

    /**
     * 统计复贷率
     * @param startMonth startMonth
     * @param endMonth endMonth
     * @return map
     */
    Map<String,Object> getLoanAgainRate(String startMonth, String endMonth);

    /**
     * 分页查找
     * @param  params params
     * @return page
     */
    PageConfig<DataStatistics> findDataStatisticsPage(HashMap<String, Object> params);

    /**
     * 删除所有的数据
     * @return int
     */
    int deleDataStatisticsAll();
}

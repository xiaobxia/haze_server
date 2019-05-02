package com.info.statistic.service;


import com.info.web.pojo.statistics.Overdue;
import com.info.web.util.PageConfig;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tl on 2018/4/2.
 */
public interface IOverdueService {
    /**
     * 获取新老全部用户的Overdue
     */
    Overdue analysis(Overdue overdue);

    /**
     * 按渠道获取新老用户的逾期天数
     */
    List<Overdue> getOverdueDaysByChannel(Date sdate, Date edate, Date pdate, int type);

    /**
     * 模型指标统计
     */
    List<Overdue> analysisByModel(Date pdate);

    /**
     * 按渠道统计 修改逾期率
     *
     */
    List<Overdue> updateByChannel(Date pdate, int type);

    /**
     * 模型指标统计查询
     */
    PageConfig<Overdue> findPage(HashMap<String, Object> params);

    /**
     * 按复审时间统计
     */
    List<Overdue> analysisByReviewTime(HashMap<String, Object> params) throws ParseException;
}

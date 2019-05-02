package com.info.web.service;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.ActiveStatisticsInfo;
import com.info.web.pojo.BorrowStatisticsReport;

public interface IBorrowStatisticsReportService {

/**
 * 根据条件创建数据
 
 * @param nowDate  计算当天传null,  如果回算 传入nowDate格式"yyyy-MM-dd"
 */
	public void createborrowStaticReportDateByDate(String nowDate);
	
	public void createborrowLateReportDateByDate(String nowDate);

	/**
	 * 页面展示
	 */
	public List<BorrowStatisticsReport> findBorrowStatisticsReportAll(Map<String, Object> map);
	/**
     * 
     * <p>Description:新增活动统计数据</p>
     * @param activeStatisticsInfo
     * @return 返回影响行数
     * @author lixingxing
     * @date 2017年3月14日 下午1:11:25
     */
    public int activeInsertBySelective(ActiveStatisticsInfo activeStatisticsInfo);
}

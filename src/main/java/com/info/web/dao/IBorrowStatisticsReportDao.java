package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.BorrowStatisticsReport;
import org.springframework.stereotype.Repository;

@Repository("borrowStatisticsReportDao")
public interface IBorrowStatisticsReportDao {
	int deleteByPrimaryKey(Integer id);

	int insert(BorrowStatisticsReport record);

	int insertSelective(BorrowStatisticsReport record);
	
	int delSelective(BorrowStatisticsReport record);

	BorrowStatisticsReport selectByPrimaryKey(Integer id);

	List<BorrowStatisticsReport> selectCurrentUsercount(String nowDate);

	List<BorrowStatisticsReport> selectPromotionCountThisDay(String nowDate);

	List<BorrowStatisticsReport> selectPromotionCountThisWeek(String nowDate);

	List<BorrowStatisticsReport> selectPromotionCountThisMonth(String nowDate);

	int updateByPrimaryKeySelective(BorrowStatisticsReport record);

	int updateByPrimaryKey(BorrowStatisticsReport record);
	int updateByReportDate(BorrowStatisticsReport record);
	List<BorrowStatisticsReport> findAll(Map<String, Object> map);
	
	List<BorrowStatisticsReport> selectOverdueThisDayCountList(String nowDate);
	List<BorrowStatisticsReport> selectOverdueThisDayCountFMList(String nowDate);
	List<BorrowStatisticsReport> selectBaddebtrateThisDayList(String nowDate);
	List<BorrowStatisticsReport> selectBaddebtrateThisDayFMList(String nowDate);
	
	List<Map<String, Object>> selectAvgThisDayCountList();
	
	long selectTotalApplyforDay(String date);
	
	long selectTotalSuceesstforDay(String date);
}
package com.info.web.dao;

import com.info.web.pojo.InfoReport;

import java.util.List;
import java.util.Map;


public interface IReportDao {

	public void saveReport(InfoReport infoReport);

	int deleteByPrimaryKey(Integer id);

	int insert(InfoReport record);

	int insertSelective(InfoReport record);

	InfoReport selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(InfoReport record);

	int updateByPrimaryKey(InfoReport record);

	List<InfoReport> selectByTimeAndAppmarket(Map<String, Object> params);

	List<InfoReport> selectInfoToday(Map<String, Object> params);

	//查询当日所有的注册量
	int selectTheDayAllRegisterCunt(Map<String, Object> params);

	//当前用户所属应用市场
	String selectTheDayUsersAppMarket(String userId);
}

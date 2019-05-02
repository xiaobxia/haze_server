package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.utils.ServiceResult;
import com.info.web.pojo.ChannelReport;
import com.info.web.pojo.ThirdChannelReport;
import com.info.web.util.PageConfig;


public interface IThirdChannelReportService {
	
	ChannelReport getChannelReportById(Integer id);

	

	/**
	 * 分页
	 */
	PageConfig<ThirdChannelReport> findPage(HashMap<String, Object> params);
	/**
	 * 分页
	 */
	PageConfig<ThirdChannelReport> findPrPage(HashMap<String, Object> params);
	
	public Integer findPrParamsCount(Map<String, Object> params);
	public Integer findParamsCount(Map<String, Object> params);
	
	PageConfig<ThirdChannelReport> findSumPage(HashMap<String, Object> params);
	
	List<ThirdChannelReport> getAllChannelReports(Map<String, Object> params);
	
	List<ThirdChannelReport> getAllPrChannelReports(Map<String, Object> params);

	/**
	 * 统计每日数据
	 * 
	 * @return
	 */
	public boolean saveThirdChannelReport(String nowTime);
	
	public boolean pushUserReport(String nowTime);
	
	public boolean pushAginUserReport(String nowTime);

}

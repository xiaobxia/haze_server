package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelReport;
import com.info.web.pojo.OutChannelLook;
import com.info.web.util.PageConfig;


public interface IChannelReportService {
	
	ChannelReport getChannelReportById(Integer id);

	

	/**
	 * 分页
	 */
	PageConfig<ChannelReport> findPage(HashMap<String, Object> params);
	/**
	 * 分页
	 */
	PageConfig<ChannelReport> findPrPage(HashMap<String, Object> params);
	
	public Integer findPrParamsCount(Map<String, Object> params);
	public Integer findParamsCount(Map<String, Object> params);
	
	PageConfig<ChannelReport> findSumPage(HashMap<String, Object> params);
	
	List<ChannelReport> getAllChannelReports(Map<String, Object> params);
	
	List<ChannelReport> getAllPrChannelReports(Map<String, Object> params);

	/**
	 * 统计每日数据
	 * 
	 * @return time
	 */
	public boolean saveChannelReport(String nowTime);

	/**
	 * 根据code 查询id
	 * @param channelCode code
	 * @return str
	 */
	String getChannelIdByCode(String channelCode);

	/**
	 * 查询今天注册量
	 * @param channelId id
	 * @return int
	 */
	int getRegisterNow(String channelId);

	/**
	 * channelId
	 * @param channelId id
	 * @return int
	 */
	int getRegisterRealNow(String channelId);

	public PageConfig<OutChannelLook> findPageOut(HashMap<String, Object> params);

}

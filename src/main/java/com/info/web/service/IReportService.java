package com.info.web.service;

import com.info.web.pojo.InfoReport;

import java.util.List;
import java.util.Map;

public interface IReportService {
	
	public void saveReport(InfoReport infoReport);

	public List<InfoReport> findReportList(Map<String, Object> params);
}

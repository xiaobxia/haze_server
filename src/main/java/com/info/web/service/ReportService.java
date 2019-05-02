package com.info.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.info.web.dao.IReportDao;
import com.info.web.pojo.InfoReport;

import java.util.List;
import java.util.Map;

@Service("reportService")
public class ReportService implements IReportService {

	@Autowired
	@Qualifier("reportDao")
	private IReportDao reportDao;
	
	public void saveReport(InfoReport infoReport) {
		reportDao.saveReport(infoReport);
	}

	@Override
	public List<InfoReport> findReportList(Map<String,Object> params){
		return reportDao.selectByTimeAndAppmarket(params);
	}

}

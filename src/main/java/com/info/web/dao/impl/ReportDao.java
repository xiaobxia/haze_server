package com.info.web.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.info.web.dao.BaseDao;
import com.info.web.dao.IReportDao;
import com.info.web.pojo.InfoReport;

import java.util.List;
import java.util.Map;

@Repository("reportDao")
public class ReportDao extends BaseDao implements IReportDao {

	@Override
	public void saveReport(InfoReport infoReport) {
		getSqlSessionTemplate().insert("report.saveReport", infoReport);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return getSqlSessionTemplate().delete("report.deleteByPrimaryKey", id);

	}

	@Override
	public int insert(InfoReport record) {
		return getSqlSessionTemplate().insert("report.insert", record);
	}

	@Override
	public int insertSelective(InfoReport record) {
		return getSqlSessionTemplate().insert("report.insertSelective", record);
	}

	@Override
	public InfoReport selectByPrimaryKey(Integer id) {
		return getSqlSessionTemplate().selectOne("report.selectByPrimaryKey", id);
	}

	@Override
	public int updateByPrimaryKeySelective(InfoReport record) {
		return getSqlSessionTemplate().update("report.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(InfoReport record) {
		return getSqlSessionTemplate().update("report.updateByPrimaryKey", record);
	}

	@Override
	public List<InfoReport> selectByTimeAndAppmarket(Map<String, Object> params) {
		return getSqlSessionTemplate().selectList("report.selectByTimeAndAppmarket", params);
	}

	@Override
	public List<InfoReport> selectInfoToday(Map<String, Object> params){
		return getSqlSessionTemplate().selectList("report.selectInfoToday", params);
	}

	@Override
	public int selectTheDayAllRegisterCunt(Map<String, Object> params) {
		return getSqlSessionTemplate().selectOne("report.selectTheDayAllRegisterCunt", params);
	}

	@Override
	public String selectTheDayUsersAppMarket(String userId) {
		List<Object> objects = getSqlSessionTemplate().selectList("report.selectTheDayUsersAppMarket", userId);
		if(null != objects && 0 < objects.size()){
			return objects.get(0).toString();
		}
		return "";
	}


}

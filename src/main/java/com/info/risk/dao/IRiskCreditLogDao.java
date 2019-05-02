package com.info.risk.dao;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskCreditLog;

@Repository
public interface IRiskCreditLogDao {
	public int insert(RiskCreditLog riskCreditLog);
}

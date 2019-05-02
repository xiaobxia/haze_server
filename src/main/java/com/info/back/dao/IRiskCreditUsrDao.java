package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskRuleCal;

@Repository
public interface IRiskCreditUsrDao {

	public RiskCreditUser findByAssetsId(String assetsId);

	public List<RiskRuleCal> findRiskRuleCalByAssetsId(HashMap<String, String> params);

	public String findSuf(String assetsId);
}

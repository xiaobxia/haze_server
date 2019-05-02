package com.info.back.service;

import java.util.List;

import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskRuleCal;


/**
 * @author user
 *
 */
public interface IRiskUserService {
	
	RiskCreditUser findRiskCreditUserByAssetsId(String assetsId);
	
	List<RiskRuleCal> findRiskRuleCalByAssetsId(String assetsId);

}

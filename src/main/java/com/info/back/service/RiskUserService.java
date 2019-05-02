package com.info.back.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IRiskCreditUsrDao;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskRuleCal;
@Slf4j
@Service("riskUserService")
public class RiskUserService implements IRiskUserService {

	@Autowired
	private IRiskCreditUsrDao riskCreditUsrDao;

	public RiskCreditUser findRiskCreditUserByAssetsId(String assetsId) {
		return riskCreditUsrDao.findByAssetsId(assetsId);
	}

	public List<RiskRuleCal> findRiskRuleCalByAssetsId(String assetsId) {
		List<RiskRuleCal> list = new ArrayList<>();
		String riskCalSuf = riskCreditUsrDao.findSuf(assetsId);
		if (StringUtils.isNotBlank(riskCalSuf)) {
			HashMap<String, String> params = new HashMap<>();
			params.put("riskCalSuf", riskCalSuf);
			params.put("assetsId", assetsId);
			try {
				list = riskCreditUsrDao.findRiskRuleCalByAssetsId(params);
			}catch (Exception e){
				log.error("findRiskRuleCalByAssetsId error:{}",e);
				list = new ArrayList<>();

			}

		}
		return list;
	}
}

package com.info.risk.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.risk.dao.IRiskManageRuleDao;
import com.info.risk.pojo.RiskManageRule;
import com.info.web.dao.IPaginationDao;
import com.info.web.util.PageConfig;

@Service
public class RiskManageRuleService implements IRiskManageRuleService {
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IRiskManageRuleDao riskManageRuleDao;
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<RiskManageRule> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RiskManageRule");
		return paginationDao
				.findPage("findAll", "findAllCount", params, "risk");
	}

	@Override
	public List<RiskManageRule> findAll() {
		return riskManageRuleDao.findAll();
	}

	@Override
	public int insert(RiskManageRule riskManageRule) {
		return riskManageRuleDao.insert(riskManageRule);
	}

	@Override
	public int update(RiskManageRule riskManageRule) {
		if (riskManageRule.getRootType().intValue() == RiskManageRule.ROOT_TYPE_ROOT) {
			riskManageRuleDao.updateRootType(riskManageRule);
		} else if (riskManageRule.getRootType().intValue() == RiskManageRule.ROOT_TYPE_ROOT_ZR) {
			riskManageRuleDao.updateRootType(riskManageRule);
		} else if (riskManageRule.getRootType().intValue() == RiskManageRule.ROOT_TYPE_ROOT_ED) {
			riskManageRuleDao.updateRootType(riskManageRule);
		}
		return riskManageRuleDao.update(riskManageRule);
	}

	@Override
	public RiskManageRule findById(Integer id) {
		return riskManageRuleDao.findById(id);
	}

	@Override
	public List<RiskManageRule> findAllByRootType(Integer rootType) {
		return riskManageRuleDao.findAllByRootType(rootType);
	}

}

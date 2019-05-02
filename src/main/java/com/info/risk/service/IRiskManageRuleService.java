package com.info.risk.service;

import java.util.HashMap;
import java.util.List;

import com.info.risk.pojo.RiskManageRule;
import com.info.web.util.PageConfig;

public interface IRiskManageRuleService {

	/**
	 * 插入
	 * 
	 * @param riskManageRule
	 * @return
	 */
	public int insert(RiskManageRule riskManageRule);

	/**
	 * 更新
	 * 
	 * @param riskManageRule
	 * @return
	 */
	public int update(RiskManageRule riskManageRule);

	/**
	 * 分页查询
	 * 
	 * @param params
	 *            ruleName规则名称；<br>
	 *            status启停状态<br>
	 *            ruleType规则类型<br>
	 *            rule_desc描述<br>
	 * 
	 * @return
	 */
	public PageConfig<RiskManageRule> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public RiskManageRule findById(Integer id);

	/**
	 * 查询全部规则
	 * 
	 * @return
	 */
	List<RiskManageRule> findAll();

	/**
	 * 根据根节点类型查询根节点
	 * 
	 * @return
	 */
	public List<RiskManageRule> findAllByRootType(Integer rootType);
}

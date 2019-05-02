package com.info.risk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskManageRule;

@Repository
public interface IRiskManageRuleDao {
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
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public RiskManageRule findById(Integer id);

	public List<RiskManageRule> findAll();

	public List<RiskManageRule> findAllByRootType(Integer rootType);

	public int updateRootType(RiskManageRule riskManageRule);
}

package com.info.risk.service;

public interface IRiskTaskService {

	/**
	 * 查询聚信立报告
	 */
	void findJxlReport();

	/**
	 * 根据准入原则判断是否调用接口，进而进行规则验证
	 */
	void updateAnalysis();

	/**
	 * 更新认证完毕的新用户的借款额度
	 */
	void updateEd();

	/**
	 * 获取魔蝎运营商报告
	 */
	void findMobileReport();

	/**
	 * 未实现，未使用
	 */
	void updateFromRisk();

}

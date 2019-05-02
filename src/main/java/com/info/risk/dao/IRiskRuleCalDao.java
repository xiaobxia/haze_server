package com.info.risk.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.risk.pojo.RiskRuleCal;

@Repository
public interface IRiskRuleCalDao {
	/**
	 * 批量插入过程数据
	 * 
	 * @param params
	 *            list批量插入的对象<br>
	 *            dbTime数据库时间
	 * @return
	 */
	public int batchInsert(HashMap<String, Object> params);

	/**
	 * 获得数据库时间
	 * 
	 * @return
	 */
	public String findDbTime();
}

package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.UserCertification;

public interface UserCerticationDao {
	/**
	 * 查询认证列表
	 * @return
	 */
	public List<UserCertification> findCerticationList();
	/**
	 * 验证用户已认证的选项
	 * @param params
	 * @return
	 */
	public Map<String, Object> checkUserCalendar(Map<String, Object> params);
}

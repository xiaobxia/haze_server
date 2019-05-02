package com.info.web.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.dao.BaseDao;
import com.info.web.dao.UserCerticationDao;
import com.info.web.pojo.UserCertification;
@Repository("userCertication")
public class UserCerticationDaoImpl extends BaseDao implements UserCerticationDao {

	@Override
	public List<UserCertification> findCerticationList() {
		return this.getSqlSessionTemplate().selectList("userCertification.selectCertificationList");
	}

	@Override
	public Map<String, Object> checkUserCalendar(Map<String, Object> params) {
		return this.getSqlSessionTemplate().selectOne("userCertification.userCalendar", params);
	}

}

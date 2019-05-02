package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackConfigParamsDao;
import com.info.web.pojo.BackConfigParams;

@Service
public class BackConfigParamsService implements IBackConfigParamsService {
	@Autowired
	IBackConfigParamsDao backConfigParamsDao;

	@Override
	public List<BackConfigParams> findParams(HashMap<String, Object> params) {

		return backConfigParamsDao.findParams(params);
	}

	@Override
	public int updateValue(List<BackConfigParams> list, String type) {
		int result = backConfigParamsDao.updateValue(list);
		if (result > 0) {
		}
		return result;
	}
}

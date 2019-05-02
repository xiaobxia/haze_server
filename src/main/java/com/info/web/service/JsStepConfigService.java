package com.info.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IJsStepConfigDao;
import com.info.web.pojo.JsStepConfig;
/**
 * 行为配置
 * @author Administrator
 *
 */
@Service
public class JsStepConfigService implements IJsStepConfigService {
	@Autowired
	private IJsStepConfigDao jsStepConfigDao;
	@Override
	public List<JsStepConfig> findList(JsStepConfig jsStepConfig) {
		return jsStepConfigDao.findList(jsStepConfig);
	}
//
//	public JsStepConfig getOne(String id){
//		return super.get(id);
//	}
//	
 
//
//	public Page<JsStepConfig> findPage(Page<JsStepConfig> page, JsStepConfig jsStepConfig) {
//		return super.findPage(page, jsStepConfig);
//	}
//	
//
//	@Transactional(readOnly = false)
//	public void save(JsStepConfig jsStepConfig) {
//		super.save(jsStepConfig);
//	}
//	
//	@Transactional(readOnly = false)
//	public void delete(JsStepConfig jsStepConfig) {
//		super.delete(jsStepConfig);
//	}
}

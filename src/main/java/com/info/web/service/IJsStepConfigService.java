package com.info.web.service;

import java.util.List;

import com.info.web.pojo.JsStepConfig;

/**
 * 行为配置
 * @author Administrator
 *
 */
 
public interface IJsStepConfigService   {
//
//	public JsStepConfig getOne(String id){
//		return super.get(id);
//	}
//	
	public List<JsStepConfig> findList(JsStepConfig jsStepConfig) ;
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

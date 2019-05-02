package com.info.web.service;

import java.util.HashMap;

import com.info.back.utils.ServiceResult;
import com.info.web.pojo.JsStepRecord;
 

/**
 * 统计行为记录
 * @author Administrator
 *
 */
 
public interface IJsStepRecordService  {
 
	
	
	
	public void save(JsStepRecord jsStepRecord) ;
	
	public ServiceResult addUserStep(HashMap<String, String> stepMap);
	
 
}

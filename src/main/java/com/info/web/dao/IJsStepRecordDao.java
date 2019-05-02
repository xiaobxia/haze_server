package com.info.web.dao;

import java.util.List;

import com.info.web.pojo.JsStepRecord;
import org.springframework.stereotype.Repository;

@Repository("jsStepRecordDao")
public interface IJsStepRecordDao {
	
	List<JsStepRecord> findPageList(JsStepRecord jsStepRecord);
	
	void  insert(JsStepRecord jsStepRecord);
	
	void  update(JsStepRecord jsStepRecord);

}

package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.JsAwardRecord;
import org.springframework.stereotype.Repository;

@Repository("jsAwardRecordDao")
public interface IJsAwardRecordDao {

	List<JsAwardRecord> findList(HashMap<String, Object> jsAwardRecord);
	
	JsAwardRecord findOne(JsAwardRecord jsAwardRecord);
	
	void insert(JsAwardRecord jsAwardRecord);
	
	void update(JsAwardRecord jsAwardRecord);
	
}

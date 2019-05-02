package com.info.web.dao;

import java.util.List;

import com.info.web.pojo.JsDrawrollsRecord;
import org.springframework.stereotype.Repository;

@Repository("jsDrawrollsRecordDao")
public interface IJsDrawrollsRecordDao  {
	
	List<JsDrawrollsRecord>  findList(JsDrawrollsRecord jsDrawrollsRecord);
	
	public List<JsDrawrollsRecord> findListTop(JsDrawrollsRecord jsDrawrollsRecord);
	
	public List<JsDrawrollsRecord> findListUser(JsDrawrollsRecord jsDrawrollsRecord);
	
	public List<JsDrawrollsRecord> findListByPhone(JsDrawrollsRecord jsDrawrollsRecord);
	JsDrawrollsRecord findMaxOne(JsDrawrollsRecord jsDrawrollsRecord);
	
	void  insert(JsDrawrollsRecord jsDrawrollsRecord);
	void  update(JsDrawrollsRecord jsDrawrollsRecord);

}

package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.info.web.pojo.JsDrawrollsRecord;
import com.info.web.util.PageConfig;

/**
 * 统计抽奖号码记录
 * @author Administrator
 *
 */
 
public interface IJsDrawrollsRecordService  {
//
//	@Autowired
//	JsDrawrollsRecordDao jsDrawrollsRecordDao;
//	public JsDrawrollsRecord getOne(String id){
//		return super.get(id);
//	}
 	public  JsDrawrollsRecord  findMaxOne(JsDrawrollsRecord jsDrawrollsRecord);
	 
	public List<JsDrawrollsRecord> findList(JsDrawrollsRecord jsDrawrollsRecord);
 	 
	public List<JsDrawrollsRecord> findListTop(JsDrawrollsRecord jsDrawrollsRecord) ;
//	
	 
	public  JsDrawrollsRecord  findOne(JsDrawrollsRecord jsDrawrollsRecord) ;
	
	 
	public void save(JsDrawrollsRecord jsDrawrollsRecord);
	
	public PageConfig<JsDrawrollsRecord> findListByUserPhone(HashMap<String, Object> params);

//	public Page<JsDrawrollsRecord> findListByUserPhone(int pageNo,int pageSize, JsDrawrollsRecord jsDrawrollsRecord) {
//		Page<JsDrawrollsRecord> page =new Page<JsDrawrollsRecord>();
//		page.setPageNo(pageNo);
//		page.setPageSize(pageSize);
//		jsDrawrollsRecord.setPage(page);
//		page.setList(jsDrawrollsRecordDao.findListByPhone(jsDrawrollsRecord));
//		if(page.getLast()<pageNo){
//			page.setList(null);
//		}
//		return page;
//	}
//	
//	public List<JsDrawrollsRecord> findListById(JsDrawrollsRecord jsDrawrollsRecord) {
//		return super.findList(jsDrawrollsRecord);
//	}
//
//	public Page<JsDrawrollsRecord> findPage(Page<JsDrawrollsRecord> page, JsDrawrollsRecord jsDrawrollsRecord) {
//		return super.findPage(page, jsDrawrollsRecord);
//	}
//	
//
//	@Transactional(readOnly = false)
//	public void save(JsDrawrollsRecord jsDrawrollsRecord) {
//		super.save(jsDrawrollsRecord);
//	}
//	
//	@Transactional(readOnly = false)
//	public void delete(JsDrawrollsRecord jsDrawrollsRecord) {
//		super.delete(jsDrawrollsRecord);
//	}
}

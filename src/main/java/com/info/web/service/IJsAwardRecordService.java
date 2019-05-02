package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.web.pojo.JsAwardRecord;
import com.info.web.util.PageConfig;
/**
 * 统计用户中奖记录
 * @author Administrator
 *
 */
 
 public interface IJsAwardRecordService  {

//	
//	public JsAwardRecord getOne(String id){
//		return super.get(id);
//	}
//	
	public List<JsAwardRecord> findList(HashMap<String, Object> jsAwardRecord) ;
	public JsAwardRecord findOne(HashMap<String, Object> jsAwardRecord);
	
	
	public void save(JsAwardRecord jsAwardRecord);
 
//	
//	public JsAwardRecord findbyEntity(JsAwardRecord jsAwardRecord) {
//		return super.get(jsAwardRecord);
//	}
//
//	public Page<JsAwardRecord> findPage(Page<JsAwardRecord> page, JsAwardRecord jsAwardRecord) {
//		return super.findPage(page, jsAwardRecord);
//	}
//	
//	public Page<JsAwardRecord> getPage(Page<JsAwardRecord> page, JsAwardRecord jsAwardRecord) {
//		return super.findPage(page, jsAwardRecord);
//	}
//	
	public PageConfig<JsAwardRecord> findPageList(HashMap<String, Object> map);
//	{
//		Page<JsAwardRecord> page =new Page<JsAwardRecord>();
//		page.setPageNo(pageNo);
//		page.setPageSize(pageSize);
//		jsAwardRecord.setPage(page);
//		page.setList(this.findList(jsAwardRecord));
//		if(page.getLast()<pageNo){
//			page.setList(null);
//		}
//		return page;
//	}
//	
//	
//
//	@Transactional(readOnly = false)
//	public void save(JsAwardRecord jsAwardRecord) {
//		super.save(jsAwardRecord);
//	}
//	
//	@Transactional(readOnly = false)
//	public void delete(JsAwardRecord jsAwardRecord) {
//		super.delete(jsAwardRecord);
//	}
//	
}

package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.constant.Constant;
import com.info.web.dao.IJsDrawrollsRecordDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.JsDrawrollsRecord;
import com.info.web.util.PageConfig;
/**
 * 统计抽奖号码记录
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = true)
public class JsDrawrollsRecordService implements IJsDrawrollsRecordService {
//
	@Autowired
	private IJsDrawrollsRecordDao jsDrawrollsRecordDao;
	@Autowired
	private IPaginationDao paginationDao;
//	public JsDrawrollsRecord getOne(String id){
//		return super.get(id);
//	}
//	
	@Override
	public List<JsDrawrollsRecord> findList(JsDrawrollsRecord jsDrawrollsRecord) {
		return jsDrawrollsRecordDao.findList(jsDrawrollsRecord);
	}
//
	@Override
	public List<JsDrawrollsRecord> findListTop(JsDrawrollsRecord jsDrawrollsRecord) {
		return  jsDrawrollsRecordDao.findListTop(jsDrawrollsRecord);
	}
//	
	@Override
	public  JsDrawrollsRecord  findOne(JsDrawrollsRecord jsDrawrollsRecord) {
		List<JsDrawrollsRecord> list=jsDrawrollsRecordDao.findList(jsDrawrollsRecord);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		 
	}
	@Override
	public  JsDrawrollsRecord  findMaxOne(JsDrawrollsRecord jsDrawrollsRecord) {
		
		return jsDrawrollsRecordDao.findMaxOne(jsDrawrollsRecord);
 
		 
	}
	
	public void save(JsDrawrollsRecord jsDrawrollsRecord) {
		if (jsDrawrollsRecord.getId()==null){
			 
			jsDrawrollsRecordDao.insert(jsDrawrollsRecord);
		}else{
			 
			jsDrawrollsRecordDao.update(jsDrawrollsRecord);
		}
		
	}
	@SuppressWarnings("unchecked")
	public PageConfig<JsDrawrollsRecord> findListByUserPhone(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "JsDrawrollsRecord");
		PageConfig<JsDrawrollsRecord> pageConfig = new PageConfig<JsDrawrollsRecord>();
		pageConfig = paginationDao.findPage("findListByPhone", "findListByPhoneCount", params,"web");
		return pageConfig;
	}
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

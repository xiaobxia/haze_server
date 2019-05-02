package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IJsAwardRecordDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.JsAwardRecord;
import com.info.web.util.PageConfig;
/**
 * 统计用户中奖记录
 * @author Administrator
 *
 */
@Service
 
public class JsAwardRecordService  implements IJsAwardRecordService{
	@Autowired
	private IJsAwardRecordDao jsAwardRecordDao;
	@Autowired
	private IPaginationDao paginationDao;
//	public JsAwardRecord getOne(String id){
//		return super.get(id);
//	}
//	
	@Override
	public List<JsAwardRecord> findList(HashMap<String,Object> jsAwardRecord) {
		return jsAwardRecordDao.findList(jsAwardRecord);
	}
//	
	@Override
	public JsAwardRecord findOne(HashMap<String,Object> jsAwardRecord) {
		List<JsAwardRecord> list=jsAwardRecordDao.findList(jsAwardRecord);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public void save(JsAwardRecord jsAwardRecord) {
		if (jsAwardRecord.getId()==null){
			 
			jsAwardRecordDao.insert(jsAwardRecord);
		}else{
			 
			jsAwardRecordDao.update(jsAwardRecord);
		}
	}
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
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<JsAwardRecord> findPageList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "JsAwardRecord");
		PageConfig<JsAwardRecord> pageConfig = new PageConfig<JsAwardRecord>();
		pageConfig = paginationDao.findPage("findList", "findListCount", params,"web");
		return pageConfig;
	}
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

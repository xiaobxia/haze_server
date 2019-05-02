package com.info.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IJsLoanPersonDao;
import com.info.web.pojo.JsLoanPerson;

/**
 * 用户
 * @author Administrator
 *
 */
@Service
public class JsLoanPersonService  implements IJsLoanPersonService{

	@Autowired
	private IJsLoanPersonDao jsLoanPersonDao;
	@Override
	public JsLoanPerson findOne(JsLoanPerson jsLoanPerson) {
		
		
		List<JsLoanPerson> list=jsLoanPersonDao.findUserList(jsLoanPerson);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void save(JsLoanPerson jsLoanPerson) {
		if (jsLoanPerson.getId()==null){
			 
			jsLoanPersonDao.insert(jsLoanPerson);
		}else{
			 
			jsLoanPersonDao.update(jsLoanPerson);
		}
		
	}
//
//	@Autowired
//	JsLoanPersonDao jsLoanPersondao;
//	public JsLoanPerson getOne(String id){
//		return super.get(id);
//	}
//	
//	public List<JsLoanPerson> findList(JsLoanPerson jsLoanPerson) {
//		return super.findList(jsLoanPerson);
//	}
//	public  JsLoanPerson  findOne(JsLoanPerson jsLoanPerson) {
//		List<JsLoanPerson> list=super.findList(jsLoanPerson);
//		if(list!=null&&list.size()>0){
//			return list.get(0);
//		}
//		return null;
//	}
//
//	public Page<JsLoanPerson> findPage(Page<JsLoanPerson> page, JsLoanPerson jsLoanPerson) {
//		return super.findPage(page, jsLoanPerson);
//	}
//	
//	public Page<JsLoanPerson> findUserPage(Page<JsLoanPerson> page, JsLoanPerson jsLoanPerson) {
//		jsLoanPerson.setPage(page);
//		page.setList( jsLoanPersondao.findUserList(jsLoanPerson));
//		return page;
//	}
//	
//
//	@Transactional(readOnly = false)
//	public void save(JsLoanPerson jsLoanPerson) {
//		super.save(jsLoanPerson);
//	}
//	
//	@Transactional(readOnly = false)
//	public void delete(JsLoanPerson jsLoanPerson) {
//		super.delete(jsLoanPerson);
//	}
}

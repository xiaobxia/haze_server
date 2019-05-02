package com.info.web.service;

import com.info.web.pojo.JsAwardRecord;
import com.info.web.pojo.JsLoanPerson;


/**
 * 用户
 * @author Administrator
 *
 */
 
public interface IJsLoanPersonService   {
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
   public  JsLoanPerson  findOne(JsLoanPerson jsLoanPerson);
   
	public void save(JsLoanPerson jsLoanPerson);
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

package com.info.web.dao;

import java.util.List;

import com.info.web.pojo.JsLoanPerson;
import org.springframework.stereotype.Repository;

@Repository("jsLoanPersonDao")
public interface IJsLoanPersonDao    {
	
	List<JsLoanPerson> findUserList(JsLoanPerson jsLoanPerson);
	
	
	void insert(JsLoanPerson jsLoanPerson);
	 

	void update(JsLoanPerson jsLoanPerson);

}

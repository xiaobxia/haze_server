package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.ObtainUserShortMessageDao;
import com.info.web.pojo.ObtainUserShortMessage;
import com.info.web.util.PageConfig;

@Service
public class ObtainUserShortMessageService implements IObtainUserShortMessageService {
	
	@Autowired
	private ObtainUserShortMessageDao userShortMessageDao;
	@Autowired
	private IPaginationDao paginationDao;
	
	/**
	 * 查询用户手机里的短信多少
	 */
	public int selectObtainUserShortMessageCount(Integer userId){
		return this.userShortMessageDao.selectObtainUserShortMessageCount(userId);
	}
	
	/**
	 * 添加用户手机里的短信入库 
	 */
	public void saveObtainUserShortMessage(List<ObtainUserShortMessage> soft){
		for (int i = 0; i < soft.size(); i++) {
			this.userShortMessageDao.saveObtainUserShortMessage(soft.get(i));
		}
	}
	
	/**
	 * 查询通讯录列表
	 */
	@SuppressWarnings("unchecked")
	public PageConfig<ObtainUserShortMessage> selectUserShortMsgList(HashMap<String, Object> params){
		params.put(Constant.NAME_SPACE, "ObtainUserShortMessage");
		return paginationDao.findPage("selectUserShortMsgPage", "selectUserShortMsgCount", params,"web");
	}
	
	/**
	 * 根据用户id删除此用户的所有短信
	 */
	public void delObtainUserShortMessage(Integer userId){
		this.userShortMessageDao.delObtainUserShortMessage(userId);
	}
}

package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.ObtainUserShortMessage;
import com.info.web.util.PageConfig;

public interface IObtainUserShortMessageService {
	/**
	 * 查询用户手机里的短信多少
	 */
	public int selectObtainUserShortMessageCount(Integer userId);
	
	/**
	 * 添加用户手机里的短信入库
	 */
	public void saveObtainUserShortMessage(List<ObtainUserShortMessage> soft);
	
	/**
	 * 查询通讯录列表
	 * @param params
	 * @return
	 */
	public PageConfig<ObtainUserShortMessage> selectUserShortMsgList(HashMap<String, Object> params);
	
	/**
	 * 根据用户id删除此用户的所有短信
	 */
	public void delObtainUserShortMessage(Integer userId);
}

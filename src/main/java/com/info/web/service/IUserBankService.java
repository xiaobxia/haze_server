package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.common.reslult.JsonResult;
import com.info.web.pojo.Content;
import com.info.web.pojo.UserCardInfo;
import com.info.web.util.PageConfig;

public interface IUserBankService {
	/**
	 * 查询银行卡列表
	 * @return
	 */
	public List<Map<String,Object>> findAllBankInfo();

	/**
	 * 查询用户下的银行卡信息
	 * @param params
	 * @return
	 */
	public List<UserCardInfo> findUserCardByUserId(HashMap<String,Object> params);
	/**
	 * 查询所有用户银行卡列表
	 * @param params
	 * @return
	 */
	public PageConfig<Map<String,String>> findAllUserBankCardList(HashMap<String,Object> params);


	/**
	 * 根据银行卡号查询该用户银行卡信息
	 * @param cardNo
	 * @return
	 */
	public UserCardInfo findBankCardByCardNo(String cardNo) throws Exception;

}
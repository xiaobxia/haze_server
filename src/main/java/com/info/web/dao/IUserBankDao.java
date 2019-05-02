package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.info.web.pojo.BankAllInfo;
import com.info.web.pojo.UserCardInfo;

public interface IUserBankDao {
	/**
	 * 保存银行卡
	 * @param bank
	 * @return
	 */
	public boolean saveUserbankCard(UserCardInfo cardInfo);
	/**
	 * 修改银行卡
	 * @param bank
	 * @return
	 */
	public boolean updateUserBankCard(UserCardInfo cardInfo);
	/**
	 * 查询银行卡列表
	 * @return
	 */
	public List<Map<String,Object>> findAllBankInfo();
	/**
	 * 更加id查询银行名
	 * @param sqlId
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,String> selectByPrimaryKey(Integer id)throws DataAccessException ;

	/**
	 * 查询用户下的银行卡信息
	 * @param params
	 * @return
	 */
	public List<UserCardInfo> findUserCardByUserId(HashMap<String, Object> params);

	/**
	 * 查询用户银行卡信息
	 * @param id
	 * @return
	 */
	public UserCardInfo findUserBankCard(Integer id);

	/**
	 * 查询用户银行卡信息列表
	 * @param id
	 * @return
	 */
	public List<UserCardInfo> findUserBankCardList(Integer id);

	/**
	 * 根据条件查询所有银行卡信息
	 * @param params
	 * @return
	 */
	public Map<String,String> selectAllBankByParams(Map<String,Object> params);

	/**
	 * 根据银行卡号查询绑卡信息
	 * @param cardNo
	 * @return
	 */
	public List<UserCardInfo> findBankCardByCardNo(String cardNo);
}
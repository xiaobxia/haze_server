package com.info.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.info.web.dao.BaseDao;
import com.info.web.dao.IUserBankDao;
import com.info.web.pojo.UserCardInfo;
@Repository("userBankDaoImpl")
public class UserBankDaoImpl extends BaseDao implements IUserBankDao{
	@Override
	public List<Map<String, Object>> findAllBankInfo() {
		return getSqlSessionTemplate().selectList("com.info.back.dao.IBankAllInfoDao.selectBankAll");
	}
	@Override
	public boolean saveUserbankCard(UserCardInfo cardInfo) {
		return getSqlSessionTemplate().insert("com.info.back.dao.IUserCardInfoDao.saveUserCardInfo", cardInfo) > 0;
	}
	@Override
	public boolean updateUserBankCard(UserCardInfo cardInfo) {
		return getSqlSessionTemplate().update("com.info.back.dao.IUserCardInfoDao.updateUserCardInfo", cardInfo) > 0;
	}
	@Override
	public Map<String,String> selectByPrimaryKey(Integer id)throws DataAccessException {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return getSqlSessionTemplate().selectOne("com.info.back.dao.IBankAllInfoDao.selectBankInfo", params);
	}

	@Override
	public Map<String,String> selectAllBankByParams(Map<String,Object> params)throws DataAccessException {

		return getSqlSessionTemplate().selectOne("com.info.back.dao.IBankAllInfoDao.selectBankInfo", params);
	}
	@Override
	public List<UserCardInfo> findUserCardByUserId(HashMap<String, Object> params) {
		return getSqlSessionTemplate().selectList("com.info.back.dao.IUserCardInfoDao.findUserCardByUserId", params);
	}

	@Override
	public UserCardInfo findUserBankCard(Integer id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		List<UserCardInfo> lsit= getSqlSessionTemplate().selectList("com.info.back.dao.IUserCardInfoDao.selectUserBankCard", params);
		if(lsit!=null&&lsit.size()>0){
			return lsit.get(0);
		}
		return null;
	}

	/**
	 * 查询用户银行卡信息列表
	 * @param id
	 * @return
	 */
	@Override
	public List<UserCardInfo> findUserBankCardList(Integer id){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		List<UserCardInfo> lsit= getSqlSessionTemplate().selectList("com.info.back.dao.IUserCardInfoDao.findUserBankCardList", params);
		return lsit;
	}

	/**
	 * 根据银行卡号查询绑卡信息
	 * @param cardNo
	 * @return
	 */
	@Override
	public List<UserCardInfo> findBankCardByCardNo(String cardNo){
		return getSqlSessionTemplate().selectList("com.info.back.dao.IUserCardInfoDao.findBankCardByCardNo", cardNo);
	}
}
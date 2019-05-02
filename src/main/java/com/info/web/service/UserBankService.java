package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserBankDao;
import com.info.web.pojo.UserCardInfo;
import com.info.web.util.PageConfig;
@Slf4j
@Service
public class UserBankService implements IUserBankService {
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	@Qualifier("userBankDaoImpl")
	private IUserBankDao userBankDao;



	@Override
	public List<Map<String, Object>> findAllBankInfo() {
		return this.userBankDao.findAllBankInfo();
	}

	@Override
	public List<UserCardInfo> findUserCardByUserId(HashMap<String, Object> params) {
		return userBankDao.findUserCardByUserId(params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Map<String, String>> findAllUserBankCardList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "UserCardInfo");
		return paginationDao.findPage("findUserBankList", "findUserBankTotal", params, "back");
	}


	/**
	 * 根据银行卡号查询该用户银行卡信息
	 *
	 */
	@Override
	public UserCardInfo findBankCardByCardNo(String cardNo){
		List<UserCardInfo> bankCard = userBankDao.findBankCardByCardNo(cardNo);
		if (null == bankCard || bankCard.size() == 0) {
			log.info("银行卡不存在!");
			return null;
		}
		return bankCard.get(0);
	}
}
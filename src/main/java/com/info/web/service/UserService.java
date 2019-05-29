package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.pojo.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserDao;
import com.info.web.dao.IUserBankDao;
import com.info.web.dao.UserCerticationDao;
import com.info.web.pojo.User;
import com.info.web.pojo.UserCardInfo;
import com.info.web.pojo.UserCertification;
import com.info.web.util.PageConfig;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	@Qualifier("userCertication")
	private UserCerticationDao userCerticationDao;
	@Autowired
	@Qualifier("userBankDaoImpl")
	private IUserBankDao userBankDao;

	@Override
	public User getUserPhoneAndName(int id) {
		return userDao.getUserPhoneAndName(id);
	}

	/**
	 * 查询用户是否存在
	 */
	public User searchUserByCheckTel(HashMap<String, Object> map){
		return this.userDao.searchUserByCheckTel(map);
	}
	
	/**
	 * 根据ID查询用户
	 */
	public User searchByUserid(int id){
		return this.userDao.searchByUserid(id);
	}

	@Override
	public int updateAmountAvailableByUserId(User user) {
		return userDao.updateAmountAvailableByUserId(user);
	}

	/**
	 * 根据ID查询用户
	 */
	public User selectCollectionByUserId(int id){
		return this.userDao.selectCollectionByUserId(id);
	}
	
	/**
	 * 根据用户ID查询验证码是否存在
	 */
	public User searchByInviteUserid(Map<String, String> map){
		return this.userDao.searchByInviteUserid(map);
	}
	
	/**
	 * 根据用户ID,手机号,证件号查询用户是否存在
	 */
	public User searchByUphoneAndUid(Map<String, Object> map){
		return this.userDao.searchByUphoneAndUid(map);
	}
	
	
	/**
	 * 用户注册
	 */
	public void saveUser(User user){
		this.userDao.saveUser(user);
	}

	/**
	 * 用户登录
	 */
	public User searchUserByLogin(HashMap<String, Object> map){
		return this.userDao.searchUserByLogin(map);
	}
	
	/***
	 * 修改用户信息
	 */
	public int updateByPrimaryKeyUser(User user){
		return this.userDao.updateByPrimaryKeyUser(user);
	}

	/**
	 * 后台查询 分页
	 */
	@SuppressWarnings("unchecked")
	public PageConfig<User> getUserPage(HashMap<String, Object> params){
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.findPage("selectUserPage", "selectUserCount", params,"web");
	}

	@Override
	public PageConfig<User> noRenewalList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.findPage("noRenewalList", "noRenewalListCount", params,"web");
	}

	public List<UserCertification> findCerticationList(){
		return this.userCerticationDao.findCerticationList();
	}

	@Override
	public Map<String, Object> checkUserCalendar(Integer id) {
		HashMap<String, Object> params=new HashMap<>();
		params.put("id", id);
		return userCerticationDao.checkUserCalendar(params);
	}

	@Override
	public UserCardInfo findUserBankCard(Integer id) {
		return userBankDao.findUserBankCard(id);
	}

	/**
	 * 查询用户所有银行卡信息
	 */
	@Override
	public List<UserCardInfo> findUserbankCardList(Integer id){
		return userBankDao.findUserBankCardList(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Map<String, String>> realNmaeList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.findPage("selectUserRealNamePage", "selectUserReanlNameCount", params,"web");
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Map<String, String>> certificationList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.findPage("selectCertificationPage", "selectCertificationCount", params,"web");
	}

	@Override
	public User searchByUserIDCard(String idCard) {
		return this.userDao.searchByUserIDCard(idCard);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<User> findJxlList(HashMap<String, Object> params){
		params.put(Constant.NAME_SPACE, "User");
		return paginationDao.getMyPage("findJxlWait", "findJxlWaitCount", params, "web");
	}

	@Override
	public List<UserDetail> getUserByChannelid(Map<String, Object> params) {
		params.put(Constant.NAME_SPACE,"User");

		return userDao.getUserByChannelid(params);
	}

	@Override
	public void updateJxl(Integer userId){
		userDao.updateUserJxl(userId);
		userDao.updateIndexInfoJxl(userId);
	}

	@Override
	public int findJxlStatus(Integer userId) {
		return userDao.findJxlStatus(userId);
	}

	@Override
	public HashMap<String, Object> selectPushId(Integer userId) {
		HashMap<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		return userDao.selectPushId(params);
	}

	@Override
	public List<Integer> getRoleByUserId(Integer id){
		return userDao.getRoleByUserId(id);
	}

	@Override
	public String selectGxbReportDataHtml(Integer userId) {
		return userDao.selectGxbReportDataHtml(userId);
	}
}

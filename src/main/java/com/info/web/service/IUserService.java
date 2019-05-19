package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.pojo.UserDetail;
import com.info.web.pojo.User;
import com.info.web.pojo.UserCardInfo;
import com.info.web.pojo.UserCertification;
import com.info.web.util.PageConfig;

public interface IUserService {
	User getUserPhoneAndName(int id);

	/**
	 * 查询用户是否存在
	 * 
	 * @param map
	 * @return
	 */
	public User searchUserByCheckTel(HashMap<String, Object> map);

	/**
	 * 根据ID查询用户
	 * 
	 * @param map
	 * @return
	 */
	public User searchByUserid(int id);

	int updateAmountAvailableByUserId(User user);

	public User selectCollectionByUserId(int id);

	/**
	 * 根据用户ID查询验证码是否存在
	 * 
	 * @param map
	 * @return
	 */
	public User searchByInviteUserid(Map<String, String> map);

	/**
	 * 根据用户ID,手机号,证件号查询用户是否存在
	 * 
	 * @param map
	 * @return
	 */
	public User searchByUphoneAndUid(Map<String, Object> map);

	/**
	 * 用户注册
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 用户登录
	 * 
	 * @param map
	 * @return
	 */
	public User searchUserByLogin(HashMap<String, Object> map);

	/***
	 * 修改用户信息
	 */
	public int updateByPrimaryKeyUser(User user);

	/**
	 * 后台查询 分页
	 */
	public PageConfig<User> getUserPage(HashMap<String, Object> params);

	/**
	 * 查询用户认证列表
	 * 
	 * @return
	 */
	public List<UserCertification> findCerticationList();

	/**
	 * 验证用户已认证的选项
	 */
	public Map<String, Object> checkUserCalendar(Integer id);

	/**
	 * 查询用户银行卡信息
	 * 
	 * @param id
	 * @return
	 */
	public UserCardInfo findUserBankCard(Integer id);

	/**
	 * 查询用户所有银行卡信息
	 * @param id
	 * @return
	 */
	public List<UserCardInfo> findUserbankCardList(Integer id);

	/**
	 * 查询实名认证列表
	 * 
	 * @param params
	 * @return
	 */
	public PageConfig<Map<String, String>> realNmaeList(
            HashMap<String, Object> params);

	/**
	 * 查询用户认证状态列表
	 * 
	 * @param params
	 * @return
	 */
	public PageConfig<Map<String, String>> certificationList(
            HashMap<String, Object> params);

	/**
	 * 根据身份证查询用户是否存在
	 * 
	 * @param idCard
	 * @return
	 */
	public User searchByUserIDCard(String idCard);

	/**
	 * 得到超过一小时仍未生成token的用户
	 * 
	 * @param params
	 *            userPhone 用户手机号码，全匹配
	 * @return
	 */
	PageConfig<User> findJxlList(HashMap<String, Object> params);

	/**
	 * 根据channelid和时间查询所有用户
	 * */
	List<UserDetail> getUserByChannelid(Map<String,Object> params);

	void updateJxl(Integer userId);
	public int findJxlStatus(Integer userId);
	
	/**
	 * 根据inviteUserid，查询pushId
	 * @return
	 */
	public HashMap<String,Object> selectPushId(Integer userId);

	List<Integer> getRoleByUserId(Integer id);
}

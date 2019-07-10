package com.info.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.pojo.CustomerClassArrange;
import com.info.back.pojo.UserDetail;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {
	User getUserPhoneAndName(int id);

	/**
	 * 查询用户是否存在
	 * @param map
	 * @return
	 */
	public User searchUserByCheckTel(HashMap<String, Object> map);
	
	/**
	 * 根据ID查询用户
	 * @param map
	 * @return
	 */
	public User searchByUserid(int id);

	public User selectCollectionByUserId(int id);
	
	/**
	 * 根据用户ID查询验证码是否存在
	 * @param map
	 * @return
	 */
	public User searchByInviteUserid(Map<String, String> map);
	
	/**
	 * 根据用户ID,手机号,证件号查询用户是否存在
	 * @param map
	 * @return
	 */
	public User searchByUphoneAndUid(Map<String, Object> map);
	
	/**
	 * 用户注册
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public User searchUserByLogin(HashMap<String, Object> map);
	
	/***
	 * 修改用户信息
	 */
	public int updateByPrimaryKeyUser(User user);
	/**
	 * 根据身份证查询用户是否存在
	 * @param idCard
	 * @return
	 */
	public User searchByUserIDCard(String idCard);
	/**
	 * 修改用户为未认证
	 * @param userId
	 * @return
	 */
	public int updateIndexInfoJxl(Integer userId);
	/**
	 * 修改用户为未认证
	 * @param userId
	 * @return
	 */
	public int updateUserJxl(Integer userId);
	
	public int findJxlStatus(Integer userId);

	int updateAmountAvailableByUserId(User user);
	
	public HashMap<String, Object> selectPushId(HashMap<String, Object> map);

	/**
	 * 查询指定渠道用户
	 * @param channelId
	 * @return
	 */
	List<User> findByChannelId(Integer channelId);

	List<Integer> getRoleByUserId(Integer id);

	Map<String,Object> selectCertificationPage(Map<String, Object> map);

	CustomerClassArrange getCustomerClassById(@Param("id")String id);

	CustomerClassArrange getCustomerClassByDate(@Param("date")String date);

	void updateCustomerClass(CustomerClassArrange customerClassArrange);

	void saveCustomerClass(CustomerClassArrange customerClassArrange);

	List<BackUser> getCustomerList();

	List<Map<String,Object>>  getCustomerClassForExcel(String [] idArray);

	String getLastClassDate();

	List<UserDetail> getUserByChannelid(Map<String,Object> params);

	String selectGxbReportDataHtml(Integer userId);

	String selectReportDataHtml(Integer userId);

	Integer selectUserIdByPhone(String userPhone);

	Integer updateUserQuota(@Param("userId") int userId, @Param("productId") int productId, @Param("borrowDay") int borrowDay, @Param("nowLimit") BigDecimal nowLimit);

	Integer addUserQuota(@Param("userId") int userId, @Param("productId") int productId, @Param("nowLimit") BigDecimal nowLimit, @Param("borrowDay") int borrowDay);

	Integer queryCountByUserId(@Param("userId") int userId);

	Integer queryUserQuotaProductId(@Param("userId") int userId);
}

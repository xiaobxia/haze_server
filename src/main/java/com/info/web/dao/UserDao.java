package com.info.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.pojo.CustomerClassArrange;
import com.info.back.pojo.UserDetail;
import com.info.web.pojo.BackUser;
import org.springframework.stereotype.Repository;

import com.info.web.pojo.User;

@Repository
public class UserDao extends BaseDao implements IUserDao {
	@Override
	public User getUserPhoneAndName(int id) {
		return this.getSqlSessionTemplate().selectOne("getUserPhoneAndName", id);
	}

	/**
	 * 查询用户是否存在
	 * @param map
	 * @return
	 */
	public User searchUserByCheckTel(HashMap<String, Object> map){
		return this.getSqlSessionTemplate().selectOne("searchUserByCheckTel", map);
	}
	
	/**
	 * 根据ID查询用户
	 * @param id
	 * @return
	 */
	public User searchByUserid(int id){
		return this.getSqlSessionTemplate().selectOne("selectByUserId", id);
	}

	/**
	 * 催收根据ID查询用户
	 * @param id
	 * @return
	 */
	public User selectCollectionByUserId(int id){
		return this.getSqlSessionTemplate().selectOne("selectCollectionByUserId", id);
	}

	/**
	 * 根据用户ID查询验证码是否存在
	 * @param map
	 * @return
	 */
	public User searchByInviteUserid(Map<String, String> map){
		return this.getSqlSessionTemplate().selectOne("selectByUserIds", map);
	}
	
	/**
	 * 根据用户ID,手机号,证件号查询用户是否存在
	 * @param map
	 * @return
	 */
	public User searchByUphoneAndUid(Map<String, Object> map){
		return this.getSqlSessionTemplate().selectOne("selectByUPhoneAndUid", map);
	}
	/**
	 * 用户注册
	 * @param user
	 */
	public void saveUser(User user){
		this.getSqlSessionTemplate().insert("saveUser", user);
	}

	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public User searchUserByLogin(HashMap<String, Object> map){
		return this.getSqlSessionTemplate().selectOne("searchUserByLogin", map);
	}
	
	/***
	 * 修改用户信息
	 */
	public int updateByPrimaryKeyUser(User user){
		return this.getSqlSessionTemplate().update("updateUser", user);
	}

	@Override
	public int updateAmountAvailableByUserId(User user) {
		return this.getSqlSessionTemplate().update("updateAmountAvailableByUserId", user);
	}

	@Override
	public User searchByUserIDCard(String idCard) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("idCard", idCard);
		return this.getSqlSessionTemplate().selectOne("selectByUserIdCard", map);
	}
	@Override
	public int updateIndexInfoJxl(Integer userId){
		return this.getSqlSessionTemplate().update("updateResetIndexInfoJxl",userId);
	}
	@Override
	public int updateUserJxl(Integer userId){
		return this.getSqlSessionTemplate().update("updateResetUserJxl",userId);
	}
	@Override
	public int findJxlStatus(Integer userId){
		return this.getSqlSessionTemplate().selectOne("findResetJxlStatus",userId);
	}
	
	@Override
	public HashMap<String, Object> selectPushId(HashMap<String, Object> map) {
		return this.getSqlSessionTemplate().selectOne("selectPushIdByInviteUserid",map);
	}

	@Override
	public List<User> findByChannelId(Integer channelId) {
		return this.getSqlSessionTemplate().selectList("selectByChannelId", channelId);
	}

	@Override
	public List<Integer> getRoleByUserId(Integer id){
		return this.getSqlSessionTemplate().selectList("getRoleByUserId", id);
	}

	@Override
	public Map<String,Object> selectCertificationPage(Map<String,Object> map){
		return this.getSqlSessionTemplate().selectOne("selectCertificationPage",map);
	}

	@Override
	public CustomerClassArrange getCustomerClassById(String id) {
		return this.getSqlSessionTemplate().selectOne("getCustomerClassById",id);
	}

	@Override
	public CustomerClassArrange getCustomerClassByDate(String date) {
		return this.getSqlSessionTemplate().selectOne("getCustomerClassByDate",date);
	}


	@Override
	public void updateCustomerClass(CustomerClassArrange customerClassArrange) {
		this.getSqlSessionTemplate().update("updateCustomerClass",customerClassArrange);
	}

	@Override
	public void saveCustomerClass(CustomerClassArrange customerClassArrange) {
		this.getSqlSessionTemplate().insert("saveCustomerClass",customerClassArrange);
	}

	@Override
	public List<BackUser> getCustomerList() {
		return this.getSqlSessionTemplate().selectList("getCustomerList");
	}

	@Override
	public List<Map<String,Object>> getCustomerClassForExcel(String[] ids) {
		return this.getSqlSessionTemplate().selectList("getCustomerClassForExcel",ids);
	}

	@Override
	public String getLastClassDate() {
		return this.getSqlSessionTemplate().selectOne("getLastClassDate");
	}

	@Override
	public List<UserDetail> getUserByChannelid(Map<String, Object> params) {
		return this.getSqlSessionTemplate().selectList("getUserByChannelid",params);
	}

	@Override
	public String selectGxbReportDataHtml(Integer userId) {
		return this.getSqlSessionTemplate().selectOne("selectGxbReportDataHtml", userId);
	}

	@Override
	public String selectReportDataHtml(Integer userId) {
		return this.getSqlSessionTemplate().selectOne("selectReportDataHtml", userId);
	}

	@Override
	public Integer selectUserIdByPhone(String userPhone) {
		return this.getSqlSessionTemplate().selectOne("selectUserIdByPhone", userPhone);
	}

	@Override
	public Integer updateUserQuota(int userId, int productId, int borrowDay, BigDecimal nowLimit) {
		Map<String, Object> params = new HashMap();
		params.put("userId", userId);
		params.put("productId", productId);
		params.put("borrowDay", borrowDay);
		params.put("nowLimit", nowLimit);
		return this.getSqlSessionTemplate().update("updateUserQuota", params);
	}

	@Override
	public Integer addUserQuota(int userId, int productId, BigDecimal nowLimit, int borrowDay) {
		Map<String, Object> params = new HashMap();
		params.put("userId", userId);
		params.put("productId", productId);
		params.put("borrowDay", borrowDay);
		params.put("nowLimit", nowLimit);
		return this.getSqlSessionTemplate().insert("addUserQuota", params);
	}

	@Override
	public Integer queryCountByUserId(int userId) {
		return this.getSqlSessionTemplate().selectOne("queryCountByUserId", userId);
	}

	@Override
	public Integer queryUserQuotaProductId(int userId) {
		return this.getSqlSessionTemplate().selectOne("queryUserQuotaProductId", userId);
	}
}

package com.info.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.*;
import com.info.web.util.PageConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

/**
 * 
 * 类描述：推广 <br>
 * 创建人：<br>
 * 创建时间：2016年12月21日 14:19:54 <br>
 * 
 * @version
 * 
 */
public interface IChannelInfoService {
	/**
	 * 根据渠道商编码查询渠道商是否存在
	 * @param code
	 * @return
	 */
	int getCountSuperChannelCode(String code);
	/**
	 * 根据条件查询用户
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态
	 * @return 角色list
	 */
	public List<ChannelInfo> findAll(Map<String, Object> params);

	public List<ChannelInfo> findAll2(Map<String, Object> params);
	
	public List<String> findAllChUser(Map<String, Object> params);
	
	public Integer findParamsCount(Map<String, Object> params);

	/**
	 * 根据条件查询用户<br>
	 * 只返回第一个用户对象<br>
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态 参数名：id 含义：用户主键
	 * @return 用户对象
	 */
	public ChannelInfo findOneChannelInfo(HashMap<String, Object> params);
	public ChannelInfo findOneChannelInfoNew(HashMap<String, Object> params);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteChannelInfoById(Integer id);

	/**
	 * 插入用户对象
	 * 
	 * @param backUser
	 */
	public void insert(ChannelInfo channelInfo);
	/**
	 * 插入推广员用户
	 * 
	 * @param backUser
	 */
	public void insertChannelUserInfo(Map<String, Object> param);


	public void insertChannelDynamic(ChannelDynamic channelDynamic, User user);

	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateById(ChannelInfo channelInfo);

	/**
	 * 分页查询推广渠道信息
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<ChannelInfo> findPage(HashMap<String, Object> params);
	
	/**
	 * 分页查询推广员信息
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<ChannelInfo> findChannelUserPage(HashMap<String, Object> params);
	
	/**
	 * 分页查询推广记录
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<ChannelInfo> findChannelRecordPage(HashMap<String, Object> params);
	/**
	 * 根据渠道code查询渠道信息
	 * @param userName
	 * @return
	 */
	public ChannelInfo findChannelCode(String channelCode);
	/**
	 * 查询渠道推广用户借款记录
	 * @param params
	 * @return
	 */
	public PageConfig<HashMap<String, Object>> findChannelUserBorrowRecord(HashMap<String, Object> params);
	/**
	 * 统计渠道推广用户 和金额
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> countChanellUser(Integer channelId);
	
	
	
	/**********************添加推广渠道上级菜单**************************/
	
	/**
	 * 渠道商管理分页(渠道的上级)
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<ChannelSuperInfo> findChannelSuperPage(HashMap<String, Object> params);
	
	
	/**
	 *新增渠道上级
	 */
	public void insertChannelSuperInfo(ChannelSuperInfo channelSuperInfo);
	
	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateChannelSuperById(ChannelSuperInfo channelSuperInfo);
	
	public ChannelSuperInfo findOneChannelSuperInfo(HashMap<String, Object> params);
	
	public List<ChannelSuperInfo> findSuperAll(Map<String, Object> params);
	
	/**********************添加推广渠道费率菜单**************************/
	
	/**
	 * 渠道费率管理分页
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<ChannelRate> findChannelRatePage(HashMap<String, Object> params);
	
	
	/**
	 * 添加费率
//	 * @param channelSuperInfo
	 */
	public void insertChannelRate(ChannelRate channelRate);
	
	/**
	 * 修改渠道费率
	 * @param channelRate
	 */
	public void updateChannelRateById(ChannelRate channelRate);
	
	/**
	 * 查询单个渠道费率
	 * @param params
	 * @return
	 */
	public ChannelRate findOneChannelRateInfo(HashMap<String, Object> params);
	/**
	 * 查询所有费率
	 * @param params
	 * @return
	 */
	public List<ChannelRate> findChannelRateAll(Map<String, Object> params);

	/**
	 * New 动态配置
	 * @param params
	 * @return
     */
	public List<ChannelInfo> findAllUserList(Map<String, Object> params);
	/**
	 * New分页查询推广员
	 *
	 * @param params
	 * @return
	 */
	PageConfig<ChannelInfo> findUserAllPage(HashMap<String, Object> params);

	PageConfig<ChannelGetUserInfo> findChannelGetUserInfo(HashMap<String, Object> params);

	public Map<String,Object> selectChannelByUserId(Map<String, Object> map);

	/**
	 * 查询当天某类用户的放款笔数
	 * @param loanTime
	 * @param userId
	 * @return
	 */
	Integer findLoanCount(Date loanTime, List<String> userId);
	/**
	 * 查询当天某类用户的还款笔数
	 * @param repayTime
	 * @param userId
	 * @return
	 */
	Integer findRepayCount(Date repayTime, List<String> userId);

	List<String> findUserId(Integer channelId);
	Integer findqqCount(Integer channelId);
	Integer findWechatCount(Integer channelId);
	Integer findApplyCount(Date applyTime, List<String> userId);
}

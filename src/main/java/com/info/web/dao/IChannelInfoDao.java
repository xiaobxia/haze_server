package com.info.web.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.OutChannelLook;
import com.info.web.pojo.OveChannelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Repository;

@Repository("channelInfoDao")
public interface IChannelInfoDao {

	int getCountSuperChannelCode(String code);

	/**
	 * 根据条件查询
	 *
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<ChannelInfo> findAll(Map<String, Object> params);

	public List<ChannelInfo> findAll2(Map<String, Object> params);

	public List<String> findAllChUser(Map<String, Object> params);


	Integer findRecordAllCount(Map<String, Object> map);

	/**
	 * 插入对象
	 *
	 * @param
	 */
	public void insert(ChannelInfo channelInfo);

	/**
	 * 根据主键删除对象
	 *
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 更新对象
	 *
	 * @param backUser
	 */
	public void updateById(ChannelInfo channelInfo);


	/**
	 * 插入推广员用户
	 *
	 * @param backUser
	 */
	public void insertChannelUserInfo(Map<String, Object> param);
	/**
	 * 根据id查询渠道商
	 * @param id
	 * @return
	 */
	public ChannelInfo findChannelCode(String id);

	public HashMap<String, Object> countChanellUser(Integer channelId);

	public List<ChannelInfo> findAllUserList(Map<String, Object> params);


	public Map<String,Object> selectChannelByUserId(Map<String, Object> map);

	List<ChannelInfo> findAllChannels();

	/**
	 * 查询当天某类用户的放款笔数
	 * @param loanTime
	 * @param userId
	 * @return
	 */
	Integer findLoanCount(@Param("loanTime")Date loanTime,@Param("userId") List<String> userId);
	/**
	 * 查询当天某类用户的还款笔数
	 * @param repayTime
	 * @param userId
	 * @return
	 */
	Integer findRepayCount(@Param("repayTime")Date repayTime,@Param("userId") List<String> userId);

	/**
	 * 查询当日回全款的用户数
	 * @param repayTime
	 * @param userId
	 * @return
	 */
	Integer findAllRepayCount(@Param("repayTime") Date repayTime,@Param("userId") List<String> userId);
    Integer findApplyCount(@Param("applyTime") Date applyTime,@Param("userId") List<String> userId);
	List<String> findUserId(@Param("channeId") Integer channeId);
    Integer findqqCount(@Param("channelId") Integer channelId,@Param("reportDate") Date reportDate);
    Integer findWechatCount(@Param("channelId") Integer channelId,@Param("reportDate") Date reportDate);
	/**
	 * 当天续借人数
	 * @param userId
	 * @param applyTime
	 * @return
	 */
    Integer xujieCount(@Param("userId") List<String> userId,@Param("applyTime") Date applyTime);
    Integer xujieSucCount(@Param("userId") List<String> userId,@Param("loanTime") Date loanTime);
    Integer saveChannelUrl(ChannelInfo channelInfo);
    List<String> findLoanUserId(@Param("userId") List<String> userId,@Param("loanTime") Date loanTime);
}

package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelInfo;
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


	Integer findRecordAllCount(Map<String, Object> params);

	/**
	 * 插入对象
	 *
	 * @param backUser
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
}

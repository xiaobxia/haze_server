package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.ChannelSuperInfo;
import org.springframework.stereotype.Repository;

@Repository("channelSuperInfoDao")
public interface IChannelSuperInfoDao {
	
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<ChannelSuperInfo> findAll(Map<String, Object> params);
	

	
 
	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(ChannelSuperInfo channelSuperInfo);

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
	public void updateById(ChannelSuperInfo channelSuperInfo);

	
	 
	/**
	 * 根据id查询渠道商
	 * @param id
	 * @return
	 */
	public ChannelInfo findChannelCode(String id);
	
	
	 
	public ChannelSuperInfo selectById(String id);

 
}

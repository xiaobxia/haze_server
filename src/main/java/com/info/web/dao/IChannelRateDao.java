package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelRate;
import org.springframework.stereotype.Repository;

@Repository("channelRateDao")
public interface IChannelRateDao {
	
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<ChannelRate> findAll(Map<String, Object> params);
	

	
 
	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(ChannelRate channelRate);

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
	public void updateById(ChannelRate channelRate);
 
	public ChannelRate selectById(Integer id);

 
}

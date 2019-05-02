package com.info.web.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.Content;
import com.info.web.util.PageConfig;

public interface IContentService {

	/**
	 * 插入
	 * 
	 * @param zbNews
	 * @return
	 */
	public int insert(Content content);

	/**
	 * 删除
	 * 
	 * @return
	 */
	public int delete(Content content);

	/**
	 * 修改
	 * 
	 * @return
	 */
	public int update(Content content);

	/**
	 * 修改点击量
	 * 
	 * @return
	 */
	public int updateViewCount(Integer id);

	/**
	 * 根据主键查询
	 * 
	 * @param id主键
	 * @return
	 */
	public Content findById(Integer id);

	/**
	 * 前台列表查看
	 * 
	 * @param params
	 *            channelType所属栏目
	 * @return
	 */
	PageConfig<Content> findPage(HashMap<String, Object> params);

	/**
	 * 后台列表查看
	 * 
	 * @param params
	 *            channelType所属栏目<br>
	 *            contentTitle标题<br>
	 *            contentTxt内容
	 * @return
	 */
	PageConfig<Content> findBackPage(HashMap<String, Object> params);
	/**
	 * 批量插入文章
	 * 
	 * @param list
	 * @return
	 */
	public int batchInsert(List<Content> list);

	/**
	 * 批量删除文章
	 * 
	 * @param list
	 * @return
	 */
	public int deleteByFromUrl(List<String> list);
}

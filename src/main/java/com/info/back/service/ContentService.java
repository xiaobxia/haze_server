package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IContentDao;
import com.info.back.utils.RequestUtils;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.Content;
import com.info.web.util.PageConfig;

@Service("backContentService")
public class ContentService implements IContentService{
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IContentDao contentDao;

	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Content> findBackPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Content");
		return paginationDao.findPage("findList", "findTotal", params, "back");
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Content> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Content");
		return paginationDao
				.findPage("findAll", "findAllCount", params, "back");
	}

	@Override
	public int delete(Content content) {
		return contentDao.delete(content);
	}

	@Override
	public Content findById(Integer id) {
		return contentDao.findById(id);
	}

	@Override
	public int insert(Content content) {
		if (StringUtils.isBlank(content.getAddIp())) {
			content.setAddIp(RequestUtils.getIpAddr());
		}
		return contentDao.insert(content);
	}

	@Override
	public int update(Content content) {
		return contentDao.update(content);
	}

	@Override
	public int updateViewCount(Integer id) {
		return contentDao.updateViewCount(id);
	}

	@Override
	public int batchInsert(List<Content> list) {
		return contentDao.batchInsert(list);
	}

	@Override
	public int deleteByFromUrl(List<String> list) {
		return contentDao.deleteByFromUrl(list);
	}

	@Override
	public int deleteDrop(Integer id) {
		return contentDao.deleteDrop(id);
	}

}

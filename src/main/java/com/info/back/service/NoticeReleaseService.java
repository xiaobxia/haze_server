package com.info.back.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.INoticeReleaseDao;
import com.info.constant.Constant;
import com.info.web.dao.PaginationDao;
import com.info.web.pojo.NoticeRelease;
import com.info.web.util.PageConfig;

@Service
public class NoticeReleaseService implements INoticeReleaseService {
	
	@Autowired
	private INoticeReleaseDao noticeReleaseDao;
	@Autowired
	private PaginationDao paginationDao;
	

	@Override
	public void insert(NoticeRelease noticeRelease) {
		noticeReleaseDao.insert(noticeRelease);
	}

	@Override
	public void update(NoticeRelease noticeRelease) {
		noticeReleaseDao.update(noticeRelease);
	}
	@SuppressWarnings("unchecked")
	public PageConfig<NoticeRelease> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "NoticeRelease");
		PageConfig<NoticeRelease> pageConfig = new PageConfig<NoticeRelease>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,"back");
		return pageConfig;
	}
	
	

}

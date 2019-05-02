package com.info.back.service;

import java.util.HashMap;

import com.info.web.pojo.NoticeRelease;
import com.info.web.util.PageConfig;

public interface INoticeReleaseService {
	
	public void insert(NoticeRelease noticeRelease);	
	public void update(NoticeRelease noticeRelease);
	public PageConfig<NoticeRelease> findPage(HashMap<String, Object> params);

}

package com.info.back.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.NoticeRelease;
import org.springframework.stereotype.Repository;

@Repository("noticeReleaseDao")
public interface INoticeReleaseDao {
	
	public NoticeRelease selectById(Integer id);
	
	public void insert(NoticeRelease noticeRelease);
	
	public void update(NoticeRelease noticeRelease);
	
	public List<NoticeRelease> findAll(HashMap<String, Object> params);

}

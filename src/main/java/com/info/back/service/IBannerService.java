package com.info.back.service;

import java.util.Map;

import com.info.web.pojo.Banner;
import com.info.web.util.PageConfig;

public interface IBannerService {
	
	public void save(Banner banner);
	
	public void delete(int id);
	
	public int deleteIndexCache();
	public void update(Banner banner);
	
	public Banner findById(int id);
	
	public PageConfig<Banner> findPage(Map<String, Object> params);
}

package com.info.back.service;

import java.util.Map;

import com.info.web.pojo.Advertisement;
import com.info.web.util.PageConfig;

public interface IAdvertisementService {
	
	public void save(Advertisement ad);
	
	public void delete(int id);
	
	public void update(Advertisement ad);
	
	public Advertisement findById(int id);
	
	public PageConfig<Advertisement> findPage(Map<String, Object> params);
	
}

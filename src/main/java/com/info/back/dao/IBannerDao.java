package com.info.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.Banner;

@Repository
public interface IBannerDao {
	public void save(Banner banner);
	
	public void delete(int id);
	
	public void update(Banner banner);
	
	public Integer countByCondition(Map<String, Object> params);
	
	public Banner findById(int id);
	
	public List<Banner> findByCondition(Map<String, Object> params);
	
	public List<Banner> findAll();
	
	public int deleteIndexCache();
	
}

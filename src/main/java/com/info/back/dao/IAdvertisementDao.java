package com.info.back.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.Advertisement;
import org.springframework.stereotype.Repository;

@Repository("advertisementDao")
public interface IAdvertisementDao {
	public void save(Advertisement ad);
	
	public void delete(int id);
	
	public void update(Advertisement ad);
	
	public Integer countByCondition(Map<String, Object> params);
	
	public Advertisement findById(int id);

	public List<Advertisement> findByCondition(Map<String, Object> params);
	
}

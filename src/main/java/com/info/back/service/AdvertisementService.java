package com.info.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IAdvertisementDao;
import com.info.web.pojo.Advertisement;
import com.info.web.util.PageConfig;

@Service
public class AdvertisementService implements IAdvertisementService {
	@Autowired
	private IAdvertisementDao advertisementDao;

	@Override
	public void save(Advertisement ad) {
		advertisementDao.save(ad);
	}

	@Override
	public void delete(int id) {
		advertisementDao.delete(id);
	}

	@Override
	public void update(Advertisement ad) {
		advertisementDao.update(ad);
	}

	@Override
	public Advertisement findById(int id) {
		return advertisementDao.findById(id);
	}

	@Override
	public PageConfig<Advertisement> findPage(Map<String, Object> params) {
		String currentpageStr = params.get("pageNum") == null ? "1" : params.get("pageNum").toString();
		String pagesizeStr = params.get("numPerPage") == null ? "10" : params.get("numPerPage").toString();
		Integer currentpage = Integer.valueOf(currentpageStr);
		Integer pagesize = Integer.valueOf(pagesizeStr);
		params.put("beginIndex", (currentpage - 1) * pagesize); 
		params.put("pagesize", pagesize); 
		PageConfig<Advertisement> pageConfig = new PageConfig<>();
		Integer count = advertisementDao.countByCondition(params);
		List<Advertisement> list =  advertisementDao.findByCondition(params);
		pageConfig.setTotalResultSize(count);
		pageConfig.setCurrentPage(currentpage);
		pageConfig.setItems(list);
		pageConfig.setPageSize(pagesize);
		return pageConfig;
	}

}

package com.info.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBannerDao;
import com.info.web.pojo.Banner;
import com.info.web.util.PageConfig;

@Service
public class BannerService implements IBannerService {

	@Autowired
	private IBannerDao bannerDao;
	
	@Override
	public void save(Banner banner) {
		bannerDao.save(banner);
	}

	@Override
	public void delete(int id) {
		bannerDao.delete(id);
	}

	@Override
	public void update(Banner banner) {
		bannerDao.update(banner);
	}

	@Override
	public Banner findById(int id) {
		return bannerDao.findById(id);
	}

	@Override
	public PageConfig<Banner> findPage(Map<String, Object> params) {
		String currentpageStr = params.get("pageNum") == null ? "1" : params.get("pageNum").toString();
		String pagesizeStr = params.get("numPerPage") == null ? "10" : params.get("numPerPage").toString();
		Integer currentpage = Integer.valueOf(currentpageStr);
		Integer pagesize = Integer.valueOf(pagesizeStr);
		params.put("beginIndex", (currentpage - 1) * pagesize); 
		params.put("pagesize", pagesize); 
		PageConfig<Banner> pageConfig = new PageConfig<Banner>();
		Integer count = bannerDao.countByCondition(params);
		List<Banner> list =  bannerDao.findByCondition(params);
		pageConfig.setTotalResultSize(count);
		pageConfig.setCurrentPage(currentpage);
		pageConfig.setItems(list);
		pageConfig.setPageSize(pagesize);
		return pageConfig;
	}

	@Override
	public int deleteIndexCache() {
		return bannerDao.deleteIndexCache();
	}

}

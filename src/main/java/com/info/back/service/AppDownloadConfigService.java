package com.info.back.service;

import com.info.base.dao.BaseMapper;
import com.info.base.service.impl.BaseServiceImpl;
import com.info.back.dao.IAppDownloadConfigDao;
import com.info.back.pojo.AppDownloadConfig;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.util.PageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * APP分发配置ServiceImpl
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-05-29 18:06:36
 */
 
@Service
public class AppDownloadConfigService extends BaseServiceImpl<AppDownloadConfig, Integer> implements IAppDownloadConfigService {
	
    private static final Logger logger = LoggerFactory.getLogger(AppDownloadConfigService.class);

	@Autowired
	private IPaginationDao paginationDao;
   
    @Resource
    private IAppDownloadConfigDao appDownloadConfigDao;

	@Override
	public BaseMapper<AppDownloadConfig, Integer> getMapper() {
		return appDownloadConfigDao;
	}

	@Override
	public PageConfig<AppDownloadConfig> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "AppDownloadConfig");
		return paginationDao.findPage("listSelective", "findParamsCount", params, "back");
	}

	@Override
	public void openOrCloseConfig(int id) {
		//修改所有状态都为关闭
		appDownloadConfigDao.updateAllStatus("0");
		//修改当前ID的状态为开启
		AppDownloadConfig appDownloadConfig = new AppDownloadConfig();
		appDownloadConfig.setId(id);
		appDownloadConfig.setStatus("1");
		appDownloadConfigDao.updateSelective(appDownloadConfig);
	}
}
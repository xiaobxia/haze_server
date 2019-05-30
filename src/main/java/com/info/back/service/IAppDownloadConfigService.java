package com.info.back.service;

import com.info.base.service.BaseService;
import com.info.back.pojo.AppDownloadConfig;
import com.info.web.util.PageConfig;

import java.util.HashMap;

/**
 * APP分发配置Service
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-05-29 18:06:36
 */
public interface IAppDownloadConfigService extends BaseService<AppDownloadConfig, Integer> {

    PageConfig<AppDownloadConfig> findPage(HashMap<String, Object> params);

    void openOrCloseConfig(int id);

}

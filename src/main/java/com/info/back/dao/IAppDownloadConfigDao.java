package com.info.back.dao;

import com.info.base.dao.BaseMapper;
import com.info.back.pojo.AppDownloadConfig;

import java.util.HashMap;

/**
 * APP分发配置Dao
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-05-29 17:48:30
 */
public interface IAppDownloadConfigDao extends BaseMapper<AppDownloadConfig,Integer> {

    Integer findParamsCount(HashMap<String, Object> params);

    int updateAllStatus(String status);

}

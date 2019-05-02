package com.info.analyze.service;

import com.info.back.pojo.AppMarketFlowRecord;
import com.info.back.pojo.AppMarketType;

import java.util.List;
import java.util.Map;

/**
 * 应用市场流量统计服务层接口
 *
 * @author tgy
 * @version [版本号, 2018年5月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IAppMarketStaticsService {

    /**
     * 定时器调用插入数据
     */
    void insertToAppmarketAnalyze() throws Exception;

    /**
     * 定时器每日插入应用市场类型数据
     */
    void inserAppMarketTypeEveryDay() throws Exception;

    /**
     * 分页查询应用市场流量统计
     *
     * @param params params
     * @return list
     */
    List<AppMarketFlowRecord> selectPageList(Map<String, Object> params);

    /**
     * 分页查询应用市场流量统计数量
     *
     * @param params params
     * @return int
     */
    int selectPageCunt(Map<String, Object> params);

    /**
     * 查询所有的app应用市场统计
     *
     * @return list
     */
    List<AppMarketFlowRecord> selectAllList(Map<String, Object> params);

    /**
     * 查询所有的app应用市场统计数量
     *
     * @return integer
     */
    Integer selectAllListCunt(Map<String, Object> params);

    /**
     * 查询应用商店列表
     *
     * @return list
     */
    List<AppMarketType> selectMarketTypeList();
}

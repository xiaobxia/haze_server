package com.info.back.service;

import com.info.back.pojo.BackDistributeRecord;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动派单操作记录日志服务接口
 *
 * @author：tgy
 */
public interface IBackDistributeRecordService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<BackDistributeRecord> findPage(Map<String, Object> params);

    /**
     * 查询数量
     *
     * @param params
     * @return
     */
    int findCunts(Map<String, Object> params);

    /**
     * 新增记录
     *
     * @param backDistributeRecord
     * @return
     */
    int add(BackDistributeRecord backDistributeRecord);
}

package com.info.back.service;

import com.info.back.dao.IBackDistributeRecordDao;
import com.info.back.pojo.BackDistributeRecord;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动派单操作记录日志服务接口
 *
 * @author：tgy
 */
@Service
public class BackDistributeRecordService implements IBackDistributeRecordService {


    @Autowired
    private IBackDistributeRecordDao backDistributeRecordDao;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<BackDistributeRecord> findPage(Map<String, Object> params) {
        return backDistributeRecordDao.findParams(params);
    }

    /**
     * 查询数量
     *
     * @param params
     * @return
     */
    @Override
    public int findCunts(Map<String, Object> params) {
        return backDistributeRecordDao.findParamsCount(params);
    }


    /**
     * 新增记录
     *
     * @param backDistributeRecord
     * @return
     */
    @Override
    public int add(BackDistributeRecord backDistributeRecord) {
        return backDistributeRecordDao.insert(backDistributeRecord);
    }
}

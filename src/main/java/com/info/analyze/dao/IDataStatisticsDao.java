package com.info.analyze.dao;

import com.info.analyze.pojo.DataStatistics;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface IDataStatisticsDao {

    /**
     * 插入数据
     * @param dataStatistics
     * @return
     */
    public int insertDataStatics(DataStatistics dataStatistics);

    /**
     * 跟新数据
     * @param dataStatistics
     * @return
     */
    public int updateDataStatics(DataStatistics dataStatistics);

    /**
     * 查找统计表中的数据
     * @param hashMap
     * @return
     */
    public List<DataStatistics> selectDataStatistics(Map<String, Object> hashMap);

    /**
     * 删除所有的数据
     * @return
     */
    public int deleDataStatisticsAll();
}

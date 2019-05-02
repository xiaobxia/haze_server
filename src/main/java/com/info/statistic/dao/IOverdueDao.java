package com.info.statistic.dao;

import com.info.web.pojo.statistics.Overdue;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tl on 2018/4/19.
 */
public interface IOverdueDao {

    void insertList(@Param("overdueList") List<Overdue> overdueList);

    List<Overdue> findParams(Map<String, Object> map);

    int findParamsCount(HashMap<String, Object> params);

    void deleteBySource(Integer source);

    void updateRate3(@Param("overdueList") List<Overdue> overdueList);

    void deleteParams(Map<String, Object> params);
}

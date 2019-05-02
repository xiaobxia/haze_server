package com.info.back.dao;

import com.info.back.pojo.AppMarketType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用市场类型DAO层
 *
 * @author tgy
 * @version [版本号, 2018年5月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface IAppMarketTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMarketType record);

    int insertSelective(AppMarketType record);

    AppMarketType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMarketType record);

    int updateByPrimaryKey(AppMarketType record);

    List<AppMarketType> selectAllType();

    int insertOrUpdate(List<AppMarketType> recordList);
}
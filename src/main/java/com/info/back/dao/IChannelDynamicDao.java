package com.info.back.dao;


import com.info.web.pojo.ChannelDynamic;
import org.springframework.stereotype.Repository;

@Repository
public interface IChannelDynamicDao {
    int deleteByPrimaryKey(Long id);

    int insert(ChannelDynamic record);

    int insertSelective(ChannelDynamic record);

    ChannelDynamic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChannelDynamic record);

    int updateByPrimaryKey(ChannelDynamic record);
}
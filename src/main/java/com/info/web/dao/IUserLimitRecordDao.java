package com.info.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.UserLimitRecord;

public interface IUserLimitRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLimitRecord record);

    int insertSelective(UserLimitRecord record);

    UserLimitRecord selectByPrimaryKey(Integer id);
    
    List<UserLimitRecord> findListBayParams(HashMap<String, Object> params);
    

    int updateByPrimaryKeySelective(UserLimitRecord record);

    int updateByPrimaryKey(UserLimitRecord record);
    
    Map<String,BigDecimal> countAddAmount(Integer userId);
}
package com.info.web.dao;

import com.info.web.pojo.LabelCountBase;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ILabelCountBaseDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelCountBase record);

    int insertSelective(LabelCountBase record);

    LabelCountBase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelCountBase record);

    int updateByPrimaryKey(LabelCountBase record);

    int deleteExistByParams(Map<String, Object> params);
}
package com.info.web.dao;

import com.info.web.pojo.LabelCountNum;
import com.info.web.pojo.LabelCountPageResult;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ILabelCountNumDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelCountNum record);

    int insertSelective(LabelCountNum record);

    LabelCountNum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelCountNum record);

    int updateByPrimaryKey(LabelCountNum record);

    List<LabelCountNum> findByParams(HashMap<String, Object> params);

    List<LabelCountPageResult> findSecondLevelNum(HashMap<String, Object> params);

    List<LabelCountPageResult> findThirdLevelNum(HashMap<String, Object> params);

}
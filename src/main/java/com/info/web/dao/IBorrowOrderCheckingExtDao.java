package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.BorrowOrderCheckingExt;

public interface IBorrowOrderCheckingExtDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowOrderCheckingExt record);

    int insertSelective(BorrowOrderCheckingExt record);

    BorrowOrderCheckingExt selectByPrimaryKey(Integer id);
    List<BorrowOrderCheckingExt> findParams(HashMap<String, Object> params);

    int updateByPrimaryKeySelective(BorrowOrderCheckingExt record);

    int updateByPrimaryKeyWithBLOBs(BorrowOrderCheckingExt record);

    int updateByPrimaryKey(BorrowOrderCheckingExt record);
    
    /**
     * 招财猫分页
     * @param params
     * @return
     * 
     */
    Integer selectAllCount(Map<String, Object> params);
    
}
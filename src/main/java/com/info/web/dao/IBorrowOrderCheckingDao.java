package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.BorrowOrderChecking;

public interface IBorrowOrderCheckingDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowOrderChecking record);

    int insertSelective(BorrowOrderChecking record);

    BorrowOrderChecking selectByPrimaryKey(Integer id);
    List<BorrowOrderChecking> selectAll(HashMap<String, Object> params);
    int updateByPrimaryKeySelective(BorrowOrderChecking record);

    int updateByPrimaryKeyWithBLOBs(BorrowOrderChecking record);

    int updateByPrimaryKey(BorrowOrderChecking record);
    
    Integer selectAllCount(Map<String, Object> params);
}
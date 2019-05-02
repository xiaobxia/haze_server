package com.info.web.dao;

import java.util.HashMap;

import com.info.web.pojo.BorrowAssetPacket;

public interface IBorrowAssetPacketDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowAssetPacket record);

    int insertSelective(BorrowAssetPacket record);

    BorrowAssetPacket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BorrowAssetPacket record);

    int updateByPrimaryKey(BorrowAssetPacket record);
    
    BorrowAssetPacket selectByParam(HashMap<String, Object> params);
}
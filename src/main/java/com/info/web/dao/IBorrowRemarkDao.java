package com.info.web.dao;

import com.info.web.pojo.BorrowRemark;
import com.info.web.pojo.LabelCountNum;

import java.util.List;
import java.util.Map;

public interface IBorrowRemarkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowRemark record);

    int insertSelective(BorrowRemark record);

    BorrowRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BorrowRemark record);

    int updateByPrimaryKeyWithBLOBs(BorrowRemark record);

    int updateByPrimaryKey(BorrowRemark record);

    //a:all

    List<LabelCountNum> getLabelsOnlineCustomer(Map<String, Object> params);

    List<LabelCountNum> getLabelsTelCustomer(Map<String, Object> params);

    List<LabelCountNum> getLabelsOnlineCustomerByParams(Map<String, Object> params);

    List<LabelCountNum> getLabelsTelCustomerByParams(Map<String, Object> params);

    Integer getUserNumByParams(Map<String, Object> params);
}
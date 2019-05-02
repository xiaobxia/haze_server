package com.info.web.dao;

import com.info.web.pojo.RepaymentAlipay;
import org.springframework.stereotype.Repository;

@Repository("repaIRepaymentAlipayDao")
public interface IRepaymentAlipayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RepaymentAlipay record);

    int insertSelective(RepaymentAlipay record);

    RepaymentAlipay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepaymentAlipay record);

    int updateByPrimaryKey(RepaymentAlipay record);
}
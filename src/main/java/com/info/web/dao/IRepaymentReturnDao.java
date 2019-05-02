package com.info.web.dao;

import com.info.web.pojo.RepaymentReturn;
import org.springframework.stereotype.Repository;

@Repository("repaymentReturnDao")
public interface IRepaymentReturnDao {

    int insertSelective(RepaymentReturn record);

}

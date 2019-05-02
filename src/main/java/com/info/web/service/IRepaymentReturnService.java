package com.info.web.service;

import com.info.web.pojo.BackUser;
import com.info.web.pojo.RepaymentReturn;

public interface IRepaymentReturnService {

    int insertSelective(RepaymentReturn record);
    String returnRepayDetail(RepaymentReturn ret, BackUser backUser);
    String returnRenewal(RepaymentReturn ret, BackUser backUser);
}

package com.info.web.service;

import com.info.web.dao.IRepaymentAlipayDao;
import com.info.web.pojo.RepaymentAlipay;

/**
 * Created by IntelliJ IDEA
 * User : zhangsh
 * Date : 2017/2/15 0015
 * Time : 18:26
 */
public interface IRepaymentAlipayService {

    boolean insertSelective(RepaymentAlipay record);

    RepaymentAlipay selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(RepaymentAlipay record);

}

package com.info.web.service;

import com.info.web.dao.IRepaymentAlipayDao;
import com.info.web.pojo.RepaymentAlipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA
 * User : zhangsh
 * Date : 2017/2/15 0015
 * Time : 18:28
 */
@Service
public class RepaymentAlipayService implements IRepaymentAlipayService{

    @Autowired
    private IRepaymentAlipayDao repaIRepaymentAlipayDao;

    @Override
    public boolean insertSelective(RepaymentAlipay record) {
        return repaIRepaymentAlipayDao.insertSelective(record) > 0;
    }

    @Override
    public RepaymentAlipay selectByPrimaryKey(Integer id) {
        return repaIRepaymentAlipayDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(RepaymentAlipay record) {
        return repaIRepaymentAlipayDao.updateByPrimaryKeySelective(record) > 0;
    }
}

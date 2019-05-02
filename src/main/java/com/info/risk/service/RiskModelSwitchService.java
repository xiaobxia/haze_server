package com.info.risk.service;

import com.info.risk.dao.IRiskModelSwitchDao;
import com.info.risk.pojo.RiskModelSwitch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tl on 2018/5/3.
 */
@Service
public class RiskModelSwitchService implements IRiskModelSwitchService {
    @Resource
    private IRiskModelSwitchDao riskModelSwitchDao;
    @Override
    public List<RiskModelSwitch> findParams(HashMap<String, Object> params) {
        return riskModelSwitchDao.findParams(params);
    }
}

package com.info.risk.service;

import com.info.risk.pojo.RiskModelSwitch;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tl on 2018/5/3.
 */
public interface IRiskModelSwitchService {
    List<RiskModelSwitch> findParams(HashMap<String, Object> params);
}

package com.info.risk.dao;

import com.info.risk.pojo.RiskModelSwitch;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tl on 2018/5/3.
 */
public interface IRiskModelSwitchDao {
    List<RiskModelSwitch> findParams(HashMap<String, Object> params);
}

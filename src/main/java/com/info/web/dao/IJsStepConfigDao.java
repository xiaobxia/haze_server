package com.info.web.dao;

import java.util.List;

import com.info.web.pojo.JsStepConfig;
import org.springframework.stereotype.Repository;

@Repository("jsStepConfigDao")
public interface IJsStepConfigDao   {
	List<JsStepConfig> findList(JsStepConfig jsStepConfig);
}

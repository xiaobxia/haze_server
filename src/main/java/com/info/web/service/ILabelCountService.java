package com.info.web.service;

import com.info.web.pojo.LabelCountBase;
import com.info.web.pojo.LabelCountPageResult;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;

public interface ILabelCountService {

    void labelCount(String countDate);

    PageConfig<LabelCountBase> findPage(HashMap<String, Object> params);

    List<LabelCountPageResult> getDetailResult(HashMap<String, Object> params);
}

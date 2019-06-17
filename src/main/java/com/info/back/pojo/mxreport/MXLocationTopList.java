package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.info.web.util.StringDateUtils;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/2/28.
 */
public class MXLocationTopList {

    private String key;

    @JsonProperty("top_item")
    private List<MXLocationTopItem> topItem;

    public MXLocationTopList(){};

    public MXLocationTopList(String key, List<MXLocationTopItem> topItem) {
        this.key = key;
        this.topItem = topItem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = StringDateUtils.underlineToCamel(key);
    }

    public List<MXLocationTopItem> getTopItem() {
        return topItem;
    }

    public void setTopItem(List<MXLocationTopItem> topItem) {
        this.topItem = topItem;
    }
}

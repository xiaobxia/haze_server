package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXContactRegion {

    private String key;

    private String desc;

    @JsonProperty("region_list")
    private List<MXRegionItem> regionList;

    public MXContactRegion(){};

    public MXContactRegion(String key, String desc, List<MXRegionItem> regionList) {
        this.key = key;
        this.desc = desc;
        this.regionList = regionList;
    }
}

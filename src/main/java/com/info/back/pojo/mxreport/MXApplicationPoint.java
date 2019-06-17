package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXApplicationPoint {

    @JsonProperty("app_point")
    private String appPoint;

    @JsonProperty("app_point_zh")
    private String appPointZh;

    private MXDetailItem item;
}

package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXVoiceCallStatistics {
    private MXItem item;
    @JsonProperty("app_point")
    private String appPoint;
    @JsonProperty("app_point_zh")
    private String appPointZh;
}

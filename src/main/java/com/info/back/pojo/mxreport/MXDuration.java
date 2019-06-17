package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXDuration {

    @JsonProperty("time_step")
    private String timeStep;

    @JsonProperty("time_step_zh")
    private String timeStepZh;

    private MXDurationItem item;


}

package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/6.
 */
@Data
public class MXApplicationCheck {

    @JsonProperty("app_point")
    private String appPoint;

    @JsonProperty("check_points")
    private MXCheckPoints checkPoints;
}

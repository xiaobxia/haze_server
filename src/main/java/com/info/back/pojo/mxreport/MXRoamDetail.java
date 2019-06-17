package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/2.
 */
@Data
public class MXRoamDetail {

    @JsonProperty("roam_day")
    private String roamDay;

    @JsonProperty("roam_location")
    private String roamLocation;
}

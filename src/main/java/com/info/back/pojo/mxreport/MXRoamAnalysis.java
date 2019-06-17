package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/2.
 */
@Data
public class MXRoamAnalysis {

    @JsonProperty("roam_location")
    private String roamLocation;

    @JsonProperty("roam_day_cnt_3m")
    private Long roamDayCnt_3;
    @JsonProperty("roam_day_cnt_6m")
    private Long roamDayCnt_6;

    @JsonProperty("continue_roam_cnt_3m")
    private Long continueRoamCnt_3;
    @JsonProperty("continue_roam_cnt_6m")
    private Long continueRoamCnt_6;

    @JsonProperty("max_roam_day_cnt_3m")
    private Long maxRoamDayCnt_3;
    @JsonProperty("max_roam_day_cnt_6m")
    private Long maxRoamDayCnt_6;
}

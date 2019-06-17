package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXServiceDetail {

    @JsonProperty("interact_mth")
    private String interactMth;

    @JsonProperty("interact_cnt")
    private Long interactCnt;

    @JsonProperty("interact_time")
    private Long interactTime;

    @JsonProperty("dial_cnt")
    private Long dialCnt;

    @JsonProperty("dialed_cnt")
    private Long dialedCnt;

    @JsonProperty("dial_time")
    private Long dialTime;

    @JsonProperty("dialed_time")
    private Long dialedTime;
}

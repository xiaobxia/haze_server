package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXLocationTopItem {

    private String location;

    @JsonProperty("peer_number_cnt")
    private Long peerNumberCnt;

    @JsonProperty("call_cnt")
    private Long callCnt;

    @JsonProperty("call_time")
    private Long callTime;

    @JsonProperty("dial_cnt")
    private Long dialCnt;

    @JsonProperty("dialed_cnt")
    private Long dialedCnt;

    @JsonProperty("dial_time")
    private Long dialTime;

    @JsonProperty("dialed_time")
    private Long dialedTime;
}

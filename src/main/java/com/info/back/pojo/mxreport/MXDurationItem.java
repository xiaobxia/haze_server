package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXDurationItem {

    @JsonProperty("total_cnt")
    private Long totalCnt;

    @JsonProperty("uniq_num_cnt")
    private Long uniqNumCnt;

    @JsonProperty("total_time")
    private Long totalTime;

    @JsonProperty("dial_cnt")
    private Long dialCnt;

    @JsonProperty("dialed_cnt")
    private Long dialedCnt;

    @JsonProperty("dial_time")
    private Long dialTime;

    @JsonProperty("dialed_time")
    private Long dialedTime;

    @JsonProperty("latest_call_time")
    private String latestCallTime;

    @JsonProperty("farthest_call_time")
    private String farthestCallTime;
}

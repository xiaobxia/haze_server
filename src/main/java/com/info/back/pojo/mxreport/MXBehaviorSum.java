package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/27.
 */
@Data
public class MXBehaviorSum {

    @JsonProperty("sms_cnt_avg")
    private Long smsCntAvg;

    @JsonProperty("net_flow_avg")
    private Long netFlowAvg;

    @JsonProperty("total_amount_avg")
    private Long totalAmountAvg;

    @JsonProperty("call_cnt_avg")
    private Long callCntAvg;

    @JsonProperty("dial_cnt_avg")
    private Long dialCntAvg;

    @JsonProperty("dialed_cnt_avg")
    private Long dialedCntAvg;

    @JsonProperty("dial_time_avg")
    private Long dialTimeAvg;

    @JsonProperty("dialed_time_avg")
    private Long dialedTimeAvg;

    @JsonProperty("sms_cnt_ratio")
    private Double smsCntRatio;

    @JsonProperty("net_flow_ratio")
    private Double netFlowRatio;

    @JsonProperty("total_amount_ratio")
    private Double totalAmountRatio;

    @JsonProperty("call_cnt_ratio")
    private Double callCntRatio;

    @JsonProperty("dial_cnt_ratio")
    private Double dialCntRatio;

    @JsonProperty("dialed_cnt_ratio")
    private Double dialedCntRatio;

    @JsonProperty("dial_time_ratio")
    private Double dialTimeRatio;

    @JsonProperty("dialed_time_ratio")
    private Double dialedTimeRatio;
}

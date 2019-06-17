package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/27.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXBehavior {

    @JsonProperty("sms_cnt")
    private Long smsCnt;

    @JsonProperty("cell_phone_num")
    private String cellPhoneNum;

    @JsonProperty("net_flow")
    private Long netFlow;

    @JsonProperty("total_amount")
    private Long totalAmount;

    @JsonProperty("cell_mth")
    private String cellMth;

    @JsonProperty("cell_loc")
    private String cellLoc;

    @JsonProperty("cell_operator_zh")
    private String cellOperatorZh;

    @JsonProperty("cell_operator")
    private String cellOperator;

    @JsonProperty("call_cnt")
    private Long callCnt;

    @JsonProperty("call_time")
    private Long callTime;

    @JsonProperty("dial_cnt")
    private Long dialCnt;

    @JsonProperty("dial_time")
    private Long dialTime;

    @JsonProperty("dialed_cnt")
    private Long dialedCnt;

    @JsonProperty("dialed_time")
    private Long dialedTime;

    @JsonProperty("rechange_cnt")
    private Long rechangeCnt;

    @JsonProperty("rechange_amount")
    private Long rechangeAmount;
}

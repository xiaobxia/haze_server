package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXSmsContactDetail {

    @JsonProperty("peer_num")
    private String peerNumber;

    @JsonProperty("sms_cnt_1w")
    private Long smsCnt1Week;

    @JsonProperty("sms_cnt_1m")
    private Long smsCnt1Month;

    @JsonProperty("sms_cnt_3m")
    private Long smsCnt3Month;

    @JsonProperty("sms_cnt_6m")
    private Long smsCnt6Month;

    @JsonProperty("send_cnt_3m")
    private Long sendCnt3Month;

    @JsonProperty("send_cnt_6m")
    private Long sendCnt6Month;

    @JsonProperty("receive_cnt_3m")
    private Long receiveCnt3Month;

    @JsonProperty("receive_cnt_6m")
    private Long receiveCnt6Month;
}

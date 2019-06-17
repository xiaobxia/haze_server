package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
@ToString
public class MXPeerNumTopItem {


    @JsonProperty("peer_number")
    private String peerNumber;

    @JsonProperty("peer_num_loc")
    private String peerNumLoc;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("company_name")
    private String companyName;

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

package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXPhoneAddressVoiceCallDetail {
    private String mobile;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("location_city")
    private String locationCity;
    @JsonProperty("call_cnt_3m")
    private int callCnt3m;
    @JsonProperty("call_time_3m")
    private int callTime3m;
    @JsonProperty("dial_cnt_3m")
    private int dialCnt3m;
    @JsonProperty("dialed_cnt_3m")
    private int dialedCnt3m;
}

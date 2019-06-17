package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhangliang on 17/3/2.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXCollectionContact {

    @JsonProperty("phone_num")
    private String phoneNum;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("phone_num_loc")
    private String phoneNumLoc;

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

    @JsonProperty("trans_start")
    private String transStart;

    @JsonProperty("trans_end")
    private String transEnd;

    @JsonProperty("sms_cnt")
    private Long smsCnt;

    public MXCollectionContact() {}

    public MXCollectionContact(String phoneNum) {
        this.phoneNum = phoneNum;
        this.contactName = StringUtils.EMPTY;
        this.relationship = StringUtils.EMPTY;
        this.phoneNumLoc = StringUtils.EMPTY;
        this.callCnt = 0L;
        this.callTime = 0L;
        this.dialCnt = 0L;
        this.dialTime = 0L;
        this.dialedCnt = 0L;
        this.dialedTime = 0L;
        this.transStart = StringUtils.EMPTY;
        this.transEnd = StringUtils.EMPTY;
        this.smsCnt = 0L;
    }
}

package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXCallContactDetail {

    @JsonProperty("peer_num")
    private String peerNumber;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("company_name")
    private String companyName;

    private String city;

    private String p_relation;

    @JsonProperty("call_cnt_1w")
    private Long callCnt1Week;

    @JsonProperty("call_cnt_1m")
    private Long callCnt1Month;

    @JsonProperty("call_cnt_3m")
    private Long callCnt3Month;

    @JsonProperty("call_cnt_6m")
    private Long callCnt6Month;

    @JsonProperty("call_time_3m")
    private Long callTime3Month;

    @JsonProperty("call_time_6m")
    private Long callTime6Month;

    @JsonProperty("dial_cnt_3m")
    private Long dialCnt3Month;

    @JsonProperty("dial_cnt_6m")
    private Long dialCnt6Month;

    @JsonProperty("dial_time_3m")
    private Long dialTime3Month;

    @JsonProperty("dial_time_6m")
    private Long dialTime6Month;

    @JsonProperty("dialed_cnt_3m")
    private Long dialedCnt3Month;

    @JsonProperty("dialed_cnt_6m")
    private Long dialedCnt6Month;

    @JsonProperty("dialed_time_3m")
    private Long dialedTime3Month;

    @JsonProperty("dialed_time_6m")
    private Long dialedTime6Month;

    @JsonProperty("call_cnt_morning_3m")
    private Long callCntMorning3Month;

    @JsonProperty("call_cnt_morning_6m")
    private Long callCntMorning6Month;

    @JsonProperty("call_cnt_noon_3m")
    private Long callCntNoon3Month;

    @JsonProperty("call_cnt_noon_6m")
    private Long callCntNoon6Month;

    @JsonProperty("call_cnt_afternoon_3m")
    private Long callCntAfternoon3Month;

    @JsonProperty("call_cnt_afternoon_6m")
    private Long callCntAfternoon6Month;

    @JsonProperty("call_cnt_evening_3m")
    private Long callCntEvening3Month;

    @JsonProperty("call_cnt_evening_6m")
    private Long callCntEvening6Month;

    @JsonProperty("call_cnt_night_3m")
    private Long callCntNight3Month;

    @JsonProperty("call_cnt_night_6m")
    private Long callCntNight6Month;

    @JsonProperty("call_cnt_weekday_3m")
    private Long callCntWeekday3Month;

    @JsonProperty("call_cnt_weekday_6m")
    private Long callCntWeekday6Month;

    @JsonProperty("call_cnt_weekend_3m")
    private Long callCntWeekend3Month;

    @JsonProperty("call_cnt_weekend_6m")
    private Long callCntWeekend6Month;

    @JsonProperty("call_cnt_holiday_3m")
    private Long callCntHoliday3Month;

    @JsonProperty("call_cnt_holiday_6m")
    private Long callCntHoliday6Month;

    @JsonProperty("call_if_whole_day_3m")
    private Boolean callIfWholeDay3Month;

    @JsonProperty("call_if_whole_day_6m")
    private Boolean callIfWholeDay6Month;

    @JsonProperty("trans_start")
    private String transStart;

    @JsonProperty("trans_end")
    private String transEnd;

    @JsonProperty("is_black")
    private Boolean isBlack;

    @JsonProperty("black_type")
    private String blackType;

    @JsonProperty("max_call_time_6m")
    private Long maxCallTime6Month;

    @JsonProperty("min_call_time_6m")
    private Long minCallTime6Month;

    @JsonProperty("avg_call_time_6m")
    private Long avgCallTime6Month;

    @JsonProperty("is_intimate")
    private Boolean isIntimate;
}

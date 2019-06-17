package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXPhoneAddressVoiceCall {
    @JsonProperty("voice_call_statistics")
    private List<MXVoiceCallStatistics> voiceCallStatistics;
    @JsonProperty("voice_call_cnt_list")
    private List<MXVoiceCallCntList> voiceCallCntList;
    @JsonProperty("voice_location_cnt_list")
    private List<MXVoiceLocationCntList> voiceLocationCntList;
}

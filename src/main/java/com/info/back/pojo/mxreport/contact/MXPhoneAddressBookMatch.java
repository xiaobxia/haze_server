package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXPhoneAddressBookMatch {
    @JsonProperty("voice_peer_num_cnt")
    private int voicePeerNumCnt;
    @JsonProperty("mobile_cnt")
    private List<MXMobileCnt> mobileCnt;
    @JsonProperty("is_same_location")
    private boolean sameLocation;
}

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
public class MXVoiceCallCntList {
    private String key;
    @JsonProperty("top_item")
    private List<MXMobileTopItem> topItem;
}

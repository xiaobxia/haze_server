package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by vivien on 2017/9/15.
 */
@Data
@ToString(callSuper = true)
public class MXVoiceLocationCntList {
    private String key;
    @JsonProperty("top_item")
    private List<MXLocationTopItem> topItem;
}

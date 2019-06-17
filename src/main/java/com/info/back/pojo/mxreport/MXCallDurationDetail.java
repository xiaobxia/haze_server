package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXCallDurationDetail {

    private String key;

    private String desc;

    @JsonProperty("duration_list")
    private List<MXDuration> durationList;
}

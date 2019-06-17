package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivien on 2017/9/18.
 */
@Data
public class MXDurationSecondDivide {
    @JsonProperty("interact_mth")
    private String interactMth;
    @JsonProperty("time_divide")
    private List<MXTimeDivide> timeDivide = new ArrayList<>();
}

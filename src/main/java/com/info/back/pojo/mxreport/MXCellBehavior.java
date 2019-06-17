package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/2/27.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MXCellBehavior {

    @JsonProperty("phone_num")
    private String phoneNum;

    @JsonProperty("sum")
    private MXBehaviorSum behaviorSum;

    private List<MXBehavior> behavior;
}

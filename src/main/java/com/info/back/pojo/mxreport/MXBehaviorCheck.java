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
public class MXBehaviorCheck {

    @JsonProperty("check_point")
    private String checkPoint;

    @JsonProperty("check_point_cn")
    private String checkPointCn;

    private String result;

    private String evidence;

    private Long score;
}

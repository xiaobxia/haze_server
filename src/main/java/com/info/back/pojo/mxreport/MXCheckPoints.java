package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/6.
 */
@Data
public class MXCheckPoints {

    @JsonProperty("key_value")
    private String keyValue;

    private String relationship;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("check_mobile")
    private String checkMobile;

    @JsonProperty("check_xiaohao")
    private String checkXiaohao;
}

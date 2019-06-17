package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXUserInfoCheck {

    @JsonProperty("check_search_info")
    private MXCheckSearchInfo checkSearchInfo;

    @JsonProperty("check_black_info")
    private MXCheckBlackInfo checkBlackInfo;
}

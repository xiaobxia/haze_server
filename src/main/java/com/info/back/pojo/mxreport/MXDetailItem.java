package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXDetailItem {

    @JsonProperty("item_1m")
    private String item1Month;

    @JsonProperty("item_3m")
    private String item3Month;

    @JsonProperty("item_6m")
    private String item6Month;

    @JsonProperty("avg_item_3m")
    private String avgItem3Month;

    @JsonProperty("avg_item_6m")
    private String avgItem6Month;
}

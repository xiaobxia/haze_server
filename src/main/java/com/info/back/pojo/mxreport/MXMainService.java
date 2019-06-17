package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/3/1.
 */
@Data
public class MXMainService {

    @JsonProperty("service_num")
    private String serviceNum;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("total_service_cnt")
    private Long totalServiceCnt;

    @JsonProperty("service_details")
    private List<MXServiceDetail> serviceDetails;
}

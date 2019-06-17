package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.info.back.pojo.mxreport.MXBasicInfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXPhoneAddressBookDetail {
    @JsonProperty("address_book_basic_info")
    private List<MXBasicInfo> addressBookBasicInfo;
    @JsonProperty("address_book_risk_analysis")
    private List<MXBasicInfo> addressBookRiskAnalysis;
    @JsonProperty("address_book_location_info")
    private List<MXBasicInfo> addressBookLocationInfo;
    @JsonProperty("address_book_location_list")
    private List<MXAddressBookLocationList> addressBookLocationList;
}

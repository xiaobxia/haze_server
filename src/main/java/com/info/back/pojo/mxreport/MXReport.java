package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.info.back.pojo.mxreport.contact.MXPhoneAddressBookDetail;
import com.info.back.pojo.mxreport.contact.MXPhoneAddressVoiceCallDetail;
import com.info.back.pojo.mxreport.contact.MXPhoneAddressBookMatch;
import com.info.back.pojo.mxreport.contact.MXPhoneAddressVoiceCall;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by zhangliang on 17/2/27.
 */
@Data
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXReport {

    private List<MXBasicInfo> report;

    @JsonProperty("user_basic")
    private List<MXBasicInfo> userBasic;

    @JsonProperty("cell_phone")
    private List<MXBasicInfo> cellPhone;

    @JsonProperty("basic_check_items")
    private List<MXBasicCheckItem> basicCheckItems;

    @JsonProperty("application_check")
    private List<MXApplicationCheck> applicationCheck;

    @JsonProperty("behavior_check")
    private List<MXBehaviorCheck> behaviorCheck;

    @JsonProperty("friend_circle")
    private MXFriendCircle friendCircle;

    @JsonProperty("cell_behavior")
    private List<MXCellBehavior> cellBehavior;

    @JsonProperty("call_contact_detail")
    private List<MXCallContactDetail> callContactDetail;

    @JsonProperty("sms_contact_detail")
    private List<MXSmsContactDetail> smsContactDetail;

    @JsonProperty("contact_region")
    private List<MXContactRegion> contactRegion;

    @JsonProperty("call_risk_analysis")
    private List<MXCallAnalysis> callRiskAnalysis;

    @JsonProperty("main_service")
    private List<MXMainService> mainService;

    @JsonProperty("call_service_analysis")
    private List<MXCallAnalysis> callServiceAnalysis;

    @JsonProperty("active_degree")
    private List<MXApplicationPoint> activeDegree;

    @JsonProperty("consumption_detail")
    private List<MXApplicationPoint> consumptionDetail;

    @JsonProperty("call_time_detail")
    private List<MXApplicationPoint> callTimeDetail;

    @JsonProperty("call_family_detail")
    private List<MXApplicationPoint> callFamilyDetail;

    @JsonProperty("call_duration_detail")
    private List<MXCallDurationDetail> callDurationDetail;

    @JsonProperty("roam_analysis")
    private List<MXRoamAnalysis> roamAnalysis;

    @JsonProperty("roam_detail")
    private List<MXRoamDetail> roamDetail;

    @JsonProperty("collection_contact")
    private List<MXCollectionContact> collectionContact;

    @JsonProperty("user_info_check")
    private List<MXUserInfoCheck> userInfoCheck;

    @JsonProperty("trip_info")
    private List<MXTripInfo> tripInfo;

    @JsonProperty("phone_address_book_detail")
    private MXPhoneAddressBookDetail phoneAddressBookDetail;

    @JsonProperty("phone_address_book_match")
    private MXPhoneAddressBookMatch phoneAddressBookMatch;

    @JsonProperty("phone_address_voice_call")
    private MXPhoneAddressVoiceCall phoneAddressVoiceCall;

    @JsonProperty("phone_address_voice_call_detail")
    private List<MXPhoneAddressVoiceCallDetail> phoneAddressVoiceCallDetail;

    @JsonProperty("duration_second_divide")
    private List<MXDurationSecondDivide> durationSecondDivide;
}

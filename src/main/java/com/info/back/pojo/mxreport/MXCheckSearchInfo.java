package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhangliang on 17/2/8.
 */
public class MXCheckSearchInfo {

    @JsonProperty("searched_org_cnt")
    private Long searchedOrgCnt = 0L;

    @JsonProperty("searched_org_type")
    private List<String> searchedOrgType = new ArrayList<>();

    @JsonProperty("idcard_with_other_names")
    private List<String> idcardWithOtherNames = new ArrayList<>();

    @JsonProperty("idcard_with_other_phones")
    private List<String> idcardWithOtherPhones = new ArrayList<>();

    @JsonProperty("phone_with_other_names")
    private List<String> phoneWithOtherNames = new ArrayList<>();

    @JsonProperty("phone_with_other_idcards")
    private List<String> phoneWithOtherIdcards = new ArrayList<>();

    @JsonProperty("register_org_cnt")
    private Long registerOrgCnt = 0L;

    @JsonProperty("register_org_type")
    private List<String> registerOrgType = new ArrayList<>();

    @JsonProperty("arised_open_web")
    private List<String> arisedOpenWeb = new ArrayList<>();

    public Long getSearchedOrgCnt() {
        return searchedOrgCnt;
    }

    public void setSearchedOrgCnt(Long searchedOrgCnt) {
        this.searchedOrgCnt = searchedOrgCnt;
    }

    public List<String> getSearchedOrgType() {
        return searchedOrgType;
    }

    public void setSearchedOrgType(List<String> searchedOrgType) {
        this.searchedOrgType = replaceList(searchedOrgType);
    }

    public List<String> getIdcardWithOtherNames() {
        return idcardWithOtherNames;
    }

    public void setIdcardWithOtherNames(List<String> idcardWithOtherNames) {
        this.idcardWithOtherNames = idcardWithOtherNames;
    }

    public List<String> getIdcardWithOtherPhones() {
        return idcardWithOtherPhones;
    }

    public void setIdcardWithOtherPhones(List<String> idcardWithOtherPhones) {
        this.idcardWithOtherPhones = idcardWithOtherPhones;
    }

    public List<String> getPhoneWithOtherNames() {
        return phoneWithOtherNames;
    }

    public void setPhoneWithOtherNames(List<String> phoneWithOtherNames) {
        this.phoneWithOtherNames = phoneWithOtherNames;
    }

    public List<String> getPhoneWithOtherIdcards() {
        return phoneWithOtherIdcards;
    }

    public void setPhoneWithOtherIdcards(List<String> phoneWithOtherIdcards) {
        this.phoneWithOtherIdcards = phoneWithOtherIdcards;
    }

    public Long getRegisterOrgCnt() {
        return registerOrgCnt;
    }

    public void setRegisterOrgCnt(Long registerOrgCnt) {
        this.registerOrgCnt = registerOrgCnt;
    }

    public List<String> getRegisterOrgType() {
        return registerOrgType;
    }

    public void setRegisterOrgType(List<String> registerOrgType) {
        this.registerOrgType = replaceList(registerOrgType);
    }

    public List<String> getArisedOpenWeb() {
        return arisedOpenWeb;
    }

    public void setArisedOpenWeb(List<String> arisedOpenWeb) {
        this.arisedOpenWeb = arisedOpenWeb;
    }

    private final static Map<String, String> map = new HashMap(){{
        put("CASH_LOAN", "网贷");
        put("COMPENSATION", "信用卡代偿");
        put("CONSUMSTAGE", "消费分期");
        put("CREDITPAY", "信用支付");
        put("DATACOVERGE", "数据聚合平台");
        put("DIVERSION", "导流平台");
        put("BANK", "银行");
        put("P2P", "P2P理财");
        put("ZHENGXIN", "信用机构");
        put("其它", "其它");
    }};

    private List<String> replaceList(List<String> oldList) {
        return oldList.stream().map(s -> map.get(s)).collect(Collectors.toList());
    }
}

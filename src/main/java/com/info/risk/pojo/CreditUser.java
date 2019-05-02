package com.info.risk.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CreditUser implements Serializable {

    private static final long serialVersionUID = -8093302009947148189L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户真实姓名
     */
    private String userName;

    /**
     * 业务
     */
    private String business;

    /**
     * 设备
     */
    private String equipment;

    /**
     * 用户身份证号
     */
    private String identityCard;

    /**
     * 用户电话号码
     */
    private String userPhone;

    /**
     * 外部用户主键
     */
    private Integer extUserId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 是否是老用户：0、新用户；1；老用户
     */
    private Integer customerType;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 是否拥有运营商数据（只为了qbm临时使用）：0、无数据；1、有数据
     */
    private Integer shujumoheFlag;

    /**
     * 民族
     */
    private String race;

    /**
     * 身份证有效期
     */
    private String idcardYears;

    /**
     * 学历（1博士、2硕士、3本科、4大专、5中专、6高中、7初中及以下）
     */
    private Integer education;

    /**
     * 第一联系人
     */
    private String firstCall;

    /**
     * 第二联系人
     */
    private String secondCall;

    /**
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 淘宝token
     */
    private String token;

    /**
     * 手机通讯录
     */
    private Set<String> callSet = new HashSet<String>();

    /**
     * 带注释的手机通讯录
     */
    private Map<String, String> phoneMap = new HashMap<String, String>();

    /**
     * 设备指纹
     */
    private Map<String, String> blackBoxes = new HashMap<String, String>();

    /**
     * 临时信息
     */
    private Map<String, String> temporaryMessage = new HashMap<String, String>();

    public CreditUser() {
    }

    public CreditUser(String userName, String identityCard, String userPhone) {
        this.userName = userName;
        this.identityCard = identityCard;
        this.userPhone = userPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdcardYears() {
        return idcardYears;
    }

    public void setIdcardYears(String idcardYears) {
        this.idcardYears = idcardYears;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(Integer extUserId) {
        this.extUserId = extUserId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Map<String, String> getBlackBoxes() {
        return blackBoxes;
    }

    public void setBlackBoxes(Map<String, String> blackBoxes) {
        this.blackBoxes = blackBoxes;
    }

    public Map<String, String> getTemporaryMessage() {
        return temporaryMessage;
    }

    public void setTemporaryMessage(Map<String, String> temporaryMessage) {
        this.temporaryMessage = temporaryMessage;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Set<String> getCallSet() {
        return callSet;
    }

    public void setCallSet(Set<String> callSet) {
        this.callSet = callSet;
    }

    public String getFirstCall() {
        return firstCall;
    }

    public void setFirstCall(String firstCall) {
        this.firstCall = firstCall;
    }

    public String getSecondCall() {
        return secondCall;
    }

    public void setSecondCall(String secondCall) {
        this.secondCall = secondCall;
    }

    public Map<String, String> getPhoneMap() {
        return phoneMap;
    }

    public void setPhoneMap(Map<String, String> phoneMap) {
        this.phoneMap = phoneMap;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Integer getShujumoheFlag() {
        return shujumoheFlag;
    }

    public void setShujumoheFlag(Integer shujumoheFlag) {
        this.shujumoheFlag = shujumoheFlag;
    }

    @Override
    public String toString() {
        return "CreditUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", business='" + business + '\'' +
                ", equipment='" + equipment + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", extUserId=" + extUserId +
                ", age=" + age +
                ", gender=" + gender +
                ", customerType=" + customerType +
                ", overdueDays=" + overdueDays +
                ", shujumoheFlag=" + shujumoheFlag +
                ", race='" + race + '\'' +
                ", idcardYears='" + idcardYears + '\'' +
                ", education=" + education +
                ", firstCall='" + firstCall + '\'' +
                ", secondCall='" + secondCall + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", callSet=" + callSet +
                ", phoneMap=" + phoneMap +
                ", blackBoxes=" + blackBoxes +
                ", temporaryMessage=" + temporaryMessage +
                '}';
    }
}

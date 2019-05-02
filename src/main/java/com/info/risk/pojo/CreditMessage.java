package com.info.risk.pojo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by zhang on 2017-11-05.
 */
@Document(collection = "CreditMessage")
public class CreditMessage implements Serializable {
    private static final long serialVersionUID = -6115755125968409058L;
    @Id
    private String id;
    private String userId;

    private String identityCard;
    //private String orderId;
    private String supplier; //第三方来源
    private String action; //
    private String url; //
    private String requestParams;
    private String responseBody;
    //private String createIp;

    private Set<String> callSet;//手机通讯录

    private String firstCall;//第一联系人
    private String secondCall;//第二联系人

    private CreditMessageStatus creditMessageStatus;

    @CreatedDate
    private Date createTime = new Date();
    @LastModifiedDate
    private Date updateTime = new Date();

    private Date notifyTime;
    private String notifyParams;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /*public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }*/


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public CreditMessageStatus getCreditMessageStatus() {
        return creditMessageStatus;
    }

    public void setCreditMessageStatus(CreditMessageStatus creditMessageStatus) {
        this.creditMessageStatus = creditMessageStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /*public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }*/

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyParams() {
        return notifyParams;
    }

    public void setNotifyParams(String notifyParams) {
        this.notifyParams = notifyParams;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    @Override
    public String toString() {
        return "CreditMessage{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", supplier='" + supplier + '\'' +
                ", action='" + action + '\'' +
                ", url='" + url + '\'' +
                ", requestParams='" + requestParams + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", callSet=" + callSet +
                ", firstCall='" + firstCall + '\'' +
                ", secondCall='" + secondCall + '\'' +
                ", creditMessageStatus=" + creditMessageStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", notifyTime=" + notifyTime +
                ", notifyParams='" + notifyParams + '\'' +
                '}';
    }
}


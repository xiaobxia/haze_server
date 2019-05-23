package com.info.web.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OveChannelInfo implements Serializable {
  private Integer channelId;//渠道id
  private String channelSuperName;//渠道商名称
  private String channelName;//渠道名称
  private String loanTime;//还款日期
  private Integer firstLoanCount =0;//首放数量
  private Integer firstRepayCount =0;//首放已还数量
  private String firstOveRate ="0.00";//首放逾期率
  private Integer reLoanCount =0;//复借放款数量
  private Integer reRepayCount =0;//复借已还数量
  private String reOveRate = "0.00";//复借率
  private Integer extendCount =0;//展期数量
  private Integer allLoanCount =0;//总放量
  private Integer allRepayCount =0;//总还量
  private String allOveRate ="0.00";//总逾期率
  private String channelStatus;//渠道状态

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelSuperName() {
        return channelSuperName;
    }

    public void setChannelSuperName(String channelSuperName) {
        this.channelSuperName = channelSuperName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getFirstLoanCount() {
        return firstLoanCount;
    }

    public void setFirstLoanCount(Integer firstLoanCount) {
        this.firstLoanCount = firstLoanCount;
    }

    public Integer getFirstRepayCount() {
        return firstRepayCount;
    }

    public void setFirstRepayCount(Integer firstRepayCount) {
        this.firstRepayCount = firstRepayCount;
    }

    public String getFirstOveRate() {
        return firstOveRate;
    }

    public void setFirstOveRate(String firstOveRate) {
        this.firstOveRate = firstOveRate;
    }

    public Integer getReLoanCount() {
        return reLoanCount;
    }

    public void setReLoanCount(Integer reLoanCount) {
        this.reLoanCount = reLoanCount;
    }

    public Integer getReRepayCount() {
        return reRepayCount;
    }

    public void setReRepayCount(Integer reRepayCount) {
        this.reRepayCount = reRepayCount;
    }

    public String getReOveRate() {
        return reOveRate;
    }

    public void setReOveRate(String reOveRate) {
        this.reOveRate = reOveRate;
    }

    public Integer getExtendCount() {
        return extendCount;
    }

    public void setExtendCount(Integer extendCount) {
        this.extendCount = extendCount;
    }

    public Integer getAllLoanCount() {
        return allLoanCount;
    }

    public void setAllLoanCount(Integer allLoanCount) {
        this.allLoanCount = allLoanCount;
    }

    public Integer getAllRepayCount() {
        return allRepayCount;
    }

    public void setAllRepayCount(Integer allRepayCount) {
        this.allRepayCount = allRepayCount;
    }

    public String getAllOveRate() {
        return allOveRate;
    }

    public void setAllOveRate(String allOveRate) {
        this.allOveRate = allOveRate;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }
}

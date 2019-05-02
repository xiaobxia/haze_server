package com.info.web.pojo;

import java.math.BigDecimal;

/**
 * Author :${cqry_2017}
 * Date   :2017-11-10 10:32.
 */
public class ChannelGetUserInfo {
    private  String seriesNum;
    private  Integer userId;
    private  String realName;
    private  String mobile;
    private  String isCompleteInfo;
    private BigDecimal borrowTotal;
    private Integer borrowCount;

    public String getSeriesNum() {
        return seriesNum;
    }

    public void setSeriesNum(String seriesNum) {
        this.seriesNum = seriesNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsCompleteInfo() {
        return isCompleteInfo;
    }

    public void setIsCompleteInfo(String isCompleteInfo) {
        this.isCompleteInfo = isCompleteInfo;
    }

    public BigDecimal getBorrowTotal() {
        return borrowTotal;
    }

    public void setBorrowTotal(BigDecimal borrowTotal) {
        this.borrowTotal = borrowTotal;
    }

    public Integer getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    private String registTime;
}

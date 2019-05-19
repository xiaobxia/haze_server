package com.info.web.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OutChannelLook implements Serializable {
    //渠道id
    private int id;
    //渠道名称
    private String channelName;
    //日期
    private Date reportDate;
    //uv 注册数量
    private int uvCount;
    //注册数量
    private int registerCountResult;
    //申请笔数
    private int borrowApplyCount;
    //放款笔数
    private int loanCount;
    //还款笔数
    private int repaymentCount;
    //注册率
    private String registRatio;
    //申请率
    private String applyRatio;
    //下款率
    private String loanRatio;
    //回款率
    private String repayRatio;
    //qq占比
    private String qqRate;
    //微信占比
    private String wechatRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getUvCount() {
        return uvCount;
    }

    public void setUvCount(int uvCount) {
        this.uvCount = uvCount;
    }

    public int getRegisterCountResult() {
        return registerCountResult;
    }

    public void setRegisterCountResult(int registerCountResult) {
        this.registerCountResult = registerCountResult;
    }

    public int getBorrowApplyCount() {
        return borrowApplyCount;
    }

    public void setBorrowApplyCount(int borrowApplyCount) {
        this.borrowApplyCount = borrowApplyCount;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }

    public int getRepaymentCount() {
        return repaymentCount;
    }

    public void setRepaymentCount(int repaymentCount) {
        this.repaymentCount = repaymentCount;
    }

    public String getRegistRatio() {
        return registRatio;
    }

    public void setRegistRatio(String registRatio) {
        this.registRatio = registRatio;
    }

    public String getApplyRatio() {
        return applyRatio;
    }

    public void setApplyRatio(String applyRatio) {
        this.applyRatio = applyRatio;
    }

    public String getLoanRatio() {
        return loanRatio;
    }

    public void setLoanRatio(String loanRatio) {
        this.loanRatio = loanRatio;
    }

    public String getRepayRatio() {
        return repayRatio;
    }

    public void setRepayRatio(String repayRatio) {
        this.repayRatio = repayRatio;
    }

    public String getQqRate() {
        return qqRate;
    }

    public void setQqRate(String qqRate) {
        this.qqRate = qqRate;
    }
}

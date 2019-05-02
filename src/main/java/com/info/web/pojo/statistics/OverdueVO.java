package com.info.web.pojo.statistics;

/**
 * Created by tl on 2018/4/2.
 */

public class OverdueVO {
    //审核员姓名加手机号
    private String reviewUser;
    //审核量
    private Integer reviewNum;
    //过件量
    private Integer passNum;
    //过件率
    private Double passRate;
    //已还量
    private Integer paidNum;
    //待还量
    private Integer toPayNum;
    //首逾量
    private Integer firstOverdueNum;
    //首逾率
    private Double firstOverdueRate;
    //催回量
    private Integer collectNum;
    //催回率
    private Double collectRate;
    //总续期次数
    private Integer renewalNum;

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    public Integer getPaidNum() {
        return paidNum;
    }

    public void setPaidNum(Integer paidNum) {
        this.paidNum = paidNum;
    }

    public Integer getToPayNum() {
        return toPayNum;
    }

    public void setToPayNum(Integer toPayNum) {
        this.toPayNum = toPayNum;
    }

    public Integer getFirstOverdueNum() {
        return firstOverdueNum;
    }

    public void setFirstOverdueNum(Integer firstOverdueNum) {
        this.firstOverdueNum = firstOverdueNum;
    }

    public Double getFirstOverdueRate() {
        return firstOverdueRate;
    }

    public void setFirstOverdueRate(Double firstOverdueRate) {
        this.firstOverdueRate = firstOverdueRate;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Double getCollectRate() {
        return collectRate;
    }

    public void setCollectRate(Double collectRate) {
        this.collectRate = collectRate;
    }

    public Integer getRenewalNum() {
        return renewalNum;
    }

    public void setRenewalNum(Integer renewalNum) {
        this.renewalNum = renewalNum;
    }
}

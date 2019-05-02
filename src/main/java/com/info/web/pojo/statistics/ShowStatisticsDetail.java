package com.info.web.pojo.statistics;

public class ShowStatisticsDetail {


    private String createTime;
    private String beginTime;
    private String endTime;
    private String assignType;
    private String jobName;
    private Integer sysCount;
    private Integer sysPaidCount;
    private Integer manCount;
    private Integer manPaidCount;
    private float paidRate; //还款率

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getSysCount() {
        return sysCount;
    }

    public void setSysCount(Integer sysCount) {
        this.sysCount = sysCount;
    }

    public Integer getSysPaidCount() {
        return sysPaidCount;
    }

    public void setSysPaidCount(Integer sysPaidCount) {
        this.sysPaidCount = sysPaidCount;
    }

    public Integer getManCount() {
        return manCount;
    }

    public void setManCount(Integer manCount) {
        this.manCount = manCount;
    }

    public Integer getManPaidCount() {
        return manPaidCount;
    }

    public void setManPaidCount(Integer manPaidCount) {
        this.manPaidCount = manPaidCount;
    }

    public float getPaidRate() {
        return paidRate;
    }

    public void setPaidRate(float paidRate) {
        this.paidRate = paidRate;
    }
}

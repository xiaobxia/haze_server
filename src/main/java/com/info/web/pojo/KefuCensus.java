package com.info.web.pojo;

public class KefuCensus {
    private Integer id;
    private Integer dayCount;
    private Integer dayRepayCount;
    private Integer allCount;
    private Integer allRepayCount;
    private String createTime;
    private Integer jobId;
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getDayRepayCount() {
        return dayRepayCount;
    }

    public void setDayRepayCount(Integer dayRepayCount) {
        this.dayRepayCount = dayRepayCount;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getAllRepayCount() {
        return allRepayCount;
    }

    public void setAllRepayCount(Integer allRepayCount) {
        this.allRepayCount = allRepayCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

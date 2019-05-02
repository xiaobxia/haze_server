package com.info.web.pojo;

import java.util.Date;

public class BorrowRemark {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer remarkFlag;

    private Integer assignId;

    private Integer lastFlag;

    private String jobName;

    private String jobPhone;

    private String remarkContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRemarkFlag() {
        return remarkFlag;
    }

    public void setRemarkFlag(Integer remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public Integer getLastFlag() {
        return lastFlag;
    }

    public void setLastFlag(Integer lastFlag) {
        this.lastFlag = lastFlag;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobPhone() {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
        this.jobPhone = jobPhone == null ? null : jobPhone.trim();
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent == null ? null : remarkContent.trim();
    }
}
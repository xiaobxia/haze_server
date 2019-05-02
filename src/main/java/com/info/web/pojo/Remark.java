package com.info.web.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Remark {
    private Integer id;
    private Integer remarkFlag; //备注标志
    private String remarkContent; //备注内容
    private Date createTime;
    private Date updateTime;
    private Integer assignId; //订单分配id
    private String jobPhone; //客服电话
    private String jobName;//客服名称

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public String getJobPhone() {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
        this.jobPhone = jobPhone;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public static final Integer ON_PHONE = 1; //在通话中
    public static final Integer PHONE_SHUT = -1; //已关机
    public static final Integer USER_REJECT = -2; //用户不接电话
    public static final Integer DEAL_BORROW_NOW = 2; //立即处理还款
    public static final Integer TOMORROW_PAY = 3; //明天还款
    public static final Integer BEFORE_NIGHT_TWELVE = 4; //晚上12点前处理还款
    public static final Integer NO_CALL_PHONE = -3;//用户未接
    public static final Map<Integer, String> borrowRemarkMap = new HashMap<Integer, String>();
    static {
        borrowRemarkMap.put(ON_PHONE,"通话中");
        borrowRemarkMap.put(PHONE_SHUT,"已关机");
        borrowRemarkMap.put(USER_REJECT,"用户按掉");
        borrowRemarkMap.put(DEAL_BORROW_NOW,"立即处理还款");
        borrowRemarkMap.put(TOMORROW_PAY,"明天还款");
        borrowRemarkMap.put(BEFORE_NIGHT_TWELVE,"晚上12点钱处理还款");
        borrowRemarkMap.put(NO_CALL_PHONE,"用户未接");
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemarkFlag() {
        return remarkFlag;
    }

    public void setRemarkFlag(Integer remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
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
}

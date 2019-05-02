package com.info.back.pojo;

import java.util.Date;

/**
 * 客服排班表
 */
public class CustomerClassArrange {
    /**
     * 排班日期 yyyy-MM-dd
     */
    private String classDate;
    /**
     * 早班客服列表
     */
    private String classMorCustomers;
    /**
     * 晚班客服列表
     */
    private String classNigCustomers;
    /**
     * 修改更新时间
     */
    private Date updateTime;

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassMorCustomers() {
        return classMorCustomers;
    }

    public void setClassMorCustomers(String classMorCustomers) {
        this.classMorCustomers = classMorCustomers;
    }

    public String getClassNigCustomers() {
        return classNigCustomers;
    }

    public void setClassNigCustomers(String classNigCustomers) {
        this.classNigCustomers = classNigCustomers;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CustomerClassArrange{" +
                "classDate=" + classDate +
                ", classMorCustomers='" + classMorCustomers + '\'' +
                ", classNigCustomers='" + classNigCustomers + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}

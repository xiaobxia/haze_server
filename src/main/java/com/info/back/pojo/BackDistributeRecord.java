package com.info.back.pojo;

import java.util.Date;

/**
 * 派单操作日志记录
 *
 * @author:tgy
 */
public class BackDistributeRecord {
    /**
     * 主键自增ID
     */
    private Long id;

    /**
     * 操作员用户ID
     */
    private Integer oprUserId;

    /**
     * 被操作用户ID
     */
    private Integer oprSetUserId;

    /**
     * 操作时间
     */
    private Date oprSetDate;

    /**
     * 操作数据(派单比例)修改
     */
    private Integer oprSetRate;

    /**
     * 手动派单笔数
     */
    private Integer oprDisCount;

    /**
     * 操作员用户名
     */
    private String oprUsrName;

    /**
     * 操作员用户名
     */
    private String oprSetUsrName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOprUserId() {
        return oprUserId;
    }

    public void setOprUserId(Integer oprUserId) {
        this.oprUserId = oprUserId;
    }

    public Integer getOprSetUserId() {
        return oprSetUserId;
    }

    public void setOprSetUserId(Integer oprSetUserId) {
        this.oprSetUserId = oprSetUserId;
    }

    public Date getOprSetDate() {
        return oprSetDate;
    }

    public void setOprSetDate(Date oprSetDate) {
        this.oprSetDate = oprSetDate;
    }

    public Integer getOprSetRate() {
        return oprSetRate;
    }

    public void setOprSetRate(Integer oprSetRate) {
        this.oprSetRate = oprSetRate;
    }

    public Integer getOprDisCount() {
        return oprDisCount;
    }

    public void setOprDisCount(Integer oprDisCount) {
        this.oprDisCount = oprDisCount;
    }

    public String getOprUsrName() {
        return oprUsrName;
    }

    public void setOprUsrName(String oprUsrName) {
        this.oprUsrName = oprUsrName;
    }

    public String getOprSetUsrName() {
        return oprSetUsrName;
    }

    public void setOprSetUsrName(String oprSetUsrName) {
        this.oprSetUsrName = oprSetUsrName;
    }
}
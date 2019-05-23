package com.info.web.pojo;



import java.io.Serializable;

/**
 * 续期表
 */
public class BackExtend implements Serializable {
    private Integer id;
    private String extendName;
    private Integer extendCount;
    private Integer extendMoney;
    private Integer extendStatus;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public Integer getExtendCount() {
        return extendCount;
    }

    public void setExtendCount(Integer extendCount) {
        this.extendCount = extendCount;
    }

    public Integer getExtendMoney() {
        return extendMoney;
    }

    public void setExtendMoney(Integer extendMoney) {
        this.extendMoney = extendMoney;
    }

    public Integer getExtendStatus() {
        return extendStatus;
    }

    public void setExtendStatus(Integer extendStatus) {
        this.extendStatus = extendStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
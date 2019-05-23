package com.info.web.pojo;



import java.io.Serializable;
import java.util.Date;

/**
 * 续期表
 */
public class BackExtend implements Serializable {
    private Integer id;//id
    private String extendName;//续期类型
    private Integer extendCount;//续期次数
    private Integer extendMoney;//续期费用
    private Integer extendStatus;//续期状态
    private String remark;//备注
    private Integer extendDay;//续期天数
    private Date createDate;//创建时间
    private Date updateDate;//更新时间

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

    public Integer getExtendDay() {
        return extendDay;
    }

    public void setExtendDay(Integer extendDay) {
        this.extendDay = extendDay;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
package com.info.web.pojo;




import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

/**
 * 提额表
 */
public class BackLimit implements Serializable {
    private Integer id;//id
    private String limitName;//提额类型
    private Integer limitCount;//还款？次可提额
    private Integer limitStatus;//提额状态 0 开启 1 关闭
    private Integer limitProductId;//提额至？个产品
    private String limitRemark;//备注
    private String limiitProductName;//产品名称
    private Date createDate;//创建时间
    private Date updateDate;//更新时间
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(Integer limitStatus) {
        this.limitStatus = limitStatus;
    }

    public Integer getLimitProductId() {
        return limitProductId;
    }

    public void setLimitProductId(Integer limitProductId) {
        this.limitProductId = limitProductId;
    }

    public String getLimitRemark() {
        return limitRemark;
    }

    public void setLimitRemark(String limitRemark) {
        this.limitRemark = limitRemark;
    }

    public String getLimiitProductName() {
        return limiitProductName;
    }

    public void setLimiitProductName(String limiitProductName) {
        this.limiitProductName = limiitProductName;
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
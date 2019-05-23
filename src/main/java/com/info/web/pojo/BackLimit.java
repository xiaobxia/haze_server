package com.info.web.pojo;



import java.io.Serializable;

/**
 * 提额表
 */
public class BackLimit implements Serializable {
    private Integer id;//id
    private String limitName;//提额类型
    private Integer limitCount;//还款？次可提额
    private Integer limitStatus;//提额至？个产品
    private Integer limitProductId;//提额状态 0 开启 1 关闭
    private String limitRemark;//备注
    private String productName;//tie

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
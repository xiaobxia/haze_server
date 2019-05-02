package com.info.web.pojo;


public class BorrowOrderTdDevice {
    private Integer id;
    private Integer assetBorrowOrderId;
    private Integer userId;

    private String deviceContent;
    private String fqzContent;

    private String requestParams;
    private String returnParams;

    private String status; //1 未验证(默认状态) 2 已验证（未通过） 3 通过（以验证）
    private String remark;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAssetBorrowOrderId(Integer assetBorrowOrderId) {
        this.assetBorrowOrderId = assetBorrowOrderId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDeviceContent(String deviceContent) {
        this.deviceContent = deviceContent;
    }

    public void setFqzContent(String fqzContent) {
        this.fqzContent = fqzContent;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public void setReturnParams(String returnParams) {
        this.returnParams = returnParams;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {

        return id;
    }

    public Integer getAssetBorrowOrderId() {
        return assetBorrowOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDeviceContent() {
        return deviceContent;
    }

    public String getFqzContent() {
        return fqzContent;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public String getReturnParams() {
        return returnParams;
    }

    public String getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return "BorrowOrderTdDevice{" +
                "id=" + id +
                ", assetBorrowOrderId=" + assetBorrowOrderId +
                ", userId=" + userId +
                ", deviceContent='" + deviceContent + '\'' +
                ", fqzContent='" + fqzContent + '\'' +
                ", requestParams='" + requestParams + '\'' +
                ", returnParams='" + returnParams + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.info.web.pojo;


import java.io.Serializable;
import java.util.Date;

public class UserBlack implements Serializable {
    private Integer id;//id
    private String userName;//姓名
    private String userPhone;//手机号
    private String idNumber;//身份证号
    private Integer status;//状态
    private String remark;//备注
    private Date createTime;//创建时间
    private Integer userType;//用户类型 0 黑名单 1 白名单

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBlack userBlack = (UserBlack) o;

        if (id != null ? !id.equals(userBlack.id) : userBlack.id != null) return false;
        if (userName != null ? !userName.equals(userBlack.userName) : userBlack.userName != null) return false;
        if (userPhone != null ? !userPhone.equals(userBlack.userPhone) : userBlack.userPhone != null) return false;
        if (idNumber != null ? !idNumber.equals(userBlack.idNumber) : userBlack.idNumber != null) return false;
        if (status != null ? !status.equals(userBlack.status) : userBlack.status != null) return false;
        if (remark != null ? !remark.equals(userBlack.remark) : userBlack.remark != null) return false;
        if (createTime != null ? !createTime.equals(userBlack.createTime) : userBlack.createTime != null) return false;
        return userType != null ? userType.equals(userBlack.userType) : userBlack.userType == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPhone != null ? userPhone.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }
}
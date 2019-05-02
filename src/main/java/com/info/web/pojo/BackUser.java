package com.info.web.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;

/**
 * 
 * 类描述：后台用户类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 上午11:13:16 <br>
 * 
 *
 */
public class BackUser implements Serializable {
	private Integer id;
	private String userAccount;
	private String userPassword;
	private String userName;
	private String userSex;
	private String userAddress;
	private String userTelephone;
	private String userMobile;
	private String userEmail;
	private String userQq;
	private Date createDate;
	private Date updateDate;
	private String addIp;
	private String remark;
	private Integer status;
	private Integer msgFlag;
    private Integer distributionRate;
	/**
	 * 客服排班上限 1：true 2：false
	 */
	private Integer orderLimitFlag;
    private Integer orderLimitMor;
    private Integer orderLimitNig;

	/** 用户状态 */
	public static final HashMap<Integer, String> ALL_STATUS = new HashMap<>();
	/** 启用 */
	public static final Integer STATUS_USE = 1;
	/** 删除 */
	public static final Integer STATUS_DELETE = 2;
	/** 复审发短信标识 */
	public static final Integer MSG_ON = 1;
	/** 复审发短信标识 */
	public static final Integer MSG_OFF = 0;
	static {
		ALL_STATUS.put(STATUS_USE, "启用");
		ALL_STATUS.put(STATUS_DELETE, "删除");
	}

    public Integer getDistributionRate() {
        return distributionRate;
    }

    public void setDistributionRate(Integer distributionRate) {
        this.distributionRate = distributionRate;
    }

	public Integer getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(Integer msgFlag) {
		this.msgFlag = msgFlag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
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

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderLimitMor() {
		return orderLimitMor;
	}

	public void setOrderLimitMor(Integer orderLimitMor) {
		this.orderLimitMor = orderLimitMor;
	}

	public Integer getOrderLimitNig() {
		return orderLimitNig;
	}

	public void setOrderLimitNig(Integer orderLimitNig) {
		this.orderLimitNig = orderLimitNig;
	}

	public Integer getOrderLimitFlag() {
		return orderLimitFlag;
	}

	public void setOrderLimitFlag(Integer orderLimitFlag) {
		this.orderLimitFlag = orderLimitFlag;
	}

	@Override
	public String toString() {
		return "BackUser{" +
				"id=" + id +
				", userAccount='" + userAccount + '\'' +
				", userPassword='" + userPassword + '\'' +
				", userName='" + userName + '\'' +
				", userSex='" + userSex + '\'' +
				", userAddress='" + userAddress + '\'' +
				", userTelephone='" + userTelephone + '\'' +
				", userMobile='" + userMobile + '\'' +
				", userEmail='" + userEmail + '\'' +
				", userQq='" + userQq + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", addIp='" + addIp + '\'' +
				", remark='" + remark + '\'' +
				", status=" + status +
				", msgFlag=" + msgFlag +
				", distributionRate=" + distributionRate +
				", orderLimitFlag=" + orderLimitFlag +
				", orderLimitMor=" + orderLimitMor +
				", orderLimitNig=" + orderLimitNig +
				'}';
	}
}

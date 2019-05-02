package com.info.risk.pojo;

import java.util.Date;

public class RiskBlackUser {
	private Integer id;
	private Integer userId;
	private String userName;
	private String cardNum;
	private String userPhone;
	private Date addTime;
	private Date updateTime;
	private Integer blackType;
	private String blackDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getBlackType() {
		return blackType;
	}

	public void setBlackType(Integer blackType) {
		this.blackType = blackType;
	}

	public String getBlackDesc() {
		return blackDesc;
	}

	public void setBlackDesc(String blackDesc) {
		this.blackDesc = blackDesc;
	}

	public RiskBlackUser() {
		super();
	}

	public RiskBlackUser(Integer userId, String userName, String cardNum, String userPhone, Integer blackType, String blackDesc) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.cardNum = cardNum;
		this.userPhone = userPhone;
		this.blackType = blackType;
		this.blackDesc = blackDesc;
	}

}

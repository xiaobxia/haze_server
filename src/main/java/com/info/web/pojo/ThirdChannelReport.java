package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ThirdChannelReport {
	
	private Integer id;
	private Date reportDate;
	private Integer registerCount;
	private String thirdType;
	private Integer attestationRealnameCount;
	private Integer attestationBankCount;
	private Integer contactCount;
	private Integer jxlCount;
	private Integer alipayCount;
	private Integer zhimaCount;
	private Integer companyCount;
	private Integer borrowApplyCount;
	private Integer borrowSucCount;
	private Integer blackUserCount;
	private Integer lateDayCount;
	private Integer lateDaySumCount;
	private BigDecimal borrowRate;
	private BigDecimal passRate;
	private BigDecimal intoMoney;
	private Integer approveErrorCount;
	private Integer thirdId;
	private Integer channelid; 
	private BigDecimal overdueMoney;
	private BigDecimal overdueMoneySum;
	private BigDecimal baddebtMoney;
	private BigDecimal baddebtMoneySum;
	private Date createdAt;
	
	
	public BigDecimal getOverdueMoney() {
		return overdueMoney;
	}
	public void setOverdueMoney(BigDecimal overdueMoney) {
		this.overdueMoney = overdueMoney;
	}
	public BigDecimal getOverdueMoneySum() {
		return overdueMoneySum;
	}
	public void setOverdueMoneySum(BigDecimal overdueMoneySum) {
		this.overdueMoneySum = overdueMoneySum;
	}
	public BigDecimal getBaddebtMoney() {
		return baddebtMoney;
	}
	public void setBaddebtMoney(BigDecimal baddebtMoney) {
		this.baddebtMoney = baddebtMoney;
	}
	public BigDecimal getBaddebtMoneySum() {
		return baddebtMoneySum;
	}
	public void setBaddebtMoneySum(BigDecimal baddebtMoneySum) {
		this.baddebtMoneySum = baddebtMoneySum;
	}
	public String getThirdType() {
		return thirdType;
	}
	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}
	public Integer getChannelid() {
		return channelid;
	}
	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}
	public Integer getThirdId() {
		return thirdId;
	}
	public void setThirdId(Integer thirdId) {
		this.thirdId = thirdId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getBorrowRate() {
		return borrowRate;
	}
	public void setBorrowRate(BigDecimal borrowRate) {
		this.borrowRate = borrowRate;
	}
	public Integer getLateDaySumCount() {
		return lateDaySumCount;
	}
	public void setLateDaySumCount(Integer lateDaySumCount) {
		this.lateDaySumCount = lateDaySumCount;
	}
	public Integer getBlackUserCount() {
		return blackUserCount;
	}
	public void setBlackUserCount(Integer blackUserCount) {
		this.blackUserCount = blackUserCount;
	}
	public Integer getLateDayCount() {
		return lateDayCount;
	}
	public void setLateDayCount(Integer lateDayCount) {
		this.lateDayCount = lateDayCount;
	}
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getRegisterCount() {
		return registerCount;
	}
	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}
	public Integer getAttestationRealnameCount() {
		return attestationRealnameCount;
	}
	public void setAttestationRealnameCount(Integer attestationRealnameCount) {
		this.attestationRealnameCount = attestationRealnameCount;
	}
	public Integer getAttestationBankCount() {
		return attestationBankCount;
	}
	public void setAttestationBankCount(Integer attestationBankCount) {
		this.attestationBankCount = attestationBankCount;
	}
	public Integer getContactCount() {
		return contactCount;
	}
	public void setContactCount(Integer contactCount) {
		this.contactCount = contactCount;
	}
	public Integer getJxlCount() {
		return jxlCount;
	}
	public void setJxlCount(Integer jxlCount) {
		this.jxlCount = jxlCount;
	}
	public Integer getAlipayCount() {
		return alipayCount;
	}
	public void setAlipayCount(Integer alipayCount) {
		this.alipayCount = alipayCount;
	}
	public Integer getZhimaCount() {
		return zhimaCount;
	}
	public void setZhimaCount(Integer zhimaCount) {
		this.zhimaCount = zhimaCount;
	}
	public Integer getCompanyCount() {
		return companyCount;
	}
	public void setCompanyCount(Integer companyCount) {
		this.companyCount = companyCount;
	}
	public Integer getBorrowApplyCount() {
		return borrowApplyCount;
	}
	public void setBorrowApplyCount(Integer borrowApplyCount) {
		this.borrowApplyCount = borrowApplyCount;
	}
	 
	public Integer getBorrowSucCount() {
		return borrowSucCount;
	}
	public void setBorrowSucCount(Integer borrowSucCount) {
		this.borrowSucCount = borrowSucCount;
	}
	public BigDecimal getPassRate() {
		return passRate;
	}
	public void setPassRate(BigDecimal passRate) {
		this.passRate = passRate;
	}
	public BigDecimal getIntoMoney() {
		return intoMoney;
	}
	public void setIntoMoney(BigDecimal intoMoney) {
		this.intoMoney = intoMoney;
	}
	public Integer getApproveErrorCount() {
		return approveErrorCount;
	}
	public void setApproveErrorCount(Integer approveErrorCount) {
		this.approveErrorCount = approveErrorCount;
	}

	
}

package com.info.web.pojo;

import java.util.Date;

public class LoanReport {

	private Integer id;
	private Integer loanOrderCount;
	private Integer loanFourdayCount;
	private Integer loanSevendayCount;
	private Long moneyAmountCount;
	private Integer fourdayMoneyCount;
	private Long sevendayMoenyCount;
	private Integer oldLoanOrderCount;
	private Long oldLoanMoneyCount;
	private Integer newLoanOrderCount;
	private Long newLoanMoneyCount;
	private Date reportDate;
	
	private Integer registerCount;
	private	Integer borrowApplyCount;
	private	Integer borrowSucCount;
	private Date createdAt;
	
	 
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getMoneyAmountCount() {
		return moneyAmountCount;
	}
	public void setMoneyAmountCount(Long moneyAmountCount) {
		this.moneyAmountCount = moneyAmountCount;
	}
	public Long getSevendayMoenyCount() {
		return sevendayMoenyCount;
	}
	public void setSevendayMoenyCount(Long sevendayMoenyCount) {
		this.sevendayMoenyCount = sevendayMoenyCount;
	}
	public Long getOldLoanMoneyCount() {
		return oldLoanMoneyCount;
	}
	public void setOldLoanMoneyCount(Long oldLoanMoneyCount) {
		this.oldLoanMoneyCount = oldLoanMoneyCount;
	}
	public Long getNewLoanMoneyCount() {
		return newLoanMoneyCount;
	}
	public void setNewLoanMoneyCount(Long newLoanMoneyCount) {
		this.newLoanMoneyCount = newLoanMoneyCount;
	}
	public Integer getRegisterCount() {
		return registerCount;
	}
	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLoanOrderCount() {
		return loanOrderCount;
	}
	public void setLoanOrderCount(Integer loanOrderCount) {
		this.loanOrderCount = loanOrderCount;
	}
	public Integer getLoanFourdayCount() {
		return loanFourdayCount;
	}
	public void setLoanFourdayCount(Integer loanFourdayCount) {
		this.loanFourdayCount = loanFourdayCount;
	}
	public Integer getLoanSevendayCount() {
		return loanSevendayCount;
	}
	public void setLoanSevendayCount(Integer loanSevendayCount) {
		this.loanSevendayCount = loanSevendayCount;
	}
	public Integer getFourdayMoneyCount() {
		return fourdayMoneyCount;
	}
	public void setFourdayMoneyCount(Integer fourdayMoneyCount) {
		this.fourdayMoneyCount = fourdayMoneyCount;
	}
	public Integer getOldLoanOrderCount() {
		return oldLoanOrderCount;
	}
	public void setOldLoanOrderCount(Integer oldLoanOrderCount) {
		this.oldLoanOrderCount = oldLoanOrderCount;
	}
	public Integer getNewLoanOrderCount() {
		return newLoanOrderCount;
	}
	public void setNewLoanOrderCount(Integer newLoanOrderCount) {
		this.newLoanOrderCount = newLoanOrderCount;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	
}

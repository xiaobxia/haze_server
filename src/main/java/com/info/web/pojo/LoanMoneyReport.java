package com.info.web.pojo;

import java.util.Date;

/**
 * 用与给催收系统报表统计
 * @author Administrator
 *
 */
public class LoanMoneyReport {

	private Integer id;
	private Date reportDate;
	private Long expireAmount;
	private Long moneyAmountCount;
	private Long fourteendayMoneyCount;
	private Long sevendayMoenyCount;
	private Long  overdueAmount;
	private Long  overdueRateSevenAmount;
	private Long  overdueRateFourteenAmount;
	private Date created_at;
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
	public Long getExpireAmount() {
		return expireAmount;
	}
	public void setExpireAmount(Long expireAmount) {
		this.expireAmount = expireAmount;
	}
	public Long getMoneyAmountCount() {
		return moneyAmountCount;
	}
	public void setMoneyAmountCount(Long moneyAmountCount) {
		this.moneyAmountCount = moneyAmountCount;
	}
	
	public Long getFourteendayMoneyCount() {
		return fourteendayMoneyCount;
	}
	public void setFourteendayMoneyCount(Long fourteendayMoneyCount) {
		this.fourteendayMoneyCount = fourteendayMoneyCount;
	}
	public Long getSevendayMoenyCount() {
		return sevendayMoenyCount;
	}
	public void setSevendayMoenyCount(Long sevendayMoenyCount) {
		this.sevendayMoenyCount = sevendayMoenyCount;
	}
	public Long getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(Long overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public Long getOverdueRateSevenAmount() {
		return overdueRateSevenAmount;
	}
	public void setOverdueRateSevenAmount(Long overdueRateSevenAmount) {
		this.overdueRateSevenAmount = overdueRateSevenAmount;
	}
	public Long getOverdueRateFourteenAmount() {
		return overdueRateFourteenAmount;
	}
	public void setOverdueRateFourteenAmount(Long overdueRateFourteenAmount) {
		this.overdueRateFourteenAmount = overdueRateFourteenAmount;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	
	 
	
}

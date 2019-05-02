package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class BorrowStatisticsReport {
    private Integer id;

    private Date reportDate;

    private Integer borrowCount;

    private Integer currentUsercount;

    private BigDecimal allBorrowRate;

    private BigDecimal dayPromotionRate;

    private BigDecimal weekPromotionRate;

    private BigDecimal monthPromotionRate;

    private BigDecimal avgPromotionRate;

    private BigDecimal danDayOverduerate;

    private BigDecimal danAvgOverduerate;

    private BigDecimal moneyDayOverduerate;

    private BigDecimal moneyAvgOverduerate;

    private BigDecimal danDayBaddebtrate;

    private BigDecimal danAvgBaddebtrate;

    private BigDecimal moneyDayBaddebtrate;

    private BigDecimal moneyAvgBaddebtrate;
    
    private BigDecimal moneyAmount;

    private Date updatedAt;

    
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

    public Integer getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;
    }

    public Integer getCurrentUsercount() {
        return currentUsercount;
    }

    public void setCurrentUsercount(Integer currentUsercount) {
        this.currentUsercount = currentUsercount;
    }

    public BigDecimal getAllBorrowRate() {
        return allBorrowRate;
    }

    public void setAllBorrowRate(BigDecimal allBorrowRate) {
        this.allBorrowRate = allBorrowRate;
    }

    public BigDecimal getDayPromotionRate() {
        return dayPromotionRate;
    }

    public void setDayPromotionRate(BigDecimal dayPromotionRate) {
        this.dayPromotionRate = dayPromotionRate;
    }

    public BigDecimal getWeekPromotionRate() {
        return weekPromotionRate;
    }

    public void setWeekPromotionRate(BigDecimal weekPromotionRate) {
        this.weekPromotionRate = weekPromotionRate;
    }

    public BigDecimal getMonthPromotionRate() {
        return monthPromotionRate;
    }

    public void setMonthPromotionRate(BigDecimal monthPromotionRate) {
        this.monthPromotionRate = monthPromotionRate;
    }

    public BigDecimal getAvgPromotionRate() {
        return avgPromotionRate;
    }

    public void setAvgPromotionRate(BigDecimal avgPromotionRate) {
        this.avgPromotionRate = avgPromotionRate;
    }

    public BigDecimal getDanDayOverduerate() {
        return danDayOverduerate;
    }

    public void setDanDayOverduerate(BigDecimal danDayOverduerate) {
        this.danDayOverduerate = danDayOverduerate;
    }

    public BigDecimal getDanAvgOverduerate() {
        return danAvgOverduerate;
    }

    public void setDanAvgOverduerate(BigDecimal danAvgOverduerate) {
        this.danAvgOverduerate = danAvgOverduerate;
    }

    public BigDecimal getMoneyDayOverduerate() {
        return moneyDayOverduerate;
    }

    public void setMoneyDayOverduerate(BigDecimal moneyDayOverduerate) {
        this.moneyDayOverduerate = moneyDayOverduerate;
    }

    public BigDecimal getMoneyAvgOverduerate() {
        return moneyAvgOverduerate;
    }

    public void setMoneyAvgOverduerate(BigDecimal moneyAvgOverduerate) {
        this.moneyAvgOverduerate = moneyAvgOverduerate;
    }

    public BigDecimal getDanDayBaddebtrate() {
        return danDayBaddebtrate;
    }

    public void setDanDayBaddebtrate(BigDecimal danDayBaddebtrate) {
        this.danDayBaddebtrate = danDayBaddebtrate;
    }

    public BigDecimal getDanAvgBaddebtrate() {
        return danAvgBaddebtrate;
    }

    public void setDanAvgBaddebtrate(BigDecimal danAvgBaddebtrate) {
        this.danAvgBaddebtrate = danAvgBaddebtrate;
    }

    public BigDecimal getMoneyDayBaddebtrate() {
        return moneyDayBaddebtrate;
    }

    public void setMoneyDayBaddebtrate(BigDecimal moneyDayBaddebtrate) {
        this.moneyDayBaddebtrate = moneyDayBaddebtrate;
    }

    public BigDecimal getMoneyAvgBaddebtrate() {
        return moneyAvgBaddebtrate;
    }

    public void setMoneyAvgBaddebtrate(BigDecimal moneyAvgBaddebtrate) {
        this.moneyAvgBaddebtrate = moneyAvgBaddebtrate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
    
}
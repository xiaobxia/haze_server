package com.info.web.pojo;

import java.util.Date;

public class ReportRepayment {
    private Integer id;

    private String reportDate;

    private Long overdueRate;

    private Long repayRate;

    private Long overdueRateSeven;

    private Long overdueRateFourteen;

    private Long overdueRateOld;

    private Long repayRateOld;

    private Long overdueRateNew;

    private Long repayRateNew;

    private Long expireAmount;

    private Long overdueAmount;

    private Long expireAmountOld;

    private Long overdueAmountOld;

    private Long expireAmountNew;

    private Long overdueAmountNew;

    private Long allBorrowCount;

    private Long allBorrowAmount;

    private Long allRepayCount;

    private Long allRepayAmount;

    private Long allOverdueCount;

    private Long allOverdueAmount;

    private Long overdueRateS1Amount;

    private Long overdueRateS2Amount;

    private Long overdueRateS3Amount;

    private Long overdueRateM3Amount;

    private Long overdueRateS1Count;

    private Long overdueRateS2Count;

    private Long overdueRateS3Count;

    private Long overdueRateM3Count;

    private Long overdueRateAmount;

    private Long repayRateAmount;

    private Long overdueRateSevenAmount;

    private Long overdueRateFourteenAmount;

    private Long overdueRateOldAmount;

    private Long repayRateOldAmount;

    private Long overdueRateNewAmount;

    private Long repayRateNewAmount;

    private Date createTime;
    //新增字段
    private Long repayAllMoney;
    private Long repayNewMoney;
    private Long repayOldMoney;
    private Long repaySjRate;
    private Long repayNewSjRate;
    private Long repayOldSjRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate == null ? null : reportDate.trim();
    }

    public Long getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(Long overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Long getRepayRate() {
        return repayRate;
    }

    public void setRepayRate(Long repayRate) {
        this.repayRate = repayRate;
    }

    public Long getOverdueRateSeven() {
        return overdueRateSeven;
    }

    public void setOverdueRateSeven(Long overdueRateSeven) {
        this.overdueRateSeven = overdueRateSeven;
    }

    public Long getOverdueRateFourteen() {
        return overdueRateFourteen;
    }

    public void setOverdueRateFourteen(Long overdueRateFourteen) {
        this.overdueRateFourteen = overdueRateFourteen;
    }

    public Long getOverdueRateOld() {
        return overdueRateOld;
    }

    public void setOverdueRateOld(Long overdueRateOld) {
        this.overdueRateOld = overdueRateOld;
    }

    public Long getRepayRateOld() {
        return repayRateOld;
    }

    public void setRepayRateOld(Long repayRateOld) {
        this.repayRateOld = repayRateOld;
    }

    public Long getOverdueRateNew() {
        return overdueRateNew;
    }

    public void setOverdueRateNew(Long overdueRateNew) {
        this.overdueRateNew = overdueRateNew;
    }

    public Long getRepayRateNew() {
        return repayRateNew;
    }

    public void setRepayRateNew(Long repayRateNew) {
        this.repayRateNew = repayRateNew;
    }

    public Long getExpireAmount() {
        return expireAmount;
    }

    public void setExpireAmount(Long expireAmount) {
        this.expireAmount = expireAmount;
    }

    public Long getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Long overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Long getExpireAmountOld() {
        return expireAmountOld;
    }

    public void setExpireAmountOld(Long expireAmountOld) {
        this.expireAmountOld = expireAmountOld;
    }

    public Long getOverdueAmountOld() {
        return overdueAmountOld;
    }

    public void setOverdueAmountOld(Long overdueAmountOld) {
        this.overdueAmountOld = overdueAmountOld;
    }

    public Long getExpireAmountNew() {
        return expireAmountNew;
    }

    public void setExpireAmountNew(Long expireAmountNew) {
        this.expireAmountNew = expireAmountNew;
    }

    public Long getOverdueAmountNew() {
        return overdueAmountNew;
    }

    public void setOverdueAmountNew(Long overdueAmountNew) {
        this.overdueAmountNew = overdueAmountNew;
    }

    public Long getAllBorrowCount() {
        return allBorrowCount;
    }

    public void setAllBorrowCount(Long allBorrowCount) {
        this.allBorrowCount = allBorrowCount;
    }

    public Long getAllBorrowAmount() {
        return allBorrowAmount;
    }

    public void setAllBorrowAmount(Long allBorrowAmount) {
        this.allBorrowAmount = allBorrowAmount;
    }

    public Long getAllRepayCount() {
        return allRepayCount;
    }

    public void setAllRepayCount(Long allRepayCount) {
        this.allRepayCount = allRepayCount;
    }

    public Long getAllRepayAmount() {
        return allRepayAmount;
    }

    public void setAllRepayAmount(Long allRepayAmount) {
        this.allRepayAmount = allRepayAmount;
    }

    public Long getAllOverdueCount() {
        return allOverdueCount;
    }

    public void setAllOverdueCount(Long allOverdueCount) {
        this.allOverdueCount = allOverdueCount;
    }

    public Long getAllOverdueAmount() {
        return allOverdueAmount;
    }

    public void setAllOverdueAmount(Long allOverdueAmount) {
        this.allOverdueAmount = allOverdueAmount;
    }

    public Long getOverdueRateS1Amount() {
        return overdueRateS1Amount;
    }

    public void setOverdueRateS1Amount(Long overdueRateS1Amount) {
        this.overdueRateS1Amount = overdueRateS1Amount;
    }

    public Long getOverdueRateS2Amount() {
        return overdueRateS2Amount;
    }

    public void setOverdueRateS2Amount(Long overdueRateS2Amount) {
        this.overdueRateS2Amount = overdueRateS2Amount;
    }

    public Long getOverdueRateS3Amount() {
        return overdueRateS3Amount;
    }

    public void setOverdueRateS3Amount(Long overdueRateS3Amount) {
        this.overdueRateS3Amount = overdueRateS3Amount;
    }

    public Long getOverdueRateS1Count() {
        return overdueRateS1Count;
    }

    public void setOverdueRateS1Count(Long overdueRateS1Count) {
        this.overdueRateS1Count = overdueRateS1Count;
    }

    public Long getOverdueRateS2Count() {
        return overdueRateS2Count;
    }

    public void setOverdueRateS2Count(Long overdueRateS2Count) {
        this.overdueRateS2Count = overdueRateS2Count;
    }

    public Long getOverdueRateS3Count() {
        return overdueRateS3Count;
    }

    public void setOverdueRateS3Count(Long overdueRateS3Count) {
        this.overdueRateS3Count = overdueRateS3Count;
    }

    public Long getOverdueRateAmount() {
        return overdueRateAmount;
    }

    public void setOverdueRateAmount(Long overdueRateAmount) {
        this.overdueRateAmount = overdueRateAmount;
    }

    public Long getRepayRateAmount() {
        return repayRateAmount;
    }

    public void setRepayRateAmount(Long repayRateAmount) {
        this.repayRateAmount = repayRateAmount;
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

    public Long getOverdueRateOldAmount() {
        return overdueRateOldAmount;
    }

    public void setOverdueRateOldAmount(Long overdueRateOldAmount) {
        this.overdueRateOldAmount = overdueRateOldAmount;
    }

    public Long getRepayRateOldAmount() {
        return repayRateOldAmount;
    }

    public void setRepayRateOldAmount(Long repayRateOldAmount) {
        this.repayRateOldAmount = repayRateOldAmount;
    }

    public Long getOverdueRateNewAmount() {
        return overdueRateNewAmount;
    }

    public void setOverdueRateNewAmount(Long overdueRateNewAmount) {
        this.overdueRateNewAmount = overdueRateNewAmount;
    }

    public Long getRepayRateNewAmount() {
        return repayRateNewAmount;
    }

    public void setRepayRateNewAmount(Long repayRateNewAmount) {
        this.repayRateNewAmount = repayRateNewAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOverdueRateM3Amount() {
        return overdueRateM3Amount;
    }

    public void setOverdueRateM3Amount(Long overdueRateM3Amount) {
        this.overdueRateM3Amount = overdueRateM3Amount;
    }

    public Long getOverdueRateM3Count() {
        return overdueRateM3Count;
    }

    public void setOverdueRateM3Count(Long overdueRateM3Count) {
        this.overdueRateM3Count = overdueRateM3Count;
    }

    public Long getRepayAllMoney() {
        return repayAllMoney;
    }

    public void setRepayAllMoney(Long repayAllMoney) {
        this.repayAllMoney = repayAllMoney;
    }

    public Long getRepayNewMoney() {
        return repayNewMoney;
    }

    public void setRepayNewMoney(Long repayNewMoney) {
        this.repayNewMoney = repayNewMoney;
    }

    public Long getRepayOldMoney() {
        return repayOldMoney;
    }

    public void setRepayOldMoney(Long repayOldMoney) {
        this.repayOldMoney = repayOldMoney;
    }

    public Long getRepaySjRate() {
        return repaySjRate;
    }

    public void setRepaySjRate(Long repaySjRate) {
        this.repaySjRate = repaySjRate;
    }

    public Long getRepayNewSjRate() {
        return repayNewSjRate;
    }

    public void setRepayNewSjRate(Long repayNewSjRate) {
        this.repayNewSjRate = repayNewSjRate;
    }

    public Long getRepayOldSjRate() {
        return repayOldSjRate;
    }

    public void setRepayOldSjRate(Long repayOldSjRate) {
        this.repayOldSjRate = repayOldSjRate;
    }
}
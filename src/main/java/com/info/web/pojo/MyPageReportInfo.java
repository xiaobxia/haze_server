package com.info.web.pojo;

import java.math.BigDecimal;

/**
 * 运营系统后台首页数据pojo
 */
public class MyPageReportInfo {
    /**
     * 总放款金额
     */
    private BigDecimal allLoanMoney;
    /**
     * 总放款数量
     */
    private long allLoanCount;
    /**
     * 总待回款金额
     */
    private  BigDecimal allPendingRepayMoney;
    /**
     * 总待回款笔数
     */
    private long allRepaymentCount;
    /**
     *总已还款金额
     */
    private BigDecimal allRepayMoney;
    /**
     * 总已还款数量
     */
    private long allRepayCount;
    /**
     * 总未逾期待收金额
     */
    private BigDecimal allOverMoney;
    /**
     * 总未逾期待收笔数
     */
    private long allOverCount;
    /**
     * 三天内到期金额
     */
    private BigDecimal threeExpireMoney;
    /**
     * 放款失败金额
     */
    private BigDecimal failLoanMoney;
    /**
     * 放款失败笔数
     */
    private long failLoanCount;
    /**
     * 逾期1-3天总金额
     */
    private BigDecimal s1Money;
    /**
     * 逾期3-7天金额
     */
    private BigDecimal s2Money;
    /**
     * 逾期7-15天金额
     */
    private BigDecimal s3Money;
    /**
     * 逾期15天金额
     */
    private BigDecimal s4Money;
    /**
     * 总用户注册数
     */
    private long allRegistCount;
    /**
     * 总用户注册百分比
     */
    private String allRegistPercentage;
    /**
     * 当天注册用户数
     */
    private long registCount;
    /**
     * 当日申请用户数
     */
    private Integer applyCount;
    /**
     * 当日放款金额
     */
    private BigDecimal loanMoney;
    /**
     * 当日放款笔数
     */
    private long loanCount;
    /**
     * 当日到期金额
     */
    private BigDecimal  pendingRepayMoney;
    /**
     * 当日到期笔数
     */
    private long pendingRepayCount;
    /**
     *全部还款金额(当日已还金额)
     */
    private BigDecimal repyMoney;
    /**
     * 全部还款订单（当日已还订单）
     */
    private long repyCount;
    /**
     * 当日展期金额
     */
    private BigDecimal extendMoney;
    /**
     * 当日展期订单
     */
    private long extendCount;
    /**
     * 当日待收金额（当日未还金额）
     */
    private BigDecimal pendingMoney;
    /**
     * 当日待收订单
     */
    private long pendingCount;
    /**
     * 当日放款率
     */
    private  String loanPercentage;
    /**
     * 当日通过率
     */
    private String passPercentage;
    /**
     * 当日回款率
     */
    private String  repayPercentage;
    /**
     * 当日复借金额
     */
    private BigDecimal reBorrowMoney;
    /**
     * 当日复借订单数
     */
    private long reBorrowCount;

    public BigDecimal getAllLoanMoney() {
        return allLoanMoney;
    }

    public void setAllLoanMoney(BigDecimal allLoanMoney) {
        this.allLoanMoney = allLoanMoney;
    }

    public long getAllLoanCount() {
        return allLoanCount;
    }

    public void setAllLoanCount(long allLoanCount) {
        this.allLoanCount = allLoanCount;
    }

    public BigDecimal getAllPendingRepayMoney() {
        return allPendingRepayMoney;
    }

    public void setAllPendingRepayMoney(BigDecimal allPendingRepayMoney) {
        this.allPendingRepayMoney = allPendingRepayMoney;
    }

    public long getAllRepaymentCount() {
        return allRepaymentCount;
    }

    public void setAllRepaymentCount(long allRepaymentCount) {
        this.allRepaymentCount = allRepaymentCount;
    }

    public BigDecimal getAllRepayMoney() {
        return allRepayMoney;
    }

    public void setAllRepayMoney(BigDecimal allRepayMoney) {
        this.allRepayMoney = allRepayMoney;
    }

    public long getAllRepayCount() {
        return allRepayCount;
    }

    public void setAllRepayCount(long allRepayCount) {
        this.allRepayCount = allRepayCount;
    }

    public BigDecimal getAllOverMoney() {
        return allOverMoney;
    }

    public void setAllOverMoney(BigDecimal allOverMoney) {
        this.allOverMoney = allOverMoney;
    }

    public long getAllOverCount() {
        return allOverCount;
    }

    public void setAllOverCount(long allOverCount) {
        this.allOverCount = allOverCount;
    }

    public BigDecimal getThreeExpireMoney() {
        return threeExpireMoney;
    }

    public void setThreeExpireMoney(BigDecimal threeExpireMoney) {
        this.threeExpireMoney = threeExpireMoney;
    }

    public BigDecimal getFailLoanMoney() {
        return failLoanMoney;
    }

    public void setFailLoanMoney(BigDecimal failLoanMoney) {
        this.failLoanMoney = failLoanMoney;
    }

    public long getFailLoanCount() {
        return failLoanCount;
    }

    public void setFailLoanCount(long failLoanCount) {
        this.failLoanCount = failLoanCount;
    }

    public BigDecimal getS1Money() {
        return s1Money;
    }

    public void setS1Money(BigDecimal s1Money) {
        this.s1Money = s1Money;
    }

    public BigDecimal getS2Money() {
        return s2Money;
    }

    public void setS2Money(BigDecimal s2Money) {
        this.s2Money = s2Money;
    }

    public BigDecimal getS3Money() {
        return s3Money;
    }

    public void setS3Money(BigDecimal s3Money) {
        this.s3Money = s3Money;
    }

    public BigDecimal getS4Money() {
        return s4Money;
    }

    public void setS4Money(BigDecimal s4Money) {
        this.s4Money = s4Money;
    }

    public long getAllRegistCount() {
        return allRegistCount;
    }

    public void setAllRegistCount(long allRegistCount) {
        this.allRegistCount = allRegistCount;
    }

    public String getAllRegistPercentage() {
        return allRegistPercentage;
    }

    public void setAllRegistPercentage(String allRegistPercentage) {
        this.allRegistPercentage = allRegistPercentage;
    }

    public long getRegistCount() {
        return registCount;
    }

    public void setRegistCount(long registCount) {
        this.registCount = registCount;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public long getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(long loanCount) {
        this.loanCount = loanCount;
    }

    public BigDecimal getPendingRepayMoney() {
        return pendingRepayMoney;
    }

    public void setPendingRepayMoney(BigDecimal pendingRepayMoney) {
        this.pendingRepayMoney = pendingRepayMoney;
    }

    public long getPendingRepayCount() {
        return pendingRepayCount;
    }

    public void setPendingRepayCount(long pendingRepayCount) {
        this.pendingRepayCount = pendingRepayCount;
    }

    public BigDecimal getRepyMoney() {
        return repyMoney;
    }

    public void setRepyMoney(BigDecimal repyMoney) {
        this.repyMoney = repyMoney;
    }

    public long getRepyCount() {
        return repyCount;
    }

    public void setRepyCount(long repyCount) {
        this.repyCount = repyCount;
    }

    public BigDecimal getExtendMoney() {
        return extendMoney;
    }

    public void setExtendMoney(BigDecimal extendMoney) {
        this.extendMoney = extendMoney;
    }

    public long getExtendCount() {
        return extendCount;
    }

    public void setExtendCount(long extendCount) {
        this.extendCount = extendCount;
    }

    public BigDecimal getPendingMoney() {
        return pendingMoney;
    }

    public void setPendingMoney(BigDecimal pendingMoney) {
        this.pendingMoney = pendingMoney;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public String getLoanPercentage() {
        return loanPercentage;
    }

    public void setLoanPercentage(String loanPercentage) {
        this.loanPercentage = loanPercentage;
    }

    public String getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    public String getRepayPercentage() {
        return repayPercentage;
    }

    public void setRepayPercentage(String repayPercentage) {
        this.repayPercentage = repayPercentage;
    }

    public BigDecimal getReBorrowMoney() {
        return reBorrowMoney;
    }

    public void setReBorrowMoney(BigDecimal reBorrowMoney) {
        this.reBorrowMoney = reBorrowMoney;
    }

    public long getReBorrowCount() {
        return reBorrowCount;
    }

    public void setReBorrowCount(long reBorrowCount) {
        this.reBorrowCount = reBorrowCount;
    }

    @Override
    public String toString() {
        return "MyPageReportInfo{" +
                "allLoanMoney=" + allLoanMoney +
                ", allLoanCount=" + allLoanCount +
                ", allPendingRepayMoney=" + allPendingRepayMoney +
                ", allRepaymentCount=" + allRepaymentCount +
                ", allRepayMoney=" + allRepayMoney +
                ", allRepayCount=" + allRepayCount +
                ", allOverMoney=" + allOverMoney +
                ", allOverCount=" + allOverCount +
                ", threeExpireMoney=" + threeExpireMoney +
                ", failLoanMoney=" + failLoanMoney +
                ", failLoanCount=" + failLoanCount +
                ", s1Money=" + s1Money +
                ", s2Money=" + s2Money +
                ", s3Money=" + s3Money +
                ", s4Money=" + s4Money +
                ", allRegistCount=" + allRegistCount +
                ", allRegistPercentage=" + allRegistPercentage +
                ", registCount=" + registCount +
                ", applyCount=" + applyCount +
                ", loanMoney=" + loanMoney +
                ", loanCount=" + loanCount +
                ", pendingRepayMoney=" + pendingRepayMoney +
                ", pendingRepayCount=" + pendingRepayCount +
                ", repyMoney=" + repyMoney +
                ", repyCount=" + repyCount +
                ", extendMoney=" + extendMoney +
                ", extendCount=" + extendCount +
                ", pendingMoney=" + pendingMoney +
                ", pendingCount=" + pendingCount +
                ", loanPercentage=" + loanPercentage +
                ", passPercentage=" + passPercentage +
                ", repayPercentage=" + repayPercentage +
                ", reBorrowMoney=" + reBorrowMoney +
                ", reBorrowCount=" + reBorrowCount +
                '}';
    }

}

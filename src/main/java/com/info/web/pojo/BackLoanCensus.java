package com.info.web.pojo;


import java.math.BigDecimal;
import java.util.Date;

public class BackLoanCensus {
    private Integer id ;//'自增主键id',
    private String repayDate ; //'还款日期',
    private Integer expireCount ; //'到期笔数',
    private BigDecimal expireMoney;//'到期金额',
    private Integer repayCount; //'还款笔数',
    private BigDecimal repayMoney;  //'还款金额',
    private Integer extendCount; //'展期笔数',
    private BigDecimal extendMoney;  //'展期金额',
    private BigDecimal extendProductMoney;//展期产品金额
    private Integer amortizationLoanCount; //'部分（分期）还款笔数',
    private BigDecimal amortizationLoanMoney; //'部分（分期）还款金额',
    private Integer oveRepayCount;  //'逾期已还笔数',
    private BigDecimal oveRepayMoney; //'逾期已还金额',
    private Integer oveWaitCount; //'逾期未还笔数',
    private BigDecimal oveWaitMoney ;//'逾期未还金额',
    private Integer repayRate; //'首借回款率',
    private Integer reRepayRate; //'复借回款率',
    private Integer oveFirstRate;  //'首逾',
    private Integer twoRate;  //'2天逾期率',
    private Integer threeRate;   //'3天逾期率',
    private Integer sevenRate; //'7天逾期率',
    private Integer oveRate; //'逾期占比',
    private Date updateDate;//更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public Integer getExpireCount() {
        return expireCount;
    }

    public void setExpireCount(Integer expireCount) {
        this.expireCount = expireCount;
    }

    public BigDecimal getExpireMoney() {
        return expireMoney;
    }

    public void setExpireMoney(BigDecimal expireMoney) {
        this.expireMoney = expireMoney;
    }

    public Integer getRepayCount() {
        return repayCount;
    }

    public void setRepayCount(Integer repayCount) {
        this.repayCount = repayCount;
    }

    public BigDecimal getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(BigDecimal repayMoney) {
        this.repayMoney = repayMoney;
    }

    public Integer getExtendCount() {
        return extendCount;
    }

    public void setExtendCount(Integer extendCount) {
        this.extendCount = extendCount;
    }

    public BigDecimal getExtendMoney() {
        return extendMoney;
    }

    public void setExtendMoney(BigDecimal extendMoney) {
        this.extendMoney = extendMoney;
    }

    public Integer getAmortizationLoanCount() {
        return amortizationLoanCount;
    }

    public void setAmortizationLoanCount(Integer amortizationLoanCount) {
        this.amortizationLoanCount = amortizationLoanCount;
    }

    public BigDecimal getAmortizationLoanMoney() {
        return amortizationLoanMoney;
    }

    public void setAmortizationLoanMoney(BigDecimal amortizationLoanMoney) {
        this.amortizationLoanMoney = amortizationLoanMoney;
    }

    public Integer getOveRepayCount() {
        return oveRepayCount;
    }

    public void setOveRepayCount(Integer oveRepayCount) {
        this.oveRepayCount = oveRepayCount;
    }

    public BigDecimal getOveRepayMoney() {
        return oveRepayMoney;
    }

    public void setOveRepayMoney(BigDecimal oveRepayMoney) {
        this.oveRepayMoney = oveRepayMoney;
    }

    public Integer getOveWaitCount() {
        return oveWaitCount;
    }

    public void setOveWaitCount(Integer oveWaitCount) {
        this.oveWaitCount = oveWaitCount;
    }

    public BigDecimal getOveWaitMoney() {
        return oveWaitMoney;
    }

    public void setOveWaitMoney(BigDecimal oveWaitMoney) {
        this.oveWaitMoney = oveWaitMoney;
    }

    public Integer getRepayRate() {
        return repayRate;
    }

    public void setRepayRate(Integer repayRate) {
        this.repayRate = repayRate;
    }

    public Integer getReRepayRate() {
        return reRepayRate;
    }

    public void setReRepayRate(Integer reRepayRate) {
        this.reRepayRate = reRepayRate;
    }

    public Integer getTwoRate() {
        return twoRate;
    }

    public void setTwoRate(Integer twoRate) {
        this.twoRate = twoRate;
    }

    public Integer getThreeRate() {
        return threeRate;
    }

    public void setThreeRate(Integer threeRate) {
        this.threeRate = threeRate;
    }

    public Integer getSevenRate() {
        return sevenRate;
    }

    public void setSevenRate(Integer sevenRate) {
        this.sevenRate = sevenRate;
    }

    public Integer getOveRate() {
        return oveRate;
    }

    public void setOveRate(Integer oveRate) {
        this.oveRate = oveRate;
    }

    public Integer getOveFirstRate() {
        return oveFirstRate;
    }

    public void setOveFirstRate(Integer oveFirstRate) {
        this.oveFirstRate = oveFirstRate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getExtendProductMoney() {
        return extendProductMoney;
    }

    public void setExtendProductMoney(BigDecimal extendProductMoney) {
        this.extendProductMoney = extendProductMoney;
    }
}

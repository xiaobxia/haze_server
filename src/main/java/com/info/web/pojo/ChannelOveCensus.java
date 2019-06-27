package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelOveCensus {
    private Integer id ;//'自增主键',
    private String  repayTime;//'还款日期',
    private Integer newLoanCount =0;//'新用户放款数量',
    private Integer newRepayCount =0; //'新用户还款数量',
    private Integer oldLoanCount =0; //'老用户放款数量',
    private Integer  oldRepayCount =0; //'老用户还款数量',
    private Integer    extendCount =0; //'展期数量',
    private BigDecimal newLoanMoney; //'新用户放款金额',
    private BigDecimal newRepayMoney; //'新用户还款金额',
    private BigDecimal oldLoanMoney; //'老用户放款金额',
    private BigDecimal oldRepayMoney;//'老用户还款金额',
    private BigDecimal extendMoney;//'展期服务费',
    private BigDecimal extendProductMoney;//'展期产品金额',
    private Date updateTime; //'更新时间',
    private Integer channelId;//渠道id
    private Integer allLoanCount =0;//总放款量
    private Integer allRepayCount =0;//总还款量
    private Integer allOveRate =0;//总逾期率
    private Integer newOveRate =0;//新用户逾期率
    private Integer oldOveRate =0;//老用户逾期率
    private String channelSuperName;//渠道商名称
    private String channelName;//渠道名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getNewLoanCount() {
        return newLoanCount;
    }

    public void setNewLoanCount(Integer newLoanCount) {
        this.newLoanCount = newLoanCount;
    }

    public Integer getNewRepayCount() {
        return newRepayCount;
    }

    public void setNewRepayCount(Integer newRepayCount) {
        this.newRepayCount = newRepayCount;
    }

    public Integer getOldLoanCount() {
        return oldLoanCount;
    }

    public void setOldLoanCount(Integer oldLoanCount) {
        this.oldLoanCount = oldLoanCount;
    }

    public Integer getOldRepayCount() {
        return oldRepayCount;
    }

    public void setOldRepayCount(Integer oldRepayCount) {
        this.oldRepayCount = oldRepayCount;
    }

    public Integer getExtendCount() {
        return extendCount;
    }

    public void setExtendCount(Integer extendCount) {
        this.extendCount = extendCount;
    }

    public BigDecimal getNewLoanMoney() {
        return newLoanMoney;
    }

    public void setNewLoanMoney(BigDecimal newLoanMoney) {
        this.newLoanMoney = newLoanMoney;
    }

    public BigDecimal getNewRepayMoney() {
        return newRepayMoney;
    }

    public void setNewRepayMoney(BigDecimal newRepayMoney) {
        this.newRepayMoney = newRepayMoney;
    }

    public BigDecimal getOldLoanMoney() {
        return oldLoanMoney;
    }

    public void setOldLoanMoney(BigDecimal oldLoanMoney) {
        this.oldLoanMoney = oldLoanMoney;
    }

    public BigDecimal getOldRepayMoney() {
        return oldRepayMoney;
    }

    public void setOldRepayMoney(BigDecimal oldRepayMoney) {
        this.oldRepayMoney = oldRepayMoney;
    }

    public BigDecimal getExtendMoney() {
        return extendMoney;
    }

    public void setExtendMoney(BigDecimal extendMoney) {
        this.extendMoney = extendMoney;
    }

    public BigDecimal getExtendProductMoney() {
        return extendProductMoney;
    }

    public void setExtendProductMoney(BigDecimal extendProductMoney) {
        this.extendProductMoney = extendProductMoney;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAllLoanCount() {
        return allLoanCount;
    }

    public void setAllLoanCount(Integer allLoanCount) {
        this.allLoanCount = allLoanCount;
    }

    public Integer getAllRepayCount() {
        return allRepayCount;
    }

    public void setAllRepayCount(Integer allRepayCount) {
        this.allRepayCount = allRepayCount;
    }

    public Integer getAllOveRate() {
        return allOveRate;
    }

    public void setAllOveRate(Integer allOveRate) {
        this.allOveRate = allOveRate;
    }

    public Integer getNewOveRate() {
        return newOveRate;
    }

    public void setNewOveRate(Integer newOveRate) {
        this.newOveRate = newOveRate;
    }

    public Integer getOldOveRate() {
        return oldOveRate;
    }

    public void setOldOveRate(Integer oldOveRate) {
        this.oldOveRate = oldOveRate;
    }

    public String getChannelSuperName() {
        return channelSuperName;
    }

    public void setChannelSuperName(String channelSuperName) {
        this.channelSuperName = channelSuperName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}

package com.info.analyze.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataStatistics {

    public final static Integer MONTHFLAG = 2;
    public final static Integer WEEKFLAG = 1;
    public final static Integer DAYFLAG = 0;
    public final static Integer MONTHADNWEEK = 3; //周跟月统计在一天

    public static Map<Integer,String> customerTypeMap = new HashMap<>();
    public static Map<Integer,String> dataCycleMap = new HashMap<>();
    static {
        customerTypeMap.put(0,"新用户");
        customerTypeMap.put(1,"老用户");

        dataCycleMap.put(DAYFLAG,"日");
        dataCycleMap.put(WEEKFLAG,"周");
        dataCycleMap.put(MONTHFLAG,"月");
    }

    private Integer id;
    private String statisticsTime; //统计时间
    private Integer applyUserNewCount; //新用户申请人数
    private Integer applyUserOldCount; // 老用户申请人数
    private Integer applyUserCount; // 申请新老用户汇总

    private Integer loanUserNewCount; //新用户放款人数
    private Integer loanUserOldCount; //老用户放款人数
    private Integer loanUserCount; //放款新老人数汇总

    private Integer loanMoneyOldCount; //老用户放款钱数
    private Integer loanMoneyNewCount; //新用户放款钱数
    private Integer loanMoneyCount; //新老用户放款钱数汇总

    public Integer getApplyUserCount() {
        return applyUserCount;
    }

    public void setApplyUserCount(Integer applyUserCount) {
        this.applyUserCount = applyUserCount;
    }

    public Integer getLoanUserCount() {
        return loanUserCount;
    }

    public void setLoanUserCount(Integer loanUserCount) {
        this.loanUserCount = loanUserCount;
    }

    public Integer getLoanMoneyCount() {
        return loanMoneyCount;
    }

    public void setLoanMoneyCount(Integer loanMoneyCount) {
        this.loanMoneyCount = loanMoneyCount;
    }

    private Integer registCount; // 注册人数
    private Integer loanBlance; //贷款余额 = 放款总数 - 已还款总数
    private Integer flag; //按周月日统计标志(默认0） 0 表示日  1 表示周 2 表示月
    private Date updateTime;
    private String oldCustomerCheckRate; //老用户核准率
    private String newCustomerCheckRate; //新用户核准率
    private String customerCheckRate; //最终核准率

    public String getOldCustomerCheckRate() {
        return oldCustomerCheckRate;
    }

    public void setOldCustomerCheckRate(String oldCustomerCheckRate) {
        this.oldCustomerCheckRate = oldCustomerCheckRate;
    }

    public String getNewCustomerCheckRate() {
        return newCustomerCheckRate;
    }

    public void setNewCustomerCheckRate(String newCustomerCheckRate) {
        this.newCustomerCheckRate = newCustomerCheckRate;
    }

    public String getCustomerCheckRate() {
        return customerCheckRate;
    }

    public void setCustomerCheckRate(String customerCheckRate) {
        this.customerCheckRate = customerCheckRate;
    }

    public Integer getLoanMoneyOldCount() {
        return loanMoneyOldCount;
    }

    public void setLoanMoneyOldCount(Integer loanMoneyOldCount) {
        this.loanMoneyOldCount = loanMoneyOldCount;
    }

    public Integer getLoanMoneyNewCount() {
        return loanMoneyNewCount;
    }

    public void setLoanMoneyNewCount(Integer loanMoneyNewCount) {
        this.loanMoneyNewCount = loanMoneyNewCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public Integer getApplyUserNewCount() {
        return applyUserNewCount;
    }

    public void setApplyUserNewCount(Integer applyUserNewCount) {
        this.applyUserNewCount = applyUserNewCount;
    }

    public Integer getApplyUserOldCount() {
        return applyUserOldCount;
    }

    public void setApplyUserOldCount(Integer applyUserOldCount) {
        this.applyUserOldCount = applyUserOldCount;
    }

    public Integer getLoanUserNewCount() {
        return loanUserNewCount;
    }

    public void setLoanUserNewCount(Integer loanUserNewCount) {
        this.loanUserNewCount = loanUserNewCount;
    }

    public Integer getLoanUserOldCount() {
        return loanUserOldCount;
    }

    public void setLoanUserOldCount(Integer loanUserOldCount) {
        this.loanUserOldCount = loanUserOldCount;
    }

    public Integer getRegistCount() {
        return registCount;
    }

    public void setRegistCount(Integer registCount) {
        this.registCount = registCount;
    }

    public Integer getLoanBlance() {
        return loanBlance;
    }

    public void setLoanBlance(Integer loanBlance) {
        this.loanBlance = loanBlance;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}

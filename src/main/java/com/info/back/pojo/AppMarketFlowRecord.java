package com.info.back.pojo;

import java.util.Date;

/**
 * 应用市场流量记录表
 *
 * @author tgy
 * @version [版本号, 2018年5月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppMarketFlowRecord {
    private Long id;

    private String appMarket;

    private Date installTime;

    private Integer registerCunt;

    private Integer allRegisterCunt;

    private Double registerTransRate;

    private Integer realnameAuthCunt;

    private Integer bindCardCunt;

    private Integer relyLoanCunt;

    private Integer yesLoanCunt;

    private Integer failLoanCunt;

    private Double crossRate;

    private Integer delayCunt;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppMarket() {
        return appMarket;
    }

    public void setAppMarket(String appMarket) {
        this.appMarket = appMarket == null ? null : appMarket.trim();
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public Integer getRegisterCunt() {
        return registerCunt;
    }

    public void setRegisterCunt(Integer registerCunt) {
        this.registerCunt = registerCunt;
    }

    public Integer getAllRegisterCunt() {
        return allRegisterCunt;
    }

    public void setAllRegisterCunt(Integer allRegisterCunt) {
        this.allRegisterCunt = allRegisterCunt;
    }

    public Double getRegisterTransRate() {
        return registerTransRate;
    }

    public void setRegisterTransRate(Double registerTransRate) {
        this.registerTransRate = registerTransRate;
    }

    public Integer getRealnameAuthCunt() {
        return realnameAuthCunt;
    }

    public void setRealnameAuthCunt(Integer realnameAuthCunt) {
        this.realnameAuthCunt = realnameAuthCunt;
    }

    public Integer getBindCardCunt() {
        return bindCardCunt;
    }

    public void setBindCardCunt(Integer bindCardCunt) {
        this.bindCardCunt = bindCardCunt;
    }

    public Integer getRelyLoanCunt() {
        return relyLoanCunt;
    }

    public void setRelyLoanCunt(Integer relyLoanCunt) {
        this.relyLoanCunt = relyLoanCunt;
    }

    public Integer getYesLoanCunt() {
        return yesLoanCunt;
    }

    public void setYesLoanCunt(Integer yesLoanCunt) {
        this.yesLoanCunt = yesLoanCunt;
    }

    public Integer getFailLoanCunt() {
        return failLoanCunt;
    }

    public void setFailLoanCunt(Integer failLoanCunt) {
        this.failLoanCunt = failLoanCunt;
    }

    public Double getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(Double crossRate) {
        this.crossRate = crossRate;
    }

    public Integer getDelayCunt() {
        return delayCunt;
    }

    public void setDelayCunt(Integer delayCunt) {
        this.delayCunt = delayCunt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
package com.info.back.pojo.ezuiresult;


import com.info.web.pojo.ChannelReport;
import com.info.web.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelReportResult {
    private Integer id;
    private Date reportDate;
    private Integer attestationRealnameCount = 0;
    private Integer attestationBankCount = 0;
    private Integer contactCount = 0;
    private Integer jxlCount = 0;
    private Integer alipayCount = 0;
    private Integer zhimaCount = 0;
    private Integer companyCount = 0;
    private Integer borrowApplyCount = 0;
    private Integer borrowSucCount = 0;
    private Integer blackUserCount = 0;
    private Integer androidCount = 0;
    private Integer iosCount = 0;
    private Integer registerCount = 0;
    private BigDecimal borrowRate;
    private BigDecimal passRate;
    private Integer channelid;
    private String channelName;
    private String channelProvince;
    private String channelCity;
    private String channelArea;
    private String channelSuperName;
    private String registerCountResult;
    private Integer borrowApplyOutCount;
    private Integer borrowSucOutCount;
    private BigDecimal borrowRateOut;
    private BigDecimal passRateOut;

    public ChannelReportResult(ChannelReport report) {
        this.reportDate = report.getReportDate();
//        Date endDiscountDate = DateUtil.getDate("2018-04-08", "yyyy-MM-dd");
//        Date seeCurrentDayDate = DateUtil.getDate("2018-04-18", "yyyy-MM-dd");
        Date seeTotalDay = DateUtil.getDate("2018-04-20", "yyyy-MM-dd");
//        boolean isSeeCurrent = reportDate.after(seeCurrentDayDate);
//        boolean isNeedDiscount = !reportDate.after(endDiscountDate);
        boolean isSeeTotalDay = reportDate.after(seeTotalDay);
//        boolean isUlhDiscount = Constant.YLHNAME.equals(appName) && reportDate.after(ulhChangeTime) && reportDate.before(ulhChangeTime2);
        this.id = report.getId();
        this.attestationRealnameCount = report.getDayRealnameCount();
        this.attestationBankCount = report.getDayBankCount();
        this.contactCount = report.getDayContactCount();
        this.jxlCount = report.getDayTdCount();
        this.alipayCount = report.getDayAlipayCount();
        this.zhimaCount = report.getDayZhimaCount();
        this.companyCount = report.getDayCompanyCount();
        this.borrowApplyCount = report.getDayBorrowApplyCount();
        this.borrowSucCount = report.getDayBorrowSucCount();
        this.blackUserCount = report.getDayBlackUserCount();
//        if (isSeeCurrent) {
//
//        } else {
//            this.attestationRealnameCount = isNeedDiscount ? getDiscountValue(report.getAttestationRealnameCount()) : report.getAttestationRealnameCount();
//            this.attestationBankCount = isNeedDiscount ? getDiscountValue(report.getAttestationBankCount()) : report.getAttestationBankCount();
//            this.contactCount = isNeedDiscount ? getDiscountValue(report.getContactCount()) : report.getContactCount();
//            this.jxlCount = isNeedDiscount ? getDiscountValue(report.getJxlCount()) : report.getJxlCount();
//            this.alipayCount = isNeedDiscount ? getDiscountValue(report.getAlipayCount()) : report.getAlipayCount();
//            this.zhimaCount = isNeedDiscount ? getDiscountValue(report.getZhimaCount()) : report.getZhimaCount();
//            this.companyCount = isNeedDiscount ? getDiscountValue(report.getCompanyCount()) : report.getCompanyCount();
//            this.borrowApplyCount = isNeedDiscount ? getDiscountValue(report.getBorrowApplyCount()) : report.getBorrowApplyCount();
//            this.borrowSucCount = isNeedDiscount ? getDiscountValue(report.getBorrowSucCount()) : report.getBorrowSucCount();
//            this.blackUserCount = isNeedDiscount ? getDiscountValue(report.getBlackUserCount()) : report.getBlackUserCount();
//        }
        this.androidCount =  report.getAndroidCount();
        this.iosCount = report.getIosCount();
        this.channelid = report.getChannelid();
        this.channelName = report.getChannelName();
        this.channelProvince = report.getChannelProvince();
        this.channelCity = report.getChannelCity();
        this.channelArea = report.getChannelArea();
        this.channelSuperName = report.getChannelSuperName();
        this.registerCount = report.getRegisterCount();
        this.borrowApplyOutCount =  report.getBorrowApplyOutCount();
        this.borrowSucOutCount = report.getBorrowSucOutCount();
        this.passRateOut = report.getPassRateOut();
        this.borrowRateOut = report.getBorrowRateOut();
    }

    private Integer getDiscountValue(Integer param) {
        return param > 10 ? (int) Math.floor(param * 0.9) : param;
    }

    public BigDecimal getUlhBorrowRateOut() {
        BigDecimal borrowRate = BigDecimal.ZERO;
        if (this.borrowApplyOutCount > 0 && this.registerCount > 0) {
            BigDecimal a = new BigDecimal(this.borrowApplyOutCount);
            BigDecimal d = new BigDecimal(this.registerCount);
            borrowRate = a.multiply(new BigDecimal(100)).divide(d, 2, BigDecimal.ROUND_HALF_UP);
        }

        return borrowRate;
    }


    public BigDecimal getUlhPaseRateOut() {
        BigDecimal borrowRate = BigDecimal.ZERO;
        if (this.borrowApplyOutCount > 0 && this.borrowSucOutCount > 0) {
            BigDecimal a = new BigDecimal(this.borrowSucOutCount);
            BigDecimal d = new BigDecimal(this.borrowApplyOutCount);
            borrowRate = a.multiply(new BigDecimal(100)).divide(d, 2, BigDecimal.ROUND_HALF_UP);
        }

        return borrowRate;
    }


    public BigDecimal getPassRate() {
        BigDecimal passRate = BigDecimal.ZERO;
        if (this.borrowApplyCount > 0 && this.borrowSucCount > 0) {
            BigDecimal a = new BigDecimal(this.borrowApplyCount);
            BigDecimal b = new BigDecimal(this.borrowSucCount);
            passRate = b.multiply(new BigDecimal(100)).divide(a, 2, BigDecimal.ROUND_HALF_UP);
        }

        return passRate;
    }

    public String getRegisterCountResult() {
        return registerCount + "【android:" + androidCount + ", ios:" + iosCount + "】";
    }

    public void setRegisterCountResult(String registerCountResult) {
        this.registerCountResult = registerCountResult;
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

    public Integer getBlackUserCount() {
        return blackUserCount;
    }

    public void setBlackUserCount(Integer blackUserCount) {
        this.blackUserCount = blackUserCount;
    }

    public void setBorrowRate(BigDecimal borrowRate) {
        this.borrowRate = borrowRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }


    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelProvince() {
        return channelProvince;
    }

    public void setChannelProvince(String channelProvince) {
        this.channelProvince = channelProvince;
    }

    public String getChannelCity() {
        return channelCity;
    }

    public void setChannelCity(String channelCity) {
        this.channelCity = channelCity;
    }

    public String getChannelArea() {
        return channelArea;
    }

    public void setChannelArea(String channelArea) {
        this.channelArea = channelArea;
    }

    public String getChannelSuperName() {
        return channelSuperName;
    }

    public void setChannelSuperName(String channelSuperName) {
        this.channelSuperName = channelSuperName;
    }

    public Integer getBorrowApplyOutCount() {
        return borrowApplyOutCount;
    }

    public void setBorrowApplyOutCount(Integer borrowApplyOutCount) {
        this.borrowApplyOutCount = borrowApplyOutCount;
    }

    public Integer getBorrowSucOutCount() {
        return borrowSucOutCount;
    }

    public void setBorrowSucOutCount(Integer borrowSucOutCount) {
        this.borrowSucOutCount = borrowSucOutCount;
    }

    public BigDecimal getBorrowRateOut() {
        return this.borrowRateOut;
    }

    public void setBorrowRateOut(BigDecimal borrowRateOut) {
        this.borrowRateOut = borrowRateOut;
    }

    public BigDecimal getPassRateOut() {
        return this.passRateOut;
    }

    public void setPassRateOut(BigDecimal passRateOut) {
        this.passRateOut = passRateOut;
    }

}

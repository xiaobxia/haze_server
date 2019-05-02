package com.info.web.pojo;

import java.util.Date;

/**
 * Created by Phi on 2018/1/2.
 */
public class BorrowOrderLocationStatisticsDo {
    private Integer id;
    private String userId;
    private Date createdAt;
    private String verifyReviewUser;
    private String userPhone;
    private String realname;
    private Integer status;
    private String paystatus;

    private String idNumber;

    private String idCardProvince;
    private String idCardCity;
    private String birthYear;

    private Date repaymentTime;
    private Date repaymentRealTime;

    private String userSex;
    private Integer userAge;
    private Integer education;
    private Integer maritalStatus;
    private String presentAddress;
    private String presentAddressDistinct;
    private String presentLatitude;
    private String presentLongitude;
    private Integer presentPeriod;


    public boolean getPassFlag() {
        if (this.getStatus() == null) {
            return false;
        }
        if (this.getStatus() == 20 || this.getStatus() == -5
                || this.getStatus() == 22 || this.getStatus() == -10
                || this.getStatus() == 21 || this.getStatus() == 23
                || this.getStatus() == 30 || this.getStatus() == -11
                || this.getStatus() == -20 || this.getStatus() == 34) {
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getVerifyReviewUser() {
        return verifyReviewUser;
    }

    public void setVerifyReviewUser(String verifyReviewUser) {
        this.verifyReviewUser = verifyReviewUser;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public boolean getBadCreditFlag() {
        if (repaymentTime == null) {
            return false;
        }
        if (repaymentRealTime == null) {
            return true;
        }
        if (daysOfTwo(repaymentTime, repaymentRealTime) > 3) {
            return true;
        }
        return false;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getIdCardProvince() {
        return idCardProvince;
    }

    public void setIdCardProvince(String idCardProvince) {
        this.idCardProvince = idCardProvince;
    }

    public String getIdCardCity() {
        return idCardCity;
    }

    public void setIdCardCity(String idCardCity) {
        this.idCardCity = idCardCity;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getRepaymentRealTime() {
        return repaymentRealTime;
    }

    public void setRepaymentRealTime(Date repaymentRealTime) {
        this.repaymentRealTime = repaymentRealTime;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPresentAddressDistinct() {
        return presentAddressDistinct;
    }

    public void setPresentAddressDistinct(String presentAddressDistinct) {
        this.presentAddressDistinct = presentAddressDistinct;
    }

    public String getPresentLatitude() {
        return presentLatitude;
    }

    public void setPresentLatitude(String presentLatitude) {
        this.presentLatitude = presentLatitude;
    }

    public String getPresentLongitude() {
        return presentLongitude;
    }

    public void setPresentLongitude(String presentLongitude) {
        this.presentLongitude = presentLongitude;
    }

    public Integer getPresentPeriod() {
        return presentPeriod;
    }

    public void setPresentPeriod(Integer presentPeriod) {
        this.presentPeriod = presentPeriod;
    }

    public static int daysOfTwo(Date fDate, Date oDate) {

        if (null == fDate || null == oDate) {

            return -1;

        }

        long intervalMilli = oDate.getTime() - fDate.getTime();

        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

//    public static void main(String[] args) {
//        Date oDate = new GregorianCalendar(2009, 2, 15,13,13,0).getTime();
//        int i = daysOfTwo(oDate,new Date());
//        System.out.println();
//    }
}

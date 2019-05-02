package com.info.back.pojo;

import com.info.web.pojo.Remark;
import com.info.web.pojo.User;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户信息表-用于在线客服模块〉
 *
 * @author Liubing
 * @create 2018/4/9
 * @since 1.0.0
 */
public class UserDetail extends User {
    /**
     * 第三方流水号
     */
    private String outTradeNo;
    /**
     * 订单号
     */
    private String yurref;

    private String idNumber;

    private String cardNo;

    private Integer lateDay;

    private String repaymentTime;

    private String browerType;

    private String bankName;

    private List<Remark> remarks;

    private String assignId;

    private String customerType;

    private String emergencyInfo;

    private Integer repaymentStatus;

    private String currentJobName;

    private String assetOrderId;

    private String loanTime;

    private Integer moneyAmount;

    private List<Map<String,Object>> banks;
    /**
     * 还款记录
     */
    private List<Map<String,Object>> repaymentHistory;
    /**
     * 续期记录
     */
    private List<Map<String,Object>> renewalHistory;

    @Override
    public String getIdNumber() {
        idNumber = showNumber(idNumber);
        return idNumber;
    }

    @Override
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCardNo() {
        cardNo = showNumber(cardNo);
        return cardNo;
    }

    public static String showNumber(String number){
        if(StringUtils.isEmpty(number) || number.length() < 7) return number;
        int startIndex = 3;
        int endIndex = number.length() - 4;
        char changeFlag = '*';
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<number.length();i++){
            char numByte = number.charAt(i);
            if(i >= startIndex && i < endIndex){
                sb.append(changeFlag);
            } else{
                sb.append(numByte);
            }
        }
        return sb.toString();
    }

    @Override
    public String getCustomerType() {
        return customerType;
    }

    @Override
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getYurref() {
        return yurref;
    }

    public void setYurref(String yurref) {
        this.yurref = yurref;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getBrowerType() {
        return browerType;
    }

    public void setBrowerType(String browerType) {
        this.browerType = browerType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    public String getEmergencyInfo() {
        return emergencyInfo;
    }

    public void setEmergencyInfo(String emergencyInfo) {
        this.emergencyInfo = emergencyInfo;
    }

    public Integer getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(Integer repaymentStatus) {
        this.repaymentStatus = repaymentStatus;
    }

    public String getCurrentJobName() {
        return currentJobName;
    }

    public void setCurrentJobName(String currentJobName) {
        this.currentJobName = currentJobName;
    }

    public String getAssetOrderId() {
        return assetOrderId;
    }

    public void setAssetOrderId(String assetOrderId) {
        this.assetOrderId = assetOrderId;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public List<Map<String, Object>> getBanks() {
        return banks;
    }

    public void setBanks(List<Map<String, Object>> banks) {
        this.banks = banks;
    }

    public List<Map<String, Object>> getRepaymentHistory() {
        return repaymentHistory;
    }

    public void setRepaymentHistory(List<Map<String, Object>> repaymentHistory) {
        this.repaymentHistory = repaymentHistory;
    }

    public List<Map<String, Object>> getRenewalHistory() {
        return renewalHistory;
    }

    public void setRenewalHistory(List<Map<String, Object>> renewalHistory) {
        this.renewalHistory = renewalHistory;
    }
}

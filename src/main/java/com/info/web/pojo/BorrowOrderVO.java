package com.info.web.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowOrderVO {
    private String id;// 借款id
    private String outTradeNo;// 订单号
    private Integer moneyAmount; //借款金额
    private Integer apr;//服务费利率(万分之一)
    private Integer loanInterests; //服务费
    private Date orderTime;// 申请时间
    private Date loanTime; //放款时间
    private String orderTypeName;//  子类型
    private Integer status;// 状态
    private String statusName;// 状态名
    private String userPhone;//手机号
    private String reviewBackUserName;//复审人员
    private String projectName;
// 状态：:、:、:；
    /**
     * 初审通过
     */
    public static final Integer STATUS_CSTG = 1;
    /**
     * 待初审(待机审)
     */
    public static final Integer STATUS_DCS = 0;
//	/**
//	 * 机审拒绝
//	 */
//	public static final Integer STATUS_JSJJ = 10001;
    /** 初审驳回
     */
    public static final Integer STATUS_CSBH = -3;
    /**复审驳回
     */
    public static final Integer STATUS_FSBH = -4;
    /**复审驳回并拉黑
     */
    public static final Integer STATUS_FSBHLH = -50;
    /***复审通过,待放款
     */
    public static final Integer STATUS_FSTG = 20;
    /** 放款驳回
     */
    public static final Integer STATUS_FKBH = -5;
    /** 放款中
     */
    public static final Integer STATUS_FKZ = 22;
    /**
     * 放款失败
     */
    public static final Integer STATUS_FKSB = -10;
    /**
     * 已放款
     */
    public static final Integer STATUS_HKZ = 21;
    /**
     * 部分还款
     */
    public static final Integer STATUS_BFHK = 23;
    /**
     * 已还款
     */
    public static final Integer STATUS_YHK = 30;
    /**
     * 逾期已还款
     */
    public static final Integer STATUS_YQYHK = 34;


    /**
     * 已逾期
     */
    public static final Integer STATUS_YYQ = -11;
    /**
     * 已坏账
     */
    public static final Integer STATUS_YHZ = -20;
    /**
     * 扣款中
     */
    public static final Integer STATUS_KKZ = 12;
    /**
     * 扣款失败
     */
    public static final Integer STATUS_KKSB = -7;
    public static final Map<Integer, String> borrowStatusMap = new HashMap<Integer, String>();

    /**
     * 1 显示评分卡
     * 2 显示：人工否决：信审人员姓名或者人工否决：空白
     * 3 其他都是默认显示信审人员姓名
     */
    private Integer reviwRiskLabel;

    public Integer getReviwRiskLabel() {
        return reviwRiskLabel;
    }

    public void setReviwRiskLabel(Integer reviwRiskLabel) {
        this.reviwRiskLabel = reviwRiskLabel;
    }

    static {
        borrowStatusMap.put(STATUS_DCS, "待机审");
//		borrowStatusMap.put(STATUS_JSJJ, "机审拒绝");
        borrowStatusMap.put(STATUS_CSBH, "初审驳回");

        borrowStatusMap.put(STATUS_CSTG, "初审通过/待复审");
        borrowStatusMap.put(STATUS_FSBH, "复审驳回");
        borrowStatusMap.put(STATUS_FSTG, "复审通过/待放款审核");
        borrowStatusMap.put(STATUS_FKBH, "放款驳回");
        borrowStatusMap.put(STATUS_FKZ, "放款中");
        borrowStatusMap.put(STATUS_FKSB, "放款失败");
        borrowStatusMap.put(STATUS_HKZ, "已放款/待还款");
        borrowStatusMap.put(STATUS_BFHK, "部分还款");
        borrowStatusMap.put(STATUS_YHK, "正常已还款");
        borrowStatusMap.put(STATUS_YQYHK, "逾期已还款");
        borrowStatusMap.put(STATUS_YYQ, "已逾期");
        borrowStatusMap.put(STATUS_YHZ, "已坏账");
        // 二级状态
        borrowStatusMap.put(STATUS_KKZ, "扣款中");// +
        borrowStatusMap.put(STATUS_KKSB, "扣款失败");// +

        //添加新用户和老用户
        borrowStatusMap.put(88,"老用户");
        borrowStatusMap.put(66,"新用户");
    }

    public String getReviewBackUserName() {
        return reviewBackUserName;
    }

    public void setReviewBackUserName(String reviewBackUserName) {
        this.reviewBackUserName = reviewBackUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Integer getApr() {
        return apr;
    }

    public void setApr(Integer apr) {
        this.apr = apr;
    }

    public Integer getLoanInterests() {
        return loanInterests;
    }

    public void setLoanInterests(Integer loanInterests) {
        this.loanInterests = loanInterests;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.statusName = borrowStatusMap.get(status);
    }

    public String getStatusName() {
        return statusName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "BorrowOrderVO{" +
                "id='" + id + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", apr=" + apr +
                ", loanInterests=" + loanInterests +
                ", orderTime=" + orderTime +
                ", loanTime=" + loanTime +
                ", orderTypeName='" + orderTypeName + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

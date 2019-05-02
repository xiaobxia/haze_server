package com.info.web.pojo.statistics;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by tl on 2018/4/2.
 */

public class Overdue {
    private Long id;
    //观察区间
    private Date startDate;
    private Date endDate;
    //观察点
    private Date pointDate;
    //逾期率按单
    private Double overdueRate;
    //逾期率按人
    private Double overUserRate;
    //坏账率逾期天数<=3
    private Double dirtyUserRateS1;
    //坏账率3<逾期天数<=15
    private Double dirtyUserRateS2;
    //坏账率15<逾期天数<=30
    private Double dirtyUserRateS3;
    //坏账率30<逾期天数
    private Double dirtyUserRateM1;
    //坏账率15<逾期天数
    private Double dirtyRate15;
    //借款审核通过率
    private Double passRate;
    //逾期分布json
    private String overdueDistribution;
    //渠道名称
    private String channelName;
    //审核方式 人工或模型
    private String reviewWay;
    //审核方式标记 人工或模型
    private Integer reviewFlag;
    //新老全部用户
    private Integer type;
    //按渠道,模型或其他
    private Integer source;
    //借款订单数
    private Integer borrowNum;
    //总逾期订单数
    private Integer overdueNum;
    //逾期天数大于三天订单数
    private Integer overdueNum3;
    //逾期率3<逾期天数
    private Double overdueRate3;
    //逾期分布
    private TreeMap<Integer,Integer> result = new TreeMap<>();

    public static final int ALL_CUSTOMER = 0; // 全部
    public static final int OLD_CUSTOMER = 1; // 老用户
    public static final int NEW_CUSTOMER = 2; // 新用户
    public static final int ORIGINAL = 0; // 原始
    public static final int CHANNEL = 2; // 渠道
    public static final int MODEL = 1; // 模型
    public static final int PERSON = 0; // 人工

    //审核员姓名加手机号
    private String reviewUser;

    //过件量
    private Integer passNum;

    //已还量
    private Integer paidNum;
    //待还量
    private Integer toPayNum;
    //首逾量
    private Integer firstOverdueNum;
    //首逾率
    private Double firstOverdueRate;
    //催回量
    private Integer collectNum;
    //催回率
    private Double collectRate;
    //总续期次数
    private Integer renewalNum;
    //逾期已还款数量
    private Integer overdueButRepay;
    //已逾期数量
    private Integer overdueYet;

    //部分还款
    private Integer partRepay;
    //已坏账
    private Integer badOrd;

    private List<Integer> list;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    public Integer getPaidNum() {
        return paidNum;
    }

    public void setPaidNum(Integer paidNum) {
        this.paidNum = paidNum;
    }

    public Integer getToPayNum() {
        return toPayNum;
    }

    public void setToPayNum(Integer toPayNum) {
        this.toPayNum = toPayNum;
    }

    public Integer getFirstOverdueNum() {
        return firstOverdueNum;
    }

    public void setFirstOverdueNum(Integer firstOverdueNum) {
        this.firstOverdueNum = firstOverdueNum;
    }

    public Double getFirstOverdueRate() {
        return firstOverdueRate;
    }

    public void setFirstOverdueRate(Double firstOverdueRate) {
        this.firstOverdueRate = firstOverdueRate;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Double getCollectRate() {
        return collectRate;
    }

    public void setCollectRate(Double collectRate) {
        this.collectRate = collectRate;
    }

    public Integer getRenewalNum() {
        return renewalNum;
    }

    public void setRenewalNum(Integer renewalNum) {
        this.renewalNum = renewalNum;
    }

    public Double getOverdueRate3() {
        return overdueRate3;
    }

    public void setOverdueRate3(Double overdueRate3) {
        this.overdueRate3 = overdueRate3;
    }

    public Integer getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(Integer overdueNum) {
        this.overdueNum = overdueNum;
    }

    public Integer getOverdueNum3() {
        return overdueNum3;
    }

    public void setOverdueNum3(Integer overdueNum3) {
        this.overdueNum3 = overdueNum3;
    }

    public Integer getReviewFlag() {
        return reviewFlag;
    }

    public void setReviewFlag(Integer reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Double getDirtyRate15() {
        return dirtyRate15;
    }

    public void setDirtyRate15(Double dirtyRate15) {
        this.dirtyRate15 = dirtyRate15;
    }

    public Integer getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(Integer borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getReviewWay() {
        return reviewWay;
    }

    public void setReviewWay(String reviewWay) {
        this.reviewWay = reviewWay;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOverdueDistribution() {
        return overdueDistribution;
    }

    public void setOverdueDistribution(String overdueDistribution) {
        this.overdueDistribution = overdueDistribution;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDirtyUserRateS1() {
        return dirtyUserRateS1;
    }

    public void setDirtyUserRateS1(Double dirtyUserRateS1) {
        this.dirtyUserRateS1 = dirtyUserRateS1;
    }

    public Double getDirtyUserRateS2() {
        return dirtyUserRateS2;
    }

    public void setDirtyUserRateS2(Double dirtyUserRateS2) {
        this.dirtyUserRateS2 = dirtyUserRateS2;
    }

    public Double getDirtyUserRateS3() {
        return dirtyUserRateS3;
    }

    public void setDirtyUserRateS3(Double dirtyUserRateS3) {
        this.dirtyUserRateS3 = dirtyUserRateS3;
    }

    public Double getDirtyUserRateM1() {
        return dirtyUserRateM1;
    }

    public void setDirtyUserRateM1(Double dirtyUserRateM1) {
        this.dirtyUserRateM1 = dirtyUserRateM1;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPointDate() {
        return pointDate;
    }

    public void setPointDate(Date pointDate) {
        this.pointDate = pointDate;
    }

    public Double getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(Double overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Double getOverUserRate() {
        return overUserRate;
    }

    public void setOverUserRate(Double overUserRate) {
        this.overUserRate = overUserRate;
    }

    public TreeMap<Integer, Integer> getResult() {
        return result;
    }

    public void setResult(TreeMap<Integer, Integer> result) {
        this.result = result;
    }

    public Integer getOverdueButRepay() {
        return overdueButRepay;
    }

    public void setOverdueButRepay(Integer overdueButRepay) {
        this.overdueButRepay = overdueButRepay;
    }

    public Integer getOverdueYet() {
        return overdueYet;
    }

    public void setOverdueYet(Integer overdueYet) {
        this.overdueYet = overdueYet;
    }

    public Integer getPartRepay() {
        return partRepay;
    }

    public void setPartRepay(Integer partRepay) {
        this.partRepay = partRepay;
    }

    public Integer getBadOrd() {
        return badOrd;
    }

    public void setBadOrd(Integer badOrd) {
        this.badOrd = badOrd;
    }
}

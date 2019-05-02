package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;

public class PlatformReport {
	private Integer id;

	private String reportDate;

	private Integer registerNum;

	private Integer realPersonNum;

	private Integer realNum;

	private BigDecimal realMoney;

	private Integer yysNum;

	private Integer yysSucNum;

	private BigDecimal yysMoney;

	private Integer bankNum;

	private Integer zmNum;

	private BigDecimal zmMoney;

	private Integer workNum;

	private Integer alipayNum;

	private Integer borrowNum;

	private Integer borrowSucNum;

	private BigDecimal borrowRate;

	private BigDecimal shouldPayMoney;

	private BigDecimal sucPayMoney;

	private Integer sucPayNum;

	private BigDecimal noPayMoney;

	private Integer noPayNum;

	private BigDecimal failPayMoney;

	private Integer failPayNum;

	private BigDecimal waitPayMoney;

	private Integer waitPayNum;

	private Integer borrowUserNum;

	private Integer borrowUserSucNum;

	private BigDecimal borrowUserRate;

	private Integer stopNum;

	private Integer fqz;

	private Integer zr;

	private Integer riskNum;

	private Integer riskSucNum;

	private Integer riskFailNum;

	private Integer riskToReview;

	private Integer riskToReviewZxErr;

	private Integer riskToReviewErr;

	private Integer riksToReviewReal;
	// 芝麻信用相关1.芝麻分；2芝麻行业关注度
	private int zmScoreSuc;
	private int zmScoreFail;
	private int zmIndustySuc;
	private int zmIndustyFail;
	// 同盾报告
	private int tdSuc;
	private int tdFail;
	// 白骑士
	private int bqsSuc;
	private int bqsFail;
	// 中智诚
	private int zzcSuc;
	private int zzcFail;
	// 91征信
	private int jyzxSuc;
	private int jyzxFail;
	// 蜜罐
	private int jxlMgSuc;
	private int jxlMgFail;
	// 聚信立
	private int jxlReportSuc;
	private int jxlReportFail;
	// 宜信
	private int yxSuc;
	private int yxFail;
	private Date addTime;

	public int getZmScoreSuc() {
		return zmScoreSuc;
	}

	public void setZmScoreSuc(int zmScoreSuc) {
		this.zmScoreSuc = zmScoreSuc;
	}

	public int getZmScoreFail() {
		return zmScoreFail;
	}

	public void setZmScoreFail(int zmScoreFail) {
		this.zmScoreFail = zmScoreFail;
	}

	public int getZmIndustySuc() {
		return zmIndustySuc;
	}

	public void setZmIndustySuc(int zmIndustySuc) {
		this.zmIndustySuc = zmIndustySuc;
	}

	public int getZmIndustyFail() {
		return zmIndustyFail;
	}

	public void setZmIndustyFail(int zmIndustyFail) {
		this.zmIndustyFail = zmIndustyFail;
	}

	public int getTdSuc() {
		return tdSuc;
	}

	public void setTdSuc(int tdSuc) {
		this.tdSuc = tdSuc;
	}

	public int getTdFail() {
		return tdFail;
	}

	public void setTdFail(int tdFail) {
		this.tdFail = tdFail;
	}

	public int getBqsSuc() {
		return bqsSuc;
	}

	public void setBqsSuc(int bqsSuc) {
		this.bqsSuc = bqsSuc;
	}

	public int getBqsFail() {
		return bqsFail;
	}

	public void setBqsFail(int bqsFail) {
		this.bqsFail = bqsFail;
	}

	public int getZzcSuc() {
		return zzcSuc;
	}

	public void setZzcSuc(int zzcSuc) {
		this.zzcSuc = zzcSuc;
	}

	public int getZzcFail() {
		return zzcFail;
	}

	public void setZzcFail(int zzcFail) {
		this.zzcFail = zzcFail;
	}

	public int getJyzxSuc() {
		return jyzxSuc;
	}

	public void setJyzxSuc(int jyzxSuc) {
		this.jyzxSuc = jyzxSuc;
	}

	public int getJyzxFail() {
		return jyzxFail;
	}

	public void setJyzxFail(int jyzxFail) {
		this.jyzxFail = jyzxFail;
	}

	public int getJxlMgSuc() {
		return jxlMgSuc;
	}

	public void setJxlMgSuc(int jxlMgSuc) {
		this.jxlMgSuc = jxlMgSuc;
	}

	public int getJxlMgFail() {
		return jxlMgFail;
	}

	public void setJxlMgFail(int jxlMgFail) {
		this.jxlMgFail = jxlMgFail;
	}

	public int getJxlReportSuc() {
		return jxlReportSuc;
	}

	public void setJxlReportSuc(int jxlReportSuc) {
		this.jxlReportSuc = jxlReportSuc;
	}

	public int getJxlReportFail() {
		return jxlReportFail;
	}

	public void setJxlReportFail(int jxlReportFail) {
		this.jxlReportFail = jxlReportFail;
	}

	public int getYxSuc() {
		return yxSuc;
	}

	public void setYxSuc(int yxSuc) {
		this.yxSuc = yxSuc;
	}

	public int getYxFail() {
		return yxFail;
	}

	public void setYxFail(int yxFail) {
		this.yxFail = yxFail;
	}

	private Date updateTime;

	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate == null ? null : reportDate.trim();
	}

	public Integer getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}

	public Integer getRealPersonNum() {
		return realPersonNum;
	}

	public void setRealPersonNum(Integer realPersonNum) {
		this.realPersonNum = realPersonNum;
	}

	public Integer getRealNum() {
		return realNum;
	}

	public void setRealNum(Integer realNum) {
		this.realNum = realNum;
	}

	public BigDecimal getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(BigDecimal realMoney) {
		this.realMoney = realMoney;
	}

	public Integer getYysNum() {
		return yysNum;
	}

	public void setYysNum(Integer yysNum) {
		this.yysNum = yysNum;
	}

	public Integer getYysSucNum() {
		return yysSucNum;
	}

	public void setYysSucNum(Integer yysSucNum) {
		this.yysSucNum = yysSucNum;
	}

	public BigDecimal getYysMoney() {
		return yysMoney;
	}

	public void setYysMoney(BigDecimal yysMoney) {
		this.yysMoney = yysMoney;
	}

	public Integer getBankNum() {
		return bankNum;
	}

	public void setBankNum(Integer bankNum) {
		this.bankNum = bankNum;
	}

	public Integer getZmNum() {
		return zmNum;
	}

	public void setZmNum(Integer zmNum) {
		this.zmNum = zmNum;
	}

	public BigDecimal getZmMoney() {
		return zmMoney;
	}

	public void setZmMoney(BigDecimal zmMoney) {
		this.zmMoney = zmMoney;
	}

	public Integer getWorkNum() {
		return workNum;
	}

	public void setWorkNum(Integer workNum) {
		this.workNum = workNum;
	}

	public Integer getAlipayNum() {
		return alipayNum;
	}

	public void setAlipayNum(Integer alipayNum) {
		this.alipayNum = alipayNum;
	}

	public Integer getBorrowNum() {
		return borrowNum;
	}

	public void setBorrowNum(Integer borrowNum) {
		this.borrowNum = borrowNum;
	}

	public Integer getBorrowSucNum() {
		return borrowSucNum;
	}

	public void setBorrowSucNum(Integer borrowSucNum) {
		this.borrowSucNum = borrowSucNum;
	}

	public BigDecimal getBorrowRate() {
		return borrowRate;
	}

	public void setBorrowRate(BigDecimal borrowRate) {
		this.borrowRate = borrowRate;
	}

	public BigDecimal getShouldPayMoney() {
		return shouldPayMoney;
	}

	public void setShouldPayMoney(BigDecimal shouldPayMoney) {
		this.shouldPayMoney = shouldPayMoney;
	}

	public BigDecimal getSucPayMoney() {
		return sucPayMoney;
	}

	public void setSucPayMoney(BigDecimal sucPayMoney) {
		this.sucPayMoney = sucPayMoney;
	}

	public Integer getSucPayNum() {
		return sucPayNum;
	}

	public void setSucPayNum(Integer sucPayNum) {
		this.sucPayNum = sucPayNum;
	}

	public BigDecimal getNoPayMoney() {
		return noPayMoney;
	}

	public void setNoPayMoney(BigDecimal noPayMoney) {
		this.noPayMoney = noPayMoney;
	}

	public Integer getNoPayNum() {
		return noPayNum;
	}

	public void setNoPayNum(Integer noPayNum) {
		this.noPayNum = noPayNum;
	}

	public BigDecimal getFailPayMoney() {
		return failPayMoney;
	}

	public void setFailPayMoney(BigDecimal failPayMoney) {
		this.failPayMoney = failPayMoney;
	}

	public Integer getFailPayNum() {
		return failPayNum;
	}

	public void setFailPayNum(Integer failPayNum) {
		this.failPayNum = failPayNum;
	}

	public BigDecimal getWaitPayMoney() {
		return waitPayMoney;
	}

	public void setWaitPayMoney(BigDecimal waitPayMoney) {
		this.waitPayMoney = waitPayMoney;
	}

	public Integer getWaitPayNum() {
		return waitPayNum;
	}

	public void setWaitPayNum(Integer waitPayNum) {
		this.waitPayNum = waitPayNum;
	}

	public Integer getBorrowUserNum() {
		return borrowUserNum;
	}

	public void setBorrowUserNum(Integer borrowUserNum) {
		this.borrowUserNum = borrowUserNum;
	}

	public Integer getBorrowUserSucNum() {
		return borrowUserSucNum;
	}

	public void setBorrowUserSucNum(Integer borrowUserSucNum) {
		this.borrowUserSucNum = borrowUserSucNum;
	}

	public BigDecimal getBorrowUserRate() {
		return borrowUserRate;
	}

	public void setBorrowUserRate(BigDecimal borrowUserRate) {
		this.borrowUserRate = borrowUserRate;
	}

	public Integer getStopNum() {
		return stopNum;
	}

	public void setStopNum(Integer stopNum) {
		this.stopNum = stopNum;
	}

	public Integer getFqz() {
		return fqz;
	}

	public void setFqz(Integer fqz) {
		this.fqz = fqz;
	}

	public Integer getZr() {
		return zr;
	}

	public void setZr(Integer zr) {
		this.zr = zr;
	}

	public Integer getRiskNum() {
		return riskNum;
	}

	public void setRiskNum(Integer riskNum) {
		this.riskNum = riskNum;
	}

	public Integer getRiskSucNum() {
		return riskSucNum;
	}

	public void setRiskSucNum(Integer riskSucNum) {
		this.riskSucNum = riskSucNum;
	}

	public Integer getRiskFailNum() {
		return riskFailNum;
	}

	public void setRiskFailNum(Integer riskFailNum) {
		this.riskFailNum = riskFailNum;
	}

	public Integer getRiskToReview() {
		return riskToReview;
	}

	public void setRiskToReview(Integer riskToReview) {
		this.riskToReview = riskToReview;
	}

	public Integer getRiskToReviewZxErr() {
		return riskToReviewZxErr;
	}

	public void setRiskToReviewZxErr(Integer riskToReviewZxErr) {
		this.riskToReviewZxErr = riskToReviewZxErr;
	}

	public Integer getRiskToReviewErr() {
		return riskToReviewErr;
	}

	public void setRiskToReviewErr(Integer riskToReviewErr) {
		this.riskToReviewErr = riskToReviewErr;
	}

	public Integer getRiksToReviewReal() {
		return riksToReviewReal;
	}

	public void setRiksToReviewReal(Integer riksToReviewReal) {
		this.riksToReviewReal = riksToReviewReal;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PlatformReport() {
		super();
	}

	public PlatformReport(String reportDate, Integer registerNum, Integer realPersonNum, Integer realNum, BigDecimal realMoney, Integer yysNum,
			Integer yysSucNum, BigDecimal yysMoney, Integer bankNum, Integer zmNum, BigDecimal zmMoney, Integer workNum, Integer alipayNum,
			Integer borrowNum, Integer borrowSucNum, BigDecimal borrowRate, BigDecimal shouldPayMoney, BigDecimal sucPayMoney, Integer sucPayNum,
			BigDecimal noPayMoney, Integer noPayNum, BigDecimal failPayMoney, Integer failPayNum, BigDecimal waitPayMoney, Integer waitPayNum,
			Integer borrowUserNum, Integer borrowUserSucNum, BigDecimal borrowUserRate, Integer stopNum, Integer fqz, Integer zr, Integer riskNum,
			Integer riskSucNum, Integer riskFailNum, Integer riskToReview, Integer riskToReviewZxErr, Integer riskToReviewErr,
			Integer riksToReviewReal, int zmScoreSuc, int zmScoreFail, int zmIndustySuc, int zmIndustyFail, int tdSuc, int tdFail, int bqsSuc,
			int bqsFail, int zzcSuc, int zzcFail, int jyzxSuc, int jyzxFail, int jxlMgSuc, int jxlMgFail, int jxlReportSuc, int jxlReportFail,
			int yxSuc, int yxFail) {
		super();
		this.reportDate = reportDate;
		this.registerNum = registerNum;
		this.realPersonNum = realPersonNum;
		this.realNum = realNum;
		this.realMoney = realMoney;
		this.yysNum = yysNum;
		this.yysSucNum = yysSucNum;
		this.yysMoney = yysMoney;
		this.bankNum = bankNum;
		this.zmNum = zmNum;
		this.zmMoney = zmMoney;
		this.workNum = workNum;
		this.alipayNum = alipayNum;
		this.borrowNum = borrowNum;
		this.borrowSucNum = borrowSucNum;
		this.borrowRate = borrowRate;
		this.shouldPayMoney = shouldPayMoney;
		this.sucPayMoney = sucPayMoney;
		this.sucPayNum = sucPayNum;
		this.noPayMoney = noPayMoney;
		this.noPayNum = noPayNum;
		this.failPayMoney = failPayMoney;
		this.failPayNum = failPayNum;
		this.waitPayMoney = waitPayMoney;
		this.waitPayNum = waitPayNum;
		this.borrowUserNum = borrowUserNum;
		this.borrowUserSucNum = borrowUserSucNum;
		this.borrowUserRate = borrowUserRate;
		this.stopNum = stopNum;
		this.fqz = fqz;
		this.zr = zr;
		this.riskNum = riskNum;
		this.riskSucNum = riskSucNum;
		this.riskFailNum = riskFailNum;
		this.riskToReview = riskToReview;
		this.riskToReviewZxErr = riskToReviewZxErr;
		this.riskToReviewErr = riskToReviewErr;
		this.riksToReviewReal = riksToReviewReal;
		this.zmScoreSuc = zmScoreSuc;
		this.zmScoreFail = zmScoreFail;
		this.zmIndustySuc = zmIndustySuc;
		this.zmIndustyFail = zmIndustyFail;
		this.tdSuc = tdSuc;
		this.tdFail = tdFail;
		this.bqsSuc = bqsSuc;
		this.bqsFail = bqsFail;
		this.zzcSuc = zzcSuc;
		this.zzcFail = zzcFail;
		this.jyzxSuc = jyzxSuc;
		this.jyzxFail = jyzxFail;
		this.jxlMgSuc = jxlMgSuc;
		this.jxlMgFail = jxlMgFail;
		this.jxlReportSuc = jxlReportSuc;
		this.jxlReportFail = jxlReportFail;
		this.yxSuc = yxSuc;
		this.yxFail = yxFail;
	}

}
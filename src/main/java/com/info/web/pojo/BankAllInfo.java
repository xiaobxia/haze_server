package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 * 银行卡
 * @author user
 *
 */
public class BankAllInfo implements Serializable{
	private Integer bankId;
	private String bankName;
	private Integer bankStatus;
	private Date bankUpdatetime;
	private String bankCode;
	private Integer bankSequence;
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}
	public Date getBankUpdatetime() {
		return bankUpdatetime;
	}
	public void setBankUpdatetime(Date bankUpdatetime) {
		this.bankUpdatetime = bankUpdatetime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Integer getBankSequence() {
		return bankSequence;
	}
	public void setBankSequence(Integer bankSequence) {
		this.bankSequence = bankSequence;
	}
}

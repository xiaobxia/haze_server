package com.info.web.pojo;

import java.util.Date;

public class BorrowAssetPacket {
    private Integer id;

    private Integer packetId;

    private Integer packetTotalamount;

    private Integer packetSignamount;

    private Integer fillupFlag;

    private Integer pushStatus;

    private Integer borrowCycle;

    private Date packetTime;

    private Date createdAt;

    private Date updatedAt;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public Integer getPacketTotalamount() {
        return packetTotalamount;
    }

    public void setPacketTotalamount(Integer packetTotalamount) {
        this.packetTotalamount = packetTotalamount;
    }

    public Integer getPacketSignamount() {
        return packetSignamount;
    }

    public void setPacketSignamount(Integer packetSignamount) {
        this.packetSignamount = packetSignamount;
    }

    public Integer getFillupFlag() {
        return fillupFlag;
    }

    public void setFillupFlag(Integer fillupFlag) {
        this.fillupFlag = fillupFlag;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Integer getBorrowCycle() {
        return borrowCycle;
    }

    public void setBorrowCycle(Integer borrowCycle) {
        this.borrowCycle = borrowCycle;
    }

    public Date getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Date packetTime) {
        this.packetTime = packetTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
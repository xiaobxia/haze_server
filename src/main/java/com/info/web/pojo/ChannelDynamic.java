package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class ChannelDynamic implements Serializable {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String apkUrl;

    private String registerPicUrl;

    private String downloadPicUrl;

    private String picCodeNum;

    private String channelTag;

    private String toutiaoConvertId;

    private Long channelInfoId;

    private Long userInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getRegisterPicUrl() {
        return registerPicUrl;
    }

    public void setRegisterPicUrl(String registerPicUrl) {
        this.registerPicUrl = registerPicUrl;
    }

    public String getDownloadPicUrl() {
        return downloadPicUrl;
    }

    public void setDownloadPicUrl(String downloadPicUrl) {
        this.downloadPicUrl = downloadPicUrl;
    }

    public String getPicCodeNum() {
        return picCodeNum;
    }

    public void setPicCodeNum(String picCodeNum) {
        this.picCodeNum = picCodeNum;
    }

    public String getChannelTag() {
        return channelTag;
    }

    public void setChannelTag(String channelTag) {
        this.channelTag = channelTag;
    }

    public String getToutiaoConvertId() {
        return toutiaoConvertId;
    }

    public void setToutiaoConvertId(String toutiaoConvertId) {
        this.toutiaoConvertId = toutiaoConvertId;
    }

    public Long getChannelInfoId() {
        return channelInfoId;
    }

    public void setChannelInfoId(Long channelInfoId) {
        this.channelInfoId = channelInfoId;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }




}
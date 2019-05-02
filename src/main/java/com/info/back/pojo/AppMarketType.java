package com.info.back.pojo;

import java.util.Date;

/**
 * 应用市场类型
 *
 * @author tgy
 * @version [版本号, 2018年5月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppMarketType {
    private Integer id;

    private String appType;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
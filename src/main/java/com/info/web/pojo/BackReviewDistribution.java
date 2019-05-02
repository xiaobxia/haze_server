package com.info.web.pojo;

/**
 * Created by tl on 2018/6/4.
 */
public class BackReviewDistribution {
    private Integer id;
    private Integer userId;
    private Integer distributionRate;
    private String userName;
    private Double disRate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDistributionRate() {
        return distributionRate;
    }

    public void setDistributionRate(Integer distributionRate) {
        this.distributionRate = distributionRate;
    }

    public Double getDisRate() {
        return disRate;
    }

    public void setDisRate(Double disRate) {
        this.disRate = disRate;
    }
}

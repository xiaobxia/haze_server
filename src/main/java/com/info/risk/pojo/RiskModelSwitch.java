package com.info.risk.pojo;

/**
 * Created by tl on 2018/5/3.
 */
public class RiskModelSwitch {
    //不启用
    public static final int OFF = 0;
    //启用
    public static final int ON = 1;
    private Long id;
    private String modelCode;
    private Integer variableVersion;
    private Integer binningVersion;
    private Integer cutoffVersion;
    private Integer canIgnoreVersion;
    private Integer modelStatus;
    private Integer modelWeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public Integer getVariableVersion() {
        return variableVersion;
    }

    public void setVariableVersion(Integer variableVersion) {
        this.variableVersion = variableVersion;
    }

    public Integer getBinningVersion() {
        return binningVersion;
    }

    public void setBinningVersion(Integer binningVersion) {
        this.binningVersion = binningVersion;
    }

    public Integer getCutoffVersion() {
        return cutoffVersion;
    }

    public void setCutoffVersion(Integer cutoffVersion) {
        this.cutoffVersion = cutoffVersion;
    }

    public Integer getCanIgnoreVersion() {
        return canIgnoreVersion;
    }

    public void setCanIgnoreVersion(Integer canIgnoreVersion) {
        this.canIgnoreVersion = canIgnoreVersion;
    }

    public Integer getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(Integer modelStatus) {
        this.modelStatus = modelStatus;
    }

    public Integer getModelWeight() {
        return modelWeight;
    }

    public void setModelWeight(Integer modelWeight) {
        this.modelWeight = modelWeight;
    }
}

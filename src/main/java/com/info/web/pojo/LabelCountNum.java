package com.info.web.pojo;

public class LabelCountNum {
    private Integer id;

    private Integer baseId;

    private Byte jobKind;

    private String value;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public Byte getJobKind() {
        return jobKind;
    }

    public void setJobKind(Byte jobKind) {
        this.jobKind = jobKind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
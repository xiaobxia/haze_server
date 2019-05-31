package com.info.web.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 群发短信实体
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-05-31 10:54:29
 */
 public class UserMassSms implements Serializable {

    private static final long serialVersionUID = -325114052679392094L;

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 回复内容
     */
    private String receiveContent;

    /**
     * 回复时间
     */
    private Date receiveTime;

    /**
     * 扩展字段
     */
    private String ext;


    /**
     * 获取主键Id
     *
     * @return id
     */
    public Integer getId(){
      return id;
    }

    /**
     * 设置主键Id
     * 
     * @param id
     */
    public void setId(Integer id){
      this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    public String getMobile(){
      return mobile;
    }

    /**
     * 设置手机号
     * 
     * @param mobile 要设置的手机号
     */
    public void setMobile(String mobile){
      this.mobile = mobile;
    }

    /**
     * 获取发送内容
     *
     * @return 发送内容
     */
    public String getSendContent(){
      return sendContent;
    }

    /**
     * 设置发送内容
     * 
     * @param sendContent 要设置的发送内容
     */
    public void setSendContent(String sendContent){
      this.sendContent = sendContent;
    }

    /**
     * 获取发送时间
     *
     * @return 发送时间
     */
    public Date getSendTime(){
      return sendTime;
    }

    /**
     * 设置发送时间
     * 
     * @param sendTime 要设置的发送时间
     */
    public void setSendTime(Date sendTime){
      this.sendTime = sendTime;
    }

    /**
     * 获取回复内容
     *
     * @return 回复内容
     */
    public String getReceiveContent(){
      return receiveContent;
    }

    /**
     * 设置回复内容
     * 
     * @param receiveContent 要设置的回复内容
     */
    public void setReceiveContent(String receiveContent){
      this.receiveContent = receiveContent;
    }

    /**
     * 获取回复时间
     *
     * @return 回复时间
     */
    public Date getReceiveTime(){
      return receiveTime;
    }

    /**
     * 设置回复时间
     * 
     * @param receiveTime 要设置的回复时间
     */
    public void setReceiveTime(Date receiveTime){
      this.receiveTime = receiveTime;
    }

    /**
     * 获取扩展字段
     *
     * @return 扩展字段
     */
    public String getExt(){
      return ext;
    }

    /**
     * 设置扩展字段
     * 
     * @param ext 要设置的扩展字段
     */
    public void setExt(String ext){
      this.ext = ext;
    }

}
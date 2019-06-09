package com.info.web.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户发送短信记录实体
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-06-08 22:02:39
 */

@Data
 public class UserSmsSendLog implements Serializable {

   private static final long serialVersionUID = -4454130629046241345L;

   /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 发送手机号
     */
    private String sendPhone;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送模板ID
     */
    private Integer sendTemplateId;

    /**
     * 发送状态，1为成功，其他均为失败
     */
    private Integer sendDeliver;

    /**
     * 短信唯一码
     */
    private String smsUuid;
}
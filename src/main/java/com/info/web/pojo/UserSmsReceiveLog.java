package com.info.web.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户发送短信记录实体
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-06-08 22:42:58
 */

@Data
 public class UserSmsReceiveLog implements Serializable {

   private static final long serialVersionUID = -1212443197966001827L;

   /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 回复手机号
     */
    private String receivePhone;

    /**
     * 回复内容
     */
    private String receiveContent;

    /**
     * 回复时间
     */
    private Date receiveTime;

}
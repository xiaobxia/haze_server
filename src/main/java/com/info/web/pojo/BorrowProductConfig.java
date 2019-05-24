package com.info.web.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BorrowProductConfig implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.id
     *
     * @mbggenerated
     */
    private Integer id;//产品id

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.borrow_amount
     *
     * @mbggenerated
     */
    private BigDecimal borrowAmount;//产品金额

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.total_fee_rate
     *
     * @mbggenerated
     */
    private BigDecimal totalFeeRate;//总费率 = 产品金额-实际到账金额

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.borrow_interest
     *
     * @mbggenerated
     */
    private BigDecimal borrowInterest;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.turst_trial
     *
     * @mbggenerated
     */
    private BigDecimal turstTrial;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.platform_licensing
     *
     * @mbggenerated
     */
    private BigDecimal platformLicensing;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.collect_channel_fee
     *
     * @mbggenerated
     */
    private BigDecimal collectChannelFee;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.account_manager_fee
     *
     * @mbggenerated
     */
    private BigDecimal accountManagerFee;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.late_fee
     *
     * @mbggenerated
     */
    private BigDecimal lateFee;//滞纳金

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.renewal_fee
     *
     * @mbggenerated
     */
    private BigDecimal renewalFee;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.renewal_poundage
     *
     * @mbggenerated
     */
    private BigDecimal renewalPoundage;//不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.borrow_day
     *
     * @mbggenerated
     */
    private Integer borrowDay;//借款期限

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.deal_flag
     *
     * @mbggenerated
     */
    private String dealFlag; //删除标志 n 未删除 y 已删除

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.create_time
     *
     * @mbggenerated
     */
    private Date createTime;//创建时间 不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;//更新时间 不需要传

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column borrow_product_config.remark
     *
     * @mbggenerated
     */
    private String remark;//备注

    private Integer status;//状态 0默认 1 非默认
    private Integer extendId; //续期id


    private Integer limitId; //提额id

    private String productName;//产品名称

    private String projectName;//马架名 不需要传


}
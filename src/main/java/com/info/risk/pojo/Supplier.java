package com.info.risk.pojo;

/**
 * 信息来源标识
 * Created by Phi on 2017/11/23.
 */
public enum Supplier {
    /**
     * 第三方
     */
    TD,// 同盾
    SJMH,// 数据魔盒
    ZMXY,// 芝麻信用
    ZMF,//芝麻分
    ZMHYGZ,//芝麻行业关注
    ZZC,//中智诚
    ZZCFQZ, //中智诚反欺诈
    ZZCHMD, //中智诚黑名单
    BQS, //白骑士
    ZCAF, //致诚阿福
    TB,//淘宝
    ZM,//芝麻
    /**
     * 通用业务评估器
     */
    BQSD,//白骑士细解析

    /**
     * 小鱼儿
     */
    USER, //用户数据解析评估器
    AUTO,//机审初审数据评估器
    AUTOLOAN,//机审放款评估器
    JXSJMH,//小鱼儿数聚魔盒评估器
    JXOC,//小鱼儿老用户评估器

    /**
     * 有零花
     */
    YLHSJMH,//有零花数聚魔盒

    /**
     * 零用贷
     */
    LYD,//零用贷
    LYDSJMH//零用贷数聚魔盒
}

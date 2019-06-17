package com.info.back.pojo.mxreport;

/**
 * Created by zhangliang on 17/3/24.
 */
public enum MXBehaviorCheckEnums {

    //运营商
    regular_circle("朋友圈在哪里"),
    phone_used_time("号码使用时间"),
    phone_silent("手机静默情况"),
    phone_call("号码通话情况"),
    contact_each_other("互通过电话的号码数量"),
    contact_macao("澳门地区"),
    contact_110("110"),
    contact_120("120"),
    contact_lawyer("律师"),
    contact_court("法院"),
    contact_loan("贷款"),
    contact_bank("银行"),
    contact_credit_card("信用卡"),
    contact_collection("催收"),
    contact_night("夜间活动情况"),
    dwell_used_time("居住地本地（省份）地址在电商中使用时长"),
    ebusiness_info("总体电商使用情况"),
    person_ebusiness_info("申请人本人电商使用情况"),
    virtual_buying("虚拟商品购买情况"),
    lottery_buying("彩票购买情况"),
    person_addr_changed("申请人本人地址变化情况"),
    phone_power_off("关机情况"),
    school_status("申请人的学籍状态是否为在校学生"),
    education_info("申请人的学历情况"),
    collection_contact("与联系人互动情况"),
    work_addr_info("申请人本人最近使用工作地址情况"),
    live_addr_info("申请人本人最近使用居住地址情况"),
    school_addr_info("申请人本人最近使用学校地址情况"),
    live_position("居住地址定位"),
    work_position("工作地址定位");

    private final String canonicalName;

    public String getCanonicalName() {
        return canonicalName;
    }

    MXBehaviorCheckEnums(String name) {
        this.canonicalName = name;
    }
}

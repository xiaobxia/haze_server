package com.info.back.pojo.mxreport;

public enum MXAppPointEnums {
    call_day("VoiceCallDay", "通话活跃天数"),
    sms_day("ShortMessageDay", "短信活跃天数"),
    recharge_cnt("RechargeTimes", "充值次数"),
    call_cnt("TotalNum", "通话次数"),
    call_time("TotalTime", "通话时长（秒）"),
    peer_num_cnt("TotalPeerNum", "通话号码数目"),
    peer_loc_cnt("TotalCityNum", "通话号码归属地总数"),
    dial_cnt("DialNum", "主叫次数"),
    dialed_cnt("DialedNum", "被叫次数"),
    dial_peer_num_cnt("TotalDialPeerNum", "主叫号码数"),
    dialed_peer_num_cnt("TotalDialedPeerNum", "被叫号码数"),
    dial_time("DialTime", "主叫时长（秒）"),
    dialed_time("DialedTime", "被叫时长（秒）"),
    sms_cnt("ShortMessageTimes", "短信次数"),
    net_total("PackageTotal", "流量套餐总量（KB）"),
    net_used("PackageUsage", "流量套餐使用量（KB）"),
    avg_call_time("CallAvgDuration", "均次通话时长（秒）", false),
    no_dial_day("NoDialDay", "无呼出天数"),
    no_dial_day_pct("NoDialDayPrecent", "无呼出天数占比", false),
    no_call_day("NoCallDay", "无通话天数"),
    no_call_day_pct("NoCallDayPrecent", "无通话天数占比", false),
    max_power_on_day("PowerOnMaxDay", "最大连续开机天数", false),
    power_off_day("PowerOffDay", "关机天数"),
    power_off_day_pct("PowerOffDayPercent", "关机天数占比", false),
    continue_power_off_cnt("ContinuePowerOffNum", "连续3天以上关机次数", false),
    total_fee("TotalFee", "消费总金额（分）"),
    net_fee("NetFee", "网络流量消费金额（分）"),
    voice_fee("VoiceFee", "通话消费金额（分）"),
    sms_fee("SmsFee", "短信消费金额（分）"),
    vas_fee("VasFee", "增值业务消费金额（分）"),
    extra_fee("ExtraFee", "其它消费金额（分）"),
    recharge_amount("RechargeAmount", "充值金额（分）"),
    max_single_recharge("MaxRechargeAmount", "单次充值最大金额（分）", false),
    max_single_time("MaxSingleTime", "单次通话最长时长（秒）", false),
    min_single_time("MinSingleTime", "单次通话最短时长（秒）", false),
    cnt_1min_within("Time1", "时长在1min内的通话次数"),
    cnt_1min_5min("Time2", "时长在1min-5min内的通话次数"),
    cnt_5min_10min("Time3", "时长在5min-10min内的通话次数"),
    cnt_10min_over("Time4", "时长在10min以上的通话次数"),
    daytime_cnt("DaytimeNum", "白天(7:00-0:00)通话次数"),
    night_cnt("NightNum", "夜晚(0:00-7:00)通话次数"),
    daytime_time("DaytimeTime", "白天(7:00-0:00)通话时长（秒）"),
    night_time("NightTime", "夜晚(0:00-7:00)通话时长（秒）"),
    local_cnt("LocalNum", "本机号码归属地通话次数"),
    remote_cnt("RemoteNum", "本机号码归属地以外通话次数"),
    local_time("LocalTime", "本机号码归属地通话时长（秒）"),
    remote_time("RemoteTime", "本机号码归属地以外通话时长（秒）"),
    is_family_member("IsFamilyNetMember", "是否为亲情网用户", false),
    is_family_master("IsFamilyNetMaster", "是否为亲情网户主", false),
    continue_recharge_month_cnt("ContinueRechargeMonths", "连续充值月数", false),
    is_address_match_attribution("IsAddressMatchPhone", "常联系地址是否为手机归属地", false),
    call_cnt_less("NumbersOfVoiceCallLess", "通话次数 小于 使用月数＊1次", false),
    call_cnt_more("NumbersOfVoiceCallMore", "通话次数 大于 使用月数＊300次", false),
    unpaid_month_cnt("UnpaidMonths", "连续欠费月数", false),
    live_month_cnt("LiveMonths", "本机号码归属地使用月数", false),
    morning("ZaoShangTimeStep", "早晨[5:30-9:00]"),
    forenoon("ShangWuTimeStep", "上午[9:00-11:30]"),
    noon("ZhongWuTimeStep", "中午[11:30-13:30]"),
    afternoon("XiaWuTimeStep", "下午[13:30-17:30]"),
    dusk("BangWanTimeStep", "傍晚[17:30-19:30]"),
    evening("WanShangTimeStep", "晚上[19:30-23:30]"),
    daybreak("LingChenTimeStep", "凌晨[23:30-1:30]"),
    midnight("ShenYeTimeStep", "深夜[1:30-5:30]"),
    no_dialed_day("NoDialedDay", "无呼入天数"),
    sms_send_cnt("ShortMessageSendTimes", "短信发送次数"),
    precent_1min_over("Time1OverPrecent", "通话时长大于1m的号码占比"),
    precent_dialed_1min_over("DialedTime1OverPrecent", "通话时长大于1m的被叫号码占比");


    /** 指标列名 */
    private String code;

    /** 指标中文名 */
    private String desc;

    /** 有无平均值 */
    private boolean avg;

    MXAppPointEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.avg = true;
    }

    MXAppPointEnums(String code, String desc, boolean avg) {
        this.code = code;
        this.desc = desc;
        this.avg = avg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getAvg() {
        return avg;
    }

    public void setAvg(boolean avg) {
        this.avg = avg;
    }
}

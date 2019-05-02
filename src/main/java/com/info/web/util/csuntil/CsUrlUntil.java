package com.info.web.util.csuntil;

import com.info.back.utils.PropertiesUtil;

/**
 * 调用催收的界面
 */
public class CsUrlUntil {

    //催收服务器地址
//    public static final String CS_OVERDUE_IP = "http://cs.jx-money.com";
    public static final String CS_OVERDUE_IP = PropertiesUtil.get("CS_URL");
    public static final String urlParams = "/back/collectionOrder/ossOverdueRecord";
    public static String urlToString (Integer orderId) {
        String mdCode = MD5Util.encrypt(orderId);
        return CS_OVERDUE_IP+urlParams+"?&orderId="+orderId+"&md5Code="+mdCode;
    }

}

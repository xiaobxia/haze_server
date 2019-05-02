package com.info.web.util.identitycardlocation;


import lombok.extern.slf4j.Slf4j;

/**
 * Created by Phi on 2017/12/27.
 */
@Slf4j
public class IdentityCardLocationUtil {

//    public static void main(String[] args) {
//        String id = "410503199202175014";
//        IDCardUtil idCardUtil = new IDCardUtil();
//
//        String s1 = getCity(id);
//        String s2 = getProvince(id);
//        String s3 = getBirthYear(id);
//
//        System.out.println();
//    }

    public static String getProvince(String identity) {
        boolean flag = IDCardUtil.checkCardId(identity);
        if (!flag) {
            return "";
        }
        String province = "";
        try {
            province = IDCardUtil.parseAddressProvince(identity);
        } catch (Exception e) {
            log.error("getProvince error:{}",e);
        }
        return province;
    }

    public static String getCity(String identity) {
        boolean flag = IDCardUtil.checkCardId(identity);
        if (!flag) {
            return "";
        }
        String city = "";
        try {
            city = IDCardUtil.parseAddressCity(identity);
        } catch (Exception e) {
            log.error("getCity error:{}",e);

        }
        return city;
    }

    public static String getBirthYear(String identity) {
        boolean flag = IDCardUtil.checkCardId(identity);
        if (!flag) {
            return "";
        }
        String birthYear = "";
        try {
            birthYear = IDCardUtil.parseBirthYear(identity);
        } catch (Exception e) {
            log.error("getBirthYear error:{}",e);

        }
        return birthYear;
    }


}

package com.info.web.util.yjr;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具类
 * @author JinLei
 */
@Slf4j
public class SignUtils {
	
    public static final String SIGN_SHA1_WITH_RSA = "SHA1WithRSA";
    
    public static String signBySoft(String privateKey, String signStr)
            throws Exception {
        return signBySoft(privateKey, signStr, 1024);
    }

    public static String signBySoft(String privateKey, String signStr, int modulus)
            throws Exception {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1 = messageDigest.digest(signStr.getBytes("utf-8"));

            byte[] encryptxtArr = RsaUtils.encryptByPrivateKey(Base64.decode(privateKey.getBytes("utf-8")), sha1,
                    modulus);

            encryptxtArr = Base64.encode(encryptxtArr);
            return new String(encryptxtArr, "utf-8");
        } catch (Exception e) {
            log.error("signBySoft error:{}",e);
            throw new Exception("软签名异常：" + e.getMessage());
        }
    }

    public static boolean verifyingSign(String publicKey, String signCipher, String signStr, int modulus)
            throws Exception {
        try {
            byte[] signCipherArr = RsaUtils.decryptByPublicKey(Base64.decode(publicKey.getBytes("utf-8")),
                    Base64.decode(signCipher.getBytes("utf-8")), modulus);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1Bytes = messageDigest.digest(signStr.getBytes("utf-8"));

            return Arrays.equals(sha1Bytes, signCipherArr);
        } catch (Exception e) {
            log.error("verifyingSign error:{}",e);
            throw new Exception("验签异常：" + e.getMessage());
        }
    }

    public static boolean verifyingSign(String publicKey, String signCipher, String signStr)
            throws Exception {
        return verifyingSign(publicKey, signCipher, signStr, 1024);
    }

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    商户私钥
     * @return 签名值
     */
    public static String signBySoftSha1WithRSA(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes("utf-8")));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance(SIGN_SHA1_WITH_RSA);

            signature.initSign(priKey);
            signature.update(content.getBytes("utf-8"));

            byte[] signed = signature.sign();

            return new String(Base64.encode(signed),"utf-8");
        } catch (Exception e) {
            log.error("signBySoftSha1WithRSA error:{}",e);
        }

        return null;
    }

    /**
     * 验签
     * @param publicKey 公钥
     * @param signCipher 签名
     * @param signStr 待验签数据
     * @return 验签是否通过
     */
    public static boolean verifyingSignSha1WithRSA(String publicKey, String signCipher, String signStr) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey.getBytes())));

        Signature signature = Signature.getInstance(SIGN_SHA1_WITH_RSA);
        signature.initVerify(pubKey);
        signature.update(signStr.getBytes("utf-8"));
        return signature.verify(Base64.decode(signCipher.getBytes("utf-8")));
    }
    
    /**
     * 获得特定的待签名数据
     *
     */
    public static String getSignData(JSONObject jsonObject) {
        Map<String, Object> resultMap = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (isNotEmpty(entry.getValue() + "")) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        // 排序
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str) && !"null".equals(str);
    }
}
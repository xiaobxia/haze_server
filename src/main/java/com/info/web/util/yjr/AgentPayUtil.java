package com.info.web.util.yjr;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentPayUtil {

	/**
	 * 商户私钥，生产环境请重新生成
	 * */
//    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOg+PNOota7ppeHwDBB7zttL/OpUmaOCbei/J2t/FLNjumsMjJVGLfdxKlrqCQXVAzAZDIakQcbfRvvlhZJCEBAnL2tru8Qp6hs9dokI2zOXjf9wyk4hPQT5/noXEnlnXO1lr4MZeXhgLKCwZOQfTeE9WSWuTmdA5Dzgu0d/TvuJAgMBAAECgYBBEqdCeyQlFWyYaQVIXRhx09HS6s99xB79twnZker/9LKYKhT+AoMAsSG4BZlvm+bfxDUBSObxTUB7di099OrAw0J1F0QpCXL5Jrxc2NdW8/j1hXb77UbdgsUZg4hM5JkJ2QRxiwT0JyWUAIikSx0W+jUzTFkz1UFaMiZOwEX7rQJBAPcd1+dvclPiR77McvxEpje04dddIiGIQxCw7oZmARMOK33Jrd3+6nTv8xlhFSWM9/xgJzQq8n+aZ4X9+ZXEp9sCQQDwl4RolGsvtp/8jyFBRNGOTQ6CWM/77lK47swzeu50GCFNyf+tLNu8kOhyk+8LIftKFm44m8PZrsZDYZLNDGlrAkA6n0bHrWWGxshUV/XzKGnyDyQATiS5pbSbMg3zriEVHyhsF7r6Te3avc2CuMgmd1Gg+kJymrmaUcu7OqvJvrQ/AkBFQylwPgIZi1bFi6MEOj6l29MofU7q9TJFYSHSVDqfm27DCTsc7MQZphH1Ild3+gFw08JJc7ZPTbxwG3/6ne8fAkBDiUWE/r5GAVrLDFXIqglkG+25B0LPGT2ttTNL2Id/4QAbUXLxGuwmBY52B8m2Y2U8agJDL6YdsE+gvykcH9oB";
    private static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOHHAaMoSGFmujypKRBh/iw7gcJEPfi27q5iSPN+JFZqIqamw+S0nE3ns+GqQAgEat9euhhdlxyNzrDBa2HRzupC7UKHjwiIisWHgb0y5SAgjRxcsjO/RN+aBYjFyijg5Kp907mWjEI1pJV5bFKFqz/MI8BVNEpzucXtIWzV4KfJAgMBAAECgYEAwl50xmWTimbJxhdLRtNV4faIyxm80DWMXYw3iYf6+Hn+wsGmu2nRdjAW6BuNbFmBODNwujE4OljKq9iX/WqGWgAHU5KCRyjWyTuIEB2eeqaEiXTcxvEiNYBGUKiH1OY6bBWu3QpYpID4nZZP0YqojkGiuAuyBBKDTqzST/xO1kECQQD+yMe9TFbpo5aGL1+MWMyKeC9TaMu95z12/7E1vsCY3nvyHF6MC3aBiTXnZoYy1Yjf/9g5G42aZDeaYR/QAWFNAkEA4trLSxiY7FIFV2GYTWwAOHzsVxogoeltfeFpXRQKQdHv0f8IvsskRCe4ud9uOY+igyiZjB/tDUf5g5+gwNYibQJAIZHc13FGhlQ6kgWN5lUQ5jG9spS9O5FLRPGdrMIsaDfwRs88GeGsxJaKlqmohuwhs7nnCGc7+hq4RMwFQAfehQJBAMqH5+O9rBmgCKDAwwasG25TKXWCIAwtqNEvw6+TyqyEz4U+GgKsvk4S4HshnPjK4Z4TSzKs9ihbT8pRY2WGR+kCQQD96X6PMOMvpcBI8u0JOn8yvtUWqRjNKxB0bW9LfV+GgPGjuCFpHfQS1aMnX7Djc9J0X8mkuDxmNitIZI4bNYVI";
    /**
	 * 平台公钥，生产环境请至门户-安全管理中查看
	 * */
//    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+IMBbuSZVmiilWuGhGm4cgTmw7YBXykebkQkIDJEifj+SZxjMJBsjZ5JqjAFSlPNW+gv9T3UXe5gBQPM8YqB+kwAWtHjzRDlU/kaAq2A+MVCqR44KDNaVK+raiBme1wJ3w0bxDPwxjMPkg2psc0jGuP+lovS3fJwNbkEHRne68wIDAQAB";
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2/H/PnFlbJRxExmmVxIbIU+xvnwRNoN/GRlfMRlesDAqQU2BPcj7iXMfX4NCK632B+Spe+oI6xAo0rJr2qyjCiUgvLl6hvd/okR+Ee9SuDMRXFhqxR0kulDhE0Dfj0dVd6SokUEOBcM16bXH0oCu0DgbiUcRDMfS5p1VxjhrxLAOJ0N4y3dek3t2Lh1fL9ok3aVXpwRzvYjS4SxBt6A03mDoHpksmSYjEA+o1NNR0h0SD0H7f7C7J1reoRqsfjcjjy3pbeCauTkCNB3TJvBVYN4LLRUHkvWeA7GwANvmpDdPYpSWCa9G3akEA7Za+y39rMqU8zCT6FtfsHH9q2OHvQIDAQAB";
    public static String execute(String reqMsg, String url) {
        try {
            /**
             * 参数转换为FastJson对象，便于操作。
             * */
            JSONObject jsonParam = JSONObject.parseObject(reqMsg);
            String sign = "no_sign";

            /**
             * 将接口中所有参数名从a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推。
             * 再把所有“参数名=参数值”以“&”字符连接起来。注意如果参数值为空，该参数不参与签名。
             * */
            String signData = SignUtils.getSignData(jsonParam);
            
            /**
             * 使用Sha1WithRSA私钥加签
             * */
            sign = SignUtils.signBySoftSha1WithRSA(signData, privateKey);
            
            jsonParam.put("sign", sign);
            log.info("reqSignData:{}",signData);

            /**
             * 发送http(s)请求
             * */
            String reqMsgHasSign = jsonParam.toJSONString();
            log.info("requestData :{}",reqMsgHasSign);

            String respStr = HttpUtils.execute(url, reqMsgHasSign, 60000);
            log.info("responseData :{}",respStr);

            JSONObject respJson = JSONObject.parseObject(respStr);
            if (!SystemRetCode.SUCCESS.toString().equalsIgnoreCase(respJson.getString(SystemRetField.RET_CODE.toString()))) {
                JSONObject retJson = new JSONObject();
                retJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.COM_ERROR.toString());
                retJson.put(SystemRetField.RET_DESC.toString(), "通讯异常，请查看日志");
                return retJson.toJSONString();
            }
            /**
             * 获取返回的数据
             * 去掉签名字段，获取待验签字符串
             * */
            String respParamsStr = respJson.getString(SystemRetField.RET_BODY.toString());
            JSONObject respParamsJson = JSONObject.parseObject(respParamsStr);
            String retSign = respParamsJson.remove("sign") + "";
            String respSignData = SignUtils.getSignData(respParamsJson);
            
            boolean signResult = false;
            /**
             * 验证平台签名
             * 使用Sha1WithRSA 公钥验证平台返回的签名字段
             * */
            log.info("签名retSign :{}",retSign);
            log.info("respSignData :{}",respSignData);

            signResult = SignUtils.verifyingSignSha1WithRSA(publicKey, retSign, respSignData);
            log.info("验签结果 :{}",signResult);

            if (!signResult) {//!signResult
                JSONObject retJson = new JSONObject();
                retJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.SIGN_ERROR.toString());
                retJson.put(SystemRetField.RET_DESC.toString(), "商户验平台签名失败，请检查公钥是否正确或者数据是否被篡改");
                return retJson.toJSONString();
            }

            JSONObject retJson = new JSONObject();
            retJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.SUCCESS.toString());
            retJson.put(SystemRetField.RET_BODY.toString(), respParamsJson);
            return retJson.toJSONString();
        } catch (Exception e) {
            log.error("execute error:{}",e);
            JSONObject retJson = new JSONObject();
            retJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.EXCEPTION.toString());
            retJson.put(SystemRetField.RET_DESC.toString(), e.getMessage());
            return retJson.toJSONString();
        }
    }

    /**
     * 验证签名
     */
    public static Boolean verifySign(JSONObject jsonObject){



        String retSign = jsonObject.remove("sign") + "";
        String respSignData = SignUtils.getSignData(jsonObject);

        boolean signResult = false;
        try {
            log.info("签名retSign:{}",retSign);
            log.info("respSignData:{}",respSignData);


            //使用Sha1WithRSA 公钥验证平台返回的签名字段
            signResult = SignUtils.verifyingSignSha1WithRSA(publicKey, retSign, respSignData);
            log.info("result:{}",signResult);

        }catch (Exception e){
            log.error("verifySign error:{}",e);
            return signResult;
        }

        return signResult;
    }

    public static String getPublicKey(){
        return publicKey;
    }

    public static String getPrivateKey(){
        return privateKey;
    }


}

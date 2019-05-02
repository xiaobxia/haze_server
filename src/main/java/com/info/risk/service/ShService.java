package com.info.risk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.risk.dao.IRiskCreditUserDao;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.DateUtil;
import com.info.web.util.GenerateNo;
import com.info.web.util.OrderNoUtil;
@Slf4j
@Service
public class ShService implements IShService {
    @Autowired
    private IRiskOrdersService riskOrdersService;
    @Autowired
    private IRiskCreditUserDao riskCreditUserDao;

    static public HttpURLConnection getConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            if (url.getProtocol().toUpperCase().startsWith("HTTPS")) {
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(new KeyManager[0], new TrustManager[]{new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }}, new SecureRandom());

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(ctx.getSocketFactory());
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                connection = conn;
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

        } catch (Exception e) {
            log.error("getConnection error:{}",e);
        }

        return connection;
    }

    public static String sendPost(String url, String p, String charset, Map<String, String> headers) {
        OutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = getConnection(realUrl);

            Map<String, String> defaultHeaders = new LinkedHashMap<String, String>();
            defaultHeaders.put("accept", "*/*");
            defaultHeaders.put("connection", "Keep-Alive");
            defaultHeaders.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (null != headers) {
                defaultHeaders.putAll(headers);
            }
            // set header
            // defaultHeaders.forEach((k, v) -> conn.setRequestProperty(k, v));
            Iterator<String> ite = defaultHeaders.keySet().iterator();
            String key = null;
            while (ite.hasNext()) {
                key = ite.next();
                conn.setRequestProperty(key, defaultHeaders.get(key));
            }
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = conn.getOutputStream();
            if (p != null) {
                // 发送请求参数
                out.write(p.getBytes(charset));
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("sendPost error:{}",e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("sendPost error:{}",ex);
            }
        }
        return result;
    }

    private static String byts2hexstr(byte[] arrayBytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (int i = 0; i < arrayBytes.length; i++) {
            tmp = Integer.toHexString(arrayBytes[i] & 0xff);
            sb.append(tmp.length() == 1 ? "0" + tmp : tmp);
        }
        return sb.toString();
    }

    public static String signature(String ORG_KEY, String timestamp, String nonce) {
        try {
            String[] arr = new String[]{timestamp, nonce, ORG_KEY};
            Arrays.sort(arr);// 参数值做字典排序
            String s = arr[0] + arr[1] + arr[2];
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes("utf-8"));
            return byts2hexstr(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("signature error:{}",e);

        } catch (UnsupportedEncodingException e) {
            log.error("signature error:{}",e);
        }
        return "";
    }

    @Override
    public ServiceResult getFqz(HashMap<String, Object> params) {
        ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
        try {
            Object userId = params.get("userId");
            if (userId != null) {
                RiskCreditUser riskCreditUser = riskCreditUserDao.findUserDetail(Integer.valueOf(userId + ""));
                if (riskCreditUser != null) {
                    LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.SH);
                    String url = map2.get("SH_URL");
                    RiskOrders orders = new RiskOrders();
                    orders.setUserId(userId + "");
                    orders.setOrderType(ConstantRisk.SH);
                    orders.setAct(ConstantRisk.SH_FQZ);
                    orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
                    JSONObject jsonObject1 = new JSONObject();
                    Date now = new Date();
                    long time = now.getTime();
                    String key = map2.get("SH_KEY");
                    String nonce = GenerateNo.generateShortUuid(10);
                    String[] arr = new String[]{time + "", nonce, key};
                    Arrays.sort(arr);// 参数值做字典排序
                    jsonObject1.put("TIMESTAMP", time);
                    jsonObject1.put("NONCE", nonce);
                    jsonObject1.put("SIGN", signature(key, time + "", nonce));
                    jsonObject1.put("ORG_NUM", map2.get("SH_JGH"));
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("FRD_PRODUCT_NUM", "AFP1000");
                    jsonObject2.put("APP_PRODUCT_NUM", "A01");
                    jsonObject2.put("FRD_DATA_NUM", 1);
                    jsonObject2.put("APP_NUM", OrderNoUtil.getInstance().getUUID());
                    jsonObject2.put("APP_DATE", DateUtil.getDateFormat(now, "yyyyMMddHHmmss"));
                    jsonObject2.put("APP_LOAN_TYPE", 1);
                    jsonObject2.put("ID_TYPE", 0);
                    jsonObject2.put("ID_NUM", riskCreditUser.getCardNum());
                    // jsonObject2.put("ID_HASH",MD5coding.getInstance().code(cardNum+""));
                    jsonObject2.put("CUST_NAME", riskCreditUser.getUserName());
                    jsonObject2.put("CELL_PHONE", riskCreditUser.getUserPhone());
                    // jsonObject2.put("CELL_PHONE_HASH",MD5coding.getInstance().code(userPhone+""));
                    if (StringUtils.isNotBlank(riskCreditUser.getEquipmentNumber())) {
                        jsonObject2.put("CELL_IMEI", riskCreditUser.getEquipmentNumber());
                    }
                    if (StringUtils.isNotBlank(riskCreditUser.getEmail())) {
                        jsonObject2.put("EMAIL", riskCreditUser.getEmail());
                    }
                    if (StringUtils.isNotBlank(riskCreditUser.getCompanyName())) {
                        jsonObject2.put("EMP_NAME", riskCreditUser.getCompanyName());
                    }
                    if (StringUtils.isNotBlank(riskCreditUser.getCompanyAddress())) {
                        jsonObject2.put("EMP_ADDR", riskCreditUser.getCompanyAddress());
                    }
                    if (StringUtils.isNotBlank(riskCreditUser.getCompanyPhone())) {
                        jsonObject2.put("EMP_PHONE", riskCreditUser.getCompanyPhone());
                    }
                    jsonObject2.put("C_1_NAME", riskCreditUser.getFirstContactName());
                    jsonObject2.put("C_1_PHONE", riskCreditUser.getFirstContactPhone());
                    Integer firstRelation = riskCreditUser.getFristContactRelation();
                    if (firstRelation == 1 || firstRelation == 2) {
                        firstRelation = 0;
                    } else if (firstRelation == 3 || firstRelation == 4) {
                        firstRelation = 1;
                    } else if (firstRelation == 5) {
                        firstRelation = 2;
                    } else {
                        firstRelation = 9;
                    }
                    jsonObject2.put("C_1_RELATION", firstRelation);
                    jsonObject2.put("C_2_NAME", riskCreditUser.getSecondContactName());
                    jsonObject2.put("C_2_PHONE", riskCreditUser.getSecondContactPhone());
                    Integer secondRelation = riskCreditUser.getSecondContactRelation();
                    if (secondRelation == 1 || secondRelation == 4) {
                        secondRelation = 4;
                    } else if (secondRelation == 2) {
                        secondRelation = 3;
                    } else if (secondRelation == 3) {
                        secondRelation = 5;
                    } else {
                        secondRelation = 9;
                    }
                    jsonObject2.put("C_2_RELATION", secondRelation);
                    jsonObject2.put("GENDER", riskCreditUser.getSex());
                    int marriage = riskCreditUser.getMaritalStatus().intValue();
                    if (marriage == 1) {
                        marriage = 10;
                    } else if (marriage == 2 || marriage == 3) {
                        marriage = 20;
                    } else if (marriage == 4) {
                        marriage = 40;
                    } else {
                        marriage = 90;
                    }
                    jsonObject2.put("MARRIAGE", marriage);
                    int education = riskCreditUser.getEducation().intValue();
                    if (education == 1 || marriage == 2) {
                        education = 10;
                    } else if (education == 3) {
                        education = 20;
                    } else if (education == 4) {
                        education = 30;
                    } else if (education == 5) {
                        education = 40;
                    } else if (education == 6) {
                        education = 60;
                    } else {
                        education = 70;
                    }
                    jsonObject2.put("EDUCATION", education);
                    jsonObject1.put("DATA", jsonObject2);
                    String jsonStrData = jsonObject1.toString();
                    orders.setReqParams("url=" + url + ",jsonStrData=" + jsonStrData);
                    riskOrdersService.insert(orders);
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    String result = sendPost(url, jsonStrData.toString(), "utf-8", headers);
                    if (StringUtils.isNotBlank(result)) {
                        JSONObject jsonObject = JSONObject.fromObject(result);
                        if ("00".equals(jsonObject.getString("RET_CODE"))) {
                            JSONObject data = jsonObject.getJSONObject("DATA");
                            if ("0000".equals(data.getString("APP_VALID"))) {
                                serviceResult = new ServiceResult(ServiceResult.SUCCESS, data.toString());
                                orders.setStatus(RiskOrders.STATUS_SUC);
                            }
                        } else {
                            serviceResult = new ServiceResult("300", jsonObject.getString("RET_CODE") + jsonObject.getString("RET_MSG"));
                        }
                        orders.setReturnParams(result);
                        riskOrdersService.update(orders);
                    } else {
                        orders.setReturnParams("sh return null");
                        serviceResult = new ServiceResult("100", "算话反欺诈返回空或请求报错");
                    }
                    riskOrdersService.update(orders);
                } else {
                    serviceResult.setMsg("用户不存在");
                }

            } else {
                serviceResult = new ServiceResult("400", "必要参数不足！");
            }
        } catch (Exception e) {
            log.error("getFqz error ,params=:{}error:{}",params, e);
        }
        return serviceResult;
    }
}

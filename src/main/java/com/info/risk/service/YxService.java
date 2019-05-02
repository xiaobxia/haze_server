package com.info.risk.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.risk.dao.IRiskOrdersDao;
import com.info.risk.pojo.BorrowRepayInfo;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.risk.utils.yx.OuterEcFormat;
import com.info.risk.utils.yx.RC4_128_V2;
import com.info.risk.utils.yx.RSA_1024_V2;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.User;
import com.info.web.util.DateUtil;
import com.info.web.util.JSONUtil;
import com.info.web.util.OrderNoUtil;
import com.tan66.datawarehouse.openapi.model.asset.MbgAssetBorrowOrder;
import com.tan66.datawarehouse.openapi.model.asset.MbgAssetRepayment;
import com.tan66.datawarehouse.openapi.model.user.MbgUserInfo;
import com.tan66.datawarehouse.openapi.rpc.api.DataWareHouseService;
import com.vxianjin.gringotts.dao.util.common.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class YxService implements IYxService {
    @Autowired
    private IRiskOrdersService riskOrdersService;
    @Autowired
    private IRiskOrdersDao riskOrdersDao;

    @Autowired
    private DataWareHouseService dataWareHouseService;

    //定义这个rc4秘钥
    private static final String rc4Key = "f524d66276fb9c15";

    @Override
    public void findForYx() {

    }

    @Override
    public ServiceResult sendYx(HashMap<String, Object> params) {
        ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
        try {
            Object userName = params.get("userName");
            Object cardNum = params.get("cardNum");
            Object userId = params.get("userId");
            if (userName != null && cardNum != null && userId != null) {
                RiskOrders orders = new RiskOrders();
                orders.setUserId(userId + "");
                orders.setOrderType(ConstantRisk.YX);
                orders.setAct(ConstantRisk.YX_BORROW);
                orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
                orders.setReqParams("userId=" + userId + ";userName=" + userName + ";cardNum=" + cardNum);
                log.info("yx card:{}", cardNum);
                log.info("sendYx send:{}", orders.toString());
                riskOrdersService.insert(orders);
                LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.YX);

                String url = map2.get("YX_URL");
                String name = map2.get("YX_NAME");
                String pwd = map2.get("YX_KEY");
                RSAPublicKey rsaKey = RSA_1024_V2.gainRSAPublicKeyFromCrtFile(ConstantRisk.YX_KEY);
                String encUserid = RSA_1024_V2.encodeByPublicKey(rsaKey, name);
                JSONObject data = new JSONObject();
                data.put("name", userName); //用户名
                data.put("idNo", cardNum); //身份证号
                data.put("queryReason", "10"); //查询原因  贷款审批
                JSONObject innerJson = new JSONObject();
                innerJson.put("tx", "101"); //业务编号类型
                innerJson.put("data", data);
                String encParams = URLEncoder.encode(RC4_128_V2.encode(innerJson.toJSONString(), pwd), "UTF-8");
                Map<String, String> paraMap = new HashMap<>();
                paraMap.put("userid", encUserid);
                paraMap.put("params", encParams);
                String result = WebClient.getInstance().doPost(url, paraMap);
                log.info("sendYx return:{}", result);
                if (StringUtils.isNotBlank(result)) {
                    OuterEcFormat rec = (OuterEcFormat) JSONUtil.jsonToBean(result, OuterEcFormat.class);
                    int code = rec.getErrorCode();
                    String msg = rec.getMessage();
                    String decodeRes = null;
                    if (code == 1) {
                        decodeRes = RC4_128_V2.decode(URLDecoder.decode(rec.getParams(), "UTF-8"), pwd);
                        log.info("解密响应:{} ", decodeRes);
                        serviceResult = new ServiceResult("600", decodeRes);
                    } else if (code == 0) {
                        decodeRes = RC4_128_V2.decode(URLDecoder.decode(rec.getParams(), "UTF-8"), pwd);
                        log.info("解密响应：{}", decodeRes);
                        orders.setStatus(RiskOrders.STATUS_SUC);
                        serviceResult = new ServiceResult(ServiceResult.SUCCESS, decodeRes);
                    } else {
                        decodeRes = result;
                        serviceResult = new ServiceResult("300", msg);
                    }
                    orders.setReturnParams(decodeRes);
                    riskOrdersService.update(orders);
                } else {
                    orders.setReturnParams("return null");
                    serviceResult = new ServiceResult("100", "返回空或请求报错");
                }
                riskOrdersService.update(orders);

            } else {
                serviceResult = new ServiceResult("400", "必要参数不足！");
            }
        } catch (Exception e) {
            log.error("sendYx error ,params=:{}error:{}", params, e);
        }
        return serviceResult;
    }


    //进行接口提供
    public ServiceResult shareDate() {

        return null;
    }

    @Override
    public HashMap<String, Object> showYxMessage(User user) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = String.valueOf(user.getId());
        hashMap.put("userId", userId);
        RiskOrders riskOrders = riskOrdersDao.findYxReturnData(hashMap);
        if (riskOrders == null) {
            return null;
        }

        log.info("getReturnParams =:{} ", riskOrders.getReturnParams());
        String result = riskOrders.getReturnParams();
        if (result == null || !StringUtils.isNotBlank(result)) {
            return null;
        }
        log.info("riskOrders = :{}", riskOrders.toString());
        HashMap<String, Object> parseDataMap = this.paraseYxReturnReport(riskOrders.getReturnParams());
        if (parseDataMap == null) {
            return null;
        }
        log.info("parseDataMap =:{} ", String.valueOf(parseDataMap));
        return parseDataMap;
    }


    private HashMap<String, Object> paraseYxReturnReport(String result) {
        HashMap<String, Object> hashMap = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(result).getAsJsonObject();
		/*if (root.getAsJsonObject("params") == null || root.getAsJsonObject("params").getAsJsonObject("data") == null) {
			return null;
		}
		JsonObject data = root.getAsJsonObject("params").getAsJsonObject("data");*/
        if (root.getAsJsonObject("data") == null) {
            return null;
        }
        JsonObject data = root.getAsJsonObject("data");
        //queryHistory
        if (data.has("queryHistory")) {
            JsonArray records = data.getAsJsonArray("queryHistory");
            //reason.getItems().put("queryHistory", "历史查询次数: " + records.size());
            JsonArray array = data.getAsJsonArray("queryHistory");
            //reason.getItems().put("queryHistory", array.toString());
            String queryHistoryStr = String.format("历史查询次数：%d", records.size());
            hashMap.put("queryHistory", queryHistoryStr); //历史查询次数
        }
        //queryStatistics
        if (data.has("queryStatistics")) {
            JsonObject object = data.getAsJsonObject("queryStatistics");
            int timesByOtherOrg = object.has("timesByOtherOrg") && StringUtils.isNumeric(object.get("timesByOtherOrg").getAsString()) ? object.get("timesByOtherOrg").getAsInt() : 0;
            int otherOrgCount = object.has("otherOrgCount") && StringUtils.isNumeric(object.get("otherOrgCount").getAsString()) ? object.get("otherOrgCount").getAsInt() : 0;
            int timesByCurrentOrg = object.has("timesByCurrentOrg") && StringUtils.isNumeric(object.get("timesByCurrentOrg").getAsString()) ? object.get("timesByCurrentOrg").getAsInt() : 0;
            String content = String.format("其他机构查询次数: %d, 其他查询机构数 %d, 本机构查询次数 %d", timesByOtherOrg, otherOrgCount, timesByCurrentOrg);
            hashMap.put("content", content);
            /*reason.getItems().put("queryStatistics", content);
			JsonObject object = data.getAsJsonObject("queryStatistics");*/
            //reason.getItems().put("queryStatistics", object.toString());
        }
        //riskResults
        if (data.has("riskResults")) {
            StringBuilder builder = new StringBuilder();
            JsonArray records = data.getAsJsonArray("riskResults");
            if (records.size() > 0) {
                for (JsonElement element : records) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("riskDetail")) {
                        builder.append(object.get("riskDetail"));
                        builder.append(", ");
                    }
                }
                //reason.getItems().put("riskResults", builder.substring(0, builder.length() - 2).toString());
                JsonArray array = data.getAsJsonArray("riskResults");
                hashMap.put("riskResults", builder.substring(0, builder.length() - 2).toString());
                //reason.getItems().put("riskResults", array.toString());
            }
        }
        //loanRecords
        if (data.has("loanRecords")) {
            JsonArray records = data.getAsJsonArray("loanRecords");
            int ot = 0;
            //float oa = 0;
            int om3 = 0;
            int om6 = 0;
            if (records.size() > 0) {
                for (JsonElement element : records) {
                    int overdueTotal = 0, overdueM3 = 0, overdueM6 = 0;
                    JsonObject object = element.getAsJsonObject();
                    log.info("overdueTotal = :{}", object.get("overdueTotal").getAsString());
                    boolean aa = StringUtils.isNumeric(object.get("overdueTotal").getAsString());
                    log.info("aa = :{}", aa);
				/*	if (object.has("overdueTotal")) {
						overdueTotal = !String.valueOf(object.get("overdueTotal")).equals("\"\"") ? object.get("overdueTotal").getAsInt() : 0;
					}
					if (object.has("overdueM3")) {
						overdueM3 = !String.valueOf(object.get("overdueM3")).equals("\"\"") ? object.get("overdueM3").getAsInt() : 0;
					}
					if (object.has("overdueM6")) {
						overdueM6 = !String.valueOf(object.get("overdueM6")).equals("\"\"") ? object.get("overdueM6").getAsInt() : 0;
					}*/
                    overdueTotal = object.has("overdueTotal") && StringUtils.isNumeric(object.get("overdueTotal").getAsString()) ? object.get("overdueTotal").getAsInt() : 0;
                    overdueM3 = object.has("overdueM3") && StringUtils.isNumeric(object.get("overdueM3").getAsString()) ? object.get("overdueM3").getAsInt() : 0;
                    overdueM6 = object.has("overdueM6") && StringUtils.isNumeric(object.get("overdueM6").getAsString()) ? object.get("overdueM6").getAsInt() : 0;
                    //float overdueAmount = object.has("overdueAmount") ? object.get("overdueAmount").getAsFloat() : 0;
					/*int overdueM3 = object.has("overdueM3") ? object.get("overdueM3").getAsInt() : 0;
					int overdueM6 = object.has("overdueM3") ? object.get("overdueM6").getAsInt() : 0;*/
                    ot += overdueTotal;
                    om3 += overdueM3;
                    om6 += overdueM6;
                }
                hashMap.put("loanRecords", "逾期总数: " + ot + ",  M3逾期次数: " + om3 + ",  M6逾期次数: " + om6);
            }
            //reason.getItems().put("loanRecords", "逾期总数: "+ ot + "  M3逾期次数: " + om3  + "  M6逾期次数: " + om6);
            //JsonArray array = data.getAsJsonArray("loanRecords");
            //reason.getItems().put("loanRecords", array.toString());
        }
        if (data.has("zcCreditScore")) {
            StringBuilder stringBuilder = new StringBuilder();
            String score = data.get("zcCreditScore").getAsString();
            stringBuilder.append("宜信分: " + score);
            if (data.has("contractBreakRate")) {
                String breakRate = data.get("contractBreakRate").getAsString();
                stringBuilder.append("<br/>" + "   违约概率估计:" + breakRate);
            }
            //reason.setExplain("宜信分: " + score + "  违约概率估计: " + breakRate);
            hashMap.put("score", stringBuilder);
        }
        return hashMap;
    }


    //--------------添加宜信数据贡献接口
    @SuppressWarnings("unchecked")
    @Override
    public String shareYxData(HttpServletRequest request) {
        HashMap<String, Object> first = new HashMap<>(); //第一级

        //验证 域名
        String checkResult = chcekIpAndServerName(request);
        if (StringUtils.isNotBlank(checkResult)) {
            return checkResult;
        }
        Gson gson = new Gson();
        String returnJson = null;

        Object requestPramsObj = request.getParameter("params");
        log.info("decodeReturnMap = :{}", requestPramsObj);
        if (requestPramsObj == null) {
            first.put("errorCode", "4012");
            first.put("message", "The request parameter has an empty value");
            returnJson = gson.toJson(first);
            return returnJson;
        }

        Map<String, Object> dataMap = null;
        try {
            dataMap = decodeReturnMap(String.valueOf(requestPramsObj));
        } catch (Exception e) {
            log.error("decodeReturnMap error:{}", e);
            first.put("errorCode", "4002");
            first.put("message", "Parameter resolution exception");
            returnJson = gson.toJson(first);
            return returnJson;
        }
        if (dataMap == null) {
            //请求参数 存在空值
            first.put("errorCode", "4012");
            first.put("message", "The request parameter has an empty value");
            returnJson = gson.toJson(first);
            return returnJson;
        }
        String idNumber = (String) dataMap.get("idNo");
        String name = (String) dataMap.get("name");

        Result<List<MbgUserInfo>> userResult = dataWareHouseService.queryUsers(idNumber, null);
        // 判断用户信息，只取第一条
        List<MbgUserInfo> userInfos = userResult.getModel();
        log.info("idNumber:{}", idNumber);
        log.info("MbgUserInfos size :" + (userInfos == null ? 0 : userInfos.size()));
        if (userInfos == null || userInfos.size() == 0) {
            first.put("errorCode", "0001");
            first.put("message", "No such user information");
            returnJson = gson.toJson(first);
            log.info("returnJson:{} ", returnJson);
            return returnJson;
        }
        if (!userInfos.get(0).getRealname().equals(name)) {
            first.put("errorCode", "4009");
            first.put("message", "The user id does not match the name");
            returnJson = gson.toJson(first);
            return returnJson;
        }
        log.info("idNumber ==:{} ", idNumber);
        //需要根据身份证号 查询信息
        Result<List<MbgAssetBorrowOrder>> borrowResult = dataWareHouseService.queryOrdersByIdNumberAndProjectName(idNumber, null);
        Result<List<MbgAssetRepayment>> repaymentResult = dataWareHouseService.selectRepaymentListByIdNumber(idNumber, null);
        log.info("idNumber:" + idNumber + " ,MbgAssetBorrowOrder size : " + (borrowResult.getModel() == null ? 0 : borrowResult.getModel().size()) + " ,MbgAssetRepayment size : " + (repaymentResult.getModel() == null ? 0 : repaymentResult.getModel().size()));
        List<BorrowRepayInfo> borrowRepayInfos = mergeBorrowRepay(borrowResult.getModel(), repaymentResult.getModel());

        if (borrowRepayInfos == null || borrowRepayInfos.isEmpty()) {
            first.put("errorCode", "0001");
            first.put("message", "No such user information");
            returnJson = gson.toJson(first);
            log.info("returnJson :{}", returnJson);
            return returnJson;
        }
        first.put("message", "success");
        first.put("errorCode", "0000");
        // params 下面的数据都要进行加密
        HashMap<String, Object> params = new HashMap<>();
        params.put("tx", "102");
        params.put("version", "v3");
        HashMap<String, Object> data = this.buidlYxReturnParams(borrowRepayInfos);
        params.put("data", data);
        String json = gson.toJson(params);

        first.put("params", params);
        log.info("idNumber =:{}json:{} ", idNumber, json);
        String cr4Str = RSA_1024_V2.encode(json, rc4Key);  //使用CR4秘钥 进行编码
        String urlEncodeStr = URLEncoder.encode(cr4Str);

        //返回报文
        first.put("params", urlEncodeStr);
        returnJson = gson.toJson(first);
        return returnJson;
    }

    //验证 域名
    private String chcekIpAndServerName(HttpServletRequest request) {
        HashMap<String, Object> first = new HashMap<>(); //第一级
        Gson gson = new Gson();
        String returnJson = null;

        //验证 域名
        String serverName = request.getServerName();
        String ip = null;
        log.info("serverName = :{}", serverName);
        log.info("getIpAddr ip = :{}", ip);
        InetAddress ServerIP = null;
        try {
            ServerIP = InetAddress.getByName(serverName);
            ip = ServerIP.getHostAddress();
            log.info("ServerIP :{}", ServerIP.getHostAddress());
        } catch (UnknownHostException e) {
            log.error("shareYxData error:{}", e);
        }
        Set serverNameSet = new HashSet(); //存储域名
        serverNameSet.add("localhost");
        serverNameSet.add("oss.jx-money.com");
        serverNameSet.add("116.62.40.106");
        serverNameSet.add("58.135.80.81");
        serverNameSet.add("106.38.115.194");
        serverNameSet.add("114.113.69.162");
        serverNameSet.add("118.31.128.29");
        serverNameSet.add("127.0.0.1");
        serverNameSet.add("120.55.214.138");
        serverNameSet.add("121.199.129.45");
        if (serverName.equals(ip)) {
            if (!serverNameSet.contains(serverName)) {
                first.put("errorCode", "4107");
                first.put("message", "IP address without permission");
                returnJson = gson.toJson(first);
            }
        } else {
            if (!serverNameSet.contains(ip)) {
                first.put("errorCode", "4107");
                first.put("message", "IP address without permission");
                returnJson = gson.toJson(first);
            }
        }
        return returnJson;
    }

    private List<BorrowRepayInfo> mergeBorrowRepay(List<MbgAssetBorrowOrder> borrowOrders, List<MbgAssetRepayment> repayments) {
        List<BorrowRepayInfo> borrowRepayInfos = new ArrayList<>();
        if (borrowOrders == null || borrowOrders.size() == 0) {
            return borrowRepayInfos;
        }
        borrowOrders.forEach(i -> {
            BorrowRepayInfo borrowRepayInfo = new BorrowRepayInfo();
            borrowRepayInfo.setBorrowOrder(i);
            if (repayments != null && repayments.size() > 0) {
                repayments.forEach(k -> {
                    // 借款的对应借款id与借款编号对应，且马甲名一样时认为是对应的
                    if (i.getId().equals(k.getAssetOrderId()) && i.getProjectName().equals(k.getProjectName())) {
                        borrowRepayInfo.setRepayment(k);
                    }
                });
            }
            borrowRepayInfos.add(borrowRepayInfo);
        });
        return borrowRepayInfos;
    }


    //对传参进行解密,并转换返回map
    public Map<String, Object> decodeReturnMap(String ecodeJsonStr) throws Exception {
        //装换为map
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        Map<String, Object> map = gson.fromJson(ecodeJsonStr, new TypeToken<Map<String, String>>() {
        }.getType());
        String strParams = (String) map.get("params");
        String decodeJsonStr = RC4_128_V2.decode(URLDecoder.decode(strParams, "UTF-8"), rc4Key); //解密内容
        JsonObject jsonObject = (JsonObject) jsonParser.parse(decodeJsonStr);
        //获取data数据
        JsonObject jsonDataObj = jsonObject.getAsJsonObject("data");
        return gson.fromJson(String.valueOf(jsonDataObj), new TypeToken<Map<String, String>>() {
        }.getType());
    }


    //数据封装
    public HashMap<String, Object> buidlYxReturnParams(List<BorrowRepayInfo> borrowRepayInfos) {
        HashMap<String, Object> data = new HashMap<>(); //第三级
        List<HashMap<String, Object>> loanRecordsList = new ArrayList<>();
        for (BorrowRepayInfo borrowRepayInfo : borrowRepayInfos) {
            HashMap<String, Object> map = new HashMap<>();
            String strDate = getShortDateStr(borrowRepayInfo.getBorrowOrder().getLoanTime());
            // 获取借款金额
            String loanMoneyStr = loanMoneyStr(borrowRepayInfo.getBorrowOrder());
            // 借款状态
            String approvalStatusCode = approvalStatusCode(borrowRepayInfo.getBorrowOrder());
            // 逾期情况，repayment
            String overdueMoney = overdueMoney(borrowRepayInfo);
            String overdueStatusStandard = overdueStatusStandard(borrowRepayInfo);
            String repaymentsStatusCode = repaymentsStatusCode(borrowRepayInfo.getBorrowOrder());

            map.put("name", borrowRepayInfo.getBorrowOrder().getRealname());
            map.put("certNo", borrowRepayInfo.getBorrowOrder().getIdNumber());
            map.put("loanDate", strDate); //借款日期
            map.put("periods", "1"); //借款期数默认为1
            map.put("loanAmount", loanMoneyStr); //借款金额
            map.put("loanTypeCode", 21); //借款类型
            map.put("approvalStatusCode", approvalStatusCode); //审批结果码
            map.put("loanStatusCode", repaymentsStatusCode); //还款状态
            //302 逾期情况显示所有字段
            if (repaymentsStatusCode != null && repaymentsStatusCode.equals("302")) {
                map.put("overdueAmount", overdueMoney);//逾期金额
                map.put("overdueTotal", "1"); //逾期次数
                if (overdueStatusStandard != null) {
                    map.put("overdueStatus", overdueStatusStandard); //逾期情况
                }
                //M3+及以上的情况
                Set m3Set = new HashSet();
                m3Set.add("M3+");
                m3Set.add("M4+");
                m3Set.add("M5+");
                m3Set.add("M6+");
                m3Set.add("M4");
                m3Set.add("M5");
                m3Set.add("M6");
                log.info("dd overdueStatusStandard = :{}", overdueStatusStandard);
                log.info("m3Set.contains(overdueStatusStandard) =:{} ", m3Set.contains(overdueStatusStandard));
                if (overdueStatusStandard != null && m3Set.contains(overdueStatusStandard)) {
                    map.put("overdueM3", "1");
                }
                Set m6Set = new HashSet();
                m6Set.add("M6+");
                if (overdueStatusStandard != null && m6Set.contains(overdueStatusStandard)) {
                    map.put("overdueM6", "1");
                }
            }
            loanRecordsList.add(map);
        }
        data.put("loanRecords", loanRecordsList);
        data.put("riskResults", new ArrayList<>());
        return data;
    }

    private String getShortDateStr(String time) {
        if (StringUtils.isBlank(time)) {
            return DateUtil.fyFormatDate(new Date());
        }
        return time.replace("-", "").trim().substring(0, 8);
    }

    //历史逾期总次数
    private String judegOverdueCount(BorrowOrder borrowOrder) {
        Integer status = borrowOrder.getStatus();
        List<Integer> yuqi = new ArrayList<>();
        yuqi.add(-11);
        yuqi.add(-20); //逾期
        if (yuqi.contains(status)) {
            return "1";
        }
        return null;
    }

    //借款金额
    private String loanMoneyStr(MbgAssetBorrowOrder borrowOrder) {
        Integer loanMoney = borrowOrder.getMoneyAmount() / 100;
        if (loanMoney <= 1000) {
            return "(0,1000]";
        } else if (loanMoney > 1000 && loanMoney <= 5000) {
            return "(1000,5000]";
        }
        return "(0,1000]";
    }


    //逾期情况 M的一些标准
    private String overdueStatusStandard(BorrowRepayInfo borrowRepayInfo) {
        if (borrowRepayInfo.getRepayment() == null) {
            return null;
        }
        Integer overdueDays = 0;
        log.info("repayment = :{}", borrowRepayInfo.getRepayment().toString());
        Date realDate = borrowRepayInfo.getRepayment().getRepaymentRealTime(); //实际还款时间
        Integer loanTerm = borrowRepayInfo.getBorrowOrder().getLoanTerm(); //一期 多少天数
        log.info("loanTime =:{} ", borrowRepayInfo.getBorrowOrder().getLoanEndTime());
        try {
            if (realDate != null) {
                overdueDays = DateUtil.daysBetween(DateUtil.getDate(borrowRepayInfo.getBorrowOrder().getLoanEndTime(), "yyyy-MM-dd HH:mm:ss"), realDate);
            } else {
                overdueDays = DateUtil.daysBetween(DateUtil.getDate(borrowRepayInfo.getBorrowOrder().getLoanEndTime(), "yyyy-MM-dd HH:mm:ss"), new Date());
            }
            int shang = overdueDays / 30;
            log.info("逾期天数:{}shang:{} ", overdueDays, shang);
            //7 天为一期
            if (overdueDays % loanTerm == 0) { //整除表示
                switch (shang) {
                    case 0:
                        return "M1";
                    case 1:
                        return "M1";
                    case 2:
                        return "M2";
                    case 3:
                        return "M3";
                    case 4:
                        return "M4";
                    case 5:
                        return "M5";
                    case 6:
                        return "M6";
                }
            } else {
                switch (shang) {
                    case 0:
                        return "M1";
                    case 1:
                        return "M1";
                    case 2:
                        return "M2";
                    case 3:
                        return "M3+";
                    case 4:
                        return "M4";
                    case 5:
                        return "M5";
                    case 6:
                        return "M6+";
                }
            }
            if (shang > 6) {
                return "M6+";
            }
            throw new ParseException(null, 1);
        } catch (ParseException e) {
            log.error("overdueStatusStandard error:{}", e);
        }
        return null;
    }

    //操作逾期金额
    private String overdueMoney(BorrowRepayInfo borrowRepayInfo) {
        if (borrowRepayInfo.getRepayment() == null) {
            return null;
        }
        log.info("repayment =:{}", borrowRepayInfo.getRepayment().toString());
        int overdueDays = 0;
        double overdueRate = 0.03;  //逾期利率
        double allMoney = 0;
        double principal = borrowRepayInfo.getBorrowOrder().getMoneyAmount() / 100;  //总金额 = 本金+利息
        Date realDate = borrowRepayInfo.getRepayment().getRepaymentRealTime(); //实际还款时间
        try {
            if (realDate != null) {
                overdueDays = DateUtil.daysBetween(DateUtil.getDate(borrowRepayInfo.getBorrowOrder().getLoanEndTime(), "yyyy-MM-dd HH:mm:ss"), realDate);
            } else {
                overdueDays = DateUtil.daysBetween(DateUtil.getDate(borrowRepayInfo.getBorrowOrder().getLoanEndTime(), "yyyy-MM-dd HH:mm:ss"), new Date());
            }
        } catch (ParseException e) {
            log.error("overdueMoney error:{}", e);
        }
        //逾期的总金额
        allMoney = principal * overdueDays * overdueRate + principal;
        if (allMoney > 0 && allMoney <= 1000) {
            return "(0,1000]";
        }
        if (allMoney > 1000 && allMoney <= 5000) {
            return "(1000,5000]";
        }
        return "(0,1000]";
    }

    //判断还款的状态
    private String repaymentsStatusCode(MbgAssetBorrowOrder borrowOrder) {
        Integer status = borrowOrder.getStatus();
        //还款状态
        List<Integer> jieQing = new ArrayList<>();
        jieQing.add(34);
        jieQing.add(30); //结清 全部还完
        List<Integer> normal = new ArrayList<>();
        normal.add(23);
        normal.add(21); //正常  部分还清且不逾期
        List<Integer> yuqi = new ArrayList<>();
        yuqi.add(-11);
        yuqi.add(-20); //逾期
        if (jieQing.contains(status)) {
            return "303";
        } else if (normal.contains(status)) {
            return "301";
        } else if (yuqi.contains(status)) {
            return "302";
        }
        return null;
    }


    //判断借款状态
    private String approvalStatusCode(MbgAssetBorrowOrder borrowOrder) {
        Integer status = borrowOrder.getStatus();
        //状态：0:待初审(待机审);-3:初审驳回;1:初审通过;-4:复审驳回;20:复审通过,待放款;-5:放款驳回;22:放款中;-10:放款失败;21已放款，还款中;23:部分还款;30:已还款;-11:已逾期;-20:已坏账，34逾期已还款；
        //放款
        List<Integer> loan = new ArrayList();
        loan.add(21);
        loan.add(23);
        loan.add(30);
        loan.add(-11);
        loan.add(-20);
        loan.add(34);
        //拒待
        List<Integer> refuse = new ArrayList();
        refuse.add(-3);
        refuse.add(-4);
        refuse.add(-5);
        //审核中
        List<Integer> underReview = new ArrayList();
        underReview.add(0);
        underReview.add(1);
        underReview.add(-10);
        underReview.add(20);
        underReview.add(22);
        String result = null;
        if (loan.contains(status)) {
            return "202";
        } else if (refuse.contains(status)) {
            return "203";
        } else if (underReview.contains(status)) {
            return "201";
        }
        return null;
    }
}

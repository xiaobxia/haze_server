package com.info.risk.utils.autorisk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.info.risk.pojo.newrisk.ShuJuMoHeVo;
import org.apache.commons.lang3.StringUtils;
import com.info.risk.pojo.Advice;
import com.info.risk.pojo.CreditReport;
import com.info.risk.pojo.Reason;
import com.info.risk.pojo.Supplier;

import java.util.*;

/**
 * @author xiefei
 * @date 2018/05/21
 */
public class RiskCreditReasonUtil {

    /**
     * 获取硬指标意见，及硬指标意见原因；老用户意见，及老用户意见原因
     *
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getRiskCreditReasonMessage(ShuJuMoHeVo shuJuMoHeVo, CreditReport creditReport,boolean autoSjmhFlag) {


        //--------------------老用户------------------------//
        Map<String, Object> stringObjectMessage = parseJXOC(creditReport);

        Map<String, String> resultMap = new HashMap<>();

        if (stringObjectMessage != null) {
            Advice oldCustomerAdvice = (Advice) stringObjectMessage.get("advice");
            List<String> oldCustomerList = (List<String>) stringObjectMessage.get("adviceReasonList");

            StringBuffer oldCustomerAdviceReason = new StringBuffer();
            for (String adviceReason : oldCustomerList) {
                oldCustomerAdviceReason.append(adviceReason).append(";");
            }
            //老用户指标建议
            resultMap.put("oldCustomerAdvice", oldCustomerAdvice + "");
            //老用户指标命中原因
            resultMap.put("oldCustomerAdviceReason", oldCustomerAdviceReason.toString());
        }


        //--------------------硬指标------------------------//
        Map<String, Object> autoMessage = RiskCreditReasonUtil.parseAuto(creditReport);
        Map<String, Object> autoLoanMessage = RiskCreditReasonUtil.parseAutoLoan(creditReport);

        List<Advice> adviceList = new ArrayList<>();
        List<String> inflexibleMessageList = new ArrayList<>();

        if (autoMessage != null) {
            adviceList.add((Advice) autoMessage.get("advice"));
            inflexibleMessageList.addAll((List<String>) autoMessage.get("adviceReasonList"));
        }

        if (autoLoanMessage != null) {
            adviceList.add((Advice) autoLoanMessage.get("advice"));
            inflexibleMessageList.addAll((List<String>) autoLoanMessage.get("adviceReasonList"));
        }

        if (shuJuMoHeVo != null) {
            if (autoSjmhFlag){
                Map<String, Object> shuJuMoHeMessage = RiskCreditReasonUtil.parseShuJuMoHe(shuJuMoHeVo);
                adviceList.add((Advice) shuJuMoHeMessage.get("advice"));
                inflexibleMessageList.addAll((List<String>) shuJuMoHeMessage.get("adviceReasonList"));
            }

            if (autoMessage == null || autoLoanMessage == null) {
                inflexibleMessageList.add("订单的风控数据结构与当前不一致");
            }
        } else {
            adviceList.add(Advice.REVIEW);

            if (autoMessage == null || autoLoanMessage == null) {
                inflexibleMessageList.add("订单的风控数据结构与当前不一致");
            } else {
                inflexibleMessageList.add("运营商数据缺失");
            }
        }

        StringBuffer inflexibleAdviceReason = new StringBuffer();
        for (String adviceReason : inflexibleMessageList) {
            inflexibleAdviceReason.append(adviceReason).append(";");
        }

        //硬指标建议
        resultMap.put("inflexibleAdvice", evaluatorStrategy(adviceList) + "");
        //硬指标命中原因
        resultMap.put("inflexibleAdviceReason", inflexibleAdviceReason.toString());

        return resultMap;
    }


    private static Map<String, Object> parseJXOC(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.JXOC.toString());

        if (reason == null) {
            return null;
        }

        List<String> adviceReasonList = new ArrayList<>();

        reason.getItems().forEach((k, v) -> {
            adviceReasonList.add(k);
        });

        Map<String, Object> resMap = new HashMap<>();
        //产生建议的理由
        resMap.put("adviceReasonList", adviceReasonList);
        //建议
        resMap.put("advice", reason.getAdvice());
        return resMap;
    }

    /**
     * 解析数聚魔盒的数据
     *
     * @param shuJuMoHeVo
     * @return
     */
    private static Map<String, Object> parseShuJuMoHe(ShuJuMoHeVo shuJuMoHeVo) {
        if (shuJuMoHeVo == null) {
            return null;
        }

        List<String> adviceReasonList = shuJuMoHeVo.getAutoList();
        List<Advice> sjmhAdviceList = shuJuMoHeVo.getAdviceList();

        Map<String, Object> resMap = new HashMap<>();
        //产生建议的理由
        resMap.put("adviceReasonList", adviceReasonList);
        //建议
        resMap.put("advice", evaluatorStrategy(sjmhAdviceList));
        return resMap;
    }

    /**
     * 解析AUTO的数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseAuto(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.AUTO.toString());

        if (reason == null) {
            return null;
        }

        List<String> adviceReasonList = new ArrayList<>();

        reason.getItems().forEach((k, v) -> {
            adviceReasonList.add(k);
        });

        Map<String, Object> resMap = new HashMap<>();
        //产生建议的理由
        resMap.put("adviceReasonList", adviceReasonList);
        //建议
        resMap.put("advice", reason.getAdvice());
        return resMap;
    }

    /**
     * 解析AUTOLOAN的数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseAutoLoan(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.AUTOLOAN.toString());

        if (reason == null) {
            return null;
        }

        List<String> adviceReasonList = new ArrayList<>();

        reason.getItems().forEach((k, v) -> {
            adviceReasonList.add(k);
        });

        Map<String, Object> resMap = new HashMap<>();
        //产生建议的理由
        resMap.put("adviceReasonList", adviceReasonList);
        //建议
        resMap.put("advice", reason.getAdvice());
        return resMap;
    }

    /**
     * 获取全部风控报文的解析数据
     *
     * @param creditReport
     * @return
     */
    public static Map<Supplier, Map<String, Object>> getReasonMessage(CreditReport creditReport) {
        Map<Supplier, Map<String, Object>> res = new HashMap<>();
        res.put(Supplier.TD, pasreTd(creditReport));
        res.put(Supplier.ZZCFQZ, parseZzcFqz(creditReport));
        res.put(Supplier.BQS, parseBqs(creditReport));
        res.put(Supplier.ZZCHMD, parseZzcBlackList(creditReport));
        res.put(Supplier.ZCAF, parseZcaf(creditReport));
        res.put(Supplier.ZMF, parseZmscore(creditReport));
        res.put(Supplier.ZMHYGZ, parseZmWatchList(creditReport));
        res.put(Supplier.AUTOLOAN, parseAutoLoan(creditReport));
        res.put(Supplier.JXOC, parseJXOCRisk(creditReport));
        return res;
    }


    /**
     * 解析同盾报文
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> pasreTd(CreditReport creditReport) {

        Reason reason = getSupplierReson(creditReport, Supplier.TD.toString());
        if (reason == null) {
            return null;
        }
        //需要显示信息的数据集合
        Set<String> set = new HashSet<String>();
        set.add("7天内申请人在多个平台申请借款");
        set.add("1个月内申请人在多个平台申请借款");
        set.add("3个月内申请人在多个平台申请借款");
        set.add("3个月内申请人在多个平台被放款_不包含本合作方");
        //存储最后的数据map
        Map<String, Object> storeData = new HashMap<>();
        //存储反欺诈列表
        List<String> tdFqzList = new ArrayList<>();

        JsonParser parser = new JsonParser();
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeData.put("checkResult", reason.getAdvice().toString());
        storeData.put("explain", reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("同盾分")) {
                storeData.put(entry.getKey(), entry.getValue());
                continue;
            }
            if (set.contains(entry.getKey())) {
                JsonObject root = parser.parse(entry.getValue()).getAsJsonObject();
                if (root != null) {
                    JsonArray jsonArray = root.getAsJsonArray("association_partner_details").getAsJsonArray();
                    Map<String, Integer> map = new HashMap<>();
                    int size = jsonArray.size();
                    int count = 0;
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            String key = jsonObject.has("industryDisplayName") ? jsonObject.get("industryDisplayName").getAsString() : "无";
                            int value = jsonObject.has("count") && StringUtils.isNumeric(jsonObject.get("count").getAsString()) ? jsonObject.get("count").getAsInt() : 0;
                            count += value;
                            map.put(key, value);
                        }
                        storeData.put(entry.getKey(), map);
                        storeData.put(entry.getKey() + "次数", count);
                        continue;
                    }
                }
            }
            //存储发欺诈数据list
            if (entry.getKey().equals("auto") || entry.getKey().equals("同盾报文建议")) {
                continue;
            }
            JsonObject root = parser.parse(entry.getValue()).getAsJsonObject();
            String rankMsg = null;
            try {
                if (rankMsg == null) {
                    rankMsg = root.get("result").getAsString();
                }
            } catch (Exception e) {
            }
            try {
                if (rankMsg == null) {
                    rankMsg = root.get("discredit_times").getAsString();
                    if (rankMsg != null) {
                        JsonArray overdueDetails = root.get("overdue_details").getAsJsonArray();
                        for (JsonElement overdueDetail : overdueDetails) {
                            JsonObject item = overdueDetail.getAsJsonObject();
                            if (overdueDetails != null) {
                                String overdueTime = item.get("overdue_time").getAsString();
                                if (overdueTime != null) {
                                    rankMsg = rankMsg + "，逾期发生时间：" + overdueTime;
                                }
                                String overdueAmountRange = item.get("overdue_amount_range").getAsString();
                                if (overdueAmountRange != null) {
                                    rankMsg = rankMsg + "，逾期金额区间：" + overdueAmountRange;
                                }
                                String overdueDayRange = item.get("overdue_day_range").getAsString();
                                if (overdueDayRange != null) {
                                    rankMsg = rankMsg + "，逾期天数区间：" + overdueDayRange;
                                }
                                String overdueCount = item.get("overdue_count").getAsString();
                                if (overdueCount != null) {
                                    rankMsg = rankMsg + "，逾期笔数：" + overdueCount;
                                }
                            }
                        }

                    }
                }
            } catch (Exception e) {
            }
            if (rankMsg != null) {
                tdFqzList.add(entry.getKey() + "，出现次数：" + rankMsg);
            } else {
                tdFqzList.add(entry.getKey());
            }
        }
        if (tdFqzList.size() > 0) {
            storeData.put("tdFqzList", tdFqzList);
        }
        return storeData;
    }


    /**
     * 解析中智诚反欺诈数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseZzcFqz(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.ZZCFQZ.toString());
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        List<String> riskList = new ArrayList<>();
        storeDataMap.put("checkResult", reason.getAdvice().toString());
        storeDataMap.put("explain", reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            riskList.add(entry.getKey());
        }
        storeDataMap.put("riskList", riskList);
        return storeDataMap;
    }

    /**
     * 解析白骑士报文数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseBqs(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.BQS.toString());
        Map<String, Object> storeDataMap = new HashMap<>();
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult", reason.getAdvice().toString());
        storeDataMap.put("explain", reason.getExplain());
        Map<String, Object> detailMap = new HashMap<>(); // 存放白骑士具体原因


        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            Map<String, String> duoTouMap = new HashMap<>();
            if (!entry.getKey().equals("auto")) {
                if (entry.getKey().equals("白骑士分")) {
                    duoTouMap.put("白骑士分", entry.getValue());
                    detailMap.put(entry.getKey(), duoTouMap);
                    continue;
                }
                JsonObject jsonObject = jsonParser.parse(entry.getValue()).getAsJsonObject();
                JsonArray jsonObjectHitRulesArray = jsonObject.has("hitRules") ? jsonObject.get("hitRules").getAsJsonArray() : null;
                if (jsonObjectHitRulesArray != null) {
                    for (int i = 0; i < jsonObjectHitRulesArray.size(); i++) {
                        JsonObject jsonObjectHitRules = jsonObjectHitRulesArray.get(i).getAsJsonObject();
                        String memory = jsonObjectHitRules.has("memo") ? jsonObjectHitRules.get("memo").getAsString() : "无";
                        String ruleName = jsonObjectHitRules.has("ruleName") ? jsonObjectHitRules.get("ruleName").getAsString() : "无";
                        if (!ruleName.equals("无")) {
                            duoTouMap.put(ruleName, memory);
                        }
                    }
                    detailMap.put(entry.getKey(), duoTouMap);
                }
            }
        }
        storeDataMap.put("detailMap", detailMap);
        return storeDataMap;
    }

    /**
     * 解析中智诚黑名单数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseZzcBlackList(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.ZZCHMD.toString());
        if (reason == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeDataMap.put("checkResult", reason.getAdvice().toString());
        storeDataMap.put("explain", reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        List<Map<String, String>> blackList = new ArrayList<>();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            if (key.equals("命中的黑名单所属的上报机构的数量") || key.equals("命中的黑名单的数量")) {
                storeDataMap.put(entry.getKey(), entry.getValue());
                continue;
            }
            if (key.equals("黑名单列表")) {
                Map<String, String> blackMap = new HashMap<>();
                JsonArray jsonArray = jsonParser.parse(entry.getValue()).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    if (jsonObject.has("name")) {
                        blackMap.put("命中的黑名单里的人的姓名", jsonObject.get("name").getAsString());
                    }
                    if (jsonObject.has("pid")) {
                        blackMap.put("命中的黑名单里的人的身份证", jsonObject.get("pid").getAsString());
                    }
                    if (jsonObject.has("phone")) {
                        blackMap.put("命中的黑名单里的人的手机号", jsonObject.get("phone").getAsString());
                    }
                    if (jsonObject.has("loan_type")) {
                        blackMap.put("黑名单申请贷款的类型", jsonObject.get("loan_type").getAsString());
                    }
                    if (jsonObject.has("confirm_type")) {
                        blackMap.put("黑名单被确认的类型", jsonObject.get("confirm_type").getAsString());
                    }
                    if (jsonObject.has("confirm_details")) {
                        blackMap.put("黑名单的确认细节", jsonObject.get("confirm_details").getAsString());
                    }
                    if (jsonObject.has("applied_at")) {
                        blackMap.put("黑名单申请贷款的时间", jsonObject.get("applied_at").getAsString());
                    }
                    if (jsonObject.has("confirmed_at")) {
                        blackMap.put("申请被确认为黑名单的时间", jsonObject.get("confirmed_at").getAsString());
                    }
                    if (jsonObject.has("status")) {
                        blackMap.put("命中的黑名单状态", jsonObject.get("status").getAsString());
                    }
                }
                blackList.add(blackMap);
                storeDataMap.put("blackList", blackList);
            }
        }
        return storeDataMap;
    }

    /**
     * 解析宜信报文数据
     *
     * @param creditReport
     * @return 返回解析后的报文
     */
    private static Map<String, Object> parseZcaf(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.ZCAF.toString());
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult", reason.getAdvice().toString());
        storeDataMap.put("explain", reason.getExplain());
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String value = entry.getValue();
            if (entry.getKey().equals("queryHistory")) {
                JsonArray records = jsonParser.parse(value).getAsJsonArray();
                String queryHistoryStr = String.format("历史查询次数：%d", records.size());
                storeDataMap.put("queryHistory", queryHistoryStr); //历史查询次数
                continue;
            }

            if (entry.getKey().equals("queryStatistics")) {
                JsonObject object = jsonParser.parse(value).getAsJsonObject();
                int timesByOtherOrg = object.has("timesByOtherOrg") && StringUtils.isNumeric(object.get("timesByOtherOrg").getAsString()) ? object.get("timesByOtherOrg").getAsInt() : 0;
                int otherOrgCount = object.has("otherOrgCount") && StringUtils.isNumeric(object.get("otherOrgCount").getAsString()) ? object.get("otherOrgCount").getAsInt() : 0;
                int timesByCurrentOrg = object.has("timesByCurrentOrg") && StringUtils.isNumeric(object.get("timesByCurrentOrg").getAsString()) ? object.get("timesByCurrentOrg").getAsInt() : 0;
                String content = String.format("其他机构查询次数: %d, 其他查询机构数 %d, 本机构查询次数 %d", timesByOtherOrg, otherOrgCount, timesByCurrentOrg);
                storeDataMap.put("content", content);
                continue;
            }

            if (entry.getKey().equals("riskResults")) {
                StringBuilder builder = new StringBuilder();
                JsonArray records = jsonParser.parse(value).getAsJsonArray();
                if (records.size() > 0) {
                    for (JsonElement element : records) {
                        JsonObject object = element.getAsJsonObject();
                        if (object.has("riskDetail")) {
                            builder.append(object.get("riskDetail"));
                            builder.append(", ");
                        }
                    }
                    storeDataMap.put("riskResults", builder.substring(0, builder.length() - 2).toString());
                    continue;
                }
            }
            if (entry.getKey().equals("loanRecords")) {
                JsonArray records = jsonParser.parse(value).getAsJsonArray();
                int ot = 0;
                //float oa = 0;
                int om3 = 0;
                int om6 = 0;
                if (records.size() > 0) {
                    for (JsonElement element : records) {
                        int overdueTotal = 0, overdueM3 = 0, overdueM6 = 0;
                        JsonObject object = element.getAsJsonObject();
                        boolean aa = StringUtils.isNumeric(object.get("overdueTotal").getAsString());
                        overdueTotal = object.has("overdueTotal") && StringUtils.isNumeric(object.get("overdueTotal").getAsString()) ? object.get("overdueTotal").getAsInt() : 0;
                        overdueM3 = object.has("overdueM3") && StringUtils.isNumeric(object.get("overdueM3").getAsString()) ? object.get("overdueM3").getAsInt() : 0;
                        overdueM6 = object.has("overdueM6") && StringUtils.isNumeric(object.get("overdueM6").getAsString()) ? object.get("overdueM6").getAsInt() : 0;
                        ot += overdueTotal;
                        om3 += overdueM3;
                        om6 += overdueM6;
                    }
                    storeDataMap.put("loanRecords", "逾期总数: " + ot + ",  M3逾期次数: " + om3 + ",  M6逾期次数: " + om6);
                    continue;
                }
            }

            if (entry.getKey().equals("zcCreditScore")) {
                String score = entry.getValue();
                stringBuilder.append("宜信分: " + score);
                continue;
            }

            if (entry.getKey().equals("contractBreakRate")) {
                String breakRate = entry.getValue();
                stringBuilder.append("<br/>" + "   违约概率估计:" + breakRate);
                continue;
            }
        }
        if (stringBuilder != null && StringUtils.isNotBlank(stringBuilder)) {
            storeDataMap.put("score", stringBuilder);
        }
        return storeDataMap;
    }

    /**
     * 解析芝麻分报文数据
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseZmscore(CreditReport creditReport) {
        Reason zmScoreReason = getSupplierReson(creditReport, Supplier.ZMF.toString());
        if (zmScoreReason == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        Map<String, String> allItemsMap = zmScoreReason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeDataMap.put("explain", zmScoreReason.getExplain());
        JsonParser jsonParser = new JsonParser();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("芝麻分")) {
                storeDataMap.put(entry.getKey(), entry.getValue());
            }
        }
        return storeDataMap;
    }

    /**
     * 芝麻行业关注
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseZmWatchList(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.ZMHYGZ.toString());
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult", reason.getAdvice().toString());
        storeDataMap.put("explain", reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("details")) {
                continue;
            }
            if (entry.getKey().equals("是否命中黑名单")) {
                if (entry.getValue().equals("true")) {
                    storeDataMap.put("blackFlag", "是");
                } else {
                    storeDataMap.put("blackFlag", "否");
                }
            } else {
                storeDataMap.put(entry.getKey(), entry.getValue());
            }

        }
        return storeDataMap;
    }

    /**
     * 小鱼儿老用户
     *
     * @param creditReport
     * @return
     */
    private static Map<String, Object> parseJXOCRisk(CreditReport creditReport) {
        Reason reason = getSupplierReson(creditReport, Supplier.JXOC.toString());
        Map<String, Object> storeData = new HashMap<>();
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        String jxocItem = "";
        storeData.put("checkResult", reason.getAdvice());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            jxocItem += (entry.getKey() + " | ");
        }
        storeData.put("jxocItem", jxocItem);
        return storeData;
    }

    /**
     * 根据各自的提供者 返回Reasn
     *
     * @param creditReport
     * @param supplier     reason提供者
     * @return
     */
    private static Reason getSupplierReson(CreditReport creditReport, String supplier) {
        if (creditReport == null) {
            return null;
        }
        Set<Reason> allResonSet = creditReport.getReasons();
        Reason reasonTd = null;
        for (Reason reason : allResonSet) {
            if (reason.getSupplier().equals(supplier)) {
                reasonTd = reason;
                break;
            }
        }
        return reasonTd;
    }


    /**
     * 评估器
     *
     * @param reasons
     * @return
     */
    public static Advice evaluatorStrategy(List<Advice> reasons) {
        if (reasons != null && reasons.contains(Advice.REJECT)) {
            return Advice.REJECT;
        }
        if (reasons != null && reasons.contains(Advice.REVIEW)) {
            return Advice.REVIEW;
        }
        return Advice.PASS;
    }
}

package com.info.risk.service;

import com.google.gson.*;
import com.info.risk.pojo.*;
import com.info.risk.pojo.newrisk.ShowRisk;
import com.info.risk.pojo.newrisk.ShuJuMoHeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutoService implements IAutoRiskService {

    @Override
    public Map<String, Object> getRiskData(CreditReport creditReport, String supplier) {
        Map<String,Object> dataMap = null;
        if (supplier.equals(Supplier.TD.toString())) {
            dataMap = pasreTd(creditReport,supplier);
        }
        if (supplier.equals(Supplier.ZZCFQZ.toString())) {
            dataMap = parseZzcFqz(creditReport,supplier);
        }
        if (supplier.equals(Supplier.BQS.toString())) {
            dataMap = parseBqs(creditReport,supplier);
        }
        if (supplier.equals(Supplier.ZZCHMD.toString())) {
            dataMap = parseZzcBlackList(creditReport,supplier);
        }
        if (supplier.equals(Supplier.ZCAF.toString())) {
            dataMap = parseZcaf(creditReport,supplier);
        }
        if (supplier.equals(Supplier.ZMF.toString())) {
            dataMap = parseZmscore(creditReport,supplier);
        }
        if (supplier.equals(Supplier.ZMHYGZ.toString())) {
            dataMap = parseZmWatchList(creditReport,supplier);
        }
        if (supplier.equals(Supplier.AUTOLOAN.toString())) {
            dataMap = parseAutoLoan(creditReport,supplier);
        }
        if (supplier.equals(Supplier.JXOC.toString())) {
            dataMap = parseJXOCRisk(creditReport,supplier);
        }
        return dataMap;
    }


    @Override
    public ShowRisk getShowRiskFromSupplier(List<Supplier> supplierList, CreditReport creditReport) {
        ShowRisk showRisk = new ShowRisk();
        //对应auto
        List<Supplier> autoSupplier = new ArrayList<>();
        autoSupplier.add(Supplier.AUTO);
        //对应Auto+autoLoan
        List<Supplier> autoAndAutoLoanSupplier = new ArrayList<>();
        autoAndAutoLoanSupplier.add(Supplier.AUTO);
        autoAndAutoLoanSupplier.add(Supplier.AUTOLOAN);
        //对应的jxoc
        List<Supplier> jxOcSupplier = new ArrayList<>();
        jxOcSupplier.add(Supplier.JXOC);

        if (supplierList.containsAll(jxOcSupplier)) {
            showRisk = getJxOc(creditReport);
        }

        if (supplierList.containsAll(autoAndAutoLoanSupplier)) {
            showRisk = getAutoAndAutoLoan(creditReport);
        }

        if (supplierList.containsAll(autoSupplier) && supplierList.size() == 1) {
            showRisk = getAuto(creditReport);
        }
        return showRisk;
    }

    //小鱼儿老用户
    private Map<String,Object> parseJXOCRisk(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport,supplier);
        Map<String,Object> storeData = new HashMap<>();
        if (reason == null) {
            return null;
        }
        Map<String,String> allItemsMap = reason.getItems();
//        List<String> list = new ArrayList<>();
        String jxocItem = "";
        storeData.put("checkResult",reason.getAdvice());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
//            list.add(entry.getKey());
            jxocItem += (entry.getKey()+" | ");
        }
        storeData.put("jxocItem",jxocItem);
        return storeData;
    }


    /**
     * auto对应 机审评估
     * @param creditReport
     * @return
     */
    private ShowRisk getAuto(CreditReport creditReport){
        ShowRisk showRisk = new ShowRisk(); //将风控信息展示到页面上
        Reason reason = this.getSupplierReson(creditReport, Supplier.AUTO.toString());
        if (reason == null) {
            return showRisk;
        }
        Map<String,String> allItemsMap = reason.getItems();
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,String> entry = entries.next();
            list.add(entry.getKey());
        }
        showRisk.setAdvice(reason.getAdvice());
        showRisk.setItemsList(list);
        return showRisk;
    }

    /**
     * 机审复审建议
     * auto+autoloan
     */
    private ShowRisk getAutoAndAutoLoan(CreditReport creditReport){
        ShowRisk showRisk = new ShowRisk();
        ShowRisk showRiskAuto = getAuto(creditReport);
        //现在解析AUTOLOAN
        Reason reason = this.getSupplierReson(creditReport, Supplier.AUTOLOAN.toString());
        if (reason == null) {
            return showRisk;
        }
        Map<String,String> allItemsMap = reason.getItems();
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,String> entry = entries.next();
            list.add(entry.getKey());
        }
        List<Advice> adviceList = new ArrayList<>();
        adviceList.add(showRiskAuto.getAdvice());
        adviceList.add(reason.getAdvice());
        //汇总最终的建议
        Advice adviceLast = evaluatorStrategy(adviceList);
        //汇总最终的建议
        if (showRiskAuto.getItemsList() != null) {
            list.addAll(showRiskAuto.getItemsList());
        }
        showRisk.setItemsList(list);
        showRisk.setAdvice(adviceLast);
        return showRisk;
    }

    public ShowRisk getJxOc(CreditReport creditReport){
        ShowRisk showRisk = new ShowRisk();
        //现在解析JXOC
        Reason reason = this.getSupplierReson(creditReport, Supplier.JXOC.toString());
        if (reason == null) {
            return null;
        }
        Map<String,String> allItemsMap = reason.getItems();
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,String> entry = entries.next();
            list.add(entry.getKey());
        }
        showRisk.setAdvice(reason.getAdvice());
        showRisk.setItemsList(list);
        return showRisk;
    }

    /**
     * 评估器
     */
    private Advice evaluatorStrategy(List<Advice> reasons) {
        if (reasons != null && reasons.contains(Advice.REJECT)) {
            return Advice.REJECT;
        }
        if (reasons != null && reasons.contains(Advice.REVIEW)) {
            return Advice.REVIEW;
        }
        return Advice.PASS;
    }


    /**
     * 解析数据墨盒
     */
    @Override
    public ShuJuMoHeVo getShuJuMoHe(RiskOrders riskOrders) {
        ShuJuMoHeVo shuJuMoHeVo = null;
        //查询risk_orders中存储的风控信息
        String jsonData = riskOrders.getReturnParams();
        Gson gson = new Gson();
        if (jsonData == null) {
            return shuJuMoHeVo;
        }
        CreditReport creditReport = gson.fromJson(jsonData,CreditReport.class);
        if (creditReport == null) {
            return shuJuMoHeVo;
        }
        Reason reason = this.getSupplierReson(creditReport, "SJMH");
        if (reason == null) {
            return shuJuMoHeVo;
        }
        Map<String,String> allItemsMap = reason.getItems();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,String> entry = entries.next();
            if (entry.getKey().equals("shuJuMoHeVo")) {
                shuJuMoHeVo = gson.fromJson(entry.getValue(),ShuJuMoHeVo.class);
            }
        }
        return shuJuMoHeVo;
    }




    /**
     *
     * @param supplier reason提供者
     * @return 根据各自的提供者 返回Reasn
     */
    private Reason getSupplierReson(CreditReport creditReport, String supplier){
        if (creditReport == null) {
            return null;
        }
        Set<Reason> allResonSet  = creditReport.getReasons();
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
     * 解析supplier 为AUTOLOAN的数据
     */
    private Map<String,Object> parseAutoLoan(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport,supplier);
        Map<String,Object> storeData = new HashMap<>();
        if (reason == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Map<String,String> allItemsMap = reason.getItems();
        storeData.put("checkResult",reason.getAdvice());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            list.add(entry.getKey());
        }
        storeData.put("autoLoanData",list);
        return storeData;
    }





    /**
     *  解析同盾报文
     */
    private Map<String,Object> pasreTd(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport,supplier);
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
        Map<String,Object> storeData = new HashMap<>();
        //存储反欺诈列表
        List<String> tdFqzList = new ArrayList<>();

        JsonParser parser = new JsonParser();
        Map<String,String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeData.put("checkResult",reason.getAdvice().toString());
        storeData.put("explain",reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("同盾分")) {
                storeData.put(entry.getKey(),entry.getValue());
                continue;
            }
            if (set.contains(entry.getKey())) {
                JsonObject root = parser.parse(entry.getValue()).getAsJsonObject();
                if (root != null) {
                    JsonArray jsonArray = root.getAsJsonArray("association_partner_details").getAsJsonArray();
                    Map<String,Integer> map = new HashMap<>();
                    int size = jsonArray.size();
                    int count = 0;
                    if (size > 0) {
                        for (int i = 0;i<size;i++) {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            String key = jsonObject.has("industryDisplayName")?jsonObject.get("industryDisplayName").getAsString():"无";
                            int value = jsonObject.has("count") && StringUtils.isNumeric(jsonObject.get("count").getAsString())?jsonObject.get("count").getAsInt():0;
                            count += value;
                            map.put(key,value);
                        }
                        storeData.put(entry.getKey(),map);
                        storeData.put(entry.getKey()+"次数",count);
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
                if (rankMsg == null){
                    rankMsg = root.get("result").getAsString();
                }
            } catch (Exception e) {
            }
            try {
                if (rankMsg == null){
                    rankMsg = root.get("discredit_times").getAsString();
                    if (rankMsg!=null){
                        JsonArray overdueDetails = root.get("overdue_details").getAsJsonArray();
                        for (JsonElement overdueDetail : overdueDetails) {
                            JsonObject item = overdueDetail.getAsJsonObject();
                            if (overdueDetails!=null){
                                String overdueTime = item.get("overdue_time").getAsString();
                                if (overdueTime!=null){
                                    rankMsg = rankMsg + "，逾期发生时间："+ overdueTime;
                                }
                                String overdueAmountRange = item.get("overdue_amount_range").getAsString();
                                if (overdueAmountRange!=null){
                                    rankMsg = rankMsg + "，逾期金额区间："+ overdueAmountRange;
                                }
                                String overdueDayRange = item.get("overdue_day_range").getAsString();
                                if (overdueDayRange!=null){
                                    rankMsg = rankMsg + "，逾期天数区间："+ overdueDayRange;
                                }
                                String overdueCount = item.get("overdue_count").getAsString();
                                if (overdueCount!=null){
                                    rankMsg = rankMsg + "，逾期笔数："+ overdueCount;
                                }
                            }
                        }

                    }
                }
            } catch (Exception e) {
            }
            if (rankMsg!=null){
                tdFqzList.add(entry.getKey()+"，出现次数："+rankMsg);
            }else {
                tdFqzList.add(entry.getKey());
            }
//            logger.info("key = " + entry.getKey() +" value = " + entry.getValue());
        }
//        logger.info("pasreTd  tdFqzList = " + String.valueOf(tdFqzList));
        if (tdFqzList.size() > 0) {
            storeData.put("tdFqzList",tdFqzList);
        }
//        logger.info("pasreTd storeData = " + storeData.toString());
        return storeData;
    }


    /**
     * 解析白骑士报文数据
     * @param creditReport
     * @param supplier
     * @return
     */
    private Map<String,Object> parseBqs(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport,supplier);
        Map<String,Object> storeDataMap = new HashMap<>();
        if (reason == null) {
            return null;
        }
        Map<String,String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
//        logger.info("bqs allItemsMap = " + allItemsMap.toString());
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult",reason.getAdvice().toString());
        storeDataMap.put("explain",reason.getExplain());
        Map<String,Object> detailMap = new HashMap<>(); // 存放白骑士具体原因


        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            Map<String, String> duoTouMap = new HashMap<>();
            if (!entry.getKey().equals("auto")) {
                if (entry.getKey().equals("欺诈信息验证")) {
                    continue;
                }
                JsonObject jsonObject = jsonParser.parse(entry.getValue()).getAsJsonObject();
                JsonArray jsonObjectHitRulesArray = jsonObject.has("hitRules") ? jsonObject.get("hitRules").getAsJsonArray():null;
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
        storeDataMap.put("detailMap",detailMap);
//        logger.info("bqs = storeDateMap = " + storeDataMap.toString());
        return storeDataMap;
    }


    /**
     *  解析芝麻分报文数据
     */
    private Map<String,Object> parseZmscore(CreditReport creditReport,String supplier){
        Reason zmScoreReason = this.getSupplierReson(creditReport,supplier);
        if (zmScoreReason == null) {
            return null;
        }
        Map<String,Object> storeDataMap = new HashMap<>();
        Map<String,String> allItemsMap = zmScoreReason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeDataMap.put("explain",zmScoreReason.getExplain());
//        logger.info("zmscore allItemsMap = " + allItemsMap.toString());
        JsonParser jsonParser = new JsonParser();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("芝麻分")) {
                storeDataMap.put(entry.getKey(),entry.getValue());
            }
//            logger.info("zmscore key = " + entry.getKey() +"  value = " + entry.getValue());
        }
//        logger.info("zmscore storeDataMap = " + storeDataMap.toString());
        return storeDataMap;
    }

    public Map<String,Object> parseZmWatchList(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport,supplier);
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult",reason.getAdvice().toString());
        storeDataMap.put("explain",reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals("details")) {
                continue;
            }
            if (entry.getKey().equals("是否命中黑名单")) {
                if (entry.getValue().equals("true")) {
                    storeDataMap.put("blackFlag","是");
                } else {
                    storeDataMap.put("blackFlag","否");
                }
            } else {
                storeDataMap.put(entry.getKey(),entry.getValue());
            }

        }
//        logger.info("parseZmWatchList storeDataMap = " +storeDataMap.toString());
        return storeDataMap;
    }
    /**
     * 解析宜信报文数据
     * @param supplier 报文提供者
     * @return 返回解析后的报文
     */
    public Map<String,Object> parseZcaf(CreditReport creditReport,String supplier) {
        Reason reason = this.getSupplierReson(creditReport, supplier);
        if (reason == null) {
            return null;
        }
        Map<String, String> allItemsMap = reason.getItems();
        if (allItemsMap == null ) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        storeDataMap.put("checkResult",reason.getAdvice().toString());
        storeDataMap.put("explain",reason.getExplain());
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String value = entry.getValue();
//            logger.info("zcaf value = " + value +"  zcaf key = "+entry.getKey());
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

            if(entry.getKey().equals("zcCreditScore")) {
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
//        logger.info("zcaf  storeDataMap = " + storeDataMap.toString());
        return storeDataMap;
    }


    /**
     * 解析中智诚反欺诈数据
     */
    private Map<String,Object> parseZzcFqz(CreditReport creditReport,String supplier){
        Reason reason = this.getSupplierReson(creditReport, supplier);
        if (reason == null) {
            return null;
        }
        Map<String,String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        Map<String,Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        List<String> riskList = new ArrayList<>();
        storeDataMap.put("checkResult",reason.getAdvice().toString());
        storeDataMap.put("explain",reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            riskList.add(entry.getKey());
        }
        storeDataMap.put("riskList",riskList);
//        logger.info("zzcFqz storeDataMap = " + storeDataMap.toString());
        return storeDataMap;
    }

    /**
     * 解析中智诚黑名单数据
     */
    public Map<String,Object> parseZzcBlackList (CreditReport creditReport,String supplier) {
        Reason reason = this.getSupplierReson(creditReport,supplier);
        if (reason == null) {
            return null;
        }
        Map<String, Object> storeDataMap = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        Map<String,String> allItemsMap = reason.getItems();
        if (allItemsMap == null) {
            return null;
        }
        storeDataMap.put("checkResult",reason.getAdvice().toString());
        storeDataMap.put("explain",reason.getExplain());
        Iterator<Map.Entry<String, String>> entries = allItemsMap.entrySet().iterator();
        List<Map<String,String>> blackList = new ArrayList<>();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            if (key.equals("命中的黑名单所属的上报机构的数量") || key.equals("命中的黑名单的数量")) {
                storeDataMap.put(entry.getKey(),entry.getValue());
                continue;
            }
            if (key.equals("黑名单列表")) {
                Map<String,String> blackMap = new HashMap<>();
                JsonArray jsonArray = jsonParser.parse(entry.getValue()).getAsJsonArray();
                for (int i = 0;i<jsonArray.size();i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    if (jsonObject.has("name")) {
                        blackMap.put("命中的黑名单里的人的姓名",jsonObject.get("name").getAsString());
                    }
                    if (jsonObject.has("pid")) {
                        blackMap.put("命中的黑名单里的人的身份证",jsonObject.get("pid").getAsString());
                    }
                    if (jsonObject.has("phone")) {
                        blackMap.put("命中的黑名单里的人的手机号",jsonObject.get("phone").getAsString());
                    }
                    if (jsonObject.has("loan_type")) {
                        blackMap.put("黑名单申请贷款的类型",jsonObject.get("loan_type").getAsString());
                    }
                    if (jsonObject.has("confirm_type")) {
                        blackMap.put("黑名单被确认的类型",jsonObject.get("confirm_type").getAsString());
                    }
                    if (jsonObject.has("confirm_details")) {
                        blackMap.put("黑名单的确认细节",jsonObject.get("confirm_details").getAsString());
                    }
                    if (jsonObject.has("applied_at")) {
                        blackMap.put("黑名单申请贷款的时间",jsonObject.get("applied_at").getAsString());
                    }
                    if (jsonObject.has("confirmed_at")) {
                        blackMap.put("申请被确认为黑名单的时间",jsonObject.get("confirmed_at").getAsString());
                    }
                    if (jsonObject.has("status")) {
                        blackMap.put("命中的黑名单状态",jsonObject.get("status").getAsString());
                    }
                }
                blackList.add(blackMap);
                storeDataMap.put("blackList",blackList);
            }
        }
//        logger.info("zzcBlackList storeDataMap = " + storeDataMap.toString());
        return storeDataMap;
    }
}

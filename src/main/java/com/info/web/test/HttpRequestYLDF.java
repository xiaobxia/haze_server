package com.info.web.test;

import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.ServiceResult;
import com.info.web.pojo.BorrowOrder;
import com.info.web.util.yjr.AgentPayUtil;
import com.info.web.util.yjr.Base64;
import com.info.web.util.yjr.CompressUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 贵州银联云平台代付
 */
@Slf4j
public class HttpRequestYLDF {


    private final static String YL_CUSTID = "CB0000030374";//
    private final static String YL_PARTNERID = "00000326"; //
    private final static String YL_PERSONAL = "PERSONAL";
    public final static String YL_BUSINESS = "BUSINESS";

    /**
     * 单笔代付
     * @param reqMsg 请求字符串
     */
    public static String simgleAgentPay(String reqMsg) {

        //代付接口地址
        String url = "https://yun.unionpay.com/gateway/api/payOrderJSB.do";
        //生成签名字段并发送http(s)请求给云平台,并获取响应结果
        return AgentPayUtil.execute(reqMsg, url);
    }

    /**
     * 批量代付
     * @param reqMsg 请求字符串
     */
    public static String batchAgentPay(String reqMsg){
        //代付接口地址
        String url = "https://yun.unionpay.com/gateway/api/batchTrans.do";
        return AgentPayUtil.execute(reqMsg, url);
    }

    /**
     * 获取代付请求字符串
     * @param order 放款订单
     * @return 请求字符串
     */
    public static String getRequestSimgleStr(BorrowOrder order, String callbackUrl){
        JSONObject reqMsgJSON = new JSONObject();
        reqMsgJSON.put("version", "4.0");
        reqMsgJSON.put("custId", YL_CUSTID);
        reqMsgJSON.put("partnerId", YL_PARTNERID);
        reqMsgJSON.put("custOrderId",order.getSerialNo());// 订单号
        reqMsgJSON.put("partnerFlowNum",String.valueOf(order.getSerialNo()));//合作方流水号，业务上如果没有该字段则和custOrderId一致
        reqMsgJSON.put("orderAmount", String.valueOf(order.getIntoMoney()));// 代付金额，单位：分
        reqMsgJSON.put("payeeOpenBankId", String.valueOf(order.getBankNumber()));	// 开户行号，收款人账户类型为对公时必填
        reqMsgJSON.put("payeeAcctNum", String.valueOf(order.getCardNo())); // 收款人帐号
        reqMsgJSON.put("payeeAcctName", order.getRealname());		   // 收款人姓名
        reqMsgJSON.put("payeeAcctType", YL_PERSONAL);//收款人账户类型,PERSONAL 对私账户|BUSINESS 对公账户

        String yurref = order.getYurref();
        String remark = "享借放款";
        if (yurref.startsWith("A")) {
            remark = "享借放款";
        } else if (yurref.startsWith("B")) {
            remark = "信审服务费";

        } else if (yurref.startsWith("C")) {
            remark = "信审服务费";
        }
        reqMsgJSON.put("remarks",remark);// 备注
        /**
         * 异步通知地址，测试时请用有效地址替换
         * 该地址用于接收云平台发送的交易结果通知，收到通知后需要返回SUCCESS字符串，不区分大小写。
         * 如果云平台通知交易结果失败（没有返回SUCCESS或网络异常等原因），云平台将按下列规则重发通知：
         * 15小时以内完成8次通知，间隔频率一般是：1s,3s,10m,30m,2h,6h,15h
         * */
        reqMsgJSON.put("notifyUrl", callbackUrl);

        return reqMsgJSON.toJSONString();
    }

    /**
     * 获取批量代付请求字符串
     * @param borrowOrders 放款订单集合
     */
    public static String getRequestBatchStr(List<BorrowOrder> borrowOrders, String callbackUrl){
        JSONObject reqMsgJSON = new JSONObject();

        reqMsgJSON.put("version", "1.0");		// 接口版本号
        reqMsgJSON.put("partnerId", YL_PARTNERID); // 合作渠道编号
        reqMsgJSON.put("custId", YL_CUSTID); // 客户号
        reqMsgJSON.put("batchId", System.currentTimeMillis()); // 批次号，和客户号唯一确定一个批次
        reqMsgJSON.put("transType", "JSB01");//交易类型 JSB01 结算宝代付
        // 回盘文件通知地址（批量代付交易结果）
//        reqMsgJSON.put("backUrl",);
        // 批次总笔数，注意：和fileContent中的总笔数需要一致
        reqMsgJSON.put("totalCount", String.valueOf(borrowOrders.size()));

//        //回调地址
        reqMsgJSON.put("backUrl",callbackUrl);

        double totalMount = 0;//放款总额
        for (BorrowOrder order: borrowOrders) {
            totalMount += order.getIntoMoney();
        }
        reqMsgJSON.put("totalAmount", String.valueOf((int)totalMount));//批次总金额（单位：分）注意：和fileContent中的总金额一致
        String fileContent = getFileContent(borrowOrders);//构造批量处理的放款订单信息
        reqMsgJSON.put("fileContent",fileContent);
        return reqMsgJSON.toJSONString();
    }

    /**
     * 获取订单查询请求字符串
     * @param orderId 订单ID
     */
    public static String getRequestQueryOrderStr(String orderId){
        JSONObject reqMsgJSON = new JSONObject();
        reqMsgJSON.put("version", "4.0");
        reqMsgJSON.put("custId", YL_CUSTID);
        reqMsgJSON.put("partnerId", YL_PARTNERID);
        reqMsgJSON.put("custOrderId", orderId);

        return reqMsgJSON.toJSONString();
    }

    /**
     * 代付交易结果查询
     * @return 查询结果
     */
    public static String queryOrderResult(String reqMsg){

        /**
         * 测试环境接口地址，生产环境接口地址请查看文档
         * */
        String url = "https://yun.unionpay.com/gateway/api/queryOrderResult.do";

        /**
         * 生成签名字段并发送http(s)请求给云平台,并返回响应结果
         * */
        return AgentPayUtil.execute(reqMsg, url);

    }

    /**
     * 处理单笔代付响应结果
     * @param result 响应结果
     */
    public static ServiceResult processPayResult(String result) {

        ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "processPayResult 未知异常，请稍后重试！");
        try {

            if (result != null && result.length() > 0) {
                //将响应结果转换为json对象
                JSONObject jsonObj = JSONObject.parseObject(result);

                if("SIGN_ERROR".equals(jsonObj.getString("retCode"))){
                    serviceResult.setCode(BorrowOrder.SUB_ERROR);
                    serviceResult.setMsg(jsonObj.getString("retDesc"));
                }

                String body = jsonObj.getString("retBody");

                JSONObject JBODY = JSONObject.parseObject(body);

                String RETCOD = JBODY.getString("retCode");
                if (RETCOD.equals("SUCCESS")) {//请求成功
                    serviceResult.setCode(BorrowOrder.SUB_SUBMIT);
                    serviceResult.setMsg("支付已被银行受理,返回状态：SUCCESS");
                } else if (RETCOD.equals("EXCEPTION")) {//请求异常
                    serviceResult.setCode(BorrowOrder.SUB_SUBMIT);
                    serviceResult.setMsg("支付未知异常，请查询支付结果确认支付状态，错误信息：" + JBODY.getString("retDesc"));
                } else {//请求失败
                    serviceResult.setCode(BorrowOrder.SUB_SUB_FAIL);
                    serviceResult.setMsg("支付失败：" + JBODY.getString("retDesc") + "|" +new String(JBODY.getString("retDesc").getBytes("gbk"),"utf-8"));
                }
            }

        } catch (Exception e) {
            log.error("processPayResult error:{}",e);
        }
        return serviceResult;
    }


    /**
     * 生成批量代付订单信息，使用代码拼接数据做base64和inflater压缩
     * @param borrowOrders 放款订单集合
     * @return
     */
    public static String getFileContent(List<BorrowOrder> borrowOrders){
        String fileContent ="";
		String backFileHead = "custOrderId|orderAmount|payeeOpenBankId|payeeAcctNum|payeeAcctName|payeeAcctType|remarks";
		StringBuilder fileContentBuilder = new StringBuilder(backFileHead + "\r\n");
		for (int i = 0; i < borrowOrders.size(); i++) {
		    BorrowOrder order = borrowOrders.get(i);
			fileContentBuilder.append(order.getSerialNo())//订单号
		            .append("|")
		            .append(order.getIntoMoney())//放款金额，单位：分
		            .append("|")
		            .append(order.getBankNumber())//收款方开户行号
		            .append("|")
		            .append(order.getCardNo())//收款方账号
		            .append("|")
		            .append(order.getRealname())//收款方姓名
		            .append("|")
		            .append(YL_PERSONAL)
		            .append("|");
            String yurref = order.getYurref();
            String remark = "多米优放款";
            if (yurref.startsWith("A")) {
                remark = "多米优放款";
            } else if (yurref.startsWith("B")) {
                remark = "信审服务费";

            } else if (yurref.startsWith("C")) {
                remark = "信审服务费";
            }

            fileContentBuilder.append(remark)
		            .append("\r\n");
		}

		try {
			fileContent = new String(Base64.encode(CompressUtil.deflater(fileContentBuilder.toString().getBytes("UTF-8"))), "UTF-8");
		} catch (Exception e) {
            log.error("getFileContent error:{}",e);
		}

		return fileContent;
    }

    /**
     * 返回批量代付订单结果
     * @param fileContent 批量代付回盘文件内容
     */
    public static List<HashMap<String,Object>> getBatchResult(String fileContent){
        List<HashMap<String,Object>> list = null;

        try {
            byte[] fileArray = CompressUtil.inflater(Base64.decode(fileContent.getBytes("UTF-8")));
            String _fileContent = new String(fileArray);
            String[] strArray = _fileContent.split("\r\n");

            if(strArray.length > 0){
                list = new ArrayList<>();
                HashMap<String, Object> map = null;
                for (int i = 0; i < strArray.length; i ++) {
                    if(i > 0){//去掉首行
                        map = new HashMap<>();
                        String[] line = strArray[i].split("\\|");
                        map.put("custOrderId",line[0]);
                        map.put("orderId",line[1]);
                        map.put("orderAmount",line[2]);
                        map.put("createTime",line[3]);
                        map.put("payeeAcctName",line[4]);
                        map.put("payeeAcctNum",line[5]);
                        map.put("payeeAcctType",line[6]);
                        map.put("transStatus",line[7]);
                        map.put("transStatusDesc",line[8]);

                        list.add(map);
                    }
                }
            }
        }catch (Exception e){
            log.error("getBatchResult error:{}",e);
        }
        return  list;
    }


    public static String getResponseParams(HttpServletRequest request){
        String asyncNoticeData = "";

        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("getResponseParams error:{}",e);
        }finally{
            try {
                asyncNoticeData = new String(outputStream.toByteArray());
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                log.error("getResponseParams error:{}",e);
            }
        }

        return asyncNoticeData;
    }


}

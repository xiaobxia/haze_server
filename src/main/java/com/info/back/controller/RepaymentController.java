package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.info.back.service.ITaskJob;
import com.info.back.utils.*;
import com.info.constant.CollectionConstant;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.pojo.*;
import com.info.web.service.*;
import com.info.web.util.*;
import com.vxianjin.gringotts.pay.model.OffPayResponse;
import com.vxianjin.gringotts.pay.service.OffLinePay;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.info.web.pojo.BorrowOrder.*;

/***
 * 还款
 *
 * @author Administrator
 *
 */
@Slf4j
@Controller
@RequestMapping("repayment/")
public class RepaymentController extends BaseController {

    @Autowired
    private IRepaymentService repaymentService;
    @Autowired
    private IReportRepaymentService reportRepaymentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRepaymentDetailService repaymentDetailService;
    @Autowired
    private IBorrowOrderService borrowOrderService;
    @Autowired
    private ITaskJob taskJob;
    @Autowired
    private IUserContactsService userContactsService;
    @Autowired
    private IRepaymentAlipayService repaymentAlipayService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private OffLinePay offLinePay;

    @RequestMapping("getRepaymentPage")
    public String getRepaymentPage(HttpServletRequest request, Integer[] statuses, Model model, String overdueStatus) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if (null == statuses || statuses.length == 0 || null == statuses[0]) {
                statuses = new Integer[]{STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ};
                model.addAttribute("statusesType", "ALL");
            }
            if ("S1".equals(overdueStatus)) {
                params.put("lateDayStart", "1");
                params.put("lateDayEnd", "3");
            } else if ("S2".equals(overdueStatus)) {
                params.put("lateDayStart", "4");
                params.put("lateDayEnd", "15");
            } else if ("S3".equals(overdueStatus)) {
                params.put("lateDayStart", "16");
                params.put("lateDayEnd", "1000");
            } else if ("M1-M2".equals(overdueStatus)) {
                params.put("lateDayStart", "31");
                params.put("lateDayEnd", "1000");
            }
            params.put("statuses", statuses);
            PageConfig<Repayment> pageConfig = repaymentService.findPage(params);
            List<Repayment> list = new ArrayList<>();
            for(Repayment repayment : pageConfig.getItems()){
                //查询用户成功借款次数
                Integer loanSucCount = repaymentService.userBorrowCount(null,repayment.getUserId());
                //该用户在还款表中无记录
                if(loanSucCount != null && loanSucCount < 1 ){
                    repayment.setLoanCount("首借");
                }else{
                    Integer loanCount = repaymentService.userBorrowCount(99999,repayment.getUserId());
                    //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                    if(loanCount < 1){
                        repayment.setLoanCount("首借");
                    }else{
                        repayment.setLoanCount(loanCount.toString());
                    }
                }
                list.add(repayment);
            }
            pageConfig.setItems(list);
            model.addAttribute("pm", pageConfig);

        } catch (Exception e) {
            log.error("getRepaymentPage error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/repaymentList";
    }

    /**
     * 还款销账
     * @param request req
     * @param statuses res
     * @param model model
     * @param overdueStatus overdueStatus
     * @return str
     */
    @RequestMapping("getRepaymentWriteOffPage")
    public String getRepaymentWriteOffPage(HttpServletRequest request, Integer[] statuses, Model model, String overdueStatus) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if(null == statuses || statuses.length == 0 || null == statuses[0]){
                statuses = new Integer[]{STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ};
                model.addAttribute("statusesType", "ALL");
            }
            if("S1".equals(overdueStatus)){
                params.put("lateDayStart", "1");
                params.put("lateDayEnd", "10");
            }
            else if("S2".equals(overdueStatus)){
                params.put("lateDayStart", "11");
                params.put("lateDayEnd", "30");
            }
            else if("S3".equals(overdueStatus)){
                params.put("lateDayStart", "31");
                params.put("lateDayEnd", "60");
            }
            params.put("statuses", statuses);
            if(params.get("userMobileLike")==null&&params.get("userAccountLike")==null){

            }else {
                PageConfig<Repayment> pageConfig = repaymentService.findWriteOffPage(params);
                List<Repayment> list = new ArrayList<>();
                for(Repayment repayment : pageConfig.getItems()){
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,repayment.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        repayment.setLoanCount("首借");
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,repayment.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            repayment.setLoanCount("首借");
                        }else{
                            repayment.setLoanCount(loanCount.toString());
                        }
                    }
                    list.add(repayment);
                }
                pageConfig.setItems(list);
                model.addAttribute("pm", pageConfig);
            }

        } catch (Exception e) {
            log.error("getRepaymentPage error:{}", e);
        }
        // 用于搜索框保留值
        model.addAttribute("params", params);
        return "repayment/repaymentWriteOffList";
    }

    @RequestMapping("getRepaymentedPage")
    public String getRepaymentedPage(HttpServletRequest request, Model model, Integer statuses[]) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if (null == statuses || statuses.length == 0 || null == statuses[0]) {
                statuses = new Integer[]{STATUS_YHK, STATUS_YQYHK};
                model.addAttribute("statusesType", "ALL");
            }
//			atuses=30&statuses=34&myId=209
            params.put("statuses", statuses);
            PageConfig<Repayment> pageConfig = repaymentService.findPage(params);
            List<Repayment> list = new ArrayList<>();
            for(Repayment repayment : pageConfig.getItems()){
                //查询用户成功借款次数
                Integer loanSucCount = repaymentService.userBorrowCount(null,repayment.getUserId());
                //该用户在还款表中无记录
                if(loanSucCount != null && loanSucCount < 1 ){
                    repayment.setLoanCount("首借");
                }else{
                    Integer loanCount = repaymentService.userBorrowCount(99999,repayment.getUserId());
                    //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                    if(loanCount < 1){
                        repayment.setLoanCount("首借");
                    }else{
                        repayment.setLoanCount(loanCount.toString());
                    }
                }
                list.add(repayment);
            }
            pageConfig.setItems(list);
            model.addAttribute("pm", pageConfig);

        } catch (Exception e) {
            log.error("getRepaymentedPage error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/repaymentedList";
    }

    @RequestMapping("getReportRepaymentCount")
    public String getReportRepaymentCount(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            PageConfig<Repayment> pageConfig = reportRepaymentService.findReportRepaymentCount(params);
            model.addAttribute("pm", pageConfig);
        } catch (Exception e) {
            log.error("getReportRepaymentCount error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/reportCount";
    }

    @RequestMapping("getReportRepaymentAmount")
    public String getReportRepaymentAmount(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            PageConfig<Repayment> pageConfig = reportRepaymentService.findReportRepaymentAmount(params);
            model.addAttribute("pm", pageConfig);
        } catch (Exception e) {
            log.error("getReportRepaymentAmount error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/reportAmount";
    }

    @RequestMapping("getReportRepaymentAll")
    public String getReportRepaymentAll(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            PageConfig<Repayment> pageConfig = reportRepaymentService.findReportRepaymentAll(params);
            model.addAttribute("pm", pageConfig);
        } catch (Exception e) {
            log.error("getReportRepaymentAll error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/reportAll";
    }
/*
    @RequestMapping("collection/{repaymentId}")
	public void collection(@PathVariable Integer repaymentId){
		try {
			Repayment repayment = repaymentService.selectByPrimaryKey(repaymentId);
			User user = userService.selectCollectionByUserId(repayment.getUserId());
			repaymentService.collection(user, repayment, null, Repayment.OVERDUE_COLLECTION);
		}
		catch (Exception e){
			logger.error("collection error", e);
		}
	}*/


    @RequestMapping("collectionUser")
    public void collectionUser(Integer[] ids) {
        try {
            for (Integer id : ids) {
                User u = userService.selectCollectionByUserId(id);
                log.info("collectionUser applying userId =:{}",u.getId());

                List<Map> loanList = new ArrayList<>();

                Map<String, Object> loanMap = new HashMap<>();

                HashMap<String, Object> params = new HashMap<>();
                params.put("userId", u.getId());
                List<UserContacts> userContacts = userContactsService.selectUserContacts(params);

                // 紧急联系人
                List<Map> mmanUserRelas = new ArrayList<>();
                Map<String, String> mmanUserRela = new HashMap<>();
                mmanUserRela.put("id", "");
                mmanUserRela.put("userId", u.getId());
                mmanUserRela.put("contactsKey", "1");
                mmanUserRela.put("relaKey", u.getFristContactRelation());
                mmanUserRela.put("infoName", u.getFirstContactName());
                mmanUserRela.put("infoValue", u.getFirstContactPhone());
                mmanUserRela.put("contactsFlag", "1");
                mmanUserRelas.add(mmanUserRela);

                mmanUserRela = new HashMap<>();
                mmanUserRela.put("id", "");
                mmanUserRela.put("userId", u.getId());
                mmanUserRela.put("contactsKey", "2");
                mmanUserRela.put("relaKey", u.getSecondContactRelation());
                mmanUserRela.put("infoName", u.getSecondContactName());
                mmanUserRela.put("infoValue", u.getSecondContactPhone());
                mmanUserRela.put("contactsFlag", "1");
                mmanUserRelas.add(mmanUserRela);

                for (UserContacts contacts : userContacts) {
                    mmanUserRela = new HashMap<>();
                    mmanUserRela.put("id", contacts.getId());
                    mmanUserRela.put("userId", u.getId());
                    mmanUserRela.put("contactsKey", "2");
                    mmanUserRela.put("infoName", contacts.getContactName());
                    mmanUserRela.put("infoValue", contacts.getContactPhone());
                    mmanUserRelas.add(mmanUserRela);
                }

                loanMap.put("mmanUserInfo", u);
                loanMap.put("mmanUserRelas", mmanUserRelas);

                loanList.add(loanMap);

                Map<String, Object> collectionRelevantJson = new HashMap<>();
                collectionRelevantJson.put("collectionRelevantJson", loanList);

                // 设置参数 可设置多个
                List<NameValuePair> postParams = new ArrayList<>();
                postParams.add(new BasicNameValuePair("collectionRelevantJson", JSONObject.fromObject(collectionRelevantJson).toString()));
                try {
                    String result = HttpUtil.getInstance().post(CollectionConstant.getCollectionPath(), postParams);
                    JSONObject obj = JSONObject.fromObject(result);
                    if (obj.getString("code").equals("0")) {
                        log.info("collectionUser applying success userId =:{}",id);
                    }
                } catch (Exception e) {
                    log.error("collection error:{}", e);
                }
            }

        } catch (Exception e) {
            log.error("collection error:{}", e);
        }
    }


    @RequestMapping("overdue1")
    public void overdue1(String ids[], String type) {
        try {
//			jedisCluster.set("RENEWAL_" + 3, "3");
//			jedisCluster.set("OVERDUE_" + 3, "3");
//			jedisCluster.set("REPAY_" + 3, "3");
//			jedisCluster.set("WITHHOLD_31dc587e7ea04342ad0860d9a1abe50d", "31dc587e7ea04342ad0860d9a1abe50d");
			/*taskJob.overdue();*/
            log.info("数据推送开始---------->overdue start");
            if (StringUtils.isNotBlank(type)) {
                for (String id : ids) {
                    try {
                        jedisCluster.set(type + "_" + id, "" + id);
                        log.info("collection tuirepay success repaymentId=:{}", id);
                    } catch (Exception e) {
                        log.error("collection tuirepay error:{} repaymentId=:{}," , e, id);
                    }
                }

            }
            log.info("数据推送结束---------->overdue end 共:{} " ,ids.length);
        } catch (Exception e) {
            log.error("overdue error:{}", e);
        }
    }

    @RequestMapping("overdue")
    public void overdue() {
        try {
            taskJob.overdue();
        } catch (Exception e) {
            log.error("overdue error:{}", e);
        }
    }

    @RequestMapping("withhold")
    public void withhold() {
        try {
            taskJob.withhold();
        } catch (Exception e) {
            log.error("withhold error", e);
        }
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }



//    @RequestMapping("withhold/{repaymentId}")
//    public void withhold(@PathVariable Integer repaymentId) {
//        try {
//            Repayment repayment = repaymentService.selectByPrimaryKey(repaymentId);
//            log.info("单个代扣开始 withhold userId =:{}",repayment.getUserId());
//            Repayment re = repaymentService.selectByPrimaryKey(repayment.getId());
//            User user = userService.searchByUserid(re.getUserId());
//            ServiceResult result = repaymentService.withhold(re, user, Repayment.COLLECTION_WITHHOLD);
//            if (result.getCode().equals(ServiceResult.SUCCESS)) {
//                RepaymentDetail detail = new RepaymentDetail();
//                detail.setUserId(re.getUserId());
//                detail.setAssetRepaymentId(re.getId());
//                detail.setTrueRepaymentMoney(re.getRepaymentAmount() - re.getRepaymentedAmount());
//                detail.setCreatedAt(new Date());
//                detail.setOrderId(result.getExt().toString());
//                detail.setRepaymentType(RepaymentDetail.TYPE_BANK_CARD_AUTO);
//                detail.setRemark("手动任务代扣");
//                detail.setUserId(re.getUserId());
//                detail.setUpdatedAt(new Date());
//                detail.setStatus(RepaymentDetail.STATUS_SUC);
//                detail.setAssetOrderId(re.getAssetOrderId());
//                repaymentDetailService.insertSelective(detail);
//                repaymentService.repay(re, detail);
//
//                // 如果是未逾期的还款，调用提额
//                if (re.getLateDay() == 0) {
//                    user = userService.searchByUserid(re.getUserId());
//                    log.info("repay to Mention Money ing ----------------->");
////					borrowOrderService.addUserLimit(user);
//                }
//            }
//        } catch (Exception e) {
//            log.error("repay withhold repaymentId =:{}error:{}",repaymentId, e);
//        }
//    }


    @RequestMapping("repaySmsRemind")
    public void repaySmsRemind(String amount, String type) {
        try {
            if (StringUtils.isNotBlank(amount)) {
                //SendSmsUtil.sendSmsDiyCL("18516777211", "尊敬的张苏豪，您的" + amount + "借款明日到期，请至APP还款，若到期未还款，平台将自动扣款，请确保尾号9691银行卡资金充足，如已还款，请忽略。");
            } else if ("9".equals(type)) {
                taskJob.repaySmsRemind9();
            } else {
                taskJob.repaySmsRemind();
            }
        } catch (Exception e) {
            log.error("repaySmsRemind error:{}", e);
        }
    }

    @RequestMapping("reportRepaymentTemp")
    public void reportRepaymentTemp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String firstRepaymentTime;
            Calendar now = Calendar.getInstance();
            for (int i = 0; i < 40; i++) {
                firstRepaymentTime = dateFormat.format(now.getTime());
                if (firstRepaymentTime.equals("2017-01-08")) {
                    break;
                }
                // 12点后的定时 更新前一日数据 （带逾期信息）
                taskJob.reportRepayment(firstRepaymentTime, i > 0);
                now.add(Calendar.DAY_OF_YEAR, -1);
            }
        } catch (Exception e) {
            log.error("reportRepayment error:{}", e);
        }
    }


    @RequestMapping("reportRepayment12")
    public void reportRepayment12() {
        try {
            taskJob.reportRepayment12();
        } catch (Exception e) {
            log.error("reportRepayment error:{}", e);
        }
    }

    @RequestMapping("toAgainRepaymentReport")
    public String toAgainRepaymentReport(HttpServletRequest request, Model model) {
        Map<String, String> params = this.getParameters(request);
        model.addAttribute("params", params);
        return "repayment/againRepaymentReport";
    }

    /**
     * 重新计算统计
     *
     * @param nowTime (年月日xxxx-xx-xx)
     * @param request req
     */
    @RequestMapping("againRepaymentReport")
    public void againRepaymentReport(HttpServletRequest request, HttpServletResponse response, String nowTime) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            if (StringUtils.isNotBlank(nowTime)) {
                Calendar now = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String firstRepaymentTime = dateFormat.format(now.getTime());
                // 当日刷新 不显示逾期
                if (nowTime.equals(firstRepaymentTime)) {
                    taskJob.reportRepayment(nowTime, false);
                } else {
                    now.add(Calendar.DAY_OF_YEAR, -1);
                    firstRepaymentTime = dateFormat.format(now.getTime());
                    // 昨日
                    if (nowTime.equals(firstRepaymentTime)) {
                        // 昨日刷新 今日12点后 显示逾期
                        if (now.get(Calendar.HOUR_OF_DAY) >= 12) {
                            taskJob.reportRepayment(nowTime, true);
                        } else {
                            taskJob.reportRepayment(nowTime, false);
                        }
                    } else {
                        taskJob.reportRepayment(nowTime, true);
                    }
                }
            }
        } catch (Exception e) {
            SpringUtils.renderDwzResult(response, false, "服务器异常，请稍后重试！", DwzResult.CALLBACK_CLOSECURRENT);
            log.error("getUserPage error:{}", e);
        }
        SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
    }

    /**
     * 刷新当日数据
     *
     * @param response res
     */
    @RequestMapping("returnRepaymentReport")
    public void returnRepaymentReport(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            taskJob.reportRepaymentE2();
            SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
        } catch (Exception e) {
            log.error("returnRepaymentReport error:{}", e);
        }
    }

    @RequestMapping("reportRepaymentE2")
    public void reportRepaymentE2() {
        try {
            taskJob.reportRepaymentE2();
        } catch (Exception e) {
            log.error("reportRepayment error:{}", e);
        }
    }

    /**
     * 还款
     *
     * @param request req
     * @param response res
     * @param model model
     * @return str
     */
    @RequestMapping("repay")
    public String repay(HttpServletRequest request, HttpServletResponse response, Model model, RepaymentDetail detail) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        BackUser backUser = this.loginAdminUser(request);
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (null != detail.getAssetRepaymentId()) {
                    // 更新的页面跳转
                    Repayment repayment = repaymentService.selectByPrimaryKey(detail.getAssetRepaymentId());
                    model.addAttribute("remainMoney", (repayment.getRepaymentAmount() - repayment.getRepaymentedAmount()) / 100.00);
                    model.addAttribute("assetRepaymentId", detail.getAssetRepaymentId());
                }
                url = "repayment/repay";
            } else {
                if (null == detail.getAssetRepaymentId()) {
                    erroMsg = "请选择一条还款数据！";
                } else {
                    OffPayResponse resultModel = offLinePay.offLineRepay(JSON.toJSONString(detail),backUser.getUserAccount());
                    log.info("repay result :{}",JSON.toJSONString(resultModel));
//
                    if(resultModel==null || !resultModel.isSucc()){
                        erroMsg = "还款接口返回失败";
                    }
                }
                SpringUtils.renderDwzResult(response, null == erroMsg, null == erroMsg ? "操作成功！" : erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            log.error("repay error:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }


    /**
     * 线下续期
     *
     * @param request req
     * @param response res
     * @param model model
     * @return str
     */
    @RequestMapping("renewal")
    public String renewal(HttpServletRequest request, HttpServletResponse response, Model model, RenewalRecord rr) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = "续期接口返回失败";
        BackUser backUser = this.loginAdminUser(request);
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                Repayment re = repaymentService.selectByPrimaryKey(rr.getAssetRepaymentId());
                BorrowOrder bo = borrowOrderService.findOneBorrow(re.getAssetOrderId());
                Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.SYS_FEE);
                // 续期费
                // 续期手续费 分为单位
                BigDecimal renewalFee = bo.getRenewalPoundage();
//                String renewalFee = keys.get("renewal_fee");
                // 待还总金额
                Long waitRepay = re.getRepaymentAmount() - re.getRepaymentedAmount();
                // 待还滞纳金
                Long waitLate = Long.parseLong(String.valueOf(re.getPlanLateFee() - re.getTrueLateFee()));
                // 待还本金
                Long waitAmount = waitRepay - waitLate;
                // 服务费
//                Integer loanApr = bo.getMoneyAmount() * bo.getApr() / 10000;   //服务费
                Integer loanApr = bo.getRenewalFee().intValue();   //服务费
                Long allCount = waitLate + loanApr + renewalFee.longValue();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                model.addAttribute("feeLateApr",bo.getLateFeeApr()* bo.getMoneyAmount()/10000);
                model.addAttribute("repaymentTime",sf.format(re.getRepaymentTime()));
                model.addAttribute("waitAmount", waitAmount);
                model.addAttribute("loanApr", loanApr);
                model.addAttribute("renewalFee", renewalFee.toString());
                model.addAttribute("waitLate", waitLate);
                model.addAttribute("allCount", allCount);
                model.addAttribute("assetRepaymentId", rr.getAssetRepaymentId());
                url = "repayment/renewal";
            } else {
                OffPayResponse resultModel = offLinePay.offLineRenewal(JSON.toJSONString(rr),backUser.getUserAccount());
                log.info("renewal result:{}",JSON.toJSONString(resultModel));
                if(resultModel!=null && resultModel.isSucc()){
                    SpringUtils.renderDwzResult(response, true, "操作成功！", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                }else{
                    SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                }
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            log.error("renewal error:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 导出待还Excel
     */
    @RequestMapping("toDHExcel")
    public void toDHExcel(HttpServletRequest request, HttpServletResponse response, Integer statuses[], String overdueStatus) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            if (null == statuses || statuses.length == 0 || null == statuses[0]) {
                statuses = new Integer[]{STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ};
            }
            if ("S1".equals(overdueStatus)) {
                params.put("lateDayStart", "1");
                params.put("lateDayEnd", "10");
            } else if ("S2".equals(overdueStatus)) {
                params.put("lateDayStart", "11");
                params.put("lateDayEnd", "30");
            } else if ("S3".equals(overdueStatus)) {
                params.put("lateDayStart", "31");
                params.put("lateDayEnd", "60");
            }
            params.put("statuses", statuses);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ, BorrowOrder.STATUS_FKBH,
                    BorrowOrder.STATUS_FKSB));
            int totalPageNum = repaymentService.findParamsCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }

            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "待还列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

            String[] titles = {"序号", "姓名", "手机号", /*"是否是老用户"*/"成功还款次数", "借款到账金额", "服务费", "总需要还款金额", "已还金额", "放款时间", "预期还款时间", "逾期天数", "状态"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<Repayment> pm = repaymentService.findPage(params);
                List<Repayment> repaymentList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (Repayment repayment : repaymentList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = repayment.getId();
                    conList[1] = repayment.getRealname();
                    conList[2] = repayment.getUserPhone();
                   /* if (0 == (repayment.getCustomerType())) {
                        conList[3] = "新用户";
                    } else {
                        conList[3] = "老用户";
                    }*/
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,repayment.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        conList[3] = "首借";
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,repayment.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            conList[3] = "首借";
                        }else{
                            conList[3] = loanCount.toString();
                        }
                    }
                    conList[4] = repayment.getRepaymentPrincipal() / 100.00;
                    conList[5] = repayment.getRepaymentInterest() / 100.00;
                    conList[6] = repayment.getRepaymentAmount() / 100.00;
                    conList[7] = repayment.getRepaymentedAmount() / 100.00;
                    conList[8] = sfd.format(repayment.getCreditRepaymentTime());
                    conList[9] = sfd.format(repayment.getRepaymentTime());
                    conList[10] = repayment.getLateDay();
                    conList[11] = BorrowOrder.borrowStatusMap.get(repayment.getStatus());
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "待还列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }
//		backBorrowOrder/toFKExcel
    }

    /**
     * 导出还款Excel
     */
    @RequestMapping("toYHExcel")
    public void toYHExcel(HttpServletRequest request, HttpServletResponse response, Integer statuses[]) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            if (null == statuses || statuses.length == 0 || null == statuses[0]) {
                statuses = new Integer[]{STATUS_YHK, STATUS_YQYHK};
            }
            params.put("statuses", statuses);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ, BorrowOrder.STATUS_FKBH,
                    BorrowOrder.STATUS_FKSB));
            int totalPageNum = repaymentService.findParamsCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }

            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "还款列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

            String[] titles = {"序号", "姓名", "手机号", /*"是否是老用户"*/"成功还款次数", "借款到账金额", "服务费", "总需要还款金额", "已还金额", "放款时间", "还款时间", "预期还款时间", "状态"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<Repayment> pm = repaymentService.findPage(params);
                List<Repayment> repaymentList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (Repayment repayment : repaymentList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = repayment.getId();
                    conList[1] = repayment.getRealname();
                    conList[2] = repayment.getUserPhone();
                   /* if (0 == (repayment.getCustomerType())) {
                        conList[3] = "新用户";
                    } else {
                        conList[3] = "老用户";
                    }*/
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,repayment.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        conList[3] = "首借";
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,repayment.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            conList[3] = "首借";
                        }else{
                            conList[3] = loanCount.toString();
                        }
                    }
                    conList[4] = repayment.getRepaymentPrincipal() / 100.00;
                    conList[5] = repayment.getRepaymentInterest() / 100.00;
                    conList[6] = repayment.getRepaymentAmount() / 100.00;
                    conList[7] = repayment.getRepaymentedAmount() / 100.00;
                    conList[8] = sfd.format(repayment.getCreditRepaymentTime());
                    conList[9] = sfd.format(repayment.getRepaymentRealTime());
                    conList[10] = sfd.format(repayment.getRepaymentTime());
                    conList[11] = BorrowOrder.borrowStatusMap.get(repayment.getStatus());
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "还款列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    /**
     * 还款对账
     *
     * @param request req
     * @param model model
     * @return str
     */
    @RequestMapping("getRepaymentCheckPage")
    public String getRepaymentCheckPage(HttpServletRequest request,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if (null == params.get("createdAt")) {
                params.put("createdAt", DateUtil.getDateFormat("yyyy-MM-dd"));
                params.put("createdAtEnd", DateUtil.getDateFormat("yyyy-MM-dd"));

            }
            PageConfig<RepaymentChecking> pageConfig = repaymentDetailService.checkRepayment(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);

        } catch (Exception e) {
            log.error("getRepaymentCheckPage error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/repaymentCheckList";
    }

    /**
     * 续期对账
     *
     * @param request req
     * @param model model
     * @return str
     */
    @RequestMapping("getRenewalCheckPage")
    public String getRenewalCheckPage(HttpServletRequest request,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            if (null == params.get("orderTime")) {
                params.put("orderTime", DateUtil.getDateFormat("yyyy-MM-dd"));
                params.put("orderTimeEnd", DateUtil.getDateFormat("yyyy-MM-dd"));

            }
            PageConfig<RepaymentChecking> pageConfig = repaymentDetailService.checkRenewal(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);

        } catch (Exception e) {
            log.error("getRepaymentCheckPage error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "repayment/renewalCheckList";
    }


    /**
     * 导出还款对账Excel
     */
    @RequestMapping("toHkExcel")
    public void toHkDZExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = repaymentDetailService.checkRepaymentCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            if (null == params.get("createdAt")) {
                params.put("createdAt", DateUtil.getDateFormat("yyyy-MM-dd"));
                params.put("createdAtEnd", DateUtil.getDateFormat("yyyy-MM-dd"));

            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "还款对账列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

            String[] titles = {"序号", "用户ID", "姓名", "手机号", "还款Id", "总还款金额", "已还款金额", "实还金额", "退款金额", "订单Id", "还款方式", "还款状态", "还款详情状态", "还款时间", "放款账户"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<RepaymentChecking> pm = repaymentDetailService.checkRepayment(params);
                List<RepaymentChecking> repaymentList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                int j = 1;
                for (RepaymentChecking rc : repaymentList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = j;
                    conList[1] = rc.getUserId();
                    conList[2] = rc.getRealname();
                    conList[3] = rc.getPhone();
                    conList[4] = rc.getReapymentId();
                    conList[5] = rc.getRepaymentAmount() / 100.00;
                    conList[6] = rc.getRepaymentedAmount() / 100.00;
                    conList[7] = rc.getTrueRepaymentMoney() / 100.00;
                    double returnMoney = 0;
                    try {
                        if (StringUtils.isNotBlank(rc.getBackOrderId())) {
                            returnMoney = Long.parseLong(rc.getBackOrderId()) / 100.00;
                        }
                    } catch (Exception e) {
                        log.error("toHkDZExcel error:{}",e);
                    }
                    conList[8] = returnMoney;
                    conList[9] = rc.getOrderId();
                    conList[10] = RepaymentDetail.REPAY_TYPE.get(rc.getRepaymentType());
                    conList[11] = BorrowOrder.borrowStatusMap.get(rc.getReapymentStatus());
                    String detailStatus = RepaymentChecking.REPAYMENT_STATUS.get(rc.getStatus());
                    if (StringUtils.isBlank(detailStatus)) {
                        detailStatus = RepaymentChecking.BACK_REPAY_STATUS.get(rc.getStatus());
                    }
                    conList[12] = detailStatus;
                    conList[13] = sfd.format(rc.getCreatedAt());
                    conList[14] = BorrowOrder.LOAN_ACCOUNTMap.get(rc.getCapitalType());
                    contents.add(conList);
                    j++;
                }
                ExcelUtil.buildExcel(workbook, "还款对账列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    /**
     * 导出续期对账Excel
     */
    @RequestMapping("toXQExcel")
    public void toXQDZExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = repaymentDetailService.checkRenewalCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            if (null == params.get("orderTime")) {
                params.put("orderTime", DateUtil.getDateFormat("yyyy-MM-dd"));
                params.put("orderTimeEnd", DateUtil.getDateFormat("yyyy-MM-dd"));

            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "续期对账列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

            String[] titles = {"序号", "用户ID", "姓名", "手机号", "还款Id", "订单ID", "总还款金额", "已还款金额", "服务费", "续期天数", "续期费", "退款金额", "续期前应还时间", "续期后应还时间", "续期方式", "续期时间", "放款账户"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<RepaymentChecking> pm = repaymentDetailService.checkRenewal(params);
                List<RepaymentChecking> checkList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                int j = 1;
                for (RepaymentChecking rc : checkList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = j;
                    conList[1] = rc.getUserId();
                    conList[2] = rc.getRealname();
                    conList[3] = rc.getPhone();
                    conList[4] = rc.getReapymentId();
                    conList[5] = rc.getOrderId();
                    conList[6] = rc.getRepaymentAmount() / 100.00;
                    conList[7] = rc.getRepaymentedAmount() / 100.00;
                    conList[8] = rc.getRepaymentInterest() / 100.00;
                    conList[9] = rc.getRenewalDay();
                    conList[10] = rc.getTrueRepaymentMoney() / 100.00;
                    conList[11] = rc.getReturnMoney() / 100.00;
                    conList[12] = sfd.format(rc.getOldRepaymentTime());
                    conList[13] = sfd.format(rc.getRepaymentTime());
                    String renewalType = "未知渠道";
                    if (rc.getRenewalType() == 1) {
                        renewalType = "富友";
                    } else if (rc.getRenewalType() == 2) {
                        renewalType = "支付宝";
                    }
                    conList[14] = renewalType;
                    conList[15] = sfd.format(rc.getOrderTime());
                    conList[16] = BorrowOrder.LOAN_ACCOUNTMap.get(rc.getCapitalType());
                    contents.add(conList);
                    j++;
                }
                ExcelUtil.buildExcel(workbook, "续期对账列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }
    }
}

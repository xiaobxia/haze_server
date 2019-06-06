package com.info.back.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.minlog.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.info.back.dao.IBackConfigParamsDao;
import com.info.back.pojo.Areas;
import com.info.back.service.IBackUserService;
import com.info.back.service.IChinaAreaService;
import com.info.back.service.IRiskUserService;
import com.info.back.service.ITaskJob;
import com.info.back.utils.*;
import com.info.constant.Constant;
import com.info.risk.dao.IRiskModelOrderDao;
import com.info.risk.dao.IRiskOrdersDao;
import com.info.risk.pojo.*;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.newrisk.ShowRisk;
import com.info.risk.pojo.newrisk.ShuJuMoHeVo;
import com.info.risk.service.IAutoRiskService;
import com.info.risk.service.IRiskModelOrderService;
import com.info.risk.utils.autorisk.RiskCreditReasonUtil;
import com.info.web.controller.BaseController;
import com.info.web.dao.BorrowProductConfigDao;
import com.info.web.dao.IUserContactsDao;
import com.info.web.dao.IndexDao;
import com.info.web.pojo.*;
import com.info.web.service.*;
import com.info.web.util.*;
import com.info.web.util.csuntil.MD5Util;
import com.vxianjin.gringotts.pay.enums.OperateType;
import com.vxianjin.gringotts.pay.enums.OrderChangeAction;
import com.vxianjin.gringotts.pay.pojo.OrderLogModel;
import com.vxianjin.gringotts.pay.service.OrderLogService;
import com.tan66.datawarehouse.openapi.model.asset.MbgAssetBorrowOrder;
import com.tan66.datawarehouse.openapi.model.audit.MbgAuditCenter;
import com.tan66.datawarehouse.openapi.model.channel.MbgChannelInfo;
import com.tan66.datawarehouse.openapi.model.credit.MbgCreditLoanPay;
import com.tan66.datawarehouse.openapi.model.mman.MbgMmanLoanCollectionOrder;
import com.tan66.datawarehouse.openapi.model.mman.MbgMmanLoanCollectionRecord;
import com.tan66.datawarehouse.openapi.model.risk.MbgRiskModelOrder;
import com.tan66.datawarehouse.openapi.model.risk.MbgRiskOrders;
import com.tan66.datawarehouse.openapi.model.user.MbgUserContacts;
import com.tan66.datawarehouse.openapi.model.user.MbgUserInfo;
import com.tan66.datawarehouse.openapi.rpc.api.DataWareHouseService;
import com.vxianjin.gringotts.dao.util.common.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("backBorrowOrder/")
public class BorrowOrderController extends BaseController {


    @Autowired
    private IBorrowOrderService borrowOrderService;
    @Autowired
    private IRepaymentService repaymentService;
    @Autowired
    private IRepaymentDetailService repaymentDetailService;
    @Autowired
    public IUserService userService;
    @Autowired
    private IUserInfoImageService userInfoImageService;
    @Autowired
    private ITaskJob taskJob;
    @Autowired
    @Qualifier("riskUserService")
    private IRiskUserService riskUserService;
    @Autowired
    private IBackUserService backUserService;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    private IChannelInfoService channelInfoService;
    @Autowired
    private IUserContactsDao userContactsDao;
    @Autowired
    private IRiskOrdersDao riskOrdersDao;
    @Autowired
    private IBackConfigParamsDao backConfigParamsDao;
    @Resource
    private IRiskModelOrderDao riskModelOrderDao;
    @Autowired
    private IChinaAreaService chinaAreaService;
    @Autowired
    private IRiskModelOrderService riskModelOrderService;
    @Autowired
    private IUserBankService userBankService;
    @Autowired
    private IAutoRiskService autoRiskService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private DataWareHouseService dataWareHouseService;
    @Autowired
    private BorrowProductConfigDao productConfigDao;

    @Autowired
    private IndexDao indexDao;


    @RequestMapping("/addUserLimit")
    public void addUserLimit(HttpServletRequest request) {

        User user = userService.searchByUserid(Integer.parseInt(request.getParameter("userId")));
        borrowOrderService.addUserLimit(user);
    }

    @RequestMapping("/queryCity")
    public void queryCity(HttpServletResponse response, ModelMap model, Integer provinceId) {
        Map<String, Object> jsonMap = new HashMap<>();
        String code = "-1";
        String msg = "异常";
        try {
            List<Areas> citys = chinaAreaService.selectCityByProvince(provinceId);
            model.addAttribute("citys", citys);
            code = "0";
            msg = "成功";
            jsonMap.put("code", code);
            jsonMap.put("message", msg);
            jsonMap.put("data", citys);

        } catch (Exception e) {
            jsonMap.put("code", code);
            jsonMap.put("message", e.getMessage());
            log.error("query city error:{}", e);
        } finally {
            SpringUtils.renderJson(response, JSONUtil.beanToJson(jsonMap));
        }
    }

    @RequestMapping("/getBorrowPage")
    public String getUserPage(HttpServletRequest request,ModelMap model) {
        HashMap<String, Object> params = getParametersO(request);
        String url = "borrow/borrowList";
        try {

            model.addAttribute("provinces", chinaAreaService.selectAllProvince());
            model.addAttribute("scoreCards", riskModelOrderService.findAllScoreCards());
            model.addAttribute("reviewUserInfos", borrowOrderService.findBackReviewUserName());
            if (null != params.get("scoreCard")) {
                String scoreCard = params.get("scoreCard").toString();
                String[] split = scoreCard.split("_");
                if ("all_scorecard".equals(scoreCard)) {
                    params.put("modelCode", "all_scorecard");
                    params.put("variableVersion", "all_scorecard");
                } else {
                    if (2 == split.length) {
                        //联合索引中modelCode在前，variableVersion在后
                        params.put("modelCode", split[0]);
                        params.put("variableVersion", split[1]);
                    }
                }
            }

            Map<String, String> statisticMap = new HashMap<>();
            if (params.containsKey("bType")) {
                // 放款界面看到的状态
                if ("fangk".equals(params.get("bType"))) {
                    params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ,
                            BorrowOrder.STATUS_FKBH, BorrowOrder.STATUS_FKSB));
                    url = "borrow/borrowList_fk";
                    // 风控界面看到的状态
                } else if (params.get("bType").toString().contains("fengk")) {
                    // 初审
                    if ("fengk_Trial".equals(params.get("bType"))) {
                        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_DCS, BorrowOrder.STATUS_CSBH
                                // ,
                                // BorrowOrder.STATUS_JSJJ
                        ));
                        // 复审
                    } else if ("fengk_Review".equals(params.get("bType")) || "fengk_order".equals(params.get("bType"))) {
                        if ("fengk_order".equals(params.get("bType"))) {
                            //我的订单
                            BackUser backUser = loginAdminUser(request);
                            Integer id = backUser.getId();
                            params.put("reviewBackUserId", id);
                            statisticMap = borrowOrderService.getStatisticMap(id);
                            url = "borrow/borrowList_mo";
                        }
                        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_FSBH, BorrowOrder.STATUS_FSTG));
                    } else {

                        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_FSBH,
                                // BorrowOrder.STATUS_JSJJ,
                                BorrowOrder.STATUS_DCS));
                    }

                    // url = "borrow/fk_borrowList";
                }
                model.addAttribute("bType", params.get("bType"));
            }
            PageConfig<BorrowOrder> pageConfig = borrowOrderService.findPage(params);
            if (pageConfig != null && pageConfig.getItems() != null) {

                //新建一个信息的list
                List<BorrowOrder> newBorroList = new ArrayList<>();
                List<BorrowOrder> borrowOrderList = pageConfig.getItems();
                for (BorrowOrder borrowOrder : borrowOrderList) {
                    if (borrowOrder == null) {
                        continue;
                    }
                    //针对
                    Map<String, Object> channelUserIdMap = new HashMap<>();
                    channelUserIdMap.put("userId", borrowOrder.getUserId());
                    Map<String, Object> channelResutlMap = channelInfoService.selectChannelByUserId(channelUserIdMap);
                    if (channelResutlMap == null) {
                        borrowOrder.setChannelName("自然流量");
                    } else {
                        borrowOrder.setChannelName(channelResutlMap.get("channel_name") == null ? null : channelResutlMap.get("channel_name").toString());
                    }
                      //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrder.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        borrowOrder.setLoanCount("首借");
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrder.getUserId());
                        //该用户在还款表中 没有已还款的记录
                        if(loanCount < 1){
                            borrowOrder.setLoanCount("首借");
                        }else{
                            borrowOrder.setLoanCount(loanCount.toString());
                        }
                    }
//                    RiskModelOrder riskOrder = riskModelOrderService.findRiskOrderByBrdId(borrowOrder.getId());
//                    if (null != riskOrder) {
//                        Integer inflexibleAdvice = riskOrder.getInflexibleAdvice();
//                        Integer customerType = riskOrder.getCustomerType();
//                        Integer modelAdvice = riskOrder.getModelAdvice();
//                        if (Integer.valueOf(1).equals(inflexibleAdvice) && Integer.valueOf(0).equals(customerType)) {
//                            if (Integer.valueOf(0).equals(modelAdvice) || Integer.valueOf(2).equals(modelAdvice)) {
//                                //评分卡
//                                borrowOrder.setReviwRiskLabel(1);
//                            } else if (1 == modelAdvice) {
//                                //人工否决:评审人员名
//                                borrowOrder.setReviwRiskLabel(2);
//                            } else {
//                                //评审人员名
//                                borrowOrder.setReviwRiskLabel(3);
//                            }
//                        } else {
//                            //评审人员名
//                            borrowOrder.setReviwRiskLabel(3);
//                        }
//                    } else {
//                        //评审人员名
//                        borrowOrder.setReviwRiskLabel(3);
//                    }
                    newBorroList.add(borrowOrder);
                }
                pageConfig.setItems(newBorroList);
            }

            model.addAttribute("pm", pageConfig);
            model.addAttribute("statisticMap", statisticMap);
            // model.addAttribute("borrowStatusMap",
            // BorrowOrder.borrowStatusMap);
            //添加新老用户参与搜索
            HashMap<Integer, String> hashMap = new HashMap<>();
            hashMap.put(0, "新用户");
            hashMap.put(1, "老用户");
            model.addAttribute("customerType", hashMap);
            //添加用户流量搜索
            List<ChannelInfo> channelInfoList = channelInfoService.findAll(new HashMap<>());
            if (channelInfoList == null) {
                channelInfoList = new ArrayList<>();
            }
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.setId(0);
            channelInfo.setChannelName("自然流量");
            channelInfoList.add(0, channelInfo);
            model.addAttribute("channelInfoList", channelInfoList);
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        if ("fengk_order".equals(params.get("bType")) && model.get("isButton") != null) {
            int myId = Integer.parseInt(params.get("myId") + "");
            params.put("myId", myId - 1);
            params.remove("parentId");
        }
        model.addAttribute("params", params);
        String appName = PropertiesUtil.get("APP_NAME");
        model.addAttribute("appName", appName);
        return url;
    }

    public <T> void fatherToChild(T father, T child) throws Exception {
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Exception("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Method method = fatherClass.getDeclaredMethod("get" + upperHeadChar(field.getName()));
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child, obj);
        }
    }

    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    public String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }

    @RequestMapping("acquireReviewOrders")
    public String acquireReviewOrders(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            BackUser backUser = loginAdminUser(request);
            boolean isSuccess = borrowOrderService.distributeBorrowOrderForReview(backUser);
            model.addAttribute("params", params);
            model.addAttribute("isButton", true);
            if (!isSuccess) {
                model.addAttribute("message", "下单失败,请先处理完订单再下单");
                model.addAttribute("notAlert", "1");
            }
        } catch (Exception e) {
            log.error("acquireReviewOrders error:{}", e);
        }
        return getUserPage(request, model);
    }

    @RequestMapping("getBorrowPageByStatus/{borrowStatus}")
    public String getBorrowPageByParams(HttpServletRequest request, HttpServletResponse response, @PathVariable("borrowStatus") String borrowStatus,
                                        Model model) {

        String url = "borrow/borrowList";
        try {

            HashMap<String, Object> params = getParametersO(request);
            params.put("borrowStatusStr", borrowStatus);
            params.put("borrowStatusArray", borrowStatus.split(","));// 格式1,1,3.5
            if (params.containsKey("bType")) {
                if ("fangk".equals(params.get("bType"))) {// 放款界面看到的状态
                    url = "borrow/borrowList_fk";
                } else if (params.get("bType").toString().contains("fengk")) {// 风控界面看到的状态
                    if ("fengk_Trial".equals(params.get("bType"))) {// 初审
                    } else if ("fengk_Review".equals(params.get("bType"))) {// 复审
                    } else {
                    }
                } else if ("duizhang".equals(params.get("bType"))) {// 放款界面看到的状态
                    Long moneyAmountSum = borrowOrderService.findMoneyAmountSucSum(params);
                    Long intoMoneySum = borrowOrderService.findIntoMoneySucSum(params);
                    model.addAttribute("moneyAmountSum", moneyAmountSum);
                    model.addAttribute("intoMoneySum", intoMoneySum);
                    url = "borrow/borrowList_dz";
                }
                model.addAttribute("bType", params.get("bType"));

            }

            PageConfig<BorrowOrder> pageConfig = borrowOrderService.findPage(params);
            List<BorrowOrder> list = new ArrayList<BorrowOrder>();
            for(BorrowOrder borrowOrder : pageConfig.getItems()){
                //查询用户成功借款次数
                Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrder.getUserId());
                //该用户在还款表中无记录
                if(loanSucCount != null && loanSucCount < 1 ){
                    borrowOrder.setLoanCount("首借");
                }else{
                    Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrder.getUserId());
                    //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                    if(loanCount < 1){
                        borrowOrder.setLoanCount("首借");
                    }else{
                        borrowOrder.setLoanCount(loanCount.toString());
                    }
                }
                list.add(borrowOrder);
            }
            pageConfig.setItems(list);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            String appName = PropertiesUtil.get("APP_NAME");
            model.addAttribute("appName", appName);
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        return url;
    }

    @RequestMapping("getBorrowDetailById")
    public String getBorrowDetailById(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = "borrow/borrowdetail";
        String appName = PropertiesUtil.get("APP_NAME");
        try {
            jxDetail(request, model);
        } catch (Exception e) {
            log.error("getBorrowDetailById error:{}", e);
        }
        model.addAttribute("appName", appName);
        return url;
    }

    /**
     * 坚持放款
     * @param type
     * @return
     */
    @RequestMapping("insistlending")
    @ResponseBody
    public String insistlending(String type,String borrowId){
        try{
            if(StringUtils.isNotBlank(type) && "0".equals(type)){
                BorrowOrder  borrow = borrowOrderService.findOneBorrow(Integer.valueOf(borrowId));
                if (BorrowOrder.borrowStatusMap_kefangkuan.contains(borrow.getStatus())) {
                    return "此状态不可操作放款";
                }
                User user = userService.searchByUserid(borrow.getUserId());
                if (user.getStatus().equals(User.USER_STATUS_THREE)) {
                    return "此用户已经被注销，不可操作放款";
                }

                //坚持放款 修改asset_borrow_order数据表中的状态为 待放款
                BorrowOrder borrowOrder = new BorrowOrder();
                borrowOrder.setId(Integer.valueOf(borrowId));
                borrowOrder.setVerifyReviewTime(new Date());
                borrowOrder.setVerifyReviewUser("人工信审，人审放款");
                borrowOrder.setStatus(22);
                borrowOrderService.updateById(borrowOrder);

                HashMap<String, Object> borrowMap = new HashMap<>();
                borrowMap.put("USER_ID", user.getId());
                borrowMap.put("BORROW_STATUS", "1");
                indexDao.updateInfoUserInfoBorrowStatus(borrowMap);

                User newUser = new User();
                newUser.setId(user.getId());
                int i = Integer.parseInt(user.getAmountAvailable()) - borrow.getMoneyAmount();
                newUser.setAmountAvailable(String.valueOf(i<=0?0:i));
                userService.updateAmountAvailableByUserId(newUser);


                //添加order_change_log表
                OrderLogModel orderLogModel = new OrderLogModel();
                orderLogModel.setUserId(user.getId());
                orderLogModel.setBorrowId(String.valueOf(borrow.getId()));
                orderLogModel.setOperateType(OperateType.BORROW.getCode());
                orderLogModel.setAction(OrderChangeAction.MAN_AUDITING.getCode());
                orderLogModel.setBeforeStatus(String.valueOf(borrow.getStatus()));
                orderLogModel.setAfterStatus("22");
                orderLogModel.setCreateTime(new Date());
                orderLogModel.setRemark(OrderChangeAction.MAN_AUDITING.getMessage());
                orderLogService.addNewOrderChangeLog(orderLogModel);
                return "success";
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e+"坚持放款");
            return "系统错误，请联系系统管理员";
        }
        return "success";
    }

    private void jxDetail(HttpServletRequest request, Model model) throws Exception {


        HashMap<String, Object> params = this.getParametersO(request);
        Integer id = Integer.valueOf(String.valueOf(params.get("id")));
        BorrowOrder borrow = null;
        try {
            borrow = borrowOrderService.findOneBorrow(id);
            if (null == borrow) {
                borrow = new BorrowOrder();
            }
            model.addAttribute("borrow", borrow);
        } catch (Exception e) {
            log.info("get asset_borrow_order error:{}", e);
        }
        /*
         * 6.20 修改借款历史展示多马甲数据（通过数据中心接口获取）
         */
        if (borrow.getIdNumber() != null && !"".equals(borrow.getIdNumber())) {
            try {
                log.info("idNumber for borrow list:{}", borrow.getIdNumber());
                List<BorrowOrder> userBorrows = new ArrayList<>();
                Result<List<MbgAssetBorrowOrder>> asetBorOrdLstResults = dataWareHouseService.queryOrdersByIdNumberAndProjectName(borrow.getIdNumber(), null);
                List<MbgAssetBorrowOrder> MbgBorListResult = asetBorOrdLstResults.getModel();
                log.info("MbgBorListResult :{}",JSON.toJSONString(MbgBorListResult));
                if (null != MbgBorListResult && 0 < MbgBorListResult.size()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (MbgAssetBorrowOrder borrowOrdSrc : MbgBorListResult) {
                        BorrowOrder borrowOrd = new BorrowOrder();
                        CopyUtil.copyProperties(borrowOrdSrc, borrowOrd);
                        String loanTime = borrowOrdSrc.getLoanTime();
                        String loanEndTime = borrowOrdSrc.getLoanEndTime();
                        String verifyReviewTime = borrowOrdSrc.getVerifyReviewTime();
                        if (null != loanTime && !"".equals(loanTime)) {
                            borrowOrd.setLoanTime(sdf.parse(loanTime));
                        }
                        if (null != loanEndTime && !"".equals(loanEndTime)) {
                            borrowOrd.setLoanEndTime(sdf.parse(loanEndTime));
                        }
                        if (null != verifyReviewTime && !"".equals(verifyReviewTime)) {
                            borrowOrd.setVerifyReviewTime(sdf.parse(verifyReviewTime));
                        }
                        borrowOrd.setPaystatus(borrowOrdSrc.getPayStatus());
                        borrowOrd.setAutoLoanFlag(Integer.valueOf(borrowOrdSrc.getAutoLoanFlag()));
                        borrowOrd.setProjectNameVal(PropertiesUtil.get(borrowOrd.getProjectName()));
                        userBorrows.add(borrowOrd);
                    }
                }
                if (userBorrows != null && 0 < userBorrows.size()) {
                    delRiskRemark(userBorrows);

                    //按照马甲名称进行排序，从小到大升序
                    Collections.sort(userBorrows, new Comparator<BorrowOrder>() {
                        @Override
                        public int compare(BorrowOrder o1, BorrowOrder o2) {
                            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                        }
                    });

                    model.addAttribute("userBorrows", userBorrows);
                }
            } catch (Exception e) {
                log.error("get borrow history error :{}", e);
            }
        }

        User user = userService.searchByUserid(borrow.getUserId());
        String cardNo = user.getIdNumber();
        if (cardNo != null && cardNo.length() >= 18) {
            String yy = cardNo.substring(6, 10); // 出生的年份
            String mm = cardNo.substring(10, 12); // 出生的月份
            String dd = cardNo.substring(12, 14); // 出生的日期
            String birthday = yy.concat("-").concat(mm).concat("-").concat(dd);
            model.addAttribute("birthday", birthday);
            model.addAttribute("age", SpringUtils.calculateAge(birthday, "yyyy-MM-dd"));
        }
        user.setEducation(User.EDUCATION_TYPE.get(user.getEducation()));
        user.setPresentPeriod(User.RESIDENCE_DATE.get(user.getPresentPeriod()));
        user.setMaritalStatus(User.MARRIAGE_STATUS.get(user.getMaritalStatus()));
        user.setFristContactRelation(User.CONTACTS_FAMILY.get(user.getFristContactRelation()));
        user.setSecondContactRelation(User.CONTACTS_OTHER.get(user.getSecondContactRelation()));
        model.addAttribute("user", user);

        model.addAttribute("operatorHtml", userService.selectGxbReportDataHtml(Integer.parseInt(user.getId())));//运营商

        UserCardInfo info = userBankService.findBankCardByCardNo(borrow.getCardNo());
        model.addAttribute("bankCard", info);
        List<UserInfoImage> userInfoImage = this.userInfoImageService.selectImageByUserId(borrow.getUserId());
        model.addAttribute("InfoImage", userInfoImage);
        model.addAttribute("params", params);

        // 查询征信信息
//        RiskCreditUser riskCreditUser = riskUserService.findRiskCreditUserByAssetsId(String.valueOf(id));
////        if (null != riskCreditUser) {
////            model.addAttribute("riskCreditUser", riskCreditUser);
////        }
        //风控审核建议
//        RiskModelOrder riskModelOrder = new RiskModelOrder();
//        Map<String, Object> riskParams = new HashMap<>();
//        riskParams.put("borrowOrderId", id);
//        riskModelOrder = riskModelOrderDao.findOneByParams(riskParams);
//        model.addAttribute("riskModelOrder", riskModelOrder);
        Map<String, Object> commonMap = commonFunction(user, borrow);
        //风控返回分数
        Integer score =repaymentDetailService.findRiskScore(Integer.valueOf(user.getId()));
        model.addAttribute("score",score);
        model.addAllAttributes(commonMap);
    }

    /**
     * 查询逾期已还款订单记录
     *
     * @return
     */
    @RequestMapping("getOverDueHistoryList")
    public String getOverDueHistoryList(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<MbgMmanLoanCollectionRecordVo> volist = null;
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            String projectName = String.valueOf(params.get("projectName"));
            Integer id = Integer.valueOf(String.valueOf(params.get("id")));

            String md5Code = MD5Util.encrypt(id);
            String orderId = String.valueOf(id);
            List<MbgMmanLoanCollectionRecord> list = new ArrayList<MbgMmanLoanCollectionRecord>();
            Result<MbgMmanLoanCollectionOrder> collectionListResult = dataWareHouseService.queryCollectionOrderByLoanIdAndProjectName(orderId, projectName);
            MbgMmanLoanCollectionOrder collectionOrder = collectionListResult.getModel();

            Result<List<MbgAuditCenter>> auditsResult = dataWareHouseService.reductionAuditRecord(orderId, projectName, 2);
            List<MbgAuditCenter> audits = auditsResult.getModel();
            StringBuilder reductionSb = new StringBuilder();

            if (CollectionUtils.isNotEmpty(audits)) {
                audits.forEach(one -> {
                    reductionSb.append(DateUtil.getDateFormat(one.getCreatetime(), "yyyy-MM-dd HH:mm:ss"))
                            .append("&nbsp;:&nbsp;").append(one.getReductionMoney()).append("<br/>");
                });
            }
            MbgCreditLoanPay loanPay = null;
            if (null != collectionOrder) {
                if ("4".equals(collectionOrder.getStatus())) {
                    Map<String, Object> payParams = new HashMap<>();
                    payParams.put("loanId", orderId);
                    payParams.put("projectName", projectName);
                    Result<MbgCreditLoanPay> mbgCreditLoanPayResult = dataWareHouseService.queryCreditLoanPayByOrderIdAndProjectName(orderId, projectName);
                    loanPay = mbgCreditLoanPayResult.getModel();
                }
                Map<String, Object> colRecordParams = new HashMap<>();
                colRecordParams.put("orderId", collectionOrder.getId());
                colRecordParams.put("projectName", projectName);

                Result<List<MbgMmanLoanCollectionRecord>> listResult = dataWareHouseService.queryCollectionRecordByOrderIdAndProjectName(collectionOrder.getId(), projectName);
                list = listResult.getModel();
                volist = new ArrayList<MbgMmanLoanCollectionRecordVo>();
                if (null != list && 0 < list.size()) {
                    for (MbgMmanLoanCollectionRecord one : list) {
                        MbgMmanLoanCollectionRecordVo vo = new MbgMmanLoanCollectionRecordVo();
                        CopyUtil.copyProperties(one, vo);
                        vo.setOverdueDays(collectionOrder.getOverduedays().toString());
                        vo.setRepaymentDescript(loanPay == null ? "" : DateUtil.getDateFormat(loanPay.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
                        vo.setReductionDescript(reductionSb.toString());
                        volist.add(vo);
                    }
                }
            }
        }catch (Exception e){
            Log.error("getOverDueHistoryList happened error!", e);
        }
        if (null != volist && 0 < volist.size()) {
            Log.info(volist.get(0).toString());
        }
        model.addAttribute("items", volist);
        model.addAttribute("params", params);
        return "borrow/overdue_borrow_history_list";
    }

    /**
     * 获取公信宝报告页面
     *
     * @param zmTokenMsg zmTokenMsg
     * @return str
     */
    private String getGongXinBaoUrl(String zmTokenMsg) {
        final String appId = PropertiesUtil.get("TAOBAO_APPID");
//        final String appSecurity = "66fa775cbaeb48058341640683559ed7";
//        final String prefixUrl = "https://prod.gxb.io/datalist.html?";
        long timestamp = System.currentTimeMillis();
        String sign = DigestUtils.md5Hex(String.format("%s%s%s", PropertiesUtil.get("TAOBAO_APPID"), PropertiesUtil.get("TAOBAO_APPSECRET"), timestamp));

        if (zmTokenMsg != null) {
            JSONObject zmObj;
            try {
                zmObj = JSONObject.parseObject(zmTokenMsg);
                if (null != zmObj && zmObj.containsKey("token")) {
                    String zmToken = zmObj.getString("token");
                    return PropertiesUtil.get("TAOBAO_REQUEST_URL") + "appId=" + appId + "&sign=" + sign + "&timestamp="
                            + timestamp + "&token=" + zmToken;
                }
            } catch (Exception e) {
                log.error("getGongXinBaoUrl error:{}", e);
            }
        }
        return null;
    }

//    /**
//     * 获取公信宝报告页面
//     *
//     * @param zmToken zmToken
//     * @return str
//     */
//    private String getGongXinBaoUrlOther(String zmToken) {
//
//        final String appId = "gxbe52b43dda1abea44";
//        final String appSecurity = "2eb2f28cccc84ade9d55a6b58bc9639c";
//        final String prefixUrl = "https://prod.gxb.io/datalist.html?";
//        long timestamp = System.currentTimeMillis();
//        String sign = DigestUtils.md5Hex(String.format("%s%s%s", appId, appSecurity, timestamp));
//        return prefixUrl + "appId=" + appId + "&sign=" + sign + "&timestamp=" + timestamp + "&token=" + zmToken;
//
//    }

    /**
     * 解析数据墨盒
     *
     * @param riskOrders riskOrders
     * @return ShuJuMoHeVo
     */
    private ShuJuMoHeVo parseSjmhVo(RiskOrders riskOrders) {
        ShuJuMoHeVo shuJuMoHeVo = null;
        if (riskOrders != null && riskOrders.getAutoSjmh() != null) {
            String jsonData = riskOrders.getAutoSjmh();
            JsonParser parser = new JsonParser();
            try {
                JsonObject root = parser.parse(jsonData).getAsJsonObject();
                String sjmhData = root.has("shuJuMoHeVoJson") ? root.get("shuJuMoHeVoJson").getAsString() : null;
                Gson gson = new Gson();
                if (sjmhData != null) {
                    shuJuMoHeVo = gson.fromJson(sjmhData, ShuJuMoHeVo.class);

                }
            } catch (Exception e) {
                log.error("parseSjmhVo assetBorrowId:{} error:{}", riskOrders.getAssetBorrowId(), e);
                return null;
            }
        }
        return shuJuMoHeVo;
    }

    private HashMap<String, Object> backConfigParamsMap() {
        HashMap<String, Object> configMap = new HashMap<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sysType", "AUTO_TYPE");
        List<BackConfigParams> list = backConfigParamsDao.findParams(hashMap);
        if (list != null) {
            for (BackConfigParams backConfigParams : list) {
                String sysValue = backConfigParams.getSysValue();
                String sysKey = backConfigParams.getSysKey();
                if (sysKey != null && "AUTO_SWITCH".equals(sysKey)) {
                    configMap.put("AUTO_SWITCH", sysValue);
                }
                if (sysKey != null && "AUTO_LOAN_RATE".equals(sysKey)) {
                    configMap.put("AUTO_LOAN_RATE", sysValue);
                }
            }
        }
        return configMap;
    }

    private void delRiskRemark( List<BorrowOrder> userBorrows){
        for (BorrowOrder borrowOrder : userBorrows) {
            String projectName = borrowOrder.getProjectName();
            RiskModelOrder riskModelOrder = new RiskModelOrder();
            Result<MbgRiskModelOrder> mbgRiskModelOrderResult = dataWareHouseService.queryRiskModelOrderByOrderIdAndProjectName(Integer.valueOf(borrowOrder.getId()), projectName);
            MbgRiskModelOrder riskModelOrderSrc = mbgRiskModelOrderResult.getModel();
            if (null != riskModelOrderSrc) {
                CopyUtil.copyProperties(riskModelOrderSrc, riskModelOrder);
                riskModelOrder.setId(Long.valueOf(riskModelOrderSrc.getId()));
            }

            if (null != riskModelOrder.getId()) {
                Integer inflexibleAdvice = riskModelOrder.getInflexibleAdvice();
                Integer customerType = riskModelOrder.getCustomerType();
                Integer modelAdvice = riskModelOrder.getModelAdvice();
                if (Integer.valueOf(1).equals(inflexibleAdvice) && Integer.valueOf(0).equals(customerType)) {
                    if (Integer.valueOf(0).equals(modelAdvice) || Integer.valueOf(2).equals(modelAdvice)) {
                        borrowOrder.setReviwRiskLabel(1);
                    } else if (1 == modelAdvice) {
                        borrowOrder.setReviwRiskLabel(2);
                    } else {
                        borrowOrder.setReviwRiskLabel(3);
                    }
                } else {
                    borrowOrder.setReviwRiskLabel(3);
                }
            } else {
                borrowOrder.setReviwRiskLabel(3);
            }
        }
    }

    private String saveUpdateBorrowJx(HttpServletRequest request, HttpServletResponse response, Model model, BorrowOrder borrowOrder) throws Exception {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {

                Integer id = Integer.valueOf(String.valueOf(params.get("id")));
                BorrowOrder borrow = borrowOrderService.findOneBorrow(id);
                model.addAttribute("borrow", borrow);
                /*
                 * 6.20 修改借款历史展示多马甲数据（通过数据中心接口获取）
                 */
//                String getBorrowUrl = PropertiesUtil.get("BORROW_LIST_URL");
//                String timeOutString = PropertiesUtil.get("TIME_OUT");
//                Map<String, Object> paramsMap = new HashMap<>();
                if (borrow.getIdNumber() != null && !"".equals(borrow.getIdNumber())) {
                    try {
                        List<BorrowOrder> userBorrows = new ArrayList<BorrowOrder>();
                        Result<List<MbgAssetBorrowOrder>> asetBorOrdLstResults = dataWareHouseService.queryOrdersByIdNumberAndProjectName(borrow.getIdNumber(), null);
                        List<MbgAssetBorrowOrder> MbgBorListResult = asetBorOrdLstResults.getModel();
                        if (null != MbgBorListResult && 0 < MbgBorListResult.size()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            for (MbgAssetBorrowOrder borrowOrdSrc : MbgBorListResult) {
                                BorrowOrder borrowOrd = new BorrowOrder();
                                CopyUtil.copyProperties(borrowOrdSrc, borrowOrd);
                                String loanTime = borrowOrdSrc.getLoanTime();
                                String loanEndTime = borrowOrdSrc.getLoanEndTime();
                                String verifyReviewTime = borrowOrdSrc.getVerifyReviewTime();
                                if (null != loanTime && !"".equals(loanTime)) {
                                    borrowOrd.setLoanTime(sdf.parse(loanTime));
                                }
                                if (null != loanEndTime && !"".equals(loanEndTime)) {
                                    borrowOrd.setLoanEndTime(sdf.parse(loanEndTime));
                                }
                                if (null != verifyReviewTime && !"".equals(verifyReviewTime)) {
                                    borrowOrd.setVerifyReviewTime(sdf.parse(verifyReviewTime));
                                }
                                borrowOrd.setPaystatus(borrowOrdSrc.getPayStatus());
                                borrowOrd.setAutoLoanFlag(Integer.valueOf(borrowOrdSrc.getAutoLoanFlag()));
                                borrowOrd.setProjectNameVal(PropertiesUtil.get(borrowOrd.getProjectName()));
                                userBorrows.add(borrowOrd);
                            }
                            if (userBorrows != null && 0 < userBorrows.size()) {
                                delRiskRemark(userBorrows);
                                //按照马甲名称进行排序，从小到大升序
                                Collections.sort(userBorrows, new Comparator<BorrowOrder>() {
                                    @Override
                                    public int compare(BorrowOrder o1, BorrowOrder o2) {
                                        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                                    }
                                });
                                log.info("userBorrows:{}", JSON.toJSONString(userBorrows));
                            }
                            model.addAttribute("userBorrows", userBorrows);
                        }
                    } catch (Exception e) {
                        log.error("get borrow history error :{}", e);
                    }
                }
                User user = userService.searchByUserid(borrow.getUserId());
                String cardNo = user.getIdNumber();
                if (cardNo != null && cardNo.length() >= 18) {
                    // 出生的年份
                    String yy = cardNo.substring(6, 10);
                    // 出生的月份
                    String mm = cardNo.substring(10, 12);
                    // 出生的日期
                    String dd = cardNo.substring(12, 14);
                    String birthday = yy.concat("-").concat(mm).concat("-").concat(dd);
                    model.addAttribute("birthday", birthday);
                    model.addAttribute("age", SpringUtils.calculateAge(birthday, "yyyy-MM-dd"));
                }
                user.setEducation(User.EDUCATION_TYPE.get(user.getEducation()));
                user.setPresentPeriod(User.RESIDENCE_DATE.get(user.getPresentPeriod()));
                user.setMaritalStatus(User.MARRIAGE_STATUS.get(user.getMaritalStatus()));
                user.setFristContactRelation(User.CONTACTS_FAMILY.get(user.getFristContactRelation()));
                user.setSecondContactRelation(User.CONTACTS_OTHER.get(user.getSecondContactRelation()));
                model.addAttribute("user", user);
                UserCardInfo info = userBankService.findBankCardByCardNo(borrow.getCardNo());
                model.addAttribute("bankCard", info);
                List<UserInfoImage> userInfoImage = this.userInfoImageService.selectImageByUserId(borrow.getUserId());
                model.addAttribute("InfoImage", userInfoImage);
                model.addAttribute("params", params);
                // 查询征信信息
                RiskCreditUser riskCreditUser = riskUserService.findRiskCreditUserByAssetsId(String.valueOf(id));
                if (null != riskCreditUser) {
                    model.addAttribute("riskCreditUser", riskCreditUser);
                }
                // 规则查询
                List<RiskRuleCal> riskRuleCalList = riskUserService.findRiskRuleCalByAssetsId(String.valueOf(id));
                model.addAttribute("riskRuleCalList", riskRuleCalList);
                url = "borrow/borrow_check";
                if (params.containsKey("bType")) {
                    if ("fangk".equals(params.get("bType"))) {
                        //放款审核：放款
                        url = "borrow/borrow_fangk_check";
                    } else if (params.get("bType").toString().contains("fengk")) {
                        if ("fengk_Trial".equals(params.get("bType"))) {
                            //机审订单：审核
                            url = "borrow/borrow_trial_check";
                        } else if ("fengk_Review".equals(params.get("bType"))) {
                            //人工复审：审核    我的订单：审核
                            url = "borrow/borrow_review_check";
                        }
                    }
                    model.addAttribute("bType", params.get("bType"));
                }

            } else {
                // // 更新或者添加操作
                if (borrowOrder.getId() != null) {
                    String key = BorrowOrder.REVIEW_BORROW + borrowOrder.getId();
                    String value = key;

                    BackUser backUser = loginAdminUser(request);
                    if (BackUser.MSG_ON.equals(backUser.getMsgFlag())) {
                        taskJob.reviewRemind(backUser.getUserName());
                        backUser.setMsgFlag(BackUser.MSG_OFF);
                        //保存登录用户信息
                        updateSessionUser(request, backUser);
                    }
                    if (jedisCluster.get(key) != null) {
                        SpringUtils.renderDwzResult(response, false, "操作失败,原因：该订单正在被审核，请稍后操作！", DwzResult.CALLBACK_CLOSECURRENT,
                                params.get("parentId").toString());
                    } else {

                        jedisCluster.setex(BorrowOrder.REVIEW_BORROW + borrowOrder.getId(), BorrowOrder.REVIEW_BORROW_SECOND, value);
                        Date nowDate = new Date();
                        BorrowOrder borrowOrderR = borrowOrderService.findOneBorrow(borrowOrder.getId());
                        Date updateTime = borrowOrderR.getUpdatedAt();
                        if ((BorrowOrder.borrowStatusMap_shenheFail.containsKey(borrowOrderR.getStatus()) && !BorrowOrder.STATUS_CSBH.equals(borrowOrderR.getStatus()))
                                || BorrowOrder.STATUS_HKZ.equals(borrowOrderR.getStatus())
                                ) {
                            // 放款成功的不让再审核
                            // 审核失败的不让再继续审核了,除了初审驳回
                            SpringUtils.renderDwzResult(response, false, "当前状态为:<b>" + borrowOrderR.getStatusName() + "</b>,该借款状态不支持继续审核！",
                                    DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        } else {
                            //处于放款状态或正在放款支付中的无法进行更新订单操作
                            if (BorrowOrder.STATUS_FKZ.equals(borrowOrderR.getStatus()) && (BorrowOrder.SUB_SUBMIT.equals(borrowOrderR.getPaystatus()) || BorrowOrder.SUB_PAY_CSZT.equals(borrowOrderR.getPaystatus()))) {
                                SpringUtils.renderDwzResult(response, false, "操作失败,原因：该订单处于放款或支付处理中，不能被进行此操作，如有疑问请联系技术人员！",
                                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                                model.addAttribute("params", params);
                                return url;
                            }

                            borrowOrder.setUpdatedAt(nowDate);
                            if ("fengk_Trial".equals(params.get("bType"))) {
                                // 初审
                                borrowOrder.setVerifyTrialTime(nowDate);
                                borrowOrder.setVerifyTrialRemark(borrowOrder.getRemark());
                                borrowOrder.setVerifyTrialUser(backUser.getUserAccount());
                            } else if ("fengk_Review".equals(params.get("bType"))) {
                                // 复审
                                borrowOrder.setVerifyReviewTime(nowDate);
                                borrowOrder.setVerifyReviewRemark(borrowOrder.getRemark());
                                borrowOrder.setVerifyReviewUser(backUser.getUserAccount());
                                riskModelOrderService.updateRiskModelOrder(borrowOrder.getId(), borrowOrder.getStatus(),
                                        backUser.getUserAccount(), borrowOrder.getRemark(), nowDate);
                            } else if ("fangk".equals(params.get("bType"))) {
                                // 放款审核
                                backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "fangkVerifyLoan", borrowOrderR.getId()
                                        .toString()));
                                borrowOrder.setVerifyLoanTime(new Date());
                                borrowOrder.setVerifyLoanRemark(borrowOrder.getRemark());
                                borrowOrder.setVerifyLoanUser(backUser.getUserAccount());

                            }
                            // 重新放款
                            if (borrowOrder.getStatus() == Integer.parseInt(BorrowOrder.STATUS_FKZ + "2")) {
                                boolean checkIsApproved = DateUtil.checkIsApproved(nowDate, updateTime);

                                if (checkIsApproved) {
                                    SpringUtils.renderDwzResult(response, false, "该借款半小时内不支持重复审核！",
                                            DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                                    model.addAttribute("params", params);
                                    return url;
                                }
                                backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "againToBatchSign", borrowOrderR.getId()
                                        .toString()));
                                List<Repayment> lists = repaymentService.findAllByBorrowId(borrowOrderR.getId());
                                if (lists == null || lists.size() < 1) {
                                    if (borrowOrderR.getStatus().intValue() != BorrowOrder.STATUS_HKZ
                                            && !borrowOrderR.getPaystatus().equals(BorrowOrder.SUB_PAY_CSZT)
                                            && (borrowOrderR.getStatus().intValue() == BorrowOrder.STATUS_FKZ || borrowOrderR.getStatus().intValue() == BorrowOrder.STATUS_FKSB)) {
                                        borrowOrder.setYurref(GenerateNo.payRecordNo("A"));
                                        borrowOrder.setSerialNo(GenerateNo.generateShortUuid(10));
                                        borrowOrder.setPaystatus(BorrowOrder.SUB_PAY_CSZT);
                                        borrowOrder.setStatus(BorrowOrder.STATUS_FKZ);
                                        borrowOrder.setPayRemark("");
                                        borrowOrder.setRemark("重新放款：" + borrowOrder.getRemark() + ";上次招行订单号：" + borrowOrderR.getYurref() + ";上次订单号：" + borrowOrderR.getSerialNo() + ";上次代付流水号：" + borrowOrderR.getOutTradeNo());
                                        borrowOrder.setVerifyLoanTime(new Date());
                                        borrowOrder.setVerifyLoanRemark("重新放款：" + borrowOrder.getRemark() + borrowOrder.getVerifyLoanRemark());
                                    } else {
                                        SpringUtils.renderDwzResult(response, false, "操作失败,原因：该订单不能被进行重新放款操作，如有疑问请联系技术人员！",
                                                DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                                        model.addAttribute("params", params);
                                        return url;
                                    }
                                } else {
                                    SpringUtils.renderDwzResult(response, false, "操作失败,原因：该订单不能被进行重新放款操作，如有疑问请联系技术人员！",
                                            DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                                    model.addAttribute("params", params);
                                    return url;
                                }
                            }
                            try{
                                OrderLogModel orderLogModel = new OrderLogModel();
                                orderLogModel.setUserId(String.valueOf(borrowOrderR.getUserId()));
                                Date now = new Date();
                                orderLogModel.setCreateTime(now);
                                orderLogModel.setUpdateTime(now);
                                orderLogModel.setBorrowId(String.valueOf(borrowOrder.getId()));
                                orderLogModel.setOperateType(OperateType.BORROW.getCode());
                                orderLogModel.setBeforeStatus(String.valueOf(borrowOrderR.getStatus()));
                                orderLogModel.setAfterStatus(String.valueOf(borrowOrder.getStatus()));
                                if ("fengk_Trial".equals(params.get("bType"))) {
                                    orderLogModel.setAction(OrderChangeAction.MACHINE_AUDITING.getCode());
                                    orderLogModel.setRemark(OrderChangeAction.MACHINE_AUDITING.getMessage());

                                } else if ("fengk_Review".equals(params.get("bType"))) {
                                    orderLogModel.setAction(OrderChangeAction.MAN_AUDITING.getCode());
                                    orderLogModel.setRemark(OrderChangeAction.MAN_AUDITING.getMessage());
                                } else if ("fangk".equals(params.get("bType"))) {
                                    orderLogModel.setAction(OrderChangeAction.FANGK_AUDITING.getCode());
                                    orderLogModel.setRemark(OrderChangeAction.FANGK_AUDITING.getMessage());
                                }
                                orderLogService.addNewOrderChangeLog(orderLogModel);
                            }catch (Exception e){
                                log.error("add order log error:{}",e);
                            }
                            borrowOrderService.updateById(borrowOrder);
                            if (borrowOrderR.getStatus().equals(BorrowOrder.STATUS_DCS)) {
                                RiskCreditUser riskCreditUser = new RiskCreditUser();
                                riskCreditUser.setAssetId(borrowOrderR.getId());
                                riskCreditUser.setRiskStatus(6);
                                borrowOrderService.updateRiskCreditUserById(riskCreditUser);
                            } else if (borrowOrderR.getStatus().equals(BorrowOrder.STATUS_CSBH)) {
                                // 初审驳回后被救回
                                RiskCreditUser riskCreditUser = new RiskCreditUser();
                                riskCreditUser.setAssetId(borrowOrderR.getId());
                                riskCreditUser.setRiskStatus(7);
                                borrowOrderService.updateRiskCreditUserById(riskCreditUser);

                                // 初审驳回后被救回需要冻结借款金额
                                User user = userService.searchByUserid(borrowOrderR.getUserId());
                                User newUser = new User();
                                newUser.setId(user.getId());
                                newUser.setAmountAvailable(String.valueOf(Integer.valueOf(user.getAmountAvailable()) - borrowOrderR.getMoneyAmount()));
                                newUser.setUpdateTime(nowDate);
                                userService.updateByPrimaryKeyUser(newUser);
                                log.info("kou UserLimit sucess 初审驳回后被救回扣除用户可用额度！");

                            }
                            /**
                             * 2017-02-20 zjb
                             */
                            if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FSTG || borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FSBH
                                    || borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FKBH || borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FKZ
                                    || borrowOrder.getStatus() == -50) {//驳回并加入黑名单
                                log.error("update riskUser because:{}", borrowOrder.getStatus());
                                RiskCreditUser riskCreditUser = new RiskCreditUser();
                                riskCreditUser.setAssetId(borrowOrderR.getId());
                                if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FSTG) {
                                    riskCreditUser.setRiskStatus(10);
                                } else if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FSBH) {
                                    riskCreditUser.setRiskStatus(11);
                                } else if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FKBH) {
                                    riskCreditUser.setRiskStatus(12);
                                } else if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FKZ) {
                                    riskCreditUser.setRiskStatus(13);
                                } else if (borrowOrder.getStatus() == -50) {
                                    //驳回并加入黑名单
                                    riskCreditUser.setRiskStatus(11);
                                    User user = new User();
                                    Integer id = Integer.valueOf(String.valueOf(params.get("id")));
                                    BorrowOrder borrow = borrowOrderService.findOneBorrow(id);
                                    user.setId(borrow.getUserId() + "");
                                    //2黑名单
                                    user.setStatus("2");
                                    int count = this.userService.updateByPrimaryKeyUser(user);
                                }

                                borrowOrderService.updateRiskCreditUserById(riskCreditUser);
                            }

                            // 审核失败，退回可用额度
                            if (BorrowOrder.borrowStatusMap_shenheFail.containsKey(borrowOrder.getStatus())) {

                                User user = userService.searchByUserid(borrowOrderR.getUserId());
                                User newUser = new User();
                                newUser.setId(user.getId());
                                newUser.setAmountAvailable(String.valueOf(Integer.valueOf(user.getAmountAvailable()) + borrowOrderR.getMoneyAmount()));
                                newUser.setUpdateTime(nowDate);
                                userService.updateByPrimaryKeyUser(newUser);
                                log.info("return UserLimit sucess 审核不通过恢复用户可用额度！");
                            }

                            // 测试放款成功 xxxxxxx xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                            if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_HKZ) {

                                Date fkDate = new Date();
                                borrowOrderR.setLoanTime(fkDate);
                                borrowOrderR.setRemark("手动放款成功");
                                borrowOrderR.setPaystatus(BorrowOrder.SUB_PAY_SUCC);
                                borrowOrderR.setPayRemark("手动放款成功");
                                borrowOrderR.setStatus(BorrowOrder.STATUS_HKZ);
                                // 放款时间加上借款期限
                                borrowOrderR.setLoanEndTime(DateUtil.addDay(fkDate, borrowOrderR.getLoanTerm()));
                                repaymentService.insertByBorrorOrder(borrowOrderR);
                                borrowOrderService.addBorrowChecking(borrowOrderR);
                            }
                            jedisCluster.del(key);
                            SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (url == null) {

                SpringUtils
                        .renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
            log.error("添加权限信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);

        Integer id = Integer.valueOf(String.valueOf(params.get("id")));
        BorrowOrder borrowDetail = borrowOrderService.findOneBorrow(id);
        User user2 = userService.searchByUserid(borrowDetail.getUserId());
        //风控审核建议
        RiskModelOrder riskModelOrder;
        Map<String, Object> riskParams = new HashMap<>();
        riskParams.put("borrowOrderId", id);
        riskModelOrder = riskModelOrderDao.findOneByParams(riskParams);
        model.addAttribute("riskModelOrder", riskModelOrder);

        Map<String, Object> commonMap = commonFunction(user2, borrowDetail);
        model.addAllAttributes(commonMap);
        return url;
    }

    /**
     * @param request     req
     * @param response    res
     * @param model       model
     * @param borrowOrder order
     * @return str
     */
    @RequestMapping("saveUpdateBorrow")
    public String saveUpdateBorrow(HttpServletRequest request, HttpServletResponse response, Model model, BorrowOrder borrowOrder) throws Exception {
        String appName = PropertiesUtil.get("APP_NAME");
        model.addAttribute("appName", appName);
        BorrowOrder borrow = borrowOrderService.findOneBorrow(borrowOrder.getId());
        //风控返回分数
        User user = userService.searchByUserid(borrow.getUserId());
        Integer score =repaymentDetailService.findRiskScore(Integer.valueOf(user.getId()));
        model.addAttribute("score",score);
        try {
            return saveUpdateBorrowJx(request, response, model, borrowOrder);
        } catch (Exception e) {
            log.error("saveUpdateBorrow error:{}", e);
        }
        return null;
    }
    /**
     * 三个主要信审界面中提取的共同方法
     *
     * @param user        user
     * @param borrowOrder order
     */
//    private Map<String, Object> commonFunction(User user, BorrowOrder borrowOrder, String projectName) {
//        log.info("commonFunction start borrowOrderId=:{}", borrowOrder.getId());
//        BussinessLogUtil.info("commonFunction start borrowOrderId=" + borrowOrder.getId());
//        Map<String, Object> resultMap = new HashMap<>();
//
//        if (null == user.getId()) {
//            return resultMap;
//        }
//
//        //公信宝报告展示url
//        String gongXinBaoUrl = getGongXinBaoUrl(user.getZmToken());
//        if (gongXinBaoUrl != null) {
//            resultMap.put("gongXinBaoUrl", gongXinBaoUrl);
//        }
//
//
//        //查询用户的通讯录
//        HashMap<String, Object> contactSqlMap = new HashMap<>();
//        contactSqlMap.put("userId", user.getId());
//        List<UserContacts> userContactsList = new ArrayList<UserContacts>();
//        Result<List<MbgUserContacts>> listResult = dataWareHouseService.queryUserContactsByUserIdAndProjectName(Integer.valueOf(user.getId()), projectName);
//        List<MbgUserContacts> userContactsListSrc = listResult.getModel();
//        if (null != userContactsListSrc && 0 < userContactsListSrc.size()) {
//            for (MbgUserContacts contacts : userContactsListSrc) {
//                UserContacts usrContacts = new UserContacts();
//                CopyUtil.copyProperties(contacts, usrContacts);
//                userContactsList.add(usrContacts);
//            }
//        }
//
//
//        //查询risk_orders中存储的风控信息
//        HashMap<String, Object> riskOrdersSqlMap = new HashMap<>();
//        riskOrdersSqlMap.put("assetBorrowId", borrowOrder.getId());
//        RiskOrders riskOrders = new RiskOrders();//queryRiskOrdersByOrderNoAndProjectName
//        Result<MbgRiskOrders> mbgRiskOrdersResult = dataWareHouseService.queryRiskOrdersByOrderIdAndProjectName(borrowOrder.getId(), projectName);
//        MbgRiskOrders riskOrdersSrc = mbgRiskOrdersResult.getModel();
//        if (null != riskOrdersSrc) {
//            CopyUtil.copyProperties(riskOrdersSrc, riskOrders);
//            riskOrders.setReturnParams(riskOrdersSrc.getReturnParams());
//            riskOrders.setId(riskOrdersSrc.getId());
//        }
//
//
//        if (riskOrders.getId() != null && riskOrders.getReturnParams() != null) {
//            //Json反解析报文数据
//            Gson gson = new Gson();
//            CreditReport creditReport = gson.fromJson(riskOrders.getReturnParams(), CreditReport.class);
//
//            //获取全部风控报文的解析数据
//            Map<Supplier, Map<String, Object>> reasonMessage = RiskCreditReasonUtil.getReasonMessage(creditReport);
//
//            reasonMessage.forEach((k, v) -> {
//                String supplierString = k.toString();
//                resultMap.put(supplierString + "Model", v);
//            });
//            //先从return_params中取，取不到从auto_sjmh中取
//            ShuJuMoHeVo shuJuMoHeVo = autoRiskService.getShuJuMoHe(riskOrders);
//            boolean autoSjmhFlag = false;
//            if (shuJuMoHeVo == null) {
//                autoSjmhFlag = true;
//                shuJuMoHeVo = parseSjmhVo(riskOrders);
//            }
//
//            Map<String, String> riskCreditReasonMessage = RiskCreditReasonUtil.getRiskCreditReasonMessage(shuJuMoHeVo, creditReport, autoSjmhFlag);
//            resultMap.put("inflexibleAdvice", riskCreditReasonMessage.get("inflexibleAdvice"));
//            resultMap.put("inflexibleAdviceReason", riskCreditReasonMessage.get("inflexibleAdviceReason"));
//            resultMap.put("oldCustomerAdvice", riskCreditReasonMessage.get("oldCustomerAdvice"));
//            resultMap.put("oldCustomerAdviceReason", riskCreditReasonMessage.get("oldCustomerAdviceReason"));
//
//            if (shuJuMoHeVo != null) {
//                shuJuMoHeVo.setRealname(user.getRealname());
//                shuJuMoHeVo.setPhoneNumber(user.getUserPhone());
//                shuJuMoHeVo.setLocal(user.getPresentAddress());
//            }
//            resultMap.put("SJMHModel", shuJuMoHeVo);
//        }
//
//        //渠道商信息
//        ChannelInfo channelInfo = null;
////        HashMap<String, Object> channelMap = new HashMap<>();
//
//        //获取渠道商的id
//        String userFrom = user.getUserFrom();
//        String inviteUserId = user.getInviteUserid();
//        if (userFrom != null && !"0".equals(userFrom)) {
//            String channelName = null;
//
//            //查询渠道
//            if (userFrom.matches("[0-9]+")) {
//                Integer channelId = Integer.valueOf(userFrom);
////                channelMap.put("id", channelId);
//                channelInfo = new ChannelInfo();
//                Result<MbgChannelInfo> mbgChannelInfoResult = dataWareHouseService.queryChannelInfoByIdAndProjectName(channelId, projectName);
//                MbgChannelInfo channelInfoSrc = mbgChannelInfoResult.getModel();
//                if (null != channelInfoSrc) {
//                    CopyUtil.copyProperties(channelInfoSrc, channelInfo);
//                }
//                channelName = channelInfo.getId() == null ? null : channelInfo.getChannelName();
//            } else {
//                channelName = "渠道号异常" + userFrom;
//            }
//            resultMap.put("channelName", channelName);
//        } else if (StringUtils.isNotEmpty(inviteUserId) && "null" != inviteUserId) {
//            User inviteUser = userService.searchByUserid(Integer.parseInt(inviteUserId));
//            resultMap.put("inviteUser", inviteUser);
//        }
//
//        //展示通讯录
//        resultMap.put("contactList", userContactsList);
//
//        log.info("commonFunction end borrowOrderId={}", borrowOrder.getId());
//        return resultMap;
//    }

    /**
     * 三个主要信审界面中提取的共同方法
     *
     * @param user        user
     * @param borrowOrder order
     */
    private Map<String, Object> commonFunction(User user, BorrowOrder borrowOrder) {
        log.info("commonFunction start borrowOrderId=:{}", borrowOrder.getId());
        BussinessLogUtil.info("commonFunction start borrowOrderId=" + borrowOrder.getId());
        Map<String, Object> resultMap = new HashMap<>();

        //查询用户的通讯录
        HashMap<String, Object> contactSqlMap = new HashMap<>();
        contactSqlMap.put("userId", user.getId());
        List<UserContacts> userContactsList = userContactsDao.selectUserContacts(contactSqlMap);


        //渠道商信息
        ChannelInfo channelInfo = null;
        HashMap<String, Object> channelMap = new HashMap<>();

        //获取渠道商的id
        String userFrom = user.getUserFrom();
        String inviteUserId = user.getInviteUserid();
        if (userFrom != null && !"0".equals(userFrom)) {
            String channelName = null;
            //查询渠道
            if (userFrom.matches("[0-9]+")) {
                Integer channelId = Integer.valueOf(userFrom);
                channelMap.put("id", channelId);
                channelInfo = channelInfoService.findOneChannelInfo(channelMap);
                channelName = channelInfo == null ? null : channelInfo.getChannelName();
            } else {
                channelName = "渠道号异常" + userFrom;
            }
            resultMap.put("channelName", channelName);
        } else if (StringUtils.isNotEmpty(inviteUserId)) {
            User inviteUser = userService.searchByUserid(Integer.parseInt(inviteUserId));
            resultMap.put("inviteUser", inviteUser);
        }

        //展示通讯录
        resultMap.put("contactList", userContactsList);

        log.info("commonFunction end borrowOrderId={}", borrowOrder.getId());
        return resultMap;
    }



    /**
     * 跳过机审，初审通过
     *
     * @param request     req
     * @param response    res
     * @param model       model
     * @param borrowOrder order
     * @return str
     */
    @RequestMapping("saveUpdateBorrowTGJS")
    public String saveUpdateBorrowTGJS(HttpServletRequest request, HttpServletResponse response, Model model, BorrowOrder borrowOrder) {

        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {

            // // 更新或者添加操作
            if (borrowOrder.getId() != null) {
                String key = BorrowOrder.REVIEW_BORROW + borrowOrder.getId();
                String value = key;

                if (jedisCluster.get(key) != null) {
                    SpringUtils.renderDwzResult(response, false, "操作失败,原因：该订单正在被审核，请稍后操作！", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                            .toString());
                } else {

                    jedisCluster.setex(BorrowOrder.REVIEW_BORROW + borrowOrder.getId(), BorrowOrder.REVIEW_BORROW_SECOND, value);
                    BorrowOrder borrowOrderR = borrowOrderService.findOneBorrow(borrowOrder.getId());
                    // 待机审核、机审失败的才可以
                    if (borrowOrderR.getStatus().intValue() == BorrowOrder.STATUS_DCS
                        //
                        // || borrowOrderR.getStatus().intValue() ==
                        // BorrowOrder.STATUS_JSJJ
                            ) {
                        BackUser backUser = loginAdminUser(request);

                        borrowOrder.setUpdatedAt(new Date());

                        borrowOrder.setStatus(BorrowOrder.STATUS_CSTG);
                        borrowOrder.setRemark("人工操作跳过机审！");

                        borrowOrder.setVerifyTrialTime(new Date());
                        borrowOrder.setVerifyTrialRemark(borrowOrder.getRemark());
                        borrowOrder.setVerifyTrialUser(backUser.getUserName());


                        try{
                            OrderLogModel orderLogModel = new OrderLogModel();
                            orderLogModel.setUserId(String.valueOf(borrowOrderR.getUserId()));
                            Date now = new Date();
                            orderLogModel.setCreateTime(now);
                            orderLogModel.setUpdateTime(now);
                            orderLogModel.setBorrowId(String.valueOf(borrowOrder.getId()));
                            orderLogModel.setOperateType(OperateType.BORROW.getCode());
                            orderLogModel.setBeforeStatus(String.valueOf(borrowOrderR.getStatus()));
                            orderLogModel.setAfterStatus(String.valueOf(borrowOrder.getStatus()));

                            orderLogModel.setAction(OrderChangeAction.SKIP_MACHINE.getCode());
                            orderLogModel.setRemark(OrderChangeAction.SKIP_MACHINE.getMessage());
                            orderLogService.addNewOrderChangeLog(orderLogModel);
                        }catch (Exception e){
                            log.error("add order log error:{}",e);
                        }
                        borrowOrderService.updateById(borrowOrder);

                        if (BorrowOrder.STATUS_DCS.equals(borrowOrderR.getStatus())) {
                            RiskCreditUser riskCreditUser = new RiskCreditUser();
                            riskCreditUser.setAssetId(borrowOrderR.getId());
                            riskCreditUser.setRiskStatus(6);
                            borrowOrderService.updateRiskCreditUserById(riskCreditUser);
                        }

                        SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());

                    } else {

                        SpringUtils.renderDwzResult(response, false, "操作失败,原因：该状态不能跳过机审或者已经过了机审！", DwzResult.CALLBACK_CLOSECURRENTDIALOG,
                                params.get("parentId").toString());

                    }
                }
            }

        } catch (Exception e) {
            if (url == null) {

                SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
                        .toString());
            }
            log.error("跳过机审失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;

    }




    @RequestMapping("getUserLimitPage")
    public String getUserLimitPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = "userInfo/userLimitRecordList";
        try {

            HashMap<String, Object> params = getParametersO(request);

            PageConfig<UserLimitRecord> pageConfig = borrowOrderService.finduserLimitPage(params);
            model.addAttribute("pm", pageConfig);

            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getUserLimitPage error:{}", e);
        }
        return url;
    }

    /**
     * 导出放款Excel
     */
    @RequestMapping("toFKExcel")
    public void toFKExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {

            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ, BorrowOrder.STATUS_FKBH,
                    BorrowOrder.STATUS_FKSB));
            int totalPageNum = borrowOrderService.findParamsCount(params);
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
            ExcelUtil.setFileDownloadHeader(request, response, "放款审核列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

            String[] titles = {"序号", "订单号", "姓名", "手机号", /*"是否是老用户"*/"成功放款次数", "借款金额(元)", "天数", "服务费利率(万分之一)", "手续费", "到账金额", "下单时间", "放款时间", "子类型", "状态",
                    "放款状态", "放款备注"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrder> pm = borrowOrderService.findPage(params);
                List<BorrowOrder> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrder borrowOrder : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrder.getId();
                    conList[1] = borrowOrder.getOutTradeNo();
                    conList[2] = borrowOrder.getRealname();
                    conList[3] = borrowOrder.getUserPhone();
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrder.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        conList[4] = "首借";
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrder.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            conList[4] = "首借";
                        }else{
                            conList[4] = loanCount.toString();
                        }
                    }
/*
                    conList[4] = conList[3] = borrowOrder.getCustomerTypeName();
*/
                    conList[5] = new BigDecimal(borrowOrder.getMoneyAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[6] = borrowOrder.getLoanTerm() + "天";
                    conList[7] = new BigDecimal(borrowOrder.getApr()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[8] = new BigDecimal(borrowOrder.getLoanInterests()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[9] = new BigDecimal(borrowOrder.getIntoMoney()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[10] = borrowOrder.getOrderTime() == null ? "" : DateUtil.getDateFormat(borrowOrder.getOrderTime(),
                            "yyyyyy-MM-dd HH:mm:ss");
                    conList[11] = borrowOrder.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrder.getLoanTime(), "yyyyyy-MM-dd HH:mm:ss");
                    conList[12] = borrowOrder.getOrderTypeName();
                    conList[13] = borrowOrder.getStatusName();
                    conList[14] = borrowOrder.getPaystatus();
                    conList[15] = borrowOrder.getPayRemark();
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "放款列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    @RequestMapping("getBorrowFreeBPage")
    public String getBorrowFreeBPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = "borrow/borrowFeeList_company";
        try {

            HashMap<String, Object> params = getParametersO(request);

            PageConfig<BorrowOrderLoan> pageConfig = borrowOrderService.findBorrowFreeBPage(params);
            model.addAttribute("pm", pageConfig);
            // model.addAttribute("borrowStatusMap",
            // BorrowOrder.borrowStatusMap);
            model.addAttribute("allstatus", BorrowOrderLoan.allstatus);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getBorrowFreeBPage error:{}", e);
        }
        return url;
    }

    /**
     * 公司服务费统计
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("getBorrowFreeBStatisPage")
    public String getBorrowFreeBStatisPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = "borrow/borrowFeeList_company_statis";
        try {

            HashMap<String, Object> params = getParametersO(request);
            params.put("status", BorrowOrderLoan.FKCG);

            Long sumLoanInterest = borrowOrderService.findloanInterestsSucSumB(params);
            model.addAttribute("sumLoanInterest", sumLoanInterest);
            PageConfig<BorrowOrderLoan> pageConfig = borrowOrderService.findBorrowFreeBStatisPage(params);
            model.addAttribute("pm", pageConfig);
            // model.addAttribute("borrowStatusMap",
            // BorrowOrder.borrowStatusMap);
            model.addAttribute("allstatus", BorrowOrderLoan.allstatus);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getBorrowFreeBPage error:{}", e);
        }
        return url;
    }

    /**
     * 个人服务费统计
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getBorrowFreeCStatisPage")
    public String getBorrowFreeCStatisPage(HttpServletRequest request, Model model) {
        String url = "borrow/borrowFeeList_person_statis";
        try {

            HashMap<String, Object> params = getParametersO(request);
            params.put("status", BorrowOrderLoan.FKCG);
            Long sumLoanInterest = borrowOrderService.findloanInterestsSucSumC(params);
            model.addAttribute("sumLoanInterest", sumLoanInterest);
            PageConfig<BorrowOrderLoanPerson> pageConfig = borrowOrderService.findBorrowFreeCStatisPage(params);
            model.addAttribute("pm", pageConfig);

            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getBorrowFreeBPage error:{}", e);
        }
        return url;
    }

    /**
     * 服务费重新审核设置可重新放款
     *
     * @param request  req
     * @param response res
     */
    @RequestMapping("againLoan/{loantype}")
    public void againLoan(HttpServletRequest request, HttpServletResponse response, @PathVariable("loantype") String loantype) {
        HashMap<String, Object> params = this.getParametersO(request);
        boolean flag = false;
        try {
            String yurref = params.get("yurref").toString();
            if (yurref != null) {
                flag = borrowOrderService.loanAgainFee(yurref, loantype);

            }

        } catch (Exception e) {
            log.error("againLoan error:{}", e);
        }
        SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "该订单不能重新放款或者操作失败！请确认该订单信息", DwzResult.CALLBACK_CLOSECURRENTDIALOG,
                params.get("parentId").toString());
    }



    @RequestMapping("getBorrowFreeCPage")
    public String getBorrowFreeCPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = "borrow/borrowFeeList_person";
        try {

            HashMap<String, Object> params = getParametersO(request);

            PageConfig<BorrowOrderLoanPerson> pageConfig = borrowOrderService.findBorrowFreeCPage(params);
            model.addAttribute("pm", pageConfig);
            // model.addAttribute("borrowStatusMap",
            // BorrowOrder.borrowStatusMap);
            model.addAttribute("allstatus", BorrowOrderLoan.allstatus);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getBorrowFreeCPage error:{}", e);
        }
        return url;
    }

    /**
     * 导出资产Excel(公司)
     */
    @RequestMapping("toBorrowFreeBExcel")
    public void toBorrowFreeBExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            Integer totalPageNum = borrowOrderService.findBorrowFreeBCount(params);
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
            ExcelUtil.setFileDownloadHeader(request, response, "服务费统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"用户ID", "借款订单号", "招行订单号", "金额(元)", "创建时间", "状态", "放款状态", "放款备注", "更新时间", "放款时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderLoan> pm = borrowOrderService.findBorrowFreeBPage(params);

                List<BorrowOrderLoan> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (BorrowOrderLoan borrowOrderLoan : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrderLoan.getUserId();
                    conList[1] = borrowOrderLoan.getAssetOrderId();
                    conList[2] = borrowOrderLoan.getYurref();
                    // if("0".equals(borrowOrder.getCustomerType())){
                    // conList[3] = "新用户";
                    // }else{
                    // conList[3] = "老用户用户";
                    // }

                    conList[3] = new BigDecimal(borrowOrderLoan.getLoanInterests()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
                    conList[4] = borrowOrderLoan.getCreatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getCreatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[5] = borrowOrderLoan.getStatusShow();
                    conList[6] = borrowOrderLoan.getPayStatus();
                    conList[7] = borrowOrderLoan.getPayRemark();
                    conList[8] = borrowOrderLoan.getUpdatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getUpdatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[9] = borrowOrderLoan.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getLoanTime(),
                            "yyyy-MM-dd HH:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    /**
     * 导出资产Excel(公司)统计
     */
    @RequestMapping("toBorrowFreeBStatisExcel")
    public void toBorrowFreeBStatisExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        params.put("status", BorrowOrderLoan.FKCG);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            Integer totalPageNum = borrowOrderService.findBorrowFreeBStatisCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }

            String fileName = "服务费统计-公司";
            if (params.containsKey("beginTime")) {
                fileName += "(" + params.get("beginTime");
            }
            if (params.containsKey("endTime")) {
                fileName += "&" + params.get("endTime") + ")";
            }
            fileName += ".xls";
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, fileName);
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"招行订单号", "金额(元)", "创建时间", "状态", "放款状态", "放款备注", "更新时间", "放款时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderLoan> pm = borrowOrderService.findBorrowFreeBStatisPage(params);

                List<BorrowOrderLoan> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrderLoan borrowOrderLoan : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrderLoan.getYurref();

                    conList[1] = new BigDecimal(borrowOrderLoan.getLoanInterests()).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
                    conList[2] = borrowOrderLoan.getCreatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getCreatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[3] = borrowOrderLoan.getStatusShow();
                    conList[4] = borrowOrderLoan.getPayStatus();
                    conList[5] = borrowOrderLoan.getPayRemark();
                    conList[6] = borrowOrderLoan.getUpdatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getUpdatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[7] = borrowOrderLoan.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getLoanTime(),
                            "yyyy-MM-dd HH:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error(" toBorrowFreeBStatisExcel 导出excel失败:{}", e);
        }

    }

    /**
     * 导出资产Excel(个人)统计
     */
    @RequestMapping("toBorrowFreeCStatisExcel")
    public void toBorrowFreeCStatisExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        params.put("status", BorrowOrderLoan.FKCG);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            Integer totalPageNum = borrowOrderService.findBorrowFreeCStatisCount(params);
            int total = 0;
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            String fileName = "服务费统计-个人";
            if (params.containsKey("beginTime")) {
                fileName += "=(" + params.get("beginTime") + "&";
            }
            if (params.containsKey("endTime")) {
                fileName += params.get("endTime") + ")";
            }
            fileName += ".xls";
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, fileName);
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"招行订单号", "金额(元)", "创建时间", "状态", "放款状态", "放款备注", "更新时间", "放款时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderLoanPerson> pm = borrowOrderService.findBorrowFreeCStatisPage(params);

                List<BorrowOrderLoanPerson> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrderLoanPerson borrowOrderLoan : borrowList) {
                    Object[] conList = new Object[titles.length];

                    conList[0] = borrowOrderLoan.getYurref();

                    conList[1] = new BigDecimal(borrowOrderLoan.getLoanInterests()).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
                    conList[2] = borrowOrderLoan.getCreatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getCreatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[3] = borrowOrderLoan.getStatusShow();
                    conList[4] = borrowOrderLoan.getPayStatus();
                    conList[5] = borrowOrderLoan.getPayRemark();
                    conList[6] = borrowOrderLoan.getUpdatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getUpdatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[7] = borrowOrderLoan.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getLoanTime(),
                            "yyyy-MM-dd HH:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error(" toBorrowFreeCStatisExcel 导出excel失败:{}", e);
        }

    }

    /**
     * 导出资产Excel(个人)
     */
    @RequestMapping("toBorrowFreeCExcel")
    public void toBorrowFreeCExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            Integer totalPageNum = borrowOrderService.findBorrowFreeCCount(params);
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
            ExcelUtil.setFileDownloadHeader(request, response, "服务费统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"用户ID", "借款订单号", "招行订单号", "金额(元)", "创建时间", "状态", "放款状态", "放款备注", "更新时间", "放款时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderLoanPerson> pm = borrowOrderService.findBorrowFreeCPage(params);
                List<BorrowOrderLoanPerson> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrderLoanPerson borrowOrderLoan : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrderLoan.getUserId();
                    conList[1] = borrowOrderLoan.getAssetOrderId();
                    conList[2] = borrowOrderLoan.getYurref();
                    // if("0".equals(borrowOrder.getCustomerType())){
                    // conList[3] = "新用户";
                    // }else{
                    // conList[3] = "老用户用户";
                    // }

                    conList[3] = new BigDecimal(borrowOrderLoan.getLoanInterests()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
                    conList[4] = borrowOrderLoan.getCreatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getCreatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[5] = borrowOrderLoan.getStatusShow();
                    conList[6] = borrowOrderLoan.getPayStatus();
                    conList[7] = borrowOrderLoan.getPayRemark();
                    conList[8] = borrowOrderLoan.getUpdatedAt() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getUpdatedAt(),
                            "yyyy-MM-dd HH:mm:ss");
                    conList[9] = borrowOrderLoan.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrderLoan.getLoanTime(),
                            "yyyy-MM-dd HH:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    /**
     * 导出对账Excel
     */
    @RequestMapping("toDzExcel/{borrowStatus}")
    public void toDzExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable("borrowStatus") String borrowStatus) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_BFHK, BorrowOrder.STATUS_YHK, BorrowOrder.STATUS_YQYHK,
                    BorrowOrder.STATUS_YHZ, BorrowOrder.STATUS_KKZ, BorrowOrder.STATUS_YYQ));
            int totalPageNum = borrowOrderService.findParamsCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "订单对账统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"债权编号", "第三方订单号", "招行订单号", "手机号码", "借款人", "身份证号码", "借款金额", "实际到账金额", "还款期限", "放款时间", "状态"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrder> pm = borrowOrderService.findPage(params);
                List<BorrowOrder> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrder borrowOrder : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrder.getId();
                    conList[1] = borrowOrder.getOutTradeNo();
                    conList[2] = borrowOrder.getYurref();
                    conList[3] = borrowOrder.getUserPhone();
                    conList[4] = borrowOrder.getRealname();
                    conList[5] = borrowOrder.getIdNumber();
                    conList[6] = new BigDecimal(borrowOrder.getMoneyAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[7] = new BigDecimal(borrowOrder.getIntoMoney()).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
                    conList[8] = borrowOrder.getLoanTerm() + "天";
                    conList[9] = borrowOrder.getLoanTime() == null ? "" : DateUtil.getDateFormat(borrowOrder.getLoanTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[10] = "放款成功";
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "用户列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

    @RequestMapping("getBorrowOrderCheckPage")
    public String getBorrowOrderCheckPage(HttpServletRequest request, HttpServletResponse response, Model model) {

        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<BorrowOrderChecking> pageConfig = borrowOrderService.findBorrowOrderCheckPage(params);
            List<BorrowOrderChecking> list = new ArrayList<>();
            for(BorrowOrderChecking borrowOrderChecking : pageConfig.getItems()){
                //查询用户成功借款次数
                Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrderChecking.getUserId());
                //该用户在还款表中无记录
                if(loanSucCount != null && loanSucCount < 1 ){
                    borrowOrderChecking.setLoanCount("首借");
                }else{
                    Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrderChecking.getUserId());
                    //该用户在还款表中 没有已还款的记录
                    if(loanCount < 1){
                        borrowOrderChecking.setLoanCount("首借");
                    }else{
                        borrowOrderChecking.setLoanCount(loanCount.toString());
                    }
                }
                list.add(borrowOrderChecking);
            }
            pageConfig.setItems(list);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("allstatus", BorrowOrderChecking.allstatus);
            model.addAttribute("capitalTypeMap", BorrowOrderChecking.capitalTypeMap);
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            log.error("getBorrowOrderCheckPage error:{}", e);
        }
        return "borrow/borrowOrderCheckPage";
    }


    @RequestMapping("getBorrowOrderCheckExtPage")
    public String getBorrowOrderCheckExtPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<BorrowOrderCheckingExt> pageConfig = borrowOrderService.findBorrowOrderCheckExtPage(params);
            List<BorrowOrderCheckingExt> list = new ArrayList<>();
            for(BorrowOrderCheckingExt borrowOrderCheckingExt : pageConfig.getItems()){
                //查询用户成功借款次数
                Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrderCheckingExt.getUserId());
                //该用户在还款表中无记录
                if(loanSucCount != null && loanSucCount < 1 ){
                    borrowOrderCheckingExt.setLoanCount("首借");
                }else{
                    Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrderCheckingExt.getUserId());
                    //该用户在还款表中 没有已还款的记录
                    if(loanCount < 1){
                        borrowOrderCheckingExt.setLoanCount("首借");
                    }else{
                        borrowOrderCheckingExt.setLoanCount(loanCount.toString());
                    }
                }
                list.add(borrowOrderCheckingExt);
            }
            pageConfig.setItems(list);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("allstatus", BorrowOrderChecking.allstatus);
            model.addAttribute("capitalTypeMap", BorrowOrderCheckingExt.capitalZCMTypeMap);
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            log.error("getBorrowOrderCheckPage error:{}", e);
        }
        return "borrow/borrowOrderCheckExtPage";
    }

    /**
     * 导出招财猫 资产
     */
    @RequestMapping("toBorrowChceckExtExcel")
    public void toExtBorrow(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = borrowOrderService.findBorrowOrderCheckExtCount(params);
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
            ExcelUtil.setFileDownloadHeader(request, response, "资产信息附表统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"借款订单ID", "姓名", "手机号码", "身份证", "银行卡号", /*"是否老用户"*/"成功还款次数", "借款金额", "借款期限", "实际到账金额", "放款时间", "资产所属", "更新时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderCheckingExt> pm = borrowOrderService.findBorrowOrderCheckExtPage(params);
                List<BorrowOrderCheckingExt> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrderCheckingExt borrowOrder : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrder.getAssetOrderId();
                    conList[1] = borrowOrder.getRealname();
                    conList[2] = borrowOrder.getUserPhone();
                    conList[3] = borrowOrder.getIdNumber();
                    conList[4] = borrowOrder.getCardNo();
                    // if("0".equals(borrowOrder.getCustomerType())){
                    // conList[3] = "新用户";
                    // }else{
                    // conList[3] = "老用户用户";
                    // }
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrder.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        conList[5] = "首借";
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrder.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            conList[5] = "首借";
                        }else{
                            conList[5] = loanCount.toString();
                        }
                    }
                    //conList[5] = User.customerTypes.get(borrowOrder.getCustomerType() + "");
                    conList[6] = new BigDecimal(borrowOrder.getMoneyAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[7] = borrowOrder.getLoanTerm() + "天";
                    conList[8] = new BigDecimal(borrowOrder.getIntoMoney()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[9] = DateUtil.getDateFormat(borrowOrder.getLoanTime(), "yyyy-MM-dd HH:mm:ss");
                    //TODO：不确定的状态显示什么
                    if (BorrowOrderCheckingExt.capitalZCMTypeMap.containsKey(borrowOrder.getCapitalType())) {
                        conList[10] = BorrowOrderCheckingExt.capitalZCMTypeMap.get(borrowOrder.getCapitalType());
                    } else {
                        conList[10] = "";
                    }
                    conList[11] = DateUtil.getDateFormat(borrowOrder.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss");

                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }
    }


    /**
     * 导出资产Excel
     */
    @RequestMapping("toBorrowChceckExcel")
    public void toBorrowChceckExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = borrowOrderService.findBorrowOrderCheckCount(params);
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
            ExcelUtil.setFileDownloadHeader(request, response, "资产信息统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"借款订单ID", "姓名", "手机号码", "身份证", "银行卡号", /*"是否老用户"*/"成功还款次数", "借款金额", "借款期限", "实际到账金额", "放款时间", "资产所属", "更新时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<BorrowOrderChecking> pm = borrowOrderService.findBorrowOrderCheckPage(params);
                List<BorrowOrderChecking> borrowList = pm.getItems();
                List<Object[]> contents = new ArrayList<Object[]>();
                for (BorrowOrderChecking borrowOrder : borrowList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = borrowOrder.getAssetOrderId();
                    conList[1] = borrowOrder.getRealname();
                    conList[2] = borrowOrder.getUserPhone();
                    conList[3] = borrowOrder.getIdNumber();
                    conList[4] = borrowOrder.getCardNo();
                    // if("0".equals(borrowOrder.getCustomerType())){
                    // conList[3] = "新用户";
                    // }else{
                    // conList[3] = "老用户用户";
                    // }
                    //查询用户成功借款次数
                    Integer loanSucCount = repaymentService.userBorrowCount(null,borrowOrder.getUserId());
                    //该用户在还款表中无记录
                    if(loanSucCount != null && loanSucCount < 1 ){
                        conList[5] = "首借";
                    }else{
                        Integer loanCount = repaymentService.userBorrowCount(99999,borrowOrder.getUserId());
                        //该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
                        if(loanCount < 1){
                            conList[5] = "首借";
                        }else{
                            conList[5] = loanCount.toString();
                        }
                    }
                    //conList[5] = User.customerTypes.get(borrowOrder.getCustomerType() + "");
                    conList[6] = new BigDecimal(borrowOrder.getMoneyAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[7] = borrowOrder.getLoanTerm() + "天";
                    conList[8] = new BigDecimal(borrowOrder.getIntoMoney()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    conList[9] = DateUtil.getDateFormat(borrowOrder.getLoanTime(), "yyyy-MM-dd HH:mm:ss");
                    conList[10] = BorrowOrderChecking.capitalTypeMap.get(borrowOrder.getCapitalType());
                    conList[11] = DateUtil.getDateFormat(borrowOrder.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss");

                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }


//    @RequestMapping("testxx")
//    public void testxx() {
//        try {
//            taskJob.assetsDivision();
//            // 查询状态
//            // taskJob.queryPaysStateNotCmb();
//            // taskJob.queryPaysStateCmb();
//            // HttpRequestCMBHL.getRequestNTQRYEBPStr();
//            // System.out.println("zz");
//            // taskJob.updateLoanTerm();
//        } catch (Exception e) {
//            log.error("getUserLimitPage error:{}", e);
//        }
//
//    }

    @RequestMapping("runWithhold")
    public void runWithhold() {
        try {
            log.info("手动定时代扣任务开始start");
            taskJob.withhold();
            log.info("手动定时代扣任务开始end");
        } catch (Exception e) {
            log.error("手动定时代扣任务 error:{}", e);
        }
    }

    @RequestMapping("runRepaySmsRemind")
    public void runRepaySmsRemind() {
        try {
            log.info("手动明日还款短信提醒任务开始start");
            taskJob.repaySmsRemind();
            log.info("手动明日还款短信提醒任务开始end");
        } catch (Exception e) {
            log.error("手动明日还款短信提醒任务 error:{}", e);
        }
    }

    @RequestMapping("runRepaySmsRemind9")
    public void runRepaySmsRemind9() {
        try {
            log.info("手动今日还款短信提醒任务开始start");
            taskJob.repaySmsRemind9();
            log.info("手动今日还款短信提醒任务end");
        } catch (Exception e) {
            log.error("手动今日还款短信提醒任务 error:{}", e);
        }
    }

    /**
     * 获取借款订单相关风控信息（用于弹出框展示详情）
     *
     * @param request  req
     * @param response res
     * @return str
     * @author Bouger
     */
    @RequestMapping(value = "getOldBorrowDetailById", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public String getOldBorrowDetailById(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        Integer id = Integer.valueOf(String.valueOf(params.get("id")));
        Object proNam = params.get("projectName");
        String projectName = null != proNam ? proNam.toString() : "";
        BorrowOrder borrow = new BorrowOrder();
        Result<MbgAssetBorrowOrder> mbgAssetBorrowOrderResult = dataWareHouseService.queryOrderByIdAndProjectName(id, projectName);
        MbgAssetBorrowOrder borrowSrc = mbgAssetBorrowOrderResult.getModel();
        if (null != borrowSrc) {
            CopyUtil.copyProperties(borrowSrc, borrow);
            borrow.setUserId(borrowSrc.getUserId());
        }

        User user = new User();
        Result<MbgUserInfo> mbgUserInfoResult = dataWareHouseService.queryUserInfoByIdAndProjectName(borrow.getUserId(), projectName);
        MbgUserInfo userSrc = mbgUserInfoResult.getModel();
        if (null != userSrc) {
            CopyUtil.copyProperties(userSrc, user);
            user.setId(String.valueOf(userSrc.getId()));
            user.setZmToken(userSrc.getZmToken());
            user.setRealname(userSrc.getRealname());
            user.setUserPhone(userSrc.getUserPhone());
            user.setPresentAddress(userSrc.getPresentAddress());
            user.setUserFrom(userSrc.getUserFrom());
            user.setInviteUserid(String.valueOf(userSrc.getInviteUserid()));
        }

        //auto 硬指标建议
//        Map<String, Object> auto = new HashMap<String, Object>();
//        auto.put("inflexibleAdvice", null);
//        auto.put("inflexibleAdviceReason", null);
//        auto.put("oldCustomerAdvice", null);
//        auto.put("oldCustomerAdviceReason", null);

//        RiskModelOrder riskModelOrder = new RiskModelOrder();
//        Map<String, Object> riskParams = new HashMap<String, Object>();
//        riskParams.put("borrowOrderId", id);
//        try {
//            Map<String, Object> commonMap = commonFunction(user, borrow, projectName);
//            auto.put("inflexibleAdvice", commonMap.get("inflexibleAdvice"));
//            auto.put("inflexibleAdviceReason", commonMap.get("inflexibleAdviceReason"));
//            auto.put("oldCustomerAdvice", commonMap.get("oldCustomerAdvice"));
//            auto.put("oldCustomerAdviceReason", commonMap.get("oldCustomerAdviceReason"));

//            Result<MbgRiskModelOrder> mbgRiskModelOrderResult = dataWareHouseService.queryRiskModelOrderByOrderIdAndProjectName(id, projectName);
//            MbgRiskModelOrder riskModelOrderSrc = mbgRiskModelOrderResult.getModel();
//            if (null != riskModelOrderSrc) {
//                CopyUtil.copyProperties(riskModelOrderSrc, riskModelOrder);
//                riskModelOrder.setId(Long.valueOf(riskModelOrderSrc.getId()));
//            }
//        } catch (Exception e) {
//            log.error("call BorrowOrderController.getOldBorrowDetailById error:{}", e);
//        }
        Map<String, Object> map = new HashMap<>();

//        map.put("auto", auto);
        map.put("borrow", borrow);
//        map.put("riskModelOrder", riskModelOrder);

        return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("getProductConfig")
    @ResponseBody
    public com.info.back.utils.Result getProductConfig() {
        List<BorrowProductConfig> configs = productConfigDao.queryAllBorrowProductConfig();
        return com.info.back.utils.Result.success(configs);
    }

}
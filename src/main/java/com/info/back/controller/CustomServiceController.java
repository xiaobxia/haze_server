package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.info.back.dao.IBackDictionaryDao;
import com.info.back.dao.IBackUserRoleDao;
import com.info.back.pojo.CustomerClassArrange;
import com.info.back.pojo.UserDetail;
import com.info.back.service.IBackUserService;
import com.info.back.service.IOnlineCustomService;
import com.info.back.service.ITaskJob;
import com.info.back.utils.*;
import com.info.constant.Constant;
import com.info.csPackage.ICsGetDataService;
import com.info.risk.utils.ConstantRisk;
import com.info.web.common.reslult.JsonResult;
import com.info.web.controller.BaseController;
import com.info.web.pojo.*;
import com.info.web.pojo.statistics.BackDictionary;
import com.info.web.service.*;
import com.info.web.util.PageConfig;
import com.info.web.util.aliyun.RocketMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.info.web.pojo.BorrowOrder.*;

@Slf4j
@Controller
@RequestMapping("customService/")
public class CustomServiceController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBackUserService backUserService;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    private IRepaymentService repaymentService;
    @Autowired
    private IBorrowOrderService borrowOrderService;
    @Autowired
    private ITaskJob taskJob;
    @Autowired
    private IRepaymentDetailService repaymentDetailService;
    @Resource
    private IOnlineCustomService onlineCustomService;
    @Resource
    private ICsGetDataService csGetDataService;
    @Resource
    private IBackDictionaryDao backDictionaryDao;
    @Autowired
    private IBackUserRoleDao backUserRoleDao;
    @Autowired
    private IChannelInfoService channelInfoService;
    @Autowired
    private ILabelCountService labelCountService;

    @Autowired
    private IBackLoanCensusService backLoanCensusService;


    @RequestMapping("findJxl/{interval}")
    public String findJxl(HttpServletRequest request, Model model, @PathVariable Integer interval) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            params.put("interval", interval);
            PageConfig<User> pageConfig = userService.findJxlList(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);
        } catch (Exception e) {
            log.error("findJxl error:{} ", e);
        }
        return "custom/jxlList";
    }

    @RequestMapping("resetJxl")
    public void resetJxl(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        boolean flag = false;
        String errMsg = "未知异常！";
        Integer id = Integer.parseInt(params.get("id").toString());
        String key = ConstantRisk.JXL_REPORT + id;
        try {
            if (jedisCluster.get(key) != null) {
                errMsg = "当前数据正在被处理,请稍后重试！";
            } else {
                if (userService.findJxlStatus(id) > 0) {
                    errMsg = "当前用户已完成运营商认证！";
                } else {
                    jedisCluster.setex(key, ConstantRisk.FLAG_SECOND, id + "");
                    userService.updateJxl(id);
                    backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "resetJxl", id + ""));
                    flag = true;
                    errMsg = "重置成功！";
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            jedisCluster.del(key);
            log.error("resetJxl error:{}", e);
        }
        SpringUtils.renderDwzResult(response, flag, errMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId").toString());
    }
    @RequestMapping(value = "orderList")
    public String getOrders(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        Integer userId=loginAdminUser(request).getId();
        //查询用户是否含有客服主管的角色
        int count = backUserService.findRoleKfM(userId);
        if(count <= 0){
            //普通客服只能查看分配给自己的订单
            params.put("kefuId",userId);
        }
        PageConfig<CustomerOrder> pageConfig = repaymentService.findOrders(params);
       // showKeFuMessagePageConfig = repaymentService.findAssignPage(params);
        model.addAttribute("pm",pageConfig);
        model.addAttribute("params", params);// 用于搜索框保留值
        //model.addAttribute("remark",Remark.borrowRemarkMap);
        List<BackDictionary> backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK);
        List<BackDictionary> backDictionaries1 = backDictionaryDao.findDictionary(Constant.USER_REMARK_ONLINE);
        backDictionaries.addAll(backDictionaries1);
        model.addAttribute("remark", BackDictionary.getDictionaryTranslateMap(backDictionaries));
        return "custom/orderList";
    }
   /* @RequestMapping(value = "orderList")
    public String getOrders(HttpServletRequest request, Model model) {
        String createTime; //预期还款时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间
        Calendar now = Calendar.getInstance();
        createTime = dateFormat.format(now.getTime());
        String status = request.getParameter("status"); //已还与未还的状态
        String jobName = request.getParameter("jobName"); //客服名字
        String labelStatus = request.getParameter("labelStatus");
        HashMap<String, Object> params = getParametersO(request);
        if (status != null && !"".equals(status)) {
            Integer flag = Integer.parseInt(status);
            if (flag == 1) { //1 标记未已还
                params.put("payStatus", 30); //根据是否还款查询 正常还款
                //续期后的还款时间至少 大于当前时间是要比大于一天的
                Calendar nowCalendar = Calendar.getInstance();
                nowCalendar.add(Calendar.DATE, 1);
                String xuqiPayTime = dateFormat.format(nowCalendar.getTime());
                params.put("xuqiPayTime", xuqiPayTime); //续期还款
            } else if (flag == 0) { //未还
                params.put("noPayStatus",21);
            }else if(flag == 2){
                params.put("yuqiStatus",-11);
            }else if(flag == 3){
                params.put("bufenStatus",23);
            }else if(flag == 4){
                params.put("yqhuanStatus",34);
            }
        }
        if (jobName != null && !"".equals(jobName)) {
            params.put("jobName", jobName); //根据客服名字模糊查询
        }
        if (labelStatus != null && !"".equals(labelStatus)) {
            params.put("remarkFlag", labelStatus);
        }
        //查询所有的客服
        HashMap<String, Object> paramsRole = new HashMap<>();
        paramsRole.put("roleName", "普通客服");
        paramsRole.put("status", 1);
        List<BackUser> backUserList = backUserService.findKeFuList(paramsRole);
        model.addAttribute("backUserList", backUserList);
        //params.put("createTime", createTime);
//        BackUser backUser = (BackUser) request.getSession().getAttribute(Constant.BACK_USER); //获取session中的值
        BackUser backUser = getSessionUser(request);
        HashMap<String, Object> roleMap = new HashMap<>(); //客服只能看到自己的订单
        roleMap.put("backUserId", backUser.getId());
        //BackUserRole backUserRole = backUserRoleDao.roleKeFu(roleMap);
        List<BackUserRole> backUserRoles = backUserRoleDao.queryCustomerService(roleMap);
        if (CollectionUtils.isNotEmpty(backUserRoles)) {
            boolean isCustomService = false;
            boolean isCustomServiceHead = false;
            for (BackUserRole backUserRole : backUserRoles) {
                if (Constant.ROLE_CUSTOMER_SERVER.intValue() == backUserRole.getRoleId().intValue()) {
                    isCustomService = true;
                } else if (Constant.ROLE_CUSTOMER_SERVER_HEAD.intValue() == backUserRole.getRoleId().intValue()) {
                    isCustomServiceHead = true;
                }
            }
            if (isCustomService && !isCustomServiceHead) {
                params.put("jobId", backUser.getId());
            }
        }
        PageConfig<ShowKeFuMessage> showKeFuMessagePageConfig;
        Integer userId=loginAdminUser(request).getId();
       //查询用户是否含有客服主管的角色
        int count = backUserService.findRoleKfM(userId);
        if (count >0){
            //针对于客服主管 则查询所有订单， 对于已经多次派单的则只展示最后一次派单记录
            params.put("assignType", 0);
            params.put("filterAssign", 1);
            showKeFuMessagePageConfig = repaymentService.findAssignPage(params);
            for(ShowKeFuMessage showKeFuMessage:showKeFuMessagePageConfig.getItems()){

            }
        }else{
            params.put("assignType", 1); //默认查询已经一键转派的订单 针对于普通客服
            params.put("filterAssign", 1);
            showKeFuMessagePageConfig = repaymentService.findAssignPage(params);
        }
        if (showKeFuMessagePageConfig != null && showKeFuMessagePageConfig.getItems() != null) {
            if (showKeFuMessagePageConfig.getItems().size() <= 0) {
                HashMap<String, Object> otherParams = new HashMap<>();
                otherParams.put("createTime", createTime);
                otherParams.put("assignType", 1);
                otherParams.put("filterAssign", 1);
                params.put("assignType", 0);
                if (!CollectionUtils.isEmpty(repaymentService.findAssignPage(otherParams).getItems())) {
                    params.put("payStatus", 30); //根据是否还款查询
                    //续期后的还款时间至少 大于当前时间是要比大于一天的
                    Calendar nowCalendar = Calendar.getInstance();
                    nowCalendar.add(Calendar.DATE, 1);
                    String xuqiPayTime = dateFormat.format(nowCalendar.getTime());
                    params.put("xuqiPayTime", xuqiPayTime); //续期还款
                    showKeFuMessagePageConfig = repaymentService.findAssignPage(params);
                    if (!"1".equals(status)) {
                        params.remove("payStatus");
                    }
                } else {
                    showKeFuMessagePageConfig = repaymentService.findAssignPage(params);
                }
            }
        }
        model.addAttribute("pm", showKeFuMessagePageConfig);
        model.addAttribute("params", params);// 用于搜索框保留值
        //model.addAttribute("remark",Remark.borrowRemarkMap);
        List<BackDictionary> backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK);
        List<BackDictionary> backDictionaries1 = backDictionaryDao.findDictionary(Constant.USER_REMARK_ONLINE);
        backDictionaries.addAll(backDictionaries1);
        model.addAttribute("remark", BackDictionary.getDictionaryTranslateMap(backDictionaries));
        return "custom/orderList";
    }*/


    /*@RequestMapping("sendToOrder")
    public String sendToOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JsonResult json = new JsonResult("0", "转派成功!");
        try {
            String userId = request.getParameter("userId");
            if (StringUtils.isEmpty(userId)) {
                json.setCode("-1");
                json.setMsg("请选择转派的客服");
            } else {
                //查询backUser中的信息
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", userId);
                List<BackUser> backUserList = backUserService.findAll(map);
                if (backUserList.size() > 0) {
                    BackUser backUser = backUserList.get(0);
                    String uId = request.getParameter("uids"); //asset_borrow_assign
                    //进行更新,根据userId 查询
                    if (!StringUtils.isEmpty(uId)) {
                        String[] list = uId.split(",");
                        for (String assignId : list) {
                            if (StringUtils.isEmpty(assignId)) {
                                continue;
                            }
                            map.clear();
                            map.put("id", assignId);
                            List<AssetBorrowAssign> exists = repaymentService.findAssetBorrowAssignByCreateTime(map);
                            if (CollectionUtils.isEmpty(exists)) {
                                continue;
                            }
                            BorrowOrder order = borrowOrderService.findOneBorrow(exists.get(0).getBorrowOrderId());
                            if (order == null) {
                                continue;
                            }
                            Integer status = order.getStatus();
                            if (status.equals(BorrowOrder.STATUS_YHK) || status.equals(BorrowOrder.STATUS_BFHK) || status.equals(BorrowOrder.STATUS_YQYHK)) {
                                continue;
                            }
                            //根据订单Id查询
//							HashMap<String, Object> assignMap = new HashMap<>();
                            AssetBorrowAssign assetBorrowAssign = new AssetBorrowAssign();
                            assetBorrowAssign.setJobId(backUser.getId());
                            assetBorrowAssign.setJobName(backUser.getUserName());
                            assetBorrowAssign.setId(Integer.parseInt(assignId));
                            repaymentService.updateAssetBorrowAssignById(assetBorrowAssign);
                        }
                    } else {
                        json.setCode("-1");
                        json.setMsg("请选择要转派的订单");
                    }
                }
            }
            SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                    json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());

        } catch (Exception e) {
            log.error("sendToOrder error:{}", e);
        }
        return null;
    }*/
    @RequestMapping("sendToOrder")
    public String sendToOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JsonResult json = new JsonResult("0", "转派成功!");
        try {
            String userId = request.getParameter("userId");
            if (StringUtils.isEmpty(userId)) {
                json.setCode("-1");
                json.setMsg("请选择转派的客服");
            } else {
                //查询backUser中的信息
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", userId);
                List<BackUser> backUserList = backUserService.findAll(map);
                if (backUserList.size() > 0) {
                    BackUser backUser = backUserList.get(0);
                    String uId = request.getParameter("uids"); //asset_borrow_assign
                    //进行更新,根据userId 查询
                    if (!StringUtils.isEmpty(uId)) {
                        String[] list = uId.split(",");
                        for (String assignId : list) {
                            if (StringUtils.isEmpty(assignId)) {
                                continue;
                            }
                            map.clear();
                            map.put("id", assignId);
                            List<AssetBorrowAssign> exists = repaymentService.findAssetBorrowAssignByCreateTime(map);
                            if (CollectionUtils.isEmpty(exists)) {
                                continue;
                            }
                            BorrowOrder order = borrowOrderService.findOneBorrow(exists.get(0).getBorrowOrderId());
                            if (order == null) {
                                continue;
                            }
                            Integer status = order.getStatus();
                            if (status.equals(BorrowOrder.STATUS_YHK) || status.equals(BorrowOrder.STATUS_BFHK) || status.equals(BorrowOrder.STATUS_YQYHK)) {
                                continue;
                            }
                            //根据订单Id查询
//							HashMap<String, Object> assignMap = new HashMap<>();
                            //将原来订单 状态改为删除不展示
                            AssetBorrowAssign assetBorrowAssign = new AssetBorrowAssign();
                            assetBorrowAssign.setId(Integer.parseInt(assignId));
                            assetBorrowAssign.setDelFlag(1);
                            repaymentService.updateAssetBorrowAssignById(assetBorrowAssign);
                            //添加一条新的人工派单记录
                            AssetBorrowAssign borrowAssign = new AssetBorrowAssign();
                            borrowAssign.setJobId(backUser.getId());
                            borrowAssign.setJobName(backUser.getUserName());
                            borrowAssign.setDelFlag(0);
                            borrowAssign.setBorrowOrderId(order.getId());
                            borrowAssign.setCreateTime(new Date());
                            borrowAssign.setAssignType(1);
                            repaymentService.insertAssetBorrowAssign(borrowAssign);
                        }
                    } else {
                        json.setCode("-1");
                        json.setMsg("请选择要转派的订单");
                    }
                }
            }
            SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                    json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());

        } catch (Exception e) {
            log.error("sendToOrder error:{}", e);
        }
        return null;
    }
    @RequestMapping("zhuanpaiJsp")
    public String zhuanpaiJsp(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        String uIds = request.getParameter("ids");
        if (StringUtils.isEmpty(uIds)) {
            model.addAttribute("message", "请选择要转派的订单");
        }
        //查询所有的客服
        HashMap<String, Object> paramsRole = new HashMap<>();
        paramsRole.put("roleName", "普通客服");
        paramsRole.put("status", 1);
        List<BackUser> backUserList = backUserService.findKeFuList(paramsRole);
        model.addAttribute("uIds", uIds);
        model.addAttribute("backUserList", backUserList);
        model.addAttribute("params", params);
        return "custom/zhuanpai";
    }

    @RequestMapping("testKefuJob")
    public void testKefuJob() {
        taskJob.autoAssignOrder();
    }

    /*@RequestMapping("testKefuJobForNig")
    public void testKefuJobForNig() {
        taskJob.autoAssignOrderForNig();
    }*/
    @RequestMapping(value = "addRemark")
    public String addRemark(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        Map<String, List<BackDictionary>> remarkData;
        Map<String, List<BackDictionary>> finalMap = new LinkedHashMap<>();
        //电话客服派单id ，在线客服为订单id
        String id = request.getParameter("id");
        List<BackDictionary> backDictionaries = backDictionaryDao.findAllLabelType();
        remarkData = backDictionaries.stream().collect(Collectors.groupingBy(BackDictionary::getDictName));
        remarkData.remove("催收客服备注");
        remarkData.remove("在线客服备注标签");
        remarkData.remove("外呼异常");
        remarkData.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().size())).forEach(e -> finalMap.put(e.getKey(), e.getValue()));
        model.addAttribute("remark", finalMap);
        model.addAttribute("id", id);
        model.addAttribute("params", params);
        return "custom/addRemark";
    }

  /*  @RequestMapping(value = "addRemark")
    public String addRemark(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        Map<String, List<BackDictionary>> remarkData;
        Map<String, List<BackDictionary>> finalMap = new LinkedHashMap<>();
        //电话客服派单id ，在线客服为订单id
        String id = request.getParameter("id");
        //区分在线客服和电话客服
        String type = request.getParameter("type");
        List<BackDictionary> backDictionaries;
        if ("online".equals(type)) {
            backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK_ONLINE);
        } else {
            backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK);

        }
        params.put("type", type);
        remarkData = backDictionaries.stream().collect(Collectors.groupingBy(BackDictionary::getDictName));
        remarkData.remove("催收客服备注");
        remarkData.remove("在线客服备注标签");
        remarkData.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().size())).forEach(e -> finalMap.put(e.getKey(), e.getValue()));
        model.addAttribute("remark", finalMap);
        model.addAttribute("id", id);
        model.addAttribute("params", params);
        return "custom/addRemark";
    }*/
    @RequestMapping(value = "addPhoneCall")
    public String addPhoneCall(HttpServletRequest request,Model model) {
        HashMap<String, Object> params = getParametersO(request);
        //电话客服派单id ，在线客服为订单id
        String userPhone = request.getParameter("userPhone");
        String userName = request.getParameter("userName");
        model.addAttribute("phone",userPhone);
        model.addAttribute("name",userName);
        model.addAttribute("params", params);
        return "custom/addAiPhoneCall";
    }
    @RequestMapping(value = "savePhoneCall", method = RequestMethod.POST)
    public String savePhoneCall(HttpServletRequest request,HttpServletResponse response) {
        HashMap<String, Object> params = getParametersO(request);

        JsonResult json = new JsonResult("0", "拨打成功");
        //电话客服派单id ，在线客服为订单id
        String userPhone = request.getParameter("userPhone");
        String userName = request.getParameter("userName");
        Map<String,String> map = new HashMap<>();
        map.put("phone",userPhone);
        map.put("name",userName);
        log.info("send ai message:{}",JSON.toJSONString(map));
        RocketMqUtil.sendAiMessage(JSON.toJSONString(map));

        SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
        return null;
    }

    @RequestMapping("getLabels")
    @ResponseBody
    public Result getLabels(@RequestParam("labelType") String labelType, @RequestParam("type") String type) {
        HashMap<String, Object> params = new HashMap<>(4);
        if ("".equals(type)) {
            List<BackDictionary> backDictionaries = backDictionaryDao.findLabelType(labelType);
            Predicate<BackDictionary> dictPred = (dictionary) -> (!"催收客服备注".equals(dictionary.getDictName()) && !"在线客服备注标签".equals(dictionary.getDictName()));
            backDictionaries = backDictionaries.stream().filter(dictPred).collect(Collectors.toList());
            return Result.success(backDictionaries);
        } else {
            params.put("dictionary", labelType);
            params.put("dictName", type);
            List<BackDictionary> backDictionaries = backDictionaryDao.findDictionarys(params);
            return Result.success(backDictionaries);
        }
    }
    @RequestMapping(value = "saveBorrowRemark", method = RequestMethod.POST)
    public String saveBorrowRemark(HttpServletRequest request, HttpServletResponse response) {
        JsonResult json = new JsonResult("0", "备注成功!");
        HashMap<String, Object> params = getParametersO(request);
        String id = request.getParameter("id");
        //获取标签
        String remarkFlag = request.getParameter("remarkFlag");
        Boolean flagRemark = true;
        if (id == null || "".equals(id)) {
            json.setCode("-1");
            json.setMsg("请选择订单");
            flagRemark = false;
        } else if (remarkFlag == null || "".equals(remarkFlag)) {
            json.setCode("-1");
            json.setMsg("请选择备注标签");
            flagRemark = false;
        }
        try {
            if (flagRemark) {
               String borrowOrderId = repaymentService.selectBorrowOrderIdByAssignId(Integer.valueOf(id));
                if (borrowOrderId != null && !"".equals(borrowOrderId)) {
                    //获取备注
                    String remarkContent = request.getParameter("remarkContent");
                    Remark remark = new Remark();
                    remark.setAssignId(Integer.parseInt(borrowOrderId));
                    remark.setRemarkFlag(Integer.parseInt(remarkFlag));
                    Date now = new Date();
                    remark.setCreateTime(now);
                    remark.setUpdateTime(now);
                    remark.setRemarkContent(remarkContent);
                    BackUser backUser = this.getSessionUser(request);
                    if (backUser != null) {
                        remark.setJobName(backUser.getUserName());
                        remark.setJobPhone(backUser.getUserMobile());
                    }
                    List<String> ids = repaymentService.getRemarkIdByOrderId(id);
                    //更新之前最新备注记录为历史记录
                    if (ids != null && !ids.isEmpty()) {
                        repaymentService.updateRemarkStatus(ids);
                    }
                    int flag = repaymentService.insertIntoRemak(remark);
                    if (flag != 1) {
                        json.setCode("-1");
                        json.setMsg("信息备注失败");
                    }
                }
            }
        } catch (Exception e) {
            log.error("save borrow remark error:{}", e);
            json.setCode("-1");
            json.setMsg("信息备注失败");
        }
        SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
        return null;
    }
   /* @RequestMapping(value = "saveBorrowRemark", method = RequestMethod.POST)
    public String saveBorrowRemark(HttpServletRequest request, HttpServletResponse response) {
        JsonResult json = new JsonResult("0", "备注成功!");
        HashMap<String, Object> params = getParametersO(request);
        //区分在线客服和电话客服
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        //获取标签
        String remarkFlag = request.getParameter("remarkFlag");
        Boolean flagRemark = true;
        if (id == null || "".equals(id)) {
            json.setCode("-1");
            json.setMsg("请选择订单");
            flagRemark = false;
        } else if (remarkFlag == null || "".equals(remarkFlag)) {
            json.setCode("-1");
            json.setMsg("请选择备注标签");
            flagRemark = false;
        }

        try {
            if (flagRemark) {
                String borrowOrderId;
                if ("online".equals(type)) {//在线客服 id 为订单id
                    borrowOrderId = id;
                } else {
                    //电话客服
                    //根据派单ID获取订单ID
                    borrowOrderId = repaymentService.selectBorrowOrderIdByAssignId(Integer.valueOf(id));
                    //保存remark之前先查看派单标签是否满足列表删除即 remarkFlag >=2
                    if (Integer.valueOf(remarkFlag) >= 2) {
                        repaymentService.updateAssignDelFlag(Integer.valueOf(id));
                    }
                }

                if (borrowOrderId != null && !"".equals(borrowOrderId)) {
                    //获取备注
                    String remarkContent = request.getParameter("remarkContent");

                    Remark remark = new Remark();
                    remark.setAssignId(Integer.parseInt(borrowOrderId));
                    remark.setRemarkFlag(Integer.parseInt(remarkFlag));
                    Date now = new Date();
                    remark.setCreateTime(now);
                    remark.setUpdateTime(now);
                    remark.setRemarkContent(remarkContent);
                    BackUser backUser = this.getSessionUser(request);
                    if (backUser != null) {
                        remark.setJobName(backUser.getUserName());
                        remark.setJobPhone(backUser.getUserMobile());
                    }
                    List<String> ids = repaymentService.getRemarkIdByOrderId(borrowOrderId);
                    //更新之前最新备注记录为历史记录
                    if (ids != null && !ids.isEmpty()) {
                        repaymentService.updateRemarkStatus(ids);
                    }
                    int flag = repaymentService.insertIntoRemak(remark);
                    if (flag != 1) {
                        json.setCode("-1");
                        json.setMsg("信息备注失败");
                    }
                }
            }
        } catch (Exception e) {
            log.error("save borrow remark error:{}", e);
            json.setCode("-1");
            json.setMsg("信息备注失败");
        }
        SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                params.get("parentId").toString());
        return null;
    }*/

    @RequestMapping(value = "showRemark")
    public String showRemark(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult json = new JsonResult("0", "");
        HashMap<String, Object> params = getParametersO(request);
        //派单ID
        String id = request.getParameter("id");
        if (id == null) {
            json.setCode("-1");
            json.setMsg("请选择订单");
            SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                    json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            return null;
        }
        //根据指派id对应的查找备注下所有的信息
        String borrowOrderId = repaymentService.selectBorrowOrderIdByAssignId(Integer.valueOf(id));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("borrowOrderId", borrowOrderId);
        List<Remark> remarkList = repaymentService.selectRemarkByCondition(hashMap);
        model.addAttribute("remarkList", remarkList);
        //model.addAttribute("remark",Remark.borrowRemarkMap);
        List<BackDictionary> backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK);
        List<BackDictionary> backDictionaries1 = backDictionaryDao.findDictionary(Constant.USER_REMARK_ONLINE);
        backDictionaries.addAll(backDictionaries1);
        model.addAttribute("remark", BackDictionary.getDictionaryTranslateMap(backDictionaries));
        return "custom/showRemark";
    }

    /**
     * 一键转派
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("keySend")
    public String keySend(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult json = new JsonResult("0", "转派成功");
        HashMap<String, Object> params = getParametersO(request);
        String assingIds = request.getParameter("ids"); //获取所有分单过后的id集合
        String type = request.getParameter("keySendFlag"); //进行转派的标志
        if (type != null) {
            String[] kefuIds = request.getParameterValues("kefuId"); //选中的客服id
//			List<ShowKeFuMessage> showKeFuMessageList = getNoPayListAssetBorrowAssign();
            List<Integer> assignIds = getNoPayListAssetBorrowAssign();
            if (kefuIds == null || kefuIds.length <= 0) {
                //进行错误指示
                json.setCode("-1");
                json.setMsg("您还没有选择指派数据");
            } else if (assignIds == null) {
                json.setCode("-1");
                json.setMsg("您已经派过单");
            } else {
                json.setCode("0");
                json.setMsg("派单成功");
                //进行装派操作
                int index = 0;
                int kefuLen = kefuIds.length;
                for (Integer assignId : assignIds) {
//					Integer assignId = showKeFuMessage.getId();
                    //获取订单
                    AssetBorrowAssign assetBorrowAssign = repaymentService.findAssetBorrowAssignById(assignId);
//					if (assetBorrowAssign != null) {
//						Integer assignType = assetBorrowAssign.getAssignType();
//						if (assignType != null && assignType.equals(1)) {  // 当assignType=1的时候 表示已经人工派过
//							continue;
//						}
//					}
                    if (index >= kefuLen) {
                        index = 0;
                    }
                    int kefuId = Integer.parseInt(kefuIds[index]);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", kefuId);
                    //获取客服
                    BackUser backUser = backUserService.findOneUser(hashMap);
                    if (backUser != null && assetBorrowAssign != null) {
                        //更新原来订单的updatetime
                        AssetBorrowAssign assetBorrowAssignDate = new AssetBorrowAssign();
                        assetBorrowAssignDate.setUpdateTime(new Date());
                        assetBorrowAssignDate.setId(assetBorrowAssign.getId());
                        repaymentService.updateAssetBorrowAssignById(assetBorrowAssignDate);
                        //插入新的人工派单数据
                        repaymentService.reAssign(backUser, assetBorrowAssign);
                    }
                    index++;
                }
            }
            SpringUtils.renderDwzResult(response, "0".equals(json.getCode()),
                    json.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            return null;

        }
        //获取所有的订单数

        //查询所有的客服
        HashMap<String, Object> paramsRole = new HashMap<String, Object>();
        paramsRole.put("roleName", "普通客服");
        paramsRole.put("status", 1);
        List<BackUser> backUserList = backUserService.findKeFuList(paramsRole);
        model.addAttribute("backUserList", backUserList);
        model.addAttribute("params", params);
        model.addAttribute("ids", assingIds); //获取所有指定订单id
        return "custom/keySend";
    }


    /**
     * 查找未还的记录
     *
     * @return list
     */
    public List<Integer> getNoPayListAssetBorrowAssign() {
        String createTime = null; //预期还款时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间
        Calendar now = Calendar.getInstance();
        createTime = dateFormat.format(now.getTime());
        HashMap<String, Object> params = new HashMap<>();
//		params.put("noPayStatus", 30);
        //params.put("createTime", createTime);

//		List<ShowKeFuMessage> showKeFuMessageList = repaymentService.getNoPayAssetBorrowAssign(params);
//		//根据订单id查询 看看是否已经派过单
//		for (ShowKeFuMessage showKeFuMessage : showKeFuMessageList) {
//			//根据订单查询
//			params.put("assignType",1);
//			params.put("borrowOrderId",showKeFuMessage.getBorrowOrderId());
//			List<ShowKeFuMessage> list = repaymentService.getNoPayAssetBorrowAssign(params);
//			if (list != null && list.size() >0) {
//				return new ArrayList<ShowKeFuMessage>();
//			}
//		}
        //校验是否派单过
        params.put("assignType", 1);
        List<Integer> checkAssignIds = repaymentService.queryAssignByCondition(params);
        if (!CollectionUtils.isEmpty(checkAssignIds) && checkAssignIds.get(0) != null) {
            return null;
        }
        params.put("assignType", 0);
        return repaymentService.queryAssignByCondition(params);
    }

    @RequestMapping("queryCustomerList")
    public String queryCustomerList(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackUser> pageConfig = backUserService.queryCustomerList(params);
        model.addAttribute("pm", pageConfig);
        model.addAttribute("params", params);
        return "custom/customerServiceList";
    }

    @RequestMapping("test")
    public void test() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(c.getTime());
        Map<String, Object> params = new HashMap<>();
        params.put("beforDate", date);
        List<Map<String, Object>> assign = repaymentService.selectAssignStatisticsByCondition(params);
        repaymentDetailService.insertStatisticsDetail(assign);
    }

    @RequestMapping("userList")
    public String userList(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            UserDetail userDetail = onlineCustomService.getUserInfoDetail(params);
            model.addAttribute("params", params);
            List<BackDictionary> backDictionaries = backDictionaryDao.findDictionary(Constant.USER_REMARK_ONLINE);
            List<BackDictionary> backDictionaries1 = backDictionaryDao.findDictionary(Constant.USER_REMARK);
            backDictionaries.addAll(backDictionaries1);
            model.addAttribute("remarkMap", BackDictionary.getDictionaryTranslateMap(backDictionaries));
            if (userDetail != null) {
                if (userDetail.getRepaymentStatus() != null && userDetail.getRepaymentStatus().equals(-11)) {
                    DynamicDataSource.setCustomerType(DynamicDataSource.QBM_CS);
                    String jobName = csGetDataService.getLoanData(userDetail);
                    userDetail.setCurrentJobName(jobName);
                    DynamicDataSource.clearCustomerType();
                }
                model.addAttribute("user", userDetail);
            }
        } catch (Exception e) {
            log.error("query user order info error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }
        return "custom/userList";
    }

    @RequestMapping("customerClass")
    public String customerClassList(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            //默认添加昨日排班
            DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(sf.format(calendar.getTime()));
            Calendar yesToday = Calendar.getInstance();
            yesToday.add(Calendar.DATE, -1);
            if (customerClassArrange == null) {
                CustomerClassArrange yesTodayClass = onlineCustomService.getCustomerClassByDate(sf.format(yesToday.getTime()));
                if (yesTodayClass != null) {
                    yesTodayClass.setClassDate(sf.format(calendar.getTime()));
                    onlineCustomService.saveCustomerClassArrange(yesTodayClass);
                }
            }

            PageConfig<Map<String, Object>> pageConfig = onlineCustomService.getCustomerClassList(params);
            if (pageConfig != null) {
                List<Map<String, Object>> list = pageConfig.getItems();
                if (list != null && !list.isEmpty())
                    for (Map<String, Object> map : list) {
                        //计算数目
                        String mor = String.valueOf(map.get("classMorCustomers"));
                        String[] morCollections = StringUtils.tokenizeToStringArray(mor, ",");
                        map.put("classMorCustomers", morCollections.length);

                        /*String nig = String.valueOf(map.get("classNigCustomers"));
                        String[] nigCollections = StringUtils.tokenizeToStringArray(nig, ",");
                        map.put("classNigCustomers", nigCollections.length);*/
                    }
                pageConfig.setItems(list);
                model.addAttribute("pm", pageConfig);
                model.addAttribute("params", params);
            }

            return "custom/customerClass";
        } catch (Exception e) {
            log.error(" customerClass error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }

    }
    @RequestMapping("addOrUpdateCustomerClass")
    public String addCustomerClass(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            String url = null;
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                //早班已排集合
                List<String> morList = new ArrayList<>();
                url = "custom/saveUpdateCustomerClass";
                if (params.get("id") != null) {//修改操作页面渲染
                    CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassById(String.valueOf(params.get("id")));

                    String[] morCustomerIds = StringUtils.tokenizeToStringArray(customerClassArrange.getClassMorCustomers(), ",");
                    morList = Arrays.asList(morCustomerIds);
                    params.put("date", customerClassArrange.getClassDate());
                } else {
                    //新增排班
                    //默认明天排班
                    DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    // 取最近一次排班日期+1
                    String lastClassDate = onlineCustomService.getLastClassDate();
                    Calendar calendar = Calendar.getInstance();
                    //if (!lastClassDate.equals("null")) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(lastClassDate)) {
                        calendar.setTime(sf.parse(lastClassDate));
                    }
                    calendar.add(Calendar.DATE, +1);
                    params.put("date", sf.format(calendar.getTime()));
                }
                //排班人员
                params.put("morIds", morList);
                //客服列表
                List<BackUser> list = onlineCustomService.getCustomerList();
                /*
                 * 客服列表
                 */
                params.put("list", list);
            } else {
                String date = String.valueOf(params.get("date"));
                if (!"".equals(date)) {
                    CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(date);
                    //更新班次
                    if (customerClassArrange != null) {
                        CustomerClassArrange classArrange = new CustomerClassArrange();
                        classArrange.setClassDate(date);
                        classArrange.setClassMorCustomers(params.get("morCustomerIds") == null ? "" : String.valueOf(params.get("morCustomerIds")));
                        classArrange.setClassNigCustomers(params.get("nigCustomerIds") == null ? "" : String.valueOf(params.get("nigCustomerIds")));
                        onlineCustomService.updateCustomerClassArrange(classArrange);
                    } else {
                        //新增
                        CustomerClassArrange classArrange = new CustomerClassArrange();
                        classArrange.setClassDate(date);
                        classArrange.setClassMorCustomers(params.get("morCustomerIds") == null ? "" : String.valueOf(params.get("morCustomerIds")));
                        classArrange.setClassNigCustomers(params.get("nigCustomerIds") == null ? "" : String.valueOf(params.get("nigCustomerIds")));
                        onlineCustomService.saveCustomerClassArrange(classArrange);
                    }
                }
                SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
            model.addAttribute("params", params);
            return url;
        } catch (Exception e) {
            log.error("addCustomerClass error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }

    }
   /* @RequestMapping("addOrUpdateCustomerClass")
    public String addCustomerClass(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            String url = null;
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                //早班已排集合
                List<String> morList = new ArrayList<>();
                //晚班已排集合
                List<String> nigList = new ArrayList<>();
                url = "custom/saveUpdateCustomerClass";
                if (params.get("id") != null) {//修改操作页面渲染
                    CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassById(String.valueOf(params.get("id")));

                    String[] morCustomerIds = StringUtils.tokenizeToStringArray(customerClassArrange.getClassMorCustomers(), ",");
                    String[] nigCustomerIds = StringUtils.tokenizeToStringArray(customerClassArrange.getClassNigCustomers(), ",");
                    morList = Arrays.asList(morCustomerIds);
                    nigList = Arrays.asList(nigCustomerIds);
                    params.put("date", customerClassArrange.getClassDate());
                } else {
                    //新增排班
                    //默认明天排班
                    DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    // 取最近一次排班日期+1
                    String lastClassDate = onlineCustomService.getLastClassDate();
                    Calendar calendar = Calendar.getInstance();
                    //if (!lastClassDate.equals("null")) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(lastClassDate)) {
                        calendar.setTime(sf.parse(lastClassDate));
                    }
                    calendar.add(Calendar.DATE, +1);
                    params.put("date", sf.format(calendar.getTime()));
                }
                //排班人员
                params.put("morIds", morList);
                params.put("nigIds", nigList);
                //客服列表
                List<BackUser> list = onlineCustomService.getCustomerList();
                *//*
                 * 客服列表
                 *//*
                params.put("list", list);

            } else {
                String date = String.valueOf(params.get("date"));
                if (!"".equals(date)) {
                    CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(date);
                    //更新班次
                    if (customerClassArrange != null) {
                        CustomerClassArrange classArrange = new CustomerClassArrange();
                        classArrange.setClassDate(date);
                        classArrange.setClassMorCustomers(params.get("morCustomerIds") == null ? "" : String.valueOf(params.get("morCustomerIds")));
                        classArrange.setClassNigCustomers(params.get("nigCustomerIds") == null ? "" : String.valueOf(params.get("nigCustomerIds")));
                        onlineCustomService.updateCustomerClassArrange(classArrange);
                    } else {
                        //新增
                        CustomerClassArrange classArrange = new CustomerClassArrange();
                        classArrange.setClassDate(date);
                        classArrange.setClassMorCustomers(params.get("morCustomerIds") == null ? "" : String.valueOf(params.get("morCustomerIds")));
                        classArrange.setClassNigCustomers(params.get("nigCustomerIds") == null ? "" : String.valueOf(params.get("nigCustomerIds")));
                        onlineCustomService.saveCustomerClassArrange(classArrange);
                    }
                }
                SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
            model.addAttribute("params", params);
            return url;
        } catch (Exception e) {
            log.error("addCustomerClass error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }

    }*/

    @RequestMapping("getCustomerClassDetail")
    public String getCustomerClassDetail(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            if (params.get("id") != null) {
                CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassById(String.valueOf(params.get("id")));

                String[] morCustomerIds = StringUtils.tokenizeToStringArray(customerClassArrange.getClassMorCustomers(), ",");
               // String[] nigCustomerIds = StringUtils.tokenizeToStringArray(customerClassArrange.getClassNigCustomers(), ",");
                List<String> morCustomerList = new ArrayList<>();
                //List<String> nigCustomerList = new ArrayList<>();
                if (morCustomerIds != null && morCustomerIds.length > 0) {
                    morCustomerList = backUserService.selectBackUserNameByIds(morCustomerIds);
                }
               /* if (nigCustomerIds != null && nigCustomerIds.length > 0) {
                    nigCustomerList = backUserService.selectBackUserNameByIds(nigCustomerIds);
                }*/
                params.put("date", customerClassArrange.getClassDate());
                model.addAttribute("params", params);
                model.addAttribute("morCustomerList", morCustomerList);
               // model.addAttribute("nigCustomerList", nigCustomerList);
            }
            return "custom/customerClassDetail";
        } catch (Exception e) {
            log.error("getCustomerClassDetail error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }
    }

    @RequestMapping("exportCustomerClass")
    public void exportCustomerClass(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            //班次ID
            String classIds = null;
            String[] idArray = null;
            if (params.get("ids") != null) {
                classIds = params.get("ids").toString();
            }
            if (classIds != null) {
                idArray = StringUtils.tokenizeToStringArray(classIds, ",");
            }
            //row
            int size = 50000;
            //sheet
            int total = 0;
            //导出数据
            List<Map<String, Object>> list = new ArrayList<>();
            if (idArray != null && idArray.length > 0) {

                list = onlineCustomService.getCustomerClassForExcel(idArray);
                int totalPageNum = list.size();
                if (totalPageNum > 0) {
                    if (totalPageNum % size > 0) {
                        total = totalPageNum / size + 1;
                    } else {
                        total = totalPageNum / size;
                    }
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "客服班次列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"日期", "早班人员", "早班人数", "晚班人员", "晚班人数"};
            for (int i = 1; i <= total; i++) {
                List<Object[]> contents = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    Map<String, Object> map = list.get(j);
                    Object[] conList = new Object[titles.length];
                    conList[0] = map.get("classDate") == null ? "" : map.get("classDate");
                    String[] morCustomerIds = StringUtils.tokenizeToStringArray(map.get("classMorCustomers") == null ? "" : String.valueOf(map.get("classMorCustomers")), ",");
                    List<String> morCustomerList = backUserService.selectBackUserNameByIds(morCustomerIds);
                    conList[1] = morCustomerList == null ? "" : morCustomerList.toString();
                    conList[2] = morCustomerList == null ? 0 : morCustomerList.size();

                    String[] nigCustomerIds = StringUtils.tokenizeToStringArray(map.get("classNigCustomers") == null ? "" : String.valueOf(map.get("classNigCustomers")), ",");
                    List<String> nigCustomerList = backUserService.selectBackUserNameByIds(nigCustomerIds);

                    conList[3] = nigCustomerList == null ? "" : nigCustomerList.toString();
                    conList[4] = nigCustomerList == null ? 0 : nigCustomerList.size();

                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "客服班次列表", titles, contents, i, total, os);
            }
        } catch (Exception e) {
            log.error("OutPutExcel error:{}", e);
        }

    }

    @RequestMapping("/searchBorrowList")
    public String getUserPage(HttpServletRequest request, ModelMap model) {
        HashMap<String, Object> params = getParametersO(request);
        String url = "custom/borrowList_customer";
        if (params.get("bType") != null && params.get("bType").equals("fangk")) {
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ,
                    BorrowOrder.STATUS_FKBH, BorrowOrder.STATUS_FKSB));
            url = "custom/borrowList_customer_fk";
        }
        try {
            PageConfig<BorrowOrder> pageConfig = new PageConfig<>();
            if (params.get("outTradeNo") != null || params.get("realname") != null || params.get("userPhone") != null) {

                pageConfig = borrowOrderService.findPage(params);
            }
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
                    newBorroList.add(borrowOrder);
                }
                pageConfig.setItems(newBorroList);
            }
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            String appName = PropertiesUtil.get("APP_NAME");
            model.addAttribute("appName", appName);
            return url;
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }
    }

    @RequestMapping("gotoUserManage")
    public String gotoUserManage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            String userId = request.getParameter("id");
            String realName = request.getParameter("realname");
            String idNumber = request.getParameter("idNumber");
            String userPhone = request.getParameter("userPhone");
            PageConfig<User> page = null;
            if (StringUtils.isEmpty(userId)) {
                userId = null;
            }
            if (StringUtils.isEmpty(realName)) {
                realName = null;
            }
            if (StringUtils.isEmpty(idNumber)) {
                idNumber = null;
            }
            if (StringUtils.isEmpty(userPhone)) {
                userPhone = null;
            }
            if (!StringUtils.isEmpty(userId) || null != userId || !StringUtils.isEmpty(userPhone)
                    || null != userPhone || !StringUtils.isEmpty(realName)
                    || null != realName || !StringUtils.isEmpty(idNumber)
                    || null != idNumber) {
                page = this.userService.getUserPage(params);
            }
            model.addAttribute("pm", page);
            model.addAttribute("searchParams", params);
            return "custom/userManageListCustomer";
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
            model.addAttribute("msg", "系统异常");
            return "error";
        }
    }

    @RequestMapping("getRepaymentPage")
    public String getRepaymentPage(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            Integer[] statuses = new Integer[]{STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ};

            //客服模块默认查询还款时间为今天数据
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            String dateStart = sf.format(calendar.getTime());
           /* calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            String dateEnd = sf.format(calendar.getTime());*/
            params.put("repaymentTimeStart", dateStart);
            params.put("repaymentTimeEnd", dateStart);
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
        return "custom/repaymentList_customer";
    }

    /**
     * 导出待还Excel
     */
    @RequestMapping("toCustomerDHExcel")
    public void toDHExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            Integer[] statuses = new Integer[]{STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ};
            params.put("statuses", statuses);
            //客服模块默认查询还款时间为今天数据
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            String dateStart = sf.format(calendar.getTime());
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            String dateEnd = sf.format(calendar.getTime());
            params.put("repaymentTimeStart", dateStart);
            params.put("repaymentTimeEnd", dateEnd);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = 50000;
            params.put(Constant.PAGE_SIZE, size);
//			params.put("statusList", Arrays.asList(BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ, BorrowOrder.STATUS_FKBH,
//					BorrowOrder.STATUS_FKSB));
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
                    /*if (0 == (repayment.getCustomerType())) {
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


    @RequestMapping("getLabelCountPage")
    public String getLabelCountPage(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            PageConfig<LabelCountBase> pageConfig = labelCountService.findPage(params);
            model.addAttribute("pm", pageConfig);
        } catch (Exception e) {
            log.error("getLabelCountPage error:{}", e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
        return "custom/label_count";
    }

    @RequestMapping("getLabelCountDetail")
    @ResponseBody
    public Result getLabelCountDetail(HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        List<LabelCountPageResult> results = labelCountService.getDetailResult(params);
        return Result.success(results);
    }

    /**
     * 催收提醒列表
     * @return
     */
    @RequestMapping("getOrderRemindList")
   public String getOrderRemindList(HttpServletRequest request, ModelMap model){
        HashMap<String, Object> params = getParametersO(request);
        try {
            Integer[] statuses = {21};
            //查询状态为待还款的
            params.put("statuses",statuses);
            //时间为还有两天还款的
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            params.put("repaymentTimeStart",sf.format(c.getTime()));
            c.add(Calendar.DAY_OF_MONTH,2);
            params.put("repaymentTimeEnd",sf.format(c.getTime()));
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
        return "custom/orderRemind";
   }

    /**
     * 贷后统计查询
     * @param request
     * @param model
     * @return
     */
   @RequestMapping("backLoanCensusResult")
   public String BackLoanCensusResult(HttpServletRequest request, ModelMap model){
       HashMap<String, Object> params = getParametersO(request);
       PageConfig<BackLoanCensus> pageConfig = backLoanCensusService.backLoanCensusResult(params);
       model.put("params",params);
       model.put("pageConfig",pageConfig);
       model.put("params",params);
       return "custom/afterLoanCensus";
   }

    /**
     * 贷后刷新功能
     * @return
     */
   @RequestMapping("freshenLoanCensusResult")
   @ResponseBody
   public void freshenLoanCensusResult(HttpServletResponse response) throws Exception {
       Boolean bool = true;
       try{
           // 调用贷后一天一次定时任务
           taskJob.BackLoanOveCensus();
       }catch(Exception e){
           bool = false;
           log.error("贷后统计刷新出现错误"+e.getMessage());
       }
       SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_RELOADPAGE);
   }

    /**
     * 刷新客服统计
     * @param response
     * @throws Exception
     */
    @RequestMapping("freshKefuCensus")
    @ResponseBody
    public void freshKefuCensus(HttpServletResponse response) throws Exception{
     Boolean bool = true;
     try{
         //调用客服统计定时任务
         taskJob.kefuCensus();
     }catch(Exception e){
        bool = false;
        log.error("客服派单统计定时任务出现错误"+e.getMessage());
     }
        SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_RELOADPAGE);
    }

    /**
     * 指向回算时间框jsp
     * @return
     */
    @RequestMapping("toBackCensusLoan")
    public String toBackCensusLoan(HttpServletRequest request, Model model){
        Map<String, String> params = this.getParameters(request);
        model.addAttribute("params", params);
        return "custom/toBackCensusLoan";
    }
    /**
     * 贷后回算功能
     * @return
     */
    @RequestMapping("backCensusLoan")
    @ResponseBody
   public void backCensusLoan(HttpServletResponse response,String repayTime,HttpServletRequest request) throws Exception{
        HashMap<String, Object> params = this.getParametersO(request);
        Boolean bool = true;
        try{
            taskJob.afterLoanCensus(repayTime);
        }catch(Exception e){
            bool = false;
            log.error("贷后统计回算出现错误"+e.getMessage());
        }
        SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
   }

   @RequestMapping("kefuCensus")
   public String kefuCensus(HttpServletRequest request, Model model){
         HashMap<String, Object> params = this.getParametersO(request);
         String begintTime = (String) params.get("beginTime");
         String endTime = (String) params.get("endTime");
         String userName = (String) params.get("userName");
         PageConfig<KefuCensus> pageConfig = borrowOrderService.kefuCensusList(params);
         model.addAttribute("pm", pageConfig);
         model.addAttribute("params", params);
         model.addAttribute("beginTime",begintTime);
         model.addAttribute("endTime",endTime);
         model.addAttribute("userName",userName);
         return "custom/kefuCensus";
   }

}

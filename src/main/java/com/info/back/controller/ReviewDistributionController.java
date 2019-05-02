package com.info.back.controller;

import com.info.back.dao.IBackConfigParamsDao;
import com.info.back.dao.IBackUserDao;
import com.info.back.pojo.BackDistributeRecord;
import com.info.back.service.IBackDistributeRecordService;
import com.info.back.service.IBackUserService;
import com.info.back.service.IReviewDistributionService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.dao.IBackReviewDistributionDao;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BackReviewDistribution;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BorrowOrder;
import com.info.web.service.IBorrowOrderService;
import com.info.web.util.BussinessLogUtil;
import com.info.web.util.PageConfig;
import com.info.web.util.StringDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("review/")
public class ReviewDistributionController extends BaseController {

    @Autowired
    private IReviewDistributionService reviewDistributionService;
    @Autowired
    private IBorrowOrderService borrowOrderService;
    @Resource
    private IBackUserDao backUserDao;
    @Resource
    private IBackReviewDistributionDao backReviewDistributionDao;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private IBackConfigParamsDao backConfigParamsDao;
    @Autowired
    private IBackDistributeRecordService backDistributeRecordService;
    @Autowired
    private IBackUserService backUserService;

    @RequestMapping("select")
    public String select(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            String disSwitch = jedisCluster.get("distributeSwitch");
            List<BackUser> list = reviewDistributionService.findAll(params);
            model.addAttribute("list", list);
            model.addAttribute("params", params);// 用于搜索框保留值
            model.addAttribute("status", disSwitch);// 记录开关状态

        } catch (Exception e) {
            log.error("select BackUser error:{}", e);
        }
        return "review/reviewUserList";
    }

    /**
     * 打开关闭派单开关
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("distribute_switch")
    public String switchChange(HttpServletRequest request, HttpServletResponse response, Model model) {
        String url = null;
        HashMap<String, Object> params = getParametersO(request);
        HashMap<String, Object> sys = new HashMap<String, Object>();
        sys.put("sysType", "SWITCH_TYPE");
        List<BackConfigParams> bakconfigs = backConfigParamsDao.findParams(sys);
        String disSwitch = jedisCluster.get("distributeSwitch");
        if (null == disSwitch) {
            jedisCluster.set("distributeSwitch", "close");
            disSwitch = jedisCluster.get("distributeSwitch");
        }
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                String dateCfg = "";
                for (BackConfigParams bankCfg : bakconfigs) {
                    if ("AUTO_DISTRIBUTE_TIME".equals(bankCfg.getSysKey())) {
                        dateCfg = bankCfg.getSysValue();
                    }
                }
                String[] split = dateCfg.split("-");
                if (null != split && 2 == split.length) {
                    model.addAttribute("startHour", split[0]);
                    model.addAttribute("endHour", split[1]);
                }
                model.addAttribute("status", disSwitch);
                url = "review/switchTimeContain";
            } else {
                String status = params.get("status").toString();
                //打开开关
                if ("open".equals(status)) {
                    if (null == params.get("startHour") || "".equals(params.get("startHour"))) {
                        SpringUtils.renderDwzResult(response, false, "起始时间不能为空", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                    } else if (null == params.get("endHour") || "".equals(params.get("endHour"))) {
                        SpringUtils.renderDwzResult(response, false, "结束时间不能为空", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                    } else {
                        String startHour = params.get("startHour").toString();
                        String endHour = params.get("endHour").toString();
                        if (!startHour.matches("([0,1]\\d|2[0-3]):[0-5]\\d$")) {
                            SpringUtils.renderDwzResult(response, false, "起始时间格式不对,形如:01:00即可", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        } else if (!endHour.matches("([0,1]\\d|2[0-3]):[0-5]\\d$")) {
                            SpringUtils.renderDwzResult(response, false, "结束时间格式不对,形如:01:00即可", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        }
                        //else if (!checkTime(startHour, endHour)) {
                        //    SpringUtils.renderDwzResult(response, false, "起始时间不能大于结束时间", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        //}
                        else {
                            //由于开关生效时间范围修改，因此每次可以重复打开开关
                            String dateValue = params.get("startHour").toString() + "-" + params.get("endHour").toString();
                            for (BackConfigParams bankCfg : bakconfigs) {
                                if ("AUTO_DISTRIBUTE_TIME".equals(bankCfg.getSysKey())) {
                                    bankCfg.setSysValue(dateValue);
                                }
                            }
                            //更改时间配置
                            backConfigParamsDao.updateValue(bakconfigs);
                            //更改开关状态
                            jedisCluster.set("distributeSwitch", status);
                            SpringUtils.renderDwzResult(response, true, "操作成功！", DwzResult.CALLBACK_CLOSECURRENT,
                                    params.get("parentId").toString());
                        }
                    }
                } else if ("close".equals(status)) {
                    if (status.equals(disSwitch)) {
                        SpringUtils.renderDwzResult(response, false, "操作失败：开关已经关闭！", DwzResult.CALLBACK_CLOSECURRENT,
                                params.get("parentId").toString());
                    } else {
                        jedisCluster.set("distributeSwitch", status);
                        SpringUtils.renderDwzResult(response, true, "操作成功！", DwzResult.CALLBACK_CLOSECURRENT,
                                params.get("parentId").toString());
                    }
                }
            }

        } catch (Exception e) {
            log.error("switch distribute error!", e);
            SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + e.getMessage(), DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());

        }
        model.addAttribute("params", params);
        return url;
    }

    private boolean checkTime(String startTime, String endTime) {
        String[] startHourVal = startTime.split(":");
        String[] endHourVal = endTime.split(":");
        if (Integer.valueOf(startHourVal[0]) > Integer.valueOf(endHourVal[0])) {
            return false;
        } else if (Integer.valueOf(startHourVal[0]).equals(Integer.valueOf(endHourVal[0]))) {
            if (Integer.valueOf(startHourVal[1]) > Integer.valueOf(endHourVal[1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 手动派单
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("autoDistribute")
    public void autoDistribute(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Map<Integer, List<BorrowOrder>> userDisOrder = new HashMap<>();
            int count = borrowOrderService.handDistributeOrderForReview(userDisOrder);
            if (0 < count) {
                //记录操作日志
                BackUser oprBackUser = loginAdminUser(request);
                BackDistributeRecord backDistributeRecord = new BackDistributeRecord();
                backDistributeRecord.setOprSetDate(new Date());
                //backDistributeRecord.setOprSetRate(backUser.getDistributionRate());
                backDistributeRecord.setOprUserId(oprBackUser.getId());
                //backDistributeRecord.setOprSetUserId(backUser.getId());
                backDistributeRecord.setOprDisCount(count);
                backDistributeRecordService.add(backDistributeRecord);
            }
            String psnVal = "<br />--------------------------------\t<br>";
            if (null != userDisOrder && 0 < userDisOrder.size()) {
                for (Integer userId : userDisOrder.keySet()) {
                    BackUser backUser = backUserService.selectUserById(userId);
                    if (null != backUser) {
                        String name = backUser.getUserName();
                        psnVal += "|["+name + "]|被派单[" + userDisOrder.get(userId).size() + "]笔\t|<br />";
                    }
                }
                psnVal += "--------------------------------\t<br>";
            }
            SpringUtils.renderText(response, "派单成功,派单数:共" + count + "笔" + psnVal);
        } catch (Exception e) {
            log.error("select BackUser error:{}", e);
            SpringUtils.renderText(response, "派单失败，原因:" + e.getMessage());
        }
    }

    /**
     * 跳转到添加页面
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveUpdate")
    public String saveUpdate(HttpServletRequest request, HttpServletResponse response, Model model, BackUser backUser) {
        Map<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    Integer id = Integer.parseInt(params.get("id").toString());
                    BackUser reviewer = backUserDao.findReviewerById(id);
                    model.addAttribute("backUser", reviewer);
                }
                url = "review/saveUpdate";
            } else {
                if (params.containsKey("id")) {
                    Integer id = Integer.parseInt(params.get("id").toString());
//                    Map<String, Object> objectMap = new HashMap<>(2);
//                    objectMap.put("userId", id);
//                    List<BackReviewDistribution> backReviewDistributions = backReviewDistributionDao.findParams(objectMap);
//                    BigDecimal sum = new BigDecimal(backUser.getDistributionRate());
//                    if (CollectionUtils.isNotEmpty(backReviewDistributions)) {
//                        for (BackReviewDistribution reviewDistribution : backReviewDistributions) {
//                            if (null == reviewDistribution || null == reviewDistribution.getDistributionRate()) {
//                                continue;
//                            }
//                            Double distributionRate = reviewDistribution.getDistributionRate();
//                            BigDecimal rate = new BigDecimal(distributionRate);
//                            if (rate.compareTo(BigDecimal.ZERO) != 0) {
//                                sum = sum.add(rate);
//                            }
//                        }
//                    }
//                    //比例大于1
//                    if (sum.compareTo(new BigDecimal(1)) == 1) {
//                        throw new RuntimeException();
//                    }
                    BackReviewDistribution backReviewDistribution = reviewDistributionService.findByUserId(id);
                    if (null != backReviewDistribution) {
                        //修改
                        backReviewDistribution.setDistributionRate(backUser.getDistributionRate());
                        reviewDistributionService.updateById(backReviewDistribution);
                    } else {
                        //插入
                        backReviewDistribution = new BackReviewDistribution();
                        backReviewDistribution.setUserId(backUser.getId());
                        backReviewDistribution.setDistributionRate(backUser.getDistributionRate());
                        reviewDistributionService.insert(backReviewDistribution);
                    }
                    //记录操作日志
                    BackUser oprBackUser = loginAdminUser(request);
                    BackDistributeRecord backDistributeRecord = new BackDistributeRecord();
                    backDistributeRecord.setOprSetDate(new Date());
                    backDistributeRecord.setOprSetRate(backUser.getDistributionRate());
                    backDistributeRecord.setOprUserId(oprBackUser.getId());
                    backDistributeRecord.setOprSetUserId(backUser.getId());
                    backDistributeRecordService.add(backDistributeRecord);

                }
                SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
        } catch (Exception e) {
            if (url == null) {
                erroMsg = "系统内部异常：" + e.getMessage();
                SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
            log.error("saveUpdate BackReviewDistribution error:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 查看操作记录
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("view_dis_opr_record")
    public String viewDisOprRecord(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        //当前页
        int currentPage = null == params.get(Constant.CURRENT_PAGE) ? 1 : Integer.valueOf(params.get(Constant.CURRENT_PAGE).toString());
        //每页的数量
        int numPerPage = null == params.get(Constant.PAGE_SIZE) ? 10 : Integer.valueOf(params.get(Constant.PAGE_SIZE).toString());
        //每页起始
        params.put("limitStart", (currentPage - 1) * numPerPage);
        //每页结束
        params.put("limitEnd", numPerPage);
        int allCount = backDistributeRecordService.findCunts(params);
        List<BackDistributeRecord> page = backDistributeRecordService.findPage(params);
        PageConfig<BackDistributeRecord> pageConfig = new PageConfig<BackDistributeRecord>();
        pageConfig.setTotalResultSize(allCount);
        pageConfig.setTotalPageNum(allCount / numPerPage + 1);
        pageConfig.setCurrentPage(currentPage);
        pageConfig.setPageSize(numPerPage);
        pageConfig.setItems(page);
        model.addAttribute("params", params);
        model.addAttribute("pm", pageConfig);
        model.addAttribute("targetTypeParam", "dialog");
        return "review/oprRecordList";
    }

}

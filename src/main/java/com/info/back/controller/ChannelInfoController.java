package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.info.analyze.service.IAppMarketStaticsService;
import com.info.back.pojo.AppMarketFlowRecord;
import com.info.back.pojo.AppMarketType;
import com.info.back.pojo.UserDetail;
import com.info.back.pojo.ezuiresult.ChannelCodes;
import com.info.back.pojo.ezuiresult.ChannelReportResult;
import com.info.back.service.IProductService;
import com.info.back.service.TaskJob;
import com.info.back.utils.*;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.pojo.*;
import com.info.web.service.*;
import com.info.web.util.*;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import com.liquan.oss.OSSModel;
import com.liquan.oss.OSSUpload;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("channel/")
public class ChannelInfoController extends BaseController {
    @Autowired
    private IChannelInfoService channelInfoService;
    @Autowired
    private IChannelReportService channelReportService;
    @Autowired
    private IUserService userService;
    @Autowired
    ILoanReportService loanReportService;
    @Autowired
    IThirdChannelReportService thirdChannelReportService;
    @Autowired
    TaskJob taskjob;
    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    ILoanMoneyReportService loanmoneyReportService;
    @Autowired
    private IAppMarketStaticsService appMarketStaticsService;
    @Autowired
    private IProductService iProductService;

    /**
     * 推广渠道分页
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getChannelInfoPage")
    public String getChannelInfoPage(HttpServletRequest request,
                                     Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<ChannelInfo> pageConfig = channelInfoService
                    .findPage(params);
            model.addAttribute("pm", pageConfig);
            // 用于搜索框保留值
            model.addAttribute("params", params);
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        return "userInfo/channelInfoPage";
    }

    /**
     * 推广员分页
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getChannelUserInfoPage")
    public String getChannelUserInfoPage(HttpServletRequest request,
                                         Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            HashMap<String, Object> chMap = getParametersO(request);
            BackUser backUser = this.loginAdminUser(request);
            dealParamMap(params, chMap, backUser);

            int superFlag = channelInfoService.getCountSuperChannelCode(backUser.getUserAddress());
            if(superFlag > 0){
                params.remove("channelCode");
                params.put("superCode",backUser.getUserAddress());
                chMap.put("channelSuperCode",backUser.getUserAddress());

                PageConfig<ChannelInfo> pageConfig = channelInfoService.findPage(params);

                /*
                 * 渠道商集合
                 */
                List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(chMap);
                model.addAttribute("channelSuperInfos", channelSuperInfos);
                model.addAttribute("pm", pageConfig);

            }else{

                PageConfig<ChannelInfo> pageConfig = channelInfoService.findChannelUserPage(params);
                List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
                /*
                 * 渠道商集合
                 */
                List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(chMap);
                model.addAttribute("channelSuperInfos", channelSuperInfos);

                model.addAttribute("channelList", channelList);
                model.addAttribute("pm", pageConfig);
            }

            // 用于搜索框保留值
            model.addAttribute("params", params);
            String bpath = request.getContextPath();
            model.addAttribute("bpath", bpath);

        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        return "userInfo/channelUserInfoPage";
    }

    /**
     * 推广记录分页
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getChannelRecordPage")
    public String getChannelRecordPage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            BackUser backUser = this.loginAdminUser(request);
            if (backUser != null
                    && !StringUtils.isBlank(backUser.getUserAddress())) {
                HashMap<String, Object> pm = new HashMap<>();
                pm.put("channelCode", backUser.getUserAddress());
                ChannelInfo channelInfo = channelInfoService
                        .findOneChannelInfo(pm);
                if (channelInfo != null) {
                    params.put("channelCode", channelInfo.getChannelCode());
                }
            }
            PageConfig<ChannelInfo> pageConfig = channelInfoService
                    .findChannelRecordPage(params);
            model.addAttribute("pm", pageConfig);
            // 用于搜索框保留值
            model.addAttribute("params", params);
            // channelReportService.saveChannelReport();
        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        return "userInfo/channelRecordPage";
    }

    /**
     * 添加或修改推广渠道
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveUpdateChannelInfo")
    public String saveUpdateChannelInfo(HttpServletRequest request,
                                        HttpServletResponse response, Model model, ChannelInfo channelInfo) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;

        try {
            /**
             * 是新增还是修改
             */
            boolean flag = params.containsKey("id");
            /*
             * 区分渠道商和渠道ID
             */
            Object channelId = params.get("id");
            params.remove("id");
            List<ChannelSuperInfo> channelSuperList = channelInfoService.findSuperAll(params);
            params.put("id",channelId);
            List<ChannelRate> channelRateList = channelInfoService.findChannelRateAll(params);
            model.addAttribute("channelSuperList", channelSuperList);
            model.addAttribute("channelRateList", channelRateList);
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (flag) {
                    // 更新的页面跳转
                    channelInfo = channelInfoService.findOneChannelInfo(params);
                    model.addAttribute("channelInfo", channelInfo);
                }
                url = "userInfo/editChannelInfo";
            } else {
                channelInfo.setStatus(1);
                // 更新或者添加操作
                if (channelInfo.getId() != null) {
                    // 加密
                    String passWord = MD5coding.MD5(AESUtil.encrypt(channelInfo.getChannelPassword(), ""));
                    channelInfo.setChannelPassword(passWord);
                    channelInfoService.updateById(channelInfo);
                } else {
                    ChannelInfo codeChannelInfo;
                    HashMap<String, Object> queryMap = new HashMap<>();
                    queryMap.put("channelCode", channelInfo.getChannelCode());
                    codeChannelInfo = channelInfoService.findOneChannelInfo(queryMap);

                    if (codeChannelInfo != null) {
                        SpringUtils.renderDwzResult(response, false,
                                "渠道编码已存在",
                                DwzResult.CALLBACK_CLOSECURRENT, params
                                        .get("parentId").toString());
                        return null;
                    }
                    String passWord = MD5coding.MD5(AESUtil.encrypt(channelInfo.getChannelPassword(), ""));

                    channelInfo.setChannelPassword(passWord);
                    channelInfoService.insert(channelInfo);
                }
                SpringUtils.renderDwzResult(response, true, "操作成功",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 分配推广员
     *
     * @param request req
     * @param model   model
     */
    @RequestMapping("relChannelUser")
    public String releaseRedenvelope(HttpServletRequest request, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        // 更新的页面跳转
        ChannelInfo channelInfo = channelInfoService.findOneChannelInfo(params);
        model.addAttribute("grant", true);
        model.addAttribute("channelInfo", channelInfo);

        return "userInfo/relChannelUser";
    }

    /**
     * 添加或修改推广员
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveUpdateChannelUserInfo")
    public String saveUpdateChannelUserInfo(HttpServletRequest request,
                                            HttpServletResponse response, Model model, User user) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    ChannelInfo channelInfo = channelInfoService
                            .findOneChannelInfo(params);
                    // 更新的页面跳转
                    // channelInfo =
                    // channelInfoService.findOneChannelInfo(params);
                    // model.addAttribute("channelInfo", channelInfo);
                    model.addAttribute("channelInfo", channelInfo);
                }
                url = "userInfo/saveChannelUser";
            } else {
                // 更新或者添加操作
                if (user.getId() == null) {
                    if (params.containsKey("channelId")) {
                        User validPhoneUser;
                        Map<String, Object> queryMap = new HashMap<>();
                        queryMap.put("userPhone", user.getUserPhone());
                        validPhoneUser = userService
                                .searchByUphoneAndUid(queryMap);

                        if (validPhoneUser != null) {
                            SpringUtils.renderDwzResult(response, false,
                                    "操作失败，该手机号已存在",
                                    DwzResult.CALLBACK_CLOSECURRENT, params
                                            .get("parentId").toString());
                            return null;
                        } else {
                            HashMap<String, Object> map = new HashMap<>();
                            String ip = this.getIpAddr(request);
                            // 加密
                            String passWord = MD5coding.MD5(AESUtil.encrypt(ConfigConstant.getConstant(Constant.DEFAULT_PWD), ""));
                            user.setUserFrom(params.get("channelId").toString());
                            user.setCreateIp(ip);
                            user.setPassword(passWord);
                            user.setUserName(user.getUserPhone());
                            user.setEquipmentNumber(user.getUserPhone());
                            userService.saveUser(user);
                            map.put("userPhone", user.getUserPhone());
                            User findUser = userService
                                    .searchUserByCheckTel(map);
                            if (findUser != null) {
                                String inviteUserid = Base64Utils.encodeStr(findUser.getId());
                                /*
                                 * 190301 用户注册链接渠道id加密
                                 */
                                String userFrom = AESUtil.encrypt(params.get("channelId").toString(),AESUtil.KEY_USER_FROM);
                                Map<String, String> keys = SysCacheUtils
                                        .getConfigParams(BackConfigParams.APP_IMG_URL);
                                String appurl = keys.get("qrcode_URL");
                                String qrurl = appurl
                                        + request.getContextPath() + "/"
                                        + "act/little-fish-register?invite_code="
                                        + inviteUserid + "&user_from="
                                        + userFrom;
                                if ("2".equals(params.get("channelNew"))) {
                                    qrurl = appurl
                                            + request.getContextPath() + "/"
                                            + "act/little-fish-register?invite_code="
                                            + inviteUserid + "&user_from="
                                            + userFrom;
                                }
                                log.info("saveUpdateChannelUserInfo qrurl=:{}", qrurl);
                                String backUrl = keys.get("channel_URL");
                                String relPath = backUrl+"/channel/toAppointChannelReport?aesCode="
                                        +AESUtil.encrypt(String.valueOf(params.get("channelCode")), AESUtil.KEY);

                                //params.put("userId", findUser.getId());
                                params.put("channelId", params.get("channelId"));
                                String shortRelPath = getShortUrl(relPath);
                                if(!"".equals(shortRelPath)){
                                    params.put("relPath", shortRelPath);
                                }else{
                                    params.put("relPath", relPath);
                                }

                                String shortUrl = getShortUrl(qrurl);
                                if(!"".equals(shortUrl)){
                                    params.put("remark", shortUrl);
                                }else{
                                    params.put("remark", qrurl);
                                }

                                channelInfoService.insertChannelUserInfo(params);
                            } else {
                                SpringUtils.renderDwzResult(response, false,
                                        "添加用户失败",
                                        DwzResult.CALLBACK_CLOSECURRENT, params
                                                .get("parentId").toString());
                            }
                        }
                    } else {
                        SpringUtils.renderDwzResult(response, false, "添加失败",
                                DwzResult.CALLBACK_CLOSECURRENT,
                                params.get("right_id").toString());
                        return null;
                    }
                    // channelInfoService.updateById(channelInfo);
                }
                SpringUtils.renderDwzResult(response, true, "操作成功",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }
    private String getShortUrl(String longUrl){
        String result="";
        try{
            String encodeUrl = URLEncoder.encode(longUrl,"UTF8");
            /*
             * 新浪短链接
             */
            String url1="http://api.t.sina.com.cn/short_url/shorten.json?source=2815391962"+"&url_long=" + encodeUrl;
            String resultStr = HttpUtil.doGet(url1,"UTF-8",null);
            JSONArray jsonArray = JSON.parseArray(resultStr);
            if(jsonArray!=null && jsonArray.size()>0){
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if(jsonObject!=null){
                    String shortUrl = jsonObject.getString("url_short");
                    if(shortUrl!=null){
                        result = shortUrl;
                    }
                }
            }
        }catch (Exception e){
            log.error("parse short url error:{}",e);
        }
        return result;
    }

    /**
     * 获取推广员动态信息
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("getDynamicInfo")
    public String getDynamicInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    ChannelInfo channelInfo = channelInfoService.findOneChannelInfoNew(params);
                    model.addAttribute("channelInfo", channelInfo);
                }
                url = "userInfo/saveDynamicChannelUser";
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }


    /**
     * 推广员动态添加
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveDynamic")
    public String saveDynamic(@RequestParam("registerPic") CommonsMultipartFile registerPic,
                              @RequestParam("downloadPic") CommonsMultipartFile downloadPic,
                              HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        HashMap<String, Object> params = this.getParametersO(request);
        String erroMsg = null;
        try {
            // 更新或者添加操作
            if (user.getId() == null) {
                if (params.containsKey("channelId")) {
                    User validPhoneUser;
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("userPhone", user.getUserPhone());
                    validPhoneUser = userService.searchByUphoneAndUid(queryMap);

                    if (validPhoneUser != null) {
                        SpringUtils.renderDwzResult(response, false, "操作失败，该手机号已存在", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
                        return null;
                    } else {
                        HashMap<String, Object> map = new HashMap<>();
                        String ip = this.getIpAddr(request);

                        String passWord = MD5coding.MD5(AESUtil.encrypt(ConfigConstant.getConstant(Constant.DEFAULT_PWD), ""));// 加密
                        user.setUserFrom(params.get("channelId").toString());
                        user.setCreateIp(ip);
                        user.setPassword(passWord);
                        user.setUserName(user.getUserPhone());
                        user.setEquipmentNumber(user.getUserPhone());// 设备号
                        userService.saveUser(user);
                        map.put("userPhone", user.getUserPhone());
                        User findUser = userService.searchUserByCheckTel(map);
                        if (findUser != null) {

                            String inviteUserid = Base64Utils.encodeStr(findUser.getId());

                            Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.APP_IMG_URL);
                            String appurl = keys.get("H5_DYNAMIC_URL");
                            String qrurl = appurl
                                    + "cashman_h5/act/light-loan-xjx-dynamic?invite_code="
                                    + inviteUserid + "&user_from="
                                    + user.getUserFrom();
                            if ("2".equals(params.get("channelNew"))) {
                                qrurl = appurl
                                        + "cashman_h5/act/light-loan-xjx-dynamic?invite_code="
                                        + inviteUserid + "&user_from="
                                        + user.getUserFrom();
                            }

//                            String qrName = MD5coding.MD5(String
//                                    .valueOf(user.getId())) + ".jpg";// 二维码图片名称
//                            String savePath = "/" + Constant.FILEPATH + "/"
//                                    + Constant.QR_FILEPATH + "/" + qrName;
//                            String qrfileRealPath = request
//                                    .getSession()
//                                    .getServletContext()
//                                    .getRealPath(
//                                            "/" + Constant.FILEPATH + "/"
//                                                    + Constant.QR_FILEPATH
//                                                    + "/" + qrName); // 二维码本地存储路径
//
//                            String logoPathDir = request
//                                    .getSession()
//                                    .getServletContext()
//                                    .getRealPath("common/web/images/logo_qr_xjx.png"); // LOGO获取本地存储路径
//
//                            QrCodeUtil.encode(qrurl, 300, 300, logoPathDir, qrfileRealPath);

                            params.put("userId", findUser.getId());
                            params.put("channelId", params.get("channelId"));
//                            params.put("relPath", savePath.replaceAll("\\\\", "\\/"));
                            params.put("relPath", "");
                            params.put("remark", qrurl);

                            channelInfoService.insertChannelUserInfo(params);


                            String registerPicUrl = getPicUrl(registerPic.getFileItem().getName(), registerPic.getInputStream(), params.get("channelId").toString(), "register");
                            String downloadPicUrl = getPicUrl(downloadPic.getFileItem().getName(), downloadPic.getInputStream(), params.get("channelId").toString(), "download");

                            ChannelDynamic channelDynamic = new ChannelDynamic();
                            channelDynamic.setChannelInfoId(Long.valueOf(params.get("channelId").toString()));
                            channelDynamic.setRegisterPicUrl(registerPicUrl);
                            channelDynamic.setDownloadPicUrl(downloadPicUrl);
                            channelDynamic.setUserInfoId(Long.valueOf(findUser.getId()));
                            channelInfoService.insertChannelDynamic(channelDynamic, user);

                        } else {
                            SpringUtils.renderDwzResult(response, false,
                                    "添加用户失败",
                                    DwzResult.CALLBACK_CLOSECURRENT, params
                                            .get("parentId").toString());
                        }
                    }
                } else {
                    SpringUtils.renderDwzResult(response, false, "添加失败",
                            DwzResult.CALLBACK_CLOSECURRENT,
                            params.get("right_id").toString());
                    return null;
                }
                // channelInfoService.updateById(channelInfo);
            }
            SpringUtils.renderDwzResult(response, true, "操作成功",
                    DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                            .toString());

        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return null;
    }

    private String getPicUrl(String name, InputStream is, String channelId, String type) {
        String bucketName = "xjxh5tg";
        String key = setPicName(channelId, type, name); //2017/06/渠道ID/reg_时间戳.jpg
        OSSUpload ossUpload = new OSSUpload();
        OSSModel ossModel = ossUpload.sampleUploadWithInputStream(bucketName, key, is);
        return ossModel.getUrl();
    }

    private String setPicName(String channelId, String type, String name) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        return year + "/" + (month + 1) + "/" + channelId + "/" + type + "_" + System.currentTimeMillis() + name.substring(name.indexOf("."));
    }

    /**
     * 删除角色
     *
     * @param request  req
     * @param response res
     */
    @RequestMapping("deleteChannelInfo")
    public void deleteChannelInfo(HttpServletRequest request,
                                  HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        boolean flag = deleteChannel(params);
        SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功" : "删除用户失败",
                DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
                        .toString());
    }

    private boolean deleteChannel(HashMap<String, Object> params) {
        boolean flag = false;
        try {
            Integer id = Integer.parseInt(params.get("id").toString());
            channelInfoService.deleteChannelInfoById(id);
            flag = true;
        } catch (Exception e) {
            log.error("deleteRole error:{}", e);
        }
        return flag;
    }

   /**
     * 推广统计(渠道)
     *
     * @param request req
     * @param model   model
     */

    @RequestMapping("getChannelReportPage")
    public String getChannelReportPage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            HashMap<String, Object> chMap = new HashMap<>();
            BackUser backUser = this.loginAdminUser(request);
            dealParamMap(params, chMap, backUser);
            boolean checkFlag = false;
            String channelNameNatural = params.get("channelName") == null ? "" : params.get("channelName").toString();
            String channelidNatural = params.get("channelid") == null ? "" : params.get("channelid").toString();
            if (params.get("channelName") != null && "自然流量".contains(params.get("channelName").toString())) {
                if (params.get("channelid") == null || "0".equals(params.get("channelid"))) {
                    params.remove("channelName");
                    params.put("channelid", "0");
                    checkFlag = true;
                }
            }

            /*
             * 是否为渠道商登录
             */
            int superFlag = channelInfoService.getCountSuperChannelCode(backUser.getUserAddress());
            if(superFlag > 0){
                params.remove("channelCode");
                params.put("superCode",backUser.getUserAddress());
                chMap.put("channelSuperCode",backUser.getUserAddress());
                PageConfig<ChannelReport> pageConfig = channelReportService.findPage(params);

//                List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
//                ChannelInfo ciNatural = new ChannelInfo();
//                ciNatural.setId(0);
//                ciNatural.setChannelName("自然流量");
//                channelList.add(ciNatural);
//
//                for (int i = channelList.size() - 1; i > 0; i--) {
//                    channelList.set(i, channelList.get(i - 1));
//                }
//                channelList.set(0, ciNatural);
//
//                model.addAttribute("channelList", channelList);
                List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(chMap);
                model.addAttribute("channelSuperInfos", channelSuperInfos);
                model.addAttribute("pm", pageConfig);
            }else{
                PageConfig<ChannelReport> pageConfig = channelReportService.findPage(params);
                //PageConfig<OutChannelLook> pageConfigs = channelReportService.findPageOut(params);
//                List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
//                ChannelInfo ciNatural = new ChannelInfo();
//                ciNatural.setId(0);
//                ciNatural.setChannelName("自然流量");
//                channelList.add(ciNatural);
//
//                for (int i = channelList.size() - 1; i > 0; i--) {fuck
//                    channelList.set(i, channelList.get(i - 1));
//                }
//                channelList.set(0, ciNatural);

//                model.addAttribute("channelList", channelList);
                List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(chMap);
                List<ChannelReport> list = new ArrayList<>();
               for(ChannelReport channelReport : pageConfig.getItems() ){
                   //qq占比
                    Integer qqCount= channelInfoService.findqqCount(channelReport.getChannelid());
                    if(qqCount != null && channelReport.getRegisterCount()!=null && channelReport.getRegisterCount()!=0){
                        Double  qqRate = qqCount*(1.0)/channelReport.getRegisterCount()*(1.0);
                        DecimalFormat df = new DecimalFormat("0.00");
                        channelReport.setQqRate(df.format(qqRate));
                    }else{
                        channelReport.setQqRate("0.00");
                    }
                    //微信占比
                    Integer wechatCount = channelInfoService.findWechatCount(channelReport.getChannelid());
                    if(wechatCount != null && channelReport.getRegisterCount()!=null && channelReport.getRegisterCount()!=0){
                        Double wechatRate = wechatCount*(1.0)/channelReport.getRegisterCount()*(1.0);
                        DecimalFormat df = new DecimalFormat("0.00");
                        channelReport.setWechatRate(df.format(wechatRate));
                    }else{
                        channelReport.setWechatRate("0.00");
                    }
                   //uv转化
                    if(null !=channelReport.getUvCount() &&channelReport.getUvCount() !=0 ){
                        if(null != channelReport.getRegisterCount()){
                            Double uvRate = channelReport.getRegisterCount()*(1.0)/channelReport.getUvCount()*(1.0);
                            DecimalFormat df = new DecimalFormat("0.00");
                            channelReport.setUvRate(df.format(uvRate));
                        }
                    }else{
                        channelReport.setUvCount(0);
                        channelReport.setUvRate("0.00");
                    }
                    /*for(OutChannelLook outChannelLook : pageConfigs.getItems()){
                        if(outChannelLook.getId() == channelReport.getChannelid()){
                            channelReport.setBorrowApplyCount(outChannelLook.getBorrowApplyCount());
                            channelReport.setLoanCount(outChannelLook.getLoanCount());
                            channelReport.setRepaymentCount(outChannelLook.getRepaymentCount());
                            channelReport.setRegistRatio(outChannelLook.getRegistRatio());
                            channelReport.setLoanRatio(outChannelLook.getLoanRatio());
                            channelReport.setRepayRatio(outChannelLook.getRepayRatio());
                        }
                    }*/
                    //申请笔数 放款笔数 放款率
                   List<String> idList=channelInfoService.findUserId(channelReport.getChannelid());
                   if(idList.size()>0) {
                       DecimalFormat df = new DecimalFormat("0.00");
                       //放款笔数
                       int loanCount = channelInfoService.findLoanCount(channelReport.getReportDate(), idList);
                       channelReport.setLoanCount(loanCount);
                       //申请笔数
                       int applyCount = channelInfoService.findApplyCount(channelReport.getReportDate(), idList);
                       channelReport.setBorrowApplyCount(applyCount);
                       //放款率 放款笔数/当日总注册数
                       if(channelReport.getRegisterCount() != 0){
                           double loanRate = channelReport.getLoanCount()*(1.0)/channelReport.getRegisterCount()*(1.0);
                           channelReport.setLoanRatio(df.format(loanRate));
                       }else{
                           channelReport.setLoanRatio("0.00");
                       }
                   }else{
                       channelReport.setLoanCount(0);
                       channelReport.setBorrowApplyCount(0);
                       channelReport.setLoanRatio("0.00");
                   }
                   list.add(channelReport);
               }
                pageConfig.setItems(list);
                model.addAttribute("channelSuperInfos", channelSuperInfos);
                model.addAttribute("pm", pageConfig);



            }

            if (checkFlag) {
                params.remove("channelid");
                params.put("channelName", channelNameNatural);
                params.put("channelid", channelidNatural);
            }
            // 用于搜索框保留值
            model.addAttribute("params", params);

        } catch (Exception e) {
            log.error("getChannelReportPage error:{}", e);
        }
        return "userInfo/channelReportPage";
    }


    /**
     * 应用市场流量统计分页
     *
     * @param request req
     * @param model   model
     */
    @RequestMapping("getAppMarketReportPage")
    public String getAppMarketReportPage(HttpServletRequest request,
                                         Model model) {

        try {
            HashMap<String, Object> params = getParametersO(request);
            //key 日期天 value 应用市场用户列表集合
//            BackUser backUser = this.loginAdminUser(request);

            //当前页
            int currentPage = null == params.get(Constant.CURRENT_PAGE) ? 1 : Integer.valueOf(params.get(Constant.CURRENT_PAGE).toString());
            //每页的数量
            int numPerPage = null == params.get(Constant.PAGE_SIZE) ? 10 : Integer.valueOf(params.get(Constant.PAGE_SIZE).toString());
            //每页起始
            params.put("limitStart", (currentPage - 1) * numPerPage);
            //每页结束
            params.put("limitEnd", numPerPage);
            //查询自然流量
            List<AppMarketFlowRecord> marketList = appMarketStaticsService.selectPageList(params);
            //查询所有应用市场数量
            int allCount = appMarketStaticsService.selectPageCunt(params);

            //查询所有应用市场
            List<AppMarketType> appTypeList = appMarketStaticsService.selectMarketTypeList();


            PageConfig<InfoReport> pageConfig = new PageConfig<>();
            pageConfig.setTotalResultSize(allCount);
            pageConfig.setTotalPageNum(allCount / numPerPage + 1);
            pageConfig.setCurrentPage(currentPage);
            pageConfig.setPageSize(numPerPage);

            model.addAttribute("appTypeList", appTypeList);
            model.addAttribute("marketList", marketList);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getAppMarketReportPage error:{}", e);
        }

        return "userInfo/appMarketReportPage";
    }

    /**
     * 应用市场流量统计分页
     *
     * @param request req
     * @param model   model
     */
    @RequestMapping("getAppMarketSumPage")
    public String getAppMarketSumPage(HttpServletRequest request, Model model) {

        try {
            HashMap<String, Object> params = getParametersO(request);
            //key 日期天 value 应用市场用户列表集合
//            BackUser backUser = this.loginAdminUser(request);

            //当前页
            int currentPage = null == params.get(Constant.CURRENT_PAGE) ? 1 : Integer.valueOf(params.get(Constant.CURRENT_PAGE).toString());
            //每页的数量
            int numPerPage = null == params.get(Constant.PAGE_SIZE) ? 10 : Integer.valueOf(params.get(Constant.PAGE_SIZE).toString());
            //每页起始
            params.put("limitStart", (currentPage - 1) * numPerPage);
            //每页结束
            params.put("limitEnd", numPerPage);
            //查询自然流量
            List<AppMarketFlowRecord> marketList = appMarketStaticsService.selectAllList(params);
            //查询所有应用市场数量
            int allCount = appMarketStaticsService.selectAllListCunt(params);

            //查询所有应用市场
            List<AppMarketType> appTypeList = appMarketStaticsService.selectMarketTypeList();

            for (AppMarketFlowRecord record : marketList) {

                double crossRate = 0.0;
                int relyCunt = (null == record.getRelyLoanCunt()) ? 0 : record.getRelyLoanCunt();
                int yesLoanCunt = (null == record.getYesLoanCunt()) ? 0 : record.getYesLoanCunt();
                int failLoanCunt = (null == record.getFailLoanCunt()) ? 0 : record.getFailLoanCunt();
                int allRegisterCunt = (null == record.getAllRegisterCunt()) ? 0 : record.getAllRegisterCunt();

                int fsCount = yesLoanCunt + failLoanCunt;
                if (fsCount > 0 && relyCunt > 0) {
                    crossRate = fsCount / (relyCunt + 0.0);
                }
                double regTrnRate = 0.0;
                if (relyCunt > 0 && allRegisterCunt > 0) {
                    regTrnRate = relyCunt / (allRegisterCunt + 0.0);
                }
                record.setRegisterTransRate(regTrnRate);

                record.setCrossRate(crossRate);

            }


            PageConfig<InfoReport> pageConfig = new PageConfig<>();
            pageConfig.setTotalResultSize(allCount);
            pageConfig.setTotalPageNum(allCount / numPerPage + 1);
            pageConfig.setCurrentPage(currentPage);
            pageConfig.setPageSize(numPerPage);

            model.addAttribute("appTypeList", appTypeList);
            model.addAttribute("marketList", marketList);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getAppMarketSumPage error:{}", e);
        }
        return "userInfo/getAppMarketSumPage";
    }

    @RequestMapping(value = "getChannelReportData")
    @ResponseBody
    public void getChannelReportData(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            if (!params.isEmpty() && params.get("beginTime") != null && isValidDate(params.get("beginTime").toString())) {
                String startDate = params.get("beginTime").toString();
                Date compare = DateUtil.getDate("2017-12-22", "yyyy-MM-dd");
                if (DateUtil.getDate(startDate, "yyyy-MM-dd").getTime() < compare.getTime()) {
                    startDate = "2017-12-22";
                }
                params.put("beginTime", startDate);
            } else {
                params.put("beginTime", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
            }
           List<ChannelReportResult> reportResults = new ArrayList<>();
            String channelCode = AESUtil.decrypt(params.get("channelCode").toString(), AESUtil.KEY);
            params.put("channelCode", channelCode);
            PageConfig<ChannelReport> pageConfig = channelReportService.findPage(getParams(params));
            String channelId = channelReportService.getChannelIdByCode(channelCode);
            for (ChannelReport report : pageConfig.getItems()) {
                ChannelReportResult reportResult;
                /*
                 * 当天实时查询
                 */
                if(DateUtil.getDateFormat("yyyy-MM-dd").equals(DateUtil.getDateFormat(report.getReportDate(),"yyyy-MM-dd"))){
                    report.setRegisterCount(channelReportService.getRegisterNow(channelId));
                    report.setDayRealnameCount(channelReportService.getRegisterRealNow(channelId));
                    reportResult = new ChannelReportResult(report);
                }else{
                    reportResult = new ChannelReportResult(report);

                }
                reportResults.add(reportResult);
            }
            renderJsonToPage(response, getResultPageConfig(pageConfig, pageConfig.getItems()));


        } catch (Exception e) {
            log.error("getChannelReportData error:{}", e);
        }
    }



    @RequestMapping("getRegisterUser")
    @ResponseBody
    public List<UserDetail> getRegisterUser (@RequestParam Map<String,Object> params){
        return userService.getUserByChannelid(params);
    }


    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
//　　　　　// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(true);
            format.parse(str);
        } catch (ParseException e) {
            log.error("isValidDate error:{}", e);
            convertSuccess = false;
        }
        return convertSuccess;
    }

    private HashMap<String, Object> getParams(HashMap<String, Object> params) {
        int rows = params.get("rows") == null ? 20 : Integer.parseInt(params.get("rows").toString());
        int page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
        params.put("numPerPage", rows);
        params.put("pageNum", page);
        return params;
    }

    private void renderJsonToPage(HttpServletResponse response, PageConfig pageConfig) {
        Map<String, Object> resultMap = new HashMap<>();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        resultMap.put("total", pageConfig.getTotalResultSize());
        resultMap.put("rows", pageConfig.getItems());
        try (PrintWriter pw = response.getWriter()) {
            if (pw != null) {
                pw.write(JSONArray.toJSONString(resultMap));
            }
        } catch (IOException e) {
            log.error("renderJsonToPage error:{}", e);
        }
    }

    @RequestMapping("toChannelCodes")
    public String toChannelCodes() {
        return "userInfo/channelCodes";
    }


    @RequestMapping("channelCodesData")
    public void channelCodesData(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            List<ChannelCodes> dataList = new ArrayList<>();
            PageConfig<ChannelInfo> pageConfig = channelInfoService.findPage(getParams(params));
            for (ChannelInfo info : pageConfig.getItems()) {
                ChannelCodes one = new ChannelCodes();
                one.setName(info.getChannelName());
                one.setAesCodes(AESUtil.encrypt(info.getChannelCode(), AESUtil.KEY));
                dataList.add(one);
            }
            renderJsonToPage(response, getResultPageConfig(pageConfig, dataList));
        } catch (Exception e) {
            log.error("channelCodesData error:{}", e);
        }
    }

    @SuppressWarnings("unchecked")
    private PageConfig getResultPageConfig(PageConfig paramConfig, List resultData) {
        PageConfig resultPage = new PageConfig();
        resultPage.setPageSize(paramConfig.getPageSize());
        resultPage.setTotalPageNum(paramConfig.getTotalPageNum());
        resultPage.setTotalResultSize(paramConfig.getTotalResultSize());
        resultPage.setCurrentPage(paramConfig.getCurrentPage());
        resultPage.setItems(resultData);
        return resultPage;
    }


    /**
     *对外推广渠道
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toAppointChannelReport")
    public String toAppointChannelReport(HttpServletRequest request, Model model) {
        String aesChannelCode = request.getParameter("aesCode");
        //String channelCode = request.getParameter("aesCode");
        String url = "userInfo/appointChannelReport";
        try {
            if (StringUtils.isEmpty(aesChannelCode)) {
                model.addAttribute("msg", "网页走丢了!");
                return "error";
            }
            String channelCode = AESUtil.decrypt(aesChannelCode, AESUtil.KEY);
            HashMap<String, Object> pm = new HashMap<>();
            pm.put("channelCode", channelCode);
            ChannelInfo channelInfo = channelInfoService
                    .findOneChannelInfo(pm);
            if (channelCode == null || channelInfo == null) {
                model.addAttribute("msg", "渠道不存在!");
                return "error";
            }
            //渠道名称
            model.addAttribute("channelName", channelInfo.getChannelName());
            model.addAttribute("channelCode", aesChannelCode);
            return url;
        } catch (Exception e) {
            log.error("toAppointChannelReport error:{}", e);
        }
        return "error";
    }


    /**
     * 推广统计(地区)
     *
     * @param request req
     * @param model   model
     */

    @RequestMapping("getChannelPrReportPage")
    public String getChannelPrReportPage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            HashMap<String, Object> chMap = new HashMap<>();
            BackUser backUser = this.loginAdminUser(request);
            dealParamMap(params, chMap, backUser);
            PageConfig<ChannelReport> pageConfig = channelReportService
                    .findPrPage(params);

            List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
            model.addAttribute("channelList", channelList);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getChannelReportPage error:{}", e);
        }
        return "userInfo/channelPrReportPage";
    }

    private void dealParamMap(HashMap<String, Object> params, HashMap<String, Object> chMap, BackUser backUser) {
        if (backUser != null && !StringUtils.isBlank(backUser.getUserAddress())) {
            HashMap<String, Object> pm = new HashMap<>();
            pm.put("channelCode", backUser.getUserAddress());
            ChannelInfo channelInfo = channelInfoService
                    .findOneChannelInfo(pm);
            if (channelInfo != null) {
                params.put("channelCode", channelInfo.getChannelCode());
                chMap.put("channelCode", channelInfo.getChannelCode());
            }
        }
    }

    /**
     * 推广统计(统计每个渠道商所有)
     *
     * @param request req
     * @param model   model
     */

    @RequestMapping("getChannelSumReportPage")
    public String getChannelSumReportPage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            HashMap<String, Object> chMap = new HashMap<>();
            BackUser backUser = this.loginAdminUser(request);
            dealParamMap(params, chMap, backUser);
            PageConfig<ChannelReport> pageConfig = channelReportService.findSumPage(params);

//            List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
//            model.addAttribute("channelList", channelList);
            model.addAttribute("params", params);
            params.remove("beginTime");
            params.remove("endTime");
            List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(params);
            model.addAttribute("channelSuperInfos", channelSuperInfos);
            model.addAttribute("pm", pageConfig);
            // 用于搜索框保留值

        } catch (Exception e) {
            log.error("getChannelReportPage error:{}", e);
        }
        return "userInfo/channelSumReportPage";
    }

    /**
     * 推广记录Excel
     */
    @RequestMapping("channelRecordToExcel")
    public void channelRecordToExcel(HttpServletRequest request,
                                     HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            params.put(Constant.PAGE_SIZE, 50000);
            int totalPageNum = channelInfoService.findParamsCount(params);
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "推广统计列表.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"注册用户", "注册用户手机号", "注册时间", "推广员姓名", "推广员电话",
                    "渠道商名称", "负责人"};
            for (int i = 1; i <= totalPageNum; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<ChannelInfo> pm = channelInfoService
                        .findChannelRecordPage(params);
                List<ChannelInfo> channelList = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (ChannelInfo channelInfo : channelList) {
                    Object[] conList = new Object[titles.length];
                    conList[0] = channelInfo.getUserName();
                    conList[1] = channelInfo.getUserTel();
                    conList[2] = DateUtil.getDateFormat(
                            channelInfo.getCreateTime(), "yy-MM-dd hh:mm:ss");
                    conList[3] = channelInfo.getRealname();
                    conList[4] = channelInfo.getUserPhone();
                    conList[5] = channelInfo.getChannelName();
                    conList[6] = channelInfo.getOperatorName();
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "放款列表", titles, contents, i,
                        pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }
    }

    /**
     * 推广统计excel
     */
    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    @RequestMapping("channelReportToExcel")
    public void borrowChartExcel(HttpServletResponse response, HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            int totalPageNum = channelReportService.findParamsCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "channelReport.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"渠道商", "日期", "注册量", "实名人数", "绑卡人数", "紧急联系人人数",
                    "运营商认证", "支付宝人数", "芝麻人数", "工作信息人数", "黑名单人数", "申请借款人数", "借款成功人数", "借款率",
                    "通过率", "新用户放款金额", "老用户放款金额", "逾期人数", "费用", "更新时间"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<ChannelReport> pm = channelReportService.findPage(params);
                List<ChannelReport> list = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (ChannelReport reports : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = reports.getChannelName() == null ? "" : reports.getChannelName();
                    conList[1] = DateUtil.getDateFormat(reports.getReportDate(),
                            "yyyy-MM-dd");
                    conList[2] = reports.getRegisterCount().toString();
                    conList[3] = reports.getAttestationRealnameCount().toString();
                    conList[4] = reports.getAttestationBankCount().toString();
                    conList[5] = reports.getContactCount().toString();
                    conList[6] = reports.getJxlCount().toString();
                    conList[7] = reports.getAlipayCount().toString();
                    conList[8] = reports.getZhimaCount().toString();
                    conList[9] = reports.getCompanyCount().toString();
                    conList[10] = reports.getBlackUserCount().toString();
                    conList[11] = reports.getBorrowApplyCount().toString();
                    conList[12] = reports.getBorrowSucCount().toString();
                    conList[13] = reports.getBorrowRate().toString();
                    conList[14] = reports.getPassRate().toString();
                    conList[15] = (reports.getNewIntoMoney()
                            .divide(new BigDecimal(100))).toString();
                    conList[16] = (reports.getOldIntoMoney()
                            .divide(new BigDecimal(100))).toString();
                    conList[17] = reports.getLateDayCount().toString();
                    conList[18] = reports.getChannelMoney().toString();
                    conList[19] = DateUtil.getDateFormat(
                            reports.getCreatedAt(), "yy-MM-dd hh:mm:ss");
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "推广统计", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }
    }

    /**
     * 推广统计(地区)excel
     */
    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    @RequestMapping("channelPrReportToExcel")
    public void channelPrReportToExcel(HttpServletResponse response, HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            int size = 50000;
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            params.put("statusList", Arrays.asList(BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_BFHK, BorrowOrder.STATUS_YHK,
                    BorrowOrder.STATUS_YQYHK, BorrowOrder.STATUS_YHZ, BorrowOrder.STATUS_YHZ, BorrowOrder.STATUS_KKZ));
//			params.put("borrowStatus", borrowStatus);
            int totalPageNum = channelReportService.findPrParamsCount(params);
            if (totalPageNum > 0) {
                if (totalPageNum % size > 0) {
                    total = totalPageNum / size + 1;
                } else {
                    total = totalPageNum / size;
                }
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "channelReportPr.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"省份", "城市", "区域", "渠道商", "日期", "注册量", "实名人数",
                    "绑卡人数", "紧急联系人人数", "运营商认证", "支付宝人数", "芝麻人数", "工作信息人数",
                    "申请借款人数", "借款成功人数", "通过率", "放款总金额"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                PageConfig<ChannelReport> pm = channelReportService.findPrPage(params);
                List<ChannelReport> list = pm.getItems();
                List<Object[]> contents = new ArrayList<>();
                for (ChannelReport reports : list) {
                    String[] conList = new String[titles.length];
                    conList[0] = reports.getChannelProvince();
                    conList[1] = reports.getChannelCity();
                    conList[2] = reports.getChannelArea();
                    conList[3] = reports.getChannelName();
                    conList[4] = DateUtil.getDateFormat(reports.getReportDate(),
                            "yyyy-MM-dd");
                    conList[5] = reports.getRegisterCount().toString();
                    conList[6] = reports.getAttestationRealnameCount().toString();
                    conList[7] = reports.getAttestationBankCount().toString();
                    conList[8] = reports.getContactCount().toString();
                    conList[9] = reports.getJxlCount().toString();
                    conList[10] = reports.getAlipayCount().toString();
                    conList[11] = reports.getZhimaCount().toString();
                    conList[12] = reports.getCompanyCount().toString();
                    conList[13] = reports.getBorrowApplyCount().toString();
                    conList[14] = reports.getBorrowSucCount().toString();
                    conList[15] = reports.getPassRate().toString();
                    conList[16] = (reports.getIntoMoney()
                            .divide(new BigDecimal(100))).toString();
                    contents.add(conList);

                }
                ExcelUtil.buildExcel(workbook, "推广统计(地区)", titles, contents, i, pm.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("导出excel失败:{}", e);
        }

    }

//    /**
//     * 生成二维码
//     *
//     * @param request  req
//     * @param response res
//     */
//    @RequestMapping("deleteChannelInfo")
//    public void qrChannel(HttpServletRequest request,
//                          HttpServletResponse response) {
//        HashMap<String, Object> params = this.getParametersO(request);
//        boolean flag = deleteChannel(params);
//        SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功" : "删除用户失败",
//                DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
//                        .toString());
//    }

    @RequestMapping("toAgainChannelReport")
    public String toAgainChannelReport(HttpServletRequest request,
                                       HttpServletResponse response, Model model) {
        Map<String, String> params = this.getParameters(request);
        model.addAttribute("params", params);
        String url = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                url = "userInfo/againReport";
            } else {
                if (params.get("nowTime") != null) {
                    jedisCluster.del("channel_report");
                    channelReportService.saveChannelReport(String.valueOf(params.get("nowTime")));
                }
                SpringUtils.renderDwzResult(response, true, "操作成功，如要继续操作，请五分钟后再进行！", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId"));
            }
        } catch (Exception e) {
            log.error("againReport error:{}", e);
        }
        return url;
    }

    /**
     * 渠道商管理分页
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getChannelSuperInfoPage")
    public String getChannelSuperInfoPage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<ChannelSuperInfo> pageConfig = channelInfoService.findChannelSuperPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getchannelSuperInfoPage error:{}", e);
        }
        return "channel/channelSuperInfoPage";
    }


    /**
     * 添加或修改推广渠道
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveUpdateChannelSuperInfo")
    public String saveUpdateChannelSuperInfo(HttpServletRequest request,
                                             HttpServletResponse response, Model model, ChannelSuperInfo channelSuperInfo) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;

        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    // 更新的页面跳转
                    channelSuperInfo = channelInfoService.findOneChannelSuperInfo(params);
                    model.addAttribute("channelSuperInfo", channelSuperInfo);
                }
                url = "channel/editChannelSuperInfo";
            } else {

                // 更新或者添加操作
                if (channelSuperInfo.getId() != null) {
//					AESUtil aesEncrypt = new AESUtil();
//					String passWord = MD5coding.MD5(aesEncrypt.encrypt(channelInfo.getChannelPassword(),""));// 加密

//					channelSuperInfo.setChannelPassword(passWord);
                    channelInfoService.updateChannelSuperById(channelSuperInfo);
                } else {
                    ChannelSuperInfo codeChannelInfo;
                    HashMap<String, Object> queryMap = new HashMap<>();
                    queryMap.put("channelSuperCode", channelSuperInfo.getChannelSuperCode());
                    codeChannelInfo = channelInfoService.findOneChannelSuperInfo(queryMap);

                    if (codeChannelInfo != null) {
                        SpringUtils.renderDwzResult(response, false,
                                "渠道编码已存在",
                                DwzResult.CALLBACK_CLOSECURRENT, params
                                        .get("parentId").toString());
                        return null;
                    }
//					AESUtil aesEncrypt = new AESUtil();
//					String passWord = MD5coding.MD5(aesEncrypt.encrypt(channelInfo.getChannelPassword(),""));// 加密

//					channelInfo.setChannelPassword(passWord);
                    channelInfoService.insertChannelSuperInfo(channelSuperInfo);
                }
                SpringUtils.renderDwzResult(response, true, "操作成功",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }


    /**
     * 渠道商管理分页
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("getChannelRatePage")
    public String getChannelRatePage(HttpServletRequest request, Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            PageConfig<ChannelRate> pageConfig = channelInfoService.findChannelRatePage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值

        } catch (Exception e) {
            log.error("getChannelRatePage error:{}", e);
        }
        return "channel/channelRatePage";
    }


    /**
     * 添加或修改推广渠道
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("saveUpdateChannelRate")
    public String saveUpdateChannelRate(HttpServletRequest request,
                                        HttpServletResponse response, Model model, ChannelRate channelRate) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;

        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    // 更新的页面跳转
                    channelRate = channelInfoService.findOneChannelRateInfo(params);
                    model.addAttribute("channelRate", channelRate);
                }
                url = "channel/editChannelRate";
            } else {

                // 更新或者添加操作
                if (channelRate.getId() != null) {
//					AESUtil aesEncrypt = new AESUtil();
//					String passWord = MD5coding.MD5(aesEncrypt.encrypt(channelInfo.getChannelPassword(),""));// 加密

//					channelSuperInfo.setChannelPassword(passWord);
                    channelInfoService.updateChannelRateById(channelRate);
                } else {
                    ChannelRate codeChannelRate;
                    HashMap<String, Object> queryMap = new HashMap<>();
                    queryMap.put("channelRateName", channelRate.getChannelRateName());
                    codeChannelRate = channelInfoService.findOneChannelRateInfo(queryMap);

                    if (codeChannelRate != null) {
                        SpringUtils.renderDwzResult(response, false,
                                "渠道费率名称已存在",
                                DwzResult.CALLBACK_CLOSECURRENT, params
                                        .get("parentId").toString());
                        return null;
                    }

                    channelInfoService.insertChannelRate(channelRate);
                }
                SpringUtils.renderDwzResult(response, true, "操作成功",
                        DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
            }
        } catch (Exception e) {
            erroMsg = "服务器异常，请稍后重试！";
            SpringUtils.renderDwzResult(response, false, "操作失败,原因："
                            + erroMsg, DwzResult.CALLBACK_CLOSECURRENT,
                    params.get("parentId").toString());
            log.error("添加渠道费率信息失败，异常信息:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

    /**
     * 计算费率
     *
     * @param request  req
     * @param response res
     * @param model    model
     * @return str
     */
    @RequestMapping("reckChannelRate")
    public String reckChannelRate(HttpServletRequest request,
                                  HttpServletResponse response, Model model, ChannelReport channelReport) {
        //Map<String, Object> params =this.getParameters(request);

        HashMap<String, Object> params = this.getParametersO(request);
        model.addAttribute("params", params);
        String url = null;
        ChannelRate channelRate;
        ChannelInfo channelInfo;
        try {
            if (params.containsKey("id")) {
                // 更新的页面跳转
                HashMap<String, Object> queryMap = new HashMap<>();
                HashMap<String, Object> queryMap1 = new HashMap<>();

                channelReport = channelReportService.getChannelReportById(channelReport.getId());
                queryMap1.put("id", channelReport.getChannelid());
                channelInfo = channelInfoService.findOneChannelInfo(queryMap1);

                if (channelInfo == null || channelInfo.getRateId() == null) {
                    SpringUtils.renderDwzResult(response, false, "该渠道没有配置计算公式，请配置", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                            .toString());
                    return null;
                } else {
                    queryMap.put("id", channelInfo.getRateId());
                    channelRate = channelInfoService.findOneChannelRateInfo(queryMap);
                    if (channelRate == null) {
                        SpringUtils.renderDwzResult(response, false, "该渠道费率不存在", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
                                .toString());
                        return null;
                    }
                }

                BigDecimal intoMoney = BigDecimal.ZERO.equals(channelReport.getNewIntoMoney())?BigDecimal.ZERO : channelReport.getNewIntoMoney().divide(new BigDecimal(100),BigDecimal.ROUND_HALF_UP);

                BigDecimal money = new BigDecimal(channelReport.getRegisterCount()).multiply(channelRate.getChannelRegisterRate())
                        .add(((intoMoney).multiply(channelRate.getChannelNewloanRate())).divide(new BigDecimal(100), 0,
                                BigDecimal.ROUND_HALF_UP));
                model.addAttribute("channelInfo", channelInfo);
                model.addAttribute("channelRate", channelRate);
                model.addAttribute("money", money == null ? BigDecimal.ZERO : money);
                model.addAttribute("channelReport", channelReport);
            }
            url = "channel/finChannelRate";

        } catch (Exception e) {
            log.error("againReport error:{}", e);
        }
        return url;
    }


//	@RequestMapping("getChannelReportChar")
//	public String getChannelReportChar(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		try {
//			HashMap<String, Object> params = getParametersO(request);
//			HashMap<String, Object> chMap = new HashMap<String, Object>();
//			BackUser backUser = this.loginAdminUser(request);
//			Object channelid = params.get("channelid");
//			Object endTime = params.get("endTime");
//			Object beginTime = params.get("beginTime");
//			List<ChannelReport> channelReportList = new ArrayList<ChannelReport>();
//			if(channelid!=null&&beginTime!=null&&endTime!=null){
//				channelReportList = channelReportService.getAllChannelReports(params);
//			}
//			List<ChannelInfo> channelList = channelInfoService.findAll(chMap);
//
//			ChannelReport channelReport = new ChannelReport();
//			String[] channelReportDates = new String[channelReportList.size()] ;
//			int[] registerCounts = new int[channelReportList.size()] ;
//			StringBuilder sbu=new StringBuilder();
//			String str="";
//			String aaa= "";
//			JSONObject json = new JSONObject();
//			if(channelReportList.size()>0){
//				int j=7;
//				if(channelReportList.size()<7){
//					j=channelReportList.size();
//				}
//				for (int i = 0; i < j; i++) {
//					channelReport = channelReportList.get(i);
//					if(channelReport!=null){
//						channelReportDates[i]= DateUtil.getDateFormat(channelReport.getReportDate(),"yyyy-MM-dd HH:mm:ss");
//						sbu.append(channelReport.getRegisterCount()).append(",");
//						registerCounts[i] = channelReport.getRegisterCount();
//					}
//
//				}
//				if(sbu!=null&&sbu.length()>0){
//					str=sbu.toString().substring(0, sbu.length()-1);
//				}
//
//			}
//
//			HashMap<String, Object>  xxMap = new HashMap<String, Object>();
//			xxMap.put("name", "注册人数");
//			xxMap.put("data", registerCounts);
//			HashMap<String,Object> map = new HashMap<String, Object>();
//			map.put("series", xxMap);
//			model.addAttribute("series",FastJsonUtils.toJson(xxMap));
//			model.addAttribute("aaa",FastJsonUtils.toJson(aaa));
//			model.addAttribute("channelReportDates", channelReportDates);
//			model.addAttribute("registerCounts",str);
//			//model.addAttribute("registerCounts", FastJsonUtils.toJson(registerCounts));
//			model.addAttribute("channelList", channelList);
//			model.addAttribute("channelReportList", channelReportList);
//			model.addAttribute("params", params);// 用于搜索框保留值
//
//		} catch (Exception e) {
//			logger.error("getChannelReportPage error", e);
//		}
//		return "channel/channelReportChar";
//	}

    /**
     * 根据type跳转到对应的视图
     */
    @RequestMapping("getChannelReportChar")
    public String getChannelReportChar(HttpServletRequest request, Model model) {
        Map<String, Object> requests = this.getParametersO(request);
        Map<String, String> params = new HashMap<>();
        try {
            List<ChannelInfo> channelList = channelInfoService.findAll(requests);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            params.put("beginTime", sdf.format(DateUtil.addDay(today, -14)));
            params.put("endTime", sdf.format(today));
            if (channelList != null && channelList.size() > 0) {
                params.put("channelid", "0");
            }
            model.addAttribute("channelList", channelList);
            model.addAttribute("params", params);

        } catch (Exception e) {
            log.error("chartsDispatcher error params:{}", e);
        }
        return "channel/channelReportChar";
    }

    /**
     * 查看分析图<br>
     *
     * @param request   req
     * @param response  res
     * @param model     model
     * @param totalJson totalJson
     */
    @RequestMapping("getChannelChar")
    public void getChannelReportChar(HttpServletRequest request,
                                     HttpServletResponse response, Model model, TotalJson totalJson) {
        HashMap<String, Object> params = getParametersO(request);
        try {
            Object channelid = params.get("channelid");
            Object endTime = params.get("endTime");
            Object beginTime = params.get("beginTime");
            List<ChannelReport> channelReportList = new ArrayList<>();
            if (channelid != null && beginTime != null && endTime != null) {
                channelReportList = channelReportService.getAllChannelReports(params);
            }
//            List<ChannelInfo> channelList = channelInfoService.findAll(chMap);

            ChannelReport channelReport;

//            JSONObject json = new JSONObject();
            List<String> categoriesDate = new ArrayList<>();
            SeriesObj vipObj = new SeriesObj("注册人数");
            SeriesObj regObj = new SeriesObj("实名人数");
            SeriesObj pcObj = new SeriesObj("借款人数");
            if (channelReportList != null && channelReportList.size() > 0) {
                List<Integer> countVip = new ArrayList<>();
                List<Integer> countReg = new ArrayList<>();
                List<Integer> countPcReg = new ArrayList<>();
                int j = 14;
                if (channelReportList.size() < 14) {
                    j = channelReportList.size();
                }
                for (int i = 0; i < j; i++) {
                    channelReport = channelReportList.get(i);
                    if (channelReport != null) {
                        countVip.add(channelReport.getRegisterCount());
                        countReg.add(channelReport.getAlipayCount());
                        countPcReg.add(channelReport.getBorrowApplyCount());
//						channelReportDates[i]= DateUtil.getDateFormat(channelReport.getReportDate(),"yyyy-MM-dd HH:mm:ss");
                        categoriesDate.add(DateUtil.getDateFormat(channelReport.getReportDate(), "yy-MM-dd"));
                    }
                }
                vipObj.setData(countVip);
                regObj.setData(countReg);
                pcObj.setData(countPcReg);
            }
            totalJson.setCategories(categoriesDate);
            totalJson.setSeriesObj(Arrays.asList(regObj, pcObj, vipObj));

        } catch (Exception e) {
            log.error("getChannelReportPage error:{}", e);
        }
        model.addAttribute("params", params);
        SpringUtils.renderJson(response, totalJson);
    }

    /**
     * New查看推广员
     *
     * @param request req
     * @param model   model
     * @return str
     */
    @RequestMapping("ChannelUserInfoPage")
    public String ChannelUserInfoPage(HttpServletRequest request,
                                      Model model) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            HashMap<String, Object> chMap = getParametersO(request);
            BackUser backUser = this.loginAdminUser(request);
            dealParamMap(params, chMap, backUser);
            PageConfig<ChannelInfo> pageConfig = channelInfoService.findUserAllPage(params);

            List<ChannelInfo> channelList = channelInfoService.findAllUserList(chMap);
            model.addAttribute("channelList", channelList);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            String bpath = request.getContextPath();
            model.addAttribute("bpath", bpath);

        } catch (Exception e) {
            log.error("getUserPage error:{}", e);
        }
        return "userInfo/channelListInfoPage";
    }

//    //获取当天的渠道商 推荐过来的用户界面
//    @RequestMapping("fromChannelUserListToday")
//    public String getFromChannelUser(HttpServletRequest request, HttpServletResponse response, Model model) {
//        log.info("fromChannelUserListToday ");
//        return null;
//    }

    @RequestMapping("getChannelUserInfos")
    public String getChannelUserInfos(HttpServletRequest request, Model model, ChannelReport channelReport) {
        try {
            HashMap<String, Object> params = getParametersO(request);
            Integer channelReportId = channelReport.getId();
            channelReportId = channelReportId == null ? Integer.parseInt(request.getParameter("channelReportId")) : channelReportId;
            channelReport = channelReportService.getChannelReportById(channelReportId);
            Integer channelId = channelReport.getChannelid();
            params.put("channelId", channelId);
            PageConfig<ChannelGetUserInfo> pageConfig = channelInfoService.findChannelGetUserInfo(params);
//		List<ChannelGetUserInfo> results = channelInfoService.findChannelGetUserInfo(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
            model.addAttribute("channelReportId", channelReportId);
        } catch (Exception e) {
            log.error("getChannelUserInfos error:{}", e);
        }
        return "channel/channelGetUserDetail";
    }

    @RequestMapping("updateStatus")
    public void updateStatus(String channleCode,Integer status,HttpServletResponse response){
        Map<String, Object> params = new HashMap<String,Object>();
        try{
            ChannelInfo channelInfo = channelInfoService.findChannelCode(channleCode);
            channelInfo.setStatus(status);
            channelInfoService.updateById(channelInfo);
            params.put("result",0);
        }catch(Exception e){
           log.error("开启或者关闭渠道"+e);
           params.put("result",1);
        }
        SpringUtils.renderJson(response, params);
    }
    /*@RequestMapping("testChannelReport")
    public String testChannelReport(){
        String nowTime = null;
        channelReportService.saveChannelReport(nowTime);
        return "success";
    };*/
    //渠道逾期统计
    @RequestMapping("getOveChannelCount")
    public String oveChannelCount(HttpServletRequest request,
                                  Model model){
        HashMap<String, Object> params = getParametersO(request);
        HashMap<String, Object> chMap = new HashMap<>();
        BackUser backUser = this.loginAdminUser(request);
        dealParamMap(params, chMap, backUser);
        boolean checkFlag = false;
        String channelNameNatural = params.get("channelName") == null ? "" : params.get("channelName").toString();
        String channelidNatural = params.get("channelid") == null ? "" : params.get("channelid").toString();
        if (params.get("channelName") != null && "自然流量".contains(params.get("channelName").toString())) {
            if (params.get("channelid") == null || "0".equals(params.get("channelid"))) {
                params.remove("channelName");
                params.put("channelid", "0");
                checkFlag = true;
            }
        }
        List<ChannelSuperInfo> channelSuperInfos = channelInfoService.findSuperAll(chMap);
        PageConfig<OveChannelInfo> pageConfig = channelReportService.findOveChannelId(params);
        model.addAttribute("channelSuperInfos", channelSuperInfos);
        model.addAttribute("pm",pageConfig);
        if (checkFlag) {
            params.remove("channelid");
            params.put("channelName", channelNameNatural);
            params.put("channelid", channelidNatural);
        }
        // 用于搜索框保留值
        model.addAttribute("params", params);
        return "userInfo/channelOvePage";
    }
    /**
     * 查看产品详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="getProductDetail" )
    @ResponseBody
    public Model getProductDetail(Model model,Integer id,HashMap<String,Object> params){
        ProductDetail productDetail = iProductService.getProductDetail(id);
        List<BackLimit> limitList= iProductService.findLimitList(params);
        List<BackExtend> extendList = iProductService.findExtendList(params);
        model.addAttribute("limitList",limitList);
        model.addAttribute("extendList",extendList);
        model.addAttribute("productDetail",productDetail);
        return model;
    }
    /**
     * 指向产品页面
     * @return
     */
    @RequestMapping("goProductList")
    public String getProductList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        List<ProductDetail> list = iProductService.moneyList(params);
        model.addAttribute("productMoneyList",list);
        PageConfig<ProductDetail>  pageConfig = iProductService.getProductList(params);
        model.addAttribute("pm",pageConfig);
        model.addAttribute("params",params);
        return "sys/productList";
    }
    /**
     * 添加产品
     * @param borrowProductConfig
     * @return
     */
    @RequestMapping(value="addProduct")
    @ResponseBody
    public Result addProduct(BorrowProductConfig borrowProductConfig){
        try{
            iProductService.addProduct(borrowProductConfig);
            return Result.success();
        }catch (Exception e){
            log.error("添加失败"+e);
            return Result.error("添加失败");
        }
    }
    /**
     * 修改产品
     * @param borrowProductConfig
     * @param
     * @return
     */
    @RequestMapping(value="updateProduct")
    @ResponseBody
    public Result updateProduct(BorrowProductConfig borrowProductConfig){
        try{
            iProductService.updateProduct(borrowProductConfig);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }

    /**
     * 设置为默认产品
     * @param id
     * @return
     */
    @RequestMapping(value="openOrCloseProduct")
    @ResponseBody
    public Result openOrCloseProduct(Integer id){
        try {
            iProductService.openOrCloseProduct(id);
            return Result.success();
        } catch (Exception e) {
            log.error("设置为默认产品"+e);
            return Result.error("设置为默认产品出错");
        }
    }
    /**
     * 续期列表
     * @param model
     * @return
     *//*
	@RequestMapping(value="getExtendList")
	@ResponseBody
	public Model getExtendList(Model  model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
	    PageConfig<BackExtend> pageConfig = iProductService.getExtendList(params);
	    model.addAttribute("pm",pageConfig);
		return model;
	}*/

    /**
     * 添加续期
     * @param backExtend
     * @return
     */
    @RequestMapping(value="addExtend")
    @ResponseBody
    public Result addExtend(BackExtend backExtend){
        try{
            iProductService.addExtend(backExtend);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 修改续期
     * @param backExtend
     * @return
     */
    @RequestMapping(value="updateExtend")
    @ResponseBody
    public Result updateExtend(BackExtend backExtend){
        try{
            iProductService.updateExtend(backExtend);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 提额列表
     * @param model
     * @return
     *//*
	@RequestMapping(value="getLimitList")
	@ResponseBody
	public Model getLimitList(Model  model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackLimit> pageConfig = iProductService.getLimitList(params);
        model.addAttribute("pm",pageConfig);
		return model;
	}*/
    /**
     * 添加提额
     * @param backLimit
     * @return
     */
    @RequestMapping(value="addBackLimit")
    @ResponseBody
    public Result addBackLimit(BackLimit backLimit){
        try{
            iProductService.addLimit(backLimit);
            return Result.success();
        }catch (Exception e){
            log.error("添加失败"+e);
            return Result.error("添加失败");
        }
    }
    /**
     * 修改提额
     * @param backLimit
     * @return
     */
    @RequestMapping(value="updateBackLimit")
    @ResponseBody
    public Result updateBackLimit(BackLimit backLimit){
        try{
            iProductService.updateLimit(backLimit);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 指向续期页面
     * @return
     */
    @RequestMapping("goextendList")
    public String getextendList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackExtend> pageConfig = iProductService.getExtendList(params);
        model.addAttribute("pm",pageConfig);
        List<BackExtend> list = iProductService.findExtendList(params);
        model.addAttribute("extendList",list);
        model.addAttribute("params",params);
        return "sys/extendList";
    }

    /**
     * 指向提额页面
     * @return
     */
    @RequestMapping("goLimitList")
    public String getLimitList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackLimit> pageConfig = iProductService.getLimitList(params);
        model.addAttribute("pm",pageConfig);
        List<BackLimit> list = iProductService.findLimitList(params);
        model.addAttribute("limitList",list);
        model.addAttribute("params",params);
        return "sys/limitList";
    }
    /**
     * 进入添加/修改产品页面
     */
    @RequestMapping("toAddOrUpdateProduct")
    public String toAddOrUpdateProduct(Integer id,Model model,HashMap<String,Object> params){
        List<BackLimit> limitList= iProductService.findLimitList(params);
        List<BackExtend> extendList = iProductService.findExtendList(params);
        model.addAttribute("limitList",limitList);
        model.addAttribute("extendList",extendList);
        if(id != null){
            ProductDetail productDetail = iProductService.getProductDetail(id);
            model.addAttribute("productDetail",productDetail);
            model.addAttribute("id",id);
            return "sys/addOrUpdateProduct";
        }
        return "sys/addOrUpdateProduct";
    }

    /**
     * 进入产品详情页面
     * @return
     */
    @RequestMapping("toProductDetail")
    public String toProductDetail(Model model,Integer id,HashMap<String,Object> params){
        ProductDetail productDetail = iProductService.getProductDetail(id);
        model.addAttribute("productDetail",productDetail);
        return "sys/productDetail";
    }

    /**
     * 进入添加或修改续期页面
     * @param
     * @return
     */
    @RequestMapping("toAddOrUpdateExtend")
    public String toAddOrUpdateExtend(Integer id,Model model, HashMap<String,Object> params){
        if(id != null ){
            BackExtend backExtend = iProductService.findExtend(id);
            model.addAttribute("backExtend",backExtend);
            model.addAttribute("id",id);
            return "sys/addOrUpdateExtend";
        }
        return "sys/addOrUpdateExtend";
    }

    /**
     * 进入添加或修改提额页面
     * @param id
     * @return
     */
    @RequestMapping("toAddOrUpdateLimit")
    public String toAddOrUpdateLimit(Integer id,Model model){
        List<ProductDetail> list = iProductService.moneyList(null);
        if(id != null){
            BackLimit backLimit = iProductService.findLimit(id);
            //查询产品详情
            ProductDetail productDetail = iProductService.getProductDetail(backLimit.getLimitProductId());
            model.addAttribute("backLimit",backLimit);
            model.addAttribute("productDetail",productDetail);
            model.addAttribute("list",list);
            model.addAttribute("id",id);
            return "sys/addOrUpdateLimit";
        }
        model.addAttribute("list",list);
        return "sys/addOrUpdateLimit";
    }
}

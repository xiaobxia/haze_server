package com.info.back.controller;

import com.info.back.service.IBannerService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.PropertiesUtil;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.SysCacheUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.Banner;
import com.info.web.service.IBackConfigParamsService;
import com.info.web.util.AliyunOSSClientUtil;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("banner/")
public class BannerController extends BaseController {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IBackConfigParamsService backConfigParamsService;
    @Autowired
    private AliyunOSSClientUtil ossClientUtil;

    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        String showType = request.getParameter("showType");
        String channelType = request.getParameter("channelType");
        String columnType = request.getParameter("columnType");
        String sortType = request.getParameter("sortType");
        if (StringUtils.equals(channelType, "全部")) {
            params.remove("channelType");
        }
        if (StringUtils.equals(showType, "全部")) {
            params.remove("showType");
        }
        if (StringUtils.equals(columnType, "全部")) {
            params.remove("columnType");
        }
        if (StringUtils.equals(sortType, "无排序")) {
            params.remove("sortType");
        }
        ModelAndView mav = new ModelAndView("banner/list");
        mav.addObject("params", params);
        PageConfig<Banner> pageConfig = bannerService.findPage(params);
        mav.addObject("pm", pageConfig);
        return mav;
    }

    @RequestMapping("accountAndTelManage")
    public String accountAndTelManage(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        HashMap<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("sysType", BackConfigParams.WEBSITE);
        String msg = "操作成功!";
        int updateCount = 0;
        try {
            List<BackConfigParams> configs = backConfigParamsService.findParams(paramMap);
            List<BackConfigParams> forUpdateConfigs = new ArrayList<>();
            final String serviceTel = "service_phone";
            final String serviceQQ = "services_qq";
            final String onlineTime = "online_time";
            final String telTime = "tel_time";
            final String payAccount = "pay_account";
            final String accountName = "account_name";
            final String qrCode = "qr_code";
            final String wechatCode = "wechat_code";
            final String weAccountName = "we_account_name";
            if ("toPage".equals(params.get("type"))) {

                if (CollectionUtils.isNotEmpty(configs)) {
                    configs.forEach(config -> {
                        String sysKey = config.getSysKey();
                        String sysVal = config.getSysValue();
                        switch (sysKey) {
                            case serviceTel:
                                model.addAttribute("serviceTel", sysVal);
                                break;
                            case serviceQQ:
                                model.addAttribute("serviceQQ", sysVal);
                                break;
                            case onlineTime:
                                model.addAttribute("onlineTime", sysVal);
                                break;
                            case telTime:
                                model.addAttribute("telTime", sysVal);
                                break;
                            case payAccount:
                                model.addAttribute("payAccount", sysVal);
                                break;
                            case accountName:
                                model.addAttribute("accountName", sysVal);
                                break;
                            case qrCode:
                                model.addAttribute("qrCode", sysVal);
                                break;
                            case wechatCode:
                                model.addAttribute("wechatCode", sysVal);
                                break;
                            case weAccountName:
                                model.addAttribute("weAccountName", sysVal);
                                break;
                            default:

                        }

                    });
                }
                return "sys/accountAndTelManage";
            } else {
                configs.forEach(config -> {
                    String key = config.getSysKey();
                    switch (key) {
                        case serviceTel:
                            config.setSysValue(params.get("serviceTel").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case serviceQQ:
                            config.setSysValue(params.get("serviceQQ").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case onlineTime:
                            config.setSysValue(params.get("onlineTime").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case telTime:
                            config.setSysValue(params.get("telTime").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case payAccount:
                            config.setSysValue(params.get("payAccount").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case accountName:
                            config.setSysValue(params.get("accountName").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case qrCode:
                            config.setSysValue(params.get("pictureUrl").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case wechatCode:
                            config.setSysValue(params.get("wechatPictureUrl").toString());
                            forUpdateConfigs.add(config);
                            break;
                        case weAccountName:
                            config.setSysValue(params.get("weAccountName").toString());
                            forUpdateConfigs.add(config);
                            break;
                        default:
                    }
                });
                updateCount = backConfigParamsService.updateValue(forUpdateConfigs, null);
            }
        } catch (Exception e) {
            log.error("accountAndTelManage error: {}", e);
            msg = "系统异常, 请稍后重试.";
        }
        SpringUtils.renderDwzResult(response, updateCount > 0, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
        return null;
    }


    @RequestMapping("saveOrUpdateBannerView")
    public ModelAndView saveOrUpdateView(Integer id, HttpServletRequest request) {
        HashMap<String, Object> params = getParametersO(request);
        ModelAndView mav = new ModelAndView("banner/saveOrUpdate");
        mav.addObject("params", params);
        if (id == null) {
            return mav;
        }
        Banner banner = bannerService.findById(id);
        mav.addObject("banner", banner);
        return mav;
    }

    @RequestMapping(value = "saveOrUpdateBanner", method = RequestMethod.POST)
    public void saveOrUpdate(Integer id, HttpServletRequest request, HttpServletResponse response, Model model, Banner banner, @RequestParam("picture") MultipartFile file) {
        String msg = "操作成功";
        boolean succ = true;
        if (!file.isEmpty()) {// 判断是否有上传文件
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String path = SysCacheUtils.getServletContext().getRealPath("/files") + File.separator + "images_banner" + File.separator + format.format(new Date());
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            long time = System.currentTimeMillis();
            String originalFilename = file.getOriginalFilename();
            String ext = "";
            if (StringUtils.isNotBlank(originalFilename)) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            path += File.separator + time + ext;
            File newFile = new File(path);
            try {
                file.transferTo(newFile);
                Map<String, String> keys = SysCacheUtils
                        .getConfigParams(BackConfigParams.APP_IMG_URL);
                String appurl = keys.get("APP_IMG_URL");
                String url = appurl + "files/images_banner/" + format.format(new Date()) + "/" + time + ext;
//				String url = "http://192.168.1.134:" + request.getServerPort() + "/backcashman/" + "files/images_banner/" + format.format(new Date()) + "/" + time + ext;
                banner.setUrl(url);
            } catch (Exception e) {
                log.error("saveOrUpdate error:{}", e);
                return;
            }
        }
        HashMap<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
		/*String presentWay = request.getParameter("presentWay");
		if (StringUtils.isBlank(presentWay)) {
			msg = "操作有误";
			succ =false;
			SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
			return;
		}
		if (presentWay.equals("0")) {// 立即发布
			banner.setStartTime("0");
			banner.setEndTime("0");
		}*/
        if (id == null) { // 保存
            try {
                bannerService.save(banner);
            } catch (Exception e) {
                msg = "保存失败";
                succ = false;
                log.error("saveOrUpdate error:{}", e);
            }
        } else { // 更新
            Banner old = bannerService.findById(id);
            if (old == null) {
                msg = "数据不存";
                succ = false;
            } else {
                try {
                    if (StringUtils.isNotBlank(banner.getShowType())) {
                        old.setShowType(banner.getShowType());
                    }
                    if (StringUtils.isNotBlank(banner.getChannelType())) {
                        old.setChannelType(banner.getChannelType());
                    }
                    if (StringUtils.isNotBlank(banner.getColumnType())) {
                        old.setColumnType(banner.getColumnType());
                    }
                    if (StringUtils.isNotBlank(banner.getTitle())) {
                        old.setTitle(banner.getTitle());
                    }
                    if (StringUtils.isNotBlank(banner.getReurl())) {
                        old.setReurl(banner.getReurl());
                    }
                    if (StringUtils.isNotBlank(banner.getUrl())) {
                        old.setUrl(banner.getUrl());
                    }
                    if (StringUtils.isNotBlank(banner.getSort())) {
                        old.setSort(banner.getSort());
                    }
                    if (StringUtils.isNotBlank(banner.getPresentWay())) {
                        old.setPresentWay(banner.getPresentWay());
                    }
                    if (StringUtils.isNotBlank(banner.getStartTime())) {
                        old.setStartTime(banner.getStartTime());
                    }
                    if (StringUtils.isNotBlank(banner.getEndTime())) {
                        old.setEndTime(banner.getEndTime());
                    }
                    if (StringUtils.isNotBlank(banner.getStatus())) {
                        old.setStatus(banner.getStatus());
                    }
                    bannerService.update(old);
                } catch (Exception e) {
                    msg = "更新失败";
                    succ = false;
                    log.error("saveOrUpdate error:{}", e);
                }
            }
        }
        SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENT);
    }

    @RequestMapping("deleteBanner")
    public void delete(Integer id, HttpServletResponse response) {
        String msg = "删除成功";
        boolean succ = true;
        if (id == null) {
            msg = "删除失败";
            SpringUtils.renderDwzResult(response, false, msg);
            return;
        }
        try {
            // 删除服务器上的图片和数据库中的记录
            Banner ad = bannerService.findById(id);
            deleteRecord(id, ad);
        } catch (Exception e) {
            msg = "删除失败";
            succ = false;
            log.error("delete error:{}", e);
        }
        SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
    }

    private void deleteRecord(Integer id, Banner ad) {
        if (ad != null) {
            String url = ad.getUrl();
            if (StringUtils.isNotBlank(url) && url.contains("/files")) {
                url = url.substring(url.indexOf("/files"));
                String[] strs = url.split("/");
                String path = SysCacheUtils.getServletContext().getRealPath("/");
                for (String str : strs) {
                    if (StringUtils.isNotBlank(str)) {
                        path += str + File.separator;
                    }
                }
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            }
            bannerService.delete(id);
        }
    }


    @RequestMapping("bannerUpdateCache")
    public void bannerUpdateCache(HttpServletRequest request, HttpServletResponse response, Model model) {

        boolean succ;
        Map<String, String> params = this.getParameters(request);
        model.addAttribute("params", params);

        String localhostBack = request.getServerName() + ":" + request.getServerPort();
        String msg = "刷新缓存失败";
        Set<String> failUrl = new HashSet<>();
        try {

            bannerService.deleteIndexCache();
            String urlString = PropertiesUtil.get("BANNER_URLS");
//			if(del==1){
            bannerUpdateOther(failUrl, urlString, localhostBack);
            if ((failUrl == null || failUrl.size() == 0)) {
                succ = true;
                msg = "刷新缓存成功";
            }
            succ = true;
//			}
        } catch (Exception e) {
            log.error("bannerUpdateCache error:{}", e);
            succ = false;
            msg = "刷新缓存失败";
        }

        if (failUrl != null && failUrl.size() > 0) {
            succ = false;
            msg = "刷新失败，失败的服务器列表：" + failUrl.toString();
        }

        SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId"));

    }

    public void bannerUpdateOther(Set<String> failUrl, String urlString, String currentIp) {

        if (StringUtils.isNotBlank(urlString)) {
            String method = PropertiesUtil.get("UPDATE_METHOD");
            String[] urls = urlString.split(",");
            for (String string : urls) {
                try {
                    if (string.indexOf(currentIp) > 0) {// 不刷新本机
                        continue;
                    }
                    URL url = new URL(string + method);
                    // 将url 以 open方法返回的urlConnection
                    // 连接强转为HttpURLConnection连接 (标识一个url所引用的远程对象连接)
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中

                    // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
                    connection.setDoOutput(true);

                    // 设置连接输入流为true
                    connection.setDoInput(true);

                    // 设置请求方式为post
                    connection.setRequestMethod("POST");

                    // post请求缓存设为false
                    connection.setUseCaches(false);

                    // 设置该HttpURLConnection实例是否自动执行重定向
                    connection.setInstanceFollowRedirects(true);
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
                    // application/x-javascript text/xml->xml数据
                    // application/x-javascript->json对象
                    // application/x-www-form-urlencoded->表单数据
                    connection.setRequestProperty("Content-Type", "application/x-javascript");

                    // 建立连接
                    // (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
                    connection.connect();

                    // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
                    DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
                    // String parm = "storeId="
                    // + URLEncoder.encode("32", "utf-8"); //
                    // URLEncoder.encode()方法
                    // 为字符串进行编码

                    // 将参数输出到连接
                    // dataout.writeBytes(parm);

                    // 输出完成后刷新并关闭流
                    dataout.flush();
                    dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
                    int reponseCode = connection.getResponseCode();
                    if (200 != reponseCode) {
                        failUrl.add(string);
                    }

                    // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
                    BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String result = bf.readLine();
                    if (result != null && !"".equals(result)) {
                        JSONObject json = JSONObject.fromObject(result);
                        DwzResult dwzResult = (DwzResult) JSONObject.toBean(json, DwzResult.class);
                        if (!(dwzResult != null && "200".equals(dwzResult.getStatusCode()))) {
                            failUrl.add(string);
                        }
                    } else {
                        failUrl.add(string);
                    }
                    bf.close(); // 重要且易忽略步骤 (关闭流,切记!)
                    connection.disconnect(); // 销毁连接

                } catch (Exception e) {
                    log.error("bannerUpdateOther error:{}", e);
                    failUrl.add(string);
                }
            }
        }
    }


}

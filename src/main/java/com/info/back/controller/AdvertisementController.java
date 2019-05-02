package com.info.back.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.info.back.service.IAdvertisementService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.SysCacheUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.Advertisement;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("ad/")
public class AdvertisementController extends BaseController{
	@Autowired
	private IAdvertisementService adService;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		HashMap<String, Object> params = getParametersO(request);
		String showType = request.getParameter("showType");
		String channelType = request.getParameter("channelType");
		String columnType = request.getParameter("columnType");
		String userLevel = request.getParameter("userLevel");
		String sortType = request.getParameter("sortType");
		String status = request.getParameter("status");
		if (StringUtils.equals(channelType, "全部")) {
			params.remove("channelType");
		}
		if (StringUtils.equals(showType, "全部")) {
			params.remove("showType");
		}
		if (StringUtils.equals(columnType, "全部")) {
			params.remove("columnType");
		}
		if (StringUtils.equals(userLevel, "全部")) {
			params.remove("userLevel");
		}
		if (StringUtils.equals(sortType, "无排序")) {
			params.remove("sortType");
		}
		if (StringUtils.equals(status, "全部")) {
			params.remove("status");
		}
		ModelAndView mav = new ModelAndView("ad/list");
		mav.addObject("params", params);
		PageConfig<Advertisement> pageConfig = adService.findPage(params);
		mav.addObject("pm", pageConfig);
		return mav;
	}
	
	@RequestMapping("saveOrUpdateAdView")
	public ModelAndView saveOrUpdateView(Integer id, HttpServletRequest request) {
		HashMap<String, Object> params = getParametersO(request);
		ModelAndView mav = new ModelAndView("ad/saveOrUpdate");
		mav.addObject("params", params);
		if (id == null) {
			return mav;
		}
		Advertisement ad = adService.findById(id);
		mav.addObject("ad", ad);
		return mav;
	}
	
	@RequestMapping(value = "saveOrUpdateAd", method = RequestMethod.POST)
	public void saveOrUpdate(Integer id, HttpServletRequest request, HttpServletResponse response, Model model, Advertisement ad, @RequestParam("picture") MultipartFile file) {
		String msg = "操作成功";
		boolean succ = true;
		if (!file.isEmpty()) {// 判断是否有上传文件
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String path = ((SysCacheUtils) request).getServletContext().getRealPath("/files") + File.separator + "images_ad" + File.separator + format.format(new Date());
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
				String url = appurl + "files/images_ad/" + format.format(new Date()) + "/" + time + ext;
				ad.setUrl(url);
			} catch (Exception e) {
				msg = "上传出错";
				succ = false;
				log.error("saveOrUpdate error:{}",e);
				return;
			}
		}
		HashMap<String, Object> params = getParametersO(request);
		model.addAttribute("params", params);
	/*	String presentWay = request.getParameter("presentWay");
		if (StringUtils.isBlank(presentWay)) {
			msg = "操作有误";
			succ =false;
			SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
			return;
		}
		if (presentWay.equals("0")) {// 立即发布
			ad.setStartTime("0");
			ad.setEndTime("0");
		}*/
		if (id == null) {// 保存
			try {
				adService.save(ad);
			} catch (Exception e) {
				msg = "保存失败";
				succ = false;
				log.error("error:{}",e);
			}
		} else { // 更新
			Advertisement old = adService.findById(id);
			if (old == null) {
				msg = "数据不存";
				succ = false;
			} else {
				try {
					if (StringUtils.isNotBlank(ad.getChannelType())) {
						old.setChannelType(ad.getChannelType());
					}
					if (StringUtils.isNotBlank(ad.getColumnType())) {
						old.setColumnType(ad.getColumnType());
					}
					if (StringUtils.isNotBlank(ad.getShowType())) {
						old.setShowType(ad.getShowType());
					}
					if (StringUtils.isNotBlank(ad.getTitle())) {
						old.setTitle(ad.getTitle());
					}
					if (StringUtils.isNotBlank(ad.getUrl())) {
						old.setUrl(ad.getUrl());
					}
					if (StringUtils.isNotBlank(ad.getUserLevel())) {
						old.setUserLevel(ad.getUserLevel());
					}
					if (StringUtils.isNotBlank(ad.getStartTime())) {
						old.setStartTime(ad.getStartTime());
					}
					if (StringUtils.isNotBlank(ad.getEndTime())) {
						old.setEndTime(ad.getEndTime());
					}
					if (StringUtils.isNotBlank(ad.getPresentWay())) {
						old.setPresentWay(ad.getPresentWay());
					}
					if (StringUtils.isNotBlank(ad.getReurl())) {
						old.setReurl(ad.getReurl());
					}
					if (StringUtils.isNotBlank(ad.getStatus())) {
						old.setStatus(ad.getStatus());
					}
					adService.update(old);
				} catch (Exception e) {
					msg = "更新失败";
					succ = false;
					log.error("error:{}",e);
				}
			}
		}
		SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}
	
	@RequestMapping("deleteAd")
	public void delete(Integer id, HttpServletRequest request, HttpServletResponse response) {
		String msg = "删除成功";
		boolean succ = true;	
		if (id == null) {
			msg = "删除失败";
			succ = false;
			SpringUtils.renderDwzResult(response, succ, msg);
			return;
		}
		try {
			// 删除服务器上的图片和数据库中的记录
			Advertisement ad = adService.findById(id);
			if (ad != null) {
				String url = ad.getUrl();
				if (StringUtils.isNotBlank(url) && url.contains("/files")) {
					url = url.substring(url.indexOf("/files"));
					String[] strs = url.split("/");
					String path = ((SysCacheUtils) request).getServletContext().getRealPath("/");
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
				adService.delete(id);
			}
		} catch (Exception e) {
			msg = "删除失败";
			succ = false;
			log.error("delete error:{}",e);
		}
		SpringUtils.renderDwzResult(response, succ, msg, DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}
	

}

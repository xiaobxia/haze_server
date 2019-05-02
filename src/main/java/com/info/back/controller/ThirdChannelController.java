package com.info.back.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.ThirdChannelReport;
import com.info.web.service.IThirdChannelReportService;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("thirdChannel/")
public class ThirdChannelController extends BaseController {

	@Autowired
	private IThirdChannelReportService thirdChannelReportService;
	

	@RequestMapping("getthirdChannelReportPage")
	public String getChannelReportPage(HttpServletRequest request, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
//			BackUser backUser = this.loginAdminUser(request);
			
			PageConfig<ThirdChannelReport> pageConfig = thirdChannelReportService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值

		} catch (Exception e) {
			log.error("getChannelReportPage error:{}", e);
		}
		return "thirdChannel/thirdChannelReportPage";
	}
	@RequestMapping("toAgainThirdChannelReport")
	public String toAgainChannelReport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		String url=null;
		try {
			if("toJsp".equals(String.valueOf(params.get("type")))){
				url="thirdChannel/againThirdReport";
			}else{
				if(params.get("nowTime")!=null){
					thirdChannelReportService.pushUserReport(String.valueOf(params.get("nowTime")));
				 }
				 SpringUtils.renderDwzResult(response, true,"操作成功，如要继续操作，请五分钟后再进行！", DwzResult.CALLBACK_CLOSECURRENT,params.get("parentId"));
			}
		} catch (Exception e) {
			log.error("againReport error:{}", e);
		}
		return url;
	}
	
	@RequestMapping("toAgainThirdPushReport")
	public String toAgainThirdPushReport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		String url=null;
		try {
			if("toJsp".equals(String.valueOf(params.get("type")))){
				url="thirdChannel/againThirdPushReport";
			}else{
				if(params.get("nowTime")!=null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar calendardate = Calendar.getInstance();
					Date sdfdate = sdf.parse(params.get("nowTime"));
					calendardate.setTime(sdfdate);
					calendardate.add(Calendar.DATE,1);
					Date yestDate=calendardate.getTime();
					String yestStr=sdf.format(yestDate);
					
					thirdChannelReportService.saveThirdChannelReport(yestStr);
				 }
				 SpringUtils.renderDwzResult(response, true,"操作成功，如要继续操作，请五分钟后再进行！", DwzResult.CALLBACK_CLOSECURRENT,params.get("parentId"));
			}
		} catch (Exception e) {
			log.error("againReport error:{}", e);
		}
		return url;
	}
	
	@RequestMapping("toAgainThirdUser")
	public String toAgainThirdUser(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		String url=null;
		try {
			if("toJsp".equals(String.valueOf(params.get("type")))){
				url="thirdChannel/againThirdUser";
			}else{
				if(params.get("nowTime")!=null){
					thirdChannelReportService.pushAginUserReport(String.valueOf(params.get("nowTime")));
				 }
				 SpringUtils.renderDwzResult(response, true,"操作成功，如要继续操作，请五分钟后再进行！", DwzResult.CALLBACK_CLOSECURRENT,params.get("parentId"));
			}
		} catch (Exception e) {
			log.error("againReport error:{}", e);
		}
		return url;
	}

}

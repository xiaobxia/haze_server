package com.info.back.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.back.pojo.GeTuiJson;
import com.info.back.service.IGeTuiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IContentService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.Content;
import com.info.web.pojo.PlatfromAdvise;
import com.info.web.pojo.PublicAbout;
import com.info.web.service.IPlatfromAdviseService;
import com.info.web.service.IPublicAboutService;
import com.info.web.util.PageConfig;
/**
 * 内容管理
 *
 * @author Sandy
 *
 */
@Slf4j
@Controller
@RequestMapping("content/")
public class ContentController extends  BaseController {

	@Autowired
	@Qualifier("backContentService")
	public IContentService contentService;
	@Autowired
	private IPublicAboutService publicAboutService;
	@Autowired
	private IPlatfromAdviseService adviseService;
	@Autowired
	private IGeTuiService geTuiService;
	/**
	 * 分页查询公告信息
	 */
	@RequestMapping("getContentPage")
	public String getContentPage(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);

			model.addAttribute("params", params);// 用于搜索框保留值
			PageConfig<Content> pageConfig =contentService.findBackPage(params);
			model.addAttribute("pm", pageConfig);
		} catch (Exception e) {
			log.error("getContentPage error:{}",e);
		}
		return "content/list";
	}
	@RequestMapping("toSaveContent")
	public String toSaveContent() {
		return "content/saveUpdate";
	}
	/**
	 * 保存货修改用户信息
	 * @param request req
	 * @param response res
	 * @param model model
	 * @param content content
	 */
	@RequestMapping("saveUpdate")
	public void saveUpdate(HttpServletRequest request,HttpServletResponse response, Model model,Content content){
		boolean bool = false;
		try {
			GeTuiJson json = new GeTuiJson();
			json.setChannelType(content.getChannelType());
			json.setTitle(content.getContentTitle());
			json.setSummary(content.getContentSummary());
			json.setContent(content.getContentTxt());
			json.setUrl(content.getExternalUrl());
			//1 显示通知 2 不现实通知
			json.setIsNotification("1");

			if (content.getId() == null) {
				bool = contentService.insert(content) > 0;
			} else {
				bool = contentService.update(content) > 0;
			}
//			//发送个推消息给APP
////			if (bool&&"CHANNEL_NOTICE".equals(content.getChannelType())) {
////				geTuiService.pushToAPP(json);
////			}

		} catch (Exception e) {
			log.error("save content error:{}", e);
		} finally {
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功"
					: "操作失败", DwzResult.CALLBACK_CLOSECURRENT);
		}
	}
	/**
	 * 删除公告
	 * @param request req
	 * @param response res
	 * @param model model
	 */
	@RequestMapping("deleteContent")
	public void deleteContent(HttpServletRequest request,HttpServletResponse response, Model model){
		boolean bool = false;
		Map<String, String> params= this.getParameters(request);
		try{
			if(StringUtils.isNotEmpty(params.get("id"))){
				bool=contentService.deleteDrop(Integer.parseInt(params.get("id")))>0;
			}
		}catch (Exception e) {
			log.error("deleteContent error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "删除成功" : "删除失败",
					DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId"));
		}
	}
	/**
	 * 跳转修改页面
	 * @param request req
	 * @param response res
	 * @param model model
	 */
	@RequestMapping("toUpdateContent")
	public String toUpdateContent(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> params= this.getParameters(request);
		try{
			if(StringUtils.isNotEmpty(params.get("id"))){
				Content content=contentService.findById(Integer.parseInt(params.get("id")));
				model.addAttribute("content",content);
			}
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch (Exception e) {
			log.error("toUpdateContent error:{}",e);
		}
		return "content/saveUpdate";
	}


	/**
	 * 关于我们
	 * @param request req
	 * @param response res
	 * @param model model
	 */
	@RequestMapping("toAbout")
	public String toAbout(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> params= this.getParameters(request);
		try{
			PublicAbout about=this.publicAboutService.selectPublicAbout();
			model.addAttribute("content",about);
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch (Exception e) {
			log.error("toAbout error:{}",e);
		}
		return "content/saveAbout";
	}

	/**
	 * 保存关于我们
	 * @param request req
	 * @param response res
	 */
	@RequestMapping("saveAbout")
	public void saveAbout(HttpServletRequest request,HttpServletResponse response,PublicAbout about){
		boolean bool = false;
//		Map<String, String> params= this.getParameters(request);
		try{
			int flae=0;
			PublicAbout about1=this.publicAboutService.selectPublicAbout();
			if(about1!=null){
				flae=this.publicAboutService.updatePublicAbout(about);
			}else{
				flae=this.publicAboutService.searchPublicAbout(about1);
			}
			if(flae>0){
				bool=true;
			}
		}catch (Exception e) {
			log.error("saveAbout error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功"
					: "操作失败", DwzResult.CALLBACK_CLOSECURRENT);
		}
	}

	/**
	 * 用户的意见反馈
	 */
	@RequestMapping("gotoAdviseList")
	public String gotoAdviseList(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params= this.getParametersO(request);
		try{
			PageConfig<PlatfromAdvise> page=this.adviseService.selectPlatfromAdvise(params);
			model.addAttribute("adviseList",page);
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch (Exception e) {
			log.error("gotoAdviseList error:{}",e);
		}
		return "content/adviseList";
	}

	/**
	 * 意见反馈详情
	 */
	@RequestMapping("gotoAdviseDetail")
	public String gotoAdviseDetail(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params= this.getParametersO(request);
		try{
			PlatfromAdvise advise=this.adviseService.selectPlatfromAdviseById(params);
			model.addAttribute("advise",advise);
		}catch (Exception e) {
			log.error("gotoAdviseDetail error:{}",e);
		}
		return "content/adviseDetail";
	}

	/**
	 * 编辑用户的意见反馈
	 */
	@RequestMapping("updateAdvisePage")
	public String updateAdvisePage(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params= this.getParametersO(request);
		try{
			PlatfromAdvise advise=this.adviseService.selectPlatfromAdviseById(params);
			model.addAttribute("advise",advise);
		}catch (Exception e) {
			log.error("updateAdvisePage error:{}",e);
		}
		return "content/updateAdvise";
	}

	/**
	 * 编辑保存用户的意见反馈
	 */
	@RequestMapping("updateAdvise")
	public void updateAdvise(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params= this.getParametersO(request);
		boolean bool = false;
		try{
			PlatfromAdvise advise=new PlatfromAdvise();
			advise.setId(params.get("id")+"");//ID
			advise.setAdviseConnectInfo(params.get("adviseConnectInfo")+"");//内容提要
			advise.setAdviseFeedback(params.get("adviseConnectInfo")+"");//处理内容
			advise.setAdviseStatus("1");//已处理
			advise.setFeedIp(this.getIpAddr(request));//处理的IP地址

			int count=this.adviseService.updatePlatfromAdvise(advise);//修改
			if(count>0){
				bool=true;
			}
		}catch (Exception e) {
			log.error("updateAdvise error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功" : "操作失败", DwzResult.CALLBACK_CLOSECURRENT);
		}
	}

	/**
	 * 删除--》隐藏用户的意见反馈
	 */
	@RequestMapping("deleteAdvise")
	public void deleteAdvise(HttpServletRequest request,HttpServletResponse response, Model model){
		HashMap<String, Object> params= this.getParametersO(request);
		boolean bool = false;
		try{
			PlatfromAdvise advise=new PlatfromAdvise();
			advise.setId(params.get("id")+"");//ID
			advise.setAdviseStatus("1");//已处理
			advise.setFeedIp(this.getIpAddr(request));//处理的IP地址
			advise.setHidden("1");//不显示

			int count=this.adviseService.updatePlatfromAdvise(advise);//修改
			if(count>0){
				bool=true;
			}
		}catch (Exception e) {
			log.error("deleteAdvise error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功" : "操作失败", DwzResult.CALLBACK_RELOADPAGE);
		}
	}

}

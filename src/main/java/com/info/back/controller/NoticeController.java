package com.info.back.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackNoticeService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.BackNotice;
@Slf4j
@Controller
@RequestMapping("notice/")
public class NoticeController extends BaseController {

	@Autowired
	private IBackNoticeService backNoticeService;

	/**
	 *分页查询
	 * 
	 * @param request req
	 * @param response res
	 * @param model model
	 * @return str
	 */
	@RequestMapping("findList")
	public String findList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String errorMsg = null;
		try {
			model.addAttribute("params", params);
			model.addAttribute("pm", backNoticeService.findPage(params));
		} catch (Exception e) {
			errorMsg = "服务器异常。请稍后重试！";
			log.error("findList error:{}", e);
		}
		model.addAttribute(MESSAGE, errorMsg);
		return "notice/list";
	}

	@RequestMapping("save")
	public String save(HttpServletRequest request,
			HttpServletResponse response, Model model, BackNotice backNotice) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String errorMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				params.put("url", "save");
				url = "notice/saveUpdate";
			} else {
				backNoticeService.insert(backNotice);
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}
		} catch (Exception e) {
			if (url == null) {
				String msg = e.getLocalizedMessage();
				if (msg.contains("UK_notice_code")) {
					errorMsg = "编码重复！";
				} else {
					errorMsg = "服务器异常，请稍后重试！";
				}
				SpringUtils.renderDwzResult(response, false,
						"操作失败," + errorMsg, DwzResult.CALLBACK_CLOSECURRENT,
						params.get("parentId").toString());
			}
			log.error("save error:{}", e);
		}
		model.addAttribute(MESSAGE, errorMsg);
		model.addAttribute("params", params);
		return url;
	}

	@RequestMapping("update")
	public String update(HttpServletRequest request,
			HttpServletResponse response, Model model, BackNotice backNotice) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String errorMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				backNotice = backNoticeService.findById(backNotice.getId());
				model.addAttribute("backNotice", backNotice);
				params.put("url", "update");
				url = "notice/saveUpdate";
			} else {
				backNoticeService.updateById(backNotice);
				SpringUtils.renderDwzResult(response, true, "操作成功",
						DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId")
								.toString());
			}
		} catch (Exception e) {
			if (url == null) {
				String msg = e.getLocalizedMessage();
				if (msg.contains("UK_notice_code")) {
					errorMsg = "编码重复！";
				} else {
					errorMsg = "服务器异常，请稍后重试！";
				}
				SpringUtils.renderDwzResult(response, false,
						"操作失败," + errorMsg, DwzResult.CALLBACK_CLOSECURRENT,
						params.get("parentId").toString());
			}
			log.error("update error:{}", e);
		}
		model.addAttribute(MESSAGE, errorMsg);
		model.addAttribute("params", params);
		return url;
	}

	@RequestMapping("delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, BackNotice BackNotice) {
		HashMap<String, Object> params = this.getParametersO(request);
		boolean flag = false;
		String errorMsg = null;
		try {
			backNoticeService.deleteById(BackNotice.getId());
			flag = true;
		} catch (Exception e) {
			errorMsg = "服务器异常，请稍后重试！";
			log.error("delete error:{}", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败"
				+ errorMsg, DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get(
				"parentId").toString());
	}
}

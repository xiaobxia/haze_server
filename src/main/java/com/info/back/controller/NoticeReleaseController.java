package com.info.back.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.info.back.service.INoticeReleaseService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.NoticeRelease;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("noticeRelease")
public class NoticeReleaseController extends BaseController{
	

	@Autowired
	private INoticeReleaseService noticeReleaseService;
	
	@RequestMapping(value = "listPage",method={RequestMethod.GET,RequestMethod.POST})
	public String addressList(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
        try {
            PageConfig<NoticeRelease> pageConfig = noticeReleaseService.findPage(params);
            model.addAttribute("pm", pageConfig);
            model.addAttribute("params", params);// 用于搜索框保留值
        } catch (Exception e) {
            log.error("addressList error :{}",e);
        }
        model.addAttribute("params", params);// 用于搜索框保留值
		return "notice/NoticeReleaseList";
	}
	
	@RequestMapping(value = "add",method={RequestMethod.GET,RequestMethod.POST})
	public String addAddress(){
		return "notice/AddNoticeRelease";
	}
	
	@RequestMapping("save")
	public void saveMessageCenter(HttpServletResponse response,NoticeRelease noticeRelease){
		boolean bool = true;
		try{
			if(noticeRelease.getId()==null){
				noticeReleaseService.insert(noticeRelease);
			}else{
				noticeReleaseService.update(noticeRelease);
			}
		}catch (Exception e) {
			bool = false;
			log.error("save error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功"
					: "操作失败", DwzResult.CALLBACK_CLOSECURRENT);
		}
	}

}

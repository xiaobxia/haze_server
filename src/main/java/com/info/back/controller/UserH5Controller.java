package com.info.back.controller;

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

import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.User;
import com.info.web.pojo.UserCardInfo;
import com.info.web.service.IUserH5service;
import com.info.web.service.IUserService;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("userH5/")
public class UserH5Controller extends BaseController{
	@Autowired
	public IUserService userService;
	@Autowired
	public IUserH5service userH5Service;
	/*
	 * 查询需要认证的H5注册用户
	 */
	@RequestMapping("getUserAuditPage")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			PageConfig<User> pageConfig =this.userH5Service.getUserAuditPage(params);		
			model.addAttribute("pm", pageConfig);
		} catch (Exception e) {
			log.error("getUserAuditPage error:{}", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "userH5/userAuditList";
	}
	/**
	 * 用户审核页面
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("userAuditPage")
	public String userAuditPage (HttpServletRequest request,Model model){
		Map<String, String> params =this.getParameters(request);
		if(StringUtils.isNotBlank(params.get("userId"))){
			User user= userService.searchByUserid(Integer.parseInt(params.get("userId")));
			String cardNo=user.getIdNumber();
			if(cardNo!=null&&cardNo.length()>=18){
				String yy= cardNo.substring(6,10); //出生的年份
				String mm= cardNo.substring(10,12); //出生的月份
				String dd = cardNo.substring(12,14); //出生的日期 
				String birthday = yy.concat("-").concat(mm).concat("-").concat(dd);
				model.addAttribute("birthday",birthday);
				model.addAttribute("age", SpringUtils.calculateAge(birthday,"yy-MM-dd"));

			}
			user.setEducation(User.EDUCATION_TYPE.get(user.getEducation()));
			user.setPresentPeriod(User.RESIDENCE_DATE.get(user.getPresentPeriod()));
			user.setMaritalStatus(User.MARRIAGE_STATUS.get(user.getMaritalStatus()));
			user.setFristContactRelation(User.CONTACTS_FAMILY.get(user.getFristContactRelation()));
			user.setSecondContactRelation(User.CONTACTS_OTHER.get(user.getSecondContactRelation()));
			model.addAttribute("user",user);
			UserCardInfo info=userService.findUserBankCard(Integer.parseInt(params.get("userId")));
			model.addAttribute("bankCard",info);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "userH5/userAuditPage";
	}
	/**
	 * 审核
	 * @param request req
	 * @param response res
	 */
	@RequestMapping("userAudit")
	public void userAudit (HttpServletRequest request,HttpServletResponse response){
		boolean bool=false;
		String msg="审核失败";
		try{
			Map<String,String> params=this.getParameters(request); 
			if(StringUtils.isNotBlank(params.get("id"))&&StringUtils.isNotBlank(params.get("auditType"))){
				if("1".equals(params.get("auditType"))){
					User user = userService.searchByUserid(Integer.parseInt(params.get("id")));
					User usr = new User();
					usr.setId(user.getId());
					usr.setRealnameStatus("1");
					usr.setRealnameTime(new Date());
					if(this.userH5Service.updateUserRemalStatus(usr)>0){
						bool=true;
						msg="审核成功";
					}
				}else{
					bool=true;
				}
			}else{
				msg="参数不正确";
			}
		}catch (Exception e) {
			msg="系统异常请稍后重试";
			log.error("userAudit error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, msg, DwzResult.CALLBACK_CLOSECURRENT);
		}
	}
	
}

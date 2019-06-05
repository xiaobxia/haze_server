package com.info.back.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.back.utils.Result;
import com.info.web.pojo.*;
import com.info.web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IRiskUserService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskRuleCal;
import com.info.web.controller.BaseController;
import com.info.web.util.PageConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("userManage/")
public class UserManageController extends BaseController{

	@Autowired
	public IUserService userService;
	@Autowired
	public IUserBankService userBankService;
	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IUserInfoImageService userInfoImageService;
	@Autowired
	private IUserContactsService userContactsService;
	@Autowired
	private IObtainUserShortMessageService userShortMessageService;
	@Autowired
	private IChannelInfoService channelInfoService;

	@Autowired
	private IUserBlackService userBlackService;

	@Autowired
	@Qualifier("riskUserService")
	private IRiskUserService riskUserService;
	
	/**
	 * 用户管理 --》用户列表 
	 */
	@RequestMapping("gotoUserManage")
	public String gotoUserManage(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {

			String status=request.getParameter("status");
			String userId=request.getParameter("id");
			String realname=request.getParameter("realname");
			String idNumber=request.getParameter("idNumber");
			String userPhone=request.getParameter("userPhone");
			String createTime=request.getParameter("createTime");
			String beginTime=request.getParameter("beginTime");
			String channelId = request.getParameter("channelId");
			String channelSuperCode = request.getParameter("channelSuperCode");
			String channelName = request.getParameter("channelName");
            if(StringUtils.isBlank(status)){status=null;}
			if(StringUtils.isNotBlank(channelSuperCode) && channelSuperCode.equals("-999")){
				params.put("channelSuperCode",null);
				params.put("userFrom",0);

			}
			if(StringUtils.isNotBlank(channelName) && channelName.equals("自然流量")){
				params.put("channelName",null);
				params.put("userFrom",0);
			}
			PageConfig<User> page = null;
			if(StringUtils.isBlank(userId)){userId=null;}
			if(StringUtils.isBlank(realname)){realname=null;}
			if(StringUtils.isBlank(idNumber)){idNumber=null;}
			if(StringUtils.isBlank(userPhone)){userPhone=null;}
			if(StringUtils.isBlank(createTime)){createTime=null;}
			if(StringUtils.isBlank(beginTime)){beginTime=null;}
/*
			if(!StringUtils.isBlank(userId)|| null!=userId || !StringUtils.isBlank(userPhone) || null!=userPhone || !StringUtils.isBlank(status) || null!=status || !StringUtils.isBlank(realname) || null!=realname || !StringUtils.isBlank(idNumber) || null!=idNumber|| !StringUtils.isBlank(createTime) || null!=createTime || !StringUtils.isBlank(beginTime) || null!=beginTime){
*/
			List<ChannelInfo> channel = channelInfoService.findAllUserList(params);
			page=this.userService.getUserPage(params);
			//}
			model.addAttribute("pm", page);
			if(StringUtils.isNotBlank(channelSuperCode) && channelSuperCode.equals("-999")){
				params.put("channelSuperCode","-999");
			}
			if(StringUtils.isNotBlank(channelName) && channelName.equals("自然流量")){
				params.put("channelName","自然流量");
			}
			model.addAttribute("searchParams", params);//用于搜索框保留值
			model.addAttribute("channel",channel);//渠道查询下拉框

		} catch (Exception e) {
			log.error("getUserPage error:{}", e);
		}
			return "userInfo/userManageList";//用户列表jsp

	}
	/**
	 * 用户管理 --》无续借用户列表 默认查询所有无续借用户
	 */
	@RequestMapping("noRenewal")
	public String noRenewalList(HttpServletRequest request,Model model) {
       try{
		   HashMap<String, Object> params = getParametersO(request);
		   String userId=request.getParameter("id");
		   String realname=request.getParameter("realname");
		   String userPhone=request.getParameter("userPhone");
		   String renewalCount=request.getParameter("renewalCount");
		   PageConfig<User> page = null;
		   if(StringUtils.isBlank(userId)){userId=null;}
		   if(StringUtils.isBlank(realname)){realname=null;}
		   if(StringUtils.isBlank(userPhone)){userPhone=null;}
		   if(StringUtils.isBlank(renewalCount)){renewalCount=null;}
		   page=this.userService.noRenewalList(params);
		   model.addAttribute("pm",page);
		   model.addAttribute("searchParams", params);
	   }catch (Exception e){
		   log.error("noRenewal error:{}", e);
	   }
	   return "userInfo/noRenewalList";
	}

	
	/**
	 * 用户管理 --》用户列表 --》 操作
	 */
	@RequestMapping("operation")
	public void operation(HttpServletRequest request,HttpServletResponse response) {
		boolean bool = false;
		try {
			String id=request.getParameter("id");
			if("1".equals(request.getParameter("option"))){//黑名单
				//根据用户id 查询用户信息
				User u = userService.searchByUserid(Integer.parseInt(id));
				if(u.getStatus().equals("2")){
					bool =false;
					SpringUtils.renderDwzResult(response, bool, "该用户已经是黑名单用户!", DwzResult.CALLBACK_RELOADPAGE);
				}else{
					User user=new User();
					user.setId(id);
					user.setStatus("2");//2黑名单
					int count=this.userService.updateByPrimaryKeyUser(user);
					if(count>0){
						bool=true;
					}
					SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_RELOADPAGE);
				}
				//加入黑名单库 判断用户是否存在于黑名单（手机号）存在则不存。
				UserBlack ub = userBlackService.findBlackUserByParams(null,0,u.getUserPhone());
				if(ub == null){
					UserBlack userBlack = new UserBlack();
					userBlack.setIdNumber(u.getIdNumber());
					userBlack.setUserName(u.getRealname());
					userBlack.setUserPhone(u.getUserPhone());
					userBlack.setCreateTime(new Date());
					userBlack.setUserType(0);
					userBlackService.addUserBlack(userBlack);
				}
			}else if("2".equals(request.getParameter("option"))){//注销/删除资料账户
				//查询用户状态为借款状态则提示不可以删除
				List<BorrowOrder> list = borrowOrderService.findByUserId(Integer.valueOf(id));
				if(list.size()>0){
					bool = false;
					SpringUtils.renderDwzResult(response, bool, "该用户为借款中状态,不能删除!", DwzResult.CALLBACK_RELOADPAGE);
				}else{
					User usr=this.userService.searchByUserid(Integer.parseInt(id));
					if(!StringUtils.isBlank(usr.getUserName())){
						usr.setUserName(usr.getUserName()+"*");
					}
					if(!StringUtils.isBlank(usr.getIdNumber())){
						usr.setIdNumber(usr.getIdNumber()+"*");
					}
					if(!StringUtils.isBlank(usr.getUserPhone())){
						usr.setUserPhone(usr.getUserPhone()+"*");
					}
					usr.setStatus("3");//3 删除
					int count=this.userService.updateByPrimaryKeyUser(usr);
					if(count>0){
						bool=true;
					}
					SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_RELOADPAGE);
				}

			}else if(request.getParameter("option").equals("3")){//保存修改手机号码
				User user=new User();
				user.setId(id);
				user.setUserName(request.getParameter("userPhone"));//用户名称
				user.setUserPhone(request.getParameter("userPhone"));//用户手机号码
				int count=this.userService.updateByPrimaryKeyUser(user);
				if(count>0){
					bool=true;
				}
				SpringUtils.renderDwzResult(response, bool, bool ? "操作成功!" : "操作失败!", DwzResult.CALLBACK_RELOADPAGE);
			}
		} catch (Exception e) {
			log.error("operation error:{}",e);
		}
	}
	
	/**
	 * 新旧号码更改
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("userManagePhone")
	public String userManagePhone (HttpServletRequest request,Model model){
		Map<String, String> params =this.getParameters(request);
		User user=this.userService.searchByUserid(Integer.parseInt(params.get("id")));
		model.addAttribute("id",user.getId());
		model.addAttribute("userPhone",user.getUserPhone());
		return "userInfo/userManageUpdatePhone";
	}
	
	/**
	 * 用户通讯录  
	 */
	@RequestMapping("gotoUserContacts")
	public String gotoUserContacts(HttpServletRequest request,Model model){
		HashMap<String, Object> params =this.getParametersO(request);
		PageConfig<UserContacts> contacts=this.userContactsService.selectUserContactsList(params);
		model.addAttribute("pm", contacts);
		model.addAttribute("searchParams", params);//用于搜索框保留值
		
		return "userInfo/userManagePhone";
	}
	
	/**
	 * 用户短信列表  
	 */
	@RequestMapping("gotoUserShortMsg")
	public String gotoUserShortMsg(HttpServletRequest request,Model model){
		HashMap<String, Object> params =this.getParametersO(request);
		PageConfig<ObtainUserShortMessage> contacts=this.userShortMessageService.selectUserShortMsgList(params);
		model.addAttribute("pm", contacts);
		model.addAttribute("searchParams", params);//用于搜索框保留值
		
		return "userInfo/userManageShortMsg";
	}


	/**
	 * 用户详情
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("userdetails")
	public String userdetails (HttpServletRequest request,Model model){
		String url = "userInfo/userdetails";
		Map<String, String> params =this.getParameters(request);
		if(params.get("isLess") != null && "Y".equals(params.get("isLess"))){
			url = "userInfo/userInfoDetail";
		}
		User user= userService.searchByUserid(Integer.parseInt(params.get("id")));
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

        model.addAttribute("operatorHtml", userService.selectGxbReportDataHtml(Integer.parseInt(user.getId())));//运营商

		BorrowOrder borrowOrderLastest = borrowOrderService.selectBorrowOrderUseId(Integer.parseInt(params.get("id")));
		model.addAttribute("borrow", borrowOrderLastest);

		if(null!=borrowOrderLastest){
			// 查询征信信息
			log.info("id:{}",borrowOrderLastest.getId());
			RiskCreditUser riskCreditUser = riskUserService.findRiskCreditUserByAssetsId(String.valueOf(borrowOrderLastest.getId()));
			if (null != riskCreditUser) {
				model.addAttribute("riskCreditUser", riskCreditUser);
			}

			// 规则查询
			List<RiskRuleCal> riskRuleCalList = riskUserService.findRiskRuleCalByAssetsId(String.valueOf(borrowOrderLastest.getId()));
			model.addAttribute("riskRuleCalList", riskRuleCalList);
		}

		List<UserCardInfo> info = userService.findUserbankCardList(Integer.parseInt(params.get("id")));
		model.addAttribute("bankCardList", info);

		List<UserInfoImage> userInfoImage=this.userInfoImageService.selectImageByUserId(Integer.parseInt(params.get("id")));
		model.addAttribute("InfoImage", userInfoImage);
		HashMap<String, Object> paramsM = new HashMap<>();
		paramsM.put("userId",user.getId());
		paramsM.put("statusList", new int[]{20,21,22,23,30,-20,-11});
		PageConfig<BorrowOrder> userBorrows = borrowOrderService.findPage(paramsM);
		model.addAttribute("userBorrows", userBorrows);
		model.addAttribute("params", params);
		return url;
	}


	@RequestMapping("updadteBankCard")
	public String updadteBankCard(HttpServletRequest request,Model model){
		Map<String, String> params =this.getParameters(request);
		UserCardInfo info=userService.findUserBankCard(Integer.parseInt(params.get("id")));
		model.addAttribute("bankIndo", info);
		List<Map<String,Object>> mapList=userBankService.findAllBankInfo();
		model.addAttribute("bankList", mapList);
		model.addAttribute("params", params);
		return "userInfo/updadteBankCard";
	}
	
/*	*//**
//	 * 重新绑定银行卡
//	 * @param request req
//	 * @param response res
//	 *//*
	@RequestMapping("updateBankCard")
	public void updateBankCard(HttpServletRequest request,HttpServletResponse response){
	boolean bool=false;
		JsonResult result=null;
	try{
			Map<String,String> pams=this.getParameters(request);
				result=userBankService.updateUpserBankCard(pams);
			if(result.isSuccessed()){
					bool=true;
				}
	}catch (Exception e) {
		result=new JsonResult("500","系统异常请稍后重试");
		log.error("updateBankCard error:{}",e);
		}finally{
			SpringUtils.renderDwzResult(response, bool, bool ? "操作成功": result.getMsg(), DwzResult.CALLBACK_CLOSECURRENT);
		}
	}*/

	/**
	 * 银行卡列表
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("userBankCardList")
	public String userBankCardList(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			model.addAttribute("params", params);// 用于搜索框保留值
			PageConfig<Map<String,String>> pageConfig=userBankService.findAllUserBankCardList(params);
			model.addAttribute("pm", pageConfig);
		}catch (Exception e) {
			log.error("userBankCardList error:{}", e);
		}
		return "userInfo/userBankCardList";
	}
	/**
	 * 实名认证列表
	 * @param request req
	 * @param model model
	 */
	@RequestMapping("userRealNameList")
	public String userRealNameList(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			model.addAttribute("params", params);// 用于搜索框保留值
			PageConfig<Map<String,String>> pageConfig=userService.realNmaeList(params);
			model.addAttribute("pm", pageConfig);
		}catch (Exception e) {
			log.error("userRealNameList error:{}",e);
		}
		return "userInfo/userRealNameList";
	}
	/**
	 * 用户认证列表
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("userCertificationList")
	public String userCertificationList(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			model.addAttribute("params", params);// 用于搜索框保留值
			PageConfig<Map<String,String>> pageConfig=userService.certificationList(params);
			model.addAttribute("pm", pageConfig);
		}catch (Exception e) {
			log.error("userCertificationList error:{}",e);
		}
		return "userInfo/userCertificationList";
	}
	/**
	 *用户通讯录列表
	 * @param request req
	 * @param model model
	 * @return str
	 */
	@RequestMapping("addressBookList")
	public String addressBookList(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			String userId=request.getParameter("userId");
			String userPhone=request.getParameter("userPhone");
			String contactName=request.getParameter("contactName");
			if(StringUtils.isBlank(userId)){userId=null;}
			if(StringUtils.isBlank(userPhone)){userPhone=null;}
			if(StringUtils.isBlank(contactName)){contactName=null;}
			PageConfig<UserContacts> pageConfig=null;
			if(!StringUtils.isBlank(userId)|| null!=userId || !StringUtils.isBlank(userPhone) || null!=userPhone || !StringUtils.isBlank(contactName) || null!=contactName){
				pageConfig=userContactsService.selectUserContactsList(params);				
			}
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch (Exception e) {
			log.error("addressBookList error:{}",e);
		}
		return "userInfo/addressBookList";
	}

    /**
     * 批量导入黑/白名单用户
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "batchimport", method = RequestMethod.POST)
	@ResponseBody
    public Result batchimport(@RequestParam(value="filename") MultipartFile file,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		HashMap<String, Object> params = getParametersO(request);
		String userType=request.getParameter("userType");
        //判断文件是否为空
        if(file==null) return null;
        //获取文件名
        String name=file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) return null;
        //批量导入。参数：文件名，文件。
        boolean b = userBlackService.batchImport(name,file,userType);
        if(b){
           return Result.success();
        }else{
          return Result.error("批量导入失败");
        }
    }

    /**
     * 黑名单用户列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("userBlackList")
    public String userBlackList(HttpServletRequest request,Model model){
        try{
            HashMap<String, Object> params = getParametersO(request);
            model.addAttribute("params", params);// 用于搜索框保留值
			params.put("userType","0");
            PageConfig<UserBlack> pageConfig=userBlackService.getUserPage(params);
            model.addAttribute("pm", pageConfig);
        }catch (Exception e) {
            log.error("userBankCardList error:{}", e);
        }
        return "userInfo/userBlackList";
    }

	/***
	 * 白名单列表
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("userWhiteList")
    public String userWhiteList(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			model.addAttribute("params", params);// 用于搜索框保留值
			params.put("userType","1");
			PageConfig<UserBlack> pageConfig=userBlackService.getUserPage(params);
			model.addAttribute("pm", pageConfig);
		}catch (Exception e) {
			log.error("userWhiteList error:{}", e);
		}
		return "userInfo/userWhiteList";
	}
	/**
	 * 删除黑名单/白名单用户
	 * @param id
	 * @return
	 */
	@RequestMapping("updateUserBlackStatus")
	@ResponseBody
    public Result updateUserBlackStatus(Integer id){
    	try{
    		userBlackService.updateUserBlackStatus(id);
			return Result.success();
		}catch(Exception e){
			log.error("删除失败"+e);
			return Result.error("删除失败");
		}
	}
	/**
	 * 导入黑名单/白名单
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("importBlackUser")
	public String importBlackUser(HttpServletRequest request,Model model){
		try{
			HashMap<String, Object> params = getParametersO(request);
			String userType=request.getParameter("userType");
			params.put("userType",userType);
			model.addAttribute("params", params);// 用于搜索框保留值
		}catch (Exception e) {
			log.error("importBlackUser error:{}", e);
		}
		return "userInfo/importBlackUser";
	}
}

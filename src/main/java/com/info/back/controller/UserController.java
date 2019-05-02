package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.web.pojo.BackUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackRoleService;
import com.info.back.service.IBackUserService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.TreeUtil;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.pojo.BackLog;
import com.info.web.pojo.BackTree;
import com.info.web.pojo.BackUser;
import com.info.web.util.PageConfig;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;

@Slf4j
@Controller
@RequestMapping("user/")
public class UserController extends BaseController {

	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private IBackRoleService backRoleService;

	@RequestMapping("getUserPage")
	public String getUserPage(HttpServletRequest request, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			 
			PageConfig<BackUser> pageConfig = backUserService.findPage(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值

		} catch (Exception e) {
			log.error("getUserPage error:{}", e);
		}
		return "user/userList";
	}
	@RequestMapping("configOrderLimit")
	public String configOrderLimit(HttpServletRequest request, HttpServletResponse response, Model model, BackUser backUser){
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String errorMsg = null;
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				if (params.containsKey("id")) {
					// 更新的页面跳转
					if("1".equals(params.get("customer"))){
						params.put("status",1);
					}
					backUser = backUserService.findOneUser(params);
					model.addAttribute("backUser", backUser);
				}
				url = "user/configOrderLimit";
			} else {
				// 更新或者添加操作
				if (backUser.getId() != null) {
					if(params.get("status") != null){
						backUserService.updateAllById(backUser);
					}else{
						backUserService.updateById(backUser);
					}
				} else {
					backUser.setAddIp(this.getIpAddr(request));
					backUser.setUserPassword(MD5coding.MD5(AESUtil.encrypt(backUser.getUserPassword(), "")));
					backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "saveUpdateUser", backUser.toString()));
					backUserService.insert(backUser);
					if("1".equals(params.get("customer"))){
						BackUserRole backUserRole = new BackUserRole();
						backUserRole.setRoleId(Constant.ROLE_CUSTOMER_SERVER);
						backUserRole.setUserId(backUser.getId());
						backUserService.inserUserRole(backUserRole);
					}
				}
				SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
			}
		} catch (Exception e) {
			log.error("configOrderLimit:{}", e);
			errorMsg = "服务器异常，请稍后重试！";
			SpringUtils
					.renderDwzResult(response, false, "操作失败,原因：" + errorMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
		}
		model.addAttribute(MESSAGE, errorMsg);
		model.addAttribute("params", params);
		return url;


	}

	/**
	 * xiangxiaoyan 跳转到添加页面
	 * 
	 * @param request req
	 * @param response res
	 * @param model model
	 * @return str
	 * @param model mo
	 * @return r
	 */
    @RequestMapping("saveUpdateUser")
    public String saveUpdateUser(HttpServletRequest request, HttpServletResponse response, Model model, BackUser backUser) {
        HashMap<String, Object> params = this.getParametersO(request);
        String url = null;
        String erroMsg = null;
        try {
            if ("toJsp".equals(String.valueOf(params.get("type")))) {
                if (params.containsKey("id")) {
                    // 更新的页面跳转
                    if("1".equals(params.get("customer"))){
                        params.put("status",1);
                    }
                    backUser = backUserService.findOneUser(params);
                    model.addAttribute("backUser", backUser);
                }
                url = "user/saveUpdateUser";
            } else {
				/*
				 * 用户地址 对应渠道推广code 不能包含空格
				 */
				backUser.setUserAddress(backUser.getUserAddress()==null?"":backUser.getUserAddress().trim());

				// 更新或者添加操作
                if (backUser.getId() != null) {
                    if(params.get("status") != null){

                        backUserService.updateAllById(backUser);
                    }else{
                        backUserService.updateById(backUser);
                    }

                } else {
                    backUser.setAddIp(this.getIpAddr(request));

                    backUser.setUserPassword(MD5coding.MD5(AESUtil.encrypt(backUser.getUserPassword(), "")));
                    backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "saveUpdateUser", backUser.toString()));
                    backUserService.insert(backUser);
                    if("1".equals(params.get("customer"))){
                        BackUserRole backUserRole = new BackUserRole();
                        backUserRole.setRoleId(Constant.ROLE_CUSTOMER_SERVER);
                        backUserRole.setUserId(backUser.getId());
                        backUserService.inserUserRole(backUserRole);
                    }
                }
                SpringUtils.renderDwzResult(response, true, "操作成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            }
        } catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			if (e.getLocalizedMessage().contains("UK_user_account")) {
				erroMsg = "用户名重复！";
			}
			if (e.getLocalizedMessage().contains("uk_user_mobile")) {
				erroMsg = "手机号码重复！";
			}
			SpringUtils
					.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
            log.error("saveUpdateUser error:{}", e);
        }
        model.addAttribute(MESSAGE, erroMsg);
        model.addAttribute("params", params);
        return url;
    }

	/**
	 * 删除角色
	 * 
	 * @param request req
	 * @param response res
	 */
	@RequestMapping("deleteUser")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		boolean flag = false;
		try {
			Integer id = Integer.parseInt(params.get("id").toString());
			backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "deleteUser", id+""));
			backUserService.deleteById(id);
			flag = true;
		} catch (Exception e) {
			log.error("deleteRole error:{}", e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功" : "删除用户失败", DwzResult.CALLBACK_CLOSECURRENTDIALOG, params.get("parentId")
				.toString());
	}

	/**
	 * 用戶授权
	 * 
	 * @param request req
	 * @param response res
	 * @param model model
	 * @return r
	 */

	@RequestMapping("saveUserRole")
	public String saveUserRole(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
//		String erroMsg = null;
		try {
			BackUser backUser = this.loginAdminUser(request);
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				params.put("userId", backUser.getId());
				List<BackTree> userAll = backRoleService.findRoleTree(params);
				Integer id = Integer.valueOf(params.get("id").toString());
				params.put("userId", id);
				List<BackTree> haveList = backRoleService.findRoleTree(params);
				String outGroupHtml = TreeUtil.getTreeStringWidthCheckBoxOne(userAll, haveList, "roleIds");
				model.addAttribute("outGroupHtml", outGroupHtml);
				params.put("url", "user/saveUserRole");
				params.put("ajaxType", "dialogAjaxDone");
				url = "showRolesRight";
			} else {
				params.put("roleIds", request.getParameterValues("roleIds"));
				backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "saveUserRole",params+""));
				backUserService.addUserRole(params);
				SpringUtils.renderDwzResult(response, true, "用户授权成功", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
			}
		} catch (Exception e) {

			SpringUtils.renderDwzResult(response, false, "操作失败", DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());

			log.error("saveUserRole error:{}", e);
		}
		model.addAttribute("params", params);
//		model.addAttribute(MESSAGE, erroMsg);
		return url;
	}

	/**
	 * 修改密码
	 * 
	 * @param request req
	 * @param response res
	 * @return str
	 */
	@RequestMapping("updateUserPassword")
	public String updateUserPassword(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		String target = "";
		boolean bool = false;
		String errorMsg = "";
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				target = "user/updateUserPwd";
			} else {
				String oldPassword = MD5coding.MD5(AESUtil.encrypt(params.get("oldPassword").toString(), ""));
				BackUser backUser = this.loginAdminUser(request);
				if (!backUser.getUserPassword().equals(oldPassword)) {
					errorMsg = "原密码错误！";
				} else {
					String newPassword = MD5coding.MD5(AESUtil.encrypt(params.get("newPassword").toString(), ""));
					BackUser backUser2 = new BackUser();
					backUser2.setId(backUser.getId());
					backUser2.setUserPassword(newPassword);
					backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "updateUserPassword",backUser2.toString()));
					backUserService.updatePwdById(backUser2);
					bool = true;
				}
				SpringUtils.renderDwzResult(response, bool, bool ? "操作成功" : "操作失败，" + errorMsg, DwzResult.CALLBACK_CLOSECURRENT);
				target = null;
			}
		} catch (Exception e) {
			log.error("updateUserPassWord error:{} ", e);
		}
		return target;
	}
}

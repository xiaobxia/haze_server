package com.info.back.controller;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.info.back.service.BackRoleService;
import com.info.back.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.info.back.service.IBackModuleService;
import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.listener.IndexInit;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackUser;

/**
 *
 * 类描述：首页类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午02:57:46 <br>
 **
 */
@Slf4j
@Controller
public class IndexController extends BaseController {
	@Autowired
	private IBackModuleService backModuleService;

	@Autowired
	private BackRoleService backRoleService;

	@RequestMapping("/")
	public String index(HttpServletRequest request,Model model) {
		try {
			HashMap<String, Object> params = new HashMap<>();
			BackUser backUser = loginAdminUser(request);//判断用户是否登录
			model.addAttribute("APP_NAME", PropertiesUtil.get("APP_NAME"));
			if (backUser == null) {
				return "login";//返回登录页面
			}
			model.addAttribute(Constant.BACK_USER, backUser);
			params.put("userId", backUser.getId());
			params.put("parentId", "0");
			Boolean showFlag = backRoleService.showIndex(backUser.getId());
			model.addAttribute("isShow" ,showFlag);
			model.addAttribute("isCustomerManager" ,!showFlag);
			// 获得顶级菜单
			List<BackModule> menuModuleList = backModuleService
					.findAllModule(params);
			if (menuModuleList != null && menuModuleList.size() > 0) {
				int moduleId = menuModuleList.get(0).getId();
				params.put("parentId", moduleId);
				// 获得某个顶级菜单的子菜单（二级菜单）
				List<BackModule> subMenu = backModuleService
						.findAllModule(params);
				for (BackModule backModule : subMenu) {
					params.put("parentId", backModule.getId());
					// 获得某个二级菜单的子菜单（三级菜单）
					List<BackModule> thirdMenu = backModuleService
							.findAllModule(params);
					backModule.setModuleList(thirdMenu);//将三级菜单放入二级菜单中
				}
				model.addAttribute("subMenu", subMenu);//二级菜单
			}
			model.addAttribute("menuModuleList", menuModuleList);//顶级菜单
		} catch (Exception e) {
			model.addAttribute("msg","服务器异常");
			log.error("back index error:{}", e);
			return "error";
		}
		return "index";
	}

	@RequestMapping("indexMain")
	public String indexMain() {
		return "indexMain";
	}
	@RequestMapping("subMenu")
	public String subMenu(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			params.put("parentId", params.get("myId"));
			// 获得某个顶级菜单的子菜单（二级菜单）
			List<BackModule> subMenu = backModuleService.findAllModule(params);
			for (BackModule backModule : subMenu) {
				params.put("parentId", backModule.getId());
				// 获得某个二级菜单的子菜单（三级菜单）
				List<BackModule> thirdMenu = backModuleService
						.findAllModule(params);
				backModule.setModuleList(thirdMenu);
			}
			model.addAttribute("subMenu", subMenu);
		} catch (Exception e) {
			log.error("subMenu:{}", e);
			model.addAttribute("msg","服务器异常");
			return "error";
		}
		return "subMenu";
	}

	@RequestMapping("rightSubList")
	public String rightSubList(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = new HashMap<>();
		try {
			params.put("parentId", request.getParameter("parentId"));
			params.put("myId", request.getParameter("myId"));
			BackUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());
			// 获得当前登录用户的myId下的子权限
			List<BackModule> rightSubList = backModuleService
					.findAllModule(params);
			model.addAttribute("rightSubList", rightSubList);
		} catch (Exception e) {
			log.error("rightSubList error:{}", e);
			model.addAttribute("msg", "服务器异常");
			return "error";
		}
		return "rightSubList";
	}

	/**
	 * 更新系统缓存
	 *
	 * @param request req
	 * @param response res
	 */
	@RequestMapping("updateCache")
	public void updateCache(HttpServletRequest request,
							HttpServletResponse response) {
		boolean flag = true;
		DwzResult dwzResult=new DwzResult("200","刷新成功");
		try {
			BackUser backUser = loginAdminUser(request);
			if (backUser != null) {
				new IndexInit().updateCache();
//				String localhostBack = request.getServerName()+":"+request.getServerPort();
//				dwzResult=backModuleService.updateCacheOthers(localhostBack);//刷新其它后台
//				  dwzResult= updateCacheOthers(localhostBack);//刷新其它后台
//				if(dwzResult.getStatusCode().equals("200")){
//					flag = true;
//				}
			}
		} catch (Exception e) {
			log.error("updateCache error:{}", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, dwzResult.getMessage());

	}

}

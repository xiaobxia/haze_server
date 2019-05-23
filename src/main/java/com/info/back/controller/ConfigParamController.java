package com.info.back.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.back.service.IProductService;
import com.info.web.pojo.*;
import com.info.web.util.PageConfig;
import com.sun.net.httpserver.Authenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackUserService;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.listener.IndexInit;
import com.info.web.service.IBackConfigParamsService;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("cofigParams/")
public class ConfigParamController extends BaseController {

	@Autowired
	IBackConfigParamsService backConfigParamsService;

	@Autowired
	private IBackUserService backUserService;

	@Autowired
	private IProductService iProductService;

	/**
	 * 进入系统配置编辑页面<br>
	 * 传入不同的type可以配置多个类型的页面
	 */
	@RequestMapping("enterCfg/{sysType}")
	public String enterCfgEdit(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("sysType") String sysType,
			BackConfigParamsVo vo) {
		String url = null;
		String erroMsg = null;
		HashMap<String, Object> params = getParametersO(request);
		params.put("sysType", sysType);
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				List<BackConfigParams> list = backConfigParamsService.findParams(params);
				model.addAttribute("cfgList", list);
				url = "sys/configParam";
			} else {
				backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "enterCfgEdit", "sysType=" + sysType + ",vo" + vo));
				backConfigParamsService.updateValue(vo.getList(), sysType);
				new IndexInit().updateCache();
				SpringUtils.renderDwzResult(response, true, "操作成功");
			}
		} catch (Exception e) {
			log.error("enterCfgEdit error:{} params=:{}",params, e);

			if (url == null) {
				SpringUtils.renderDwzResult(response, false, "操作失败");
			} else {
				erroMsg = "服务器异常，请稍后重试！";
			}
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}

	/**
	 * 指向产品页面
	 * @return
	 */
	@RequestMapping("goProductList")
	public String getProductList(){
      return "sys/productList";
	}

	/**
	 * 指向续期页面
	 * @return
	 */
	@RequestMapping("goextendList")
	public String getextendList(){
		return "sys/extendList";
	}

	/**
	 * 指向提额页面
	 * @return
	 */
	@RequestMapping("goLimitList")
	public String getLimitList(){
		return "sys/limitList";
	}

	/**
	 * 产品列表
	 * @param model
	 * @return
	 */
    @RequestMapping(value="getProductList")
	@ResponseBody
	public Model getProductList(Model  model,HttpServletRequest request){
		HashMap<String, Object> params = getParametersO(request);
		PageConfig<ProductDetail>  pageConfig = iProductService.getProductList(params);
		model.addAttribute("pm",pageConfig);
		return model;
	}

	/**
	 * 查看产品详情
	 * @param model
	 * @param id
	 * @return
	 */
    @RequestMapping(value="getProductDetail")//
	@ResponseBody
	public Model getProductDetail(Model model,Integer id,HashMap<String,Object> params){
       ProductDetail productDetail = iProductService.getProductDetail(id);
       List<BackLimit> limitList= iProductService.findLimitList(params);
       List<BackExtend> extendList = iProductService.findExtendList(params);
       model.addAttribute("limitList",limitList);
       model.addAttribute("extendList",extendList);
       model.addAttribute("productDetail",productDetail);
       return model;
	}

	/**
	 * 添加产品
	 * @param borrowProductConfig
	 * @param model
	 * @return
	 */
	@RequestMapping(value="addProduct")
	@ResponseBody
	public Model addProduct(BorrowProductConfig borrowProductConfig,Model model){
		try{
			iProductService.addProduct(borrowProductConfig);
            model.addAttribute("result", "success");
		}catch (Exception e){
			log.error("添加失败"+e);
			model.addAttribute("result","error");
		}
		return model;
	}
	/**
	 * 修改产品
	 * @param borrowProductConfig
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updateProduct")
	@ResponseBody
	public Model updateProduct(BorrowProductConfig borrowProductConfig,Model model){
		try{
			iProductService.updateProduct(borrowProductConfig);
			model.addAttribute("result","success");
		}catch(Exception e){
			log.error("修改失败"+e);
			model.addAttribute("result","error");
		}
		return model;
	}
	/**
	 * 续期列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getExtendList")
	@ResponseBody
	public Model getExtendList(Model  model){

		return model;
	}
	/**
	 * 提额列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getLimitList")
	@ResponseBody
	public Model getLimitList(Model  model){

		return model;
	}
}

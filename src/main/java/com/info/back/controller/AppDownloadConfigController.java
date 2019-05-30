package com.info.back.controller;

import com.info.back.pojo.AppDownloadConfig;
import com.info.back.service.IAppDownloadConfigService;
import com.info.back.utils.Result;
import com.info.web.controller.BaseController;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
* APP分发配置Controller
*
* @author fully
* @version 1.0.0
* @date 2019-05-29 18:06:36
*/

@Slf4j
@Controller
@RequestMapping("appConfig/")
public class AppDownloadConfigController extends BaseController {

   @Autowired
   private IAppDownloadConfigService appDownloadConfigService;


   @RequestMapping("/list")
   public ModelAndView addUserLimit(HttpServletRequest request) {

      HashMap<String, Object> params = getParametersO(request);
      PageConfig<AppDownloadConfig> page = appDownloadConfigService.findPage(params);

      ModelAndView mav = new ModelAndView("appConfig/list");
      mav.addObject("pm", page);
      mav.addObject("params", params);

      return mav;
   }

   /**
    * 进入添加/修改产品页面
    */
   @RequestMapping("toAddOrUpdateAppConfig")
   public String toAddOrUpdateProduct(Integer id, Model model){
      if(id != null){
         model.addAttribute("appConfigDetail",appDownloadConfigService.getById(id));
         model.addAttribute("id",id);
         return "appConfig/addOrUpdateAppConfig";
      }
      return "appConfig/addOrUpdateAppConfig";
   }

   /**
    * 添加产品
    * @param appDownloadConfig
    * @return
    */
   @RequestMapping(value="addConfig")
   @ResponseBody
   public Result addConfig(AppDownloadConfig appDownloadConfig){
      try{
         appDownloadConfigService.insertRecord(appDownloadConfig);
         return Result.success();
      }catch (Exception e){
         log.error("添加失败"+e);
         return Result.error("添加失败");
      }
   }

   /**
    * 修改产品
    * @param appDownloadConfig
    * @return
    */
   @RequestMapping(value="editConfig")
   @ResponseBody
   public Result editConfig(AppDownloadConfig appDownloadConfig){
      try{
         appDownloadConfigService.updateRecord(appDownloadConfig);
         return Result.success();
      }catch (Exception e){
         log.error("修改失败"+e);
         return Result.error("修改失败");
      }
   }

   /**
    * 开关
    * @param id
    * @return
    */
   @RequestMapping(value="openOrCloseConfig")
   @ResponseBody
   public Result openOrCloseConfig(Integer id){
      try {
         appDownloadConfigService.openOrCloseConfig(id);
         return Result.success();
      } catch (Exception e) {
         log.error("设置为默认"+e);
         return Result.error("设置为默认出错");
      }
   }

}

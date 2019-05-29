package com.info.back.controller;

import com.info.back.service.IProductService;
import com.info.back.service.TaskJob;
import com.info.back.utils.*;
import com.info.web.controller.BaseController;
import com.info.web.pojo.*;
import com.info.web.service.*;
import com.info.web.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("product/")
public class ProductController extends BaseController {
    @Autowired
    ILoanReportService loanReportService;
    @Autowired
    IThirdChannelReportService thirdChannelReportService;
    @Autowired
    TaskJob taskjob;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    ILoanMoneyReportService loanmoneyReportService;
    @Autowired
    private IProductService iProductService;

    /**
     * 查看产品详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="getProductDetail")
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
     * 指向产品页面
     * @return
     */
    @RequestMapping("goProductList")
    public String getProductList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        List<ProductDetail> list = iProductService.moneyList(null);
        model.addAttribute("productMoneyList",list);
        PageConfig<ProductDetail>  pageConfig = iProductService.getProductList(params);
        model.addAttribute("pm",pageConfig);
        model.addAttribute("params",params);
        return "sys/productList";
    }
    /**
     * 添加产品
     * @param borrowProductConfig
     * @return
     */
    @RequestMapping(value="addProduct")
    @ResponseBody
    public Result addProduct(BorrowProductConfig borrowProductConfig){
        try{
            iProductService.addProduct(borrowProductConfig);
            return Result.success();
        }catch (Exception e){
            log.error("添加失败"+e);
            return Result.error("添加失败");
        }
    }
    /**
     * 修改产品
     * @param borrowProductConfig
     * @param
     * @return
     */
    @RequestMapping(value="updateProduct")
    @ResponseBody
    public Result updateProduct(BorrowProductConfig borrowProductConfig){
        try{
            iProductService.updateProduct(borrowProductConfig);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }

    /**
     * 设置为默认产品
     * @param id
     * @return
     */
    @RequestMapping(value="openOrCloseProduct")
    @ResponseBody
    public Result openOrCloseProduct(Integer id){
        try {
            iProductService.openOrCloseProduct(id);
            return Result.success();
        } catch (Exception e) {
            log.error("设置为默认产品"+e);
            return Result.error("设置为默认产品出错");
        }
    }

    /**
     * 添加续期
     * @param backExtend
     * @return
     */
    @RequestMapping(value="addExtend")
    @ResponseBody
    public Result addExtend(BackExtend backExtend){
        try{
            iProductService.addExtend(backExtend);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 修改续期
     * @param backExtend
     * @return
     */
    @RequestMapping(value="updateExtend")
    @ResponseBody
    public Result updateExtend(BackExtend backExtend){
        try{
            iProductService.updateExtend(backExtend);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 添加提额
     * @param backLimit
     * @return
     */
    @RequestMapping(value="addBackLimit")
    @ResponseBody
    public Result addBackLimit(BackLimit backLimit){
        try{
            iProductService.addLimit(backLimit);
            return Result.success();
        }catch (Exception e){
            log.error("添加失败"+e);
            return Result.error("添加失败");
        }
    }
    /**
     * 修改提额
     * @param backLimit
     * @return
     */
    @RequestMapping(value="updateBackLimit")
    @ResponseBody
    public Result updateBackLimit(BackLimit backLimit){
        try{
            iProductService.updateLimit(backLimit);
            return Result.success();
        }catch(Exception e){
            log.error("修改失败"+e);
            return Result.error("修改失败");
        }
    }
    /**
     * 指向续期页面
     * @return
     */
    @RequestMapping("goextendList")
    public String getextendList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackExtend> pageConfig = iProductService.getExtendList(params);
        model.addAttribute("pm",pageConfig);
        List<BackExtend> list = iProductService.findExtendList(null);
        model.addAttribute("extendList",list);
        model.addAttribute("params",params);
        return "sys/extendList";
    }

    /**
     * 指向提额页面
     * @return
     */
    @RequestMapping("goLimitList")
    public String getLimitList(Model model,HttpServletRequest request){
        HashMap<String, Object> params = getParametersO(request);
        PageConfig<BackLimit> pageConfig = iProductService.getLimitList(params);
        model.addAttribute("pm",pageConfig);
        List<BackLimit> list = iProductService.findLimitList(null);
        model.addAttribute("limitList",list);
        model.addAttribute("params",params);
        return "sys/limitList";
    }
    /**
     * 进入添加/修改产品页面
     */
    @RequestMapping("toAddOrUpdateProduct")
    public String toAddOrUpdateProduct(Integer id,Model model,HashMap<String,Object> params){
        List<BackLimit> limitList= iProductService.findLimitList(params);
        List<BackExtend> extendList = iProductService.findExtendList(params);
        model.addAttribute("limitList",limitList);
        model.addAttribute("extendList",extendList);
        if(id != null){
            ProductDetail productDetail = iProductService.getProductDetail(id);
            model.addAttribute("productDetail",productDetail);
            model.addAttribute("id",id);
            return "sys/addOrUpdateProduct";
        }
        return "sys/addOrUpdateProduct";
    }

    /**
     * 进入产品详情页面
     * @return
     */
    /*@RequestMapping("toProductDetail")
    public String toProductDetail(Model model,Integer id,HashMap<String,Object> params){
        ProductDetail productDetail = iProductService.getProductDetail(id);
        model.addAttribute("productDetail",productDetail);
        return "sys/productDetail";
    }*/

    /**
     * 进入添加或修改续期页面
     * @param
     * @return
     */
    @RequestMapping("toAddOrUpdateExtend")
    public String toAddOrUpdateExtend(Integer id,Model model, HashMap<String,Object> params){
        if(id != null ){
            BackExtend backExtend = iProductService.findExtend(id);
            model.addAttribute("backExtend",backExtend);
            model.addAttribute("id",id);
            return "sys/addOrUpdateExtend";
        }
        return "sys/addOrUpdateExtend";
    }

    /**
     * 进入添加或修改提额页面
     * @param id
     * @return
     */
    @RequestMapping("toAddOrUpdateLimit")
    public String toAddOrUpdateLimit(Integer id,Model model){
        List<ProductDetail> list = iProductService.moneyList(null);
        if(id != null){
            BackLimit backLimit = iProductService.findLimit(id);
            //查询产品详情
            ProductDetail productDetail = iProductService.getProductDetail(backLimit.getLimitProductId());
            model.addAttribute("backLimit",backLimit);
            model.addAttribute("productDetail",productDetail);
            model.addAttribute("list",list);
            model.addAttribute("id",id);
            return "sys/addOrUpdateLimit";
        }
        model.addAttribute("list",list);
        return "sys/addOrUpdateLimit";
    }

    @RequestMapping("toExtendDetail")
    public String toExtendDetail(Integer id,Model model){
        BackExtend backExtend = iProductService.findExtend(id);
        model.addAttribute("backExtend",backExtend);
        return "sys/extendDetail";
    }

    @RequestMapping("toLimitDetail")
    public String toLimitDetail(Integer id,Model model){
        List<ProductDetail> list = iProductService.moneyList(null);
        BackLimit backLimit = iProductService.findLimit(id);
        //查询产品详情
        ProductDetail productDetail = iProductService.getProductDetail(backLimit.getLimitProductId());
        model.addAttribute("backLimit",backLimit);
        model.addAttribute("productDetail",productDetail);
        return "sys/limitDetail";
    }
}

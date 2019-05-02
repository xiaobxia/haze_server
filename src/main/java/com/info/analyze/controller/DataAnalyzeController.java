package com.info.analyze.controller;

import com.google.gson.Gson;
import com.info.analyze.pojo.DataStatistics;
import com.info.analyze.service.IDataStatisticsService;
import com.info.web.controller.BaseController;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("dataAnalyze/")
public class DataAnalyzeController extends BaseController{

    @Autowired
    private IDataStatisticsService dataStatisticsService;

    /**
     * 手动触发
     * http://47.98.200.113:8081/back/dataAnalyze/analyzeDataInsert?flag=1
     * @param flag flag
     */
    @RequestMapping("analyzeDataInsert")
    public void analyzeDataInsert(String flag){
        if (flag != null && flag.equals("1")) {
            log.info("start analyzeDateInsert ");
            //删除以前的数据
            dataStatisticsService.deleDataStatisticsAll();
            dataStatisticsService.insertDataStatisticsByFlag(true);
            log.info("end analyzeDateInsert ");
        }
    }

    /**
     * 访问地址：http://localhost:8080/back/dataAnalyze/getLoanRate?startMonth=2017-10&endMonth=2018-04
     * @param response res
     * @param startMonth startMonth
     * @param endMonth endMonth
     */
    @RequestMapping("getLoanRate")
    public void getLoanAgainRate(HttpServletResponse response, String startMonth, String endMonth) {
        log.info("getLoanAgainRate");
        Map<String,Object> map = new HashMap<>();
        Gson gson = new Gson();
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            if (startMonth == null && endMonth == null) {
                map.put("error","startmonth or endMonth is empty");
            } else {
                Map<String,Object> rateMap = dataStatisticsService.getLoanAgainRate(startMonth,endMonth);
                map.putAll(rateMap);
                map.put("time",startMonth+"--"+endMonth);
            }
            printWriter.write(gson.toJson(map));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("getLoanAgainRate error:{}",e);
        }


    }

    @RequestMapping("index")
    public String dataRiskStaticsIndex(HttpServletRequest request, Model model){
        HashMap<String, Object> params = getParametersO(request);
        if (!params.containsKey("flag")) {
            params.put("flag","0"); //默认显示tian
        }
        try{
            //分页查询
            PageConfig<DataStatistics> pageConfig = dataStatisticsService.findDataStatisticsPage(params);
            model.addAttribute("pm",pageConfig);
            model.addAttribute("params",params);
            model.addAttribute("dateCycleMap", DataStatistics.dataCycleMap);
            model.addAttribute("customerTypeMap",DataStatistics.customerTypeMap);
            return "analyze/index";
        }catch (Exception e){
            log.error("dataRiskStaticsIndex error:{}",e);
            model.addAttribute("msg","系统异常");
            return "error";
        }
    }


}

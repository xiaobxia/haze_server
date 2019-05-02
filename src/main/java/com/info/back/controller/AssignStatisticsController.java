package com.info.back.controller;

import com.info.back.service.IBackUserService;
import com.info.back.utils.ExcelUtil;
import com.info.constant.Constant;
import com.info.web.controller.AssignController;
import com.info.web.service.IRepaymentDetailService;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 〈一句话功能简述〉<br> 
 * 〈订单派送模块统计〉
 *
 * @author Liubing
 * @create 2018/3/14
 * @since 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("assign/")
public class AssignStatisticsController extends AssignController {

    @Resource
    private IRepaymentDetailService repaymentDetailService;
    @Resource
    private IBackUserService backUserService;

    /**
     * 统计页面详情按钮
     * */
    @RequestMapping("statisticsDetail")
    public String statisticsDetail(@ModelAttribute("params") HashMap<String,Object> params,Model model){
        try{
            String createDate = params.get("createDate") == null?"":params.get("createDate").toString();
            Date day = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String date = formatter.format(day);
            if(date.compareTo(createDate) == 0){
                params.put("isToday",1);
                if(sdf.format(new Date()).compareTo("18:00:00") >= 0){
                    params.put("artificialSend",1);
                }
            }
            if(params.get("className")!=null){
                if("系统派单".equals(String.valueOf(params.get("className")))){
                    params.put("classId",0);
                }else{
                    params.put("classId",1);
                }

            }
            List<Map<String,Object>> assign = repaymentDetailService.selectAssignStatisticsByCondition(params);
            model.addAttribute("assign",assign);
            model.addAttribute("params",params);
        }catch (Exception e){
            log.error("statisticsDetail error:{}",e);
            model.addAttribute("msg","服务器异常");
            return "error";
        }
        return "custom/statisticsDetail";
    }

    /**
     * 查询推送统计数据
     * */
    @RequestMapping("assignStatistics")
    public String assignStatistics(@ModelAttribute("params") HashMap<String,Object> params, Model model){
        try{
            String dateEnd = params.get("dateEnd") == null?"":params.get("dateEnd").toString();
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            if(StringUtils.isEmpty(dateEnd) || dateEnd.compareTo(df.format(day)) >= 0){
                params.put("includeToday",1);
                if(sdf.format(new Date()).compareTo("18:00:00") >= 0){
                    params.put("artificialSend",1);
                }
            }
            PageConfig<Map<String,Object>> pageConfig = repaymentDetailService.assignStatistics(params);
            model.addAttribute("pm",pageConfig);
            model.addAttribute("params",params);
        }catch (Exception e){
            model.addAttribute("msg","服务器异常");
            log.error("assignStatistics error:{}",e);
            return "error";
        }
        return "custom/statistics";
    }

    @RequestMapping("assignBackMoneyStatistics")
    public String assignBackMoneyStatistics(@ModelAttribute("params") HashMap<String,Object> params, Model model){
        try{

            if(params.get("mobile")!=null || params.get("jobName")!=null || params.get("startDate")!=null){
                //手机号码输入后查询id
                if(params.get("mobile")!=null){
                    String backUserId =backUserService.getBackUserIdByPhone(String.valueOf(params.get("mobile")));
                    params.put("jobId",backUserId);
                }
                //有开始时间则判断结束时间 或给默认值
                if(params.get("startDate")!=null){
                    if(params.get("endDate")==null){
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        String endDate = sf.format(calendar.getTime());
                        params.put("endDate",endDate);
                    }
                }
                PageConfig<Map<String,Object>> pageConfig = repaymentDetailService.assignBackMoneyStatistics(params);
                model.addAttribute("pm",pageConfig);
            }
            //页面下方统计
            List<Map<String,Object>> assign = repaymentDetailService.selectAssignStatisticsBackMoney(params);
            model.addAttribute("assign",assign);
            model.addAttribute("params",params);
        }catch (Exception e){
            model.addAttribute("msg","服务器异常");
            log.error("assignBackMoneyStatistics error:{}",e);
            return "error";
        }
        return "custom/backMoneyStatistics";
    }
    @RequestMapping("outPutExcel")
    public void outPutExcel(HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> params = this.getParametersO(request);
        try {
            //客服ID
            String jobIds=null;
            String[] jobArray = null;
            if(params.get("jobIds")!=null){
                jobIds = params.get("jobIds").toString();
            }
            if (jobIds!=null ) {
                jobArray =  StringUtils.tokenizeToStringArray(jobIds, ",");
            }
            //row
            int size = 50000;
            //sheet
            int total = 0;
            params.put(Constant.PAGE_SIZE, size);
            PageConfig<Map<String,Object>> pageConfig =new PageConfig<>();
            //导出数据
            List<Map<String,Object>> list;
            if(jobArray!=null && jobArray.length >0){
                params.put("jobIds",jobArray);
                int totalPageNum = repaymentDetailService.assignBackMoneyStatisticsCount(params);
                if (totalPageNum > 0) {
                    if (totalPageNum % size > 0) {
                        total = totalPageNum / size + 1;
                    } else {
                        total = totalPageNum / size;
                    }
                }
                pageConfig = repaymentDetailService.assignBackMoneyStatistics(params);

            }

            list = pageConfig.getItems();
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            ExcelUtil.setFileDownloadHeader(request, response, "客服回款统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);

            String[] titles = {"客服","手机号", "早班派单总件数", "早班回款总件数", "早班回款率", "晚班派单总数", "晚班回款总数", "晚班回款率", "派单总件数", "回款总件数", "回款率"};
            for (int i = 1; i <= total; i++) {
                params.put(Constant.CURRENT_PAGE, i);
                List<Object[]> contents = new ArrayList<>();
                for(int j=0;j<list.size();j++){
                    Map<String,Object> map = list.get(j);
                    Object[] conList = new Object[titles.length];
                    conList[0] = map.get("jobName")==null?"":map.get("jobName");
                    conList[1] = map.get("phone")==null?"":map.get("phone");
                    conList[2] = map.get("assignCount1")==null?"":map.get("assignCount1");
                    conList[3] = map.get("payBackCount1")==null?"":map.get("payBackCount1");
                    if(map.get("payBackCount1")!=null && map.get("assignCount1")!=null){
                        conList[4] = nt.format(Double.parseDouble(String.valueOf(map.get("payBackCount1")))/Double.parseDouble(String.valueOf(map.get("assignCount1"))));
                    }else{
                        conList[4] = " ";
                    }
                    conList[5] = map.get("assignCount2")==null?"":map.get("assignCount2");
                    conList[6] = map.get("payBackCount2")==null?"":map.get("payBackCount2");
                    if(map.get("payBackCount2")!=null && map.get("assignCount2")!=null){
                        conList[7] = nt.format(Double.parseDouble(String.valueOf(map.get("payBackCount2")))/Double.parseDouble(String.valueOf(map.get("assignCount2"))));
                    }else{
                        conList[7] = " ";
                    }
                    Integer countAssign1 = Integer.valueOf(map.get("assignCount1")==null?"0":map.get("assignCount1").toString());
                    Integer countAssign2 = Integer.valueOf(map.get("assignCount2")==null?"0":map.get("assignCount2").toString());
                    Integer countAssign= countAssign1+countAssign2;
                    conList[8] = countAssign;
                    Integer payBackCount1 = Integer.valueOf(map.get("payBackCount1")==null?"0":map.get("payBackCount1").toString());
                    Integer payBackCount2 = Integer.valueOf(map.get("payBackCount2")==null?"0":map.get("payBackCount2").toString());
                    Integer countPayBack = payBackCount1 +payBackCount2;
                    conList[9] = countPayBack;
                    conList[10] = nt.format(countPayBack.doubleValue()/countAssign.doubleValue());
                    contents.add(conList);
                }
                ExcelUtil.buildExcel(workbook, "客服回款统计", titles, contents, i, pageConfig.getTotalPageNum(), os);
            }
        } catch (Exception e) {
            log.error("OutPutExcel error:{}", e);
        }
    }

}

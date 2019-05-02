package com.info.back.controller;

import com.info.back.pojo.DayRepayFailExcel;
import com.info.back.utils.ExcelUtil;
import com.info.web.dao.IRepaymentDetailDao;
import com.info.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cqry_2016 on 2018/4/9
 */
@Slf4j
@Controller
@RequestMapping("excel/")
public class GetExcelController {
    @Autowired
    private IRepaymentDetailDao repaymentDetailDao;

    /**
     * 当日手动还款未成功统计
     */
    @RequestMapping("getRepaymentFailExcel")
    public void getRepaymentFailExcel(HttpServletResponse response, HttpServletRequest request) {
        try {
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
                throw new RuntimeException("时间不能为空！");
            }
            List<DayRepayFailExcel> item = repaymentDetailDao.getRepayFailRecords(startTime, endTime);
//            int totalPageNum = item.size();
            ExcelUtil.setFileDownloadHeader(request, response, "当日手动还款未成功统计.xls");
            response.setContentType("application/msexcel");// 定义输出类型
            SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
            String[] titles = {"编号", "最后一次操作时间", "用户名", "手机号", "性别",
                    "预期还款时间", "终端类型", "备注"};
            List<Object[]> contents = new ArrayList<Object[]>();
            for (DayRepayFailExcel one : item) {
                Object[] conList = new Object[titles.length];
                conList[0] = one.getUserId();
                conList[1] = DateUtil.getDateFormat(one.getCreatedAt(), "yy-MM-dd hh:mm:ss");
                conList[2] = one.getRealname();
                conList[3] = one.getUserName();
                conList[4] = one.getUserSex();
                conList[5] = one.getRepaymentTime();
                conList[6] = one.getTermType();
                conList[7] = one.getRemark();
                contents.add(conList);
            }
            ExcelUtil.buildExcel(workbook, "统计", titles, contents, 1,
                    1, os);
        } catch (Exception e) {
            log.error("当日手动还款未成功统计导出失败:{}", e);
        }
    }
}

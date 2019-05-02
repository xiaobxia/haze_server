package com.info.back.controller;

import com.info.back.utils.Result;
import com.info.web.util.AliyunOSSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传至阿里云oss
 *
 * @author cqry_2016
 * @create 2018-08-30 10:56
 **/

@Slf4j
@Controller
@RequestMapping("oss/upload")
public class OssFileUploadController {

    @Resource
    private AliyunOSSClientUtil ossClientUtil;

    @RequestMapping("picture")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) {
        String nameSuffix = "pay_qrCode";
        try {
            if (file.isEmpty()) {
                return Result.OK;
            }
            ossClientUtil.createFolder(nameSuffix);
            String url = ossClientUtil.uploadAndGetUrl(file, nameSuffix);
            return Result.success(url);
        } catch (Exception e) {
            log.error("OssFileUploadController upload error: {}", e);
            return Result.error("上传失败.");
        }
    }
}

package com.info.web.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * Author :cqry_2017
 * Date   :2017-10-14 16:43.
 */
@Slf4j
@Component
@PropertySource(value = "classpath:aliyun-oss.properties")
public class AliyunOSSClientUtil {

    /**
     * 阿里云API的内或外网域名
     */
    @Value("${ENDPOINT}")
    private String endpoint;

    /**
     * 阿里云API的密钥Access Key ID
     */
    @Value("${ACCESS_KEY_ID}")
    private String accessKeyId;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    @Value("${ACCESS_KEY_SECRET}")
    private String accessKeySecret;

    /**
     * 阿里云API的bucket名称
     */
    @Value("${BACKET_NAME}")
    private String backetName;

    /**
     * 阿里云API的根文件夹名称
     */
    @Value("${ROOT_FOLDER}")
    private String folder;

    @Value("${CDN_DOMAIN_URL:}")
    private String cdnDomainUrl;


    private OSSClient ossClient;

    private String seprator = "/";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private OSSClient getOSSClient() {
        if (ossClient == null) {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return ossClient;
    }

    /**
     * 创建存储空间
     */
    public String createBucketName(OSSClient ossClient, String bucketName) {
        //存储空间
        if (!ossClient.doesBucketExist(bucketName)) {
            //不存在则创建
            Bucket bucket = ossClient.createBucket(bucketName);
            return bucket.getName();
        }
        return bucketName;
    }

    /**
     * 删除存储空间buckName
     *
     * @param ossClient  oss对象
     * @param bucketName 存储空间
     */
    public void deleteBucket(OSSClient ossClient, String bucketName) {
        ossClient.deleteBucket(bucketName);
//        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     *
     * @param folederName 模拟文件夹名如"qj_nanjing/"
     * @return 文件夹名
     */
    public String createFolder(String folederName) {
        //文件夹名
        final String keySuffixWithSlash = folder + seprator + folederName + seprator;
        //判断文件夹是否存在，不存在则创建
        if (!getOSSClient().doesObjectExist(this.backetName, keySuffixWithSlash)) {
            //创建文件夹
            getOSSClient().putObject(this.backetName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            //得到文件夹名
            OSSObject object = getOSSClient().getObject(this.backetName, keySuffixWithSlash);
            return object.getKey();
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
    }


    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    private String uploadFile2OSS(InputStream instream, String fileName, String fileNameSuffix) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = getOSSClient().putObject(backetName, folder + seprator + fileNameSuffix + seprator + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (Exception e) {
            log.error("uploadFile2OSS error: {}", e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    private String getContentType(String FilenameExtension) {
        if ("bmp".equalsIgnoreCase(FilenameExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(FilenameExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(FilenameExtension) ||
                "jpg".equalsIgnoreCase(FilenameExtension) ||
                "png".equalsIgnoreCase(FilenameExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(FilenameExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(FilenameExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.visio";
        }
        if ("pptx".equalsIgnoreCase(FilenameExtension) ||
                "ppt".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("docx".equalsIgnoreCase(FilenameExtension) ||
                "doc".equalsIgnoreCase(FilenameExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(FilenameExtension)) {
            return "text/xml";
        }
        //默认返回格式
        return "image/jpeg";
    }

    /**
     * 获得图片路径
     */
    private String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(folder  + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传图片并返回url
     *
     */
    public String uploadAndGetUrl(MultipartFile file, String nameSuffix) {
        return getUrl(uploadImg2Oss(file, nameSuffix));
    }

    /**
     * File转换为MultipartFile类型
     *
     */
    public MultipartFile file2MultipartFile(String path) throws IOException {
        File appImage = new File(path);
        InputStream inputStream = new FileInputStream(appImage);
        MultipartFile multipartFile = new MockMultipartFile("file", appImage.getName(), "text/plain", IOUtils.toByteArray(inputStream));
        return multipartFile;
    }

    /**
     * 上传图片
     *
     */
    public void uploadImg2Oss(String url) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1], "");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("图片上传失败");
        }
    }

    private String uploadImg2Oss(MultipartFile file, String fileNameSuffix) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String name = fileNameSuffix + "_" + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            uploadFile2OSS(inputStream, name, fileNameSuffix);
            return folder + seprator + fileNameSuffix + seprator + name;
        } catch (Exception e) {
            throw new RuntimeException("图片上传失败");
        }
    }

    /**
     * 获得url链接
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(backetName, key);
        req.setExpiration(expiration);
        URL url = getOSSClient().generatePresignedUrl(req);
        if (url != null) {
            if(!"".equals(cdnDomainUrl)) {
                return url.toString().substring(0, url.toString().indexOf("?")).replaceAll(backetName + "." + endpoint, cdnDomainUrl);
            } else {
                return url.toString().substring(0, url.toString().indexOf("?"));
            }
        }
        return null;
    }

    //测试
//    public static void main(String[] args) throws Exception {
    //初始化OSSClient
//        OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
    //上传文件
//        String files = "D:\\test.jpg";
//        String[] file = files.split(",");
//        for (String filename : file) {
//            //System.out.println("filename:"+filename);
//            File filess = new File(filename);
//
//            InputStream inputStream = new FileInputStream(files);
//            MultipartFile multipartFile = new MockMultipartFile("file", filess.getName(), "text/plain", IOUtils.toByteArray(inputStream));
//
//
//            AliyunOSSClientUtil util = new AliyunOSSClientUtil();
//            String name = util.uploadImg2Oss(multipartFile);
//            System.out.println("文件名: " + name);
//            System.out.println("文件路径：" + util.getImgUrl(name));
//            String md5key = AliyunOSSClientUtil.uploadFile2OSS(inputStream,filess.getName());
//            logger.info("上传后的文件MD5数字唯一签名:" + md5key);
//    }


}
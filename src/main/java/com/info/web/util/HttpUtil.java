package com.info.web.util;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import javax.activation.MimetypesFileTypeMap;
import java.net.URLConnection;
import java.util.Iterator;
@Slf4j
public class HttpUtil {

    private static final String CHARSET = "UTF-8";
    private static HttpUtil httpUtil;

    //定义sms
    //dc 数据类型
    private static final int DATACODING = 15;
    //rf 响应格式
    private static final int REPSPONSEFORMAT = 2;
    //rd 是否需要状态报告
    private static final int REPORTDATA = 1;
    //tf 短信内容的传输编码
    private static final int TRANSFERENCODING = 3;
    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    /**
     * 扫描图片信息
     *
     * @param urlStr str
     * @param textMap text
     * @param fileMap file
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     *                    contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUploadImage(String urlStr, Map<String, String> textMap, Map<String, String> fileMap, String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setChunkedStreamingMode(0);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes(CHARSET));
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        } else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        } else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes(CHARSET));
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // 读取返回数据
                StringBuffer strBuf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), CHARSET));

                String line ;
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line).append("\n");
                }
                res = strBuf.toString();
                reader.close();
            } else {
                StringBuffer error = new StringBuffer();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        conn.getErrorStream(), CHARSET));
                String line1 ;
                while ((line1 = bufferedReader.readLine()) != null) {
                    error.append(line1).append("\n");
                }
                res = error.toString();
                bufferedReader.close();
            }
            log.info("return :{}",res);
        } catch (Exception e) {
            log.error("formUploadImage error:{}",e);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    public static String getHttpMess(String surl, String inputParam, String requestMethod, String charset) {
        StringBuilder sbReturn = new StringBuilder();
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(surl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(2000);
            connection.setChunkedStreamingMode(0);
            connection.connect();
            OutputStream output = connection.getOutputStream();
            OutputStreamWriter osWriter = new OutputStreamWriter(output);
            osWriter.write(inputParam.toCharArray(), 0, inputParam.length());
            osWriter.flush();
            osWriter.close();
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {/* 4 判断访问的状态码 */
                return null;
            }
            InputStream in = null;
            in = connection.getInputStream();
            BufferedReader data = new BufferedReader(
                    new InputStreamReader(in, charset));
            String tempbf;
            while ((tempbf = data.readLine()) != null) {
                sbReturn.append(tempbf);
                sbReturn.append("\r\n");
            }
            data.close();
            in.close();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                connection = null;
            }
        }
        return sbReturn.toString();
    }

    public String doPost(String url, String params)
            throws IOException {
//		logger.info("请求参数:" + params.toString());
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(params.toString(), "utf-8");
        httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");

        httpPost.setEntity(stringEntity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent(), "utf-8"));
            String resultStr = reader.readLine();
//			logger.info("resultStr=" + resultStr);
            while (null != resultStr) {
                result = resultStr;
                resultStr = reader.readLine();
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static InputStream MxGet(String geturl, String Authorization) {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(geturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", Authorization);
            connection.connect();
            connection.disconnect();
            inputStream = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 调用 API
     *
     * @return str
     */
    public String post(String apiURL, List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(apiURL);
        String body = null;
//      logger.info("parameters:" + parameters);
        try {

            // 建立一个NameValuePair数组，用于存储欲传送的参数
//          method.addHeader("Content-type","application/json; charset=utf-8");
//          method.setHeader("Accept", "application/json");
            if (params != null) {
                // 设置字符集
                HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                // 设置参数实体
                method.setEntity(entity);
            }
//          method.setEntity(new SerializableEntity(parameters, Charset.forName("UTF-8")));

            HttpResponse response = httpClient.execute(method);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed:{}",response.getStatusLine());
            }

            // Read the response body
            body = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            // 网络错误
            log.error("http post exception :{}", e);
        }
        return body;
    }


    public static String postForm(String url, Map<String, Object> params) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        String sub = null;
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sub = sb.substring(0, sb.length() - 1);
        }
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            //// POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(1000);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sub);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    /**
     * 调用 API
     *
     * @return str
     */
    public static String postJson(String apiURL, String params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(apiURL);
        String body = null;
//      logger.info("parameters:" + parameters);
        try {

            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (params != null) {
                // 设置字符集
                StringEntity stringEntity = new StringEntity(params, "utf-8");
                // 设置参数实体
                httpPost.setEntity(stringEntity);
            }
//          httpPost.setEntity(new SerializableEntity(parameters, Charset.forName("UTF-8")));

            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed:" + response.getStatusLine());
            }

            // Read the response body
            body = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            // 网络错误
            log.error("http post exception :{}", e);
        }
        return body;
    }
    public String postJson(String apiURL, String params,int timeOut){
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiURL);
        String body = null;
        try {
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
            httpPost.setConfig(requestConfig);
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (params != null) {
                // 设置字符集
                StringEntity stringEntity = new StringEntity(params, "utf-8");
                // 设置参数实体
                httpPost.setEntity(stringEntity);
            }
//          httpPost.setEntity(new SerializableEntity(parameters, Charset.forName("UTF-8")));

            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed:{}" ,response.getStatusLine());
            }

            // Read the response body
            body = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            // 网络错误
            log.error("http post exception :{}", e);
        }
        return body;
    }

    /**
     * 天畅云 短信发送
     * @param url url
     * @param paramMap map
     * @return str
     */
    public static String sendPost(String url, Map<String, Object> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            paramMap.put("dc", DATACODING);
            paramMap.put("rf", REPSPONSEFORMAT);
            paramMap.put("rd", REPORTDATA);
            paramMap.put("tf", TRANSFERENCODING);
            // 设置请求属性
            String param = "";
            if (paramMap != null && paramMap.size() > 0) {
                Iterator<String> ite = paramMap.keySet().iterator();
                while (ite.hasNext()) {
                    // key
                    String key = ite.next();
                    Object value = paramMap.get(key);
                    param += key + "=" + value + "&";
                }
                param = param.substring(0, param.length() - 1);
                //System.out.println(param);
            }

            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("sendPost error:{}",e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 通过get来访问url
     *
     * @param src          需要访问的地址
     * @param outputEncode 获取输出时的编码
     * @param headers      需要添加的访问头信息
     * @return str
     */
    public static String doGet(String src, String outputEncode, HashMap<String, String> headers) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(src);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(3000);
        BufferedReader in = null;
        try {
            if (headers != null) {
                Iterator<String> iterators = headers.keySet().iterator();
                while (iterators.hasNext()) {
                    String key = iterators.next();
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), outputEncode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("\n" + line);
            }
            return result.toString();
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (in != null) {
                in.close();
            }
        }

    }



    public static String sendPost(String url,Map<String, String> headers, JSONObject data, String encoding) {
        /*log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + JSON.toJSONString(data));*/
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }
            // 设置实体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data)));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                //log.error("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
            //log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        return resultJson;
    }

}
